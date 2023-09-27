/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.deploy.auto.context;

import com.liferay.portal.kernel.plugin.PluginPackage;

import java.io.File;

/**
 * @author Miguel Pastor
 */
public class AutoDeploymentContext {

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

	private String _context;
	private String _destDir;
	private File _file;
	private PluginPackage _pluginPackage;

}