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

package com.liferay.jenkins.results.parser;

import java.io.File;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Michael Hashimoto
 */
public abstract class BatchTestClassGroup extends BaseTestClassGroup {

	public int getAxisCount() {
		return axisTestClassGroups.size();
	}

	public AxisTestClassGroup getAxisTestClassGroup(int axisId) {
		return axisTestClassGroups.get(axisId);
	}

	public String getBatchName() {
		return batchName;
	}

	public GitWorkingDirectory getGitWorkingDirectory() {
		return gitWorkingDirectory;
	}

	public Properties getPortalTestProperties() {
		return portalTestProperties;
	}

	protected BatchTestClassGroup(
		String batchName, GitWorkingDirectory gitWorkingDirectory,
		String testSuiteName) {

		this.batchName = batchName;
		this.gitWorkingDirectory = gitWorkingDirectory;
		this.testSuiteName = testSuiteName;

		portalTestProperties = JenkinsResultsParserUtil.getProperties(
			new File(
				this.gitWorkingDirectory.getWorkingDirectory(),
				"test.properties"));

		_setTestRelevantChanges();
	}

	protected String getFirstPropertyValue(
		Properties properties, List<String> propertyNames) {

		for (String propertyName : propertyNames) {
			if (propertyName == null) {
				continue;
			}

			if (properties.containsKey(propertyName)) {
				String propertyValue = properties.getProperty(propertyName);

				if ((propertyValue != null) && !propertyValue.isEmpty()) {
					return propertyValue;
				}
			}
		}

		return null;
	}

	protected String getWildcardPropertyName(
		Properties properties, String propertyName) {

		return getWildcardPropertyName(properties, propertyName, null);
	}

	protected String getWildcardPropertyName(
		Properties properties, String propertyName, String testSuiteName) {

		for (String wildcardPropertyName : properties.stringPropertyNames()) {
			if (!wildcardPropertyName.startsWith(propertyName)) {
				continue;
			}

			Matcher matcher = _propertyNamePattern.matcher(
				wildcardPropertyName);

			if (matcher.find()) {
				String batchNameRegex = matcher.group("batchName");

				batchNameRegex = batchNameRegex.replace("*", ".+");

				if (!batchName.matches(batchNameRegex)) {
					continue;
				}

				if ((testSuiteName != null) &&
					!testSuiteName.equals(matcher.group("testSuiteName"))) {

					continue;
				}

				return wildcardPropertyName;
			}
		}

		return null;
	}

	protected final Map<Integer, AxisTestClassGroup> axisTestClassGroups =
		new HashMap<>();
	protected final String batchName;
	protected final GitWorkingDirectory gitWorkingDirectory;
	protected final Properties portalTestProperties;
	protected boolean testRelevantChanges;
	protected final String testSuiteName;

	private void _setTestRelevantChanges() {
		List<String> propertyNames = new ArrayList<>();

		if (testSuiteName != null) {
			propertyNames.add(
				JenkinsResultsParserUtil.combine(
					"test.relevant.changes[", testSuiteName, "]"));
		}

		propertyNames.add("test.relevant.changes");

		String propertyValue = getFirstPropertyValue(
			portalTestProperties, propertyNames);

		if (propertyValue != null) {
			testRelevantChanges = Boolean.parseBoolean(propertyValue);

			return;
		}

		testRelevantChanges = _DEFAULT_TEST_RELEVANT_CHANGES;
	}

	private static final boolean _DEFAULT_TEST_RELEVANT_CHANGES = false;

	private final Pattern _propertyNamePattern = Pattern.compile(
		"[^\\]]+\\[(?<batchName>[^\\]]+)\\](\\[(?<testSuiteName>[^\\]]+)\\])?");

}