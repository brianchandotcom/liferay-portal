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
import java.io.StringReader;

import java.util.Objects;
import java.util.Properties;

/**
 * @author Peter Yoo
 */
public class BuildFactory {

	public static Build newBuild(String url, Build parentBuild) {
		url = JenkinsResultsParserUtil.getLocalURL(url);

		if (url.contains("AXIS_VARIABLE=")) {
			return new AxisBuild(url, (BatchBuild)parentBuild);
		}

		if (url.contains("subrepository-source-format")) {
			return new BatchBuild(url, (TopLevelBuild)parentBuild);
		}

		if (url.contains("-source-format")) {
			return new SourceFormatBuild(url, (TopLevelBuild)parentBuild);
		}

		if (url.contains("-source")) {
			return new SourceBuild(url, parentBuild);
		}

		if (url.contains("-validation")) {
			return new ValidationBuild(url, (TopLevelBuild)parentBuild);
		}

		if (url.contains("root-cause-analysis-tool-batch")) {
			return new FreestyleBatchBuild(url, (TopLevelBuild)parentBuild);
		}

		for (String batchToken : _TOKENS_BATCH) {
			if (url.contains(batchToken)) {
				return new BatchBuild(url, (TopLevelBuild)parentBuild);
			}
		}

		TopLevelBuild topLevelBuild = new DefaultTopLevelBuild(
			url, (TopLevelBuild)parentBuild);

		String jobName = topLevelBuild.getJobName();

		if (jobName.equals("root-cause-analysis-tool")) {
			return new RootCauseAnalysisToolBuild(
				url, (TopLevelBuild)parentBuild);
		}

		if (jobName.startsWith("test-portal-acceptance-pullrequest")) {
			String testSuite = topLevelBuild.getParameterValue("CI_TEST_SUITE");

			if (Objects.equals(testSuite, "bundle")) {
				return new StandaloneTopLevelBuild(
					url, (TopLevelBuild)parentBuild);
			}

			return new PortalPullRequestTesterTopLevelBuild(
				url, (TopLevelBuild)parentBuild);
		}

		if ((parentBuild != null) &&
			jobName.equals("test-portal-acceptance-pullrequest(ee-6.2.x)")) {

			String jenkinsJobVariant = topLevelBuild.getParameterValue(
				"JENKINS_JOB_VARIANT");

			if ((jenkinsJobVariant != null) &&
				jenkinsJobVariant.equals("rebase-error")) {

				return new RebaseErrorTopLevelBuild(
					url, (TopLevelBuild)parentBuild);
			}
		}

		return topLevelBuild;
	}

	public static Build newBuildFromArchive(String archiveName) {
		String url = JenkinsResultsParserUtil.combine(
			"${dependencies.url}/", archiveName, "/", "archive.properties");

		Properties archiveProperties = new Properties();

		try {
			archiveProperties.load(
				new StringReader(
					JenkinsResultsParserUtil.toString(
						JenkinsResultsParserUtil.getLocalURL(url))));
		}
		catch (IOException ioe) {
			throw new RuntimeException(
				"Unable to find archive " + archiveName, ioe);
		}

		return newBuild(
			archiveProperties.getProperty("top.level.build.url"), null);
	}

	private static final String[] _TOKENS_BATCH = {
		"-batch", "-dist", "environment-"
	};

}