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

		setContextClassLoader(getClass().getClassLoader());
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
		catch (InterruptedException ie) {
		}

		while (_started) {
			try {
				sleep(_autoDeployDir.getInterval());
			}
			catch (InterruptedException ie) {
			}

			try {
				_autoDeployDir.scanDirectory();
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn("Unable to scan the auto deploy directory", e);
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AutoDeployScanner.class);

	private final AutoDeployDir _autoDeployDir;
	private boolean _started = true;

}