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

import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.service.CPDefinitionOptionValueRelService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.ProductOptionValue;
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
	property = "dto.class.name=com.liferay.commerce.product.model.CPDefinitionOptionValueRel",
	service = {DTOConverter.class, ProductOptionValueDTOConverter.class}
)
public class ProductOptionValueDTOConverter
	implements DTOConverter<CPDefinitionOptionValueRel, ProductOptionValue> {

	@Override
	public String getContentType() {
		return ProductOptionValue.class.getSimpleName();
	}

	@Override
	public ProductOptionValue toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CPDefinitionOptionValueRel cpDefinitionOptionValueRel =
			_cpDefinitionOptionValueRelService.getCPDefinitionOptionValueRel(
				(Long)dtoConverterContext.getId());

		return new ProductOptionValue() {
			{
				id =
					cpDefinitionOptionValueRel.
						getCPDefinitionOptionValueRelId();
				key = cpDefinitionOptionValueRel.getKey();
				name = LanguageUtils.getLanguageIdMap(
					cpDefinitionOptionValueRel.getNameMap());
				priority = cpDefinitionOptionValueRel.getPriority();
			}
		};
	}

	@Reference
	private CPDefinitionOptionValueRelService
		_cpDefinitionOptionValueRelService;

}