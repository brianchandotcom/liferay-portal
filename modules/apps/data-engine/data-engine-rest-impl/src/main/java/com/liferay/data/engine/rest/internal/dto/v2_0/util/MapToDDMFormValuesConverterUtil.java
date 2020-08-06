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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.apache.commons.lang.ClassUtils;

/**
 * @author Jeyvison Nascimento
 * @author Leonardo Barros
 */
public class MapToDDMFormValuesConverterUtil {

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

		if (MapUtil.isNotEmpty(dataRecordValues)) {
			Map<String, DDMFormField> ddmFormFields =
				ddmForm.getDDMFormFieldsMap(true);

			for (Map.Entry<String, DDMFormField> entry :
					ddmFormFields.entrySet()) {

				if (dataRecordValues.containsKey(entry.getKey())) {
					List<DDMFormFieldValue> ddmFormFieldValues =
						createDDMFormFieldValues(
							dataRecordValues, entry.getValue(), locale);

					Stream<DDMFormFieldValue> stream =
						ddmFormFieldValues.stream();

					stream.forEach(ddmFormValues::addDDMFormFieldValue);
				}
			}
		}

		return ddmFormValues;
	}

	protected static List<DDMFormFieldValue> createDDMFormFieldValues(
		Map<String, Object> dataRecordValues, DDMFormField ddmFormField,
		Locale locale) {

		if ((dataRecordValues == null) ||
			!dataRecordValues.containsKey(ddmFormField.getName())) {

			return ListUtil.fromArray(
				new DDMFormFieldValue() {
					{
						setName(ddmFormField.getName());
					}
				});
		}

		if (StringUtil.equals(ddmFormField.getType(), "fieldset")) {
			if (ListUtil.isEmpty(ddmFormField.getNestedDDMFormFields())) {
				return ListUtil.fromArray(
					new DDMFormFieldValue() {
						{
							setName(ddmFormField.getName());
						}
					});
			}

			Map<String, Object> fieldSetInstanceValues =
				(Map<String, Object>)dataRecordValues.get(
					ddmFormField.getName());

			List<DDMFormFieldValue> ddmFormFieldValues = new ArrayList<>(
				fieldSetInstanceValues.size());

			for (Map.Entry<String, Object> entry :
					fieldSetInstanceValues.entrySet()) {

				DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue() {
					{
						setName(ddmFormField.getName());
						setInstanceId(entry.getKey());
					}
				};

				for (DDMFormField nestedDDMFormField :
						ddmFormField.getNestedDDMFormFields()) {

					List<DDMFormFieldValue> nestedDDMFormFieldValues =
						createDDMFormFieldValues(
							(Map<String, Object>)fieldSetInstanceValues.get(
								ddmFormFieldValue.getInstanceId()),
							nestedDDMFormField, locale);

					Stream<DDMFormFieldValue> stream =
						nestedDDMFormFieldValues.stream();

					stream.forEach(
						ddmFormFieldValue::addNestedDDMFormFieldValue);
				}

				ddmFormFieldValues.add(ddmFormFieldValue);
			}

			return ddmFormFieldValues;
		}

		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue() {
			{
				setName(ddmFormField.getName());
				setValue(ddmFormField.getPredefinedValue());
			}
		};

		if (ddmFormField.isRepeatable()) {
			List<Object> list = null;

			if (ddmFormField.isLocalizable()) {
				Object value = dataRecordValues.get(ddmFormField.getName());

				if (!(value instanceof Map)) {
					throw new IllegalArgumentException(
						"Field value is not a map");
				}

				Map<String, Object> localizedValues =
					(Map<String, Object>)value;

				list = (List<Object>)localizedValues.get(
					LanguageUtil.getLanguageId(locale));
			}
			else {
				list = (List<Object>)dataRecordValues.get(
					ddmFormField.getName());
			}

			if (list == null) {
				return ListUtil.fromArray(ddmFormFieldValue);
			}

			List<DDMFormFieldValue> ddmFormFieldValues = new ArrayList<>(
				list.size());

			for (Object object : list) {
				ddmFormFieldValue = new DDMFormFieldValue();

				ddmFormFieldValue.setName(ddmFormField.getName());

				LocalizedValue localizedValue = new LocalizedValue();

				localizedValue.addString(locale, String.valueOf(object));

				ddmFormFieldValue.setValue(localizedValue);

				ddmFormFieldValues.add(ddmFormFieldValue);
			}

			return ddmFormFieldValues;
		}

		if (dataRecordValues.get(ddmFormField.getName()) != null) {
			ddmFormFieldValue.setValue(
				createValue(
					ddmFormField, locale,
					dataRecordValues.get(ddmFormField.getName())));
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