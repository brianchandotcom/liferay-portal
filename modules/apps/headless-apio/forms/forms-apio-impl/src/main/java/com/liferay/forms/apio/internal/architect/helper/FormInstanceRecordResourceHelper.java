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

package com.liferay.forms.apio.internal.architect.helper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.liferay.apio.architect.language.Language;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.forms.apio.internal.architect.FormFieldValue;
import com.liferay.portal.kernel.exception.PortalException;

import java.lang.reflect.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.ws.rs.ServerErrorException;

/**
 * @author Paulo Cruz
 */
public class FormInstanceRecordResourceHelper {

	public static DDMFormValues getDDMFormValues(
		String fieldValues, DDMForm ddmForm, Locale locale) {

		Gson gson = new Gson();

		Type listType = new FormFieldValueListToken().getType();

		List<FormFieldValue> formFieldValues = gson.fromJson(
			fieldValues, listType);

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		for (FormFieldValue formFieldValue : formFieldValues) {
			DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

			ddmFormFieldValue.setInstanceId(formFieldValue.getInstanceId());
			ddmFormFieldValue.setName(formFieldValue.getName());

			LocalizedValue localizedValue = new LocalizedValue();

			localizedValue.addString(locale, formFieldValue.getValue());
			ddmFormFieldValue.setValue(localizedValue);
			ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);
		}

		return ddmFormValues;
	}

	public static String getFieldValuesJSON(
		DDMFormInstanceRecord ddmFormInstanceRecord, Language language) {

		try {
			Gson gson = new Gson();

			DDMFormValues ddmFormValues =
				ddmFormInstanceRecord.getDDMFormValues();

			List<DDMFormFieldValue> ddmFormFieldValues =
				ddmFormValues.getDDMFormFieldValues();

			List<FormFieldValue> formFieldValues = new ArrayList<>();

			for (DDMFormFieldValue ddmFormFieldValue : ddmFormFieldValues) {
				String instanceId = ddmFormFieldValue.getInstanceId();
				String name = ddmFormFieldValue.getName();
				Value value = ddmFormFieldValue.getValue();

				String valueString = value.getString(
					language.getPreferredLocale());

				formFieldValues.add(
					new FormFieldValue(instanceId, name, valueString));
			}

			return gson.toJson(formFieldValues);
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	private static class FormFieldValueListToken
		extends TypeToken<ArrayList<FormFieldValue>> {
	}

}