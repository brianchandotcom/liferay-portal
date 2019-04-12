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

package com.liferay.data.engine.rest.internal.field.type.v1_0.util;

import com.liferay.data.engine.rest.dto.v1_0.DataDefinitionField;
import com.liferay.data.engine.rest.internal.field.type.v1_0.CaptchaFieldType;
import com.liferay.data.engine.rest.internal.field.type.v1_0.CheckboxFieldType;
import com.liferay.data.engine.rest.internal.field.type.v1_0.CheckboxMultipleFieldType;
import com.liferay.data.engine.rest.internal.field.type.v1_0.DateFieldType;
import com.liferay.data.engine.rest.internal.field.type.v1_0.DocumentLibraryFieldType;
import com.liferay.data.engine.rest.internal.field.type.v1_0.EditorFieldType;
import com.liferay.portal.template.soy.data.SoyDataFactory;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

/**
 * @author Marcela Cunha
 */
public class FieldTypeUtil {

	public static void includeFieldTypeContext(
		DataDefinitionField dataDefinitionField, String fieldType,
		Map<String, Object> fieldTypeContext,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, boolean readOnly,
		SoyDataFactory soyDataFactory) {

		if (StringUtils.equals(fieldType, "captcha")) {
			CaptchaFieldType.includeContext(
				fieldTypeContext, dataDefinitionField, httpServletRequest,
				httpServletResponse, readOnly, soyDataFactory);
		}
		else if (StringUtils.equals(fieldType, "checkbox")) {
			CheckboxFieldType.includeContext(
				fieldTypeContext, dataDefinitionField, httpServletRequest,
				httpServletResponse, readOnly);
		}
		else if (StringUtils.equals(fieldType, "checkbox_multiple")) {
			CheckboxMultipleFieldType.includeContext(
				fieldTypeContext, dataDefinitionField, httpServletRequest,
				httpServletResponse, readOnly);
		}
		else if (StringUtils.equals(fieldType, "date")) {
			DateFieldType.includeContext(
				fieldTypeContext, dataDefinitionField, httpServletRequest,
				httpServletResponse, readOnly);
		}
		else if (StringUtils.equals(fieldType, "document_library")) {
			DocumentLibraryFieldType.includeContext(
				fieldTypeContext, dataDefinitionField, httpServletRequest,
				httpServletResponse, readOnly);
		}
		else if (StringUtils.equals(fieldType, "editor")) {
			EditorFieldType.includeContext(
				fieldTypeContext, dataDefinitionField, httpServletRequest,
				httpServletResponse, readOnly);
		}
	}

}