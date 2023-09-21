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
@FeatureFlags({"LPS-167253", "LPS-178642"})
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
					"retrieveType", "collection"
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
					"retrieveType", "collection"
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
					"retrieveType", "collection"
				).put(
					"scope", "company"
				).toString(),
				"headless-builder/endpoints", Http.Method.POST
			).toString(),
			JSONCompareMode.STRICT);

		JSONObject apiApplicationJSONObject1 = HTTPTestUtil.invokeToJSONObject(
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
					apiApplicationJSONObject1.getLong("id")
				).put(
					"r_requestAPISchemaToAPIEndpoints_c_apiSchemaId",
					RandomTestUtil.nextLong()
				).put(
					"r_responseAPISchemaToAPIEndpoints_c_apiSchemaId",
					RandomTestUtil.nextLong()
				).put(
					"retrieveType", "collection"
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
					"retrieveType", "collection"
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
				"Path must contain a path parameter between curly braces."
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
						StringPool.FORWARD_SLASH, StringPool.OPEN_CURLY_BRACE)
				).put(
					"pathParameter", "externalReferenceCode"
				).put(
					"retrieveType", "singleElement"
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
					"retrieveType", "collection"
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
				"Path parameter cannot be null in a single element endpoint."
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
					"retrieveType", "singleElement"
				).put(
					"scope", "company"
				).toString(),
				"headless-builder/endpoints", Http.Method.POST
			).toString(),
			JSONCompareMode.STRICT);

		JSONObject apiSchemaJSONObject1 = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"mainObjectDefinitionERC",
				_objectDefinition.getExternalReferenceCode()
			).put(
				"name", RandomTestUtil.randomString()
			).put(
				"r_apiApplicationToAPISchemas_c_apiApplicationId",
				apiApplicationJSONObject1.getLong("id")
			).toString(),
			"headless-builder/schemas", Http.Method.POST);

		JSONAssert.assertEquals(
			JSONUtil.put(
				"r_apiApplicationToAPIEndpoints_c_apiApplicationId",
				apiApplicationJSONObject1.get("id")
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
					apiApplicationJSONObject1.getLong("id")
				).put(
					"r_requestAPISchemaToAPIEndpoints_c_apiSchemaId",
					apiSchemaJSONObject1.getLong("id")
				).put(
					"r_responseAPISchemaToAPIEndpoints_c_apiSchemaId",
					apiSchemaJSONObject1.getLong("id")
				).put(
					"retrieveType", "collection"
				).put(
					"scope", "company"
				).toString(),
				"headless-builder/endpoints", Http.Method.POST
			).toString(),
			JSONCompareMode.LENIENT);
		JSONAssert.assertEquals(
			JSONUtil.put(
				"r_apiApplicationToAPIEndpoints_c_apiApplicationId",
				apiApplicationJSONObject1.get("id")
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
					StringBundler.concat(
						StringPool.FORWARD_SLASH, RandomTestUtil.randomString(),
						StringPool.FORWARD_SLASH, StringPool.OPEN_CURLY_BRACE,
						RandomTestUtil.randomString(),
						StringPool.CLOSE_CURLY_BRACE)
				).put(
					"pathParameter", "ID"
				).put(
					"r_apiApplicationToAPIEndpoints_c_apiApplicationId",
					apiApplicationJSONObject1.getLong("id")
				).put(
					"r_requestAPISchemaToAPIEndpoints_c_apiSchemaId",
					apiSchemaJSONObject1.getLong("id")
				).put(
					"r_responseAPISchemaToAPIEndpoints_c_apiSchemaId",
					apiSchemaJSONObject1.getLong("id")
				).put(
					"retrieveType", "singleElement"
				).put(
					"scope", "company"
				).toString(),
				"headless-builder/endpoints", Http.Method.POST
			).toString(),
			JSONCompareMode.LENIENT);

		String path = StringPool.FORWARD_SLASH + RandomTestUtil.randomString();

		JSONObject apiEndpointJSONObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"httpMethod", "get"
			).put(
				"name", RandomTestUtil.randomString()
			).put(
				"path", path
			).put(
				"r_apiApplicationToAPIEndpoints_c_apiApplicationId",
				apiApplicationJSONObject1.getLong("id")
			).put(
				"retrieveType", "collection"
			).put(
				"scope", "company"
			).toString(),
			"headless-builder/endpoints", Http.Method.POST);

		JSONAssert.assertEquals(
			JSONUtil.put(
				"r_apiApplicationToAPIEndpoints_c_apiApplicationId",
				apiApplicationJSONObject1.get("id")
			).toString(),
			apiEndpointJSONObject.toString(), JSONCompareMode.LENIENT);

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
					apiApplicationJSONObject1.getLong("id")
				).put(
					"retrieveType", "collection"
				).put(
					"scope", "company"
				).toString(),
				"headless-builder/endpoints", Http.Method.POST
			).toString(),
			JSONCompareMode.STRICT);

		JSONObject apiApplicationJSONObject2 = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"applicationStatus", "published"
			).put(
				"baseURL", StringUtil.toLowerCase(RandomTestUtil.randomString())
			).put(
				"title", RandomTestUtil.randomString()
			).toString(),
			"headless-builder/applications", Http.Method.POST);

		JSONObject apiSchemaJSONObject2 = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"mainObjectDefinitionERC",
				_objectDefinition.getExternalReferenceCode()
			).put(
				"name", RandomTestUtil.randomString()
			).put(
				"r_apiApplicationToAPISchemas_c_apiApplicationId",
				apiApplicationJSONObject2.getLong("id")
			).toString(),
			"headless-builder/schemas", Http.Method.POST);

		JSONAssert.assertEquals(
			JSONUtil.put(
				"status", "BAD_REQUEST"
			).put(
				"title",
				"The API endpoint and the API schema must be related to the " +
					"same API application."
			).toString(),
			HTTPTestUtil.invokeToJSONObject(
				null,
				StringBundler.concat(
					"headless-builder/schemas/",
					apiSchemaJSONObject2.getLong("id"),
					"/responseAPISchemaToAPIEndpoints/",
					apiEndpointJSONObject.getLong("id")),
				Http.Method.PUT
			).toString(),
			JSONCompareMode.STRICT);

		JSONAssert.assertEquals(
			JSONUtil.put(
				"status", "BAD_REQUEST"
			).put(
				"title",
				"The API endpoint and the API schema must be related to the " +
					"same API application."
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
					apiApplicationJSONObject1.getLong("id")
				).put(
					"r_requestAPISchemaToAPIEndpoints_c_apiSchemaId",
					apiSchemaJSONObject2.getLong("id")
				).put(
					"retrieveType", "collection"
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