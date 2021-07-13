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

package com.liferay.commerce.test.util;

import com.liferay.commerce.product.model.CPTaxCategory;
import com.liferay.commerce.product.service.CPTaxCategoryLocalServiceUtil;
import com.liferay.commerce.tax.engine.fixed.service.CommerceTaxFixedRateAddressRelLocalServiceUtil;
import com.liferay.commerce.tax.model.CommerceTaxMethod;
import com.liferay.commerce.tax.service.CommerceTaxMethodLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;

import java.util.List;

/**
 * @author Riccardo Alberti
 */
public class CommerceTaxTestUtil {

	public static CommerceTaxMethod addCommerceByAddressTaxMethod(
			long userId, long commerceChannelGroupId, boolean percentage)
		throws PortalException {

		String commerceTaxMethodEngineKey = "by-address";

		return CommerceTaxMethodLocalServiceUtil.addCommerceTaxMethod(
			userId, commerceChannelGroupId,
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(), commerceTaxMethodEngineKey,
			percentage, true);
	}

	public static long addTaxCategoryId(long groupId) throws PortalException {
		CPTaxCategory cpTaxCategory =
			CPTaxCategoryLocalServiceUtil.addCPTaxCategory(
				StringPool.BLANK, RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(),
				ServiceContextTestUtil.getServiceContext(groupId));

		return cpTaxCategory.getCPTaxCategoryId();
	}

	public static long getDefaultCompanyTaxCategory(long groupId)
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		List<CPTaxCategory> cpTaxCategories =
			CPTaxCategoryLocalServiceUtil.getCPTaxCategories(
				serviceContext.getCompanyId());

		if (cpTaxCategories.isEmpty()) {
			return addTaxCategoryId(groupId);
		}

		CPTaxCategory cpTaxCategory = cpTaxCategories.get(0);

		return cpTaxCategory.getCPTaxCategoryId();
	}

	public static BigDecimal getPriceWithoutTaxAmount(
		BigDecimal priceWithTaxAmount, BigDecimal taxRate,
		RoundingMode roundingMode) {

		BigDecimal taxValue = priceWithTaxAmount.multiply(taxRate);

		BigDecimal denominator = _ONE_HUNDRED.add(taxRate);

		taxValue = taxValue.divide(denominator, _SCALE, roundingMode);

		return priceWithTaxAmount.subtract(taxValue);
	}

	public static BigDecimal getPriceWithTaxAmount(
		BigDecimal price, BigDecimal taxRate, RoundingMode roundingMode) {

		BigDecimal taxValue = price.multiply(taxRate);

		taxValue = taxValue.divide(_ONE_HUNDRED, _SCALE, roundingMode);

		return price.add(taxValue);
	}

	public static void setCommerceMethodTaxRate(
			long userId, long commerceChannelGroupId, long cpTaxCategoryId,
			long commerceTaxMethodId, double rate)
		throws PortalException {

		CommerceTaxFixedRateAddressRelLocalServiceUtil.
			addCommerceTaxFixedRateAddressRel(
				userId, commerceChannelGroupId, commerceTaxMethodId,
				cpTaxCategoryId, 0, 0, StringPool.BLANK, rate);
	}

	private static final BigDecimal _ONE_HUNDRED = new BigDecimal("100");

	private static final int _SCALE = 10;

}