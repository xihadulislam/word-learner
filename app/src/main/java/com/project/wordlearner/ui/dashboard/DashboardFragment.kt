package com.project.wordlearner.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.project.wordlearner.R
import java.util.*
import kotlin.math.abs

class DashboardFragment : Fragment() {

    companion object {
        fun newInstance() = DashboardFragment()
    }

    private lateinit var viewModel: DashboardViewModel

    private lateinit var recyclerView: RecyclerView

    private lateinit var adapter: DashboardAdapter

    private lateinit var sliderAdapter: CategorySliderAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    var timer: Timer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)

//        recyclerView = view.findViewById(R.id.dashboardList)
//        adapter = DashboardAdapter(requireContext())
//
//        recyclerView.also {
//            it.setHasFixedSize(true)
//            it.setItemViewCacheSize(20)
//            ViewCompat.setNestedScrollingEnabled(it, false)
//            it.layoutManager = LinearLayoutManager(requireContext())
//            it.adapter = adapter
//        }
//
//        val list = mutableListOf<String>()
//        list.add("trtertre")
//        list.add("trtertre")
//        list.add("trtertre")
//        list.add("trtertre")
//
//        adapter.setUpdatedList(list)

        viewPager = view.findViewById(R.id.newsSliderViewPagerID)
        tabLayout = view.findViewById(R.id.tabIndicatorId)


        sliderAdapter = CategorySliderAdapter()
        viewPager.adapter = sliderAdapter
        viewPager.clipToPadding = false
        viewPager.clipChildren = false
        viewPager.offscreenPageLimit = 3
        viewPager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(40))
        compositePageTransformer.addTransformer { page, position ->

            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.15f
        }
        viewPager.setPageTransformer(compositePageTransformer)


        val timerTask: TimerTask = object : TimerTask() {
            override fun run() {
                viewPager.post {
                    viewPager.currentItem = (viewPager.currentItem + 1) % 5
                }
            }
        }
        timer = Timer()
        timer?.schedule(timerTask, 6000, 6000)


        val list = mutableListOf<String>()
        list.add("trtertre")
        list.add("trtertre")
        list.add("trtertre")
        list.add("trtertre")

        setUpNewsSlider(list)

    }

    private fun setUpNewsSlider(sliderList: List<String>) {
        sliderAdapter.setNewList(sliderList)
        TabLayoutMediator(tabLayout, viewPager) { tab, _ ->
            viewPager.currentItem = tab.position
        }.attach()
    }

}