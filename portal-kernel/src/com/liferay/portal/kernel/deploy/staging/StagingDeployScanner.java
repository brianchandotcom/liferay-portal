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

package com.liferay.portal.kernel.deploy.staging;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

/**
 * @author Ryan Park
 */
public class StagingDeployScanner extends Thread {

	public StagingDeployScanner(
		ThreadGroup threadGroup, String name,
		StagingDeployDir stagingDeployDir) {

		super(threadGroup, name);

		_stagingDeployDir = stagingDeployDir;

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
		catch (InterruptedException ie) {
		}

		while (_started) {
			try {
				sleep(_stagingDeployDir.getInterval());
			}
			catch (InterruptedException ie) {
			}

			try {
				_stagingDeployDir.scanDirectory();
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn("Unable to scan the staging deploy directory", e);
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		StagingDeployScanner.class);

	private final StagingDeployDir _stagingDeployDir;
	private boolean _started = true;

}