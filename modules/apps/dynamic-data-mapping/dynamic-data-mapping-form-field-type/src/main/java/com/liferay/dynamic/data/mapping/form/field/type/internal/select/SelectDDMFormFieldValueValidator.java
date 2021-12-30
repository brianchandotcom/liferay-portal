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

package com.liferay.dynamic.data.mapping.form.field.type.internal.select;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueValidationException;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueValidator;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(
	immediate = true,
	property = "ddm.form.field.type.name=" + DDMFormFieldTypeConstants.SELECT,
	service = DDMFormFieldValueValidator.class
)
public class SelectDDMFormFieldValueValidator
	implements DDMFormFieldValueValidator {

	@Override
	public void validate(DDMFormField ddmFormField, Value value)
		throws DDMFormFieldValueValidationException {

		if (Objects.equals(ddmFormField.getDataSourceType(), "manual")) {
			try {
				_validateDDMFormFieldOptions(ddmFormField, value);
			}
			catch (DDMFormFieldValueValidationException
						ddmFormFieldValueValidationException) {

				throw ddmFormFieldValueValidationException;
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception, exception);
				}

				throw new DDMFormFieldValueValidationException(exception);
			}
		}
	}

	@Reference
	protected JSONFactory jsonFactory;

	private void _validateDDMFormFieldOptions(
			DDMFormField ddmFormField, Value value)
		throws Exception {

		DDMFormFieldOptions ddmFormFieldOptions =
			ddmFormField.getDDMFormFieldOptions();

		if (ddmFormFieldOptions == null) {
			throw new DDMFormFieldValueValidationException(
				String.format(
					"Options must be set for select field \"%s\"",
					ddmFormField.getName()));
		}

		Set<String> optionsValues = ddmFormFieldOptions.getOptionsValues();

		if (optionsValues.isEmpty()) {
			throw new DDMFormFieldValueValidationException(
				"Options must contain at least one alternative");
		}

		Map<Locale, String> selectedValues = value.getValues();

		for (String selectedValue : selectedValues.values()) {
			_validateSelectedValue(ddmFormField, optionsValues, selectedValue);
		}
	}

	private void _validateSelectedValue(
			DDMFormField ddmFormField, Set<String> optionValues,
			String selectedValue)
		throws Exception {

		JSONArray jsonArray = jsonFactory.createJSONArray(selectedValue);

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

	private static final Log _log = LogFactoryUtil.getLog(
		SelectDDMFormFieldValueValidator.class);

}