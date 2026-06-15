/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.io.File;

import java.util.Iterator;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONObject;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Calum Ragan
 */
public class DefaultBuildDatabaseTest {

	@Test
	public void testGetJSONObject() {
		BuildDatabaseArgs buildDatabaseArgs = new BuildDatabaseArgs();

		buildDatabaseArgs.modifiedFiles = new String[] {_MODIFIED_FILE};

		buildDatabaseArgs.addProperty(
			_PROPERTIES_KEY, _PROPERTY_NAME, _PROPERTY_VALUE);

		BuildDatabase buildDatabase =
			BuildDatabaseTestUtil.addPortalAcceptancePullRequest(
				buildDatabaseArgs);

		DefaultBuildDatabase reloadedBuildDatabase = _getReloadedBuildDatabase(
			buildDatabase);

		JSONObject jsonObject = buildDatabase.getJSONObject();

		JSONObject reloadedJSONObject = reloadedBuildDatabase.getJSONObject();

		Assert.assertTrue(jsonObject.similar(reloadedJSONObject));
	}

	@Test
	public void testGetProperties() {
		BuildDatabaseArgs buildDatabaseArgs = new BuildDatabaseArgs();

		buildDatabaseArgs.addProperty(
			_PROPERTIES_KEY, _PROPERTY_NAME, _PROPERTY_VALUE);

		BuildDatabase buildDatabase =
			BuildDatabaseTestUtil.addPortalAcceptancePullRequest(
				buildDatabaseArgs);

		DefaultBuildDatabase reloadedBuildDatabase = _getReloadedBuildDatabase(
			buildDatabase);

		Assert.assertTrue(reloadedBuildDatabase.hasProperties(_PROPERTIES_KEY));

		Properties properties = reloadedBuildDatabase.getProperties(
			_PROPERTIES_KEY);

		Assert.assertEquals(
			_PROPERTY_VALUE, properties.getProperty(_PROPERTY_NAME));
	}

	@Test
	public void testGetPullRequest() {
		DefaultBuildDatabase reloadedBuildDatabase = _getReloadedBuildDatabase(
			BuildDatabaseTestUtil.addPortalAcceptancePullRequest());

		JSONObject jsonObject = reloadedBuildDatabase.getJSONObject();

		JSONObject pullRequestsJSONObject = jsonObject.getJSONObject(
			"pull_requests");

		Iterator<String> iterator = pullRequestsJSONObject.keys();

		String pullRequestURL = iterator.next();

		Assert.assertTrue(reloadedBuildDatabase.hasPullRequest(pullRequestURL));

		PullRequest pullRequest = reloadedBuildDatabase.getPullRequest(
			pullRequestURL);

		Assert.assertEquals(pullRequestURL, pullRequest.getHtmlURL());
	}

	@Test
	public void testHasJob() {
		BuildDatabaseArgs buildDatabaseArgs = new BuildDatabaseArgs();

		buildDatabaseArgs.jobKey = _JOB_KEY;
		buildDatabaseArgs.modifiedFiles = new String[] {_MODIFIED_FILE};

		BuildDatabase buildDatabase =
			BuildDatabaseTestUtil.addPortalAcceptancePullRequest(
				buildDatabaseArgs);

		DefaultBuildDatabase reloadedBuildDatabase = _getReloadedBuildDatabase(
			buildDatabase);

		Assert.assertTrue(reloadedBuildDatabase.hasJob(_JOB_KEY));

		JSONObject jsonObject = reloadedBuildDatabase.getJSONObject();

		JSONObject jobsJSONObject = jsonObject.getJSONObject("jobs");

		JSONObject jobJSONObject = jobsJSONObject.getJSONObject(_JOB_KEY);

		JSONObject branchJSONObject = jobJSONObject.getJSONObject("branch");

		JSONArray modifiedFilesJSONArray = branchJSONObject.getJSONArray(
			"modified_files");

		Assert.assertEquals(1, modifiedFilesJSONArray.length());
		Assert.assertEquals(
			_MODIFIED_FILE, modifiedFilesJSONArray.getString(0));
	}

	@Test
	public void testHasWorkspace() {
		DefaultBuildDatabase reloadedBuildDatabase = _getReloadedBuildDatabase(
			BuildDatabaseTestUtil.addPortalAcceptancePullRequest());

		JSONObject jsonObject = reloadedBuildDatabase.getJSONObject();

		JSONObject workspacesJSONObject = jsonObject.getJSONObject(
			"workspaces");

		Iterator<String> iterator = workspacesJSONObject.keys();

		Assert.assertTrue(reloadedBuildDatabase.hasWorkspace(iterator.next()));
	}

	private DefaultBuildDatabase _getReloadedBuildDatabase(
		BuildDatabase buildDatabase) {

		File buildDatabaseFile = buildDatabase.getBuildDatabaseFile();

		return new DefaultBuildDatabase(buildDatabaseFile.getParentFile());
	}

	private static final String _JOB_KEY =
		"dxp_test-portal-acceptance-pullrequest(master)_default";

	private static final String _MODIFIED_FILE = "modules/apps/foo/Bar.java";

	private static final String _PROPERTIES_KEY = "start.properties";

	private static final String _PROPERTY_NAME = "junit.test.timeout.minutes";

	private static final String _PROPERTY_VALUE = "30";

}