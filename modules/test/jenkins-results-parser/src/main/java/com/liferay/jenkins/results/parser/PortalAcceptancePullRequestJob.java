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

import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Michael Hashimoto
 */
public class PortalAcceptancePullRequestJob
	extends PortalGitRepositoryJob implements TestSuiteJob {

	public PortalAcceptancePullRequestJob(String jobName) {
		this(jobName, "default");
	}

	public PortalAcceptancePullRequestJob(
		String jobName, String testSuiteName) {

		super(jobName);

		_testSuiteName = testSuiteName;
	}

	@Override
	public Set<String> getBatchNames() {
		Properties jobProperties = getJobProperties();

		String testBatchNames = JenkinsResultsParserUtil.getProperty(
			jobProperties, "test.batch.names[" + _testSuiteName + "]");

		if (testBatchNames == null) {
			testBatchNames = JenkinsResultsParserUtil.getProperty(
				jobProperties, "test.batch.names");
		}

		Set<String> testBatchNamesSet = getSetFromString(testBatchNames);

		if (_isRelevantTestSuite() && _isPortalWebOnly()) {
			String[] portalWebOnlyBatchNameMarkers = {
				"compile-jsp", "functional", "portal-web", "source-format"
			};

			Set<String> portalWebOnlyBatchNamesSet = new TreeSet<>();

			for (String testBatchName : testBatchNamesSet) {
				for (String portalWebOnlyBatchNameMarker :
						portalWebOnlyBatchNameMarkers) {

					if (testBatchName.contains(portalWebOnlyBatchNameMarker)) {
						portalWebOnlyBatchNamesSet.add(testBatchName);

						break;
					}
				}
			}

			return portalWebOnlyBatchNamesSet;
		}

		return testBatchNamesSet;
	}

	@Override
	public Set<String> getDistTypes() {
		Properties jobProperties = getJobProperties();

		String testBatchDistAppServers = JenkinsResultsParserUtil.getProperty(
			jobProperties,
			"test.batch.dist.app.servers[" + _testSuiteName + "]");

		if (testBatchDistAppServers == null) {
			testBatchDistAppServers = JenkinsResultsParserUtil.getProperty(
				jobProperties, "test.batch.dist.app.servers");
		}

		return getSetFromString(testBatchDistAppServers);
	}

	@Override
	public String getPoshiQuery(String testBatchName) {
		String[] propertyNames = {
			JenkinsResultsParserUtil.combine(
				"test.batch.run.property.query[", testBatchName, "][",
				_testSuiteName, "]"),
			JenkinsResultsParserUtil.combine(
				"test.batch.run.property.query[", testBatchName, "]")
		};

		Properties jobProperties = getJobProperties();

		for (String propertyName : propertyNames) {
			if (jobProperties.containsKey(propertyName)) {
				String propertyValue = JenkinsResultsParserUtil.getProperty(
					jobProperties, propertyName);

				if ((propertyValue != null) && !propertyValue.isEmpty()) {
					return propertyValue;
				}
			}
		}

		return null;
	}

	@Override
	public String getTestSuiteName() {
		return _testSuiteName;
	}

	private boolean _isPortalWebOnly() {
		GitWorkingDirectory gitWorkingDirectory = getGitWorkingDirectory();

		File portalWebDirectory = new File(
			gitWorkingDirectory.getWorkingDirectory(), "portal-web");

		for (File modifiedFile : gitWorkingDirectory.getModifiedFilesList()) {
			if (!JenkinsResultsParserUtil.isFileInDirectory(
					portalWebDirectory, modifiedFile)) {

				return false;
			}
		}

		return true;
	}

	private boolean _isRelevantTestSuite() {
		return _testSuiteName.equals("relevant");
	}

	private final String _testSuiteName;

}