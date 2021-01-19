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

package com.liferay.data.engine.rest.internal.strategy;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
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
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author Jeyvison Nascimento
 * @author Leonardo Barros
 */
public class DefaultMapToDDMFormValuesConverterStrategy
	implements MapToDDMFormValuesConverterStrategy {

	public static DefaultMapToDDMFormValuesConverterStrategy getInstance() {
		return _defaultMapToDDMFormValuesConverterStrategy;
	}

	@Override
	public void setDDMFormFieldValues(
		Map<String, Object> dataRecordValues, DDMForm ddmForm,
		DDMFormValues ddmFormValues, Locale locale) {

		Map<String, DDMFormField> ddmFormFields = ddmForm.getDDMFormFieldsMap(
			false);

		for (Map.Entry<String, DDMFormField> entry : ddmFormFields.entrySet()) {
			_addDDFormFieldValues(
				dataRecordValues, entry.getValue(), ddmFormValues,
				ddmForm.getDefaultLocale(), locale);
		}
	}

	protected List<DDMFormFieldValue> createDDMFormFieldValues(
		Map<String, Object> dataRecordValues, DDMFormField ddmFormField,
		Locale defaultLocale, Locale locale) {

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
							nestedDDMFormField, defaultLocale, locale);

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
					LanguageUtil.getLanguageId(
						(Locale)GetterUtil.getObject(
							locale, LocaleUtil.getSiteDefault())));

				if (!Objects.equals(
						LocaleUtil.getSiteDefault(), defaultLocale) &&
					ListUtil.isEmpty(list)) {

					list = (List<Object>)localizedValues.get(
						LanguageUtil.getLanguageId(defaultLocale));
				}
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

				if (object instanceof Map) {
					JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
						(Map)object);

					localizedValue.addString(
						(Locale)GetterUtil.getObject(locale, defaultLocale),
						jsonObject.toString());
				}
				else if (object instanceof Object[]) {
					JSONArray jsonArray = JSONUtil.putAll((Object[])object);

					localizedValue.addString(
						(Locale)GetterUtil.getObject(locale, defaultLocale),
						jsonArray.toString());
				}
				else {
					localizedValue.addString(
						(Locale)GetterUtil.getObject(locale, defaultLocale),
						String.valueOf(object));
				}

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

	private DefaultMapToDDMFormValuesConverterStrategy() {
	}

	private List<DDMFormFieldValue> _addDDFormFieldValues(
		Map<String, Object> dataRecordValues, DDMFormField ddmFormField,
		DDMFormValues ddmFormValues, Locale defaultLocale, Locale locale) {

		List<DDMFormFieldValue> ddmFormFieldValues = new ArrayList<>();

		ddmFormFieldValues.addAll(
			createDDMFormFieldValues(
				dataRecordValues, ddmFormField, defaultLocale, locale));

		Stream<DDMFormFieldValue> stream = ddmFormFieldValues.stream();

		stream.forEach(
			ddmFormFieldValue -> {
				List<DDMFormField> nestedDDMFormFields =
					ddmFormField.getNestedDDMFormFields();

				List<DDMFormFieldValue> nestedDDMFormFieldValues =
					ddmFormFieldValue.getNestedDDMFormFieldValues();

				nestedDDMFormFieldValues.clear();

				nestedDDMFormFields.forEach(
					nestedDDMFormField -> {
						List<DDMFormFieldValue>
							updatedNestedDDMFormFieldValues =
								_addDDFormFieldValues(
									dataRecordValues, nestedDDMFormField,
									ddmFormValues, defaultLocale, locale);

						updatedNestedDDMFormFieldValues.forEach(
							ddmFormFieldValue::addNestedDDMFormFieldValue);
					});

				ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);
			});

		return ddmFormFieldValues;
	}

	private static final DefaultMapToDDMFormValuesConverterStrategy
		_defaultMapToDDMFormValuesConverterStrategy =
			new DefaultMapToDDMFormValuesConverterStrategy();

}