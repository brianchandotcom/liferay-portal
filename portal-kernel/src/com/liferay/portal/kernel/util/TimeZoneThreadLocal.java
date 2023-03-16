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

import com.liferay.petra.lang.CentralizedThreadLocal;

import java.util.TimeZone;

/**
 * @author Brian Wing Shun Chan
 */
public class TimeZoneThreadLocal {

	public static TimeZone getDefaultTimeZone() {
		return _defaultTimeZone.get();
	}

	public static TimeZone getThemeDisplayTimeZone() {
		return _themeDisplayTimeZone.get();
	}

	public static void setDefaultTimeZone(TimeZone timeZone) {
		_defaultTimeZone.set(timeZone);
	}

	public static void setThemeDisplayTimeZone(TimeZone timeZone) {
		_themeDisplayTimeZone.set(timeZone);
	}

	private static final ThreadLocal<TimeZone> _defaultTimeZone =
		new CentralizedThreadLocal<>(
			TimeZoneThreadLocal.class + "._defaultTimeZone");
	private static final ThreadLocal<TimeZone> _themeDisplayTimeZone =
		new CentralizedThreadLocal<>(
			TimeZoneThreadLocal.class + "._themeDisplayTimeZone");

}