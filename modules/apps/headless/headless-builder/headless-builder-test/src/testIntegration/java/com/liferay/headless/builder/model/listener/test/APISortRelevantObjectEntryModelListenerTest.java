/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.builder.model.listener.test;

import com.liferay.headless.builder.test.BaseTestCase;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.util.HTTPTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.FeatureFlags;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Sergio Jiménez del Coso
 */
@FeatureFlags({"LPS-167253", "LPS-178642"})
public class APISortRelevantObjectEntryModelListenerTest extends BaseTestCase {

	@Test
	public void test() throws Exception {
		JSONObject apiApplicationJSONObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"applicationStatus", "published"
			).put(
				"baseURL", StringUtil.toLowerCase(RandomTestUtil.randomString())
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
				"path", StringPool.FORWARD_SLASH + RandomTestUtil.randomString()
			).put(
				"r_apiApplicationToAPIEndpoints_c_apiApplicationId",
				apiApplicationJSONObject.getLong("id")
			).put(
				"retrieveType", "collection"
			).put(
				"scope", "company"
			).toString(),
			"headless-builder/endpoints", Http.Method.POST);

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"objectFieldERC", RandomTestUtil.randomString()
			).put(
				"oDataSort", RandomTestUtil.randomString()
			).put(
				"order", "ascending"
			).put(
				"r_apiEndpointToAPISorts_c_apiEndpointId",
				apiEndpointJSONObject.getLong("id")
			).toString(),
			"headless-builder/sorts", Http.Method.POST);

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
				"oDataSort", RandomTestUtil.randomString()
			).put(
				"order", "ascending"
			).put(
				"r_apiEndpointToAPISorts_c_apiEndpointId",
				RandomTestUtil.randomLong()
			).toString(),
			"headless-builder/sorts", Http.Method.POST);

		Assert.assertEquals("BAD_REQUEST", jsonObject.get("status"));
		Assert.assertEquals(
			"An API sort must be related to an API endpoint.",
			jsonObject.get("title"));
	}

}