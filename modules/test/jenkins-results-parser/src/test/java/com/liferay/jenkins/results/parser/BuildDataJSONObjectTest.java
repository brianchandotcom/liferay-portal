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

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Leslie Wong
 */
public class BuildDataJSONObjectTest
	extends com.liferay.jenkins.results.parser.Test {

	@Test
	public void testGetBuildDataMap() throws IOException {
		File file = new File(dependenciesDirs.get(0), "test.json");

		BuildDataJSONObject buildDataJSONObject = new BuildDataJSONObject(
			JenkinsResultsParserUtil.read(file));

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
	public void testGetBuildDataMapWithPattern() throws IOException {
		File file = new File(dependenciesDirs.get(0), "test.json");

		BuildDataJSONObject buildDataJSONObject = new BuildDataJSONObject(
			JenkinsResultsParserUtil.read(file));

		Map<String, String> actualMap = buildDataJSONObject.getBuildDataMap(
			"git.portal.properties", _localPropertyNamePattern);

		Map<String, String> expectedMap = new HashMap<>();

		expectedMap.put("local.git.branch.cached", "true");

		Assert.assertEquals(expectedMap, actualMap);
	}

	private static final Pattern _localPropertyNamePattern = Pattern.compile(
		".*local\\..*");

}