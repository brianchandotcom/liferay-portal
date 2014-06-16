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

package com.liferay.portal.wab.extender.internal.processor;

import com.liferay.portal.events.GlobalStartupAction;
import com.liferay.portal.kernel.deploy.auto.AutoDeployException;
import com.liferay.portal.kernel.deploy.auto.AutoDeployListener;
import com.liferay.portal.kernel.deploy.auto.context.AutoDeploymentContext;
import com.liferay.portal.kernel.deploy.hot.DependencyManagementThreadLocal;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.wab.extender.internal.connection.WabURLConnection;
import com.liferay.portal.wab.extender.internal.util.AntUtil;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import java.util.List;
import java.util.Map;

import org.osgi.framework.BundleContext;

/**
 * @author Raymond Augé
 * @author Miguel Pastor
 */
public class WabProcessor {

	public WabProcessor(
		BundleContext bundleContext, ClassLoader classLoader, File file,
		Map<String, String[]> parameters) {

		_bundleContext = bundleContext;
		_classLoader = classLoader;
		_file = file;
		_parameters = parameters;
	}

	public java.io.InputStream getInputStream() throws IOException {
		_deployedAppFolder = _autoDeploy();

		if ((_deployedAppFolder == null) || !_deployedAppFolder.exists() ||
			!_deployedAppFolder.isDirectory()) {

			return null;
		}

		return null;
	}

	private File _autoDeploy() {
		String webContextpath = MapUtil.getString(
			_parameters, WabURLConnection.WEB_CONTEXT_PATH);

		if (!webContextpath.startsWith(StringPool.SLASH)) {
			webContextpath = StringPool.SLASH.concat(webContextpath);
		}

		AutoDeploymentContext autoDeploymentContext =
			_buildAutoDeploymentContext(webContextpath);

		_executeAutoDeployers(autoDeploymentContext);

		File deployDir = autoDeploymentContext.getDeployDir();

		if (!deployDir.exists()) {
			File[] files = deployDir.getParentFile().listFiles(
				new FilenameFilter() {
					@Override
					public boolean accept(File dir, String name) {
						return name.endsWith(".war");
					}
				}
			);

			if ((files == null) || (files.length == 0)) {

				// This should not happen

				return null;
			}

			File file = files[0];

			deployDir.mkdirs();

			AntUtil.expandFile(file, deployDir);
		}

		return deployDir;
	}

	private AutoDeploymentContext _buildAutoDeploymentContext(String context) {
		File file = new File(_file.getParentFile(), "deploy");

		file.mkdirs();

		AutoDeploymentContext autoDeploymentContext =
			new AutoDeploymentContext();

		autoDeploymentContext.setContext(context);
		autoDeploymentContext.setDestDir(file.getAbsolutePath());
		autoDeploymentContext.setFile(_file);

		return autoDeploymentContext;
	}

	private void _executeAutoDeployers(
		AutoDeploymentContext autoDeploymentContext) {

		boolean enabled = DependencyManagementThreadLocal.isEnabled();

		try {
			DependencyManagementThreadLocal.setEnabled(false);

			List<AutoDeployListener> autoDeployListeners =
				GlobalStartupAction.getAutoDeployListeners(false);

			for (AutoDeployListener autoDeployListener : autoDeployListeners) {
				try {
					autoDeployListener.deploy(autoDeploymentContext);
				}
				catch (AutoDeployException ade) {
					_log.error(ade);
				}
			}
		}
		finally {
			DependencyManagementThreadLocal.setEnabled(enabled);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(WabProcessor.class);

	private BundleContext _bundleContext;
	private ClassLoader _classLoader;
	private File _deployedAppFolder;
	private File _file;
	private File _manifestFile;
	private Map<String, String[]> _parameters;

}