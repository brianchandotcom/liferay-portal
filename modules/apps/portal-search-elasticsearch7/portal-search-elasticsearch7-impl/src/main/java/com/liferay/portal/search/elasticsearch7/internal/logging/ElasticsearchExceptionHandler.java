/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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