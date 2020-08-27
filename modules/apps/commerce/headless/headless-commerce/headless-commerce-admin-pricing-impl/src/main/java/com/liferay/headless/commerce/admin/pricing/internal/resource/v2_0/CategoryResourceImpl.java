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

package com.liferay.headless.commerce.admin.pricing.internal.resource.v2_0;

import com.liferay.headless.commerce.admin.pricing.dto.v2_0.Category;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.DiscountCategory;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.PriceModifierCategory;
import com.liferay.headless.commerce.admin.pricing.internal.dto.v2_0.converter.CategoryDTOConverter;
import com.liferay.headless.commerce.admin.pricing.resource.v2_0.CategoryResource;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.fields.NestedFieldId;

import javax.validation.constraints.NotNull;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Zoltán Takács
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v2_0/category.properties",
	scope = ServiceScope.PROTOTYPE, service = CategoryResource.class
)
public class CategoryResourceImpl extends BaseCategoryResourceImpl {

	@NestedField(parentClass = DiscountCategory.class, value = "category")
	@Override
	public Category getDiscountIdCategoryPage(
			@NestedFieldId(value = "categoryId") @NotNull Long id)
		throws Exception {

		return _categoryDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				id, contextAcceptLanguage.getPreferredLocale()));
	}

	@NestedField(parentClass = PriceModifierCategory.class, value = "category")
	@Override
	public Category getPriceModifierIdCategory(
			@NestedFieldId(value = "categoryId") @NotNull Long id)
		throws Exception {

		return _categoryDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				id, contextAcceptLanguage.getPreferredLocale()));
	}

	@Reference
	private CategoryDTOConverter _categoryDTOConverter;

}