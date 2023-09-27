/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.filter;

/**
 * @author Luca Pellizzon
 */
public class DateFDSFilterItem {

	public DateFDSFilterItem(int day, int month, int year) {
		_day = day;
		_month = month;
		_year = year;
	}

	public int getDay() {
		return _day;
	}

	public int getMonth() {
		return _month;
	}

	public int getYear() {
		return _year;
	}

	private final int _day;
	private final int _month;
	private final int _year;

}