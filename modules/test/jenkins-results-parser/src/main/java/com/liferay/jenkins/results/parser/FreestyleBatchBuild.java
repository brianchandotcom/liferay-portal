/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.net.MalformedURLException;
import java.net.URL;

import org.dom4j.Element;

/**
 * @author Michael Hashimoto
 */
public class FreestyleBatchBuild extends BaseBuild {

	@Override
	public void addTimelineData(TimelineData timelineData) {
		timelineData.addTimelineData(this);
	}

	@Override
	public URL getArtifactsBaseURL() {
		TopLevelBuild topLevelBuild = getTopLevelBuild();

		StringBuilder sb = new StringBuilder();

		sb.append(topLevelBuild.getArtifactsBaseURL());
		sb.append("/");
		sb.append(getJobVariant());

		try {
			return new URL(sb.toString());
		}
		catch (MalformedURLException malformedURLException) {
			return null;
		}
	}

	@Override
	public String getBuildName() {
		return getJobVariant();
	}

	@Override
	public String getJobVariant() {
		return getParameterValue("RUN_ID");
	}

	protected FreestyleBatchBuild(String url) {
		this(url, null);
	}

	protected FreestyleBatchBuild(String url, TopLevelBuild topLevelBuild) {
		super(url, topLevelBuild);
	}

	@Override
	protected Element getGitHubMessageJobResultsElement() {
		String result = getResult();

		int failCount = 0;
		int successCount = 0;

		if (result.equals("UNSTABLE")) {
			failCount = getTestCountByStatus("FAILURE");
			successCount = getTestCountByStatus("SUCCESS");
		}

		return Dom4JUtil.getNewElement(
			"div", null, Dom4JUtil.getNewElement("h6", null, "Job Results:"),
			Dom4JUtil.getNewElement(
				"p", null, String.valueOf(successCount),
				JenkinsResultsParserUtil.getNounForm(
					successCount, " Tests", " Test"),
				" Passed.", Dom4JUtil.getNewElement("br"),
				String.valueOf(failCount),
				JenkinsResultsParserUtil.getNounForm(
					failCount, " Tests", " Test"),
				" Failed."));
	}

}