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

package com.liferay.portal.test;

import com.liferay.portal.kernel.test.LogAssertionAppender;
import com.liferay.portal.kernel.test.LogAssertionHandler;

import java.util.logging.Handler;

import org.apache.log4j.Appender;

/**
 * @author William Newbury
 */
public class LogAssertionUtil {

	public static void installJdk14Handler() {
		java.util.logging.Logger jdkLogger = java.util.logging.Logger.getLogger(
			"");

		Handler[] handlers = jdkLogger.getHandlers();

		Boolean needsInstalation = true;

		for (Handler handler : handlers) {
			if (handler instanceof LogAssertionHandler) {
				needsInstalation = true;
			}
		}

		if (needsInstalation) {
			LogAssertionHandler logAssertionHandler = new LogAssertionHandler(
				jdkLogger);

			jdkLogger.addHandler(logAssertionHandler);
		}
	}

	public static void installLog4jAppender() {
		org.apache.log4j.Logger log4jLogger =
			org.apache.log4j.Logger.getRootLogger();

		Appender appender = log4jLogger.getAppender("logAssertionAppender");

		if (appender == null) {
			LogAssertionAppender logAssertionAppender =
				new LogAssertionAppender(log4jLogger);

			logAssertionAppender.setName("logAssertionAppender");

			log4jLogger.addAppender(logAssertionAppender);
		}
	}

}