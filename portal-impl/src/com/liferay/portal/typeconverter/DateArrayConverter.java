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

package com.liferay.portal.typeconverter;

import java.util.Date;

import jodd.typeconverter.ConvertBean;
import jodd.typeconverter.TypeConverter;

import jodd.util.CsvUtil;

/**
 * @author Raymond Augé
 */
public class DateArrayConverter implements TypeConverter<Date[]> {

	public DateArrayConverter(ConvertBean convertBean) {
		_convertBean = convertBean;
	}

	@Override
	public Date[] convert(Object value) {
		if (value == null) {
			return null;
		}

		Class<?> type = value.getClass();

		if (type.isArray() == false) {
			if (type == String.class) {
				String[] values = CsvUtil.toStringArray(value.toString());

				return convertArray(values);
			}

			return new Date[] {_convertBean.toDate(value)};
		}

		Class<?> componentType = type.getComponentType();

		if (componentType.isPrimitive() && (type == long[].class)) {
			long[] values = (long[])value;

			Date[] results = new Date[values.length];

			for (int i = 0; i < values.length; i++) {
				results[i] = _convertBean.toDate(values[i]);
			}

			return results;
		}

		return convertArray((Object[])value);
	}

	protected Date[] convertArray(Object[] values) {
		Date[] results = new Date[values.length];

		for (int i = 0; i < values.length; i++) {
			results[i] = _convertBean.toDate(values[i]);
		}

		return results;
	}

	private final ConvertBean _convertBean;

}