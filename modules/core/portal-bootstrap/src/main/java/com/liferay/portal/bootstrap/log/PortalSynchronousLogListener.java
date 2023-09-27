/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.bootstrap.log;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;

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

		if (StringUtil.equals(message, "ServiceEvent REGISTERING") ||
			StringUtil.equals(message, "ServiceEvent UNREGISTERING")) {

			message =
				message + StringPool.SPACE + logEntry.getServiceReference();
		}

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