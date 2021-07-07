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

package com.liferay.learn.resources;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.net.HttpURLConnection;
import java.net.URL;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.LinkedList;
import java.util.Queue;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class LearnResourcesTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testURLConnectivity() throws Exception {
		Path learnResourcesPath = Paths.get("../../../", "learn-resources");

		learnResourcesPath = learnResourcesPath.toAbsolutePath();
		learnResourcesPath = learnResourcesPath.normalize();

		Assert.assertTrue(Files.exists(learnResourcesPath));

		boolean useHead = false;

		for (Path path :
				Files.newDirectoryStream(learnResourcesPath, "*.json")) {

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				new String(Files.readAllBytes(path)));

			Queue<JSONObject> queue = new LinkedList<>();

			queue.add(jsonObject);

			while ((jsonObject = queue.poll()) != null) {
				for (String key : jsonObject.keySet()) {
					if (key.equals("url")) {
						URL url = new URL(jsonObject.getString(key));

						HttpURLConnection httpURLConnection =
							(HttpURLConnection)url.openConnection();

						httpURLConnection.addRequestProperty(
							HttpHeaders.USER_AGENT,
							"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:221.0) " +
								"Gecko/20100101 Firefox/31.0");

						if (useHead) {
							httpURLConnection.setRequestMethod("HEAD");

							useHead = true;
						}

						Assert.assertEquals(
							StringBundler.concat(
								"URL ", url, " in ", path, " is unreachable"),
							HttpURLConnection.HTTP_OK,
							httpURLConnection.getResponseCode());
					}
					else {
						JSONObject valueJSONObject = jsonObject.getJSONObject(
							key);

						if (valueJSONObject != null) {
							queue.add(valueJSONObject);
						}
					}
				}
			}
		}
	}

}