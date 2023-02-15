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

package com.liferay.headless.builder.internal.dto.converter;

import com.liferay.headless.builder.model.HeadlessBuilderEntry;
import com.liferay.headless.builder.operation.Response;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.type.PrimaryKeyInfoFieldType;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.message.RootElementProvider;

import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Carlos Correa
 */
@Component(
	property = "dto.class.name=com.liferay.headless.builder.model.HeadlessBuilderEntry",
	service = {DTOConverter.class, HeadlessBuilderElementDTOConverter.class}
)
public class HeadlessBuilderElementDTOConverter
	implements DTOConverter
		<HeadlessBuilderEntry,
		 HeadlessBuilderElementDTOConverter.HeadlessBuilderElement> {

	@Override
	public String getContentType() {
		return HeadlessBuilderEntry.class.getSimpleName();
	}

	@Override
	public HeadlessBuilderElement toDTO(
		DTOConverterContext dtoConverterContext,
		HeadlessBuilderEntry headlessBuilderEntry) {

		HeadlessBuilderElement headlessBuilderElement =
			new HeadlessBuilderElement();

		Response response = (Response)dtoConverterContext.getAttribute(
			"response");

		Map<String, InfoField> infoFields = response.getInfoFields();

		for (Map.Entry<String, InfoField> entry : infoFields.entrySet()) {
			headlessBuilderElement.put(
				entry.getKey(),
				_getValue(headlessBuilderEntry, entry.getValue()));
		}

		headlessBuilderElement.setName(headlessBuilderEntry.getName());

		return headlessBuilderElement;
	}

	public static class HeadlessBuilderElement
		extends HashMap<String, Object> implements RootElementProvider {

		public String getName() {
			return _name;
		}

		@Override
		public String getRootElement() {
			return _name;
		}

		public void setName(String name) {
			_name = name;
		}

		private String _name;

	}

	private Object _getValue(
		HeadlessBuilderEntry headlessBuilderEntry, InfoField infoField) {

		if (infoField.getInfoFieldType() instanceof PrimaryKeyInfoFieldType) {
			return headlessBuilderEntry.getPrimaryKey();
		}

		Map<String, Object> content =
			(Map<String, Object>)headlessBuilderEntry.getContent();

		return content.get(infoField.getName());
	}

}