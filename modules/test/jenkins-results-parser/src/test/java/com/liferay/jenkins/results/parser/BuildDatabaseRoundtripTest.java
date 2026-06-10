/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.io.File;

import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONObject;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Calum Ragan
 */
public class BuildDatabaseRoundtripTest {

	@Test
	public void testRoundtrip() throws Exception {
		BuildDatabase stagedBuildDatabase =
			BuildDatabaseTestUtil.addPortalAcceptancePR(
				buildDatabaseArgs -> buildDatabaseArgs.addModifiedFile(
					_MODIFIED_FILE),
				buildDatabaseArgs -> buildDatabaseArgs.setProperty(
					_PROPERTIES_KEY, _PROPERTY_NAME, _PROPERTY_VALUE));

		File buildDatabaseFile = stagedBuildDatabase.getBuildDatabaseFile();

		DefaultBuildDatabase reloadedBuildDatabase = new DefaultBuildDatabase(
			buildDatabaseFile.getParentFile());

		JSONObject stagedJSONObject = stagedBuildDatabase.getJSONObject();
		JSONObject reloadedJSONObject = reloadedBuildDatabase.getJSONObject();

		Assert.assertTrue(
			"Reloaded build database did not match the staged build database",
			stagedJSONObject.similar(reloadedJSONObject));

		// getJob constructs a Git-coupled Job, so assert the staged job and its
		// modified files through the hermetic has* accessor and the re-read
		// JSON.

		Assert.assertTrue(reloadedBuildDatabase.hasJob(_JOB_KEY));

		JSONObject jobsJSONObject = reloadedJSONObject.getJSONObject("jobs");

		JSONObject jobJSONObject = jobsJSONObject.getJSONObject(_JOB_KEY);

		JSONObject branchJSONObject = jobJSONObject.getJSONObject("branch");

		JSONArray modifiedFilesJSONArray = branchJSONObject.getJSONArray(
			"modified_files");

		Assert.assertEquals(1, modifiedFilesJSONArray.length());
		Assert.assertEquals(
			_MODIFIED_FILE, modifiedFilesJSONArray.getString(0));

		// getProperties is hermetic.

		Assert.assertTrue(reloadedBuildDatabase.hasProperties(_PROPERTIES_KEY));

		Properties properties = reloadedBuildDatabase.getProperties(
			_PROPERTIES_KEY);

		Assert.assertEquals(
			_PROPERTY_VALUE, properties.getProperty(_PROPERTY_NAME));

		// getPullRequest is hermetic.

		JSONObject pullRequestsJSONObject = reloadedJSONObject.getJSONObject(
			"pull_requests");

		String pullRequestURL = pullRequestsJSONObject.keys(
		).next();

		Assert.assertTrue(reloadedBuildDatabase.hasPullRequest(pullRequestURL));

		PullRequest pullRequest = reloadedBuildDatabase.getPullRequest(
			pullRequestURL);

		Assert.assertEquals(pullRequestURL, pullRequest.getHtmlURL());

		// getWorkspace constructs a Git-coupled Workspace, so assert the staged
		// workspace through the hermetic has* accessor.

		JSONObject workspacesJSONObject = reloadedJSONObject.getJSONObject(
			"workspaces");

		for (String workspaceKey : workspacesJSONObject.keySet()) {
			Assert.assertTrue(reloadedBuildDatabase.hasWorkspace(workspaceKey));
		}
	}

	private static final String _JOB_KEY = "PortalAcceptancePullRequestJob";

	private static final String _MODIFIED_FILE = "modules/apps/foo/Bar.java";

	private static final String _PROPERTIES_KEY = "start.properties";

	private static final String _PROPERTY_NAME = "junit.test.timeout.minutes";

	private static final String _PROPERTY_VALUE = "30";

}
