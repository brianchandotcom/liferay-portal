/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.lang.reflect.Proxy;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Michael Hashimoto
 */
public class BuildRunnerFactory {

	public static BuildRunner<?> newBuildRunner(BuildData buildData) {
		String jobName = buildData.getJobName();

		BuildRunner<?> buildRunner = null;

		if (jobName.equals("root-cause-analysis-tool")) {
			buildRunner = new RootCauseAnalysisToolTopLevelBuildRunner(
				(PortalTopLevelBuildData)buildData);
		}

		if ((buildRunner == null) &&
			jobName.equals("root-cause-analysis-tool-batch")) {

			buildRunner = new RootCauseAnalysisBatchBuildRunner(
				(PortalBatchBuildData)buildData);
		}

		if ((buildRunner == null) &&
			jobName.startsWith("test-portal-testsuite-upstream-controller(")) {

			Matcher matcher = _jobNamePattern.matcher(jobName);

			if (matcher.find() && (matcher.group("testSuiteName") != null)) {
				buildRunner =
					new PortalTestSuiteUpstreamControllerSingleSuiteBuildRunner(
						(PortalTestSuiteUpstreamControllerBuildData)buildData);
			}
			else {
				buildRunner = new PortalTestSuiteUpstreamControllerBuildRunner(
					(PortalTestSuiteUpstreamControllerBuildData)buildData);
			}
		}

		if ((buildRunner == null) && jobName.equals("test-poshi-release")) {
			buildRunner = new PoshiReleasePortalTopLevelBuildRunner(
				(PortalTopLevelBuildData)buildData);
		}

		if ((buildRunner == null) &&
			(jobName.startsWith(
				"test-qa-websites-functional-daily-controller") ||
			 jobName.startsWith(
				 "test-qa-websites-functional-weekly-controller"))) {

			buildRunner = new QAWebsitesControllerBuildRunner(buildData);
		}

		if ((buildRunner == null) &&
			jobName.startsWith("test-results-consistency-report-controller")) {

			buildRunner = new TestResultsConsistencyReportControllerBuildRunner(
				(BaseBuildData)buildData);
		}

		if ((buildRunner == null) && jobName.contains("-batch")) {
			buildRunner = new DefaultPortalBatchBuildRunner(
				(PortalBatchBuildData)buildData);
		}

		if (buildRunner == null) {
			throw new RuntimeException("Invalid build data " + buildData);
		}

		return (BuildRunner<?>)Proxy.newProxyInstance(
			BuildRunner.class.getClassLoader(),
			new Class<?>[] {BuildRunner.class}, new MethodLogger(buildRunner));
	}

	private static final Pattern _jobNamePattern = Pattern.compile(
		"[^\\(]+\\((?<upstreamBranchName>[^_]+)" +
			"(_(?<testSuiteName>[^\\)]+))?\\)");

}