/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.typeconverter;

import java.math.BigDecimal;

import jodd.typeconverter.TypeConversionException;
import jodd.typeconverter.TypeConverter;

/**
 * @author Raymond Augé
 */
public class NumberConverter implements TypeConverter<Number> {

	@Override
	public Number convert(Object value) {
		if (value == null) {
			return null;
		}

		if (value.getClass() == Number.class) {
			return (Number)value;
		}

		try {
			String valueString = value.toString();

			valueString = valueString.trim();

			return new BigDecimal(valueString);
		}
		catch (NumberFormatException numberFormatException) {
			throw new TypeConversionException(value, numberFormatException);
		}
	}

}