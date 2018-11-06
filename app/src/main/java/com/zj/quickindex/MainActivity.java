package com.zj.quickindex;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MotionEvent;
import android.view.View;

import com.zj.quickindex.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private ActivityMainBinding binding;
    private CoinBarAdapter coinBarAdapter;
    List<String> partentList=new ArrayList<>();
    List<List<PairInfoBean>> childList=new ArrayList<>();

    private ExpandCoinAdapter expandCoinAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil. setContentView(this,R.layout.activity_main);
        binding.setPresenter(this);

        binding.recycleList.setHasFixedSize(true);
        binding.recycleList.setLayoutManager(new LinearLayoutManager(this));
        coinBarAdapter=new CoinBarAdapter(this);
        binding.recycleList.setAdapter(coinBarAdapter);

        expandCoinAdapter=new ExpandCoinAdapter(this,partentList,childList);
        binding.expandedMenu.setAdapter(expandCoinAdapter);
        binding.expandedMenu.setGroupIndicator(null);

        getBar();
        getExpandData();

        binding.recycleList.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (coinBarAdapter.getDataCount()>0){
                    float height=binding.recycleList.getHeight()/coinBarAdapter.getDataCount();
                    float x=0;
                    float y=0;
                    switch (event.getAction()){

                        case MotionEvent.ACTION_DOWN:
                            y=Math.abs(event.getY());
                            int downPos= (int) (y/height);
                            binding.expandedMenu.setSelectedGroup(downPos);
                            break;
                        case MotionEvent.ACTION_MOVE:
                            y=Math.abs(event.getY());
                            int pos= (int) (y/height);

                            binding.expandedMenu.setSelectedGroup(pos);
//                            showToast(coinBarAdapter.getDataList().get(pos));
                            break;
                        case MotionEvent.ACTION_UP:


                            break;
                    }
                }
                return false;
            }
        });
    }



    public void getExpandData(){

        char  a='A';
        for (int i=0;i<26;i++){
            partentList.add(a+"");
            List<PairInfoBean> pairInfoBeanList=new ArrayList<>();
            for (int j=0;j<10;j++){
                PairInfoBean pairInfoBean=new PairInfoBean();
                pairInfoBean.pairName=a+"/"+j;
                pairInfoBeanList.add(pairInfoBean);
            }
            childList.add(pairInfoBeanList);
            a++;

        }
        expandCoinAdapter.notifyDataSetChanged();

        for (int i=0;i<expandCoinAdapter.getGroupCount();i++){
            binding.expandedMenu.expandGroup(i);
        }
    }


    public void getBar(){
        List<String> list=new ArrayList<>();
        char  a='A';
        for (int i=0;i<26;i++){
            list.add(a+"");
            a++;
        }
        coinBarAdapter.setData(list);


    }
}
