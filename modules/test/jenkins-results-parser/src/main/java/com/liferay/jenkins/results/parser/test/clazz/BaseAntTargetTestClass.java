/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.clazz;

import com.liferay.jenkins.results.parser.DownstreamBuildReport;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.TestClassReport;
import com.liferay.jenkins.results.parser.test.clazz.group.BatchTestClassGroup;

import java.io.File;

import java.util.Properties;

import org.json.JSONObject;

/**
 * @author Brittney Nguyen
 */
public abstract class BaseAntTargetTestClass extends BaseTestClass {

	@Override
	public int compareTo(TestClass testClass) {
		int result = super.compareTo(testClass);

		if (result != 0) {
			return result;
		}

		if (testClass instanceof BaseAntTargetTestClass) {
			BaseAntTargetTestClass otherBaseAntTargetTestClass =
				(BaseAntTargetTestClass)testClass;

			String otherAntTargetName =
				otherBaseAntTargetTestClass.getAntTargetName();

			if ((_antTargetName == null) && (otherAntTargetName == null)) {
				return 0;
			}

			if (_antTargetName == null) {
				return -1;
			}

			if (otherAntTargetName == null) {
				return 1;
			}

			return _antTargetName.compareTo(otherAntTargetName);
		}

		return 0;
	}

	public String getAntTargetName() {
		return _antTargetName;
	}

	public DownstreamBuildReport getCachedDownstreamBuildReport() {
		if (!isBuildCachingEnabled()) {
			return null;
		}

		if (!_cachedTestClassReportSearched) {
			getCachedTestClassReport();
		}

		return _cachedDownstreamBuildReport;
	}

	public TestClassReport getCachedTestClassReport() {
		if (!isBuildCachingEnabled() || _cachedTestClassReportSearched) {
			return _cachedTestClassReport;
		}

		BatchTestClassGroup batchTestClassGroup = getBatchTestClassGroup();

		_cachedTestClassReport = batchTestClassGroup.getCachedTestClassReport(
			getTestClassName());

		if (_cachedTestClassReport != null) {
			_cachedDownstreamBuildReport =
				_cachedTestClassReport.getDownstreamBuildReport();
		}

		_cachedTestClassReportSearched = true;

		return _cachedTestClassReport;
	}

	@Override
	public JSONObject getJSONObject() {
		JSONObject jsonObject = super.getJSONObject();

		if (!JenkinsResultsParserUtil.isNullOrEmpty(_antTargetName)) {
			jsonObject.put("ant_target_name", _antTargetName);
		}

		if (!JenkinsResultsParserUtil.isNullOrEmpty(
				_testrayMainComponentName)) {

			jsonObject.put(
				"testray_main_component_name", _testrayMainComponentName);
		}

		if (!JenkinsResultsParserUtil.isNullOrEmpty(_testrayTeamName)) {
			jsonObject.put("testray_team_name", _testrayTeamName);
		}

		return jsonObject;
	}

	@Override
	public String getTestClassName() {
		String name = getName();

		return name.replaceAll("/", ".");
	}

	public String getTestrayMainComponentName() {
		return _testrayMainComponentName;
	}

	public String getTestrayTeamName() {
		return _testrayTeamName;
	}

	protected BaseAntTargetTestClass(
		BatchTestClassGroup batchTestClassGroup, File testClassFile) {

		this(batchTestClassGroup, testClassFile, null);
	}

	protected BaseAntTargetTestClass(
		BatchTestClassGroup batchTestClassGroup, File testClassFile,
		String antTargetName) {

		super(batchTestClassGroup, testClassFile);

		_antTargetName = antTargetName;

		addTestClassMethods();

		File testPropertiesBaseDir = getTestPropertiesBaseDir(
			getTestClassFile());

		if ((testPropertiesBaseDir != null) && testPropertiesBaseDir.exists()) {
			_testPropertiesFile = new File(
				testPropertiesBaseDir, "test.properties");

			Properties testProperties = JenkinsResultsParserUtil.getProperties(
				_testPropertiesFile);

			_testrayMainComponentName = JenkinsResultsParserUtil.getProperty(
				testProperties, "testray.main.component.name", false,
				antTargetName);

			_testrayTeamName = JenkinsResultsParserUtil.getProperty(
				testProperties, "testray.team.name", false, antTargetName);
		}
		else {
			_testPropertiesFile = null;
			_testrayMainComponentName = null;
			_testrayTeamName = null;
		}
	}

	protected BaseAntTargetTestClass(
		BatchTestClassGroup batchTestClassGroup, JSONObject jsonObject) {

		super(batchTestClassGroup, jsonObject);

		_antTargetName = jsonObject.optString("ant_target_name", null);

		if (jsonObject.has("test_properties_file")) {
			_testPropertiesFile = new File(
				jsonObject.getString("test_properties_file"));
		}
		else {
			_testPropertiesFile = null;
		}

		_testrayMainComponentName = jsonObject.optString(
			"testray_main_component_name");
		_testrayTeamName = jsonObject.optString("testray_team_name");
	}

	protected abstract void addTestClassMethods();

	private final String _antTargetName;
	private DownstreamBuildReport _cachedDownstreamBuildReport;
	private TestClassReport _cachedTestClassReport;
	private boolean _cachedTestClassReportSearched;
	private final File _testPropertiesFile;
	private final String _testrayMainComponentName;
	private final String _testrayTeamName;

}