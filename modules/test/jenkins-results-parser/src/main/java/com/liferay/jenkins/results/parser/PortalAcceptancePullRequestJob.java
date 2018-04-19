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
public class PortalAcceptancePullRequestJob extends PortalRepositoryJob {

	public PortalAcceptancePullRequestJob(String url) {
		this(url, "default");
	}

	public PortalAcceptancePullRequestJob(String url, String testSuiteName) {
		super(url);

		_testSuiteName = testSuiteName;
	}

	@Override
	public Set<String> getBatchNames() {
		Properties portalTestProperties = getPortalTestProperties();

		String testBatchNames = JenkinsResultsParserUtil.getProperty(
			portalTestProperties, "test.batch.names[" + _testSuiteName + "]");

		if (testBatchNames == null) {
			testBatchNames = JenkinsResultsParserUtil.getProperty(
				portalTestProperties, "test.batch.names");
		}

		Set<String> testBatchNamesSet = getSetFromString(testBatchNames);

		if (_isPortalWebOnly()) {
			String[] portalWebOnlyBatchNameMarkers =
				{"compile-jsp", "functional", "portal-web", "source-format"};

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
		Properties portalTestProperties = getPortalTestProperties();

		String testBatchDistAppServers = JenkinsResultsParserUtil.getProperty(
			portalTestProperties,
			"test.batch.dist.app.servers[" + _testSuiteName + "]");

		if (testBatchDistAppServers == null) {
			testBatchDistAppServers = JenkinsResultsParserUtil.getProperty(
				portalTestProperties, "test.batch.dist.app.servers");
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

		Properties portalTestProperties = getPortalTestProperties();

		for (String propertyName : propertyNames) {
			if (portalTestProperties.containsKey(propertyName)) {
				String propertyValue = JenkinsResultsParserUtil.getProperty(
					portalTestProperties, propertyName);

				if ((propertyValue != null) && !propertyValue.isEmpty()) {
					return propertyValue;
				}
			}
		}

		return null;
	}

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

	private final String _testSuiteName;

}