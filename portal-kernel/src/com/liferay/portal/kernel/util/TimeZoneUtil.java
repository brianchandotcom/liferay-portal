/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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