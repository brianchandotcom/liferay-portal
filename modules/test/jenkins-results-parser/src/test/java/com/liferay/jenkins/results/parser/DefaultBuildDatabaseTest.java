/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.io.File;

import java.util.Iterator;

import org.json.JSONObject;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Calum Ragan
 */
public class DefaultBuildDatabaseTest {

	@Test
	public void testGetPullRequest() {
		BuildDatabase buildDatabase =
			BuildDatabaseTestUtil.newBuildDatabaseWithPullRequest();

		File buildDatabaseFile = buildDatabase.getBuildDatabaseFile();

		DefaultBuildDatabase reloadedBuildDatabase = new DefaultBuildDatabase(
			buildDatabaseFile.getParentFile());

		JSONObject jsonObject = reloadedBuildDatabase.getJSONObject();

		JSONObject pullRequestsJSONObject = jsonObject.getJSONObject(
			"pull_requests");

		Iterator<String> iterator = pullRequestsJSONObject.keys();

		String pullRequestURL = iterator.next();

		PullRequest pullRequest = reloadedBuildDatabase.getPullRequest(
			pullRequestURL);

		Assert.assertEquals(pullRequestURL, pullRequest.getHtmlURL());
	}

}