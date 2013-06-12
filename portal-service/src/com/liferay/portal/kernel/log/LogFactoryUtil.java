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

package com.liferay.portal.kernel.log;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
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
			logWrapper = new LogWrapper(_logFactory.getLog(name));

			LogWrapper previousLogWrapper = _logWrappers.putIfAbsent(
				name, logWrapper);

			if (previousLogWrapper != null) {
				logWrapper = previousLogWrapper;
			}
		}

		return logWrapper;
	}

	public static LogFactory getLogFactory() {
		PortalRuntimePermission.checkGetBeanProperty(LogFactoryUtil.class);

		return _logFactory;
	}

	public static void setLogFactory(LogFactory logFactory) {
		PortalRuntimePermission.checkSetBeanProperty(LogFactoryUtil.class);

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
		new ConcurrentHashMap<String, LogWrapper>();

}