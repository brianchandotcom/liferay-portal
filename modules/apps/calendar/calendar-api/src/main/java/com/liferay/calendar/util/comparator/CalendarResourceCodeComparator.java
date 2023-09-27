/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.util.comparator;

import com.liferay.calendar.model.CalendarResource;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Eduardo Lundgren
 * @author Fabio Pezzutto
 */
public class CalendarResourceCodeComparator
	extends OrderByComparator<CalendarResource> {

	public static final String ORDER_BY_ASC =
		"CalendarResource.code, CalendarResource.name ASC";

	public static final String ORDER_BY_DESC =
		"CalendarResource.code, CalendarResource.name DESC";

	public static final String[] ORDER_BY_FIELDS = {"code", "name"};

	public CalendarResourceCodeComparator() {
		this(false);
	}

	public CalendarResourceCodeComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(
		CalendarResource calendarResource1,
		CalendarResource calendarResource2) {

		String code1 = calendarResource1.getCode();
		String code2 = calendarResource2.getCode();

		int value = code1.compareTo(code2);

		if (value == 0) {
			String name1 = calendarResource1.getName();
			String name2 = calendarResource2.getName();

			value = name1.compareTo(name2);
		}

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