/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.bean.BeanPropertiesUtil;

import java.util.Date;

/**
 * @author Brian Wing Shun Chan
 */
public class OrderByComparatorFactoryUtil {

	public static OrderByComparator create(
		String tableName, Object... columns) {

		return new DefaultOrderByComparator(tableName, columns);
	}

	private static class DefaultOrderByComparator extends OrderByComparator {

		@Override
		public int compare(Object object1, Object object2) {
			for (int i = 0; i < _columns.length; i += 2) {
				String columnName = String.valueOf(_columns[i]);
				boolean columnAscending = Boolean.valueOf(
					String.valueOf(_columns[i + 1]));

				Class<?> clazzColumnType =
					BeanPropertiesUtil.getObjectTypeSilent(object1, columnName);

				Object object1ColumnType = null;

				try {
					object1ColumnType = clazzColumnType.newInstance();
				}
				catch (Exception e) {
				}

				Object object1ColumnValue = BeanPropertiesUtil.getObjectSilent(
					object1, columnName);
				Object object2ColumnValue = BeanPropertiesUtil.getObjectSilent(
					object2, columnName);

				if (object1ColumnType instanceof Date) {
					Date object1Column1ValueDate = (Date)object1ColumnValue;
					Date object2Column1ValueDate = (Date)object2ColumnValue;

					int value = DateUtil.compareTo(
						object1Column1ValueDate, object2Column1ValueDate);

					if (value == 0) {
						continue;
					}

					if (columnAscending) {
						return value;
					}
					else {
						return -value;
					}
				}
				else if (object1ColumnType instanceof Comparable<?>) {
					Comparable<Object> object1Column1ValueComparable =
						(Comparable<Object>)object1ColumnValue;
					Comparable<Object> object2Column1ValueComparable =
						(Comparable<Object>)object2ColumnValue;

					int value = object1Column1ValueComparable.compareTo(
						object2Column1ValueComparable);

					if (value == 0) {
						continue;
					}

					if (columnAscending) {
						return value;
					}
					else {
						return -value;
					}
				}
			}

			return 0;
		}

		@Override
		public String getOrderBy() {
			StringBundler sb = new StringBundler();

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

			int index = orderBy.indexOf(
				StringPool.PERIOD + field + StringPool.SPACE);

			if (index == -1) {
				return false;
			}

			int x = orderBy.indexOf(_ORDER_BY_ASC, index);

			if (x == -1) {
				return false;
			}

			int y = orderBy.indexOf(_ORDER_BY_DESC, index);

			if ((y >= 0) && (y < x)) {
				return false;
			}
			else {
				return true;
			}
		}

		private DefaultOrderByComparator(String tableName, Object... columns) {
			_tableName = tableName;
			_columns = columns;

			if ((_columns.length == 0) || ((_columns.length % 2) != 0)) {
				throw new IllegalArgumentException(
					"Columns length is not an even number");
			}
		}

		private static final String _ORDER_BY_ASC = " ASC";
		private static final String _ORDER_BY_DESC = " DESC";

		private Object[] _columns;
		private String _tableName;

	}

}