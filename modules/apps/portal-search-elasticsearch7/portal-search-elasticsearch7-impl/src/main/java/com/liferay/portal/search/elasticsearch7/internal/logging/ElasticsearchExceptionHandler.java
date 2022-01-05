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

package com.liferay.portal.search.elasticsearch7.internal.logging;

import com.liferay.portal.kernel.log.Log;

/**
 * @author Adam Brandizzi
 */
public class ElasticsearchExceptionHandler {

	public ElasticsearchExceptionHandler(Log log, boolean logExceptionsOnly) {
		_log = log;
		_logExceptionsOnly = logExceptionsOnly;
	}

	public <T extends Throwable> void handleDeleteDocumentException(T t)
		throws T {

		if (_isIndexNotFound(t)) {
			if (_log.isInfoEnabled()) {
				_log.info(t, t);
			}
		}
		else {
			logOrThrow(t);
		}
	}

	public <T extends Throwable> void logOrThrow(T t) throws T {
		if (_logExceptionsOnly) {
			_log.error(t, t);
		}
		else if (_logExceptionsOnly) {
			_log.error(t, t);
		}
		else {
			throw t;
		}
	}

	protected static final String INDEX_NOT_FOUND_EXCEPTION_MESSAGE =
		"type=index_not_found_exception";

	private boolean _isIndexNotFound(Throwable throwable) {
		String message = throwable.getMessage();

		if (message.contains(INDEX_NOT_FOUND_EXCEPTION_MESSAGE)) {
			return true;
		}

		return false;
	}

	private final Log _log;
	private final boolean _logExceptionsOnly;

}