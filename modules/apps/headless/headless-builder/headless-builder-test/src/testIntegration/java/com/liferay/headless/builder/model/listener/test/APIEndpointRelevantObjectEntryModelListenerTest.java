/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.builder.model.listener.test;

import com.liferay.headless.builder.test.BaseTestCase;
import com.liferay.headless.builder.util.ObjectDefinitionTestUtil;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.HTTPTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.FeatureFlags;

import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

/**
 * @author Sergio Jiménez del Coso
 */
@FeatureFlags({"LPS-167253", "LPS-184413", "LPS-186757"})
public class APIEndpointRelevantObjectEntryModelListenerTest
	extends BaseTestCase {

	@Before
	public void setUp() throws Exception {
		super.setUp();

		_objectDefinition = ObjectDefinitionTestUtil.publishObjectDefinition(
			Collections.singletonList(
				ObjectFieldUtil.createObjectField(
					"Text", "String", true, true, null,
					RandomTestUtil.randomString(),
					"x" + RandomTestUtil.randomString(), false)));
	}

	@Test
	public void test() throws Exception {
		JSONAssert.assertEquals(
			JSONUtil.put(
				"status", "BAD_REQUEST"
			).put(
				"title",
				"An API endpoint must be related to an API application."
			).toString(),
			HTTPTestUtil.invokeToJSONObject(
				JSONUtil.put(
					"httpMethod", "get"
				).put(
					"name", RandomTestUtil.randomString()
				).put(
					"path",
					StringPool.FORWARD_SLASH + RandomTestUtil.randomString()
				).put(
					"scope", "company"
				).toString(),
				"headless-builder/endpoints", Http.Method.POST
			).toString(),
			JSONCompareMode.STRICT);

		JSONAssert.assertEquals(
			JSONUtil.put(
				"status", "BAD_REQUEST"
			).put(
				"title",
				"An API endpoint must be related to an API application."
			).toString(),
			HTTPTestUtil.invokeToJSONObject(
				JSONUtil.put(
					"httpMethod", "get"
				).put(
					"name", RandomTestUtil.randomString()
				).put(
					"path",
					StringPool.FORWARD_SLASH + RandomTestUtil.randomString()
				).put(
					"r_apiApplicationToAPIEndpoints_c_apiApplicationId",
					RandomTestUtil.randomLong()
				).put(
					"scope", "company"
				).toString(),
				"headless-builder/endpoints", Http.Method.POST
			).toString(),
			JSONCompareMode.STRICT);

		JSONAssert.assertEquals(
			JSONUtil.put(
				"status", "BAD_REQUEST"
			).put(
				"title",
				"An API endpoint must be related to an API application."
			).toString(),
			HTTPTestUtil.invokeToJSONObject(
				JSONUtil.put(
					"httpMethod", "get"
				).put(
					"name", RandomTestUtil.randomString()
				).put(
					"path",
					StringPool.FORWARD_SLASH + RandomTestUtil.randomString()
				).put(
					"r_apiApplicationToAPIEndpoints_c_apiApplicationId",
					TestPropsValues.getUserId()
				).put(
					"scope", "company"
				).toString(),
				"headless-builder/endpoints", Http.Method.POST
			).toString(),
			JSONCompareMode.STRICT);

		JSONObject apiApplicationJSONObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"applicationStatus", "published"
			).put(
				"baseURL", StringUtil.toLowerCase(RandomTestUtil.randomString())
			).put(
				"title", RandomTestUtil.randomString()
			).toString(),
			"headless-builder/applications", Http.Method.POST);

		JSONAssert.assertEquals(
			JSONUtil.put(
				"status", "BAD_REQUEST"
			).put(
				"title", "An API endpoint must be related to an API schema."
			).toString(),
			HTTPTestUtil.invokeToJSONObject(
				JSONUtil.put(
					"httpMethod", "get"
				).put(
					"name", RandomTestUtil.randomString()
				).put(
					"path",
					StringPool.FORWARD_SLASH + RandomTestUtil.randomString()
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
				"headless-builder/endpoints", Http.Method.POST
			).toString(),
			JSONCompareMode.STRICT);

		JSONAssert.assertEquals(
			JSONUtil.put(
				"status", "BAD_REQUEST"
			).put(
				"title",
				"Path can have a maximum of 255 alphanumeric characters."
			).toString(),
			HTTPTestUtil.invokeToJSONObject(
				JSONUtil.put(
					"httpMethod", "get"
				).put(
					"name", RandomTestUtil.randomString()
				).put(
					"path",
					StringBundler.concat(
						StringPool.FORWARD_SLASH, RandomTestUtil.randomString(),
						StringPool.FORWARD_SLASH, RandomTestUtil.randomString(),
						StringPool.COMMA)
				).put(
					"scope", "company"
				).toString(),
				"headless-builder/endpoints", Http.Method.POST
			).toString(),
			JSONCompareMode.STRICT);
		JSONAssert.assertEquals(
			JSONUtil.put(
				"status", "BAD_REQUEST"
			).put(
				"title", "Path must start with the \"/\" character."
			).toString(),
			HTTPTestUtil.invokeToJSONObject(
				JSONUtil.put(
					"httpMethod", "get"
				).put(
					"name", RandomTestUtil.randomString()
				).put(
					"path",
					StringBundler.concat(
						RandomTestUtil.randomString(), StringPool.FORWARD_SLASH,
						StringPool.COMMA)
				).put(
					"scope", "company"
				).toString(),
				"headless-builder/endpoints", Http.Method.POST
			).toString(),
			JSONCompareMode.STRICT);

		JSONObject apiSchemaJSONObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"mainObjectDefinitionERC",
				_objectDefinition.getExternalReferenceCode()
			).put(
				"name", RandomTestUtil.randomString()
			).put(
				"r_apiApplicationToAPISchemas_c_apiApplicationId",
				apiApplicationJSONObject.getLong("id")
			).toString(),
			"headless-builder/schemas", Http.Method.POST);

		JSONAssert.assertEquals(
			JSONUtil.put(
				"r_apiApplicationToAPIEndpoints_c_apiApplicationId",
				apiApplicationJSONObject.get("id")
			).put(
				"status", JSONUtil.put("code", 0)
			).toString(),
			HTTPTestUtil.invokeToJSONObject(
				JSONUtil.put(
					"httpMethod", "get"
				).put(
					"name", RandomTestUtil.randomString()
				).put(
					"path",
					StringPool.FORWARD_SLASH + RandomTestUtil.randomString()
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
				"headless-builder/endpoints", Http.Method.POST
			).toString(),
			JSONCompareMode.LENIENT);

		String path = StringPool.FORWARD_SLASH + RandomTestUtil.randomString();

		JSONAssert.assertEquals(
			JSONUtil.put(
				"r_apiApplicationToAPIEndpoints_c_apiApplicationId",
				apiApplicationJSONObject.get("id")
			).toString(),
			HTTPTestUtil.invokeToJSONObject(
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
				"headless-builder/endpoints", Http.Method.POST
			).toString(),
			JSONCompareMode.LENIENT);
		JSONAssert.assertEquals(
			JSONUtil.put(
				"status", "BAD_REQUEST"
			).put(
				"title",
				"There is an API endpoint with the same HTTP method and path."
			).toString(),
			HTTPTestUtil.invokeToJSONObject(
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
				"headless-builder/endpoints", Http.Method.POST
			).toString(),
			JSONCompareMode.STRICT);
	}

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition;

}