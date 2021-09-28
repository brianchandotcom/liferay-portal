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
import com.liferay.commerce.product.service.CPOptionCategoryService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.OptionCategory;
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
	property = "dto.class.name=com.liferay.commerce.product.model.CPOptionCategory",
	service = {DTOConverter.class, OptionCategoryDTOConverter.class}
)
public class OptionCategoryDTOConverter
	implements DTOConverter<CPOptionCategory, OptionCategory> {

	@Override
	public String getContentType() {
		return OptionCategory.class.getSimpleName();
	}

	@Override
	public OptionCategory toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CPOptionCategory cpOptionCategory =
			_cpOptionCategoryService.getCPOptionCategory(
				(Long)dtoConverterContext.getId());

		return new OptionCategory() {
			{
				description = LanguageUtils.getLanguageIdMap(
					cpOptionCategory.getDescriptionMap());
				id = cpOptionCategory.getCPOptionCategoryId();
				key = cpOptionCategory.getKey();
				priority = cpOptionCategory.getPriority();
				title = LanguageUtils.getLanguageIdMap(
					cpOptionCategory.getTitleMap());
			}
		};
	}

	@Reference
	private CPOptionCategoryService _cpOptionCategoryService;

}