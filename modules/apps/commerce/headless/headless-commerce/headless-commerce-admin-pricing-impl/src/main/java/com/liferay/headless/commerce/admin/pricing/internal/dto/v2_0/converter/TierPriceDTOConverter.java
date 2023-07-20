/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.pricing.internal.dto.v2_0.converter;

import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.util.CommercePriceFormatter;
import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.model.CommerceTierPriceEntry;
import com.liferay.commerce.price.list.service.CommerceTierPriceEntryService;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CPInstanceUnitOfMeasure;
import com.liferay.commerce.product.service.CPInstanceUnitOfMeasureLocalService;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.TierPrice;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import java.math.BigDecimal;
import java.math.RoundingMode;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "dto.class.name=com.liferay.commerce.price.list.model.CommerceTierPriceEntry",
	service = DTOConverter.class
)
public class TierPriceDTOConverter
	implements DTOConverter<CommerceTierPriceEntry, TierPrice> {

	@Override
	public String getContentType() {
		return TierPrice.class.getSimpleName();
	}

	@Override
	public TierPrice toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CommerceTierPriceEntry commerceTierPriceEntry =
			_commerceTierPriceEntryService.getCommerceTierPriceEntry(
				(Long)dtoConverterContext.getId());

		CommercePriceEntry commercePriceEntry =
			commerceTierPriceEntry.getCommercePriceEntry();

		CommercePriceList commercePriceList =
			commercePriceEntry.getCommercePriceList();

		CommerceCurrency commerceCurrency =
			commercePriceList.getCommerceCurrency();

		ExpandoBridge expandoBridge = commerceTierPriceEntry.getExpandoBridge();

		BigDecimal tierPriceEntryPrice = commerceTierPriceEntry.getPrice();

		Locale locale = dtoConverterContext.getLocale();

		return new TierPrice() {
			{
				actions = dtoConverterContext.getActions();
				customFields = expandoBridge.getAttributes();
				discountDiscovery =
					commerceTierPriceEntry.isDiscountDiscovery();
				discountLevel1 = commerceTierPriceEntry.getDiscountLevel1();
				discountLevel2 = commerceTierPriceEntry.getDiscountLevel2();
				discountLevel3 = commerceTierPriceEntry.getDiscountLevel3();
				discountLevel4 = commerceTierPriceEntry.getDiscountLevel4();
				displayDate = commerceTierPriceEntry.getDisplayDate();
				expirationDate = commerceTierPriceEntry.getExpirationDate();
				externalReferenceCode =
					commerceTierPriceEntry.getExternalReferenceCode();
				id = commerceTierPriceEntry.getCommerceTierPriceEntryId();

				price = tierPriceEntryPrice.doubleValue();
				priceEntryExternalReferenceCode =
					commercePriceEntry.getExternalReferenceCode();
				priceEntryId = commercePriceEntry.getCommercePriceEntryId();
				priceFormatted = _formatPrice(
					tierPriceEntryPrice, commerceCurrency, locale);

				setMinimumQuantity(
					() -> {
						CPInstance cpInstance =
							commercePriceEntry.getCPInstance();

						CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure =
							_cpInstanceUnitOfMeasureLocalService.
								fetchCPInstanceUnitOfMeasure(
									cpInstance.getCPInstanceId(),
									commercePriceEntry.getUnitOfMeasureKey());

						if (cpInstanceUnitOfMeasure != null) {
							BigDecimal tierPriceEntryMinQuantity =
								commerceTierPriceEntry.getMinQuantity();

							return tierPriceEntryMinQuantity.setScale(
								cpInstanceUnitOfMeasure.getPrecision(),
								RoundingMode.HALF_UP);
						}

						return commerceTierPriceEntry.getMinQuantity();
					});
			}
		};
	}

	private String _formatPrice(
			BigDecimal price, CommerceCurrency commerceCurrency, Locale locale)
		throws Exception {

		if (price == null) {
			price = BigDecimal.ZERO;
		}

		return _commercePriceFormatter.format(commerceCurrency, price, locale);
	}

	@Reference
	private CommercePriceFormatter _commercePriceFormatter;

	@Reference
	private CommerceTierPriceEntryService _commerceTierPriceEntryService;

	@Reference
	private CPInstanceUnitOfMeasureLocalService
		_cpInstanceUnitOfMeasureLocalService;

}