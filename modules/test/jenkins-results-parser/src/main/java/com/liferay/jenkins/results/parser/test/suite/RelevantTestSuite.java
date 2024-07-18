/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.suite;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.PortalAcceptancePullRequestJob;
import com.liferay.jenkins.results.parser.PortalGitWorkingDirectory;
import com.liferay.jenkins.results.parser.test.batch.TestBatch;

import java.io.File;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Kenji Heigel
 */
public class RelevantTestSuite {

	public RelevantTestSuite(
		PortalAcceptancePullRequestJob portalAcceptancePullRequestJob) {

		PortalGitWorkingDirectory portalGitWorkingDirectory =
			portalAcceptancePullRequestJob.getPortalGitWorkingDirectory();

		_modifiedFiles = portalGitWorkingDirectory.getModifiedFilesList();

		_relevantRuleEngine = RelevantRuleEngine.getInstance(
			portalAcceptancePullRequestJob);
	}

	public List<TestBatch> getTestBatches() {
		File baseTestPropertiesFile = new File(
			_relevantRuleEngine.getBaseDir(), "test.properties");

		String testBatchNamesPropertyValue =
			JenkinsResultsParserUtil.getProperty(
				JenkinsResultsParserUtil.getProperties(baseTestPropertiesFile),
				"test.batch.names[relevant]");

		if (testBatchNamesPropertyValue == null) {
			throw new RuntimeException(
				"Please set test.batch.names[relevant] in " +
					baseTestPropertiesFile);
		}

		List<String> validTestBatchNames = Arrays.asList(
			testBatchNamesPropertyValue.split(","));

		List<TestBatch> testBatches = new ArrayList<>();

		List<RelevantRule> relevantRules =
			_relevantRuleEngine.getMatchingRelevantRules(_modifiedFiles);

		Collections.sort(relevantRules);

		for (RelevantRule relevantRule : relevantRules) {
			for (TestBatch testBatch : relevantRule.getTestBatches()) {
				if (testBatches.contains(testBatch)) {
					TestBatch existingTestBatch = testBatches.get(
						testBatches.indexOf(testBatch));

					existingTestBatch.merge(testBatch);

					continue;
				}

				if (!validTestBatchNames.isEmpty() &&
					validTestBatchNames.contains(testBatch.getName())) {

					testBatches.add(testBatch);
				}
			}
		}

		Collections.sort(testBatches);

		return testBatches;
	}

	public void setModifiedFiles(List<File> modifiedFiles) {
		_modifiedFiles = modifiedFiles;
	}

	private List<File> _modifiedFiles;
	private final RelevantRuleEngine _relevantRuleEngine;

}