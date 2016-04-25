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
import com.liferay.portal.kernel.util.StringUtil;

import java.io.File;

/**
 * @author Ryan Park
 */
public class StagingDeployDir {

	public static final String DEFAULT_NAME = "defaultStagingDeployDir";

	public StagingDeployDir(String name, File stagingDir, long interval) {
		_name = name;
		_stagingDir = stagingDir;
		_interval = interval;
	}

	public long getInterval() {
		return _interval;
	}

	public String getName() {
		return _name;
	}

	public File getStagingDir() {
		return _stagingDir;
	}

	public void start() {
		if (!_stagingDir.exists()) {
			if (_log.isInfoEnabled()) {
				_log.info("Creating missing directory " + _stagingDir);
			}

			boolean created = _stagingDir.mkdirs();

			if (!created) {
				_log.error(
					"Directory " + _stagingDir + " could not be created");
			}
		}

		if ((_interval > 0) &&
			((_stagingDeployScanner == null) ||
			 !_stagingDeployScanner.isAlive())) {

			try {
				Thread currentThread = Thread.currentThread();

				_stagingDeployScanner = new StagingDeployScanner(
					currentThread.getThreadGroup(),
					StagingDeployScanner.class.getName(), this);

				_stagingDeployScanner.start();

				if (_log.isInfoEnabled()) {
					_log.info(
						"Staging deploy scanner started for " + _stagingDir);
				}
			}
			catch (Exception e) {
				_log.error(e, e);

				stop();

				return;
			}
		}
		else {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Staging deploy scanning is disabled for " + _stagingDir);
			}
		}
	}

	public void stop() {
		if (_stagingDeployScanner != null) {
			_stagingDeployScanner.pause();
		}
	}

	protected void processFile(File file) {
	}

	protected void scanDirectory() {
		File[] files = _stagingDir.listFiles();

		if (files == null) {
			return;
		}

		for (File file : files) {
			if (file.isFile()) {
				processFile(file);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		StagingDeployDir.class);

	private static StagingDeployScanner _stagingDeployScanner;

	private final long _interval;
	private final String _name;
	private final File _stagingDir;

}