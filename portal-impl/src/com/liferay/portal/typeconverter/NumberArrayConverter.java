/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.typeconverter;

import jodd.typeconverter.TypeConverter;
import jodd.typeconverter.impl.BigDecimalConverter;

import jodd.util.CsvUtil;

/**
 * @author Raymond Augé
 */
public class NumberArrayConverter implements TypeConverter<Number[]> {

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

			return new Number[] {_bigDecimalConverter.convert(value)};
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
			results[i] = _bigDecimalConverter.convert(values[i]);
		}

		return results;
	}

	private final BigDecimalConverter _bigDecimalConverter =
		new BigDecimalConverter();

}