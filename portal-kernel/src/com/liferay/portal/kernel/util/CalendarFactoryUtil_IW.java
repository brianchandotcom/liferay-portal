/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	private static CalendarFactoryUtil_IW _instance = new CalendarFactoryUtil_IW();
}