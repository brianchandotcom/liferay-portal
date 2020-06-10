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

package com.liferay.headless.admin.content.internal.dto.v1_0.util;

import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldType;

/**
 * @author Cristina González
 */
public class ContentStructureUtil {

	public static String toDataType(DDMFormField ddmFormField) {
		String type = ddmFormField.getType();

		if (DDMFormFieldType.DOCUMENT_LIBRARY.equals(type)) {
			return "document";
		}
		else if (DDMFormFieldType.JOURNAL_ARTICLE.equals(type)) {
			return "structuredContent";
		}
		else if (DDMFormFieldType.LINK_TO_PAGE.equals(type)) {
			return "url";
		}
		else if (DDMFormFieldType.RADIO.equals(type)) {
			return "string";
		}

		return ddmFormField.getDataType();
	}

	public static String toInputControl(DDMFormField ddmFormField) {
		String type = ddmFormField.getType();

		if (DDMFormFieldType.CHECKBOX.equals(type) ||
			DDMFormFieldType.RADIO.equals(type) ||
			DDMFormFieldType.SELECT.equals(type) ||
			DDMFormFieldType.TEXT.equals(type) ||
			DDMFormFieldType.TEXT_AREA.equals(type)) {

			return type;
		}

		return null;
	}

}