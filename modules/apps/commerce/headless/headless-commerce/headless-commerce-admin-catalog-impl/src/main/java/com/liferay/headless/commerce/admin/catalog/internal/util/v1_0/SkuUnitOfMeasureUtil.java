/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.catalog.internal.util.v1_0;

import com.liferay.commerce.price.list.constants.CommercePriceListConstants;
import com.liferay.commerce.price.list.exception.CommercePriceEntryPriceException;
import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceEntryService;
import com.liferay.commerce.price.list.service.CommercePriceListLocalService;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CPInstanceUnitOfMeasure;
import com.liferay.commerce.product.service.CPInstanceUnitOfMeasureService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.SkuUnitOfMeasure;
import com.liferay.headless.commerce.core.util.LanguageUtils;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;

import java.math.BigDecimal;

/**
 * @author João Victor Cordeiro
 */
public class SkuUnitOfMeasureUtil {

	public static CPInstanceUnitOfMeasure addOrUpdateCPInstanceUnitOfMeasure(
			CPInstanceUnitOfMeasureService cpInstanceUnitOfMeasureService,
			CommercePriceEntryService commercePriceEntryService,
			CommercePriceListLocalService commercePriceListLocalService,
			CPInstance cpInstance, SkuUnitOfMeasure skuUnitOfMeasure,
			ServiceContext serviceContext)
		throws Exception {

		CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure =
			cpInstanceUnitOfMeasureService.addOrUpdateCPInstanceUnitOfMeasure(
				cpInstance.getCPInstanceId(),
				GetterUtil.get(skuUnitOfMeasure.getActive(), true),
				(BigDecimal)GetterUtil.getNumber(
					skuUnitOfMeasure.getIncrementalOrderQuantity(),
					BigDecimal.ONE),
				skuUnitOfMeasure.getKey(),
				LanguageUtils.getLocalizedMap(skuUnitOfMeasure.getName()),
				GetterUtil.getInteger(skuUnitOfMeasure.getPrecision()),
				skuUnitOfMeasure.getPricingQuantity(),
				GetterUtil.get(
					skuUnitOfMeasure.getPrimary(),
					_isDefaultPrimary(
						cpInstanceUnitOfMeasureService,
						cpInstance.getCPInstanceId())),
				GetterUtil.getDouble(skuUnitOfMeasure.getPriority()),
				(BigDecimal)GetterUtil.getNumber(
					skuUnitOfMeasure.getRate(), BigDecimal.ONE),
				cpInstance.getSku());

		int count =
			cpInstanceUnitOfMeasureService.getCPInstanceUnitOfMeasuresCount(
				cpInstance.getCPInstanceId());

		if ((count > 1) && (skuUnitOfMeasure.getBasePrice() == null)) {
			throw new CommercePriceEntryPriceException();
		}

		if (skuUnitOfMeasure.getBasePrice() != null) {
			updateCommercePriceEntry(
				commercePriceEntryService, commercePriceListLocalService,
				cpInstance, cpInstanceUnitOfMeasure,
				skuUnitOfMeasure.getBasePrice(),
				CommercePriceListConstants.TYPE_PRICE_LIST, serviceContext);
		}

		if (skuUnitOfMeasure.getPromoPrice() != null) {
			updateCommercePriceEntry(
				commercePriceEntryService, commercePriceListLocalService,
				cpInstance, cpInstanceUnitOfMeasure,
				skuUnitOfMeasure.getPromoPrice(),
				CommercePriceListConstants.TYPE_PROMOTION, serviceContext);
		}

		return cpInstanceUnitOfMeasure;
	}

	public static void updateCommercePriceEntry(
			CommercePriceEntryService commercePriceEntryService,
			CommercePriceListLocalService commercePriceListLocalService,
			CPInstance cpInstance,
			CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure, BigDecimal price,
			String type, ServiceContext serviceContext)
		throws Exception {

		if (price == null) {
			return;
		}

		CommercePriceEntry commercePriceEntry =
			commercePriceEntryService.getInstanceBaseCommercePriceEntry(
				cpInstance.getCPInstanceUuid(), type,
				cpInstanceUnitOfMeasure.getKey());

		if (commercePriceEntry == null) {
			CommercePriceList commercePriceList =
				commercePriceListLocalService.
					getCatalogBaseCommercePriceListByType(
						cpInstance.getGroupId(), type);

			commercePriceEntryService.addCommercePriceEntry(
				null, cpInstance.getCPInstanceId(),
				commercePriceList.getCommercePriceListId(), price, false, null,
				cpInstanceUnitOfMeasure.getKey(), serviceContext);
		}
		else {
			commercePriceEntryService.updatePricingInfo(
				commercePriceEntry.getCommercePriceEntryId(),
				commercePriceEntry.isBulkPricing(), price,
				commercePriceEntry.isPriceOnApplication(),
				commercePriceEntry.getPromoPrice(),
				cpInstanceUnitOfMeasure.getKey(), serviceContext);
		}
	}

	private static boolean _isDefaultPrimary(
			CPInstanceUnitOfMeasureService cpInstanceUnitOfMeasureService,
			long cpInstanceId)
		throws Exception {

		int count =
			cpInstanceUnitOfMeasureService.getCPInstanceUnitOfMeasuresCount(
				cpInstanceId);

		if (count > 0) {
			return false;
		}

		return true;
	}

}