/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.log;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 * @author Raymond Augé
 */
public class LogFactoryUtil {

	public static Log getLog(Class<?> c) {
		return getLog(c.getName());
	}

	public static Log getLog(String name) {

		// The following concurrent collection retrieve has the side effect of a
		// memory fence read. This will invalidate all dirty cache data if there
		// are any. If the LogWrapper swap happens before this, the new Log will
		// be visible to the current Thread.

		LogWrapper logWrapper = _logWrappers.get(name);

		if (logWrapper == null) {
			Log log = _logFactory.getLog(name);

			if (SanitizerLogWrapper.isEnabled()) {
				logWrapper = new SanitizerLogWrapper(log);
			}
			else if (log instanceof LogWrapper) {
				logWrapper = (LogWrapper)log;
			}
			else {
				logWrapper = new LogWrapper(_logFactory.getLog(name));
			}

			LogWrapper previousLogWrapper = _logWrappers.putIfAbsent(
				name, logWrapper);

			if (previousLogWrapper != null) {
				logWrapper = previousLogWrapper;
			}
		}

		return logWrapper;
	}

	public static LogFactory getLogFactory() {
		return _logFactory;
	}

	public static void setLogFactory(LogFactory logFactory) {
		for (Map.Entry<String, LogWrapper> entry : _logWrappers.entrySet()) {
			String name = entry.getKey();

			LogWrapper logWrapper = entry.getValue();

			logWrapper.setLog(logFactory.getLog(name));
		}

		// The following volatile write will flush out all cache data. All
		// previously swapped LogWrappers will be visible for any reads after a
		// memory fence read according to the happens-before rules.

		_logFactory = logFactory;
	}

	private static volatile LogFactory _logFactory = new Jdk14LogFactoryImpl();
	private static final ConcurrentMap<String, LogWrapper> _logWrappers =
		new ConcurrentHashMap<>();

}