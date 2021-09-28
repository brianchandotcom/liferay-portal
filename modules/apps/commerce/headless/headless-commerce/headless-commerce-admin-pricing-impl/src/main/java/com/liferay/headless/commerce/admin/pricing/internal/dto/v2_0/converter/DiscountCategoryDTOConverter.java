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
import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.model.CommerceDiscountRel;
import com.liferay.commerce.discount.service.CommerceDiscountRelService;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.DiscountCategory;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = "dto.class.name=com.liferay.commerce.discount.model.CommerceDiscountRel-Category",
	service = {DiscountCategoryDTOConverter.class, DTOConverter.class}
)
public class DiscountCategoryDTOConverter
	implements DTOConverter<CommerceDiscountRel, DiscountCategory> {

	@Override
	public String getContentType() {
		return DiscountCategory.class.getSimpleName();
	}

	@Override
	public DiscountCategory toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CommerceDiscountRel commerceDiscountRel =
			_commerceDiscountRelService.getCommerceDiscountRel(
				(Long)dtoConverterContext.getId());

		AssetCategory assetCategory = _assetCategoryService.getCategory(
			commerceDiscountRel.getClassPK());

		CommerceDiscount commerceDiscount =
			commerceDiscountRel.getCommerceDiscount();

		return new DiscountCategory() {
			{
				actions = dtoConverterContext.getActions();
				categoryExternalReferenceCode =
					assetCategory.getExternalReferenceCode();
				categoryId = assetCategory.getCategoryId();
				discountCategoryId =
					commerceDiscountRel.getCommerceDiscountRelId();
				discountExternalReferenceCode =
					commerceDiscount.getExternalReferenceCode();
				discountId = commerceDiscount.getCommerceDiscountId();
			}
		};
	}

	@Reference
	private AssetCategoryService _assetCategoryService;

	@Reference
	private CommerceDiscountRelService _commerceDiscountRelService;

}