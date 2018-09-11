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

package com.liferay.forms.apio.internal.util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.reflect.TypeToken;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.forms.apio.internal.model.FormFieldValue;

import java.lang.reflect.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

/**
 * @author Paulo Cruz
 */
public final class FormValuesUtil {

	public static DDMFormValues getDDMFormValues(
		String fieldValues, DDMForm ddmForm, Locale locale) {

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		ddmFormValues.addAvailableLocale(locale);
		ddmFormValues.setDefaultLocale(locale);

		FormValuesUtil.FormFieldValueListToken formFieldValueListToken =
			new FormValuesUtil.FormFieldValueListToken();

		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmForm.getDDMFormFieldsMap(true);

		Type listType = formFieldValueListToken.getType();

		Gson gson = new Gson();

		List<FormFieldValue> formFieldValues = gson.fromJson(
			fieldValues, listType);

		for (FormFieldValue formFieldValue : formFieldValues) {
			DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

			ddmFormFieldValue.setName(formFieldValue.name);

			DDMFormField ddmFormField = ddmFormFieldsMap.get(
				formFieldValue.name);

			Value value = _EMPTY_VALUE;

			if ((ddmFormField != null) && !ddmFormField.isTransient()) {
				value = Optional.ofNullable(
					formFieldValue.value
				).map(
					FormValuesUtil::_toString
				).map(
					stringValue -> _getValue(stringValue, ddmFormField, locale)
				).orElse(
					_EMPTY_VALUE
				);
			}

			_setFieldValue(value, ddmFormValues, ddmFormFieldValue);
		}

		return ddmFormValues;
	}

	private static Value _getValue(
		String stringValue, DDMFormField ddmFormField, Locale locale) {

		Value value = null;

		if (ddmFormField.isLocalizable()) {
			value = new LocalizedValue();

			value.addString(locale, stringValue);
		}
		else {
			value = new UnlocalizedValue(stringValue);
		}

		return value;
	}

	private static void _setFieldValue(
		Value value, DDMFormValues ddmFormValues,
		DDMFormFieldValue ddmFormFieldValue) {

		ddmFormFieldValue.setValue(value);

		ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);
	}

	private static String _toString(JsonElement jsonElement) {
		if (jsonElement instanceof JsonPrimitive) {
			JsonPrimitive jsonPrimitive = (JsonPrimitive)jsonElement;

			if (!jsonPrimitive.isJsonNull()) {
				return jsonPrimitive.getAsString();
			}
		}

		return jsonElement.toString();
	}

	private static final Value _EMPTY_VALUE = new UnlocalizedValue(
		(String)null);

	private static class FormFieldValueListToken
		extends TypeToken<ArrayList<FormFieldValue>> {
	}

}