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

package com.liferay.analytics.settings.rest.internal.util.comparator;

import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Thiago Buarque
 */
public class IgnoreCaseOrderByComparator<T> extends OrderByComparator<T> {

	public IgnoreCaseOrderByComparator(Object... columns) {
		if ((columns.length == 0) || ((columns.length % 2) != 0)) {
			throw new IllegalArgumentException(
				"Columns length is not an even number");
		}

		_columns = columns;
	}

	@Override
	public int compare(T object1, T object2) {
		for (int i = 0; i < _columns.length; i += 2) {
			String columnName = String.valueOf(_columns[i]);

			Object columnValue1 = BeanPropertiesUtil.getObjectSilent(
				object1, columnName);
			Object columnValue2 = BeanPropertiesUtil.getObjectSilent(
				object2, columnName);

			int value;

			if ((columnValue1 instanceof String) &&
				(columnValue2 instanceof String)) {

				String columnValue1String = (String)columnValue1;

				value = columnValue1String.compareToIgnoreCase(
					(String)columnValue2);
			}
			else {
				Comparable<Object> columnValueComparable1 =
					(Comparable<Object>)columnValue1;
				Comparable<Object> columnValueComparable2 =
					(Comparable<Object>)columnValue2;

				value = columnValueComparable1.compareTo(
					columnValueComparable2);
			}

			if (value == 0) {
				continue;
			}

			boolean columnAscending = Boolean.valueOf(
				String.valueOf(_columns[i + 1]));

			if (columnAscending) {
				return value;
			}

			return -value;
		}

		return 0;
	}

	private final Object[] _columns;

}