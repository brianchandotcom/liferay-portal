/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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