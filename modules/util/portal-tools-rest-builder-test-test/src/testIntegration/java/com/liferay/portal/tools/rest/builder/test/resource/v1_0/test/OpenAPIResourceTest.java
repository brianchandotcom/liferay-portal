/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.rest.builder.test.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.util.HTTPTestUtil;
import com.liferay.portal.kernel.util.Http;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author Alejandro Tardín
 */
@RunWith(Arquillian.class)
public class OpenAPIResourceTest {

	@Test
	public void testGetOpenAPI() throws Exception {
		JSONAssert.assertEquals(
			JSONUtil.put(
				"components",
				JSONUtil.put(
					"schemas",
					JSONUtil.put("TestEntity", JSONUtil.put("x-test", true)))
			).toString(),
			HTTPTestUtil.invokeToJSONObject(
				null, "test/v1.0/openapi.json", Http.Method.GET
			).toString(),
			false);
	}

	@Test
	public void testGetOpenAPIFilterableFields() throws Exception {
		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			null, "test/v1.0/openapi.json", Http.Method.GET);

		JSONArray xFilterableJSONArray = jsonObject.getJSONObject(
			"components"
		).getJSONObject(
			"schemas"
		).getJSONObject(
			"TestEntity"
		).getJSONArray(
			"x-filterable"
		);

		List<String> expectedFilterableFields = Arrays.asList(
			"companyId", "creatorId", "customFields/customAttribute1",
			"customFields/customAttribute2", "customFields/customFlag",
			"dateModified", "description", "expirationDate", "folderId",
			"friendlyUrl", "id", "keywords", "priority", "published",
			"statusCode", "title", "viewCount");

		List<String> xFilterableList = new ArrayList<>();

		for (int i = 0; i < xFilterableJSONArray.length(); i++) {
			xFilterableList.add(xFilterableJSONArray.getString(i));
		}

		Assert.assertEquals(expectedFilterableFields, xFilterableList);
	}

}