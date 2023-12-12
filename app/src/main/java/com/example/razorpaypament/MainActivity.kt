package com.example.razorpaypament

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.razorpaypament.databinding.ActivityMainBinding
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONObject
import java.lang.Exception

class MainActivity : AppCompatActivity(), PaymentResultListener {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.paymentBtn.setOnClickListener {
            val payment = binding.editPayment.text.toString().trim().toInt()
            savePayment(payment)
        }
        Checkout.preload(this@MainActivity)
    }

    private fun savePayment(payment: Int) {
        val checkOut = Checkout()
        checkOut.setKeyID("rzp_test_9JZiWARukAUmQR")

        try {
            val option = JSONObject()
            option.put("name","Payment Demo")
            option.put("des","Pay bills of our shopping..")
            option.put("them.color","#ff5500")
            option.put("currency","INR")
            option.put("amount",payment*100)

            val retryOpt  = JSONObject()
            retryOpt.put("enable",true)
            retryOpt.put("max_count",4)
            retryOpt.put("retryOpt",retryOpt)

            checkOut.open(this@MainActivity,option)
        }
        catch (e: Exception){
            Toast.makeText(this@MainActivity, e.message.toString(), Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }

    override fun onPaymentSuccess(p0: String?) {
        binding.getSuccessId.text=p0
        binding.getSuccessId.setTextColor(Color.GREEN)
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        binding.getSuccessId.text=p1
        binding.getSuccessId.setTextColor(Color.RED)
    }
}