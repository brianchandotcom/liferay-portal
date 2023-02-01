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

package com.liferay.apibuilder.rest.internal.dto.converter;

import com.liferay.apibuilder.model.APIBuilderEntry;
import com.liferay.apibuilder.operation.Response;
import com.liferay.apibuilder.rest.dto.APIBuilderElement;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.type.PrimaryKeyInfoFieldType;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Carlos Correa
 */
@Component(
	property = "dto.class.name=com.liferay.apibuilder.model.APIBuilderEntry",
	service = {APIBuilderElementDTOConverter.class, DTOConverter.class}
)
public class APIBuilderElementDTOConverter
	implements DTOConverter<APIBuilderEntry, APIBuilderElement> {

	@Override
	public String getContentType() {
		return APIBuilderEntry.class.getSimpleName();
	}

	@Override
	public APIBuilderElement toDTO(
		DTOConverterContext dtoConverterContext,
		APIBuilderEntry apiBuilderEntry) {

		APIBuilderElement apiBuilderElement = new APIBuilderElement();

		Response response = (Response)dtoConverterContext.getAttribute(
			"response");

		Map<String, InfoField> infoFields = response.getInfoFields();

		for (Map.Entry<String, InfoField> entry : infoFields.entrySet()) {
			apiBuilderElement.put(
				entry.getKey(), _getValue(apiBuilderEntry, entry.getValue()));
		}

		apiBuilderElement.setName(apiBuilderEntry.getName());

		return apiBuilderElement;
	}

	private Object _getValue(
		APIBuilderEntry apiBuilderEntry, InfoField infoField) {

		if (infoField.getInfoFieldType() instanceof PrimaryKeyInfoFieldType) {
			return apiBuilderEntry.getPrimaryKey();
		}

		Map<String, Object> content =
			(Map<String, Object>)apiBuilderEntry.getContent();

		return content.get(infoField.getName());
	}

}