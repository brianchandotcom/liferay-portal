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

import com.liferay.commerce.pricing.model.CommercePriceModifier;
import com.liferay.commerce.pricing.model.CommercePriceModifierRel;
import com.liferay.commerce.pricing.model.CommercePricingClass;
import com.liferay.commerce.pricing.service.CommercePriceModifierRelService;
import com.liferay.commerce.pricing.service.CommercePricingClassService;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.PriceModifierProductGroup;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = "dto.class.name=com.liferay.commerce.pricing.model.CommercePriceModifierRel-ProductGroup",
	service = {DTOConverter.class, PriceModifierProductGroupDTOConverter.class}
)
public class PriceModifierProductGroupDTOConverter
	implements DTOConverter
		<CommercePriceModifierRel, PriceModifierProductGroup> {

	@Override
	public String getContentType() {
		return PriceModifierProductGroup.class.getSimpleName();
	}

	@Override
	public PriceModifierProductGroup toDTO(
			DTOConverterContext dtoConverterContext)
		throws Exception {

		CommercePriceModifierRel commercePriceModifierRel =
			_commercePriceModifierRelService.getCommercePriceModifierRel(
				(Long)dtoConverterContext.getId());

		CommercePricingClass commercePricingClass =
			_commercePricingClassService.getCommercePricingClass(
				commercePriceModifierRel.getClassPK());

		CommercePriceModifier commercePriceModifier =
			commercePriceModifierRel.getCommercePriceModifier();

		return new PriceModifierProductGroup() {
			{
				actions = dtoConverterContext.getActions();
				priceModifierExternalReferenceCode =
					commercePriceModifier.getExternalReferenceCode();
				priceModifierId =
					commercePriceModifier.getCommercePriceModifierId();
				priceModifierProductGroupId =
					commercePriceModifierRel.getCommercePriceModifierRelId();
				productGroupExternalReferenceCode =
					commercePricingClass.getExternalReferenceCode();
				productGroupId =
					commercePricingClass.getCommercePricingClassId();
			}
		};
	}

	@Reference
	private CommercePriceModifierRelService _commercePriceModifierRelService;

	@Reference
	private CommercePricingClassService _commercePricingClassService;

}