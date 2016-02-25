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
public interface DateFormatFactory {

	public DateFormat getDate(Locale locale);

	public DateFormat getDate(Locale locale, TimeZone timeZone);

	public DateFormat getDate(TimeZone timeZone);

	public DateFormat getDateTime(Locale locale);

	public DateFormat getDateTime(Locale locale, TimeZone timeZone);

	public DateFormat getDateTime(TimeZone timeZone);

	public DateFormat getSimpleDateFormat(String pattern);

	public DateFormat getSimpleDateFormat(String pattern, Locale locale);

	public DateFormat getSimpleDateFormat(
		String pattern, Locale locale, TimeZone timeZone);

	public DateFormat getSimpleDateFormat(String pattern, TimeZone timeZone);

	public DateFormat getTime(Locale locale);

	public DateFormat getTime(Locale locale, TimeZone timeZone);

	public DateFormat getTime(TimeZone timeZone);

}