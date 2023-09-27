/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.field.type.internal.util;

import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.Locale;
import java.util.Map;

/**
 * @author Marcela Cunha
 */
public class DDMFormFieldTypeUtil {

	public static Object getChangedPropertyValue(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext,
		String propertyName) {

		Map<String, Object> changedProperties =
			(Map<String, Object>)ddmFormFieldRenderingContext.getProperty(
				"changedProperties");

		if (MapUtil.isNotEmpty(changedProperties)) {
			Object changedProperty = changedProperties.get(propertyName);

			if (changedProperty != null) {
				return changedProperty;
			}
		}

		return ddmFormField.getProperty(propertyName);
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

	public static String[] getPropertyValues(
		DDMFormField ddmFormField, Locale locale, String propertyName) {

		return TransformUtil.transform(
			(Object[])ddmFormField.getProperty(propertyName),
			object -> {
				LocalizedValue localizedValue = (LocalizedValue)object;

				return GetterUtil.getString(localizedValue.getString(locale));
			},
			String.class);
	}

	public static String getValue(String valueString) {
		try {
			JSONArray jsonArray = JSONFactoryUtil.createJSONArray(valueString);

			return GetterUtil.getString(jsonArray.get(0));
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsonException);
			}
		}

		return valueString;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormFieldTypeUtil.class);

}