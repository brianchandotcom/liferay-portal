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

package com.liferay.dynamic.data.mapping.type.radio.internal;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueValidationException;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueValidator;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(
	immediate = true, property = "ddm.form.field.type.name=radio",
	service = DDMFormFieldValueValidator.class
)
public class RadioDDMFormFieldValueValidator
	implements DDMFormFieldValueValidator {

	@Override
	public void validate(
			DDMFormField ddmFormField, DDMFormFieldValue ddmFormFieldValue)
		throws DDMFormFieldValueValidationException {

		DDMFormFieldOptions ddmFormFieldOptions =
			ddmFormField.getDDMFormFieldOptions();

		if (ddmFormFieldOptions == null) {
			throw new DDMFormFieldValueValidationException(
				String.format(
					"Options must be set for radio field \"%s\"",
					ddmFormField.getName()));
		}

		Set<String> optionValues = ddmFormFieldOptions.getOptionsValues();

		if (optionValues.isEmpty()) {
			throw new DDMFormFieldValueValidationException(
				"Options must contain at least one alternative");
		}

		Value value = ddmFormFieldValue.getValue();

		Map<Locale, String> selectedValues = value.getValues();

		for (String selectedValue : selectedValues.values()) {
			validateSelectedValue(ddmFormField, optionValues, selectedValue);
		}
	}

	protected JSONArray createJSONArray(String fieldName, String json) {
		try {
			return jsonFactory.createJSONArray(json);
		}
		catch (JSONException jsone) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(jsone, jsone);
			}

			throw new IllegalStateException(
				String.format(
					"Invalid data stored for radio field \"%s\"", fieldName));
		}
	}

	protected void validateSelectedValue(
			DDMFormField ddmFormField, Set<String> optionValues,
			String selectedValue)
		throws DDMFormFieldValueValidationException {

		JSONArray jsonArray = createJSONArray(
			ddmFormField.getName(), selectedValue);

		for (int i = 0; i < jsonArray.length(); i++) {
			if (Validator.isNull(jsonArray.getString(i)) &&
				!ddmFormField.isRequired()) {

				continue;
			}

			if (!optionValues.contains(jsonArray.getString(i))) {
				throw new DDMFormFieldValueValidationException(
					String.format(
						"The selected option \"%s\" is not a valid alternative",
						jsonArray.getString(i)));
			}
		}
	}

	@Reference
	protected JSONFactory jsonFactory;

	private static final Log _log = LogFactoryUtil.getLog(
		RadioDDMFormFieldValueValidator.class);

}