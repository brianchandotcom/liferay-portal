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

package com.liferay.headless.commerce.admin.pricing.internal.dto.v2_0.converter;

import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.util.CommercePriceFormatter;
import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceEntryService;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.PriceEntry;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import java.math.BigDecimal;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = "dto.class.name=com.liferay.commerce.price.list.model.CommercePriceEntry",
	service = {DTOConverter.class, PriceEntryDTOConverter.class}
)
public class PriceEntryDTOConverter
	implements DTOConverter<CommercePriceEntry, PriceEntry> {

	@Override
	public String getContentType() {
		return PriceEntry.class.getSimpleName();
	}

	@Override
	public PriceEntry toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CommercePriceEntry commercePriceEntry =
			_commercePriceEntryService.getCommercePriceEntry(
				(Long)dtoConverterContext.getId());

		CommercePriceList commercePriceList =
			commercePriceEntry.getCommercePriceList();

		CommerceCurrency commerceCurrency =
			commercePriceList.getCommerceCurrency();

		CPInstance cpInstance = commercePriceEntry.getCPInstance();

		ExpandoBridge expandoBridge = commercePriceEntry.getExpandoBridge();

		BigDecimal priceEntryPrice = commercePriceEntry.getPrice();

		Locale locale = dtoConverterContext.getLocale();

		return new PriceEntry() {
			{
				actions = dtoConverterContext.getActions();
				bulkPricing = commercePriceEntry.isBulkPricing();
				customFields = expandoBridge.getAttributes();
				discountDiscovery = commercePriceEntry.isDiscountDiscovery();
				discountLevel1 = commercePriceEntry.getDiscountLevel1();
				discountLevel2 = commercePriceEntry.getDiscountLevel2();
				discountLevel3 = commercePriceEntry.getDiscountLevel3();
				discountLevel4 = commercePriceEntry.getDiscountLevel4();
				discountLevelsFormatted = _getDiscountLevelsFormatted(
					commercePriceEntry);
				displayDate = commercePriceEntry.getDisplayDate();
				expirationDate = commercePriceEntry.getExpirationDate();
				externalReferenceCode =
					commercePriceEntry.getExternalReferenceCode();
				hasTierPrice = commercePriceEntry.isHasTierPrice();
				price = priceEntryPrice.doubleValue();
				priceEntryId = commercePriceEntry.getCommercePriceEntryId();
				priceFormatted = _formatPrice(
					priceEntryPrice, commerceCurrency, locale);
				priceListId = commercePriceEntry.getCommercePriceListId();
				skuExternalReferenceCode =
					cpInstance.getExternalReferenceCode();
				skuId = cpInstance.getCPInstanceId();
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

	private String _getDiscountLevelFormatted(BigDecimal discountLevel) {
		if (discountLevel == null) {
			return StringPool.BLANK;
		}

		return String.valueOf(discountLevel.doubleValue());
	}

	private String _getDiscountLevelsFormatted(
		CommercePriceEntry commercePriceEntry) {

		if (commercePriceEntry.isDiscountDiscovery() ||
			((commercePriceEntry.getDiscountLevel1() == null) &&
			 (commercePriceEntry.getDiscountLevel2() == null) &&
			 (commercePriceEntry.getDiscountLevel3() == null) &&
			 (commercePriceEntry.getDiscountLevel4() == null))) {

			return StringPool.BLANK;
		}

		StringBuffer sb = new StringBuffer();

		sb.append(
			_getDiscountLevelFormatted(commercePriceEntry.getDiscountLevel1()));
		sb.append(" - ");
		sb.append(
			_getDiscountLevelFormatted(commercePriceEntry.getDiscountLevel2()));
		sb.append(" - ");
		sb.append(
			_getDiscountLevelFormatted(commercePriceEntry.getDiscountLevel3()));
		sb.append(" - ");
		sb.append(
			_getDiscountLevelFormatted(commercePriceEntry.getDiscountLevel4()));

		return sb.toString();
	}

	@Reference
	private CommercePriceEntryService _commercePriceEntryService;

	@Reference
	private CommercePriceFormatter _commercePriceFormatter;

}