/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
		catch (NumberFormatException nfe) {
			throw new TypeConversionException(value, nfe);
		}
	}

}