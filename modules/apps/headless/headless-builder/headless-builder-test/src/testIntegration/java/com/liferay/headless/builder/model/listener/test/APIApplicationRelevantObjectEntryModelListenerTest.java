/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
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

import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

/**
 * @author Sergio Jiménez del Coso
 */
@FeatureFlags({"LPS-167253", "LPS-178642"})
public class APIApplicationRelevantObjectEntryModelListenerTest
	extends BaseTestCase {

	@Test
	public void test() throws Exception {
		JSONAssert.assertEquals(
			JSONUtil.put(
				"status", "BAD_REQUEST"
			).put(
				"title",
				"Base URL can have a maximum of 255 alphanumeric characters."
			).toString(),
			HTTPTestUtil.invokeToJSONObject(
				JSONUtil.put(
					"applicationStatus", "unpublished"
				).put(
					"baseURL",
					StringUtil.toLowerCase(RandomTestUtil.randomString()) +
						StringPool.FORWARD_SLASH
				).put(
					"title", RandomTestUtil.randomString()
				).toString(),
				"headless-builder/applications", Http.Method.POST
			).toString(),
			JSONCompareMode.STRICT);
		JSONAssert.assertEquals(
			JSONUtil.put(
				"status", "BAD_REQUEST"
			).put(
				"title",
				"Base URL can have a maximum of 255 alphanumeric characters."
			).toString(),
			HTTPTestUtil.invokeToJSONObject(
				JSONUtil.put(
					"applicationStatus", "unpublished"
				).put(
					"baseURL",
					StringUtil.toLowerCase(RandomTestUtil.randomString(256)) +
						StringPool.FORWARD_SLASH
				).put(
					"title", RandomTestUtil.randomString()
				).toString(),
				"headless-builder/applications", Http.Method.POST
			).toString(),
			JSONCompareMode.STRICT);
		JSONAssert.assertEquals(
			JSONUtil.put(
				"status", "BAD_REQUEST"
			).put(
				"title", "Base URL must contain only lower case characters."
			).toString(),
			HTTPTestUtil.invokeToJSONObject(
				JSONUtil.put(
					"applicationStatus", "unpublished"
				).put(
					"baseURL",
					RandomTestUtil.randomString(
						255
					).toUpperCase()
				).put(
					"title", RandomTestUtil.randomString()
				).toString(),
				"headless-builder/applications", Http.Method.POST
			).toString(),
			JSONCompareMode.STRICT);

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"applicationStatus", "unpublished"
			).put(
				"baseURL", StringUtil.toLowerCase(RandomTestUtil.randomString())
			).put(
				"title", RandomTestUtil.randomString()
			).toString(),
			"headless-builder/applications", Http.Method.POST);

		Assert.assertEquals(
			0,
			jsonObject.getJSONObject(
				"status"
			).get(
				"code"
			));
	}

}