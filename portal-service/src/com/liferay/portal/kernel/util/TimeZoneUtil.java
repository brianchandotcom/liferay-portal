/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

/**
 * @author Brian Wing Shun Chan
 */
public class TimeZoneUtil {

	public static TimeZone getDefault() {
		return _instance._getDefault();
	}

	public static TimeZone getTimeZone(String timeZoneId) {
		return _instance._getTimeZone(timeZoneId);
	}

	public static void setDefault(String timeZoneId) {
		_instance._setDefault(timeZoneId);
	}

	private TimeZoneUtil() {
		_timeZone = _getTimeZone(StringPool.UTC);
	}

	private TimeZone _getDefault() {
		TimeZone timeZone = TimeZoneThreadLocal.getDefaultTimeZone();

		if (timeZone != null) {
			return timeZone;
		}

		return _timeZone;
	}

	private TimeZone _getTimeZone(String timeZoneId) {
		TimeZone timeZone = _timeZones.get(timeZoneId);

		if (timeZone == null) {
			timeZone = TimeZone.getTimeZone(timeZoneId);

			_timeZones.put(timeZoneId, timeZone);
		}

		return timeZone;
	}

	private void _setDefault(String timeZoneId) {
		if (Validator.isNotNull(timeZoneId)) {
			_timeZone = TimeZone.getTimeZone(timeZoneId);
		}
	}

	private static TimeZoneUtil _instance = new TimeZoneUtil();

	private TimeZone _timeZone;
	private Map<String, TimeZone> _timeZones = new HashMap<String, TimeZone>();

}