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

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import java.util.Locale;

/**
 * @author Rafael Praxedes
 * @author Guilherme Camacho
 */
public class NumericDDMFormFieldUtil {

	public static DecimalFormat getDecimalFormat(Locale locale) {
		DecimalFormat decimalFormat = (DecimalFormat)DecimalFormat.getInstance(
			locale);

		DecimalFormatSymbols decimalFormatSymbols =
			decimalFormat.getDecimalFormatSymbols();

		decimalFormatSymbols.setZeroDigit('0');

		decimalFormat.setDecimalFormatSymbols(decimalFormatSymbols);

		decimalFormat.setGroupingUsed(false);
		decimalFormat.setMaximumFractionDigits(Integer.MAX_VALUE);
		decimalFormat.setParseBigDecimal(true);

		return decimalFormat;
	}

	public static String getFormattedValue(Locale locale, String value) {
		if (Validator.isNull(value) || !NumberUtil.hasDecimalSeparator(value)) {
			return value;
		}

		DecimalFormat decimalFormat = getDecimalFormat(locale);

		DecimalFormatSymbols decimalFormatSymbols =
			decimalFormat.getDecimalFormatSymbols();

		return StringUtil.replace(
			value, value.charAt(NumberUtil.getDecimalSeparatorIndex(value)),
			decimalFormatSymbols.getDecimalSeparator());
	}

}