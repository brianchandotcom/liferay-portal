/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		return StringBundler.concat(
			"{level=", getPriority(), ", message=", getMessage(), "}");
	}

	private final LoggingEvent _loggingEvent;

}