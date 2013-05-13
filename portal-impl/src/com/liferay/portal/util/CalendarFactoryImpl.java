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

package com.liferay.portal.util;

import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.util.CalendarFactory;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author Brian Wing Shun Chan
 */
@DoPrivileged
public class CalendarFactoryImpl implements CalendarFactory {

	@Override
	public Calendar getCalendar() {
		return new GregorianCalendar();
	}

	@Override
	public Calendar getCalendar(int year, int month, int date) {
		return new GregorianCalendar(year, month, date);
	}

	@Override
	public Calendar getCalendar(
		int year, int month, int date, int hour, int minute) {

		return new GregorianCalendar(year, month, date, hour, minute);
	}

	@Override
	public Calendar getCalendar(
		int year, int month, int date, int hour, int minute, int second) {

		return new GregorianCalendar(year, month, date, hour, minute, second);
	}

	@Override
	public Calendar getCalendar(Locale locale) {
		return new GregorianCalendar(locale);
	}

	@Override
	public Calendar getCalendar(TimeZone timeZone) {
		return new GregorianCalendar(timeZone);
	}

	@Override
	public Calendar getCalendar(TimeZone timeZone, Locale locale) {
		return new GregorianCalendar(timeZone, locale);
	}

}