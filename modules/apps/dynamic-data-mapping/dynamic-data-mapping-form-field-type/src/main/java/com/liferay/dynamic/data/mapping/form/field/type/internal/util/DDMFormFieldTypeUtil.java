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
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Locale;
import java.util.stream.Stream;

/**
 * @author Marcela Cunha
 */
public class DDMFormFieldTypeUtil {

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

	public static String[] getPropertyValues(
		DDMFormField ddmFormField, Locale locale, String propertyName) {

		return Stream.of(
			(Object[])ddmFormField.getProperty(propertyName)
		).map(
			LocalizedValue.class::cast
		).map(
			localizedValue -> GetterUtil.getString(
				localizedValue.getString(locale))
		).toArray(
			String[]::new
		);
	}

	public static String getValue(String valueString) {
		try {
			JSONArray jsonArray = JSONFactoryUtil.createJSONArray(valueString);

			return GetterUtil.getString(jsonArray.get(0));
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsonException, jsonException);
			}
		}

		return valueString;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormFieldTypeUtil.class);

}