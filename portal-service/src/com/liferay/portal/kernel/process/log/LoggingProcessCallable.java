/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.process.log;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.process.ProcessCallable;
import com.liferay.portal.kernel.process.ProcessException;
import com.liferay.portal.kernel.util.StringPool;

import java.io.IOException;

/**
 * @author Shuyang Zhou
 */
public class LoggingProcessCallable implements ProcessCallable<String> {

	public LoggingProcessCallable(byte[] logData) {
		this(logData, false);
	}

	public LoggingProcessCallable(byte[] logData, boolean errorLog) {
		_errorLog = errorLog;
		_logData = logData;
	}

	public String call() throws ProcessException {
		try {
			if (_errorLog) {
				System.err.write(_logData);
			}
			else {
				System.out.write(_logData);
			}
		}
		catch (IOException ioe) {
			_log.error("Failed to output log message : " + new String(_logData),
				ioe);
		}

		return StringPool.BLANK;
	}

	private static Log _log = LogFactoryUtil.getLog(
		LoggingProcessCallable.class);

	private final boolean _errorLog;
	private final byte[] _logData;

}