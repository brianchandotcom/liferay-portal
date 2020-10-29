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

package com.liferay.data.engine.rest.internal.storage.util;

import com.liferay.data.engine.rest.dto.v2_0.DataDefinition;
import com.liferay.data.engine.rest.dto.v2_0.DataDefinitionField;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Jeyvison Nascimento
 * @author Leonardo Barros
 */
public class DataStorageUtil {

	public static Map<String, Object> toDataRecordValues(
			DDMFormValues ddmFormValues, DDMStructure ddmStructure)
		throws PortalException {

		if (ddmFormValues == null) {
			return Collections.emptyMap();
		}

		DDMForm ddmForm = ddmStructure.getDDMForm();

		_addMissingDDMFormFieldValues(
			ddmForm.getDDMFormFields(), ddmFormValues);

		Map<String, DDMFormField> ddmFormFields =
			ddmStructure.getFullHierarchyDDMFormFieldsMap(true);

		Map<String, Object> values = new HashMap<>(ddmFormFields.size());

		for (DDMFormFieldValue ddmFormFieldValue :
				ddmFormValues.getDDMFormFieldValues()) {

			_addValues(ddmFormFields, ddmFormFieldValue, values);
		}

		return values;
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
						(List<Object>)dataRecordValues.get(entry.getKey())));
			}
			else {
				jsonObject.put(
					entry.getKey(), dataRecordValues.get(entry.getKey()));
			}
		}

		return jsonObject.toString();
	}

	private static void _addMissingDDMFormFieldValues(
		List<DDMFormField> ddmFormFields, DDMFormValues ddmFormValues) {

		Map<String, List<DDMFormFieldValue>> ddmFormFieldValues =
			ddmFormValues.getDDMFormFieldValuesMap(false);

		for (DDMFormField ddmFormField : ddmFormFields) {
			if (!ddmFormFieldValues.containsKey(ddmFormField.getName()) &&
				!_isFieldSet(ddmFormField)) {

				DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue() {
					{
						setInstanceId(StringUtil.randomString());
						setName(ddmFormField.getName());
					}
				};

				ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);

				if (ListUtil.isNotEmpty(
						ddmFormField.getNestedDDMFormFields())) {

					_addMissingDDMFormFieldValues(
						ddmFormField.getNestedDDMFormFields(),
						ddmFormFieldValues, ddmFormFieldValue);
				}
			}
		}
	}

	private static void _addMissingDDMFormFieldValues(
		List<DDMFormField> ddmFormFields,
		Map<String, List<DDMFormFieldValue>> ddmFormFieldValues,
		DDMFormFieldValue parentDDMFormFieldValue) {

		for (DDMFormField ddmFormField : ddmFormFields) {
			if (!ddmFormFieldValues.containsKey(ddmFormField.getName())) {
				DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue() {
					{
						setInstanceId(StringUtil.randomString());
						setName(ddmFormField.getName());
					}
				};

				parentDDMFormFieldValue.addNestedDDMFormFieldValue(
					ddmFormFieldValue);

				if (ListUtil.isNotEmpty(
						ddmFormField.getNestedDDMFormFields())) {

					_addMissingDDMFormFieldValues(
						ddmFormField.getNestedDDMFormFields(),
						ddmFormFieldValues, ddmFormFieldValue);
				}
			}
		}
	}

	private static void _addValue(
		DDMFormField ddmFormField, DDMFormFieldValue ddmFormFieldValue,
		Map<String, Object> values) {

		if (ddmFormField == null) {
			return;
		}

		String name = ddmFormField.getName();

		Value value = ddmFormFieldValue.getValue();

		if (value == null) {
			values.put(name, null);

			return;
		}

		if (ddmFormField.isRepeatable()) {
			if (ddmFormField.isLocalizable()) {
				Map<String, Object> localizedValues =
					(Map<String, Object>)values.getOrDefault(
						name, new HashMap<>());

				LocalizedValue localizedValue = (LocalizedValue)value;

				Set<Locale> availableLocales =
					localizedValue.getAvailableLocales();

				for (Locale locale : availableLocales) {
					String languageId = LanguageUtil.getLanguageId(locale);

					List<Object> list =
						(List<Object>)localizedValues.getOrDefault(
							languageId, new ArrayList<>());

					list.add(localizedValue.getString(locale));

					localizedValues.put(languageId, list);
				}

				values.put(name, localizedValues);
			}
			else {
				List<Object> list = (List<Object>)values.getOrDefault(
					name, new ArrayList<>());

				list.add(value.getString(value.getDefaultLocale()));

				values.put(name, list);
			}
		}
		else if (ddmFormField.isLocalizable()) {
			values.put(
				name,
				_toLocalizedMap(ddmFormField.getType(), (LocalizedValue)value));
		}
		else {
			values.put(name, value.getString(value.getDefaultLocale()));
		}
	}

	private static void _addValues(
		Map<String, DDMFormField> ddmFormFields,
		DDMFormFieldValue ddmFormFieldValue, Map<String, Object> values) {

		DDMFormField ddmFormField = ddmFormFields.get(
			ddmFormFieldValue.getName());

		if ((ddmFormField != null) &&
			StringUtil.equals(ddmFormField.getType(), "fieldset")) {

			if (ListUtil.isEmpty(
					ddmFormFieldValue.getNestedDDMFormFieldValues())) {

				return;
			}

			if (!values.containsKey(ddmFormField.getName())) {
				values.put(ddmFormField.getName(), new HashMap<>());
			}

			Map<String, Object> fieldSetInstanceValues =
				(Map<String, Object>)values.get(ddmFormField.getName());

			if (!fieldSetInstanceValues.containsKey(
					ddmFormFieldValue.getInstanceId())) {

				fieldSetInstanceValues.put(
					ddmFormFieldValue.getInstanceId(), new HashMap<>());
			}

			for (DDMFormFieldValue nestedDDMFormFieldValue :
					ddmFormFieldValue.getNestedDDMFormFieldValues()) {

				_addValues(
					ddmFormFields, nestedDDMFormFieldValue,
					(Map<String, Object>)fieldSetInstanceValues.get(
						ddmFormFieldValue.getInstanceId()));
			}
		}
		else {
			_addValue(ddmFormField, ddmFormFieldValue, values);
		}
	}

	private static boolean _isFieldSet(DDMFormField ddmFormField) {
		if (GetterUtil.getBoolean(
				ddmFormField.getProperty("upgradedStructure")) ||
			Validator.isNotNull(ddmFormField.getProperty("ddmStructureId"))) {

			return true;
		}

		return false;
	}

	private static Map<String, Object> _toLocalizedMap(
		String fieldType, LocalizedValue localizedValue) {

		Set<Locale> availableLocales = localizedValue.getAvailableLocales();

		Stream<Locale> stream = availableLocales.stream();

		if (fieldType.equals(DDMFormFieldType.CHECKBOX_MULTIPLE) ||
			fieldType.equals(DDMFormFieldType.SELECT)) {

			return stream.collect(
				Collectors.toMap(
					LanguageUtil::getLanguageId,
					locale -> _toStringList(locale, localizedValue)));
		}

		return stream.collect(
			Collectors.toMap(
				LanguageUtil::getLanguageId, localizedValue::getString));
	}

	private static List<String> _toStringList(
		Locale locale, LocalizedValue localizedValue) {

		try {
			return JSONUtil.toStringList(
				JSONFactoryUtil.createJSONArray(
					localizedValue.getString(locale)));
		}
		catch (JSONException jsonException) {
			return Collections.emptyList();
		}
	}

}