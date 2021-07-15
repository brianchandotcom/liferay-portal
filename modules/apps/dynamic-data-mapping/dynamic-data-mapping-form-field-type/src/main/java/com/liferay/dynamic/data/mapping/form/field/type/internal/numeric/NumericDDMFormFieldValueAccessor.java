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

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueAccessor;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.util.NumberUtil;
import com.liferay.dynamic.data.mapping.util.NumericDDMFormFieldUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.math.BigDecimal;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

import java.util.Locale;
import java.util.function.IntFunction;

import org.osgi.service.component.annotations.Component;

/**
 * @author Rafael Praxedes
 */
@Component(
	immediate = true,
	property = "ddm.form.field.type.name=" + DDMFormFieldTypeConstants.NUMERIC,
	service = {
		DDMFormFieldValueAccessor.class, NumericDDMFormFieldValueAccessor.class
	}
)
public class NumericDDMFormFieldValueAccessor
	implements DDMFormFieldValueAccessor<BigDecimal> {

	@Override
	public IntFunction<BigDecimal[]> getArrayGeneratorIntFunction() {
		return BigDecimal[]::new;
	}

	@Override
	public BigDecimal getValue(
		DDMFormFieldValue ddmFormFieldValue, Locale locale) {

		Value value = ddmFormFieldValue.getValue();

		return _getParsedValue(locale, value.getString(locale));
	}

	@Override
	public BigDecimal getValueForEvaluation(
		DDMFormFieldValue ddmFormFieldValue, Locale locale) {

		Value value = ddmFormFieldValue.getValue();

		String valueString = value.getString(locale);

		if (Validator.isNotNull(valueString) &&
			NumberUtil.hasDecimalSeparator(valueString)) {

			DecimalFormat decimalFormat =
				NumericDDMFormFieldUtil.getDecimalFormat(locale);

			DecimalFormatSymbols decimalFormatSymbols =
				decimalFormat.getDecimalFormatSymbols();

			valueString = StringUtil.replace(
				valueString,
				valueString.charAt(
					NumberUtil.getDecimalSeparatorIndex(valueString)),
				decimalFormatSymbols.getDecimalSeparator());
		}

		return _getParsedValue(locale, valueString);
	}

	private BigDecimal _getParsedValue(Locale locale, String value) {
		try {
			DecimalFormat decimalFormat =
				NumericDDMFormFieldUtil.getDecimalFormat(locale);

			return (BigDecimal)decimalFormat.parse(value);
		}
		catch (ParseException parseException) {
			if (_log.isDebugEnabled()) {
				_log.debug(parseException, parseException);
			}
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		NumericDDMFormFieldValueAccessor.class);

}