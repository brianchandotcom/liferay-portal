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

package com.liferay.headless.builder.model.listener.test;

import com.liferay.headless.builder.test.BaseTestCase;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.util.HTTPTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.test.rule.FeatureFlags;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Alberto Javier Moreno Lage
 */
@FeatureFlags({"LPS-167253", "LPS-184413", "LPS-186757"})
public class APIFilterRelevantObjectEntryModelListenerTest
	extends BaseTestCase {

	@Test
	public void test() throws Exception {
		JSONObject apiApplicationJSONObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"applicationStatus", "published"
			).put(
				"baseURL", RandomTestUtil.randomString()
			).put(
				"title", RandomTestUtil.randomString()
			).toString(),
			"headless-builder/applications", Http.Method.POST);

		JSONObject apiEndpointJSONObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"httpMethod", "get"
			).put(
				"name", RandomTestUtil.randomString()
			).put(
				"path", RandomTestUtil.randomString()
			).put(
				"r_apiApplicationToAPIEndpoints_c_apiApplicationId",
				apiApplicationJSONObject.getLong("id")
			).put(
				"scope", "company"
			).toString(),
			"headless-builder/endpoints", Http.Method.POST);

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"objectFieldERC", RandomTestUtil.randomString()
			).put(
				"oDataFilter", "test ne 1"
			).put(
				"r_apiEndpointToAPIFilters_c_apiEndpointId",
				apiEndpointJSONObject.getLong("id")
			).toString(),
			"headless-builder/filters", Http.Method.POST);

		Assert.assertEquals(
			0,
			jsonObject.getJSONObject(
				"status"
			).get(
				"code"
			));

		jsonObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"objectFieldERC", RandomTestUtil.randomString()
			).put(
				"oDataFilter", "test ne 1"
			).put(
				"r_apiEndpointToAPIFilters_c_apiEndpointId",
				RandomTestUtil.randomLong()
			).toString(),
			"headless-builder/filters", Http.Method.POST);

		Assert.assertEquals("BAD_REQUEST", jsonObject.get("status"));
		Assert.assertEquals(
			"An API filter must be related to an API endpoint.",
			jsonObject.get("title"));
	}

}