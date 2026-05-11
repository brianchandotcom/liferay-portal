/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.filter;

/**
 * @author Mikel Lorza
 */
public class DateTimeFDSFilterItem {

	public static final DateTimeFDSFilterItem NOW = new DateTimeFDSFilterItem();

	public DateTimeFDSFilterItem(
		int day, int month, int year, int hour, int minute) {

		_day = day;
		_month = month;
		_year = year;
		_hour = hour;
		_minute = minute;

		_now = false;
	}

	public int getDay() {
		return _day;
	}

	public int getHour() {
		return _hour;
	}

	public int getMinute() {
		return _minute;
	}

	public int getMonth() {
		return _month;
	}

	public int getYear() {
		return _year;
	}

	public boolean isNow() {
		return _now;
	}

	private DateTimeFDSFilterItem() {
		_day = 0;
		_month = 0;
		_year = 0;
		_hour = 0;
		_minute = 0;
		_now = true;
	}

	private final int _day;
	private final int _hour;
	private final int _minute;
	private final int _month;
	private final boolean _now;
	private final int _year;

}