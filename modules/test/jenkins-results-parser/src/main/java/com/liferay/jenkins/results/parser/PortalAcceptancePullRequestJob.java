/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.io.File;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class PortalAcceptancePullRequestJob
	extends PortalAcceptanceTestSuiteJob implements PortalWorkspaceJob {

	public boolean isCentralMergePullRequest() {
		if (_centralMergePullRequest != null) {
			return _centralMergePullRequest;
		}

		GitWorkingDirectory gitWorkingDirectory = getGitWorkingDirectory();

		List<File> currentBranchModifiedFiles =
			gitWorkingDirectory.getModifiedFilesList();

		if (currentBranchModifiedFiles.size() == 1) {
			File modifiedFile = currentBranchModifiedFiles.get(0);

			String modifiedFileName = modifiedFile.getName();

			if (modifiedFileName.equals("ci-merge")) {
				_centralMergePullRequest = true;

				return _centralMergePullRequest;
			}
		}

		_centralMergePullRequest = false;

		return _centralMergePullRequest;
	}

	protected PortalAcceptancePullRequestJob(
		BuildProfile buildProfile, String jobName,
		PortalGitWorkingDirectory portalGitWorkingDirectory,
		String testSuiteName, String upstreamBranchName) {

		super(
			buildProfile, jobName, portalGitWorkingDirectory, testSuiteName,
			upstreamBranchName);
	}

	protected PortalAcceptancePullRequestJob(JSONObject jsonObject) {
		super(jsonObject);
	}

	@Override
	protected Set<String> getRawBatchNames() {
		Set<String> batchNames = super.getRawBatchNames();

		if (_isRelevantTestSuite() &&
			!_hasMatchingFiles(_REST_BUILDER_FILE_PATTERN_STRING)) {

			batchNames.remove("rest-builder-jdk8");
		}

		if (_isRelevantTestSuite() && _hasOnlyFilesInDirectory("modules")) {
			batchNames.remove("semantic-versioning-jdk8");
		}

		if (_isRelevantTestSuite() && _hasOnlyFilesInDirectory("portal-web")) {
			String[] portalWebOnlyBatchNameMarkers = {
				"compile-jsp", "functional", "portal-web", "source-format"
			};

			Set<String> portalWebOnlyBatchNames = new TreeSet<>();

			for (String batchName : batchNames) {
				for (String portalWebOnlyBatchNameMarker :
						portalWebOnlyBatchNameMarkers) {

					if (batchName.contains(portalWebOnlyBatchNameMarker)) {
						portalWebOnlyBatchNames.add(batchName);

						break;
					}
				}
			}

			return portalWebOnlyBatchNames;
		}

		return batchNames;
	}

	private boolean _hasMatchingFiles(String pattern) {
		GitWorkingDirectory gitWorkingDirectory = getGitWorkingDirectory();

		for (File modifiedFile : gitWorkingDirectory.getModifiedFilesList()) {
			if (modifiedFile.getPath(
				).matches(
					pattern
				)) {

				return true;
			}
		}

		return false;
	}

	private boolean _hasOnlyFilesInDirectory(String directoryName) {
		GitWorkingDirectory gitWorkingDirectory = getGitWorkingDirectory();

		File directory = new File(
			gitWorkingDirectory.getWorkingDirectory(), directoryName);

		for (File modifiedFile : gitWorkingDirectory.getModifiedFilesList()) {
			if (!JenkinsResultsParserUtil.isFileInDirectory(
					directory, modifiedFile)) {

				return false;
			}
		}

		return true;
	}

	private boolean _isRelevantTestSuite() {
		String testSuiteName = getTestSuiteName();

		return testSuiteName.equals("relevant");
	}

	private static final String _REST_BUILDER_FILE_PATTERN_STRING =
		JenkinsResultsParserUtil.combine(
			"(.+)?rest-openapi(.+)?.yaml|", "(.+)?rest-config(.+)?.yaml|",
			"(.+)?portal-tools-rest-builder(.+)?");

	private Boolean _centralMergePullRequest;

}