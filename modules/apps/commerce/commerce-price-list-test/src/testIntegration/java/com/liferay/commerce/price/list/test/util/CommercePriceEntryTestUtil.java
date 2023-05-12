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
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.service.CPInstanceLocalServiceUtil;
import com.liferay.commerce.product.service.CommerceCatalogLocalServiceUtil;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.math.BigDecimal;

import java.util.Calendar;
import java.util.Currency;
import java.util.GregorianCalendar;
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
				null, commerceCatalog.getGroupId(), currency.getCurrencyCode(),
				name, RandomTestUtil.randomDouble(), true, null, null);

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

		CPInstance cpInstance = CPInstanceLocalServiceUtil.getCPInstance(skuId);

		CPDefinition cpDefinition = cpInstance.getCPDefinition();

		return CommercePriceEntryLocalServiceUtil.addCommercePriceEntry(
			externalReferenceCode, cpDefinition.getCProductId(),
			cpInstance.getCPInstanceUuid(), commercePriceListId,
			BigDecimal.valueOf(price), BigDecimal.valueOf(promoPrice),
			ServiceContextTestUtil.getServiceContext(
				commercePriceList.getGroupId()));
	}

	public static CommercePriceEntry addCommercePriceEntry(
			String externalReferenceCode, long cpProductId,
			String cpInstanceUuid, long commercePriceListId, BigDecimal price)
		throws PortalException {

		CommercePriceList commercePriceList =
			CommercePriceListLocalServiceUtil.getCommercePriceList(
				commercePriceListId);

		return CommercePriceEntryLocalServiceUtil.addCommercePriceEntry(
			externalReferenceCode, cpProductId, cpInstanceUuid,
			commercePriceListId, price, BigDecimal.ZERO,
			ServiceContextTestUtil.getServiceContext(
				commercePriceList.getGroupId()));
	}

	public static CommercePriceEntry addOrUpdateCommercePriceEntry(
			String externalReferenceCode, long commercePriceEntryId, long skuId,
			long commercePriceListId, String skuExternalReferenceCode,
			Double price, Double promoPrice)
		throws PortalException {

		CommercePriceList commercePriceList =
			CommercePriceListLocalServiceUtil.getCommercePriceList(
				commercePriceListId);

		CPInstance cpInstance = CPInstanceLocalServiceUtil.getCPInstance(skuId);

		CPDefinition cpDefinition = cpInstance.getCPDefinition();

		Calendar now = new GregorianCalendar();

		return CommercePriceEntryLocalServiceUtil.addOrUpdateCommercePriceEntry(
			externalReferenceCode, commercePriceEntryId,
			cpDefinition.getCProductId(), cpInstance.getCPInstanceUuid(),
			commercePriceListId, true, null, null, null, null,
			now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH),
			now.get(Calendar.YEAR), now.get(Calendar.HOUR),
			now.get(Calendar.MINUTE), 0, 0, 0, 0, 0, true,
			BigDecimal.valueOf(price), false, BigDecimal.valueOf(promoPrice),
			skuExternalReferenceCode,
			ServiceContextTestUtil.getServiceContext(
				commercePriceList.getGroupId()));
	}

}