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

package com.liferay.commerce.internal.util;

import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.currency.model.CommerceMoneyFactory;
import com.liferay.commerce.discount.CommerceDiscountValue;
import com.liferay.commerce.tax.CommerceTaxCalculation;
import com.liferay.commerce.tax.CommerceTaxValue;
import com.liferay.commerce.util.CommerceBigDecimalUtil;
import com.liferay.portal.kernel.exception.PortalException;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import java.util.List;

/**
 * @author Riccardo Alberti
 */
public class CommercePriceConverterUtil {

	public static CommerceDiscountValue getConvertedCommerceDiscountValue(
		CommerceDiscountValue commerceDiscountValue, BigDecimal initialPrice,
		BigDecimal discountedPrice, CommerceMoneyFactory commerceMoneyFactory,
		RoundingMode roundingMode) {

		if (commerceDiscountValue == null) {
			return null;
		}

		CommerceMoney currentDiscountAmountCommerceMoney =
			commerceDiscountValue.getDiscountAmount();

		BigDecimal discountAmount = initialPrice.subtract(discountedPrice);

		CommerceMoney convertedDiscountAmountCommerceMoney =
			commerceMoneyFactory.create(
				currentDiscountAmountCommerceMoney.getCommerceCurrency(),
				discountAmount);

		BigDecimal discountPercentage = _ONE_HUNDRED;

		if (CommerceBigDecimalUtil.gt(discountedPrice, BigDecimal.ZERO)) {
			discountPercentage = _getDiscountPercentage(
				discountedPrice, initialPrice, roundingMode);
		}

		return new CommerceDiscountValue(
			commerceDiscountValue.getId(), convertedDiscountAmountCommerceMoney,
			discountPercentage,
			_getPercentages(
				commerceDiscountValue.getDiscountPercentage(),
				discountPercentage, commerceDiscountValue.getPercentages(),
				roundingMode));
	}

	public static BigDecimal getConvertedPrice(
			long commerceChannelGroupId, long cpInstanceId,
			long commerceBillingAddressId, long commerceShippingAddressId,
			BigDecimal price, boolean includeTax,
			CommerceTaxCalculation commerceTaxCalculation)
		throws PortalException {

		if (CommerceBigDecimalUtil.isZero(price) && includeTax) {
			return price;
		}

		List<CommerceTaxValue> commerceTaxValues =
			commerceTaxCalculation.getCommerceTaxValues(
				commerceChannelGroupId, cpInstanceId, commerceBillingAddressId,
				commerceShippingAddressId, price, includeTax);

		if (commerceTaxValues.isEmpty()) {
			return price;
		}

		BigDecimal taxAmount = BigDecimal.ZERO;

		for (CommerceTaxValue commerceTaxValue : commerceTaxValues) {
			taxAmount = taxAmount.add(commerceTaxValue.getAmount());
		}

		if (includeTax) {
			return price.subtract(taxAmount);
		}

		return price.add(taxAmount);
	}

	private static BigDecimal _getDiscountPercentage(
		BigDecimal discountedAmount, BigDecimal amount,
		RoundingMode roundingMode) {

		double actualPrice = discountedAmount.doubleValue();
		double originalPrice = amount.doubleValue();

		double percentage = actualPrice / originalPrice;

		BigDecimal discountPercentage = new BigDecimal(percentage);

		discountPercentage = discountPercentage.multiply(_ONE_HUNDRED);

		MathContext mathContext = new MathContext(
			discountPercentage.precision(), roundingMode);

		return _ONE_HUNDRED.subtract(discountPercentage, mathContext);
	}

	private static BigDecimal[] _getPercentages(
		BigDecimal currentPercentage, BigDecimal percentage,
		BigDecimal[] percentages, RoundingMode roundingMode) {

		if ((currentPercentage == null) ||
			CommerceBigDecimalUtil.isZero(currentPercentage) ||
			(percentage == null) || CommerceBigDecimalUtil.isZero(percentage)) {

			return new BigDecimal[] {
				BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,
				BigDecimal.ZERO
			};
		}

		BigDecimal percentageRatio = percentage.divide(
			currentPercentage, _SCALE, roundingMode);

		for (int i = 0; i < percentages.length; i++) {
			if ((percentages[i] != null) &&
				!CommerceBigDecimalUtil.isZero(percentages[i])) {

				percentages[i] = percentages[i].multiply(percentageRatio);
			}
		}

		return percentages;
	}

	private static final BigDecimal _ONE_HUNDRED = BigDecimal.valueOf(100);

	private static final int _SCALE = 10;

}