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

package com.liferay.portal.kernel.test;

import com.liferay.portal.kernel.log.Jdk14LogImpl;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.log.LogWrapper;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Shuyang Zhou
 */
public class JDKLoggerTestUtil {

	public static CaptureHandler configureJDKLogger(String name, Level level) {
		LogWrapper logWrapper = (LogWrapper)LogFactoryUtil.getLog(name);

		Log log = logWrapper.getWrappedLog();

		if (!(log instanceof Jdk14LogImpl)) {
			throw new IllegalStateException(
				"Log " + name + " is not a JDK logger");
		}

		Jdk14LogImpl jdk14LogImpl = (Jdk14LogImpl)log;

		Logger logger = jdk14LogImpl.getWrappedLogger();

		CaptureHandler captureHandler = new CaptureHandler(logger, level);

		logger.addHandler(captureHandler);

		return captureHandler;
	}

	static {

		// See LPS-32051 and LPS-32471

		LogFactoryUtil.getLog(JDKLoggerTestUtil.class);
	}

}