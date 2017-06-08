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
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.File;
import java.io.FilenameFilter;

/**
 * @author Ryan Park
 */
public class StagingDeployDir {

	public static final String DEFAULT_NAME = "defaultStagingDeployDir";

	public static void stage(File file) throws StagingDeployException {
		if (!file.isFile()) {
			return;
		}

		File stagingDir = new File(
			PropsUtil.get(PropsKeys.STAGING_DEPLOY_DEPLOY_DIR));

		FileUtil.move(file, new File(stagingDir, file.getName()));
	}

	public StagingDeployDir(
		String name, File stagingDir, File deployDir, long interval) {

		_name = name;
		_stagingDir = stagingDir;
		_deployDir = deployDir;
		_interval = interval;
	}

	public void deployStagedFiles() {
		File[] files = _stagingDir.listFiles(_stagedFilenameFilter);

		for (File file : files) {
			String fileName = file.getName();

			int i = fileName.indexOf(".staged");

			fileName = fileName.substring(0, i);

			FileUtil.move(file, new File(_deployDir, fileName));
		}
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

		deployStagedFiles();
	}

	protected void processFile(File file) {
		String fileName = file.getName();

		boolean resolves = resolveDependencies(file);

		if (resolves) {
			fileName = fileName.concat(".staged");
		}
		else {
			fileName = fileName.concat(".staging_failed");
		}

		FileUtil.move(file, new File(_stagingDir, fileName));
	}

	protected boolean resolveDependencies(File file) {
		return true;
	}

	protected void scanDirectory() {
		File[] files = _stagingDir.listFiles(_processedFilenameFilter);

		if (files == null) {
			return;
		}

		for (File file : files) {
			String fileName = StringUtil.toLowerCase(file.getName());

			if (file.isFile() &&
				(fileName.endsWith(".jar") || fileName.endsWith(".lpkg"))) {

				processFile(file);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		StagingDeployDir.class);

	private static StagingDeployScanner _stagingDeployScanner;

	private final File _deployDir;
	private final long _interval;
	private final String _name;
	private final FilenameFilter _processedFilenameFilter =
		new ProcessedFilenameFilter();
	private final FilenameFilter _stagedFilenameFilter =
		new StagedFilenameFilter();
	private final File _stagingDir;

	private static class ProcessedFilenameFilter implements FilenameFilter {

		public boolean accept(File dir, String name) {
			if (name.endsWith(".staged") || name.endsWith(".staging_failed")) {
				return false;
			}
			else {
				return true;
			}
		}

	}

	private static class StagedFilenameFilter implements FilenameFilter {

		public boolean accept(File dir, String name) {
			if (name.endsWith(".staged")) {
				return true;
			}
			else {
				return false;
			}
		}

	}

}