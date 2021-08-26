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

package com.liferay.headless.commerce.core.util;

import com.liferay.portal.kernel.util.CalendarFactoryUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author Igor Beslic
 */
public class DateConfig {

	public static DateConfig toDisplayDateConfig(Date date, TimeZone timeZone) {
		if (date == null) {
			return new DateConfig(CalendarFactoryUtil.getCalendar(timeZone));
		}

		Calendar calendar = CalendarFactoryUtil.getCalendar(
			date.getTime(), timeZone);

		return new DateConfig(calendar);
	}

	public static DateConfig toExpirationDateConfig(
		Date date, TimeZone timeZone) {

		if (date == null) {
			Calendar expirationCalendar = CalendarFactoryUtil.getCalendar(
				timeZone);

			expirationCalendar.add(Calendar.MONTH, 1);

			return new DateConfig(expirationCalendar);
		}

		Calendar calendar = CalendarFactoryUtil.getCalendar(
			date.getTime(), timeZone);

		return new DateConfig(calendar);
	}

	public DateConfig(Calendar calendar) {
		_month = calendar.get(Calendar.MONTH);
		_day = calendar.get(Calendar.DAY_OF_MONTH);
		_year = calendar.get(Calendar.YEAR);

		int hour = calendar.get(Calendar.HOUR);

		_minute = calendar.get(Calendar.MINUTE);

		int expirationDateAmPm = calendar.get(Calendar.AM_PM);

		if (expirationDateAmPm == Calendar.PM) {
			hour += 12;
		}

		_hour = hour;
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

	private final int _day;
	private final int _hour;
	private final int _minute;
	private final int _month;
	private final int _year;

}