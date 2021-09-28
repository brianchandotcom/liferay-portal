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
import com.liferay.commerce.price.list.service.CommercePriceEntryService;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.headless.commerce.admin.pricing.dto.v1_0.PriceEntry;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

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

		CPInstance cpInstance = commercePriceEntry.getCPInstance();

		ExpandoBridge expandoBridge = commercePriceEntry.getExpandoBridge();

		return new PriceEntry() {
			{
				customFields = expandoBridge.getAttributes();
				externalReferenceCode =
					commercePriceEntry.getExternalReferenceCode();
				hasTierPrice = commercePriceEntry.isHasTierPrice();
				id = commercePriceEntry.getCommercePriceEntryId();
				price = commercePriceEntry.getPrice();
				priceListId = commercePriceEntry.getCommercePriceListId();
				promoPrice = commercePriceEntry.getPromoPrice();
				sku = cpInstance.getSku();
				skuExternalReferenceCode =
					cpInstance.getExternalReferenceCode();
				skuId = cpInstance.getCPInstanceId();
			}
		};
	}

	@Reference
	private CommercePriceEntryService _commercePriceEntryService;

}