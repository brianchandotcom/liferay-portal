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
import com.liferay.commerce.product.model.CPTaxCategory;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.ProductTaxConfiguration;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, property = "dto.class.name=ProductTaxConfiguration",
	service = {DTOConverter.class, ProductTaxConfigurationDTOConverter.class}
)
public class ProductTaxConfigurationDTOConverter
	implements DTOConverter<CPDefinition, ProductTaxConfiguration> {

	@Override
	public String getContentType() {
		return ProductTaxConfiguration.class.getSimpleName();
	}

	@Override
	public ProductTaxConfiguration toDTO(
			DTOConverterContext dtoConverterContext)
		throws Exception {

		CPDefinition cpDefinition = _cpDefinitionService.getCPDefinition(
			(Long)dtoConverterContext.getId());

		CPTaxCategory cpTaxCategory = cpDefinition.getCPTaxCategory();

		return new ProductTaxConfiguration() {
			{
				id = cpDefinition.getCPTaxCategoryId();
				taxable = !cpDefinition.isTaxExempt();
				taxCategory = _getTaxCategory(
					cpTaxCategory, dtoConverterContext.getLocale());
			}
		};
	}

	private String _getTaxCategory(CPTaxCategory cpTaxCategory, Locale locale) {
		if (cpTaxCategory == null) {
			return null;
		}

		return cpTaxCategory.getName(locale);
	}

	@Reference
	private CPDefinitionService _cpDefinitionService;

}