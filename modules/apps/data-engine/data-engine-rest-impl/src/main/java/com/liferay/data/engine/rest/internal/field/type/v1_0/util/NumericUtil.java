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

import java.text.DecimalFormat;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Gabriel Albuquerque
 */
public class NumericUtil {

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

	private static final Map<Locale, DecimalFormat> _decimalFormattersMap =
		new ConcurrentHashMap<>();

}