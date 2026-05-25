/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mcp.server.rest.internal.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.mcp.server.rest.dto.v1_0.Tool;
import com.liferay.mcp.server.rest.dto.v1_0.ToolSummary;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Base64;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
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

	@Before
	public void setUp() throws Exception {
		_openAPIJSONObject = JSONFactoryUtil.createJSONObject(
			StringUtil.read(
				OpenAPIUtilTest.class.getResourceAsStream(
					"dependencies/openapi.json")));
	}

	@Test
	public void testGetOptions() {
		_testGetOptions(
			null, null, Http.Method.GET,
			"http://localhost/test/v1.0/items?restrictFields=actions",
			JSONFactoryUtil.createJSONObject(), "getItems");
		_testGetOptions(
			null, null, Http.Method.GET,
			"http://localhost/test/v1.0/items?page=1&pageSize=20" +
				"&restrictFields=actions",
			JSONUtil.put(
				"page", "1"
			).put(
				"pageSize", "20"
			),
			"getItems");
		_testGetOptions(
			null, null, Http.Method.GET,
			"http://localhost/test/v1.0/items?filter=name+eq+%27John+Doe%27" +
				"&restrictFields=actions",
			JSONUtil.put("filter", "name eq 'John Doe'"), "getItems");
		_testGetOptions(
			null, null, Http.Method.GET,
			"http://localhost/test/v1.0/items?restrictFields=actions",
			JSONUtil.put("fields", "name"), "getItems");
		_testGetOptions(
			null, null, Http.Method.GET,
			"http://localhost/test/v1.0/items?restrictFields=actions",
			JSONUtil.put("restrictFields", "name"), "getItems");
		_testGetOptions(
			null, null, Http.Method.GET,
			"http://localhost/test/v1.0/items/123?restrictFields=actions",
			JSONUtil.put("itemId", "123"), "getItem");
		_testGetOptions(
			JSONUtil.put(
				"name", "Test"
			).toString(),
			"application/json", Http.Method.PATCH,
			"http://localhost/test/v1.0/items/123",
			JSONUtil.put(
				"body", JSONUtil.put("name", "Test")
			).put(
				"itemId", "123"
			),
			"patchItem");
		_testGetOptions(
			"{}", "application/json", Http.Method.POST,
			"http://localhost/test/v1.0/items",
			JSONUtil.put("body", JSONFactoryUtil.createJSONObject()),
			"postItem");

		String fileContent = RandomTestUtil.randomString();
		String fileName = RandomTestUtil.randomString();
		String name = RandomTestUtil.randomString();

		Http.Options options = OpenAPIUtil.getOptions(
			"http://localhost/test",
			JSONUtil.put(
				"data",
				JSONUtil.put(
					"contentType", "text/plain"
				).put(
					"data",
					() -> {
						Base64.Encoder encoder = Base64.getEncoder();

						return encoder.encodeToString(fileContent.getBytes());
					}
				).put(
					"filename", fileName
				)
			).put(
				"name", name
			),
			_openAPIJSONObject, "postBinary");

		Assert.assertNull(options.getBody());
		_assertMultipartContentType(options);
		Assert.assertEquals(Http.Method.POST, options.getMethod());
		Assert.assertEquals(
			"http://localhost/test/v1.0/binaries", options.getLocation());

		List<Http.FilePart> fileParts = options.getFileParts();

		Assert.assertEquals(fileParts.toString(), 1, fileParts.size());

		Http.FilePart filePart = fileParts.get(0);

		Assert.assertEquals("text/plain", filePart.getContentType());
		Assert.assertEquals(fileName, filePart.getFileName());
		Assert.assertEquals("data", filePart.getName());
		Assert.assertArrayEquals(fileContent.getBytes(), filePart.getValue());

		Map<String, String> parts = options.getParts();

		Assert.assertEquals(parts.toString(), 1, parts.size());
		Assert.assertEquals(name, parts.get("name"));

		options = OpenAPIUtil.getOptions(
			"http://localhost/test",
			JSONUtil.put(
				"boolean", true
			).put(
				"integer", 1
			).put(
				"string", fileContent
			),
			_openAPIJSONObject, "postUpload");

		Assert.assertNull(options.getBody());
		_assertMultipartContentType(options);
		Assert.assertNull(options.getFileParts());
		Assert.assertEquals(Http.Method.POST, options.getMethod());
		Assert.assertEquals(
			"http://localhost/test/v1.0/uploads", options.getLocation());

		parts = options.getParts();

		Assert.assertEquals(parts.toString(), 3, parts.size());
		Assert.assertEquals("true", parts.get("boolean"));
		Assert.assertEquals("1", parts.get("integer"));
		Assert.assertEquals(fileContent, parts.get("string"));

		AssertUtils.assertFailure(
			IllegalArgumentException.class,
			StringBundler.concat(
				"The \"postItem\" tool requires the request payload nested ",
				"under a \"body\" property. Pass any path or query parameters ",
				"as siblings of \"body\" rather than flattening the payload ",
				"into the input map."),
			() -> OpenAPIUtil.getOptions(
				"http://localhost/test",
				JSONUtil.put(
					RandomTestUtil.randomString(),
					RandomTestUtil.randomString()),
				_openAPIJSONObject, "postItem"));
	}

	@Test
	public void testGetTool() throws Exception {
		AssertUtils.assertFailure(
			IllegalArgumentException.class,
			"OpenAPI document has no tool with name \"missing\"",
			() -> OpenAPIUtil.getTool(_openAPIJSONObject, "missing"));
		AssertUtils.assertFailure(
			IllegalArgumentException.class,
			"OpenAPI document has no \"paths\" object",
			() -> OpenAPIUtil.getTool(
				JSONFactoryUtil.createJSONObject(),
				RandomTestUtil.randomString()));
		AssertUtils.assertFailure(
			IllegalArgumentException.class, "Request body has no content",
			() -> _getInputSchema(_openAPIJSONObject, "postEmptyContent"));
		AssertUtils.assertFailure(
			IllegalArgumentException.class,
			"Request body content has no \"schema\"",
			() -> _getInputSchema(_openAPIJSONObject, "postNoSchema"));

		_testGetTool(
			"This is the description", "get_test_v1.0_items_itemId.json",
			"getItem");
		_testGetTool(
			"This is the summary. This is the description",
			"get_test_v1.0_items.json", "getItems");
		_testGetTool("This is the summary", "get_c_test.json", "getItemsPage");
		_testGetTool(
			"PATCH /v1.0/items/{itemId}", "patch_test_v1.0_items_itemId.json",
			"patchItem");
		_testGetTool(
			"POST /v1.0/binaries", "post_test_v1.0_binaries.json",
			"postBinary");
		_testGetTool(
			"POST /v1.0/described", "post_test_v1.0_described.json",
			"postDescribed");
		_testGetTool(
			"POST /v1.0/items", "post_test_v1.0_items.json", "postItem");
		_testGetTool(
			"POST /v1.0/levels", "post_test_v1.0_levels.json", "postLevel");
		_testGetTool(
			"POST /v1.0/no-content", "post_test_v1.0_no-content.json",
			"postNoContent");
		_testGetTool(
			"POST /v1.0/parents", "post_test_v1.0_parents.json", "postParent");
		_testGetTool(
			"POST /v1.0/undescribed", "post_test_v1.0_undescribed.json",
			"postUndescribed");
		_testGetTool(
			"POST /v1.0/uploads", "post_test_v1.0_uploads.json", "postUpload");
		_testGetTool(
			"PUT /v1.0/items/{itemId}", "put_test_v1.0_items_itemId.json",
			"putItem");
	}

	@Test
	public void testGetToolSummaries() {
		List<ToolSummary> toolSummaries = OpenAPIUtil.getToolSummaries(
			_openAPIJSONObject);

		Assert.assertEquals(toolSummaries.toString(), 15, toolSummaries.size());
		_assertToolSummary(
			"POST /v1.0/described", "postDescribed", toolSummaries.get(0));
		_assertToolSummary(
			"POST /v1.0/levels", "postLevel", toolSummaries.get(1));
		_assertToolSummary(
			"POST /v1.0/undescribed", "postUndescribed", toolSummaries.get(2));
		_assertToolSummary(
			"This is the description", "getItem", toolSummaries.get(3));
		_assertToolSummary(
			"PATCH /v1.0/items/{itemId}", "patchItem", toolSummaries.get(4));
		_assertToolSummary(
			"PUT /v1.0/items/{itemId}", "putItem", toolSummaries.get(5));
		_assertToolSummary(
			"POST /v1.0/binaries", "postBinary", toolSummaries.get(6));
		_assertToolSummary(
			"POST /v1.0/empty-content", "postEmptyContent",
			toolSummaries.get(7));
		_assertToolSummary(
			"POST /v1.0/no-content", "postNoContent", toolSummaries.get(8));
		_assertToolSummary(
			"POST /v1.0/parents", "postParent", toolSummaries.get(9));
		_assertToolSummary(
			"POST /v1.0/uploads", "postUpload", toolSummaries.get(10));
		_assertToolSummary(
			"This is the summary. This is the description", "getItems",
			toolSummaries.get(11));
		_assertToolSummary(
			"POST /v1.0/items", "postItem", toolSummaries.get(12));
		_assertToolSummary(
			"POST /v1.0/no-schema", "postNoSchema", toolSummaries.get(13));
		_assertToolSummary(
			"This is the summary", "getItemsPage", toolSummaries.get(14));

		AssertUtils.assertFailure(
			IllegalArgumentException.class,
			"OpenAPI document has no \"paths\" object",
			() -> OpenAPIUtil.getToolSummaries(
				JSONFactoryUtil.createJSONObject()));
	}

	private void _assertMultipartContentType(Http.Options options) {
		String contentTypeHeader = options.getHeader("Content-Type");

		Assert.assertNotNull(contentTypeHeader);
		Assert.assertTrue(
			contentTypeHeader,
			contentTypeHeader.startsWith("multipart/form-data; boundary="));
	}

	private void _assertToolSummary(
		String expectedDescription, String expectedName,
		ToolSummary toolSummary) {

		Assert.assertEquals(expectedDescription, toolSummary.getDescription());
		Assert.assertEquals(expectedName, toolSummary.getName());
	}

	private Map<String, ?> _getInputSchema(
		JSONObject openAPIJSONObject, String toolName) {

		Tool tool = OpenAPIUtil.getTool(openAPIJSONObject, toolName);

		return tool.getInputSchema();
	}

	private String _read(String fileName) throws Exception {
		return StringUtil.read(
			getClass().getResourceAsStream("dependencies/" + fileName));
	}

	private void _testGetOptions(
		String expectedBody, String expectedContentType,
		Http.Method expectedMethod, String expectedURL,
		JSONObject inputJSONObject, String toolName) {

		Http.Options options = OpenAPIUtil.getOptions(
			"http://localhost/test", inputJSONObject, _openAPIJSONObject,
			toolName);

		Http.Body body = options.getBody();

		if (expectedBody == null) {
			Assert.assertNull(body);
		}
		else {
			Assert.assertEquals(expectedBody, body.getContent());
			Assert.assertEquals(expectedContentType, body.getContentType());
			Assert.assertEquals(
				expectedContentType, options.getHeader("Content-Type"));
		}

		Assert.assertNull(options.getFileParts());
		Assert.assertEquals(expectedURL, options.getLocation());
		Assert.assertEquals(expectedMethod, options.getMethod());
		Assert.assertNull(options.getParts());
	}

	private void _testGetTool(
			String expectedDescription, String expectedSchemaFileName,
			String toolName)
		throws Exception {

		Tool tool = OpenAPIUtil.getTool(_openAPIJSONObject, toolName);

		Assert.assertEquals(expectedDescription, tool.getDescription());
		Assert.assertEquals(toolName, tool.getName());

		JSONAssert.assertEquals(
			_read(expectedSchemaFileName),
			new ObjectMapper(
			).writeValueAsString(
				tool.getInputSchema()
			),
			true);
	}

	private JSONObject _openAPIJSONObject;

}