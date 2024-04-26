/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.clazz;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.test.clazz.group.BatchTestClassGroup;

import java.io.File;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

/**
 * @author Kenji Heigel
 */
public class PlaywrightTestClass extends JUnitTestClass {

	@Override
	public JSONObject getJSONObject() {
		JSONObject jsonObject = super.getJSONObject();

		jsonObject.put(
			"minimum_slave_ram", _minimumSlaveRAM
		).put(
			"slave_label", _slaveLabel
		).put(
			"spec_title", _specTitle
		).put(
			"testray_main_component_name", _testrayMainComponentName
		);

		return jsonObject;
	}

	public Integer getMinimumSlaveRAM() {
		return _minimumSlaveRAM;
	}

	@Override
	public String getName() {
		return JenkinsResultsParserUtil.combine(
			getSpecFilePath(), " > ", getSpecTitle());
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

	public String getSpecTitle() {
		return _specTitle;
	}

	protected PlaywrightTestClass(
		BatchTestClassGroup batchTestClassGroup, File testClassFile,
		String specTitle) {

		super(batchTestClassGroup, testClassFile);

		_specTitle = specTitle;

		File testPropertiesBaseDir = getTestPropertiesBaseDir(
			getTestClassFile());

		if ((testPropertiesBaseDir != null) && testPropertiesBaseDir.exists()) {
			File testPropertiesFile = new File(
				testPropertiesBaseDir, "test.properties");

			Properties testProperties = JenkinsResultsParserUtil.getProperties(
				testPropertiesFile);

			String minimumSlaveRAM = JenkinsResultsParserUtil.getProperty(
				testProperties, "test.batch.minimum.slave.ram");

			if ((minimumSlaveRAM == null) || !minimumSlaveRAM.matches("\\d+")) {
				minimumSlaveRAM = _MINIMUM_SLAVE_RAM_DEFAULT;
			}

			_minimumSlaveRAM = Integer.valueOf(minimumSlaveRAM);

			String slaveLabel = JenkinsResultsParserUtil.getProperty(
				testProperties, "test.batch.slave.label");

			if (JenkinsResultsParserUtil.isNullOrEmpty(slaveLabel)) {
				slaveLabel = _SLAVE_LABEL_DEFAULT;
			}

			_slaveLabel = slaveLabel;

			_testrayMainComponentName = JenkinsResultsParserUtil.getProperty(
				testProperties, "testray.main.component.name");
		}
		else {
			_minimumSlaveRAM = null;
			_slaveLabel = null;
			_testrayMainComponentName = null;
		}
	}

	protected PlaywrightTestClass(
		BatchTestClassGroup batchTestClassGroup, JSONObject jsonObject) {

		super(batchTestClassGroup, jsonObject);

		_minimumSlaveRAM = jsonObject.optInt("minimum_slave_ram");
		_slaveLabel = jsonObject.optString("slave_label");
		_specTitle = jsonObject.getString("spec_title");
		_testrayMainComponentName = jsonObject.optString(
			"testray_main_component_name");
	}

	private static final String _MINIMUM_SLAVE_RAM_DEFAULT = "12";

	private static final String _SLAVE_LABEL_DEFAULT = "!master";

	private static final Pattern _testFilePathPattern = Pattern.compile(
		".+/test/playwright/tests/(?<specFilePath>.+)");

	private final Integer _minimumSlaveRAM;
	private final String _slaveLabel;
	private final String _specTitle;
	private final String _testrayMainComponentName;

}