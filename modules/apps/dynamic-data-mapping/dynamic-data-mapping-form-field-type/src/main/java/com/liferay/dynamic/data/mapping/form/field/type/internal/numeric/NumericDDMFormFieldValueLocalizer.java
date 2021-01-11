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

package com.liferay.dynamic.data.mapping.form.field.type.internal.numeric;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueEditingAware;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueLocalizer;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.text.DecimalFormat;
import java.text.ParseException;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Rodrigo Paulino
 */
@Component(
	immediate = true,
	property = "ddm.form.field.type.name=" + DDMFormFieldTypeConstants.NUMERIC,
	service = DDMFormFieldValueLocalizer.class
)
public class NumericDDMFormFieldValueLocalizer
	implements DDMFormFieldValueEditingAware, DDMFormFieldValueLocalizer {

	@Override
	public boolean isEditingFieldValue() {
		return _editingFieldValue;
	}

	@Override
	public String localize(String value, Locale locale) {
		try {
			DecimalFormat decimalFormat =
				NumericDDMFormFieldUtil.getNumberFormat(locale);

			Number number = GetterUtil.getNumber(decimalFormat.parse(value));

			String formattedNumber = decimalFormat.format(number);

			if (!value.equals(formattedNumber)) {
				DecimalFormat defaultDecimalFormat =
					NumericDDMFormFieldUtil.getNumberFormat(LocaleUtil.US);

				number = defaultDecimalFormat.parse(value);

				formattedNumber = decimalFormat.format(number);

				String lastChar = String.valueOf(
					value.charAt(value.length() - 1));

				if (isEditingFieldValue() &&
					(lastChar.equals(StringPool.COMMA) ||
					 lastChar.equals(StringPool.PERIOD))) {

					formattedNumber = formattedNumber.concat(lastChar);
				}
			}

			return formattedNumber;
		}
		catch (ParseException parseException) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to parse number for locale " + locale,
					parseException);
			}
		}

		return value;
	}

	@Override
	public void setEditingFieldValue(boolean editingFieldValue) {
		_editingFieldValue = editingFieldValue;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		NumericDDMFormFieldValueLocalizer.class);

	private boolean _editingFieldValue;

}