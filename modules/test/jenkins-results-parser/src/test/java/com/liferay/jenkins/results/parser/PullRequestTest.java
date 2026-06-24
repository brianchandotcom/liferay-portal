/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.util.Arrays;
import java.util.Iterator;

import org.json.JSONObject;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Kenji Heigel
 */
public class PullRequestTest extends com.liferay.jenkins.results.parser.Test {

	@Test
	public void testGetCIMergeSHA() throws Exception {
		PullRequest pullRequest = _newPullRequest();

		UrlReader urlReader = mockUrlReader();

		setUrlReaderOutput(
			JenkinsResultsParserUtil.combine(
				"[{\"filename\": \"test/ci-merge\", ",
				"\"patch\": \"+abcdef0123456789abcdef0123456789abcdef01\"}]"),
			"/files", urlReader);

		Assert.assertEquals(
			"abcdef0123456789abcdef0123456789abcdef01",
			pullRequest.getCIMergeSHA());
	}

	@Test
	public void testGetFileNames() throws Exception {
		PullRequest pullRequest = _newPullRequest();

		UrlReader urlReader = mockUrlReader();

		setUrlReaderOutput(
			JenkinsResultsParserUtil.combine(
				"[{\"filename\": \"modules/apps/foo/Foo.java\"}, ",
				"{\"filename\": \"portal-impl/Bar.java\"}]"),
			"/files", urlReader);

		Assert.assertEquals(
			Arrays.asList("modules/apps/foo/Foo.java", "portal-impl/Bar.java"),
			pullRequest.getFileNames());
	}

	private PullRequest _newPullRequest() {
		BuildDatabase buildDatabase =
			BuildDatabaseTestUtil.newBuildDatabaseWithPullRequest();

		BuildDatabaseUtil.setBuildDatabase(buildDatabase);

		JSONObject jsonObject = buildDatabase.getJSONObject();

		JSONObject pullRequestsJSONObject = jsonObject.getJSONObject(
			"pull_requests");

		Iterator<String> iterator = pullRequestsJSONObject.keys();

		return PullRequestFactory.newPullRequest(iterator.next(), null);
	}

}