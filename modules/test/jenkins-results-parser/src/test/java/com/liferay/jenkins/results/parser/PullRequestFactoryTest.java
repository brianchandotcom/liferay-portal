/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.util.Iterator;

import org.json.JSONObject;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Calum Ragan
 */
public class PullRequestFactoryTest {

	@After
	public void tearDown() {
		BuildDatabaseUtil.clearBuildDatabases();
	}

	@Test
	public void testNewPullRequest() {
		BuildDatabase buildDatabase =
			BuildDatabaseTestUtil.addPortalAcceptancePR();

		BuildDatabaseUtil.setBuildDatabase(buildDatabase);

		JSONObject jsonObject = buildDatabase.getJSONObject();

		JSONObject pullRequestsJSONObject = jsonObject.getJSONObject(
			"pull_requests");

		Iterator<String> iterator = pullRequestsJSONObject.keys();

		String pullRequestURL = iterator.next();

		JSONObject pullRequestJSONObject = pullRequestsJSONObject.getJSONObject(
			pullRequestURL);

		PullRequest pullRequest = PullRequestFactory.newPullRequest(
			pullRequestURL, null);

		Assert.assertEquals(pullRequestURL, pullRequest.getHtmlURL());
		Assert.assertEquals(
			String.valueOf(pullRequestJSONObject.getInt("number")),
			pullRequest.getNumber());

		JSONObject baseJSONObject = pullRequestJSONObject.getJSONObject("base");

		JSONObject repositoryJSONObject = baseJSONObject.getJSONObject("repo");

		JSONObject ownerJSONObject = repositoryJSONObject.getJSONObject(
			"owner");

		Assert.assertEquals(
			ownerJSONObject.getString("login"), pullRequest.getOwnerUsername());
	}

}
