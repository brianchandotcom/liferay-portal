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

import com.liferay.petra.string.StringBundler;

import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.ThrowableInformation;

/**
 * @author Tina Tian
 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
 *              LoggerTestUtil.Log4JLogEntry}
 */
@Deprecated
public class LogEvent {

	public LogEvent(LoggingEvent loggingEvent) {
		_loggingEvent = loggingEvent;
	}

	public String getMessage() {
		return _loggingEvent.getRenderedMessage();
	}

	public String getPriority() {
		return String.valueOf(_loggingEvent.getLevel());
	}

	public Throwable getThrowable() {
		ThrowableInformation throwableInformation =
			_loggingEvent.getThrowableInformation();

		if (throwableInformation != null) {
			return throwableInformation.getThrowable();
		}

		return null;
	}

	public Object getWrappedObject() {
		return _loggingEvent;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(5);

		sb.append("{level=");
		sb.append(getPriority());
		sb.append(", message=");
		sb.append(getMessage());
		sb.append("}");

		return sb.toString();
	}

	private final LoggingEvent _loggingEvent;

}