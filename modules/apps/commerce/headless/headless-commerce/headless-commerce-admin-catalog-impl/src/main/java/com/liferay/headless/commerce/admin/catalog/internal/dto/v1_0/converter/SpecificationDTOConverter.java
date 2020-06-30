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

import com.liferay.commerce.product.model.CPOptionCategory;
import com.liferay.commerce.product.model.CPSpecificationOption;
import com.liferay.commerce.product.service.CPSpecificationOptionService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.OptionCategory;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.Specification;
import com.liferay.headless.commerce.core.dto.v1_0.converter.DTOConverter;
import com.liferay.headless.commerce.core.dto.v1_0.converter.DTOConverterContext;
import com.liferay.headless.commerce.core.dto.v1_0.converter.DTOConverterRegistry;
import com.liferay.headless.commerce.core.dto.v1_0.converter.DefaultDTOConverterContext;
import com.liferay.headless.commerce.core.util.LanguageUtils;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "model.class.name=com.liferay.commerce.product.model.CPSpecificationOption",
	service = {DTOConverter.class, SpecificationDTOConverter.class}
)
public class SpecificationDTOConverter implements DTOConverter {

	@Override
	public String getContentType() {
		return Specification.class.getSimpleName();
	}

	public Specification toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CPSpecificationOption cpSpecificationOption =
			_cpSpecificationOptionService.getCPSpecificationOption(
				dtoConverterContext.getResourcePrimKey());

		CPOptionCategory cpOptionCategory =
			cpSpecificationOption.getCPOptionCategory();

		DTOConverter optionCategoryDTOConverter =
			_dtoConverterRegistry.getDTOConverter(
				CPOptionCategory.class.getName());

		Specification specification = new Specification() {
			{
				description = LanguageUtils.getLanguageIdMap(
					cpSpecificationOption.getDescriptionMap());
				facetable = cpSpecificationOption.isFacetable();
				id = cpSpecificationOption.getCPSpecificationOptionId();
				key = cpSpecificationOption.getKey();
				title = LanguageUtils.getLanguageIdMap(
					cpSpecificationOption.getTitleMap());
			}
		};

		if (cpOptionCategory != null) {
			OptionCategory optionCategory =
				(OptionCategory)optionCategoryDTOConverter.toDTO(
					new DefaultDTOConverterContext(
						dtoConverterContext.getLocale(),
						cpOptionCategory.getCPOptionCategoryId()));

			specification.setOptionCategory(optionCategory);
		}

		return specification;
	}

	@Reference
	private CPSpecificationOptionService _cpSpecificationOptionService;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

}