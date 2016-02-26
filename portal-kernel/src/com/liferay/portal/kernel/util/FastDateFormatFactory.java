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

import java.text.Format;

import java.util.Locale;
import java.util.TimeZone;

/**
 * @author Brian Wing Shun Chan
 */
public interface FastDateFormatFactory {

	public Format getDate(int style, Locale locale, TimeZone timeZone);

	public Format getDate(Locale locale);

	public Format getDate(Locale locale, TimeZone timeZone);

	public Format getDate(TimeZone timeZone);

	public Format getDateTime(
		int dateStyle, int timeStyle, Locale locale, TimeZone timeZone);

	public Format getDateTime(Locale locale);

	public Format getDateTime(Locale locale, TimeZone timeZone);

	public Format getDateTime(TimeZone timeZone);

	public Format getSimpleDateFormat(String pattern);

	public Format getSimpleDateFormat(String pattern, Locale locale);

	public Format getSimpleDateFormat(
		String pattern, Locale locale, TimeZone timeZone);

	public Format getSimpleDateFormat(String pattern, TimeZone timeZone);

	public Format getTime(int style, Locale locale, TimeZone timeZone);

	public Format getTime(Locale locale);

	public Format getTime(Locale locale, TimeZone timeZone);

	public Format getTime(TimeZone timeZone);

}