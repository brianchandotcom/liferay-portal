/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

import java.text.DateFormat;

import java.util.Locale;
import java.util.TimeZone;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 */
@ProviderType
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