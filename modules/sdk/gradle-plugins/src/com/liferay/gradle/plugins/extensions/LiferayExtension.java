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

		File pluginSrcDir = project.file("docroot/WEB-INF/src");

		if (!pluginSrcDir.exists()) {
			pluginSrcDir = project.file("src");
		}

		_pluginSrcDir = pluginSrcDir;
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
	private final Project _project;

}