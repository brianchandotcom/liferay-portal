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

import com.liferay.petra.string.StringPool;

import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class TimeZoneUtil {

	public static final TimeZone GMT = TimeZone.getTimeZone("GMT");

	public static TimeZone getDefault() {
		TimeZone timeZone = TimeZoneThreadLocal.getDefaultTimeZone();

		if (timeZone != null) {
			return timeZone;
		}

		return _timeZone;
	}

	public static TimeZoneUtil getInstance() {
		return new TimeZoneUtil();
	}

	public static TimeZone getTimeZone(String timeZoneId) {
		TimeZone timeZone = _timeZones.get(timeZoneId);

		if (timeZone == null) {
			timeZone = TimeZone.getTimeZone(timeZoneId);

			_timeZones.put(timeZoneId, timeZone);
		}

		return timeZone;
	}

	public static void setDefault(String timeZoneId) {
		if (Validator.isNotNull(timeZoneId)) {
			_timeZone = TimeZone.getTimeZone(timeZoneId);
		}
	}

	private TimeZoneUtil() {
	}

	private static volatile TimeZone _timeZone = TimeZone.getTimeZone(
		StringPool.UTC);
	private static final Map<String, TimeZone> _timeZones =
		new ConcurrentHashMap<>();

}