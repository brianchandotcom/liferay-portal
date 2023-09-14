/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.test.util.price.list;

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
			BigDecimal.valueOf(price), false, BigDecimal.valueOf(promoPrice),
			null,
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
			commercePriceListId, price, false, BigDecimal.ZERO, null,
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

		Calendar calendar = new GregorianCalendar();

		return CommercePriceEntryLocalServiceUtil.addOrUpdateCommercePriceEntry(
			externalReferenceCode, commercePriceEntryId,
			cpDefinition.getCProductId(), cpInstance.getCPInstanceUuid(),
			commercePriceListId, true, null, null, null, null,
			calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
			calendar.get(Calendar.YEAR), calendar.get(Calendar.HOUR),
			calendar.get(Calendar.MINUTE), 0, 0, 0, 0, 0, true,
			BigDecimal.valueOf(price), false, BigDecimal.valueOf(promoPrice),
			skuExternalReferenceCode, null,
			ServiceContextTestUtil.getServiceContext(
				commercePriceList.getGroupId()));
	}

	public static CommercePriceEntry addOrUpdateCommercePriceEntry(
			String externalReferenceCode, long commercePriceEntryId, long skuId,
			long commercePriceListId, String skuExternalReferenceCode,
			Double price, Double promoPrice, String unitOfMeasureKey)
		throws PortalException {

		CommercePriceList commercePriceList =
			CommercePriceListLocalServiceUtil.getCommercePriceList(
				commercePriceListId);

		CPInstance cpInstance = CPInstanceLocalServiceUtil.getCPInstance(skuId);

		CPDefinition cpDefinition = cpInstance.getCPDefinition();

		Calendar calendar = new GregorianCalendar();

		return CommercePriceEntryLocalServiceUtil.addOrUpdateCommercePriceEntry(
			externalReferenceCode, commercePriceEntryId,
			cpDefinition.getCProductId(), cpInstance.getCPInstanceUuid(),
			commercePriceListId, true, null, null, null, null,
			calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
			calendar.get(Calendar.YEAR), calendar.get(Calendar.HOUR),
			calendar.get(Calendar.MINUTE), 0, 0, 0, 0, 0, true,
			BigDecimal.valueOf(price), false, BigDecimal.valueOf(promoPrice),
			skuExternalReferenceCode, unitOfMeasureKey,
			ServiceContextTestUtil.getServiceContext(
				commercePriceList.getGroupId()));
	}

	public static void deleteCommercePriceEntries(
			String cpInstanceUuid, BigDecimal quantity, String unitOfMeasureKey)
		throws PortalException {

		CommercePriceEntryLocalServiceUtil.deleteCommercePriceEntries(
			cpInstanceUuid, quantity, unitOfMeasureKey);
	}

	public static List<CommercePriceEntry> getCommercePriceEntries(
		String cpInstanceUuid, BigDecimal quantity, String unitOfMeasureKey) {

		return CommercePriceEntryLocalServiceUtil.getCommercePriceEntries(
			cpInstanceUuid, quantity, unitOfMeasureKey);
	}

}