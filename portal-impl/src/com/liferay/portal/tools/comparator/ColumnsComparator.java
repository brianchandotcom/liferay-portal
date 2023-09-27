/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.comparator;

import java.util.Comparator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class ColumnsComparator implements Comparator<Object> {

	public ColumnsComparator(List<String> columnNames) {
		this(columnNames.toArray(new String[0]));
	}

	public ColumnsComparator(String columnName) {
		this(new String[] {columnName});
	}

	public ColumnsComparator(String[] columnNames) {
		_columnNames = columnNames;
	}

	@Override
	public int compare(Object object1, Object object2) {
		Object[] column1 = (Object[])object1;
		Object[] column2 = (Object[])object2;

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

	private final String[] _columnNames;

}