/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.engine.rest.internal.strategy;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.Collection;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.ClassUtils;

/**
 * @author Rafael Praxedes
 */
public interface MapToDDMFormValuesConverterStrategy {

	public default LocalizedValue createLocalizedValue(
		Locale locale, Object value) {

		if (!(value instanceof Map)) {
			throw new IllegalArgumentException("Field's value must be a map");
		}

		LocalizedValue localizedValue = new LocalizedValue();

		Map<String, ?> localizedValues = (Map<String, ?>)value;

		if (locale == null) {
			for (Map.Entry<String, ?> entry : localizedValues.entrySet()) {
				if (entry.getValue() instanceof Collection) {
					JSONArray jsonArray = JSONFactoryUtil.createJSONArray(
						(Collection<?>)entry.getValue());

					localizedValue.addString(
						LocaleUtil.fromLanguageId(entry.getKey()),
						jsonArray.toString());
				}
				else if (entry.getValue() instanceof Map) {
					JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
						(Map)entry.getValue());

					localizedValue.addString(
						LocaleUtil.fromLanguageId(entry.getKey()),
						jsonObject.toString());
				}
				else if (entry.getValue() instanceof Object[]) {
					JSONArray jsonArray = JSONUtil.putAll(
						(Object[])entry.getValue());

					localizedValue.addString(
						LocaleUtil.fromLanguageId(entry.getKey()),
						jsonArray.toString());
				}
				else {
					localizedValue.addString(
						LocaleUtil.fromLanguageId(entry.getKey()),
						MapUtil.getString(
							(Map<String, ?>)value, entry.getKey()));
				}
			}
		}
		else {
			String languageId = LanguageUtil.getLanguageId(locale);

			Object object = localizedValues.get(languageId);

			if (object == null) {
				return localizedValue;
			}

			if (object instanceof Collection) {
				JSONArray jsonArray = JSONFactoryUtil.createJSONArray(
					(Collection<?>)object);

				localizedValue.addString(locale, jsonArray.toString());
			}
			else if (object instanceof Object[]) {
				JSONArray jsonArray = JSONUtil.putAll((Object[])object);

				localizedValue.addString(locale, jsonArray.toString());
			}
			else {
				localizedValue.addString(
					locale,
					MapUtil.getString((Map<String, ?>)value, languageId));
			}
		}

		return localizedValue;
	}

	public default Value createValue(
		DDMFormField ddmFormField, Locale locale, Object value) {

		if (value instanceof Object[]) {
			value = JSONUtil.putAll(
				(Object[])value
			).toString();
		}

		if (ddmFormField.isLocalizable()) {
			return createLocalizedValue(locale, value);
		}

		if (!(value instanceof String) &&
			(ClassUtils.wrapperToPrimitive(value.getClass()) == null)) {

			throw new IllegalArgumentException(
				"Field's value must be a primitive value");
		}

		return new UnlocalizedValue(GetterUtil.getString(value));
	}

	public void setDDMFormFieldValues(
		Map<String, Object> dataRecordValues, DDMForm ddmForm,
		DDMFormValues ddmFormValues, Locale locale);

}