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
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;
import java.util.Map;

/**
 * @author Rafael Praxedes
 */
public class NestedFieldsSupportMapToDDMFormValuesConverterStrategy
	implements MapToDDMFormValuesConverterStrategy {

	public static NestedFieldsSupportMapToDDMFormValuesConverterStrategy
		getInstance() {

		return _nestedFieldsSupportMapToDDMFormValuesConverterStrategy;
	}

	@Override
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

		Object value = fieldInstanceValue.get("value");

		if (!StringUtil.equals(ddmFormField.getType(), "fieldset") &&
			Validator.isNotNull(value)) {

			ddmFormFieldValue.setValue(
				createValue(ddmFormField, locale, value));
		}

		if (ListUtil.isNotEmpty(ddmFormField.getNestedDDMFormFields())) {
			Map<String, Object> nestedValues =
				(Map<String, Object>)fieldInstanceValue.get("nestedValues");

			if (MapUtil.isEmpty(nestedValues)) {
				return ddmFormFieldValue;
			}

			for (Map.Entry<String, Object> entry : nestedValues.entrySet()) {
				String[] parts = StringUtil.split(entry.getKey(), "_INSTANCE_");

				ddmFormFieldValue.addNestedDDMFormFieldValue(
					createDDMFormFieldValue(
						ddmFormFields.get(parts[0]), ddmFormFields,
						(Map<String, Object>)entry.getValue(), parts[1],
						locale));
			}
		}

		return ddmFormFieldValue;
	}

	private NestedFieldsSupportMapToDDMFormValuesConverterStrategy() {
	}

	private static NestedFieldsSupportMapToDDMFormValuesConverterStrategy
		_nestedFieldsSupportMapToDDMFormValuesConverterStrategy =
			new NestedFieldsSupportMapToDDMFormValuesConverterStrategy();

}