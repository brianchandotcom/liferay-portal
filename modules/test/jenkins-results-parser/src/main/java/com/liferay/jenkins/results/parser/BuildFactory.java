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

/**
 * @author Peter Yoo
 */
public class BuildFactory {

	public static Build newBuild(String url, Build parentBuild) {
		url = JenkinsResultsParserUtil.getLocalURL(url);

		if (url.contains("AXIS_VARIABLE=")) {
			return new AxisBuild(url, (BatchBuild)parentBuild);
		}

		if (url.contains("-source")) {
			return new SourceBuild(url, parentBuild);
		}

		for (String batchIndicator : _BATCH_INDICATORS) {
			if (url.contains(batchIndicator)) {
				BatchBuild batchBuild = new BatchBuild(
					url, (TopLevelBuild)parentBuild);

				String jobVariant = batchBuild.getParameterValue("JOB_VARIANT");

				if (jobVariant != null) {
					if (jobVariant.contains("functional")) {
						batchBuild = new FunctionalBatchBuild(
							url, (TopLevelBuild)parentBuild);
					}

					if (jobVariant.contains("modules-integration")) {
						batchBuild = new ModulesIntegrationBatchBuild(
							url, (TopLevelBuild)parentBuild);
					}
				}

				return batchBuild;
			}
		}

		TopLevelBuild topLevelBuild = new TopLevelBuild(
			url, (TopLevelBuild)parentBuild);

		String jobName = topLevelBuild.getJobName();

		if (jobName.equals("test-portal-acceptance-pullrequest(ee-6.2.x)")) {
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

	private static final String[] _BATCH_INDICATORS =
		{"-batch", "-dist", "environment-"};

}