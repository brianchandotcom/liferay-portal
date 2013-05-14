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
import com.liferay.portal.kernel.util.FastDateFormatConstants;
import com.liferay.portal.kernel.util.FastDateFormatFactory;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.text.Format;

import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.time.FastDateFormat;

/**
 * @author Brian Wing Shun Chan
 */
@DoPrivileged
public class FastDateFormatFactoryImpl implements FastDateFormatFactory {

	@Override
	public Format getDate(int style, Locale locale, TimeZone timeZone) {
		String key = getKey(style, locale, timeZone);

		Format format = _dateFormats.get(key);

		if (format == null) {
			format = FastDateFormat.getDateInstance(style, timeZone, locale);

			_dateFormats.put(key, format);
		}

		return format;
	}

	@Override
	public Format getDate(Locale locale) {
		return getDate(locale, null);
	}

	@Override
	public Format getDate(Locale locale, TimeZone timeZone) {
		return getDate(FastDateFormatConstants.SHORT, locale, timeZone);
	}

	@Override
	public Format getDate(TimeZone timeZone) {
		return getDate(LocaleUtil.getDefault(), timeZone);
	}

	@Override
	public Format getDateTime(
		int dateStyle, int timeStyle, Locale locale, TimeZone timeZone) {

		String key = getKey(dateStyle, timeStyle, locale, timeZone);

		Format format = _dateTimeFormats.get(key);

		if (format == null) {
			format = FastDateFormat.getDateTimeInstance(
				dateStyle, timeStyle, timeZone, locale);

			_dateTimeFormats.put(key, format);
		}

		return format;
	}

	@Override
	public Format getDateTime(Locale locale) {
		return getDateTime(locale, null);
	}

	@Override
	public Format getDateTime(Locale locale, TimeZone timeZone) {
		return getDateTime(
			FastDateFormatConstants.SHORT, FastDateFormatConstants.SHORT,
			locale, timeZone);
	}

	@Override
	public Format getDateTime(TimeZone timeZone) {
		return getDateTime(LocaleUtil.getDefault(), timeZone);
	}

	@Override
	public Format getSimpleDateFormat(String pattern) {
		return getSimpleDateFormat(pattern, LocaleUtil.getDefault(), null);
	}

	@Override
	public Format getSimpleDateFormat(String pattern, Locale locale) {
		return getSimpleDateFormat(pattern, locale, null);
	}

	@Override
	public Format getSimpleDateFormat(
		String pattern, Locale locale, TimeZone timeZone) {

		String key = getKey(pattern, locale, timeZone);

		Format format = _simpleDateFormats.get(key);

		if (format == null) {
			format = FastDateFormat.getInstance(pattern, timeZone, locale);

			_simpleDateFormats.put(key, format);
		}

		return format;
	}

	@Override
	public Format getSimpleDateFormat(String pattern, TimeZone timeZone) {
		return getSimpleDateFormat(pattern, LocaleUtil.getDefault(), timeZone);
	}

	@Override
	public Format getTime(int style, Locale locale, TimeZone timeZone) {
		String key = getKey(style, locale, timeZone);

		Format format = _timeFormats.get(key);

		if (format == null) {
			format = FastDateFormat.getTimeInstance(style, timeZone, locale);

			_timeFormats.put(key, format);
		}

		return format;
	}

	@Override
	public Format getTime(Locale locale) {
		return getTime(locale, null);
	}

	@Override
	public Format getTime(Locale locale, TimeZone timeZone) {
		return getTime(FastDateFormatConstants.SHORT, locale, timeZone);
	}

	@Override
	public Format getTime(TimeZone timeZone) {
		return getTime(LocaleUtil.getDefault(), timeZone);
	}

	protected String getKey(Object... arguments) {
		StringBundler sb = new StringBundler(arguments.length * 2 - 1);

		for (int i = 0; i < arguments.length; i++) {
			sb.append(arguments[i]);

			if ((i + 1) < arguments.length) {
				sb.append(StringPool.UNDERLINE);
			}
		}

		return sb.toString();
	}

	private Map<String, Format> _dateFormats =
		new ConcurrentHashMap<String, Format>();
	private Map<String, Format> _dateTimeFormats =
		new ConcurrentHashMap<String, Format>();
	private Map<String, Format> _simpleDateFormats =
		new ConcurrentHashMap<String, Format>();
	private Map<String, Format> _timeFormats =
		new ConcurrentHashMap<String, Format>();

}