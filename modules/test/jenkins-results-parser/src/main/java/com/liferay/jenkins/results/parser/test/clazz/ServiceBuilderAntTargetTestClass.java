/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.clazz;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.test.clazz.group.BatchTestClassGroup;

import java.io.File;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class ServiceBuilderAntTargetTestClass extends BaseTestClass {

	public String getTestrayMainComponentName() {
		return _testrayMainComponentName;
	}

	protected ServiceBuilderAntTargetTestClass(
		BatchTestClassGroup batchTestClassGroup, File testClassFile) {

		super(batchTestClassGroup, testClassFile);

		addTestClassMethod("build-service-counter");
		addTestClassMethod("build-service-portal");
		addTestClassMethod("build-service-portlets");

		File testPropertiesBaseDir = getTestPropertiesBaseDir(
			getTestClassFile());

		if ((testPropertiesBaseDir != null) && testPropertiesBaseDir.exists()) {
			_testPropertiesFile = new File(
				testPropertiesBaseDir, "test.properties");

			_testrayMainComponentName = JenkinsResultsParserUtil.getProperty(
				JenkinsResultsParserUtil.getProperties(_testPropertiesFile),
				"testray.main.component.name");
		}
		else {
			_testPropertiesFile = null;
			_testrayMainComponentName = null;
		}
	}

	protected ServiceBuilderAntTargetTestClass(
		BatchTestClassGroup batchTestClassGroup, JSONObject jsonObject) {

		super(batchTestClassGroup, jsonObject);

		if (jsonObject.has("test_properties_file")) {
			_testPropertiesFile = new File(
				jsonObject.getString("test_properties_file"));
		}
		else {
			_testPropertiesFile = null;
		}

		_testrayMainComponentName = jsonObject.optString(
			"testray_main_component_name");
	}

	private final File _testPropertiesFile;
	private final String _testrayMainComponentName;

}