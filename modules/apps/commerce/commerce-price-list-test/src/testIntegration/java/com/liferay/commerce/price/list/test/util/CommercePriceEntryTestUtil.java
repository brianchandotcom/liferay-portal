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
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.service.CommerceCatalogLocalServiceUtil;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.math.BigDecimal;

import java.util.Currency;
import java.util.List;

/**
 * @author Zoltán Takács
 * @author Luca Pellizzon
 */
public class CommercePriceEntryTestUtil {

	public static CommercePriceEntry addCommercePriceEntry(long groupId)
		throws PortalException {

		Currency currency = Currency.getInstance(LocaleUtil.US);
		String name = RandomTestUtil.randomString();

		CPInstance cpInstance = CPTestUtil.addCPInstance(groupId);

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		List<CommerceCatalog> commerceCatalogs =
			CommerceCatalogLocalServiceUtil.getCommerceCatalogs(
				group.getCompanyId(), true);

		CommerceCatalog commerceCatalog = commerceCatalogs.get(0);

		CommercePriceList commercePriceList =
			CommercePriceListTestUtil.addCommercePriceList(
				commerceCatalog.getGroupId(), currency.getCurrencyCode(), name,
				RandomTestUtil.randomDouble(), true, null, null, null);

		double price = RandomTestUtil.randomDouble();
		double promoPrice = RandomTestUtil.randomDouble();

		return addCommercePriceEntry(
			null, cpInstance.getCPInstanceId(),
			commercePriceList.getCommercePriceListId(), price, promoPrice);
	}

	public static CommercePriceEntry addCommercePriceEntry(
			String externalReferenceCode, long skuId, long commercePriceListId,
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

	public static CommercePriceEntry addCommercePriceEntry(
			String externalReferenceCode, long cpProductId,
			String cpInstanceUuid, long commercePriceListId, BigDecimal price)
		throws PortalException {

		CommercePriceList commercePriceList =
			CommercePriceListLocalServiceUtil.getCommercePriceList(
				commercePriceListId);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				commercePriceList.getGroupId());

		return CommercePriceEntryLocalServiceUtil.addCommercePriceEntry(
			externalReferenceCode, cpProductId, cpInstanceUuid,
			commercePriceListId, price, BigDecimal.ZERO, serviceContext);
	}

	public static CommercePriceEntry upsertCommercePriceEntry(
			String externalReferenceCode, long commercePriceEntryId, long skuId,
			long commercePriceListId, String skuExternalReferenceCode,
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