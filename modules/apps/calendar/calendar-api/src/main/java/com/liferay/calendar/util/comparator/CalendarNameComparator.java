/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.util.comparator;

import com.liferay.calendar.model.Calendar;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Eduardo Lundgren
 * @author Fabio Pezzutto
 */
public class CalendarNameComparator extends OrderByComparator<Calendar> {

	public static final String ORDER_BY_ASC = "Calendar.name ASC";

	public static final String ORDER_BY_DESC = "Calendar.name DESC";

	public static final String[] ORDER_BY_FIELDS = {"name"};

	public CalendarNameComparator() {
		this(false);
	}

	public CalendarNameComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(Calendar calendar1, Calendar calendar2) {
		String name1 = calendar1.getName();
		String name2 = calendar2.getName();

		int value = name1.compareTo(name2);

		if (_ascending) {
			return value;
		}

		return -value;
	}

	@Override
	public String getOrderBy() {
		if (_ascending) {
			return ORDER_BY_ASC;
		}

		return ORDER_BY_DESC;
	}

	@Override
	public String[] getOrderByFields() {
		return ORDER_BY_FIELDS;
	}

	@Override
	public boolean isAscending() {
		return _ascending;
	}

	private final boolean _ascending;

}