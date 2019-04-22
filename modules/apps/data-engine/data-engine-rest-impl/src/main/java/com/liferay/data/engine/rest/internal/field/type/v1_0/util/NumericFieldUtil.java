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

package com.liferay.data.engine.rest.internal.field.type.v1_0.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Gabriel Albuquerque
 */
public class NumericFieldUtil {

	public static String getFormattedValue(Object value, Locale locale) {
		if (Validator.isNull(value) ||
			StringUtil.equals((String)value, "NaN")) {

			return StringPool.BLANK;
		}

		DecimalFormat numberFormat = getNumberFormat(locale);

		return numberFormat.format(GetterUtil.getNumber(value));
	}

	public static DecimalFormat getNumberFormat(Locale locale) {
		DecimalFormat formatter = _decimalFormattersMap.get(locale);

		if (formatter == null) {
			formatter = (DecimalFormat)DecimalFormat.getInstance(locale);

			formatter.setGroupingUsed(false);
			formatter.setMaximumFractionDigits(Integer.MAX_VALUE);
			formatter.setParseBigDecimal(true);

			_decimalFormattersMap.put(locale, formatter);
		}

		return formatter;
	}

	public static Map<String, String> getSymbolsMap(Locale locale) {
		DecimalFormat formatter = getNumberFormat(locale);

		DecimalFormatSymbols decimalFormatSymbols =
			formatter.getDecimalFormatSymbols();

		Map<String, String> symbolsMap = new HashMap<>();

		symbolsMap.put(
			"decimalSymbol",
			String.valueOf(decimalFormatSymbols.getDecimalSeparator()));
		symbolsMap.put(
			"thousandsSeparator",
			String.valueOf(decimalFormatSymbols.getGroupingSeparator()));

		return symbolsMap;
	}

	private static final Map<Locale, DecimalFormat> _decimalFormattersMap =
		new ConcurrentHashMap<>();

}