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

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPInstanceService;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.DiscountProduct;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.PriceEntry;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.PriceModifierProduct;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.Product;
import com.liferay.headless.commerce.admin.pricing.internal.dto.v2_0.converter.ProductDTOConverter;
import com.liferay.headless.commerce.admin.pricing.resource.v2_0.ProductResource;
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
	properties = "OSGI-INF/liferay/rest/v2_0/product.properties",
	scope = ServiceScope.PROTOTYPE, service = ProductResource.class
)
public class ProductResourceImpl extends BaseProductResourceImpl {

	@NestedField(parentClass = DiscountProduct.class, value = "product")
	@Override
	public Product getDiscountIdProductPage(
			@NestedFieldId(value = "productId") @NotNull Long id)
		throws Exception {

		return _productDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				id, contextAcceptLanguage.getPreferredLocale()));
	}

	@NestedField(parentClass = PriceEntry.class, value = "product")
	@Override
	public Product getPriceEntryIdProduct(
			@NestedFieldId(value = "skuId") @NotNull Long id)
		throws Exception {

		CPInstance cpInstance = _cpInstanceService.getCPInstance(id);

		CPDefinition cpDefinition = cpInstance.getCPDefinition();

		return _productDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				cpDefinition.getCProductId(),
				contextAcceptLanguage.getPreferredLocale()));
	}

	@NestedField(parentClass = PriceModifierProduct.class, value = "product")
	@Override
	public Product getPriceModifierIdProduct(
			@NestedFieldId(value = "productId") @NotNull Long id)
		throws Exception {

		return _productDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				id, contextAcceptLanguage.getPreferredLocale()));
	}

	@Reference
	private CPInstanceService _cpInstanceService;

	@Reference
	private ProductDTOConverter _productDTOConverter;

}