/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.testray;

import com.liferay.gradle.util.GradleUtil;

import java.io.File;

import org.gradle.api.Project;

/**
 * @author Andrea Di Giorgi
 */
public class TestrayExtension {

	public TestrayExtension(Project project) {
		_project = project;
	}

	public String getJenkinsResultsParserVersion() {
		return GradleUtil.toString(_jenkinsResultsParserVersion);
	}

	public File getTestrayPropertiesFile() {
		return GradleUtil.toFile(_project, _testrayPropertiesFile);
	}

	public void setJenkinsResultsParserVersion(
		Object jenkinsResultsParserVersion) {

		_jenkinsResultsParserVersion = jenkinsResultsParserVersion;
	}

	public void setTestrayPropertiesFile(Object testrayPropertiesFile) {
		_testrayPropertiesFile = testrayPropertiesFile;
	}

	private Object _jenkinsResultsParserVersion = "1.0.1187";
	private final Project _project;
	private Object _testrayPropertiesFile = "testray.properties";

}