/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.html.parser.internal;

import com.liferay.petra.reflect.ReflectionUtil;

import java.lang.reflect.Method;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.htmlparser.jericho.Config;
import net.htmlparser.jericho.Logger;
import net.htmlparser.jericho.LoggerProvider;

/**
 * @author Shuyang Zhou
 */
public class CachedLoggerProvider implements LoggerProvider {

	public static void install() throws Exception {
		Class<?> clazz = Class.forName("net.htmlparser.jericho.LoggerFactory");

		Method method = ReflectionUtil.getDeclaredMethod(
			clazz, "getDefaultLoggerProvider");

		LoggerProvider loggerProvider = (LoggerProvider)method.invoke(null);

		CachedLoggerProvider cachedLoggerProvider = new CachedLoggerProvider(
			loggerProvider);

		Config.LoggerProvider = cachedLoggerProvider;
	}

	public CachedLoggerProvider(LoggerProvider loggerProvider) {
		_loggerProvider = loggerProvider;
	}

	@Override
	public Logger getLogger(String name) {
		Logger logger = _loggers.get(name);

		if (logger == null) {
			logger = _loggerProvider.getLogger(name);

			_loggers.put(name, logger);
		}

		return logger;
	}

	@Override
	public Logger getSourceLogger() {
		return getLogger("net.htmlparser.jericho");
	}

	private final LoggerProvider _loggerProvider;
	private final Map<String, Logger> _loggers = new ConcurrentHashMap<>();

}