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

import com.liferay.headless.commerce.admin.pricing.dto.v2_0.DiscountProductGroup;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.PriceModifierProductGroup;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.ProductGroup;
import com.liferay.headless.commerce.admin.pricing.internal.dto.v2_0.converter.ProductGroupDTOConverter;
import com.liferay.headless.commerce.admin.pricing.resource.v2_0.ProductGroupResource;
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
	properties = "OSGI-INF/liferay/rest/v2_0/product-group.properties",
	scope = ServiceScope.PROTOTYPE, service = ProductGroupResource.class
)
public class ProductGroupResourceImpl extends BaseProductGroupResourceImpl {

	@NestedField(
		parentClass = DiscountProductGroup.class, value = "productGroup"
	)
	@Override
	public ProductGroup getDiscountIdProductGroupPage(
			@NestedFieldId(value = "productGroupId") @NotNull Long id)
		throws Exception {

		return _productGroupDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				id, contextAcceptLanguage.getPreferredLocale()));
	}

	@NestedField(
		parentClass = PriceModifierProductGroup.class, value = "productGroup"
	)
	@Override
	public ProductGroup getPriceModifierIdProductGroup(
			@NestedFieldId(value = "productGroupId") @NotNull Long id)
		throws Exception {

		return _productGroupDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				id, contextAcceptLanguage.getPreferredLocale()));
	}

	@Reference
	private ProductGroupDTOConverter _productGroupDTOConverter;

}