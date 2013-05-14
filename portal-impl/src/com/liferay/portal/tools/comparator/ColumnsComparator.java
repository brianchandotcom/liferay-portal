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

package com.liferay.portal.tools.comparator;

import java.util.Comparator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class ColumnsComparator implements Comparator<Object> {

	public ColumnsComparator(List<String> columnNames) {
		this(columnNames.toArray(new String[columnNames.size()]));
	}

	public ColumnsComparator(String columnName) {
		this(new String[] {columnName});
	}

	public ColumnsComparator(String[] columnNames) {
		_columnNames = columnNames;
	}

	@Override
	public int compare(Object obj1, Object obj2) {
		Object[] column1 = (Object[])obj1;
		Object[] column2 = (Object[])obj2;

		String columnName1 = (String)column1[0];
		String columnName2 = (String)column2[0];

		int x = -1;

		for (int i = 0; i < _columnNames.length; i++) {
			if (_columnNames[i].equals(columnName1)) {
				x = i;

				break;
			}
		}

		int y = -1;

		for (int i = 0; i < _columnNames.length; i++) {
			if (_columnNames[i].equals(columnName2)) {
				y = i;

				break;
			}
		}

		if ((x == -1) && (y > -1)) {
			return 1;
		}
		else if ((x > -1) && (y == -1)) {
			return -1;
		}
		else if ((x > -1) && (y > -1)) {
			if (x < y) {
				return -1;
			}
			else if (x > y) {
				return 1;
			}
		}

		return 0;
	}

	private String[] _columnNames;

}