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

/**
 * @author Brian Wing Shun Chan
 */
public class CalendarFactoryUtil_IW {
	public static CalendarFactoryUtil_IW getInstance() {
		return _instance;
	}

	public java.util.Calendar getCalendar() {
		return CalendarFactoryUtil.getCalendar();
	}

	public java.util.Calendar getCalendar(int year, int month, int date) {
		return CalendarFactoryUtil.getCalendar(year, month, date);
	}

	public java.util.Calendar getCalendar(int year, int month, int date,
		int hour, int minute) {
		return CalendarFactoryUtil.getCalendar(year, month, date, hour, minute);
	}

	public java.util.Calendar getCalendar(int year, int month, int date,
		int hour, int minute, int second) {
		return CalendarFactoryUtil.getCalendar(year, month, date, hour, minute,
			second);
	}

	public java.util.Calendar getCalendar(int year, int month, int date,
		int hour, int minute, int second, int millisecond) {
		return CalendarFactoryUtil.getCalendar(year, month, date, hour, minute,
			second, millisecond);
	}

	public java.util.Calendar getCalendar(int year, int month, int date,
		int hour, int minute, int second, int millisecond,
		java.util.TimeZone timeZone) {
		return CalendarFactoryUtil.getCalendar(year, month, date, hour, minute,
			second, millisecond, timeZone);
	}

	public java.util.Calendar getCalendar(java.util.Locale locale) {
		return CalendarFactoryUtil.getCalendar(locale);
	}

	public java.util.Calendar getCalendar(long time) {
		return CalendarFactoryUtil.getCalendar(time);
	}

	public java.util.Calendar getCalendar(long time, java.util.TimeZone timeZone) {
		return CalendarFactoryUtil.getCalendar(time, timeZone);
	}

	public java.util.Calendar getCalendar(java.util.TimeZone timeZone) {
		return CalendarFactoryUtil.getCalendar(timeZone);
	}

	public java.util.Calendar getCalendar(java.util.TimeZone timeZone,
		java.util.Locale locale) {
		return CalendarFactoryUtil.getCalendar(timeZone, locale);
	}

	private CalendarFactoryUtil_IW() {
	}

	private static CalendarFactoryUtil_IW
		_instance = new CalendarFactoryUtil_IW();
}