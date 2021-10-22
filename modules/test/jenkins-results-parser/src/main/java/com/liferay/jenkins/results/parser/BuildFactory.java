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
import java.util.regex.Matcher;

/**
 * @author Peter Yoo
 */
public class BuildFactory {

	public static Build newBuild(String url, Build parentBuild) {
		url = JenkinsResultsParserUtil.getLocalURL(url);

		Matcher matcher = _buildURLMultiPattern.find(url);

		if (matcher == null) {
			throw new IllegalArgumentException(
				"Invalid Jenkins build URL: " + url);
		}

		String axisVariable = matcher.group("axisVariable");

		if (axisVariable != null) {
			String jobVariant = JenkinsResultsParserUtil.getBuildParameter(
				url, "JOB_VARIANT");

			if ((jobVariant != null) && jobVariant.contains("cucumber")) {
				return new CucumberAxisBuild(url, (BatchBuild)parentBuild);
			}

			if ((jobVariant != null) &&
				(jobVariant.contains("functional") ||
				 jobVariant.contains("test-portal-environment") ||
				 jobVariant.contains("test-portal-fixpack-environment"))) {

				return new PoshiAxisBuild(url, (BatchBuild)parentBuild);
			}

			return new AxisBuild(url, (BatchBuild)parentBuild);
		}

		String jobName = matcher.group("jobName");

		if (jobName.contains("-controller")) {
			return new DefaultTopLevelBuild(url, (TopLevelBuild)parentBuild);
		}

		if (jobName.contains("-source-format")) {
			return new SourceFormatBuild(url, (TopLevelBuild)parentBuild);
		}

		if (jobName.contains("-source")) {
			return new SourceBuild(url, parentBuild);
		}

		if (jobName.contains("-validation")) {
			return new ValidationBuild(url, (TopLevelBuild)parentBuild);
		}

		if (jobName.contains("root-cause-analysis-tool-batch")) {
			return new FreestyleBatchBuild(url, (TopLevelBuild)parentBuild);
		}

		for (String batchToken : _TOKENS_BATCH) {
			if (jobName.contains(batchToken)) {
				if (jobName.contains("qa-websites")) {
					return new QAWebsitesBatchBuild(
						url, (TopLevelBuild)parentBuild);
				}

				return new BatchBuild(url, (TopLevelBuild)parentBuild);
			}
		}

		if (jobName.equals("root-cause-analysis-tool")) {
			return new RootCauseAnalysisToolBuild(
				url, (TopLevelBuild)parentBuild);
		}

		if (jobName.startsWith("test-portal-acceptance-pullrequest")) {
			String testSuite = null;

			try {
				testSuite = JenkinsResultsParserUtil.getBuildParameter(
					url, "CI_TEST_SUITE");
			}
			catch (RuntimeException runtimeException) {
				System.out.println(runtimeException.getMessage());
			}

			if (Objects.equals(testSuite, "bundle")) {
				return new StandaloneTopLevelBuild(
					url, (TopLevelBuild)parentBuild);
			}

			return new PullRequestPortalTopLevelBuild(
				url, (TopLevelBuild)parentBuild);
		}

		if (jobName.startsWith("test-plugins-acceptance-pullrequest")) {
			return new PullRequestPluginsTopLevelBuild(
				url, (TopLevelBuild)parentBuild);
		}

		if (jobName.equals("test-plugins-extraapps")) {
			return new ExtraAppsPluginsTopLevelBuild(
				url, (TopLevelBuild)parentBuild);
		}

		if (jobName.equals("test-plugins-marketplaceapp")) {
			return new MarketplaceAppPluginsTopLevelBuild(
				url, (TopLevelBuild)parentBuild);
		}

		if (jobName.equals("test-portal-app-release")) {
			return new PortalAppReleaseTopLevelBuild(
				url, (TopLevelBuild)parentBuild);
		}

		if (jobName.startsWith("test-portal-aws(")) {
			return new PortalAWSTopLevelBuild(url, (TopLevelBuild)parentBuild);
		}

		if (jobName.startsWith("test-portal-environment(") ||
			jobName.startsWith("test-portal-environment-release(") ||
			jobName.startsWith("test-portal-fixpack-environment(")) {

			return new PortalEnvironmentBuild(url, (TopLevelBuild)parentBuild);
		}

		if (jobName.equals("test-portal-fixpack-release")) {
			return new PortalFixpackReleasePortalTopLevelBuild(
				url, (TopLevelBuild)parentBuild);
		}

		if (jobName.equals("test-portal-hotfix-release")) {
			return new PortalHotfixReleasePortalTopLevelBuild(
				url, (TopLevelBuild)parentBuild);
		}

		if (jobName.equals("test-portal-release")) {
			return new PortalReleasePortalTopLevelBuild(
				url, (TopLevelBuild)parentBuild);
		}

		if (jobName.matches(
				"test-subrepository-acceptance-pullrequest\\(.*\\)")) {

			return new PullRequestSubrepositoryTopLevelBuild(
				url, (TopLevelBuild)parentBuild);
		}

		if (jobName.contains("plugins")) {
			return new PluginsTopLevelBuild(url, (TopLevelBuild)parentBuild);
		}

		if (jobName.contains("portal")) {
			if (jobName.contains("upstream")) {
				return new UpstreamPortalTopLevelBuild(
					url, (TopLevelBuild)parentBuild);
			}

			return new PortalTopLevelBuild(url, (TopLevelBuild)parentBuild);
		}

		if (jobName.contains("qa-websites")) {
			return new QAWebsitesTopLevelBuild(url, (TopLevelBuild)parentBuild);
		}

		return new DefaultTopLevelBuild(url, (TopLevelBuild)parentBuild);
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
		catch (IOException ioException) {
			throw new RuntimeException(
				"Unable to find archive " + archiveName, ioException);
		}

		return newBuild(
			archiveProperties.getProperty("top.level.build.url"), null);
	}

	private static final String _BUILD_URL_SUFFIX_REGEX =
		JenkinsResultsParserUtil.combine(
			"((?<axisVariable>AXIS_VARIABLE=[^,/]+(,[^/]+)?)|)/?",
			"((?<buildNumber>\\d+)|buildWithParameters\\?" +
				"(?<queryString>.*))/?");

	private static final String[] _TOKENS_BATCH = {
		"-batch", "-chrome", "-dist", "-edge", "-firefox", "-ie11", "-safari"
	};

	private static final MultiPattern _buildURLMultiPattern = new MultiPattern(
		JenkinsResultsParserUtil.combine(
			"\\w+://(?<master>[^/]+)/+job/+(?<jobName>[^/]+)/?",
			_BUILD_URL_SUFFIX_REGEX),
		JenkinsResultsParserUtil.combine(
			".*?Test/+[^/]+/+(?<master>test-[0-9]-[0-9]{1,2})/",
			"(?<jobName>[^/]+)/?", _BUILD_URL_SUFFIX_REGEX));

}