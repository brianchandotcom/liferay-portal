/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.builder.model.listener.test;

import com.liferay.headless.builder.test.BaseTestCase;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.util.HTTPTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.test.rule.FeatureFlags;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Sergio Jiménez del Coso
 */
@FeatureFlags({"LPS-167253", "LPS-184413", "LPS-186757"})
public class APIEndpointRelevantObjectEntryModelListenerTest
	extends BaseTestCase {

	@Test
	public void test() throws Exception {
		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"httpMethod", "get"
			).put(
				"name", RandomTestUtil.randomString()
			).put(
				"path", StringPool.FORWARD_SLASH + RandomTestUtil.randomString()
			).put(
				"scope", "company"
			).toString(),
			"headless-builder/endpoints", Http.Method.POST);

		Assert.assertEquals("BAD_REQUEST", jsonObject.get("status"));
		Assert.assertEquals(
			"An API endpoint must be related to an API application.",
			jsonObject.get("title"));

		jsonObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"httpMethod", "get"
			).put(
				"name", RandomTestUtil.randomString()
			).put(
				"path", StringPool.FORWARD_SLASH + RandomTestUtil.randomString()
			).put(
				"r_apiApplicationToAPIEndpoints_c_apiApplicationId",
				RandomTestUtil.randomLong()
			).put(
				"scope", "company"
			).toString(),
			"headless-builder/endpoints", Http.Method.POST);

		Assert.assertEquals("BAD_REQUEST", jsonObject.get("status"));
		Assert.assertEquals(
			"An API endpoint must be related to an API application.",
			jsonObject.get("title"));

		jsonObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"httpMethod", "get"
			).put(
				"name", RandomTestUtil.randomString()
			).put(
				"path", StringPool.FORWARD_SLASH + RandomTestUtil.randomString()
			).put(
				"r_apiApplicationToAPIEndpoints_c_apiApplicationId",
				TestPropsValues.getUserId()
			).put(
				"scope", "company"
			).toString(),
			"headless-builder/endpoints", Http.Method.POST);

		Assert.assertEquals("BAD_REQUEST", jsonObject.get("status"));
		Assert.assertEquals(
			"An API endpoint must be related to an API application.",
			jsonObject.get("title"));

		JSONObject apiApplicationJSONObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"applicationStatus", "published"
			).put(
				"baseURL", RandomTestUtil.randomString()
			).put(
				"title", RandomTestUtil.randomString()
			).toString(),
			"headless-builder/applications", Http.Method.POST);

		jsonObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"httpMethod", "get"
			).put(
				"name", RandomTestUtil.randomString()
			).put(
				"path", StringPool.FORWARD_SLASH + RandomTestUtil.randomString()
			).put(
				"r_apiApplicationToAPIEndpoints_c_apiApplicationId",
				apiApplicationJSONObject.getLong("id")
			).put(
				"r_requestAPISchemaToAPIEndpoints_c_apiSchemaId",
				RandomTestUtil.nextLong()
			).put(
				"r_responseAPISchemaToAPIEndpoints_c_apiSchemaId",
				RandomTestUtil.nextLong()
			).put(
				"scope", "company"
			).toString(),
			"headless-builder/endpoints", Http.Method.POST);

		Assert.assertEquals("BAD_REQUEST", jsonObject.get("status"));
		Assert.assertEquals(
			"An API endpoint must be related to an API schema.",
			jsonObject.get("title"));

		jsonObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"httpMethod", "get"
			).put(
				"name", RandomTestUtil.randomString()
			).put(
				"path",
				StringBundler.concat(
					RandomTestUtil.randomString(), StringPool.FORWARD_SLASH,
					RandomTestUtil.randomString(), StringPool.COMMA)
			).put(
				"scope", "company"
			).toString(),
			"headless-builder/endpoints", Http.Method.POST);

		Assert.assertEquals("BAD_REQUEST", jsonObject.get("status"));
		Assert.assertEquals(
			"Path can have a maximum of 255 alphanumeric characters.",
			jsonObject.get("title"));

		JSONObject apiSchemaJSONObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"mainObjectDefinitionERC", RandomTestUtil.randomString()
			).put(
				"name", RandomTestUtil.randomString()
			).put(
				"r_apiApplicationToAPISchemas_c_apiApplicationId",
				apiApplicationJSONObject.getLong("id")
			).toString(),
			"headless-builder/schemas", Http.Method.POST);

		jsonObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"httpMethod", "get"
			).put(
				"name", RandomTestUtil.randomString()
			).put(
				"path", StringPool.FORWARD_SLASH + RandomTestUtil.randomString()
			).put(
				"r_apiApplicationToAPIEndpoints_c_apiApplicationId",
				apiApplicationJSONObject.getLong("id")
			).put(
				"r_requestAPISchemaToAPIEndpoints_c_apiSchemaId",
				apiSchemaJSONObject.getLong("id")
			).put(
				"r_responseAPISchemaToAPIEndpoints_c_apiSchemaId",
				apiSchemaJSONObject.getLong("id")
			).put(
				"scope", "company"
			).toString(),
			"headless-builder/endpoints", Http.Method.POST);

		Assert.assertEquals(
			0,
			jsonObject.getJSONObject(
				"status"
			).get(
				"code"
			));
		Assert.assertEquals(
			apiApplicationJSONObject.get("id"),
			jsonObject.get(
				"r_apiApplicationToAPIEndpoints_c_apiApplicationId"));

		String path = StringPool.FORWARD_SLASH + RandomTestUtil.randomString();

		jsonObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"httpMethod", "get"
			).put(
				"name", RandomTestUtil.randomString()
			).put(
				"path", path
			).put(
				"r_apiApplicationToAPIEndpoints_c_apiApplicationId",
				apiApplicationJSONObject.getLong("id")
			).put(
				"scope", "company"
			).toString(),
			"headless-builder/endpoints", Http.Method.POST);

		Assert.assertEquals(
			jsonObject.get("r_apiApplicationToAPIEndpoints_c_apiApplicationId"),
			apiApplicationJSONObject.get("id"));

		jsonObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"httpMethod", "get"
			).put(
				"name", RandomTestUtil.randomString()
			).put(
				"path", path
			).put(
				"r_apiApplicationToAPIEndpoints_c_apiApplicationId",
				apiApplicationJSONObject.getLong("id")
			).put(
				"scope", "company"
			).toString(),
			"headless-builder/endpoints", Http.Method.POST);

		Assert.assertEquals("BAD_REQUEST", jsonObject.get("status"));
		Assert.assertEquals(
			"There is an API endpoint with the same HTTP method and path.",
			jsonObject.get("title"));
	}

}