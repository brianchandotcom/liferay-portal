/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mcp.server.rest.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.mcp.server.rest.client.dto.v1_0.Prerequisite;
import com.liferay.mcp.server.rest.client.dto.v1_0.ToolSearchResult;
import com.liferay.mcp.server.rest.client.http.HttpInvoker;
import com.liferay.mcp.server.rest.client.pagination.Page;
import com.liferay.mcp.server.rest.test.util.MCPServerTestUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.test.util.CompanyConfigurationTemporarySwapper;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.util.HTTPTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.FeatureFlag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Petteri Karttunen
 */
@FeatureFlag("LPD-63311")
@RunWith(Arquillian.class)
public class ToolSearchResultResourceTest
	extends BaseToolSearchResultResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		MCPServerTestUtil.processBatchEngineUnits();
	}

	@Override
	@Test
	public void testGetToolSearchToolSearchResultsPage() throws Exception {
		_testGetToolSearchToolSearchResultsPageIsOnlyInTheToolSearchProfile();
		_testGetToolSearchToolSearchResultsPageWithABatchRequest();
		_testGetToolSearchToolSearchResultsPageWithALongSearch();
		_testGetToolSearchToolSearchResultsPageWithAnAction();
		_testGetToolSearchToolSearchResultsPageWithAnEntityWord();
		_testGetToolSearchToolSearchResultsPageWithAnObjectDefinition();
		_testGetToolSearchToolSearchResultsPageWithAScope();
		_testGetToolSearchToolSearchResultsPageWithTheInvokeTool();
		_testGetToolSearchToolSearchResultsPageWithNothingRelevant();
		_testGetToolSearchToolSearchResultsPageWithPrerequisites();
		_testGetToolSearchToolSearchResultsPageWithRequiredInputSchemaOnAListing();
		_testGetToolSearchToolSearchResultsPageWithTheMaxResultsCount();
	}

	private String _getToolNames(List<ToolSearchResult> toolSearchResults) {
		return StringUtil.merge(
			ListUtil.toList(toolSearchResults, ToolSearchResult::getToolName),
			StringPool.SPACE);
	}

	private List<ToolSearchResult> _search(
			boolean includeRequiredInputSchema, String search)
		throws Exception {

		Page<ToolSearchResult> page =
			toolSearchResultResource.getToolSearchToolSearchResultsPage(
				includeRequiredInputSchema, search);

		return new ArrayList<>(page.getItems());
	}

	private void _testGetToolSearchToolSearchResultsPageIsOnlyInTheToolSearchProfile()
		throws Exception {

		ObjectEntry objectEntry =
			MCPServerTestUtil.fetchMCPServerProfileObjectEntry("default");

		List<String> toolNames = MCPServerTestUtil.getMCPServerProfileToolNames(
			objectEntry);

		Assert.assertFalse(
			toolNames.toString(),
			toolNames.contains("getToolSearchToolSearchResultsPage"));

		objectEntry = MCPServerTestUtil.fetchMCPServerProfileObjectEntry(
			"tool-search");

		Assert.assertNotNull(
			"The tool-search profile is not seeded", objectEntry);

		toolNames = MCPServerTestUtil.getMCPServerProfileToolNames(objectEntry);

		Assert.assertTrue(
			toolNames.toString(),
			toolNames.contains("getToolSearchToolSearchResultsPage"));
		Assert.assertTrue(
			toolNames.toString(),
			toolNames.contains("postToolSetToolSetNameToolInvoke"));
	}

	private void _testGetToolSearchToolSearchResultsPageWithABatchRequest()
		throws Exception {

		List<ToolSearchResult> toolSearchResults = _search(
			false, "delete twenty users at once");

		ToolSearchResult toolSearchResult = toolSearchResults.get(0);

		Assert.assertTrue(
			_getToolNames(toolSearchResults),
			StringUtil.endsWith(toolSearchResult.getToolName(), "Batch"));

		toolSearchResults = _search(false, "delete a user");

		for (ToolSearchResult singleToolSearchResult : toolSearchResults) {
			Assert.assertFalse(
				_getToolNames(toolSearchResults),
				StringUtil.endsWith(
					singleToolSearchResult.getToolName(), "Batch"));
		}
	}

	private void _testGetToolSearchToolSearchResultsPageWithALongSearch()
		throws Exception {

		HttpInvoker.HttpResponse httpResponse =
			toolSearchResultResource.
				getToolSearchToolSearchResultsPageHttpResponse(
					false,
					StringUtil.merge(
						Collections.nCopies(30, "create a blog posting"),
						StringPool.SPACE));

		Assert.assertEquals(400, httpResponse.getStatusCode());

		String content = httpResponse.getContent();

		Assert.assertTrue(content, content.contains("one action at a time"));
	}

	private void _testGetToolSearchToolSearchResultsPageWithAScope()
		throws Exception {

		List<ToolSearchResult> toolSearchResults = _search(
			false, "create a blog posting in a site");

		ToolSearchResult toolSearchResult = toolSearchResults.get(0);

		Assert.assertEquals(
			_getToolNames(toolSearchResults), "postSiteBlogPosting",
			toolSearchResult.getToolName());
		Assert.assertEquals(
			"headless-delivery-v1.0", toolSearchResult.getToolSetName());
	}

	private void _testGetToolSearchToolSearchResultsPageWithAnAction()
		throws Exception {

		List<ToolSearchResult> toolSearchResults = _search(
			false, "publish a change list");

		ToolSearchResult toolSearchResult = toolSearchResults.get(0);

		Assert.assertEquals(
			_getToolNames(toolSearchResults), "postCTCollectionPublish",
			toolSearchResult.getToolName());
	}

	private void _testGetToolSearchToolSearchResultsPageWithAnEntityWord()
		throws Exception {

		List<ToolSearchResult> toolSearchResults = _search(
			false, "create an entity");

		ToolSearchResult toolSearchResult = toolSearchResults.get(0);

		Assert.assertEquals(
			_getToolNames(toolSearchResults), "postObjectDefinition",
			toolSearchResult.getToolName());
	}

	private void _testGetToolSearchToolSearchResultsPageWithAnObjectDefinition()
		throws Exception {

		String suffix = StringUtil.upperCaseFirstLetter(
			StringUtil.toLowerCase(RandomTestUtil.randomString()));

		String name = "PetStore" + suffix;

		ObjectDefinition objectDefinition =
			ObjectDefinitionTestUtil.addCustomObjectDefinition(name);

		try {
			objectDefinition =
				ObjectDefinitionLocalServiceUtil.publishCustomObjectDefinition(
					TestPropsValues.getUserId(),
					objectDefinition.getObjectDefinitionId());

			String search =
				"create a pet store " + StringUtil.toLowerCase(suffix);

			List<ToolSearchResult> toolSearchResults = _search(false, search);

			ToolSearchResult toolSearchResult = toolSearchResults.get(0);

			Assert.assertEquals(
				_getToolNames(toolSearchResults), "post" + name,
				toolSearchResult.getToolName());
			Assert.assertTrue(
				toolSearchResult.getToolSetName(),
				StringUtil.startsWith(toolSearchResult.getToolSetName(), "c-"));

			ObjectDefinitionLocalServiceUtil.deleteObjectDefinition(
				objectDefinition);

			objectDefinition = null;

			for (ToolSearchResult remainingToolSearchResult :
					_search(false, search)) {

				Assert.assertNotEquals(
					"post" + name, remainingToolSearchResult.getToolName());
			}
		}
		finally {
			if (objectDefinition != null) {
				ObjectDefinitionLocalServiceUtil.deleteObjectDefinition(
					objectDefinition);
			}
		}
	}

	private void _testGetToolSearchToolSearchResultsPageWithNothingRelevant()
		throws Exception {

		List<ToolSearchResult> toolSearchResults = _search(
			false, "zyxwvu qponml kjihgf");

		Assert.assertTrue(
			_getToolNames(toolSearchResults), toolSearchResults.isEmpty());
	}

	private void _testGetToolSearchToolSearchResultsPageWithPrerequisites()
		throws Exception {

		List<ToolSearchResult> toolSearchResults = _search(
			true, "create a web content article");

		ToolSearchResult toolSearchResult = toolSearchResults.get(0);

		Assert.assertEquals(
			_getToolNames(toolSearchResults), "postSiteStructuredContent",
			toolSearchResult.getToolName());

		Map<String, String> prerequisiteToolNames = new HashMap<>();

		for (Prerequisite prerequisite : toolSearchResult.getPrerequisites()) {
			prerequisiteToolNames.put(
				prerequisite.getParameter(), prerequisite.getToolName());
		}

		Assert.assertEquals(
			prerequisiteToolNames.toString(), "getSitesPage",
			prerequisiteToolNames.get("siteId"));
		Assert.assertEquals(
			prerequisiteToolNames.toString(), "getSiteContentStructuresPage",
			prerequisiteToolNames.get("contentStructureId"));

		Assert.assertNotNull(
			toolSearchResult.getToolName(),
			toolSearchResult.getRequiredInputSchema());

		ToolSearchResult secondToolSearchResult = toolSearchResults.get(1);

		Assert.assertNull(
			"Only the top result carries the required input schema",
			secondToolSearchResult.getRequiredInputSchema());
		Assert.assertNull(
			"Only the top result carries the prerequisites",
			secondToolSearchResult.getPrerequisites());
	}

	private void _testGetToolSearchToolSearchResultsPageWithRequiredInputSchemaOnAListing()
		throws Exception {

		List<ToolSearchResult> toolSearchResults = _search(
			true, "list the blog entries of a site");

		ToolSearchResult toolSearchResult = toolSearchResults.get(0);

		Assert.assertTrue(
			_getToolNames(toolSearchResults),
			StringUtil.endsWith(toolSearchResult.getToolName(), "Page"));

		Map<String, ?> requiredInputSchema =
			toolSearchResult.getRequiredInputSchema();

		Map<String, ?> properties = (Map<String, ?>)requiredInputSchema.get(
			"properties");

		Assert.assertTrue(
			requiredInputSchema.toString(), properties.containsKey("fields"));
	}

	private void _testGetToolSearchToolSearchResultsPageWithTheInvokeTool()
		throws Exception {

		JSONObject jsonObject = HTTPTestUtil.invokeToJSONObject(
			JSONUtil.put(
				"search", "create a blog posting in a site"
			).toString(),
			"mcp-server/v1.0/tool-sets/mcp-server-v1.0/tools" +
				"/getToolSearchToolSearchResultsPage/invoke",
			Http.Method.POST);

		JSONArray itemsJSONArray = jsonObject.getJSONArray("items");

		JSONObject itemJSONObject = itemsJSONArray.getJSONObject(0);

		Assert.assertEquals(
			itemsJSONArray.toString(), "postSiteBlogPosting",
			itemJSONObject.getString("toolName"));
	}

	private void _testGetToolSearchToolSearchResultsPageWithTheMaxResultsCount()
		throws Exception {

		try (CompanyConfigurationTemporarySwapper
				companyConfigurationTemporarySwapper =
					new CompanyConfigurationTemporarySwapper(
						TestPropsValues.getCompanyId(),
						"com.liferay.mcp.server.rest.internal.configuration." +
							"MCPServerConfiguration",
						HashMapDictionaryBuilder.<String, Object>put(
							"enabled", true
						).put(
							"searchToolMaxResultsCount", 3
						).build())) {

			List<ToolSearchResult> toolSearchResults = _search(
				false, "create a site");

			Assert.assertEquals(
				_getToolNames(toolSearchResults), 3, toolSearchResults.size());
		}
	}

}