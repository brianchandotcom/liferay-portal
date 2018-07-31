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

import java.text.DateFormat;

import java.util.Locale;
import java.util.TimeZone;

/**
 * @author Brian Wing Shun Chan
 */
public class DateFormatFactoryUtil {

	public static DateFormat getDate(Locale locale) {
		return getDateFormatFactory().getDate(locale);
	}

	public static DateFormat getDate(Locale locale, TimeZone timeZone) {
		return getDateFormatFactory().getDate(locale, timeZone);
	}

	public static DateFormat getDate(TimeZone timeZone) {
		return getDateFormatFactory().getDate(timeZone);
	}

	public static DateFormatFactory getDateFormatFactory() {
		return _fastDateFormatFactory;
	}

	public static DateFormat getDateTime(Locale locale) {
		return getDateFormatFactory().getDateTime(locale);
	}

	public static DateFormat getDateTime(Locale locale, TimeZone timeZone) {
		return getDateFormatFactory().getDateTime(locale, timeZone);
	}

	public static DateFormat getDateTime(TimeZone timeZone) {
		return getDateFormatFactory().getDateTime(timeZone);
	}

	public static DateFormat getSimpleDateFormat(String pattern) {
		return getDateFormatFactory().getSimpleDateFormat(pattern);
	}

	public static DateFormat getSimpleDateFormat(
		String pattern, Locale locale) {

		return getDateFormatFactory().getSimpleDateFormat(pattern, locale);
	}

	public static DateFormat getSimpleDateFormat(
		String pattern, Locale locale, TimeZone timeZone) {

		return getDateFormatFactory().getSimpleDateFormat(
			pattern, locale, timeZone);
	}

	public static DateFormat getSimpleDateFormat(
		String pattern, TimeZone timeZone) {

		return getDateFormatFactory().getSimpleDateFormat(pattern, timeZone);
	}

	public static DateFormat getTime(Locale locale) {
		return getDateFormatFactory().getTime(locale);
	}

	public static DateFormat getTime(Locale locale, TimeZone timeZone) {
		return getDateFormatFactory().getTime(locale, timeZone);
	}

	public static DateFormat getTime(TimeZone timeZone) {
		return getDateFormatFactory().getTime(timeZone);
	}

	public void setDateFormatFactory(DateFormatFactory fastDateFormatFactory) {
		_fastDateFormatFactory = fastDateFormatFactory;
	}

	private static DateFormatFactory _fastDateFormatFactory;

}