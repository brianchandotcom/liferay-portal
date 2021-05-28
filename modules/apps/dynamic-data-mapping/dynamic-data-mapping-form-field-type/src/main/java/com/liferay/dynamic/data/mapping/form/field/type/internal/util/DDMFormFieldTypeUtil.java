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

package com.liferay.dynamic.data.mapping.form.field.type.internal.util;

import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Locale;

/**
 * @author Marcela Cunha
 */
public class DDMFormFieldTypeUtil {

	public static String getPredefinedValue(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		if (ddmFormFieldRenderingContext.isViewMode()) {
			return StringPool.BLANK;
		}

		return getPropertyValue(
			ddmFormField, ddmFormFieldRenderingContext.getLocale(),
			"predefinedValue");
	}

	public static String getPropertyValue(
		DDMFormField ddmFormField, Locale locale, String propertyName) {

		LocalizedValue value = (LocalizedValue)ddmFormField.getProperty(
			propertyName);

		if (value == null) {
			return StringPool.BLANK;
		}

		return GetterUtil.getString(value.getString(locale));
	}

	public static String getPropertyValue(
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext,
		String propertyName) {

		return GetterUtil.getString(
			ddmFormFieldRenderingContext.getProperty(propertyName));
	}

}