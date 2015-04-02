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

package com.liferay.gradle.plugins.extensions;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.util.Properties;

import org.gradle.api.Project;

/**
 * @author Andrea Di Giorgi
 */
public class LiferayExtension {

	public LiferayExtension(Project project) throws Exception {
		_project = project;

		_bndProperties = _loadProperties("bnd.bnd");
		_pluginPackageProperties = _loadProperties(
			"docroot/WEB-INF/liferay-plugin-package.properties");

		_portalVersion = "7.0.0-SNAPSHOT";

		File pluginSrcDir = project.file("docroot/WEB-INF/src");

		if (!pluginSrcDir.exists()) {
			pluginSrcDir = project.file("src");
		}

		_pluginSrcDir = pluginSrcDir;

		String projectName = project.getName();

		int index = projectName.lastIndexOf("-");

		_pluginType = projectName.substring(index + 1);

		_tmpDir = new File(project.getRootDir(), "tmp");
	}

	public String getBndProperty(String key) {
		return _bndProperties.getProperty(key);
	}

	public String getPluginPackageProperty(String key) {
		return _pluginPackageProperties.getProperty(key);
	}

	public File getPluginSrcDir() {
		return _pluginSrcDir;
	}

	public String getPluginType() {
		return _pluginType;
	}

	public String getPortalVersion() {
		return _portalVersion;
	}

	public File getTmpDir() {
		return _tmpDir;
	}

	public boolean isOsgiPlugin() {
		if (!_bndProperties.isEmpty()) {
			return true;
		}

		return false;
	}

	public void setPortalVersion(String portalVersion) {
		_portalVersion = portalVersion;
	}

	public void setTmpDir(File tmpDir) {
		_tmpDir = tmpDir;
	}

	private Properties _loadProperties(String fileName) throws IOException {
		Properties properties = new Properties();

		File file = _project.file(fileName);

		if (file.exists()) {
			try (FileInputStream fileInputStream = new FileInputStream(file)) {
				properties.load(fileInputStream);
			}
		}

		return properties;
	}

	private final Properties _bndProperties;
	private final Properties _pluginPackageProperties;
	private final File _pluginSrcDir;
	private final String _pluginType;
	private String _portalVersion;
	private final Project _project;
	private File _tmpDir;

}