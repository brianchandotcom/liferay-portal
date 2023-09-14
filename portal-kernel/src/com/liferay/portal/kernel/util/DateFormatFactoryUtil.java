/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		return getDate(locale, null);
	}

	public static DateFormat getDate(Locale locale, TimeZone timeZone) {
		DateFormat dateFormat = DateFormat.getDateInstance(
			DateFormat.SHORT, locale);

		if (timeZone != null) {
			dateFormat.setTimeZone(timeZone);
		}

		return dateFormat;
	}

	public static DateFormat getDate(TimeZone timeZone) {
		return getDate(LocaleUtil.getDefault(), timeZone);
	}

	public static DateFormat getDateTime(Locale locale) {
		return getDateTime(locale, null);
	}

	public static DateFormat getDateTime(Locale locale, TimeZone timeZone) {
		DateFormat dateFormat = DateFormat.getDateTimeInstance(
			DateFormat.SHORT, DateFormat.SHORT, locale);

		if (timeZone != null) {
			dateFormat.setTimeZone(timeZone);
		}

		return dateFormat;
	}

	public static DateFormat getDateTime(TimeZone timeZone) {
		return getDateTime(LocaleUtil.getDefault(), timeZone);
	}

	public static DateFormat getSimpleDateFormat(String pattern) {
		return getSimpleDateFormat(pattern, LocaleUtil.getDefault(), null);
	}

	public static DateFormat getSimpleDateFormat(
		String pattern, Locale locale) {

		return getSimpleDateFormat(pattern, locale, null);
	}

	public static DateFormat getSimpleDateFormat(
		String pattern, Locale locale, TimeZone timeZone) {

		DateFormat dateFormat = new PortalSimpleDateFormat(pattern, locale);

		if (timeZone != null) {
			dateFormat.setTimeZone(timeZone);
		}

		return dateFormat;
	}

	public static DateFormat getSimpleDateFormat(
		String pattern, TimeZone timeZone) {

		return getSimpleDateFormat(pattern, LocaleUtil.getDefault(), timeZone);
	}

	public static DateFormat getTime(Locale locale) {
		return getTime(locale, null);
	}

	public static DateFormat getTime(Locale locale, TimeZone timeZone) {
		DateFormat dateFormat = DateFormat.getTimeInstance(
			DateFormat.SHORT, locale);

		if (timeZone != null) {
			dateFormat.setTimeZone(timeZone);
		}

		return dateFormat;
	}

	public static DateFormat getTime(TimeZone timeZone) {
		return getTime(LocaleUtil.getDefault(), timeZone);
	}

}