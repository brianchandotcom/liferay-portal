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

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.service.AssetCategoryService;
import com.liferay.commerce.pricing.model.CommercePriceModifier;
import com.liferay.commerce.pricing.model.CommercePriceModifierRel;
import com.liferay.commerce.pricing.service.CommercePriceModifierRelService;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.PriceModifierCategory;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = "dto.class.name=com.liferay.commerce.pricing.model.CommercePriceModifierRel-Category",
	service = {DTOConverter.class, PriceModifierCategoryDTOConverter.class}
)
public class PriceModifierCategoryDTOConverter
	implements DTOConverter<CommercePriceModifierRel, PriceModifierCategory> {

	@Override
	public String getContentType() {
		return PriceModifierCategory.class.getSimpleName();
	}

	@Override
	public PriceModifierCategory toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CommercePriceModifierRel commercePriceModifierRel =
			_commercePriceModifierRelService.getCommercePriceModifierRel(
				(Long)dtoConverterContext.getId());

		AssetCategory assetCategory = _assetCategoryService.getCategory(
			commercePriceModifierRel.getClassPK());

		CommercePriceModifier commercePriceModifier =
			commercePriceModifierRel.getCommercePriceModifier();

		return new PriceModifierCategory() {
			{
				actions = dtoConverterContext.getActions();
				categoryExternalReferenceCode =
					assetCategory.getExternalReferenceCode();
				categoryId = assetCategory.getCategoryId();
				priceModifierCategoryId =
					commercePriceModifierRel.getCommercePriceModifierRelId();
				priceModifierExternalReferenceCode =
					commercePriceModifier.getExternalReferenceCode();
				priceModifierId =
					commercePriceModifier.getCommercePriceModifierId();
			}
		};
	}

	@Reference
	private AssetCategoryService _assetCategoryService;

	@Reference
	private CommercePriceModifierRelService _commercePriceModifierRelService;

}