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

import java.io.File;
import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Leslie Wong
 */
public class BuildDataJSONObjectTest
	extends com.liferay.jenkins.results.parser.Test {

	@Test
	public void testGetBuildDataMap()
		throws IOException, MalformedURLException {

		File file = new File(dependenciesDirs.get(0), "test.json");

		URL url = file.toURL();

		String jsonString = JenkinsResultsParserUtil.toString(url.toString());

		BuildDataJSONObject buildDataJSONObject = new BuildDataJSONObject(
			jsonString);

		Map<String, String> actualMap = buildDataJSONObject.getBuildDataMap(
			"git.portal.properties");

		Map<String, String> expectedMap = new HashMap<>();

		expectedMap.put("github.pull.request.number", "5515");
		expectedMap.put("github.receiver.username", "liferay");
		expectedMap.put("github.sender.branch.name", "pull-request");
		expectedMap.put("github.sender.username", "liferay");
		expectedMap.put("github.upstream.branch.name", "7.0.x");
		expectedMap.put("local.git.branch.cached", "true");

		Assert.assertEquals(expectedMap, actualMap);
	}

	@Test
	public void testGetBuildDataMapWithFilter()
		throws IOException, MalformedURLException {

		File file = new File(dependenciesDirs.get(0), "test.json");

		URL url = file.toURL();

		String jsonString = JenkinsResultsParserUtil.toString(url.toString());

		BuildDataJSONObject buildDataJSONObject = new BuildDataJSONObject(
			jsonString);

		Map<String, String> actualMap =
			buildDataJSONObject.getBuildDataMapWithFilter(
				"git.portal.properties", "local.");

		Map<String, String> expectedMap = new HashMap<>();

		expectedMap.put("local.git.branch.cached", "true");

		Assert.assertEquals(expectedMap, actualMap);
	}

}