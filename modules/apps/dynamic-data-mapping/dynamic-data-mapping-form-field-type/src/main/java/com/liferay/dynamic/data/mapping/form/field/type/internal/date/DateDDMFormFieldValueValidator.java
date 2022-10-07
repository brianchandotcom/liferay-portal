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

package com.liferay.dynamic.data.mapping.form.field.type.internal.date;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueValidationException;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueValidator;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.Validator;

import java.text.ParseException;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcela Cunha
 * @author Pedro Queiroz
 */
@Component(
	property = "ddm.form.field.type.name=" + DDMFormFieldTypeConstants.DATE,
	service = DDMFormFieldValueValidator.class
)
public class DateDDMFormFieldValueValidator
	implements DDMFormFieldValueValidator {

	@Override
	public void validate(DDMFormField ddmFormField, Value value)
		throws DDMFormFieldValueValidationException {

		for (Locale availableLocale : value.getAvailableLocales()) {
			_validateDateValue(
				ddmFormField, availableLocale,
				value.getString(availableLocale));
		}
	}

	@Reference
	protected JSONFactory jsonFactory;

	private void _validateDateValue(
			DDMFormField ddmFormField, Locale locale, String valueString)
		throws DDMFormFieldValueValidationException {

		if (Validator.isNotNull(valueString)) {
			try {
				DateUtil.formatDate("yyyy-MM-dd", valueString, locale);
			}
			catch (ParseException parseException) {
				if (_log.isDebugEnabled()) {
					_log.debug(parseException);
				}

				throw new DDMFormFieldValueValidationException(
					String.format(
						"Invalid date input for date field \"%s\"",
						ddmFormField.getName()));
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DateDDMFormFieldValueValidator.class);

}