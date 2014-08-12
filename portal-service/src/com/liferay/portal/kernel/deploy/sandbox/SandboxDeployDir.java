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

package com.liferay.portal.kernel.deploy.sandbox;

import com.liferay.portal.kernel.io.DirectoryFilter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.io.File;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Igor Spasic
 * @author Brian Wing Shun Chan
 */
public class SandboxDeployDir {

	public static final String DEFAULT_NAME = "defaultSandboxDeployDir";

	public SandboxDeployDir(
		String name, File deployDir, long interval,
		List<SandboxDeployListener> sandboxDeployListeners) {

		_name = name;
		_deployDir = deployDir;
		_interval = interval;
		_sandboxDeployListeners =
			new CopyOnWriteArrayList<SandboxDeployListener>(
				sandboxDeployListeners);
	}

	public File getDeployDir() {
		return _deployDir;
	}

	public long getInterval() {
		return _interval;
	}

	public List<SandboxDeployListener> getListeners() {
		return _sandboxDeployListeners;
	}

	public String getName() {
		return _name;
	}

	public void registerListener(SandboxDeployListener listener) {
		_sandboxDeployListeners.add(listener);
	}

	public void start() {
		if (!_deployDir.exists()) {
			if (_log.isInfoEnabled()) {
				_log.info("Creating missing directory " + _deployDir);
			}

			boolean created = _deployDir.mkdirs();

			if (!created) {
				_log.error("Directory " + _deployDir + " could not be created");
			}
		}

		if (_interval > 0) {
			_existingDirs = ListUtil.fromArray(
				_deployDir.listFiles(_directoryFilter));

			Iterator<File> itr = _existingDirs.iterator();

			while (itr.hasNext()) {
				File dir = itr.next();

				if (!FileUtil.exists(dir + "/WEB-INF/web.xml")) {
					itr.remove();
				}
			}

			try {
				Thread currentThread = Thread.currentThread();

				_sandboxDeployScanner = new SandboxDeployScanner(
					currentThread.getThreadGroup(),
					SandboxDeployScanner.class.getName(), this);

				_sandboxDeployScanner.start();

				if (_log.isInfoEnabled()) {
					_log.info(
						"Sandbox deploy scanner started for " + _deployDir);
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
					"Sandbox deploy scanning is disabled for " + _deployDir);
			}
		}
	}

	public void stop() {
		if (_sandboxDeployScanner != null) {
			_sandboxDeployScanner.pause();
		}
	}

	public void unregisterListener(
		SandboxDeployListener sandboxDeployListener) {

		_sandboxDeployListeners.remove(sandboxDeployListener);
	}

	protected void deployDir(File file) {
		String fileName = file.getName();

		if (!file.canRead()) {
			_log.error("Unable to read " + fileName);

			return;
		}

		if (!file.canWrite()) {
			_log.error("Unable to write " + fileName);

			return;
		}

		if (_log.isInfoEnabled()) {
			_log.info("Processing " + fileName);
		}

		try {
			for (SandboxDeployListener sandboxDeployListener :
					_sandboxDeployListeners) {

				sandboxDeployListener.deploy(file);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	protected void scanDirectory() {
		File[] currentDirs = _deployDir.listFiles(_directoryFilter);

		if (currentDirs.length != _existingDirs.size()) {
			for (File dir : currentDirs) {
				if (!_existingDirs.contains(dir)) {
					_existingDirs.add(dir);

					deployDir(dir);
				}
			}
		}

		Iterator<File> itr = _existingDirs.iterator();

		while (itr.hasNext()) {
			File dir = itr.next();

			if (!dir.exists()) {
				itr.remove();

				undeployDir(dir);
			}
		}
	}

	protected void undeployDir(File file) {
		try {
			for (SandboxDeployListener sandboxDeployListener :
					_sandboxDeployListeners) {

				sandboxDeployListener.undeploy(file);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SandboxDeployDir.class);

	private final File _deployDir;
	private final DirectoryFilter _directoryFilter = new DirectoryFilter();
	private List<File> _existingDirs;
	private final long _interval;
	private final String _name;
	private final List<SandboxDeployListener> _sandboxDeployListeners;
	private SandboxDeployScanner _sandboxDeployScanner;

}