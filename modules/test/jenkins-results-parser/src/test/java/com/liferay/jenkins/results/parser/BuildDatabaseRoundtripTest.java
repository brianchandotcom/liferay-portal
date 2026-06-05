/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.io.File;

import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONObject;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * @author Calum Ragan
 */
public class BuildDatabaseRoundtripTest {

	@Test
	public void testRoundtrip() throws Exception {
		BuildDatabase inMemoryBuildDatabase =
			BuildDatabaseTestUtil.addPortalAcceptancePR(
				args -> args.addModifiedFile("modules/apps/foo/Bar.java"),
				args -> args.setProperty(
					"start.properties", "junit.test.timeout.minutes", "30"));

		File buildDir = temporaryFolder.newFolder();

		DefaultBuildDatabase stagedBuildDatabase = new DefaultBuildDatabase(
			buildDir);

		stagedBuildDatabase.setJSONObject(
			inMemoryBuildDatabase.getJSONObject());

		stagedBuildDatabase.write();

		DefaultBuildDatabase reloadedBuildDatabase = new DefaultBuildDatabase(
			buildDir);

		Assert.assertEquals(
			inMemoryBuildDatabase.getJSONObject(
			).toString(),
			reloadedBuildDatabase.getJSONObject(
			).toString());

		Assert.assertTrue(reloadedBuildDatabase.hasJob(_JOB_KEY));

		JSONObject reloadedJSONObject = reloadedBuildDatabase.getJSONObject();

		JSONObject jobsJSONObject = reloadedJSONObject.getJSONObject("jobs");

		JSONObject jobJSONObject = jobsJSONObject.getJSONObject(_JOB_KEY);

		JSONObject branchJSONObject = jobJSONObject.getJSONObject("branch");

		JSONArray modifiedFilesJSONArray = branchJSONObject.getJSONArray(
			"modified_files");

		Assert.assertEquals(1, modifiedFilesJSONArray.length());
		Assert.assertEquals(
			"modules/apps/foo/Bar.java", modifiedFilesJSONArray.getString(0));

		Assert.assertTrue(
			reloadedBuildDatabase.hasProperties("start.properties"));

		Properties properties = reloadedBuildDatabase.getProperties(
			"start.properties");

		Assert.assertEquals(
			"30", properties.getProperty("junit.test.timeout.minutes"));
	}

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	private static final String _JOB_KEY = "PortalAcceptancePullRequestJob";

}