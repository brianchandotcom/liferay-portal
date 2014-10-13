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

import com.liferay.portal.kernel.deploy.auto.context.AutoDeploymentContext;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.File;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Ivica Cardic
 * @author Brian Wing Shun Chan
 */
public class AutoDeployDir {

	public static final String DEFAULT_NAME = "defaultAutoDeployDir";

	public static void deploy(
			AutoDeploymentContext autoDeploymentContext,
			List<AutoDeployListener> autoDeployListeners)
		throws AutoDeployException {

		List<String> duplicateApplicableAutoDeployListenerClassNames =
			new ArrayList<String>();

		for (AutoDeployListener autoDeployListener : autoDeployListeners) {
			if (autoDeployListener.deploy(autoDeploymentContext) !=
					AutoDeployer.CODE_NOT_APPLICABLE) {

				Class<?> autoDeployListenerClass =
					autoDeployListener.getClass();

				duplicateApplicableAutoDeployListenerClassNames.add(
					autoDeployListenerClass.getName());
			}
		}

		if (duplicateApplicableAutoDeployListenerClassNames.size() > 1) {
			StringBundler sb = new StringBundler(5);

			sb.append("The auto deploy listeners ");
			sb.append(
				StringUtil.merge(
					duplicateApplicableAutoDeployListenerClassNames, ", "));
			sb.append(" all deployed ");
			sb.append(autoDeploymentContext.getFile());
			sb.append(", but only one should have.");

			throw new AutoDeployException(sb.toString());
		}
	}

	public AutoDeployDir(
		String name, File deployDir, File destDir, long interval,
		List<AutoDeployListener> autoDeployListeners) {

		_name = name;
		_deployDir = deployDir;
		_destDir = destDir;
		_interval = interval;
		_autoDeployListeners = new CopyOnWriteArrayList<AutoDeployListener>(
			autoDeployListeners);
		_blacklistFileTimestamps = new HashMap<String, Long>();
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

		if ((_interval > 0) &&
			((_autoDeployScanner == null) || !_autoDeployScanner.isAlive())) {

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

		if (_blacklistFileTimestamps.containsKey(fileName) &&
			(_blacklistFileTimestamps.get(fileName) == file.lastModified())) {

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Skip processing of " + fileName + " because it is " +
						"blacklisted");
			}

			return;
		}

		if (_log.isInfoEnabled()) {
			_log.info("Processing " + fileName);
		}

		try {
			AutoDeploymentContext autoDeploymentContext =
				buildAutoDeploymentContext(file);

			deploy(autoDeploymentContext, _autoDeployListeners);

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

		_blacklistFileTimestamps.put(fileName, file.lastModified());
	}

	protected void scanDirectory() {
		File[] files = _deployDir.listFiles();

		if (files == null) {
			return;
		}

		Set<String> blacklistedFileNames = _blacklistFileTimestamps.keySet();

		Iterator<String> iterator = blacklistedFileNames.iterator();

		while (iterator.hasNext()) {
			String blacklistedFileName = iterator.next();

			boolean blacklistedFileExists = false;

			for (File file : files) {
				if (StringUtil.equalsIgnoreCase(
						blacklistedFileName, file.getName())) {

					blacklistedFileExists = true;
				}
			}

			if (!blacklistedFileExists) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Remove blacklisted file " + blacklistedFileName +
							" because it was deleted");
				}

				iterator.remove();
			}
		}

		for (File file : files) {
			String fileName = file.getName();

			fileName = StringUtil.toLowerCase(fileName);

			if (file.isFile() &&
				(fileName.endsWith(".jar") || fileName.endsWith(".lpkg") ||
				 fileName.endsWith(".war") || fileName.endsWith(".xml") ||
				 fileName.endsWith(".zip"))) {

				processFile(file);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(AutoDeployDir.class);

	private static AutoDeployScanner _autoDeployScanner;

	private final List<AutoDeployListener> _autoDeployListeners;
	private final Map<String, Long> _blacklistFileTimestamps;
	private final File _deployDir;
	private final File _destDir;
	private final long _interval;
	private final String _name;

}