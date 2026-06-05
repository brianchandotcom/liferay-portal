/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

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
		BuildDatabaseUtil.clearInstances();
	}

	@Test
	public void testNewPullRequestReadsFromInjectedBuildDatabase() {
		BuildDatabase inMemoryBuildDatabase =
			BuildDatabaseTestUtil.addPortalAcceptancePR();

		PullRequest seededPullRequest = new PullRequest(
			_newPullRequestJSONObject(_PULL_REQUEST_URL, _PULL_REQUEST_NUMBER));

		inMemoryBuildDatabase.putPullRequest(
			_PULL_REQUEST_URL, seededPullRequest);

		BuildDatabaseUtil.setBuildDatabase(inMemoryBuildDatabase);

		PullRequest pullRequest = PullRequestFactory.newPullRequest(
			_PULL_REQUEST_URL, null);

		Assert.assertEquals(
			String.valueOf(_PULL_REQUEST_NUMBER), pullRequest.getNumber());
		Assert.assertEquals(_PULL_REQUEST_URL, pullRequest.getHtmlURL());
		Assert.assertEquals(_OWNER_LOGIN, pullRequest.getOwnerUsername());
	}

	private JSONObject _newPullRequestJSONObject(String htmlURL, int number) {
		JSONObject ownerJSONObject = new JSONObject();

		ownerJSONObject.put("login", _OWNER_LOGIN);

		JSONObject repoJSONObject = new JSONObject();

		repoJSONObject.put(
			"name", _REPO_NAME
		).put(
			"owner", ownerJSONObject
		);

		JSONObject baseJSONObject = new JSONObject();

		baseJSONObject.put("repo", repoJSONObject);

		JSONObject jsonObject = new JSONObject();

		jsonObject.put(
			"base", baseJSONObject
		).put(
			"html_url", htmlURL
		).put(
			"number", number
		);

		return jsonObject;
	}

	private static final String _OWNER_LOGIN = "test-pr-factory-owner";

	private static final int _PULL_REQUEST_NUMBER = 999991;

	private static final String _PULL_REQUEST_URL =
		"https://github.com/test-pr-factory-owner/test-pr-factory-repo/pull/" +
			_PULL_REQUEST_NUMBER;

	private static final String _REPO_NAME = "test-pr-factory-repo";

}