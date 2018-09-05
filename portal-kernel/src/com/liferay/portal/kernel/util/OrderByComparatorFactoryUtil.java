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

package com.liferay.portal.kernel.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.model.BaseModel;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class OrderByComparatorFactoryUtil {

	public static <T extends BaseModel<T>> OrderByComparator<T> create(
		String tableName, Object... columns) {

		if ((columns.length == 0) || ((columns.length % 2) != 0)) {
			throw new IllegalArgumentException(
				"Columns length is not an even number");
		}

		return new DefaultOrderByComparator<>(tableName, columns);
	}

	protected static class DefaultOrderByComparator<T extends BaseModel<T>>
		extends OrderByComparator<T> {

		@Override
		public int compare(T object1, T object2) {
			for (int i = 0; i < _columns.length; i += 2) {
				String columnName = String.valueOf(_columns[i]);
				boolean columnAscending = Boolean.valueOf(
					String.valueOf(_columns[i + 1]));

				Object columnInstance = null;

				Class<?> columnClass = BeanPropertiesUtil.getObjectTypeSilent(
					object1, columnName);

				if (columnClass.isPrimitive()) {
					columnInstance = _primitiveObjects.get(columnClass);
				}
				else {
					try {
						columnInstance = columnClass.newInstance();
					}
					catch (Exception e) {
					}
				}

				Object columnValue1 = BeanPropertiesUtil.getObjectSilent(
					object1, columnName);
				Object columnValue2 = BeanPropertiesUtil.getObjectSilent(
					object2, columnName);

				if (columnInstance instanceof Date) {
					Date columnValueDate1 = (Date)columnValue1;
					Date columnValueDate2 = (Date)columnValue2;

					int value = DateUtil.compareTo(
						columnValueDate1, columnValueDate2);

					if (value == 0) {
						continue;
					}

					if (columnAscending) {
						return value;
					}

					return -value;
				}
				else if (columnInstance instanceof Comparable<?>) {
					Comparable<Object> columnValueComparable1 =
						(Comparable<Object>)columnValue1;
					Comparable<Object> columnValueComparable2 =
						(Comparable<Object>)columnValue2;

					int value = columnValueComparable1.compareTo(
						columnValueComparable2);

					if (value == 0) {
						continue;
					}

					if (columnAscending) {
						return value;
					}

					return -value;
				}
			}

			return 0;
		}

		@Override
		public String getOrderBy() {
			StringBundler sb = new StringBundler(5 * _columns.length - 1);

			for (int i = 0; i < _columns.length; i += 2) {
				if (i != 0) {
					sb.append(StringPool.COMMA);
				}

				sb.append(_tableName);
				sb.append(StringPool.PERIOD);

				String columnName = String.valueOf(_columns[i]);
				boolean columnAscending = Boolean.valueOf(
					String.valueOf(_columns[i + 1]));

				sb.append(columnName);

				if (columnAscending) {
					sb.append(_ORDER_BY_ASC);
				}
				else {
					sb.append(_ORDER_BY_DESC);
				}
			}

			return sb.toString();
		}

		@Override
		public boolean isAscending(String field) {
			String orderBy = getOrderBy();

			if (orderBy == null) {
				return false;
			}

			int x = orderBy.indexOf(
				StringPool.PERIOD + field + StringPool.SPACE);

			if (x == -1) {
				return false;
			}

			int y = orderBy.indexOf(_ORDER_BY_ASC, x);

			if (y == -1) {
				return false;
			}

			int z = orderBy.indexOf(_ORDER_BY_DESC, x);

			if ((z >= 0) && (z < y)) {
				return false;
			}

			return true;
		}

		private DefaultOrderByComparator(String tableName, Object... columns) {
			_tableName = tableName;
			_columns = columns;
		}

		private static final String _ORDER_BY_ASC = " ASC";

		private static final String _ORDER_BY_DESC = " DESC";

		private static final Map<Class<?>, Object> _primitiveObjects =
			new HashMap<Class<?>, Object>() {
				{
					put(boolean.class, Boolean.TRUE);
					put(byte.class, Byte.valueOf("0"));
					put(char.class, Character.valueOf('0'));
					put(double.class, Double.valueOf(0));
					put(float.class, Float.valueOf(0));
					put(int.class, Integer.valueOf(0));
					put(long.class, Long.valueOf(0));
					put(short.class, Short.valueOf("0"));
				}
			};

		private final Object[] _columns;
		private final String _tableName;

	}

}