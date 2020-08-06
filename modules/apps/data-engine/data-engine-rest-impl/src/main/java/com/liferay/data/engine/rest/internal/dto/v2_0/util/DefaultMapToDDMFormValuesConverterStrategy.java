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
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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

	public void setDDMFormFieldValues(
		Map<String, Object> dataRecordValues, DDMForm ddmForm,
		DDMFormValues ddmFormValues, Locale locale) {

		Map<String, DDMFormField> ddmFormFields = ddmForm.getDDMFormFieldsMap(
			true);

		for (Map.Entry<String, DDMFormField> entry : ddmFormFields.entrySet()) {
			if (dataRecordValues.containsKey(entry.getKey())) {
				List<DDMFormFieldValue> ddmFormFieldValues =
					createDDMFormFieldValues(
						dataRecordValues, entry.getValue(), locale);

				Stream<DDMFormFieldValue> stream = ddmFormFieldValues.stream();

				stream.forEach(ddmFormValues::addDDMFormFieldValue);
			}
		}
	}

	protected List<DDMFormFieldValue> createDDMFormFieldValues(
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

	private DefaultMapToDDMFormValuesConverterStrategy() {
	}

	private static DefaultMapToDDMFormValuesConverterStrategy
		_defaultMapToDDMFormValuesConverterStrategy =
			new DefaultMapToDDMFormValuesConverterStrategy();

}