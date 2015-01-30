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

package com.liferay.arquillian.extension.internal.log;

import com.liferay.portal.kernel.util.StringBundler;

import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.ThrowableInformation;

import org.jboss.arquillian.core.spi.event.Event;

/**
 * @author Cristina González
 */
public class LogMessageError implements Event {

	public LogMessageError(LoggingEvent loggingEvent, Thread logThread) {
		_logThread = logThread;

		org.apache.log4j.Level level = loggingEvent.getLevel();

		Object message = loggingEvent.getMessage();

		String logMessage = getLogMessage(
			level.toString(), loggingEvent.getLoggerName(), message.toString());

		ThrowableInformation throwableInformation =
			loggingEvent.getThrowableInformation();

		Throwable throwable = null;

		if (throwableInformation != null) {
			throwable = throwableInformation.getThrowable();
		}

		_assertionError = new AssertionError(logMessage, throwable);
	}

	public LogMessageError(LogRecord logRecord, Thread logThread) {
		_logThread = logThread;

		Level level = logRecord.getLevel();

		String logMessage = getLogMessage(
			level.getName(), logRecord.getLoggerName(), logRecord.getMessage());

		_assertionError = new AssertionError(logMessage, logRecord.getThrown());
	}

	public AssertionError getAssertionError() {
		return _assertionError;
	}

	public String getLogMessage(
		String level, String loggerName, String message) {

		StringBundler sb = new StringBundler(6);

		sb.append("{level=");
		sb.append(level);
		sb.append(", loggerName=");
		sb.append(loggerName);
		sb.append(", message=");
		sb.append(message);

		return sb.toString();
	}

	public Thread getLogThread() {
		return _logThread;
	}

	private final AssertionError _assertionError;
	private final Thread _logThread;

}