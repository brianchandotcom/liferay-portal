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