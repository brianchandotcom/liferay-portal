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

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author Brian Wing Shun Chan
 * @author Marcellus Tavares
 */
public class CalendarFactoryUtil {

	public static Calendar getCalendar() {
		return getCalendarFactory().getCalendar();
	}

	public static Calendar getCalendar(int year, int month, int date) {
		return getCalendarFactory().getCalendar(year, month, date);
	}

	public static Calendar getCalendar(
		int year, int month, int date, int hour, int minute) {

		return getCalendarFactory().getCalendar(
			year, month, date, hour, minute);
	}

	public static Calendar getCalendar(
		int year, int month, int date, int hour, int minute, int second) {

		return getCalendarFactory().getCalendar(
			year, month, date, hour, minute, second);
	}

	public static Calendar getCalendar(
		int year, int month, int date, int hour, int minute, int second,
		int millisecond) {

		return getCalendarFactory().getCalendar(
			year, month, date, hour, minute, second, millisecond);
	}

	public static Calendar getCalendar(
		int year, int month, int date, int hour, int minute, int second,
		int millisecond, TimeZone timeZone) {

		return getCalendarFactory().getCalendar(
			year, month, date, hour, minute, second, millisecond, timeZone);
	}

	public static Calendar getCalendar(Locale locale) {
		return getCalendarFactory().getCalendar(locale);
	}

	public static Calendar getCalendar(long time) {
		return getCalendarFactory().getCalendar(time);
	}

	public static Calendar getCalendar(long time, TimeZone timeZone) {
		return getCalendarFactory().getCalendar(time, timeZone);
	}

	public static Calendar getCalendar(TimeZone timeZone) {
		return getCalendarFactory().getCalendar(timeZone);
	}

	public static Calendar getCalendar(TimeZone timeZone, Locale locale) {
		return getCalendarFactory().getCalendar(timeZone, locale);
	}

	public static CalendarFactory getCalendarFactory() {
		return _calendarFactory;
	}

	public void setCalendarFactory(CalendarFactory calendarFactory) {
		_calendarFactory = calendarFactory;
	}

	private static CalendarFactory _calendarFactory;

}