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

package com.liferay.portal.test.log;

import com.liferay.portal.kernel.util.StringPool;

import java.util.logging.Logger;

/**
 * @author William Newbury
 */
public class LogAssertionUtil {

	public static void enableLogAssertion() {
		installJdk14Handler();
		installLog4jAppender();
	}

	protected static void installJdk14Handler() {
		Logger logger = Logger.getLogger(StringPool.BLANK);

		logger.removeHandler(LogAssertionHandler.INSTANCE);

		logger.addHandler(LogAssertionHandler.INSTANCE);
	}

	protected static void installLog4jAppender() {
		org.apache.log4j.Logger logger =
			org.apache.log4j.Logger.getRootLogger();

		logger.removeAppender(LogAssertionAppender.INSTANCE);

		logger.addAppender(LogAssertionAppender.INSTANCE);
	}

}