package id.bungamungil.flatmap_map

import id.bungamungil.flatmap_map.model.Product
import id.bungamungil.flatmap_map.model.ProductGroup
import io.reactivex.Single
import java.math.BigDecimal

fun main() {
    val productGroup = ProductGroup("ProductGroup A", listOf(
        Product("Product A1", BigDecimal(99_900_000)),
        Product("Product A2", BigDecimal(9_900_000)),
        Product("Product A3", BigDecimal(18_880_000)),
        Product("Product A4", BigDecimal(100_900_000))
    ))
    Single.just(productGroup)
        .flatMap(::countTotalPriceByFlatMap)
        .subscribe { totalPrice: BigDecimal?, error: Throwable? ->
            println("Total Price ${totalPrice}")
        }
    Single.just(productGroup)
        .map(::countTotalPriceByMap)
        .subscribe { totalPrice: BigDecimal?, erorr: Throwable? ->
            println("Total Price ${totalPrice}")
        }
}

fun countTotalPriceByFlatMap(productGroup: ProductGroup): Single<BigDecimal> {
    return Single.create { emitter ->
        var totalPrice = BigDecimal.ZERO
        productGroup.products.forEach { product: Product ->
            totalPrice += product.price
        }
        emitter.onSuccess(totalPrice)
    }
}

fun countTotalPriceByMap(productGroup: ProductGroup): BigDecimal {
    var totalPrice = BigDecimal.ZERO
    productGroup.products.forEach { product: Product ->
        totalPrice += product.price
    }
    return totalPrice
}
