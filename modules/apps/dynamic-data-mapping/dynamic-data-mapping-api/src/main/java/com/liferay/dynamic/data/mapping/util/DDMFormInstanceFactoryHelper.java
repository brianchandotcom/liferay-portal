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

package com.liferay.dynamic.data.mapping.util;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.FieldConstants;

import java.io.Serializable;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author     Marcellus Tavares
 * @deprecated As of 3.5.0, with no direct replacement
 */
@Deprecated
public class DDMFormInstanceFactoryHelper {

	public DDMFormInstanceFactoryHelper(
		Class<?> clazz, DDMFormValues ddmFormValues, Locale locale) {

		_locale = locale;

		setDDMFormFieldNameMethodMap(clazz);

		DDMForm ddmForm = ddmFormValues.getDDMForm();

		_ddmForm = ddmForm;

		_ddmFormFieldValuesMap = ddmFormValues.getDDMFormFieldValuesMap();
	}

	public Map<String, Object> getProperties() {
		Map<String, Object> properties = new HashMap<>();

		for (DDMFormField ddmFormField : _ddmForm.getDDMFormFields()) {
			String methodName = _ddmFormFieldMethodNameMap.get(
				ddmFormField.getName());

			Object value = null;

			List<DDMFormFieldValue> ddmFormFieldValues =
				_ddmFormFieldValuesMap.get(ddmFormField.getName());

			if (ddmFormField.isRepeatable()) {
				value = toArrayValue(ddmFormField, ddmFormFieldValues);
			}
			else {
				value = toSimpleValue(ddmFormField, ddmFormFieldValues.get(0));
			}

			properties.put(methodName, value);
		}

		return properties;
	}

	protected void setDDMFormFieldNameMethodMap(Class<?> clazz) {
		DDMFormFactoryHelper ddmFormFactoryHelper = new DDMFormFactoryHelper(
			clazz);

		for (Method method : ddmFormFactoryHelper.getDDMFormFieldMethods()) {
			DDMFormFieldFactoryHelper ddmFormFieldFactoryHelper =
				new DDMFormFieldFactoryHelper(method);

			_ddmFormFieldMethodNameMap.put(
				ddmFormFieldFactoryHelper.getDDMFormFieldName(),
				method.getName());
		}
	}

	protected Object toArrayValue(
		DDMFormField ddmFormField, List<DDMFormFieldValue> ddmFormFieldValues) {

		List<Serializable> values = new ArrayList<>();

		for (DDMFormFieldValue ddmFormFieldValue : ddmFormFieldValues) {
			Serializable value = toSimpleValue(ddmFormField, ddmFormFieldValue);

			values.add(value);
		}

		return FieldConstants.getSerializable(
			ddmFormField.getDataType(), values);
	}

	protected Serializable toSimpleValue(
		DDMFormField ddmFormField, DDMFormFieldValue ddmFormFieldValue) {

		Value value = ddmFormFieldValue.getValue();

		return FieldConstants.getSerializable(
			ddmFormField.getDataType(), value.getString(_locale));
	}

	private final DDMForm _ddmForm;
	private final Map<String, String> _ddmFormFieldMethodNameMap =
		new HashMap<>();
	private final Map<String, List<DDMFormFieldValue>> _ddmFormFieldValuesMap;
	private final Locale _locale;

}