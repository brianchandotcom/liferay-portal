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

import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.service.CPOptionService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.Option;
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
	property = "model.class.name=com.liferay.commerce.product.model.CPOption",
	service = {DTOConverter.class, OptionDTOConverter.class}
)
public class OptionDTOConverter implements DTOConverter<CPOption, Option> {

	@Override
	public String getContentType() {
		return Option.class.getSimpleName();
	}

	@Override
	public Option toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CPOption cpOption = _cpOptionService.getCPOption(
			(Long)dtoConverterContext.getId());

		return new Option() {
			{
				actions = dtoConverterContext.getActions();
				description = LanguageUtils.getLanguageIdMap(
					cpOption.getDescriptionMap());
				externalReferenceCode = cpOption.getExternalReferenceCode();
				facetable = cpOption.isFacetable();
				fieldType = Option.FieldType.create(
					cpOption.getDDMFormFieldTypeName());
				id = cpOption.getCPOptionId();
				key = cpOption.getKey();
				name = LanguageUtils.getLanguageIdMap(cpOption.getNameMap());
				required = cpOption.isRequired();
				skuContributor = cpOption.isSkuContributor();
			}
		};
	}

	@Reference
	private CPOptionService _cpOptionService;

}