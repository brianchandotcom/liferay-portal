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

package com.liferay.portal.kernel.deploy.auto.context;

import com.liferay.portal.kernel.plugin.PluginPackage;

import java.io.File;

/**
 * @author Miguel Pastor
 */
public class AutoDeploymentContext {

	public String getAppServerType() {
		return _appServerType;
	}

	public String getContext() {
		return _context;
	}

	public File getDeployDir() {
		return new File(_destDir, _context);
	}

	public String getDestDir() {
		return _destDir;
	}

	public File getFile() {
		return _file;
	}

	public PluginPackage getPluginPackage() {
		return _pluginPackage;
	}

	public void setAppServerType(String appServerType) {
		_appServerType = appServerType;
	}

	public void setContext(String context) {
		_context = context;
	}

	public void setDestDir(String destDir) {
		_destDir = destDir;
	}

	public void setFile(File file) {
		_file = file;
	}

	public void setPluginPackage(PluginPackage pluginPackage) {
		_pluginPackage = pluginPackage;
	}

	private String _appServerType;
	private String _context;
	private String _destDir;
	private File _file;
	private PluginPackage _pluginPackage;

}