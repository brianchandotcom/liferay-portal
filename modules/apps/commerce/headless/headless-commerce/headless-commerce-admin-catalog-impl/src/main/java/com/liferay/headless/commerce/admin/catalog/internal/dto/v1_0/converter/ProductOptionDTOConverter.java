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

import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.service.CPDefinitionOptionRelService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.ProductOption;
import com.liferay.headless.commerce.core.util.LanguageUtils;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = "dto.class.name=com.liferay.commerce.product.model.CPDefinitionOptionRel",
	service = {DTOConverter.class, ProductOptionDTOConverter.class}
)
public class ProductOptionDTOConverter
	implements DTOConverter<CPDefinitionOptionRel, ProductOption> {

	@Override
	public String getContentType() {
		return ProductOption.class.getSimpleName();
	}

	@Override
	public ProductOption toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CPDefinitionOptionRel cpDefinitionOptionRel =
			_cpDefinitionOptionRelService.getCPDefinitionOptionRel(
				(Long)dtoConverterContext.getId());

		CPOption cpOption = cpDefinitionOptionRel.getCPOption();

		return new ProductOption() {
			{
				description = LanguageUtils.getLanguageIdMap(
					cpDefinitionOptionRel.getDescriptionMap());
				facetable = cpDefinitionOptionRel.isFacetable();
				fieldType = cpDefinitionOptionRel.getDDMFormFieldTypeName();
				id = cpDefinitionOptionRel.getCPDefinitionOptionRelId();
				key = cpOption.getKey();
				name = LanguageUtils.getLanguageIdMap(
					cpDefinitionOptionRel.getNameMap());
				optionId = cpOption.getCPOptionId();
				required = cpDefinitionOptionRel.isRequired();
				skuContributor = cpDefinitionOptionRel.isSkuContributor();
			}
		};
	}

	@Reference
	private CPDefinitionOptionRelService _cpDefinitionOptionRelService;

}