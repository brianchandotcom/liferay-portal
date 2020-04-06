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
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang.ClassUtils;

/**
 * @author Jeyvison Nascimento
 * @author Leonardo Barros
 */
public class DataRecordValuesUtil {

	public static DDMFormValues toDDMFormValues(
		Map<String, Object> dataRecordValues, DDMForm ddmForm, Locale locale) {

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		if (locale == null) {
			Set<Locale> availableLocales = ddmForm.getAvailableLocales();

			Stream<Locale> stream = availableLocales.stream();

			stream.forEach(ddmFormValues::addAvailableLocale);

			ddmFormValues.setDefaultLocale(ddmForm.getDefaultLocale());
		}
		else {
			ddmFormValues.addAvailableLocale(locale);

			ddmFormValues.setDefaultLocale(locale);
		}

		Map<String, DDMFormField> ddmFormFields = ddmForm.getDDMFormFieldsMap(
			true);

		for (Map.Entry<String, DDMFormField> entry : ddmFormFields.entrySet()) {
			List<DDMFormFieldValue> ddmFormFieldValues =
				createDDMFormFieldValues(
					dataRecordValues, entry.getValue(), locale);

			Stream<DDMFormFieldValue> stream = ddmFormFieldValues.stream();

			stream.forEach(ddmFormValues::addDDMFormFieldValue);
		}

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

	protected static List<DDMFormFieldValue> createDDMFormFieldValues(
		Map<String, Object> dataRecordValues, DDMFormField ddmFormField,
		Locale locale) {

		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

		String name = ddmFormField.getName();

		ddmFormFieldValue.setName(name);

		ddmFormFieldValue.setValue(ddmFormField.getPredefinedValue());

		if (ListUtil.isNotEmpty(ddmFormField.getNestedDDMFormFields())) {
			for (DDMFormField nestedDDMFormField :
					ddmFormField.getNestedDDMFormFields()) {

				List<DDMFormFieldValue> ddmFormFieldValues =
					createDDMFormFieldValues(
						dataRecordValues, nestedDDMFormField, locale);

				Stream<DDMFormFieldValue> stream = ddmFormFieldValues.stream();

				stream.forEach(ddmFormFieldValue::addNestedDDMFormFieldValue);
			}
		}

		if ((dataRecordValues == null) || !dataRecordValues.containsKey(name)) {
			return ListUtil.fromArray(ddmFormFieldValue);
		}

		if (ddmFormField.isRepeatable()) {
			Object[] instancesValue = null;

			if (ddmFormField.isLocalizable()) {
				Object value = dataRecordValues.get(name);

				if (!(value instanceof Map)) {
					throw new IllegalArgumentException(
						"Field value is not a map");
				}

				Map<String, Object> localizedValues =
					(Map<String, Object>)value;

				instancesValue = (Object[])localizedValues.get(
					LanguageUtil.getLanguageId(locale));
			}
			else {
				instancesValue = (Object[])dataRecordValues.get(name);
			}

			if (instancesValue == null) {
				return ListUtil.fromArray(ddmFormFieldValue);
			}

			List<DDMFormFieldValue> ddmFormFieldValues = new ArrayList<>(
				instancesValue.length);

			for (Object instanceValue : instancesValue) {
				ddmFormFieldValue = new DDMFormFieldValue();

				ddmFormFieldValue.setName(ddmFormField.getName());

				LocalizedValue localizedValue = new LocalizedValue();

				localizedValue.addString(locale, String.valueOf(instanceValue));

				ddmFormFieldValue.setValue(localizedValue);

				ddmFormFieldValues.add(ddmFormFieldValue);
			}

			return ddmFormFieldValues;
		}

		if (dataRecordValues.get(name) != null) {
			ddmFormFieldValue.setValue(
				createValue(ddmFormField, locale, dataRecordValues.get(name)));
		}

		return ListUtil.fromArray(ddmFormFieldValue);
	}

	protected static LocalizedValue createLocalizedValue(
		Locale locale, Object value) {

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

	protected static Value createValue(
		DDMFormField ddmFormField, Locale locale, Object value) {

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

}