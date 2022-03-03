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
		else if (jobName.equals("root-cause-analysis-tool-batch")) {
			buildRunner = new RootCauseAnalysisBatchBuildRunner(
				(PortalBatchBuildData)buildData);
		}
		else if (jobName.contains("-batch")) {
			buildRunner = new DefaultPortalBatchBuildRunner(
				(PortalBatchBuildData)buildData);
		}

		if (jobName.startsWith("test-portal-testsuite-upstream-controller(")) {
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

		if (jobName.startsWith(
				"test-qa-websites-functional-daily-controller")) {

			buildRunner = new QAWebsitesControllerBuildRunner(buildData);
		}

		if (jobName.startsWith("test-results-consistency-report-controller")) {
			buildRunner = new TestResultsConsistencyReportControllerBuildRunner(
				(BaseBuildData)buildData);
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