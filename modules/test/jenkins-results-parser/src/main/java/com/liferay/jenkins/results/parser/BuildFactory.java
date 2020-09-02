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
			String jobVariant = JenkinsResultsParserUtil.getBuildParameter(
				url, "JOB_VARIANT");

			if ((jobVariant != null) && jobVariant.contains("cucumber")) {
				return new CucumberAxisBuild(url, (BatchBuild)parentBuild);
			}

			if ((jobVariant != null) && jobVariant.contains("functional")) {
				return new PoshiAxisBuild(url, (BatchBuild)parentBuild);
			}

			return new AxisBuild(url, (BatchBuild)parentBuild);
		}

		if (url.contains("-controller")) {
			return new DefaultTopLevelBuild(url, (TopLevelBuild)parentBuild);
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
				if (url.contains("qa-websites")) {
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
			String testSuite = JenkinsResultsParserUtil.getBuildParameter(
				url, "CI_TEST_SUITE");

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

		if (jobName.equals("test-portal-fixpack-release")) {
			return new PortalFixpackReleasePortalTopLevelBuild(
				url, (TopLevelBuild)parentBuild);
		}

		if (jobName.equals("test-portal-release")) {
			return new PortalReleasePortalTopLevelBuild(
				url, (TopLevelBuild)parentBuild);
		}

		if (jobName.contains("plugins")) {
			return new PluginsTopLevelBuild(url, (TopLevelBuild)parentBuild);
		}

		if (jobName.contains("portal")) {
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

	private static final String[] _TOKENS_BATCH = {
		"-batch", "-dist", "environment-"
	};

}