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

import com.liferay.data.engine.rest.dto.v2_0.DataDefinition;
import com.liferay.data.engine.rest.dto.v2_0.DataDefinitionField;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Jeyvison Nascimento
 */
public class DataRecordValuesUtil {

	public static Map<String, Object> toDataRecordValues(
			DataDefinition dataDefinition, String json)
		throws Exception {

		Map<String, Object> dataRecordValues = new HashMap<>();

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(json);

		for (DataDefinitionField dataDefinitionField :
				dataDefinition.getDataDefinitionFields()) {

			if (!jsonObject.has(dataDefinitionField.getName())) {
				continue;
			}

			dataRecordValues.put(
				dataDefinitionField.getName(),
				_toDataRecordValue(dataDefinitionField, jsonObject));
		}

		return dataRecordValues;
	}

	public static DDMFormValues toDDMFormValues(
		Map<String, Object> dataRecordValues, DDMForm ddmForm, Locale locale) {

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		ddmFormValues.addAvailableLocale(locale);

		Map<String, DDMFormField> ddmFormFields = ddmForm.getDDMFormFieldsMap(
			true);

		for (Map.Entry<String, DDMFormField> entry : ddmFormFields.entrySet()) {
			ddmFormValues.addDDMFormFieldValue(
				_createDDMFormFieldValue(
					dataRecordValues, entry.getValue(), locale));
		}

		ddmFormValues.setDefaultLocale(locale);

		return ddmFormValues;
	}

	public static String toJSON(
		DataDefinition dataDefinition, Map<String, ?> dataRecordValues) {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		Map<String, DataDefinitionField> dataDefinitionFields = Stream.of(
			dataDefinition.getDataDefinitionFields()
		).collect(
			Collectors.toMap(
				dataDefinitionField -> dataDefinitionField.getName(),
				Function.identity())
		);

		for (Map.Entry<String, DataDefinitionField> entry :
				dataDefinitionFields.entrySet()) {

			if (!dataRecordValues.containsKey(entry.getKey())) {
				continue;
			}

			DataDefinitionField dataDefinitionField = entry.getValue();

			if (dataDefinitionField.getRepeatable()) {
				jsonObject.put(
					entry.getKey(),
					JSONFactoryUtil.createJSONArray(
						(Object[])dataRecordValues.get(entry.getKey())));
			}
			else {
				jsonObject.put(
					entry.getKey(), dataRecordValues.get(entry.getKey()));
			}
		}

		return jsonObject.toString();
	}

	private static DDMFormFieldValue _createDDMFormFieldValue(
		Map<String, Object> dataRecordValues, DDMFormField ddmFormField,
		Locale locale) {

		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

		String name = ddmFormField.getName();

		ddmFormFieldValue.setName(name);

		if (dataRecordValues == null) {
			return ddmFormFieldValue;
		}

		Object value = dataRecordValues.get(name);

		if (value != null) {
			if (value instanceof Object[]) {
				JSONArray jsonArray = JSONUtil.putAll((Object[])value);

				value = jsonArray.toString();
			}

			if (ddmFormField.isLocalizable()) {
				LocalizedValue localizedValue = new LocalizedValue();

				localizedValue.addString(locale, String.valueOf(value));

				ddmFormFieldValue.setValue(localizedValue);
			}
			else {
				ddmFormFieldValue.setValue(
					new UnlocalizedValue(String.valueOf(value)));
			}
		}

		if (ListUtil.isNotEmpty(ddmFormField.getNestedDDMFormFields())) {
			for (DDMFormField nestedDDMFormField :
					ddmFormField.getNestedDDMFormFields()) {

				ddmFormFieldValue.addNestedDDMFormFieldValue(
					_createDDMFormFieldValue(
						dataRecordValues, nestedDDMFormField, locale));
			}
		}

		return ddmFormFieldValue;
	}

	private static Object _toDataRecordValue(
		DataDefinitionField dataDefinitionField, JSONObject jsonObject) {

		if (dataDefinitionField.getRepeatable()) {
			return JSONUtil.toObjectArray(
				jsonObject.getJSONArray(dataDefinitionField.getName()));
		}

		return jsonObject.get(dataDefinitionField.getName());
	}

}