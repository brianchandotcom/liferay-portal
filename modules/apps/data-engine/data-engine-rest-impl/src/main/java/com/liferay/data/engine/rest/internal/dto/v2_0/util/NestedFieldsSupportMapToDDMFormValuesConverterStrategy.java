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

package com.liferay.data.engine.rest.internal.dto.v2_0.util;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.ClassUtils;

/**
 * @author Rafael Praxedes
 */
public class NestedFieldsSupportMapToDDMFormValuesConverterStrategy
	implements MapToDDMFormValuesConverterStrategy {

	public static NestedFieldsSupportMapToDDMFormValuesConverterStrategy
		getInstance() {

		return _nestedFieldsSupportMapToDDMFormValuesConverterStrategy;
	}

	public void setDDMFormFieldValues(
		Map<String, Object> dataRecordValues, DDMForm ddmForm,
		DDMFormValues ddmFormValues, Locale locale) {

		Map<String, DDMFormField> ddmFormFields = ddmForm.getDDMFormFieldsMap(
			true);

		for (Map.Entry<String, Object> entry : dataRecordValues.entrySet()) {
			String[] parts = StringUtil.split(entry.getKey(), "_INSTANCE_");

			ddmFormValues.addDDMFormFieldValue(
				createDDMFormFieldValue(
					ddmFormFields.get(parts[0]), ddmFormFields,
					(Map<String, Object>)entry.getValue(), parts[1], locale));
		}
	}

	protected DDMFormFieldValue createDDMFormFieldValue(
		DDMFormField ddmFormField, Map<String, DDMFormField> ddmFormFields,
		Map<String, Object> fieldInstanceValue, String instanceId,
		Locale locale) {

		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue() {
			{
				setName(ddmFormField.getName());
				setInstanceId(instanceId);
			}
		};

		if (!StringUtil.equals(ddmFormField.getType(), "fieldset")) {
			ddmFormFieldValue.setValue(
				createValue(
					ddmFormField, locale, fieldInstanceValue.get("value")));
		}

		if (ListUtil.isNotEmpty(ddmFormField.getNestedDDMFormFields())) {
			_setNestedDDMFormFieldValues(
				ddmFormFields, ddmFormFieldValue, locale,
				(Map<String, Object>)fieldInstanceValue.get("nestedValues"));
		}

		return ddmFormFieldValue;
	}

	protected LocalizedValue createLocalizedValue(Locale locale, Object value) {
		if (!(value instanceof Map)) {
			throw new IllegalArgumentException("Field's value must be a map");
		}

		LocalizedValue localizedValue = new LocalizedValue();

		Map<String, ?> localizedValues = (Map<String, ?>)value;

		if (locale == null) {
			for (Map.Entry<String, ?> entry : localizedValues.entrySet()) {
				if (entry.getValue() instanceof Object[]) {
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

			if (!localizedValues.containsKey(languageId)) {
				return localizedValue;
			}

			if (localizedValues.get(languageId) instanceof Object[]) {
				JSONArray jsonArray = JSONUtil.putAll(
					(Object[])localizedValues.get(languageId));

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

	protected Value createValue(
		DDMFormField ddmFormField, Locale locale, Object value) {

		if (value == null) {
			return ddmFormField.getPredefinedValue();
		}

		if (value instanceof Object[]) {
			JSONArray jsonArray = JSONUtil.putAll((Object[])value);

			value = jsonArray.toString();
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

	private NestedFieldsSupportMapToDDMFormValuesConverterStrategy() {
	}

	private void _setNestedDDMFormFieldValues(
		Map<String, DDMFormField> ddmFormFields,
		DDMFormFieldValue ddmFormFieldValue, Locale locale,
		Map<String, Object> nestedValues) {

		if (MapUtil.isEmpty(nestedValues)) {
			return;
		}

		for (Map.Entry<String, Object> entry : nestedValues.entrySet()) {
			String[] parts = StringUtil.split(entry.getKey(), "_INSTANCE_");

			ddmFormFieldValue.addNestedDDMFormFieldValue(
				createDDMFormFieldValue(
					ddmFormFields.get(parts[0]), ddmFormFields,
					(Map<String, Object>)entry.getValue(), parts[1], locale));
		}
	}

	private static NestedFieldsSupportMapToDDMFormValuesConverterStrategy
		_nestedFieldsSupportMapToDDMFormValuesConverterStrategy =
			new NestedFieldsSupportMapToDDMFormValuesConverterStrategy();

}