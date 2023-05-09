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

package com.liferay.dynamic.data.mapping.form.field.type.internal.util;

import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;

import java.util.Locale;

/**
 * @author Renan Vasconcelos
 */
public class DDMFormFieldValueUtil {

	public static String getOptionLabel(
		DDMFormFieldValue ddmFormFieldValue, Locale locale) {

		DDMFormFieldOptions ddmFormFieldOptions = _getDDMFormFieldOptions(
			ddmFormFieldValue);

		String optionValue = getOptionValue(ddmFormFieldValue, locale);

		LocalizedValue optionLabel = ddmFormFieldOptions.getOptionLabels(
			optionValue);

		if (optionLabel == null) {
			return optionValue;
		}

		return optionLabel.getString(locale);
	}

	public static String getOptionValue(
		DDMFormFieldValue ddmFormFieldValue, Locale locale) {

		Value value = ddmFormFieldValue.getValue();

		return value.getString(locale);
	}

	private static DDMFormFieldOptions _getDDMFormFieldOptions(
		DDMFormFieldValue ddmFormFieldValue) {

		DDMFormField ddmFormField = ddmFormFieldValue.getDDMFormField();

		return ddmFormField.getDDMFormFieldOptions();
	}

}