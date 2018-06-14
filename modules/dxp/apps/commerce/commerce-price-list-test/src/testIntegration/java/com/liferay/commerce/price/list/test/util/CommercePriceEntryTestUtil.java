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

package com.liferay.commerce.price.list.test.util;

import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceEntryLocalServiceUtil;
import com.liferay.commerce.price.list.service.CommercePriceListLocalServiceUtil;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;

import java.math.BigDecimal;

import java.util.Currency;
import java.util.Locale;

/**
 * @author Zoltán Takács
 */
public class CommercePriceEntryTestUtil {

	public static CommercePriceEntry addCommercePriceEntry(long groupId)
		throws PortalException {

		Currency currency = Currency.getInstance(Locale.US);
		String name = RandomTestUtil.randomString();

		CPInstance cpInstance = CPTestUtil.addCPInstance(groupId);

		CommercePriceList commercePriceList =
			CommercePriceListTestUtil.addCommercePriceList(
				groupId, currency.getCurrencyCode(), name,
				RandomTestUtil.randomDouble(), true, null, null, null);

		double price = RandomTestUtil.randomDouble();
		double promoPrice = RandomTestUtil.randomDouble();

		return addCommercePriceEntry(
			cpInstance.getCPInstanceId(),
			commercePriceList.getCommercePriceListId(), null, price,
			promoPrice);
	}

	public static CommercePriceEntry addCommercePriceEntry(
			long skuId, long commercePriceListId, String externalReferenceCode,
			Double price, Double promoPrice)
		throws PortalException {

		CommercePriceList commercePriceList =
			CommercePriceListLocalServiceUtil.getCommercePriceList(
				commercePriceListId);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				commercePriceList.getGroupId());

		return CommercePriceEntryLocalServiceUtil.addCommercePriceEntry(
			skuId, commercePriceListId, externalReferenceCode,
			BigDecimal.valueOf(price), BigDecimal.valueOf(promoPrice),
			serviceContext);
	}

	public static CommercePriceEntry upsertCommercePriceEntry(
			long commercePriceEntryId, long skuId, long commercePriceListId,
			String externalReferenceCode, String skuExternalReferenceCode,
			Double price, Double promoPrice)
		throws PortalException {

		CommercePriceList commercePriceList =
			CommercePriceListLocalServiceUtil.getCommercePriceList(
				commercePriceListId);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				commercePriceList.getGroupId());

		return CommercePriceEntryLocalServiceUtil.upsertCommercePriceEntry(
			commercePriceEntryId, skuId, commercePriceListId,
			externalReferenceCode, BigDecimal.valueOf(price),
			BigDecimal.valueOf(promoPrice), skuExternalReferenceCode,
			serviceContext);
	}

}