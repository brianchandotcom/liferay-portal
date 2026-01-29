/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.clazz;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.history.BatchHistory;
import com.liferay.jenkins.results.parser.history.TestClassHistory;
import com.liferay.jenkins.results.parser.test.clazz.group.BatchTestClassGroup;

import java.io.File;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

/**
 * @author Kenji Heigel
 */
public class PlaywrightJUnitTestClass extends JUnitTestClass {

	@Override
	public long getAverageDuration() {
		if (_averageDuration != null) {
			return _averageDuration;
		}

		Map<String, TestClassHistory> testClassHistoriesMap =
			_getTestClassHistoriesMap();

		if (testClassHistoriesMap.isEmpty()) {
			return 0L;
		}

		long totalAverageDuration = 0L;

		BatchTestClassGroup batchTestClassGroup = getBatchTestClassGroup();

		long defaultTestDuration = batchTestClassGroup.getDefaultTestDuration();

		for (TestClassHistory testClassHistory :
				testClassHistoriesMap.values()) {

			long averageDuration = defaultTestDuration;

			if (testClassHistory != null) {
				averageDuration = testClassHistory.getAverageDuration();
			}

			totalAverageDuration += averageDuration;
		}

		_averageDuration = totalAverageDuration;

		return _averageDuration;
	}

	@Override
	public long getAverageOverheadDuration() {
		if (_averageOverheadDuration != null) {
			return _averageOverheadDuration;
		}

		Map<String, TestClassHistory> testClassHistoriesMap =
			_getTestClassHistoriesMap();

		if (testClassHistoriesMap.isEmpty()) {
			return 0L;
		}

		long totalAverageOverheadDuration = 0L;

		BatchTestClassGroup batchTestClassGroup = getBatchTestClassGroup();

		long defaultTestOverheadDuration =
			batchTestClassGroup.getDefaultTestOverheadDuration();

		for (TestClassHistory testClassHistory :
				testClassHistoriesMap.values()) {

			long averageTestOverheadDuration = defaultTestOverheadDuration;

			if (testClassHistory != null) {
				averageTestOverheadDuration =
					testClassHistory.getAverageOverheadDuration();
			}

			totalAverageOverheadDuration += averageTestOverheadDuration;
		}

		_averageOverheadDuration =
			totalAverageOverheadDuration / testClassHistoriesMap.size();

		return totalAverageOverheadDuration;
	}

	@Override
	public JSONObject getJSONObject() {
		JSONObject jsonObject = super.getJSONObject();

		jsonObject.put(
			"minimum_slave_ram", _minimumSlaveRAM
		).put(
			"slave_label", _slaveLabel
		);

		return jsonObject;
	}

	public Integer getMinimumSlaveRAM() {
		return _minimumSlaveRAM;
	}

	@Override
	public String getName() {
		return getSpecFilePath();
	}

	public String getSlaveLabel() {
		return _slaveLabel;
	}

	public String getSpecFilePath() {
		Matcher matcher = _testFilePathPattern.matcher(
			JenkinsResultsParserUtil.getCanonicalPath(getTestClassFile()));

		if (!matcher.find()) {
			return null;
		}

		return matcher.group("specFilePath");
	}

	public boolean isAnalyticsCloudEnabled() {
		return _analyticsCloudEnabled;
	}

	protected PlaywrightJUnitTestClass(
		BatchTestClassGroup batchTestClassGroup, File testClassFile) {

		super(batchTestClassGroup, testClassFile);

		File testPropertiesBaseDir = getTestPropertiesBaseDir(
			getTestClassFile());

		if ((testPropertiesBaseDir != null) && testPropertiesBaseDir.exists()) {
			File testPropertiesFile = new File(
				testPropertiesBaseDir, "test.properties");

			Properties testProperties = JenkinsResultsParserUtil.getProperties(
				testPropertiesFile);

			String analyticsCloudEnabled = JenkinsResultsParserUtil.getProperty(
				testProperties, "analytics.cloud.enabled");

			if (!JenkinsResultsParserUtil.isNullOrEmpty(
					analyticsCloudEnabled) &&
				analyticsCloudEnabled.equals("true")) {

				_analyticsCloudEnabled = true;
			}

			String minimumSlaveRAM = JenkinsResultsParserUtil.getProperty(
				testProperties, "test.batch.minimum.slave.ram");

			if ((minimumSlaveRAM == null) || !minimumSlaveRAM.matches("\\d+")) {
				minimumSlaveRAM = _MINIMUM_SLAVE_RAM_DEFAULT;
			}

			_minimumSlaveRAM = Integer.valueOf(minimumSlaveRAM);

			String slaveLabel = JenkinsResultsParserUtil.getProperty(
				testProperties, "test.batch.slave.label");

			if (JenkinsResultsParserUtil.isNullOrEmpty(slaveLabel)) {
				slaveLabel = getSlaveLabel();
			}

			_slaveLabel = slaveLabel;
		}
		else {
			_minimumSlaveRAM = null;
			_slaveLabel = null;
		}
	}

	protected PlaywrightJUnitTestClass(
		BatchTestClassGroup batchTestClassGroup, JSONObject jsonObject) {

		super(batchTestClassGroup, jsonObject);

		_minimumSlaveRAM = jsonObject.optInt("minimum_slave_ram");
		_slaveLabel = jsonObject.optString("slave_label");
	}

	private Map<String, TestClassHistory> _getTestClassHistoriesMap() {
		if (_testClassHistoriesMap != null) {
			return _testClassHistoriesMap;
		}

		_testClassHistoriesMap = new HashMap<>();

		BatchTestClassGroup batchTestClassGroup = getBatchTestClassGroup();

		BatchHistory batchHistory = batchTestClassGroup.getBatchHistory();

		if (batchHistory == null) {
			return _testClassHistoriesMap;
		}

		for (TestClassMethod testClassMethod : getTestClassMethods()) {
			PlaywrightTestClassMethod playwrightTestClassMethod =
				(PlaywrightTestClassMethod)testClassMethod;

			String key = JenkinsResultsParserUtil.combine(
				getName(), ".", playwrightTestClassMethod.getTestName());

			TestClassHistory testClassHistory =
				batchHistory.getTestClassHistory(key);

			_testClassHistoriesMap.put(key, testClassHistory);
		}

		return _testClassHistoriesMap;
	}

	private static final String _MINIMUM_SLAVE_RAM_DEFAULT = "12";

	private static final Pattern _testFilePathPattern = Pattern.compile(
		".+/playwright/(setup|tests)/(?<specFilePath>.+)");

	private boolean _analyticsCloudEnabled;
	private Long _averageDuration;
	private Long _averageOverheadDuration;
	private final Integer _minimumSlaveRAM;
	private final String _slaveLabel;
	private Map<String, TestClassHistory> _testClassHistoriesMap;

}