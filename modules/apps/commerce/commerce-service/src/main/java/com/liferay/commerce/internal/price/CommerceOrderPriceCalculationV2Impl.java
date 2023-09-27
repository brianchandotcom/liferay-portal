/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.price;

import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.discount.CommerceDiscountCalculation;
import com.liferay.commerce.discount.CommerceDiscountValue;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.price.CommerceOrderPrice;
import com.liferay.commerce.price.CommerceOrderPriceCalculation;
import com.liferay.commerce.price.CommerceOrderPriceImpl;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.tax.CommerceTaxCalculation;
import com.liferay.portal.kernel.exception.PortalException;

import java.math.BigDecimal;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 * @author Marco Leo
 */
@Component(service = CommerceOrderPriceCalculation.class)
public class CommerceOrderPriceCalculationV2Impl
	extends BaseCommerceOrderPriceCalculation {

	@Override
	public CommerceOrderPrice getCommerceOrderPrice(
			CommerceOrder commerceOrder, boolean secure,
			CommerceContext commerceContext)
		throws PortalException {

		if (commerceOrder == null) {
			return getEmptyCommerceOrderPrice(
				commerceContext.getCommerceCurrency());
		}

		if (!commerceOrder.isOpen()) {
			return getCommerceOrderPriceFromOrder(commerceOrder);
		}

		CommerceDiscountValue orderShippingCommerceDiscountValue;
		CommerceDiscountValue orderSubtotalCommerceDiscountValue;
		CommerceDiscountValue orderTotalCommerceDiscountValue;

		BigDecimal shippingAmount = commerceOrder.getShippingAmount();

		CommerceCurrency commerceCurrency = commerceOrder.getCommerceCurrency();

		CommerceMoney shippingTaxAmountCommerceMoney =
			_commerceTaxCalculation.getShippingTaxValue(
				commerceOrder, commerceCurrency);

		BigDecimal shippingWithTaxAmount = shippingAmount.add(
			shippingTaxAmountCommerceMoney.getPrice());

		CommerceMoney subtotalCommerceMoney = getSubtotal(
			commerceOrder, secure, commerceContext);

		BigDecimal subtotalAmount = subtotalCommerceMoney.getPrice();

		BigDecimal totalAmount = subtotalAmount;

		CommerceMoney taxValueCommerceMoney = getTaxValue(
			commerceOrder, secure, commerceContext);

		BigDecimal subtotalWithTaxAmount = subtotalAmount.add(
			taxValueCommerceMoney.getPrice());

		BigDecimal totalTaxValue = taxValueCommerceMoney.getPrice();

		totalTaxValue = totalTaxValue.add(
			shippingTaxAmountCommerceMoney.getPrice());

		BigDecimal totalWithTaxAmount = totalAmount.add(totalTaxValue);

		BigDecimal shippingDiscounted = shippingAmount;
		BigDecimal shippingDiscountedWithTaxAmount = shippingWithTaxAmount;
		BigDecimal subtotalDiscounted = subtotalAmount;
		BigDecimal subtotalDiscountedWithTaxAmount = subtotalWithTaxAmount;
		BigDecimal totalDiscounted = totalAmount;
		BigDecimal totalDiscountedWithTaxAmount = totalWithTaxAmount;

		boolean discountsTargetNetPrice = true;

		CommerceChannel commerceChannel =
			commerceChannelLocalService.fetchCommerceChannel(
				commerceContext.getCommerceChannelId());

		if (commerceChannel != null) {
			discountsTargetNetPrice =
				commerceChannel.isDiscountsTargetNetPrice();
		}

		if (discountsTargetNetPrice) {
			orderShippingCommerceDiscountValue =
				_commerceDiscountCalculation.
					getOrderShippingCommerceDiscountValue(
						commerceOrder, shippingAmount, commerceContext);

			orderSubtotalCommerceDiscountValue =
				_commerceDiscountCalculation.
					getOrderSubtotalCommerceDiscountValue(
						commerceOrder, subtotalAmount, commerceContext);

			if (orderSubtotalCommerceDiscountValue != null) {
				CommerceMoney discountAmountCommerceMoney =
					orderSubtotalCommerceDiscountValue.getDiscountAmount();

				totalAmount = totalAmount.subtract(
					discountAmountCommerceMoney.getPrice());

				subtotalDiscounted = subtotalDiscounted.subtract(
					discountAmountCommerceMoney.getPrice());
			}

			totalAmount = totalAmount.add(shippingAmount);

			if (orderShippingCommerceDiscountValue != null) {
				CommerceMoney discountAmountCommerceMoney =
					orderShippingCommerceDiscountValue.getDiscountAmount();

				totalAmount = totalAmount.subtract(
					discountAmountCommerceMoney.getPrice());

				shippingDiscounted = shippingDiscounted.subtract(
					discountAmountCommerceMoney.getPrice());

				BigDecimal discountPercentage =
					orderShippingCommerceDiscountValue.getDiscountPercentage();

				BigDecimal shippingTaxAmount =
					shippingTaxAmountCommerceMoney.getPrice();

				BigDecimal shippingTaxValueDifference =
					shippingTaxAmount.multiply(discountPercentage);

				shippingTaxValueDifference = shippingTaxValueDifference.divide(
					_ONE_HUNDRED);

				totalTaxValue = totalTaxValue.subtract(
					shippingTaxValueDifference);
			}

			totalDiscounted = totalAmount;

			orderTotalCommerceDiscountValue =
				_commerceDiscountCalculation.getOrderTotalCommerceDiscountValue(
					commerceOrder, totalAmount, commerceContext);

			if (orderTotalCommerceDiscountValue != null) {
				CommerceMoney discountAmountCommerceMoney =
					orderTotalCommerceDiscountValue.getDiscountAmount();

				totalDiscounted = totalDiscounted.subtract(
					discountAmountCommerceMoney.getPrice());
			}

			subtotalDiscountedWithTaxAmount = subtotalDiscounted.add(
				taxValueCommerceMoney.getPrice());
			shippingDiscountedWithTaxAmount = shippingDiscounted.add(
				shippingTaxAmountCommerceMoney.getPrice());
			totalDiscountedWithTaxAmount = totalDiscounted.add(totalTaxValue);

			totalWithTaxAmount = totalAmount.add(totalTaxValue);
		}
		else {
			orderShippingCommerceDiscountValue =
				_commerceDiscountCalculation.
					getOrderShippingCommerceDiscountValue(
						commerceOrder, shippingWithTaxAmount, commerceContext);

			orderSubtotalCommerceDiscountValue =
				_commerceDiscountCalculation.
					getOrderSubtotalCommerceDiscountValue(
						commerceOrder, subtotalWithTaxAmount, commerceContext);

			if (orderSubtotalCommerceDiscountValue != null) {
				CommerceMoney discountAmountCommerceMoney =
					orderSubtotalCommerceDiscountValue.getDiscountAmount();

				totalWithTaxAmount = totalWithTaxAmount.subtract(
					discountAmountCommerceMoney.getPrice());

				subtotalDiscountedWithTaxAmount =
					subtotalDiscountedWithTaxAmount.subtract(
						discountAmountCommerceMoney.getPrice());
			}

			totalWithTaxAmount = totalWithTaxAmount.add(shippingAmount);

			if (orderShippingCommerceDiscountValue != null) {
				CommerceMoney discountAmountCommerceMoney =
					orderShippingCommerceDiscountValue.getDiscountAmount();

				totalWithTaxAmount = totalWithTaxAmount.subtract(
					discountAmountCommerceMoney.getPrice());

				shippingDiscountedWithTaxAmount =
					shippingDiscountedWithTaxAmount.subtract(
						discountAmountCommerceMoney.getPrice());

				CommerceDiscountValue
					orderShippingWithoutTaxCommerceDiscountValue =
						_commerceDiscountCalculation.
							getOrderShippingCommerceDiscountValue(
								commerceOrder, shippingAmount, commerceContext);

				if (orderShippingWithoutTaxCommerceDiscountValue != null) {
					CommerceMoney discountWithoutTaxAmountCommerceMoney =
						orderShippingWithoutTaxCommerceDiscountValue.
							getDiscountAmount();

					BigDecimal discountWithoutTax =
						discountWithoutTaxAmountCommerceMoney.getPrice();

					BigDecimal discountWithTax =
						discountAmountCommerceMoney.getPrice();

					BigDecimal shippingTaxValueDifference =
						discountWithTax.subtract(discountWithoutTax);

					totalTaxValue = totalTaxValue.subtract(
						shippingTaxValueDifference);
				}
			}

			totalDiscountedWithTaxAmount = totalWithTaxAmount;

			orderTotalCommerceDiscountValue =
				_commerceDiscountCalculation.getOrderTotalCommerceDiscountValue(
					commerceOrder, totalWithTaxAmount, commerceContext);

			if (orderTotalCommerceDiscountValue != null) {
				CommerceMoney discountAmountCommerceMoney =
					orderTotalCommerceDiscountValue.getDiscountAmount();

				totalDiscountedWithTaxAmount =
					totalDiscountedWithTaxAmount.subtract(
						discountAmountCommerceMoney.getPrice());
			}

			shippingDiscounted = shippingDiscountedWithTaxAmount.subtract(
				shippingTaxAmountCommerceMoney.getPrice());
			subtotalDiscounted = subtotalDiscountedWithTaxAmount.subtract(
				taxValueCommerceMoney.getPrice());
			totalDiscounted = totalDiscountedWithTaxAmount.subtract(
				totalTaxValue);

			totalAmount = totalWithTaxAmount.subtract(totalTaxValue);
		}

		// fill data

		CommerceOrderPriceImpl commerceOrderPriceImpl =
			new CommerceOrderPriceImpl();

		setDiscountValues(
			discountsTargetNetPrice, shippingAmount, shippingDiscounted,
			orderShippingCommerceDiscountValue, subtotalAmount,
			subtotalDiscounted, orderSubtotalCommerceDiscountValue, totalAmount,
			totalDiscounted, orderTotalCommerceDiscountValue,
			commerceOrderPriceImpl, commerceOrder);

		commerceOrderPriceImpl.setShippingValue(
			commerceMoneyFactory.create(
				commerceOrder.getCommerceCurrency(), shippingAmount));
		commerceOrderPriceImpl.setShippingValueWithTaxAmount(
			commerceMoneyFactory.create(
				commerceOrder.getCommerceCurrency(), shippingWithTaxAmount));
		commerceOrderPriceImpl.setSubtotal(subtotalCommerceMoney);
		commerceOrderPriceImpl.setSubtotalWithTaxAmount(
			commerceMoneyFactory.create(
				commerceOrder.getCommerceCurrency(), subtotalWithTaxAmount));
		commerceOrderPriceImpl.setTaxValue(
			commerceMoneyFactory.create(
				commerceOrder.getCommerceCurrency(), totalTaxValue));
		commerceOrderPriceImpl.setTotal(
			commerceMoneyFactory.create(
				commerceOrder.getCommerceCurrency(),
				totalDiscounted.add(totalTaxValue)));
		commerceOrderPriceImpl.setTotalWithTaxAmount(
			commerceMoneyFactory.create(
				commerceOrder.getCommerceCurrency(),
				totalDiscountedWithTaxAmount));

		setDiscountValuesWithTaxAmount(
			discountsTargetNetPrice, shippingWithTaxAmount,
			shippingDiscountedWithTaxAmount, orderShippingCommerceDiscountValue,
			subtotalWithTaxAmount, subtotalDiscountedWithTaxAmount,
			orderSubtotalCommerceDiscountValue, totalWithTaxAmount,
			totalDiscountedWithTaxAmount, orderTotalCommerceDiscountValue,
			commerceOrderPriceImpl, commerceOrder);

		return commerceOrderPriceImpl;
	}

	@Override
	public CommerceOrderPrice getCommerceOrderPrice(
			CommerceOrder commerceOrder, CommerceContext commerceContext)
		throws PortalException {

		return getCommerceOrderPrice(commerceOrder, true, commerceContext);
	}

	@Override
	public CommerceMoney getSubtotal(
			CommerceOrder commerceOrder, boolean secure,
			CommerceContext commerceContext)
		throws PortalException {

		BigDecimal subtotal = BigDecimal.ZERO;

		if (commerceOrder == null) {
			return commerceMoneyFactory.create(
				commerceContext.getCommerceCurrency(), subtotal);
		}

		if (!commerceOrder.isOpen()) {
			return commerceMoneyFactory.create(
				commerceContext.getCommerceCurrency(),
				commerceOrder.getSubtotal());
		}

		for (CommerceOrderItem commerceOrderItem :
				commerceOrder.getCommerceOrderItems()) {

			subtotal = subtotal.add(commerceOrderItem.getFinalPrice());
		}

		return commerceMoneyFactory.create(
			commerceContext.getCommerceCurrency(), subtotal);
	}

	@Override
	public CommerceMoney getSubtotal(
			CommerceOrder commerceOrder, CommerceContext commerceContext)
		throws PortalException {

		return getSubtotal(commerceOrder, true, commerceContext);
	}

	@Override
	public CommerceMoney getTaxValue(
			CommerceOrder commerceOrder, boolean secure,
			CommerceContext commerceContext)
		throws PortalException {

		if (commerceOrder == null) {
			return commerceMoneyFactory.emptyCommerceMoney();
		}

		if (!commerceOrder.isOpen()) {
			return commerceMoneyFactory.create(
				commerceContext.getCommerceCurrency(),
				commerceOrder.getTaxAmount());
		}

		return _commerceTaxCalculation.getTaxAmount(
			commerceOrder, commerceContext.getCommerceCurrency());
	}

	@Override
	public CommerceMoney getTaxValue(
			CommerceOrder commerceOrder, CommerceContext commerceContext)
		throws PortalException {

		return getTaxValue(commerceOrder, true, commerceContext);
	}

	@Override
	public CommerceMoney getTotal(
			CommerceOrder commerceOrder, boolean secure,
			CommerceContext commerceContext)
		throws PortalException {

		if (!commerceOrder.isOpen()) {
			return commerceMoneyFactory.create(
				commerceContext.getCommerceCurrency(),
				commerceOrder.getTotal());
		}

		CommerceOrderPrice commerceOrderPrice = getCommerceOrderPrice(
			commerceOrder, commerceContext);

		return commerceOrderPrice.getTotal();
	}

	@Override
	public CommerceMoney getTotal(
			CommerceOrder commerceOrder, CommerceContext commerceContext)
		throws PortalException {

		return getTotal(commerceOrder, true, commerceContext);
	}

	private static final BigDecimal _ONE_HUNDRED = BigDecimal.valueOf(100);

	@Reference
	private CommerceDiscountCalculation _commerceDiscountCalculation;

	@Reference
	private CommerceTaxCalculation _commerceTaxCalculation;

}