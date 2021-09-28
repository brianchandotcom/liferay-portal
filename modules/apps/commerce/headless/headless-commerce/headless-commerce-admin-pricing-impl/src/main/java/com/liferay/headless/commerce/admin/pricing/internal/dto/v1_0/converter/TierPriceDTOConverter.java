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

package com.liferay.headless.commerce.admin.pricing.internal.dto.v1_0.converter;

import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.model.CommerceTierPriceEntry;
import com.liferay.commerce.price.list.service.CommerceTierPriceEntryService;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.headless.commerce.admin.pricing.dto.v1_0.TierPrice;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = "dto.class.name=com.liferay.commerce.price.list.model.CommerceTierPriceEntry",
	service = {DTOConverter.class, TierPriceDTOConverter.class}
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

		ExpandoBridge expandoBridge = commerceTierPriceEntry.getExpandoBridge();

		return new TierPrice() {
			{
				customFields = expandoBridge.getAttributes();
				externalReferenceCode =
					commerceTierPriceEntry.getExternalReferenceCode();
				id = commerceTierPriceEntry.getCommerceTierPriceEntryId();
				minimumQuantity = commerceTierPriceEntry.getMinQuantity();
				price = commerceTierPriceEntry.getPrice();
				priceEntryExternalReferenceCode =
					commercePriceEntry.getExternalReferenceCode();
				priceEntryId = commercePriceEntry.getCommercePriceEntryId();
				promoPrice = commerceTierPriceEntry.getPromoPrice();
			}
		};
	}

	@Reference
	private CommerceTierPriceEntryService _commerceTierPriceEntryService;

}