/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.catalog.internal.dto.v1_0.converter;

import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.price.list.constants.CommercePriceListConstants;
import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceEntryService;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CPInstanceUnitOfMeasure;
import com.liferay.commerce.product.service.CPInstanceService;
import com.liferay.commerce.product.service.CPInstanceUnitOfMeasureLocalService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.SkuUnitOfMeasure;
import com.liferay.headless.commerce.core.util.LanguageUtils;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Stefano Motta
 */
@Component(
	property = "dto.class.name=com.liferay.commerce.product.model.CPInstanceUnitOfMeasure",
	service = DTOConverter.class
)
public class SkuUnitOfMeasureDTOConverter
	implements DTOConverter<CPInstanceUnitOfMeasure, SkuUnitOfMeasure> {

	@Override
	public String getContentType() {
		return CPInstanceUnitOfMeasure.class.getSimpleName();
	}

	@Override
	public SkuUnitOfMeasure toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure =
			_cpInstanceUnitOfMeasureLocalService.getCPInstanceUnitOfMeasure(
				(Long)dtoConverterContext.getId());

		CPInstance cpInstance = _cpInstanceService.getCPInstance(
			cpInstanceUnitOfMeasure.getCPInstanceId());

		return new SkuUnitOfMeasure() {
			{
				actions = dtoConverterContext.getActions();
				active = cpInstanceUnitOfMeasure.isActive();
				basePrice = _getInstanceBaseCommercePriceEntryPrice(
					cpInstance.getCPInstanceUuid(),
					cpInstanceUnitOfMeasure.getKey(),
					CommercePriceListConstants.TYPE_PRICE_LIST);
				id = cpInstanceUnitOfMeasure.getCPInstanceUnitOfMeasureId();
				key = cpInstanceUnitOfMeasure.getKey();
				name = LanguageUtils.getLanguageIdMap(
					cpInstanceUnitOfMeasure.getNameMap());
				precision = cpInstanceUnitOfMeasure.getPrecision();
				primary = cpInstanceUnitOfMeasure.isPrimary();
				priority = cpInstanceUnitOfMeasure.getPriority();
				promoPrice = _getInstanceBaseCommercePriceEntryPrice(
					cpInstance.getCPInstanceUuid(),
					cpInstanceUnitOfMeasure.getKey(),
					CommercePriceListConstants.TYPE_PROMOTION);
				sku = cpInstanceUnitOfMeasure.getSku();
				skuId = cpInstanceUnitOfMeasure.getCPInstanceId();

				setIncrementalOrderQuantity(
					() -> {
						BigDecimal incrementalOrderQuantity =
							cpInstanceUnitOfMeasure.
								getIncrementalOrderQuantity();

						if (incrementalOrderQuantity == null) {
							return null;
						}

						return incrementalOrderQuantity.setScale(
							cpInstanceUnitOfMeasure.getPrecision(),
							RoundingMode.HALF_UP);
					});
				setRate(
					() -> {
						BigDecimal rate = cpInstanceUnitOfMeasure.getRate();

						if (rate == null) {
							return null;
						}

						return rate.setScale(
							cpInstanceUnitOfMeasure.getPrecision(),
							RoundingMode.HALF_UP);
					});
			}
		};
	}

	private BigDecimal _getInstanceBaseCommercePriceEntryPrice(
			String cpInstanceUuid, String key, String type)
		throws Exception {

		CommercePriceEntry commercePriceEntry =
			_commercePriceEntryService.getInstanceBaseCommercePriceEntry(
				cpInstanceUuid, type, key);

		if (commercePriceEntry == null) {
			return null;
		}

		CommercePriceList commercePriceList =
			commercePriceEntry.getCommercePriceList();

		CommerceCurrency commerceCurrency =
			commercePriceList.getCommerceCurrency();

		return commerceCurrency.round(commercePriceEntry.getPrice());
	}

	@Reference
	private CommercePriceEntryService _commercePriceEntryService;

	@Reference
	private CPInstanceService _cpInstanceService;

	@Reference
	private CPInstanceUnitOfMeasureLocalService
		_cpInstanceUnitOfMeasureLocalService;

}