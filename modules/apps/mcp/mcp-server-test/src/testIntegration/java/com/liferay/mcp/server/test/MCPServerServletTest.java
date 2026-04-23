/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mcp.server.test;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.constants.ObjectEntryFolderConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import io.modelcontextprotocol.client.McpClient;
import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.client.transport.HttpClientStreamableHttpTransport;
import io.modelcontextprotocol.spec.McpSchema;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import java.util.Base64;
import java.util.Collections;
import java.util.List;

import org.hamcrest.CoreMatchers;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author Alejandro Tardín
 */
@FeatureFlag("LPD-63311")
@RunWith(Arquillian.class)
public class MCPServerServletTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@FeatureFlag("LPD-86164")
	@Test
	public void testServiceWithModifiedProfile() throws Exception {
		String name = RandomTestUtil.randomString();

		ObjectEntry objectEntry = _addObjectEntry(
			name, "GET /test/v1.0/test-entities",
			"POST /test/v1.0/test-entities");

		McpSyncClient mcpSyncClient = _getMcpSyncClient(name);

		mcpSyncClient.initialize();

		McpSchema.ListToolsResult listToolsResult = mcpSyncClient.listTools();

		List<McpSchema.Tool> tools = listToolsResult.tools();

		Assert.assertEquals(tools.toString(), 2, tools.size());

		_assertTool(
			tools.get(0), "getTestEntitiesPage",
			"get_test_v1.0_test_entities.json");
		_assertTool(
			tools.get(1), "postTestEntity",
			"post_test_v1.0_test_entities.json");

		mcpSyncClient.closeGracefully();

		_objectEntryLocalService.updateObjectEntry(
			TestPropsValues.getUserId(), objectEntry.getObjectEntryId(),
			ObjectEntryFolderConstants.PARENT_OBJECT_ENTRY_FOLDER_ID_DEFAULT,
			HashMapBuilder.<String, Serializable>put(
				"description", RandomTestUtil.randomString()
			).put(
				"endpoints", "GET /test/v1.0/test-entities"
			).put(
				"name", name
			).build(),
			ServiceContextTestUtil.getServiceContext());

		mcpSyncClient = _getMcpSyncClient(name);

		mcpSyncClient.initialize();

		listToolsResult = mcpSyncClient.listTools();

		tools = listToolsResult.tools();

		Assert.assertEquals(tools.toString(), 1, tools.size());

		_assertTool(
			tools.get(0), "getTestEntitiesPage",
			"get_test_v1.0_test_entities.json");

		mcpSyncClient.closeGracefully();
	}

	@Test
	public void testServiceWithoutProfile() throws Exception {
		McpSyncClient mcpSyncClient = _getMcpSyncClient(null);

		mcpSyncClient.initialize();

		McpSchema.ListToolsResult listToolsResult = mcpSyncClient.listTools();

		List<McpSchema.Tool> tools = listToolsResult.tools();

		Assert.assertEquals(tools.toString(), 3, tools.size());

		McpSchema.Tool tool1 = tools.get(0);

		Assert.assertEquals("call-http-endpoint", tool1.name());

		McpSchema.Tool tool2 = tools.get(1);

		Assert.assertEquals("get-openapi", tool2.name());

		McpSchema.Tool tool3 = tools.get(2);

		Assert.assertEquals("get-openapis", tool3.name());

		McpSchema.CallToolResult callToolResult = mcpSyncClient.callTool(
			new McpSchema.CallToolRequest(
				"get-openapis", Collections.emptyMap()));

		List<McpSchema.Content> contents = callToolResult.content();

		McpSchema.TextContent content = (McpSchema.TextContent)contents.get(0);

		callToolResult = mcpSyncClient.callTool(
			new McpSchema.CallToolRequest(
				"get-openapi",
				HashMapBuilder.<String, Object>put(
					"url",
					() -> {
						JSONObject jsonObject =
							JSONFactoryUtil.createJSONObject(content.text());

						JSONArray jsonArray = jsonObject.getJSONArray("/test");

						return jsonArray.getString(0);
					}
				).build()));

		contents = callToolResult.content();

		McpSchema.TextContent newContent = (McpSchema.TextContent)contents.get(
			0);

		Assert.assertThat(
			newContent.text(), CoreMatchers.containsString("/test"));

		callToolResult = mcpSyncClient.callTool(
			new McpSchema.CallToolRequest(
				"call-http-endpoint",
				HashMapBuilder.<String, Object>put(
					"method", "GET"
				).put(
					"path", "/test/v1.0/test-entities"
				).build()));

		contents = callToolResult.content();

		newContent = (McpSchema.TextContent)contents.get(0);

		Assert.assertNotNull(
			JSONFactoryUtil.createJSONObject(
				newContent.text()
			).getJSONArray(
				"items"
			));

		mcpSyncClient.closeGracefully();
	}

	@FeatureFlag("LPD-86164")
	@Test
	public void testServiceWithProfile() throws Exception {
		String name = RandomTestUtil.randomString();

		_addObjectEntry(
			name, "GET /test/v1.0/test-entities",
			"POST /test/v1.0/test-entities");

		McpSyncClient mcpSyncClient = _getMcpSyncClient(name);

		mcpSyncClient.initialize();

		McpSchema.ListToolsResult listToolsResult = mcpSyncClient.listTools();

		List<McpSchema.Tool> tools = listToolsResult.tools();

		Assert.assertEquals(tools.toString(), 2, tools.size());

		_assertTool(
			tools.get(0), "getTestEntitiesPage",
			"get_test_v1.0_test_entities.json");
		_assertTool(
			tools.get(1), "postTestEntity",
			"post_test_v1.0_test_entities.json");

		McpSchema.CallToolResult callToolResult = mcpSyncClient.callTool(
			new McpSchema.CallToolRequest(
				"postTestEntity",
				HashMapBuilder.<String, Object>put(
					"body",
					HashMapBuilder.<String, Object>put(
						"name", name
					).put(
						"type", "ChildTestEntity1"
					).build()
				).build()));

		List<McpSchema.Content> contents = callToolResult.content();

		McpSchema.TextContent content = (McpSchema.TextContent)contents.get(0);

		Assert.assertFalse(content.text(), callToolResult.isError());

		Assert.assertEquals(
			name,
			JSONFactoryUtil.createJSONObject(
				content.text()
			).getString(
				"name"
			));

		callToolResult = mcpSyncClient.callTool(
			new McpSchema.CallToolRequest(
				"getTestEntitiesPage", Collections.emptyMap()));

		contents = callToolResult.content();

		content = (McpSchema.TextContent)contents.get(0);

		Assert.assertFalse(content.text(), callToolResult.isError());

		Assert.assertTrue(
			JSONUtil.toStringList(
				JSONFactoryUtil.createJSONObject(
					content.text()
				).getJSONArray(
					"items"
				),
				"name"
			).contains(
				name
			));

		mcpSyncClient.closeGracefully();
	}

	private ObjectEntry _addObjectEntry(String name, String... endpoints)
		throws Exception {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.
				fetchObjectDefinitionByExternalReferenceCode(
					"L_MCP_SERVER_PROFILE", TestPropsValues.getCompanyId());

		return _objectEntryLocalService.addObjectEntry(
			0, TestPropsValues.getUserId(),
			objectDefinition.getObjectDefinitionId(),
			ObjectEntryFolderConstants.PARENT_OBJECT_ENTRY_FOLDER_ID_DEFAULT,
			null,
			HashMapBuilder.<String, Serializable>put(
				"description", RandomTestUtil.randomString()
			).put(
				"endpoints", StringUtil.merge(endpoints, "\n")
			).put(
				"name", name
			).build(),
			ServiceContextTestUtil.getServiceContext());
	}

	private void _assertTool(
			McpSchema.Tool tool, String expectedName,
			String expectedSchemaFileName)
		throws Exception {

		Assert.assertEquals(expectedName, tool.name());

		JSONAssert.assertEquals(
			StringUtil.read(
				MCPServerServletTest.class.getResourceAsStream(
					"dependencies/" + expectedSchemaFileName)),
			new ObjectMapper(
			).writeValueAsString(
				tool.inputSchema()
			),
			false);
	}

	private String _getAuthorization() {
		try {
			Base64.Encoder encoder = Base64.getEncoder();

			String userNameAndPassword =
				"test@liferay.com:" + PropsValues.DEFAULT_ADMIN_PASSWORD;

			return "Basic " +
				new String(
					encoder.encode(userNameAndPassword.getBytes("UTF-8")),
					"UTF-8");
		}
		catch (UnsupportedEncodingException unsupportedEncodingException) {
			throw new RuntimeException(unsupportedEncodingException);
		}
	}

	private McpSyncClient _getMcpSyncClient(String profileName) {
		return McpClient.sync(
			HttpClientStreamableHttpTransport.builder(
				"http://localhost:8080/o/"
			).customizeRequest(
				builder -> builder.header("Authorization", _getAuthorization())
			).endpoint(
				(profileName != null) ? "mcp/" + profileName : "mcp"
			).build()
		).capabilities(
			McpSchema.ClientCapabilities.builder(
			).elicitation(
				true, true
			).build()
		).build();
	}

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

}