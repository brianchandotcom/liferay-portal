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

package com.liferay.structure.apio.internal.util;

import com.liferay.dynamic.data.mapping.model.DDMFormFieldType;
import com.liferay.structure.apio.architect.util.StructureFieldConverter;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;

/**
 * Converts values used in {@code DDMFormField} to values to be exposed through
 * the web API.
 *
 * @author Rubén Pulido
 * @review
 */
@Component(immediate = true, service = StructureFieldConverter.class)
public class StructureFieldConverterImpl implements StructureFieldConverter {

	@Override
	public String getFieldDataType(String dataType) {
		return getFieldDataType(dataType, null);
	}

	@Override
	public String getFieldDataType(String dataType, String type) {
		if (Objects.equals(dataType, "document-library") ||
			(Objects.equals(dataType, "string") &&
			 Objects.equals(type, "document_library"))) {

			return "document";
		}
		else if (Objects.equals(dataType, "journal-article")) {
			return "structuredContent";
		}
		else if (Objects.equals(dataType, "link-to-page")) {
			return "url";
		}
		else if (Objects.equals(dataType, "radio") ||
				 Objects.equals(type, "paragraph")) {

			return "string";
		}
		else if (Objects.equals(dataType, "string") &&
				 Objects.equals(type, "date")) {

			return "date";
		}

		return dataType;
	}

	@Override
	public String getFieldInputControl(String type) {
		if (DDMFormFieldType.CHECKBOX.equals(type) ||
			DDMFormFieldType.CHECKBOX_MULTIPLE.equals(type) ||
			DDMFormFieldType.RADIO.equals(type) ||
			DDMFormFieldType.SELECT.equals(type) ||
			DDMFormFieldType.TEXT.equals(type) ||
			DDMFormFieldType.TEXT_AREA.equals(type) ||
			Objects.equals(type, "grid") || Objects.equals(type, "paragraph")) {

			return type;
		}

		return null;
	}

}