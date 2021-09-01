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

package com.liferay.headless.commerce.admin.channel.internal.dto.v1_0.converter;

import com.liferay.commerce.product.model.CPTaxCategory;
import com.liferay.commerce.product.service.CPTaxCategoryService;
import com.liferay.headless.commerce.admin.channel.dto.v1_0.Channel;
import com.liferay.headless.commerce.admin.channel.dto.v1_0.TaxCategory;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Sbarra
 */
@Component(
	enabled = false,
	property = "dto.class.name=com.liferay.commerce.product.model.CPTaxCategory",
	service = {DTOConverter.class, TaxCategoryDTOConverter.class}
)
public class TaxCategoryDTOConverter
	implements DTOConverter<CPTaxCategory, TaxCategory> {

	@Override
	public String getContentType() {
		return Channel.class.getSimpleName();
	}

	@Override
	public TaxCategory toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CPTaxCategory cpTaxCategory = _cpTaxCategoryService.getCPTaxCategory(
			(Long)dtoConverterContext.getId());

		return new TaxCategory() {
			{
				description = LocalizedMapUtil.getLanguageIdMap(
					cpTaxCategory.getDescriptionMap());
				id = cpTaxCategory.getCPTaxCategoryId();
				name = LocalizedMapUtil.getLanguageIdMap(
					cpTaxCategory.getNameMap());
			}
		};
	}

	@Reference
	private CPTaxCategoryService _cpTaxCategoryService;

}