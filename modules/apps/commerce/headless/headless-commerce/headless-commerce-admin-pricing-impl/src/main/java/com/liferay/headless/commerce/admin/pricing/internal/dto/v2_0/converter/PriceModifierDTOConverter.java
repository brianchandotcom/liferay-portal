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

import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceListService;
import com.liferay.commerce.pricing.model.CommercePriceModifier;
import com.liferay.commerce.pricing.service.CommercePriceModifierService;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.PriceModifier;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = "dto.class.name=com.liferay.commerce.pricing.model.CommercePriceModifier",
	service = {DTOConverter.class, PriceModifierDTOConverter.class}
)
public class PriceModifierDTOConverter
	implements DTOConverter<CommercePriceModifier, PriceModifier> {

	@Override
	public String getContentType() {
		return PriceModifier.class.getSimpleName();
	}

	@Override
	public PriceModifier toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CommercePriceModifier commercePriceModifier =
			_commercePriceModifierService.getCommercePriceModifier(
				(Long)dtoConverterContext.getId());

		CommercePriceList commercePriceList =
			_commercePriceListService.getCommercePriceList(
				commercePriceModifier.getCommercePriceListId());

		return new PriceModifier() {
			{
				actions = dtoConverterContext.getActions();
				active = !commercePriceModifier.isInactive();
				displayDate = commercePriceModifier.getDisplayDate();
				expirationDate = commercePriceModifier.getExpirationDate();
				externalReferenceCode =
					commercePriceModifier.getExternalReferenceCode();
				id = commercePriceModifier.getCommercePriceModifierId();
				modifierAmount = commercePriceModifier.getModifierAmount();
				modifierType = commercePriceModifier.getModifierType();
				priceListExternalReferenceCode =
					commercePriceList.getExternalReferenceCode();
				priceListId = commercePriceList.getCommercePriceListId();
				priority = commercePriceModifier.getPriority();
				target = commercePriceModifier.getTarget();
				title = commercePriceModifier.getTitle();
			}
		};
	}

	@Reference
	private CommercePriceListService _commercePriceListService;

	@Reference
	private CommercePriceModifierService _commercePriceModifierService;

}