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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
	public List<String> getBatchNames() {
		String testBatchNames = getProperty(
			portalTestProperties, "test.batch.names[" + _testSuiteName + "]");

		if (testBatchNames == null) {
			testBatchNames = getProperty(
				portalTestProperties, "test.batch.names");
		}

		List<String> testBatchNamesList = getListFromString(testBatchNames);

		if (_isPortalWebOnly()) {
			List<String> irrelevantBatchNamesList = new ArrayList<>();

			for (String testBatchName : testBatchNamesList) {
				if (!testBatchName.contains("compile-jsp") &&
					!testBatchName.contains("functional") &&
					!testBatchName.contains("portal-web") &&
					!testBatchName.contains("source-format")) {

					irrelevantBatchNamesList.add(testBatchName);
				}
			}

			testBatchNamesList.removeAll(irrelevantBatchNamesList);
		}

		return testBatchNamesList;
	}

	@Override
	public List<String> getDistTypes() {
		String testBatchDistAppServers = getProperty(
			portalTestProperties,
			"test.batch.dist.app.servers[" + _testSuiteName + "]");

		if (testBatchDistAppServers == null) {
			testBatchDistAppServers = getProperty(
				portalTestProperties, "test.batch.dist.app.servers");
		}

		return getListFromString(testBatchDistAppServers);
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

		for (String propertyName : propertyNames) {
			if (portalTestProperties.containsKey(propertyName)) {
				String propertyValue = getProperty(
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
		StringBuilder sb = new StringBuilder();

		sb.append("git diff-tree --no-commit-id --name-only -r ");
		sb.append(System.getenv("TOP_LEVEL_BRANCH_NAME"));
		sb.append(" ");
		sb.append(System.getenv("GITHUB_UPSTREAM_BRANCH_SHA"));

		GitWorkingDirectory gitWorkingDirectory = getGitWorkingDirectory();

		GitWorkingDirectory.ExecutionResult executionResult =
			gitWorkingDirectory.executeBashCommands(sb.toString());

		String standardOut = executionResult.getStandardOut();

		if (!Objects.isNull(standardOut)) {
			for (String diffFilePath : standardOut.split("\n")) {
				if (!diffFilePath.contains("portal-web/")) {
					return false;
				}
			}
		}

		return true;
	}

	private final String _testSuiteName;

}