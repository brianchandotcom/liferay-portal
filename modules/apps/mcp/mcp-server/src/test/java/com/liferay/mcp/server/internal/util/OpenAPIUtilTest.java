/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mcp.server.internal.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import io.modelcontextprotocol.spec.McpSchema;

import java.util.Collections;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author Alejandro Tardín
 */
public class OpenAPIUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() throws Exception {
		_openAPIJSON = StringUtil.read(
			OpenAPIUtilTest.class.getResourceAsStream(
				"dependencies/openapi.json"));
	}

	@Test
	public void testGetHttpCallArguments() throws Exception {
		_testGetHttpCallArguments(
			Collections.emptyMap(), "GET /test/v1.0/items", null, "GET",
			"http://localhost/test/v1.0/items");
		_testGetHttpCallArguments(
			HashMapBuilder.<String, Object>put(
				"fields", ""
			).build(),
			"GET /test/v1.0/items", null, "GET",
			"http://localhost/test/v1.0/items");
		_testGetHttpCallArguments(
			LinkedHashMapBuilder.<String, Object>put(
				"fields", "name"
			).put(
				"page", "1"
			).put(
				"pageSize", "20"
			).build(),
			"GET /test/v1.0/items", null, "GET",
			"http://localhost/test/v1.0/items?fields=name&page=1&pageSize=20");
		_testGetHttpCallArguments(
			HashMapBuilder.<String, Object>put(
				"filter", "name eq 'John Doe'"
			).build(),
			"GET /test/v1.0/items", null, "GET",
			"http://localhost/test/v1.0/items?filter=name+eq+%27John+Doe%27");
		_testGetHttpCallArguments(
			HashMapBuilder.<String, Object>put(
				"fields", "name"
			).put(
				"itemId", "123"
			).build(),
			"GET /test/v1.0/items/{itemId}", null, "GET",
			"http://localhost/test/v1.0/items/123?fields=name");
		_testGetHttpCallArguments(
			HashMapBuilder.<String, Object>put(
				"itemId", "123"
			).build(),
			"GET /test/v1.0/items/{itemId}", null, "GET",
			"http://localhost/test/v1.0/items/123");
		_testGetHttpCallArguments(
			HashMapBuilder.<String, Object>put(
				"itemId", "456"
			).build(),
			"GET /test/v1.0/items/{itemId}", null, "GET",
			"http://localhost/test/v1.0/items/456");
		_testGetHttpCallArguments(
			HashMapBuilder.<String, Object>put(
				"body", "{\"name\": \"Test\"}"
			).build(),
			"POST /test/v1.0/items", "{\"name\": \"Test\"}", "POST",
			"http://localhost/test/v1.0/items");
		_testGetHttpCallArguments(
			HashMapBuilder.<String, Object>put(
				"body",
				HashMapBuilder.<String, Object>put(
					"name", "Test"
				).build()
			).build(),
			"POST /test/v1.0/items", "{\"name\": \"Test\"}", "POST",
			"http://localhost/test/v1.0/items");
	}

	@Test
	public void testGetOpenAPIURL() {
		AssertUtils.assertFailure(
			IllegalArgumentException.class,
			"Endpoint has no base path: GET /single",
			() -> OpenAPIUtil.getOpenAPIURL("GET /single"));
		AssertUtils.assertFailure(
			IllegalArgumentException.class,
			"Endpoint has no method/path separator: ",
			() -> OpenAPIUtil.getOpenAPIURL(""));
		AssertUtils.assertFailure(
			IllegalArgumentException.class,
			"Endpoint has no method/path separator: INVALID",
			() -> OpenAPIUtil.getOpenAPIURL("INVALID"));
		AssertUtils.assertFailure(
			IllegalArgumentException.class,
			"Endpoint has no method/path separator: no-space",
			() -> OpenAPIUtil.getOpenAPIURL("no-space"));

		Assert.assertEquals(
			"/c/test/openapi.json",
			OpenAPIUtil.getOpenAPIURL("GET /c/test/items"));
		Assert.assertEquals(
			"/headless-delivery/v1.0/openapi.json",
			OpenAPIUtil.getOpenAPIURL(
				"GET /headless-delivery/v1.0/blog-postings"));
		Assert.assertEquals(
			"/test/v1.0/openapi.json",
			OpenAPIUtil.getOpenAPIURL("DELETE /test/v1.0/items/{itemId}/sub"));
		Assert.assertEquals(
			"/test/v1.0/openapi.json",
			OpenAPIUtil.getOpenAPIURL("GET /test/v1.0"));
		Assert.assertEquals(
			"/test/v1.0/openapi.json",
			OpenAPIUtil.getOpenAPIURL("GET /test/v1.0/items"));
		Assert.assertEquals(
			"/test/v1.0/openapi.json",
			OpenAPIUtil.getOpenAPIURL("POST /test/v1.0/items/{itemId}"));
	}

	@Test
	public void testGetTool() throws Exception {
		AssertUtils.assertFailure(
			IllegalArgumentException.class,
			"Endpoint has no base path: GET /single",
			() -> OpenAPIUtil.getTool("GET /single", _openAPIJSON));
		AssertUtils.assertFailure(
			IllegalArgumentException.class,
			"Endpoint has no method/path separator: INVALID",
			() -> OpenAPIUtil.getTool("INVALID", _openAPIJSON));
		AssertUtils.assertFailure(
			IllegalArgumentException.class,
			"Endpoint has no method/path separator: no-space",
			() -> OpenAPIUtil.getTool("no-space", _openAPIJSON));
		AssertUtils.assertFailure(
			IllegalArgumentException.class,
			"OpenAPI document has no 'paths' object",
			() -> OpenAPIUtil.getTool("GET /test/v1.0/items", "{}"));
		AssertUtils.assertFailure(
			IllegalArgumentException.class,
			"OpenAPI document has no path item for: /nonexistent",
			() -> OpenAPIUtil.getTool(
				"GET /test/v1.0/nonexistent", _openAPIJSON));
		AssertUtils.assertFailure(
			IllegalArgumentException.class,
			"OpenAPI path item has no operation for method: DELETE",
			() -> OpenAPIUtil.getTool("DELETE /test/v1.0/items", _openAPIJSON));
		AssertUtils.assertFailure(
			IllegalArgumentException.class,
			"Request body has no application/json content",
			() -> OpenAPIUtil.getTool("POST /test/v1.0/uploads", _openAPIJSON));
		Assert.assertThrows(
			JSONException.class,
			() -> OpenAPIUtil.getTool("GET /test/v1.0/items", "Invalid JSON"));

		_testGetTool(
			"GET /c/test/", "This is the summary", "get_c_test.json",
			"getItemsPage");
		_testGetTool(
			"GET /test/v1.0/items",
			"This is the summary. This is the description",
			"get_test_v1.0_items.json", "getItems");
		_testGetTool(
			"GET /test/v1.0/items/{itemId}", "This is the description",
			"get_test_v1.0_items_itemId.json", "getItem");
		_testGetTool(
			"PATCH /test/v1.0/items/{itemId}",
			"PATCH /test/v1.0/items/{itemId}",
			"patch_test_v1.0_items_itemId.json", "patchItem");
		_testGetTool(
			"POST /test/v1.0/items", "POST /test/v1.0/items",
			"post_test_v1.0_items.json", "postItem");
		_testGetTool(
			"PUT /test/v1.0/items/{itemId}", "PUT /test/v1.0/items/{itemId}",
			"put_test_v1.0_items_itemId.json", "putItem");
	}

	private String _read(String fileName) throws Exception {
		return StringUtil.read(
			getClass().getResourceAsStream("dependencies/" + fileName));
	}

	private void _testGetHttpCallArguments(
			Map<String, Object> arguments, String endpoint, String expectedBody,
			String expectedMethod, String expectedUrl)
		throws Exception {

		OpenAPIUtil.HttpCallArguments httpCallArguments =
			OpenAPIUtil.getHttpCallArguments(
				arguments, "http://localhost", endpoint);

		JSONAssert.assertEquals(
			expectedBody, httpCallArguments.getBody(), true);
		Assert.assertEquals(expectedMethod, httpCallArguments.getMethod());
		Assert.assertEquals(expectedUrl, httpCallArguments.getUrl());
	}

	private void _testGetTool(
			String endpoint, String expectedDescription,
			String expectedSchemaFileName, String expectedToolName)
		throws Exception {

		McpSchema.Tool tool = OpenAPIUtil.getTool(endpoint, _openAPIJSON);

		Assert.assertEquals(expectedDescription, tool.description());
		JSONAssert.assertEquals(
			_read(expectedSchemaFileName),
			new ObjectMapper(
			).writeValueAsString(
				tool.inputSchema()
			),
			true);
		Assert.assertEquals(expectedToolName, tool.name());
	}

	private static String _openAPIJSON;

}