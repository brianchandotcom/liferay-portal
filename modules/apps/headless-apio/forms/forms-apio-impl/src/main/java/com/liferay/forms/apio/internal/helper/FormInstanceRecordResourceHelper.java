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

package com.liferay.forms.apio.internal.helper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.liferay.apio.architect.functional.Try;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.forms.apio.internal.FormFieldValue;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.lang.reflect.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Paulo Cruz
 */
public class FormInstanceRecordResourceHelper {

	public static DDMFormValues getDDMFormValues(
		String fieldValues, DDMForm ddmForm, Locale locale) {

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		Gson gson = new Gson();

		FormFieldValueListToken formFieldValueListToken =
			new FormFieldValueListToken();

		Type listType = formFieldValueListToken.getType();

		List<FormFieldValue> formFieldValues = gson.fromJson(
			fieldValues, listType);

		for (FormFieldValue formFieldValue : formFieldValues) {
			DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

			ddmFormFieldValue.setInstanceId(formFieldValue.identifier);
			ddmFormFieldValue.setName(formFieldValue.name);

			LocalizedValue localizedValue = new LocalizedValue();

			localizedValue.addString(locale, formFieldValue.value);

			ddmFormFieldValue.setValue(localizedValue);

			ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);
		}

		return ddmFormValues;
	}

	public static String getFieldValuesJSON(
		DDMFormInstanceRecord ddmFormInstanceRecord, Locale locale) {

		Gson gson = new Gson();

		return Try.fromFallible(
			ddmFormInstanceRecord::getDDMFormValues
		).map(
			DDMFormValues::getDDMFormFieldValues
		).map(
			List::stream
		).map(
			stream -> stream.map(_toFormFieldValue(locale))
		).map(
			stream -> stream.collect(Collectors.toList())
		).map(
			gson::toJson
		).fold(
			e -> {
				_log.error(e, e);

				return null;
			},
			json -> json
		);
	}

	private static Function<DDMFormFieldValue, FormFieldValue>
		_toFormFieldValue(Locale locale) {

		return ddmFormFieldValue -> {
			String instanceId = ddmFormFieldValue.getInstanceId();
			String name = ddmFormFieldValue.getName();

			Value value = ddmFormFieldValue.getValue();

			String valueString = value.getString(locale);

			return new FormFieldValue(instanceId, name, valueString);
		};
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FormInstanceRecordResourceHelper.class);

	private static class FormFieldValueListToken
		extends TypeToken<ArrayList<FormFieldValue>> {
	}

}