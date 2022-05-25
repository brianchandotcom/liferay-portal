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

package com.liferay.portal.bootstrap.log;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import org.eclipse.equinox.log.SynchronousLogListener;

import org.osgi.framework.BundleException;
import org.osgi.service.log.LogEntry;
import org.osgi.service.log.LogLevel;

/**
 * @author Raymond Augé
 * @author Kamesh Sampath
 */
public class PortalSynchronousLogListener implements SynchronousLogListener {

	@Override
	public void logged(LogEntry logEntry) {
		Log log = LogFactoryUtil.getLog(
			"osgi.logging.".concat(logEntry.getLoggerName()));

		LogLevel level = logEntry.getLogLevel();

		String message = logEntry.getMessage();

		Throwable throwable = logEntry.getException();

		if ((level == LogLevel.DEBUG) && log.isDebugEnabled()) {
			log.debug(message, throwable);
		}
		else if ((level == LogLevel.ERROR) && log.isErrorEnabled()) {
			log.error(message, throwable);
		}
		else if ((level == LogLevel.INFO) && log.isInfoEnabled()) {
			log.info(message, throwable);
		}
		else if ((level == LogLevel.WARN) && log.isWarnEnabled()) {
			log.warn(message, throwable);
		}

		if ((throwable != null) && (throwable instanceof BundleException) &&
			(_JENKINS_HOME != null)) {

			String throwableMessage = throwable.getMessage();

			if (throwableMessage.startsWith("Could not resolve module")) {
				log.error("Exiting the JVM", throwable);

				System.exit(1);
			}
		}
	}

	private static final String _JENKINS_HOME = System.getenv("JENKINS_HOME");

}