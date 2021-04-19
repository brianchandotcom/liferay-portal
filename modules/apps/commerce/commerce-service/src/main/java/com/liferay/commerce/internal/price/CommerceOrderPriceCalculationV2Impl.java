/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.commerce.internal.price;

import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.currency.model.CommerceMoneyFactory;
import com.liferay.commerce.discount.CommerceDiscountCalculation;
import com.liferay.commerce.discount.CommerceDiscountValue;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.price.CommerceOrderPrice;
import com.liferay.commerce.price.CommerceOrderPriceImpl;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.tax.CommerceTaxCalculation;
import com.liferay.portal.kernel.exception.PortalException;

import java.math.BigDecimal;

/**
 * @author Alessio Antonio Rendina
 * @author Marco Leo
 */
public class CommerceOrderPriceCalculationV2Impl
	extends BaseCommerceOrderPriceCalculation {

	public CommerceOrderPriceCalculationV2Impl(
		CommerceChannelLocalService commerceChannelLocalService,
		CommerceDiscountCalculation commerceDiscountCalculation,
		CommerceMoneyFactory commerceMoneyFactory,
		CommerceTaxCalculation commerceTaxCalculation) {

		super(commerceChannelLocalService, commerceMoneyFactory);

		_commerceDiscountCalculation = commerceDiscountCalculation;
		_commerceTaxCalculation = commerceTaxCalculation;
	}

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

	private final CommerceDiscountCalculation _commerceDiscountCalculation;
	private final CommerceTaxCalculation _commerceTaxCalculation;

}