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

package com.liferay.portal.kernel.process.log;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.process.ProcessCallable;
import com.liferay.portal.kernel.util.StringPool;

import java.io.IOException;

/**
 * @author Shuyang Zhou
 */
public class LoggingProcessCallable implements ProcessCallable<String> {

	public LoggingProcessCallable(byte[] bytes) {
		this(bytes, false);
	}

	public LoggingProcessCallable(byte[] bytes, boolean error) {
		_bytes = bytes;
		_error = error;
	}

	@Override
	public String call() {
		try {
			if (_error) {
				System.err.write(_bytes);
			}
			else {
				System.out.write(_bytes);
			}
		}
		catch (IOException ioe) {
			_log.error(
				"Unable to output log message: " + new String(_bytes), ioe);
		}

		return StringPool.BLANK;
	}

	private static final long serialVersionUID = 1L;

	private static Log _log = LogFactoryUtil.getLog(
		LoggingProcessCallable.class);

	private final byte[] _bytes;
	private final boolean _error;

}