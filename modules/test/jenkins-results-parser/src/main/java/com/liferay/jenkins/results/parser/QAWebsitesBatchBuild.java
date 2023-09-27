/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

/**
 * @author Michael Hashimoto
 */
public class QAWebsitesBatchBuild extends BatchBuild {

	@Override
	public String getBatchName() {
		String jobName = getJobName();

		if (jobName.contains("firefox")) {
			return "functional-firefox-jdk8";
		}

		if (jobName.contains("internet.explorer")) {
			return "functional-ie-jdk8";
		}

		return "functional-chrome-jdk8";
	}

	protected QAWebsitesBatchBuild(
		String url, TopLevelBuild parentTopLevelBuild) {

		super(JenkinsResultsParserUtil.getLocalURL(url), parentTopLevelBuild);

		findDownstreamBuilds();
	}

}