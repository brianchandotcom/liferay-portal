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

package com.liferay.headless.commerce.admin.catalog.internal.dto.v1_0.converter;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.ProductShippingConfiguration;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import java.math.BigDecimal;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, property = "dto.class.name=ProductShippingConfiguration",
	service = {
		DTOConverter.class, ProductShippingConfigurationDTOConverter.class
	}
)
public class ProductShippingConfigurationDTOConverter
	implements DTOConverter<CPDefinition, ProductShippingConfiguration> {

	@Override
	public String getContentType() {
		return ProductShippingConfiguration.class.getSimpleName();
	}

	@Override
	public ProductShippingConfiguration toDTO(
			DTOConverterContext dtoConverterContext)
		throws Exception {

		CPDefinition cpDefinition = _cpDefinitionService.getCPDefinition(
			(Long)dtoConverterContext.getId());

		return new ProductShippingConfiguration() {
			{
				depth = BigDecimal.valueOf(cpDefinition.getDepth());
				freeShipping = cpDefinition.isFreeShipping();
				height = BigDecimal.valueOf(cpDefinition.getHeight());
				shippable = cpDefinition.isShippable();
				shippingExtraPrice = BigDecimal.valueOf(
					cpDefinition.getShippingExtraPrice());
				shippingSeparately = cpDefinition.isShipSeparately();
				weight = BigDecimal.valueOf(cpDefinition.getWeight());
				width = BigDecimal.valueOf(cpDefinition.getWidth());
			}
		};
	}

	@Reference
	private CPDefinitionService _cpDefinitionService;

}