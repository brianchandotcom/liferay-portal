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

package com.liferay.osgi.log.service.extender.internal.osgi.commands;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.log4j.Log4JUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.log.LogLevel;
import org.osgi.service.log.admin.LoggerAdmin;
import org.osgi.service.log.admin.LoggerContext;

/**
 * @author Raymond Augé
 */
public class LoggingLevelsOSGiCommands {

	public LoggingLevelsOSGiCommands(LoggerAdmin loggerAdmin) {
		_loggerAdmin = loggerAdmin;
	}

	public String level(String context, String name, String level) {
		Objects.requireNonNull(name);

		LoggerContext loggerContext = _loggerAdmin.getLoggerContext(context);

		Map<String, LogLevel> logLevels = loggerContext.getLogLevels();

		LogLevel logLevel = LogLevel.ERROR;

		try {
			if (level == null) {
				logLevels.remove(name);
			}
			else {
				logLevel = LogLevel.valueOf(StringUtil.toUpperCase(level));

				logLevels.put(name, logLevel);
			}

			Log4JUtil.setLevel(
				"osgi.logging.".concat(name), logLevel.name(), false);

			loggerContext.setLogLevels(logLevels);

			return StringBundler.concat(
				name, StringPool.EQUAL, logLevel.name());
		}
		catch (IllegalArgumentException illegalArgumentException) {
			return "Invalid log level: " + level;
		}
	}

	public String[] levels(String context) {
		LoggerContext loggerContext = _loggerAdmin.getLoggerContext(context);

		List<String> categories = new ArrayList<>();

		Map<String, LogLevel> logLevels = loggerContext.getLogLevels();

		for (Map.Entry<String, LogLevel> entry : logLevels.entrySet()) {
			LogLevel logLevel = entry.getValue();

			categories.add(
				StringBundler.concat(
					entry.getKey(), StringPool.EQUAL, logLevel.name()));
		}

		return categories.toArray(new String[0]);
	}

	private final LoggerAdmin _loggerAdmin;

}