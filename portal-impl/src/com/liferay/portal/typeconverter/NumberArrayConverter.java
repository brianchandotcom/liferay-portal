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

import jodd.typeconverter.ConvertBean;
import jodd.typeconverter.TypeConverter;

import jodd.util.CsvUtil;

/**
 * @author Raymond Augé
 */
public class NumberArrayConverter implements TypeConverter<Number[]> {

	public NumberArrayConverter(ConvertBean convertBean) {
		this.convertBean = convertBean;
	}

	@Override
	public Number[] convert(Object value) {
		if (value == null) {
			return null;
		}

		Class<?> type = value.getClass();

		if (type.isArray() == false) {
			if (type == String.class) {
				String[] values = CsvUtil.toStringArray(value.toString());

				return convertArray(values);
			}

			return new Number[] {convertBean.toBigDecimal(value)};
		}

		Class<?> componentType = type.getComponentType();

		if (componentType.isPrimitive()) {
			if (type == boolean[].class) {
				boolean[] values = (boolean[])value;

				Number[] results = new Number[values.length];

				for (int i = 0; i < values.length; i++) {
					results[i] = (values[i] == true) ? 1 : 0;
				}

				return results;
			}
			else if (type == byte[].class) {
				byte[] values = (byte[])value;

				Number[] results = new Number[values.length];

				for (int i = 0; i < values.length; i++) {
					results[i] = values[i];
				}

				return results;
			}
			else if (type == double[].class) {
				double[] values = (double[])value;

				Number[] results = new Number[values.length];

				for (int i = 0; i < values.length; i++) {
					results[i] = values[i];
				}

				return results;
			}
			else if (type == float[].class) {
				float[] values = (float[])value;

				Number[] results = new Number[values.length];

				for (int i = 0; i < values.length; i++) {
					results[i] = values[i];
				}

				return results;
			}
			else if (type == int[].class) {
				int[] values = (int[])value;

				Number[] results = new Number[values.length];

				for (int i = 0; i < values.length; i++) {
					results[i] = values[i];
				}

				return results;
			}
			else if (type == long[].class) {
				long[] values = (long[])value;

				Number[] results = new Number[values.length];

				for (int i = 0; i < values.length; i++) {
					results[i] = values[i];
				}

				return results;
			}
			else if (type == short[].class) {
				short[] values = (short[])value;

				Number[] results = new Number[values.length];

				for (int i = 0; i < values.length; i++) {
					results[i] = values[i];
				}

				return results;
			}
		}

		return convertArray((Object[])value);
	}

	protected Number[] convertArray(Object[] values) {
		Number[] results = new Number[values.length];

		for (int i = 0; i < values.length; i++) {
			results[i] = convertBean.toBigDecimal(values[i]);
		}

		return results;
	}

	protected ConvertBean convertBean;

}