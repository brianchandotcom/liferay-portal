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

import org.apache.tools.ant.Project;

import org.json.JSONObject;

/**
 * @author Leslie Wong
 */
public class SubrepositoryTaskReport extends SubrepositoryTask {

	public SubrepositoryTaskReport(Project project, String buildURL)
		throws Exception {

		this.buildURL = buildURL;
		this.project = project;

		testReportJSONObject = JenkinsResultsParserUtil.toJSONObject(
			JenkinsResultsParserUtil.getLocalURL(
				buildURL + "testReport/api/json?tree=failCount"));

		if (testReportJSONObject.getInt("failCount") > 0) {
			result = "FAILURE";
		}
		else {
			result = "SUCCESS";
		}
	}

	@Override
	public String getGitHubMessage() throws Exception {
		StringBuilder sb = new StringBuilder();

		sb.append(UnstableMessageUtil.getUnstableMessage(project, buildURL));

		return sb.toString();
	}

	protected static JSONObject testReportJSONObject;

	protected String buildURL;
	protected Project project;

}