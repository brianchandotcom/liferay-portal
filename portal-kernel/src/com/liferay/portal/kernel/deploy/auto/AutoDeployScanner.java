/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.deploy.auto;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

/**
 * @author Ivica Cardic
 * @author Brian Wing Shun Chan
 */
public class AutoDeployScanner extends Thread {

	public AutoDeployScanner(
		ThreadGroup threadGroup, String name, AutoDeployDir autoDeployDir) {

		super(threadGroup, name);

		_autoDeployDir = autoDeployDir;

		Class<?> clazz = getClass();

		setContextClassLoader(clazz.getClassLoader());

		setDaemon(true);
		setPriority(MIN_PRIORITY);
	}

	public void pause() {
		_started = false;
	}

	@Override
	public void run() {
		try {
			sleep(1000 * 10);
		}
		catch (InterruptedException interruptedException) {
			if (_log.isDebugEnabled()) {
				_log.debug(interruptedException);
			}
		}

		while (_started) {
			try {
				sleep(_autoDeployDir.getInterval());
			}
			catch (InterruptedException interruptedException) {
				if (_log.isDebugEnabled()) {
					_log.debug(interruptedException);
				}
			}

			try {
				_autoDeployDir.scanDirectory();
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to scan the auto deploy directory", exception);
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AutoDeployScanner.class);

	private final AutoDeployDir _autoDeployDir;
	private boolean _started = true;

}