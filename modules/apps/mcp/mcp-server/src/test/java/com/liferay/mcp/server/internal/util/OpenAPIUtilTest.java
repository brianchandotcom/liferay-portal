/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mcp.server.internal.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.portal.kernel.util.HashMapBuilder;
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
	public void testGetHttpCallArguments() {
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
			HashMapBuilder.<String, Object>put(
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
				"fields", "name"
			).put(
				"itemId", "123"
			).build(),
			"GET /test/v1.0/items/{itemId}", null, "GET",
			"http://localhost/test/v1.0/items/123?fields=name");
		_testGetHttpCallArguments(
			HashMapBuilder.<String, Object>put(
				"filter", "name eq 'John Doe'"
			).build(),
			"GET /test/v1.0/items", null, "GET",
			"http://localhost/test/v1.0/items?filter=name+eq+%27John+Doe%27");
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
		Assert.assertNull(OpenAPIUtil.getOpenAPIURL(""));
		Assert.assertNull(OpenAPIUtil.getOpenAPIURL("INVALID"));
		Assert.assertNull(OpenAPIUtil.getOpenAPIURL("no-space"));
		Assert.assertNull(OpenAPIUtil.getOpenAPIURL("GET /single"));
		Assert.assertEquals(
			"/test/v1.0/openapi.json",
			OpenAPIUtil.getOpenAPIURL("GET /test/v1.0"));
		Assert.assertEquals(
			"/test/v1.0/openapi.json",
			OpenAPIUtil.getOpenAPIURL("GET /test/v1.0/items"));
		Assert.assertEquals(
			"/test/v1.0/openapi.json",
			OpenAPIUtil.getOpenAPIURL("POST /test/v1.0/items/{itemId}"));
		Assert.assertEquals(
			"/test/v1.0/openapi.json",
			OpenAPIUtil.getOpenAPIURL("DELETE /test/v1.0/items/{itemId}/sub"));
		Assert.assertEquals(
			"/c/test/openapi.json",
			OpenAPIUtil.getOpenAPIURL("GET /c/test/items"));
		Assert.assertEquals(
			"/headless-delivery/v1.0/openapi.json",
			OpenAPIUtil.getOpenAPIURL(
				"GET /headless-delivery/v1.0/blog-postings"));
	}

	@Test
	public void testGetTool() throws Exception {
		Assert.assertNull(OpenAPIUtil.getTool("INVALID", _openAPIJSON));
		Assert.assertNull(OpenAPIUtil.getTool("no-space", _openAPIJSON));

		Assert.assertNull(OpenAPIUtil.getTool("GET /test/v1.0/items", "{}"));

		Assert.assertNull(
			OpenAPIUtil.getTool("DELETE /test/v1.0/items", _openAPIJSON));
		Assert.assertNull(OpenAPIUtil.getTool("GET /single", _openAPIJSON));
		Assert.assertNull(
			OpenAPIUtil.getTool("GET /test/v1.0/nonexistent", _openAPIJSON));
		Assert.assertNull(
			OpenAPIUtil.getTool("POST /test/v1.0/uploads", _openAPIJSON));

		_testGetTool(
			"GET /c/test/", "get_c_test.json", "getItemsPage", "Get items");
		_testGetTool(
			"GET /test/v1.0/items", "get_test_v1.0_items.json", "getItems",
			"Get all items. Returns a list");
		_testGetTool(
			"GET /test/v1.0/items/{itemId}", "get_test_v1.0_items_itemId.json",
			"getItem", "Get an item by ID");
		_testGetTool(
			"PATCH /test/v1.0/items/{itemId}",
			"patch_test_v1.0_items_itemId.json", "patchItem",
			"PATCH /test/v1.0/items/{itemId}");
		_testGetTool(
			"POST /test/v1.0/items", "post_test_v1.0_items.json", "postItem",
			"POST /test/v1.0/items");
		_testGetTool(
			"PUT /test/v1.0/items/{itemId}", "put_test_v1.0_items_itemId.json",
			"putItem", "PUT /test/v1.0/items/{itemId}");

		try {
			OpenAPIUtil.getTool("GET /test/v1.0/items", "not valid json");

			Assert.fail();
		}
		catch (Exception exception) {
		}
	}

	private String _read(String fileName) throws Exception {
		return StringUtil.read(
			getClass().getResourceAsStream("dependencies/" + fileName));
	}

	private void _testGetHttpCallArguments(
		Map<String, Object> arguments, String endpoint, String expectedBody,
		String expectedMethod, String expectedUrl) {

		OpenAPIUtil.HttpCallArguments httpCallArguments =
			OpenAPIUtil.getHttpCallArguments(
				arguments, "http://localhost", endpoint);

		Assert.assertEquals(expectedBody, httpCallArguments.getBody());
		Assert.assertEquals(expectedMethod, httpCallArguments.getMethod());
		Assert.assertEquals(expectedUrl, httpCallArguments.getUrl());
	}

	private void _testGetTool(
			String endpoint, String expectedSchemaFileName,
			String expectedToolName, String expectedDescription)
		throws Exception {

		McpSchema.Tool tool = OpenAPIUtil.getTool(endpoint, _openAPIJSON);

		Assert.assertNotNull(tool);
		Assert.assertEquals(expectedDescription, tool.description());
		Assert.assertEquals(expectedToolName, tool.name());

		if (expectedSchemaFileName != null) {
			JSONAssert.assertEquals(
				_read(expectedSchemaFileName),
				new ObjectMapper(
				).writeValueAsString(
					tool.inputSchema()
				),
				false);
		}
	}

	private static String _openAPIJSON;

}