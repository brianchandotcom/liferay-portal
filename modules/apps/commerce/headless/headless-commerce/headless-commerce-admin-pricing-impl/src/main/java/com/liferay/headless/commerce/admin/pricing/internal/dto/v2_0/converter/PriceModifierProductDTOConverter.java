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
import com.liferay.commerce.pricing.service.CommercePriceModifierRelService;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.PriceModifierProduct;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = "dto.class.name=com.liferay.commerce.pricing.model.CommercePriceModifierRel-Product",
	service = {DTOConverter.class, PriceModifierProductDTOConverter.class}
)
public class PriceModifierProductDTOConverter
	implements DTOConverter<CommercePriceModifierRel, PriceModifierProduct> {

	@Override
	public String getContentType() {
		return PriceModifierProduct.class.getSimpleName();
	}

	@Override
	public PriceModifierProduct toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CommercePriceModifierRel commercePriceModifierRel =
			_commercePriceModifierRelService.getCommercePriceModifierRel(
				(Long)dtoConverterContext.getId());

		CPDefinition cpDefinition = _cpDefinitionService.getCPDefinition(
			commercePriceModifierRel.getClassPK());

		CProduct cProduct = cpDefinition.getCProduct();

		CommercePriceModifier commercePriceModifier =
			commercePriceModifierRel.getCommercePriceModifier();

		return new PriceModifierProduct() {
			{
				actions = dtoConverterContext.getActions();
				priceModifierExternalReferenceCode =
					commercePriceModifier.getExternalReferenceCode();
				priceModifierId =
					commercePriceModifier.getCommercePriceModifierId();
				priceModifierProductId =
					commercePriceModifierRel.getCommercePriceModifierRelId();
				productExternalReferenceCode =
					cProduct.getExternalReferenceCode();
				productId = cProduct.getCProductId();
			}
		};
	}

	@Reference
	private CommercePriceModifierRelService _commercePriceModifierRelService;

	@Reference
	private CPDefinitionService _cpDefinitionService;

}