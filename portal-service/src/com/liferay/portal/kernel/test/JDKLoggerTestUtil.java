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

package com.liferay.portal.kernel.test;

import com.liferay.portal.kernel.log.Jdk14LogImpl;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.log.LogWrapper;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * @author Shuyang Zhou
 */
public class JDKLoggerTestUtil {

	public static List<LogRecord> configureJDKLogger(String name, Level level) {
		LogWrapper logWrapper = (LogWrapper)LogFactoryUtil.getLog(name);

		Log log = logWrapper.getLog();

		if (!(log instanceof Jdk14LogImpl)) {
			throw new IllegalStateException(
				"Log " + name + " is not a jdk logger");
		}

		Jdk14LogImpl jdk14LogImpl = (Jdk14LogImpl)log;

		Logger logger = jdk14LogImpl.getLog();

		for (Handler handler : logger.getHandlers()) {
			logger.removeHandler(handler);
		}

		logger.setLevel(level);
		logger.setUseParentHandlers(false);

		CaptureHandler captureHandler = new CaptureHandler();

		logger.addHandler(captureHandler);

		return captureHandler._logRecords;
	}

	private static class CaptureHandler extends Handler {

		@Override
		public void close() throws SecurityException {
			_logRecords.clear();
		}

		@Override
		public void flush() {
			_logRecords.clear();
		}

		@Override
		public boolean isLoggable(LogRecord logRecord) {
			return false;
		}

		@Override
		public void publish(LogRecord logRecord) {
			_logRecords.add(logRecord);
		}

		private List<LogRecord> _logRecords =
			new CopyOnWriteArrayList<LogRecord>();

	}

	static {

		// See LPS-32051 and LPS-32471

		LogFactoryUtil.getLog(JDKLoggerTestUtil.class);
	}

}