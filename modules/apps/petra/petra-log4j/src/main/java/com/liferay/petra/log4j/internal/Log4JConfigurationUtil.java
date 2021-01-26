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

package com.liferay.petra.log4j.internal;

import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;

import java.util.Enumeration;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerRepository;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * @author Hai Yu
 */
public class Log4JConfigurationUtil {

	public static void configureLog4JXml(String xml) {
		DOMConfigurator domConfigurator = new DOMConfigurator();

		domConfigurator.doConfigure(
			new UnsyncStringReader(xml), LogManager.getLoggerRepository());
	}

	public static String getOriginalLevel(String className) {
		Level level = Level.ALL;

		Enumeration<Logger> enumeration = LogManager.getCurrentLoggers();

		while (enumeration.hasMoreElements()) {
			Logger logger = enumeration.nextElement();

			if (className.equals(logger.getName())) {
				level = logger.getLevel();

				break;
			}
		}

		return level.toString();
	}

	public static void setLevel(String name, String priority) {
		Logger logger = Logger.getLogger(name);

		logger.setLevel(Level.toLevel(priority));
	}

	public static void shutdownLog4J() {
		LoggerRepository loggerRepository = LogManager.getLoggerRepository();

		loggerRepository.shutdown();
	}

}