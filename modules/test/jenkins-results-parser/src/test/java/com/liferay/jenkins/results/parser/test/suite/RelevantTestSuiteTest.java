/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.suite;

import com.liferay.jenkins.results.parser.GitWorkingDirectoryFactory;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.Job;
import com.liferay.jenkins.results.parser.JobFactory;
import com.liferay.jenkins.results.parser.PortalAcceptancePullRequestJob;
import com.liferay.jenkins.results.parser.PortalGitWorkingDirectory;
import com.liferay.jenkins.results.parser.job.property.JobProperty;
import com.liferay.jenkins.results.parser.test.batch.JUnitTestBatch;
import com.liferay.jenkins.results.parser.test.batch.JUnitTestSelector;
import com.liferay.jenkins.results.parser.test.batch.PlaywrightTestBatch;
import com.liferay.jenkins.results.parser.test.batch.PlaywrightTestSelector;
import com.liferay.jenkins.results.parser.test.batch.TestBatch;

import java.io.File;
import java.io.IOException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Kenji Heigel
 */
public class RelevantTestSuiteTest {

	@After
	public void tearDown() {
		RelevantRuleEngine.clear();
	}

	@Test
	public void testJUnitTestSelectorMerge() throws IOException {
		RelevantTestSuite relevantTestSuite = new RelevantTestSuite(
			_portalAcceptancePullRequestJob);

		relevantTestSuite.setModifiedFiles(
			Arrays.asList(
				new File(_baseDir, "text_file_0.txt"),
				new File(_baseDir, "modules/module-1/text_file_1.txt")));

		RelevantRuleEngine relevantRuleEngine =
			RelevantRuleEngine.getInstance();

		relevantRuleEngine.setBaseDir(_baseDir);

		JUnitTestBatch jUnitTestBatch = null;

		for (TestBatch testBatch : relevantTestSuite.getTestBatches()) {
			if (testBatch instanceof JUnitTestBatch) {
				jUnitTestBatch = (JUnitTestBatch)testBatch;

				break;
			}
		}

		JUnitTestSelector jUnitTestSelector = jUnitTestBatch.getTestSelector();

		List<JobProperty> includesJobProperties =
			jUnitTestSelector.getIncludesJobProperties();

		String globs = JenkinsResultsParserUtil.read(
			new File(_baseDir, "modules/module-1/text_file_1.txt"));

		int globCount = 0;

		for (JobProperty jobProperty : includesJobProperties) {
			String jobPropertyValue = jobProperty.getValue();

			for (String glob : jobPropertyValue.split(",")) {
				Assert.assertTrue(globs.contains(glob));

				globCount++;
			}
		}

		Assert.assertEquals(5, globCount);
	}

	@Test
	public void testPlaywrightTestSelectorMerge() {
		RelevantTestSuite relevantTestSuite = new RelevantTestSuite(
			_portalAcceptancePullRequestJob);

		relevantTestSuite.setModifiedFiles(
			Arrays.asList(
				new File(_baseDir, "modules/module-1/text_file_1.txt"),
				new File(_baseDir, "modules/module-2/text_file_2.txt")));

		RelevantRuleEngine relevantRuleEngine =
			RelevantRuleEngine.getInstance();

		relevantRuleEngine.setBaseDir(_baseDir);

		PlaywrightTestBatch playwrightTestBatch = null;

		for (TestBatch testBatch : relevantTestSuite.getTestBatches()) {
			if (testBatch instanceof PlaywrightTestBatch) {
				playwrightTestBatch = (PlaywrightTestBatch)testBatch;

				break;
			}
		}

		PlaywrightTestSelector playwrightTestSelector =
			playwrightTestBatch.getTestSelector();

		Set<String> expectedPlaywrightProjectNames = new HashSet<>(
			Arrays.asList(
				"module-1-playwright-project", "module-2-playwright-project"));

		Assert.assertEquals(
			expectedPlaywrightProjectNames,
			playwrightTestSelector.getPlaywrightProjectNames());
	}

	private static File _getPortalDir(File file) {
		if (file == null) {
			file = new File(".");

			file = JenkinsResultsParserUtil.getCanonicalFile(file);
		}

		String fileName = file.getName();

		if (fileName.equals("liferay-portal")) {
			return file;
		}

		file = file.getParentFile();

		if (file == null) {
			throw new RuntimeException(
				"Unable to find portal directory from: " + file);
		}

		return _getPortalDir(file);
	}

	private static final File _baseDir;
	private static final PortalAcceptancePullRequestJob
		_portalAcceptancePullRequestJob;

	static {
		File baseDir = new File(
			"src/test/resources/dependencies/test/suite" +
				"/RelevantRuleEngineTest");

		_baseDir = JenkinsResultsParserUtil.getCanonicalFile(baseDir);

		String upstreamBranchName = "master";
		String repositoryName = "liferay-portal";

		PortalGitWorkingDirectory portalGitWorkingDirectory =
			(PortalGitWorkingDirectory)
				GitWorkingDirectoryFactory.newGitWorkingDirectory(
					upstreamBranchName, _getPortalDir(null), repositoryName);

		_portalAcceptancePullRequestJob =
			(PortalAcceptancePullRequestJob)JobFactory.newJob(
				Job.BuildProfile.DXP,
				"test-portal-acceptance-pullrequest(master)", null,
				portalGitWorkingDirectory, upstreamBranchName, null,
				repositoryName, "relevant", upstreamBranchName);
	}

}