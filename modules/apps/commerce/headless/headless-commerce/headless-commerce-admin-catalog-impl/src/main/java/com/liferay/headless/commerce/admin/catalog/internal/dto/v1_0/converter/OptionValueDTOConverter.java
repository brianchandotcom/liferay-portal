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

import com.liferay.commerce.product.model.CPOptionValue;
import com.liferay.commerce.product.service.CPOptionValueService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.OptionValue;
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
	property = "model.class.name=com.liferay.commerce.product.model.CPOptionValue",
	service = {DTOConverter.class, OptionValueDTOConverter.class}
)
public class OptionValueDTOConverter
	implements DTOConverter<CPOptionValue, OptionValue> {

	@Override
	public String getContentType() {
		return OptionValue.class.getSimpleName();
	}

	@Override
	public OptionValue toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CPOptionValue cpOptionValue = _cpOptionValueService.getCPOptionValue(
			(Long)dtoConverterContext.getId());

		return new OptionValue() {
			{
				actions = dtoConverterContext.getActions();
				externalReferenceCode =
					cpOptionValue.getExternalReferenceCode();
				id = cpOptionValue.getCPOptionValueId();
				key = cpOptionValue.getKey();
				name = LanguageUtils.getLanguageIdMap(
					cpOptionValue.getNameMap());
				priority = cpOptionValue.getPriority();
			}
		};
	}

	@Reference
	private CPOptionValueService _cpOptionValueService;

}