/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.deploy.auto.context.AutoDeploymentContext;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.File;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Ivica Cardic
 * @author Brian Wing Shun Chan
 */
public class AutoDeployDir {

	public static final String DEFAULT_NAME = "defaultAutoDeployDir";

	public AutoDeployDir(
		String name, File deployDir, File destDir, long interval,
		List<AutoDeployListener> autoDeployListeners) {

		_name = name;
		_deployDir = deployDir;
		_destDir = destDir;
		_interval = interval;
		_autoDeployListeners = new CopyOnWriteArrayList<AutoDeployListener>(
			autoDeployListeners);
		_blacklistFiles = new HashMap<String, Long>();
	}

	public File getDeployDir() {
		return _deployDir;
	}

	public File getDestDir() {
		return _destDir;
	}

	public long getInterval() {
		return _interval;
	}

	public List<AutoDeployListener> getListeners() {
		return _autoDeployListeners;
	}

	public String getName() {
		return _name;
	}

	public void registerListener(AutoDeployListener listener) {
		_autoDeployListeners.add(listener);
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
			try {
				Thread currentThread = Thread.currentThread();

				_autoDeployScanner = new AutoDeployScanner(
					currentThread.getThreadGroup(),
					AutoDeployScanner.class.getName(), this);

				_autoDeployScanner.start();

				if (_log.isInfoEnabled()) {
					_log.info("Auto deploy scanner started for " + _deployDir);
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
				_log.info("Auto deploy scanning is disabled for " + _deployDir);
			}
		}
	}

	public void stop() {
		if (_autoDeployScanner != null) {
			_autoDeployScanner.pause();
		}
	}

	public void unregisterListener(AutoDeployListener autoDeployListener) {
		_autoDeployListeners.remove(autoDeployListener);
	}

	protected AutoDeploymentContext buildAutoDeploymentContext(File file) {
		AutoDeploymentContext autoDeploymentContext =
			new AutoDeploymentContext();

		autoDeploymentContext.setFile(file);

		return autoDeploymentContext;
	}

	protected void processFile(File file) {
		String fileName = file.getName();

		if (!file.canRead()) {
			_log.error("Unable to read " + fileName);

			return;
		}

		if (!file.canWrite()) {
			_log.error("Unable to write " + fileName);

			return;
		}

		long lastModified = file.lastModified();

		if (_blacklistFiles.containsKey(fileName) &&
			(_blacklistFiles.get(fileName) == lastModified)) {

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Skip processing of " + fileName + " because it is " +
						"blacklisted.");
			}

			return;
		}

		if (_log.isInfoEnabled()) {
			_log.info("Processing " + fileName);
		}

		try {
			AutoDeploymentContext autoDeploymentContext =
				buildAutoDeploymentContext(file);

			for (AutoDeployListener autoDeployListener : _autoDeployListeners) {
				autoDeployListener.deploy(autoDeploymentContext);
			}

			if (file.delete()) {
				return;
			}

			_log.error("Auto deploy failed to remove " + fileName);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		if (_log.isInfoEnabled()) {
			_log.info("Add " + fileName + " to the blacklist");
		}

		_blacklistFiles.put(fileName, lastModified);
	}

	protected void scanDirectory() {
		File[] files = _deployDir.listFiles();

		Iterator<String> itr = _blacklistFiles.keySet().iterator();

		while (itr.hasNext()) {
			String blacklistedFileName = itr.next();

			boolean blacklistedFileExists = false;

			for (File file : files) {
				if (blacklistedFileName.equalsIgnoreCase(file.getName())) {
					blacklistedFileExists = true;
				}
			}

			if (!blacklistedFileExists) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Remove blacklisted file " + blacklistedFileName +
							" because it was deleted.");
				}

				itr.remove();
			}
		}

		for (File file : files) {
			String fileName = file.getName().toLowerCase();

			if (file.isFile() &&
				(fileName.endsWith(".jar") || fileName.endsWith(".lpkg") ||
				 fileName.endsWith(".war") || fileName.endsWith(".xml") ||
				 fileName.endsWith(".zip"))) {

				processFile(file);
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(AutoDeployDir.class);

	private List<AutoDeployListener> _autoDeployListeners;
	private AutoDeployScanner _autoDeployScanner;
	private Map<String, Long> _blacklistFiles;
	private File _deployDir;
	private File _destDir;
	private long _interval;
	private String _name;

}