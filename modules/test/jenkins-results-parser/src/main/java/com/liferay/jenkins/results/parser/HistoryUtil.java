/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.io.IOException;

import java.net.URL;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Michael Hashimoto
 */
public class HistoryUtil {

	public static JobHistory getJobHistory(Job job) {
		URL ciHistoryURL = _getCIHistoryURL(job);

		JobHistory jobHistory = _jobHistories.get(ciHistoryURL);

		if (jobHistory == null) {
			jobHistory = new JobHistory(ciHistoryURL);

			_jobHistories.put(ciHistoryURL, jobHistory);
		}

		return jobHistory;
	}

	private static URL _getCIHistoryURL(Job job) {
		String jobName = job.getJobName();

		String testSuiteName = null;

		if (job instanceof TestSuiteJob) {
			TestSuiteJob testSuiteJob = (TestSuiteJob)job;

			testSuiteName = testSuiteJob.getTestSuiteName();
		}

		String upstreamBranchName = null;

		if (job instanceof PortalTestClassJob) {
			PortalTestClassJob portalTestClassJob = (PortalTestClassJob)job;

			PortalGitWorkingDirectory portalGitWorkingDirectory =
				portalTestClassJob.getPortalGitWorkingDirectory();

			if (portalGitWorkingDirectory != null) {
				upstreamBranchName =
					portalGitWorkingDirectory.getUpstreamBranchName();
			}
		}

		try {
			return new URL(
				JenkinsResultsParserUtil.getProperty(
					JenkinsResultsParserUtil.getBuildProperties(),
					"ci.history.json.url", jobName, testSuiteName,
					upstreamBranchName));
		}
		catch (IOException ioException) {
			ioException.printStackTrace();
		}

		return null;
	}

	private static final Map<URL, JobHistory> _jobHistories = new HashMap<>();

}