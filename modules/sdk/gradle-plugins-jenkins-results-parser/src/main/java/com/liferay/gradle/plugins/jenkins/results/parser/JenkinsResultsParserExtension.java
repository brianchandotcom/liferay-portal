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

package com.liferay.gradle.plugins.jenkins.results.parser;

import com.liferay.gradle.util.GradleUtil;

import java.io.File;

import org.gradle.api.Project;

/**
 * @author Andrea Di Giorgi
 */
public class JenkinsResultsParserExtension {

	public JenkinsResultsParserExtension(Project project) {
		_project = project;
	}

	public File getTestrayPropertiesFile() {
		return GradleUtil.toFile(_project, _testrayPropertiesFile);
	}

	public String getVersion() {
		return GradleUtil.toString(_version);
	}

	public void setTestrayPropertiesFile(Object testrayPropertiesFile) {
		_testrayPropertiesFile = testrayPropertiesFile;
	}

	public void setVersion(Object version) {
		_version = version;
	}

	private final Project _project;
	private Object _testrayPropertiesFile = "testray.properties";
	private Object _version = "1.0.970";

}