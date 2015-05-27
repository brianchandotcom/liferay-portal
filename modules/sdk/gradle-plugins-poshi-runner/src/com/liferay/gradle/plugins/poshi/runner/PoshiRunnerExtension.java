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

package com.liferay.gradle.plugins.poshi.runner;

import java.io.File;

import java.util.HashMap;
import java.util.Map;

import org.gradle.api.Project;

/**
 * @author Andrea Di Giorgi
 */
public class PoshiRunnerExtension {

	public PoshiRunnerExtension(Project project) {
		_project = project;
	}

	public File getBaseDir() {
		return _project.file(_baseDir);
	}

	public String getOpenCVVersion() {
		return _openCVVersion;
	}

	public Map<String, Object> getPoshiProperties() {
		return _poshiProperties;
	}

	public String getVersion() {
		return _version;
	}

	public void poshiProperty(String key, Object value) {
		_poshiProperties.put(key, value);
	}

	public void setBaseDir(Object baseDir) {
		_baseDir = baseDir;
	}

	public void setOpenCVVersion(String openCVVersion) {
		_openCVVersion = openCVVersion;
	}

	public void setPoshiProperties(Map<String, ?> poshiProperties) {
		_poshiProperties.clear();

		_poshiProperties.putAll(poshiProperties);
	}

	public void setVersion(String version) {
		_version = version;
	}

	private Object _baseDir = "poshi-tests";
	private String _openCVVersion = "2.4.9-0.9";
	private final Map<String, Object> _poshiProperties = new HashMap<>();
	private final Project _project;
	private String _version = "latest.release";

}