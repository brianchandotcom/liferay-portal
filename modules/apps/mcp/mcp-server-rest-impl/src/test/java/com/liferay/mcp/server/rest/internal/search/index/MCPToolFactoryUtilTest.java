/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mcp.server.rest.internal.search.index;

import com.liferay.mcp.server.rest.internal.search.index.constants.MCPToolConstants;
import com.liferay.mcp.server.rest.internal.search.index.util.IntentUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Petteri Karttunen
 */
public class MCPToolFactoryUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetDescription() {
		Assert.assertEquals(
			"Creates a blog posting. The site must exist.",
			_getDescription("The site must exist.", "Creates a blog posting"));
		Assert.assertEquals(
			"Creates a blog posting. The site must exist.",
			_getDescription("The site must exist.", "Creates a blog posting."));
		Assert.assertEquals(
			"The site must exist.",
			_getDescription("The site must exist.", StringPool.BLANK));
	}

	@Test
	public void testGetExpansions() {
		String[] expansions = _getExpansions(
			false, false, "blog posting", IntentUtil.INTENT_CREATE, null,
			"/sites/{siteId}/blog-postings", "postSiteBlogPosting");

		_assertExpansion(expansions, "create blog posting in a site");

		String[] verbs = IntentUtil.getIntentVerbs(IntentUtil.INTENT_CREATE);

		Assert.assertEquals(
			Arrays.toString(expansions), verbs.length, expansions.length);
	}

	@Test
	public void testGetExpansionsWithABatchTool() {
		String[] expansions = _getExpansions(
			true, false, "blog posting", IntentUtil.INTENT_CREATE, null,
			"/sites/{siteId}/blog-postings/batch", "postSiteBlogPostingBatch");

		_assertExpansion(expansions, "batch add blog postings in a site");

		for (String expansion : expansions) {
			Assert.assertTrue(
				expansion, StringUtil.startsWith(expansion, "batch "));
		}
	}

	@Test
	public void testGetExpansionsWithAByPhraseBeforeTheEntity() {
		_assertExpansion(
			_getExpansions(
				false, true, "child comment", IntentUtil.INTENT_LIST, null,
				"/by-external-reference-code/{externalReferenceCode}/comments" +
					"/{commentExternalReferenceCode}/child-comments",
				"getByExternalReferenceCodeCommentChildCommentsPage"),
			"browse child comments by comment external reference code");
	}

	@Test
	public void testGetExpansionsWithACollectionNounAfterTheEntity() {
		_assertExpansion(
			_getExpansions(
				false, true, "catalog", IntentUtil.INTENT_LIST, null,
				"/catalog/{catalogId}/permissions",
				"getCatalogPermissionsPage"),
			"browse catalog permissions by catalog id");
	}

	@Test
	public void testGetExpansionsWithACollectionTool() {
		String[] expansions = _getExpansions(
			false, true, "blog posting", IntentUtil.INTENT_LIST, null,
			"/sites/{siteId}/blog-postings", "getSiteBlogPostingsPage");

		_assertExpansion(expansions, "list blog postings in a site");
		_assertExpansion(expansions, "get all blog postings in a site");
	}

	@Test
	public void testGetExpansionsWithAModifierAfterTheEntity() {
		_assertExpansion(
			_getExpansions(
				false, true, "document", IntentUtil.INTENT_LIST, null,
				"/sites/{siteId}/documents/rated-by-me",
				"getSiteDocumentsRatedByMePage"),
			"browse documents in a site rated by me");

		_assertExpansion(
			_getExpansions(
				false, true, "warehouse item", IntentUtil.INTENT_LIST, null,
				"/warehouseItems/updated", "getWarehouseItemsUpdatedPage"),
			"browse warehouse items updated");
	}

	@Test
	public void testGetExpansionsWithAParameterBeforeTheEntity() {
		_assertExpansion(
			_getExpansions(
				false, false, "attachment", IntentUtil.INTENT_CREATE, null,
				"/products/{id}/images", "postProductIdImage"),
			"add image in a product by id");

		_assertExpansion(
			_getExpansions(
				false, true, "attachment", IntentUtil.INTENT_LIST, null,
				"/products/{id}/images", "getProductIdImagesPage"),
			"browse images in a product by id");
	}

	@Test
	public void testGetExpansionsWithAParameterBetweenTheEntityWords() {
		_assertExpansion(
			_getExpansions(
				false, true, "option value", IntentUtil.INTENT_LIST, null,
				"/options/{id}/optionValues", "getOptionIdOptionValuesPage"),
			"browse option values by id");
	}

	@Test
	public void testGetExpansionsWithAParameterWordInsideAnotherWord() {
		_assertExpansion(
			_getExpansions(
				false, false, "comment", IntentUtil.INTENT_READ, null,
				"/videos/{videoId}/comments/{id}", "getVideoComment"),
			"fetch comment in a video by id");

		_assertExpansion(
			_getExpansions(
				false, false, "provider", IntentUtil.INTENT_READ, null,
				"/providers/{id}", "getProvider"),
			"fetch provider by id");
	}

	@Test
	public void testGetExpansionsWithAParentReachedByExternalReferenceCode() {
		_assertExpansion(
			_getExpansions(
				false, true, "blog posting", IntentUtil.INTENT_LIST, null,
				"/sites/by-external-reference-code" +
					"/{siteExternalReferenceCode}/blog-postings",
				"getSiteBlogPostingsPage"),
			"browse blog postings in a site");
	}

	@Test
	public void testGetExpansionsWithAParentStartingWithAVowel() {
		_assertExpansion(
			_getExpansions(
				false, true, "document", IntentUtil.INTENT_LIST, null,
				"/asset-libraries/{assetLibraryId}/documents",
				"getAssetLibraryDocumentsPage"),
			"browse documents in an asset library");
	}

	@Test
	public void testGetExpansionsWithAParentStartingWithU() {
		_assertExpansion(
			_getExpansions(
				false, true, "phone", IntentUtil.INTENT_LIST, null,
				"/user-accounts/{userAccountId}/phones",
				"getUserAccountPhonesPage"),
			"browse phones in a user account");
	}

	@Test
	public void testGetExpansionsWithAParentThatIsPartOfTheEntity() {
		_assertExpansion(
			_getExpansions(
				false, true, "process metric", IntentUtil.INTENT_LIST, null,
				"/processes/{processId}/metrics", "getProcessMetricsPage"),
			"browse process metrics by process id");
	}

	@Test
	public void testGetExpansionsWithAPathParameterInTheToolName() {
		_assertExpansion(
			_getExpansions(
				false, false, "blog posting", IntentUtil.INTENT_READ, null,
				"/sites/{siteId}/blog-postings/by-external-reference-code" +
					"/{externalReferenceCode}",
				"getSiteBlogPostingByExternalReferenceCode"),
			"fetch blog posting in a site by external reference code");
	}

	@Test
	public void testGetExpansionsWithATagAbsentFromTheToolName() {
		_assertExpansion(
			_getExpansions(
				false, false, "object entry", IntentUtil.INTENT_READ, null,
				"/by-external-reference-code/{externalReferenceCode}",
				"getByExternalReferenceCode"),
			"fetch object entry by external reference code");

		_assertExpansion(
			_getExpansions(
				false, false, "organization", "delete", null,
				"/organizations/{organizationId}/user-accounts" +
					"/by-email-address/{emailAddress}",
				"deleteUserAccountByEmailAddress"),
			"delete user account by email address");

		_assertExpansion(
			_getExpansions(
				false, true, "assignee", IntentUtil.INTENT_LIST, null,
				"/workflow-tasks/{workflowTaskId}/assignable-users",
				"getWorkflowTaskAssignableUsersPage"),
			"browse assignable users in a workflow task");
	}

	@Test
	public void testGetExpansionsWithAnEntityEndingInPage() {
		_assertExpansion(
			_getExpansions(
				false, false, "wiki page", IntentUtil.INTENT_CREATE, null,
				"/wiki-nodes/{wikiNodeId}/wiki-pages", "postWikiNodeWikiPage"),
			"add wiki page in a wiki node");

		_assertExpansion(
			_getExpansions(
				false, false, "wiki page", IntentUtil.INTENT_READ, null,
				"/wiki-pages/{wikiPageId}", "getWikiPage"),
			"fetch wiki page by wiki page id");
	}

	@Test
	public void testGetExpansionsWithAnEntityEndingInSS() {
		_assertExpansion(
			_getExpansions(
				false, true, "export process", IntentUtil.INTENT_LIST, null,
				"/sites/{siteId}/export-processes",
				"getSiteExportProcessesPage"),
			"browse export processes in a site");
	}

	@Test
	public void testGetExpansionsWithAnOperationActionSuffix() {
		_assertExpansion(
			_getExpansions(
				false, false, "structured content", "subscribe", "Subscribe",
				"/structured-contents/{structuredContentId}/subscribe",
				"putStructuredContentSubscribe"),
			"subscribe structured content by structured content id");
	}

	@Test
	public void testGetExpansionsWithAnOperationActionSuffixInAParent() {
		_assertExpansion(
			_getExpansions(
				false, false, "blog posting", "subscribe", "Subscribe",
				"/sites/{siteId}/blog-postings/subscribe",
				"putSiteBlogPostingSubscribe"),
			"subscribe blog postings in a site");
	}

	@Test
	public void testGetExpansionsWithTheEntityAsTheParent() {
		String[] expansions = _getExpansions(
			false, false, "site", IntentUtil.INTENT_READ, null,
			"/sites/by-external-reference-code/{externalReferenceCode}",
			"getSiteByExternalReferenceCode");

		_assertExpansion(expansions, "fetch site by external reference code");

		for (String expansion : expansions) {
			Assert.assertFalse(expansion, expansion.contains("in a site"));
		}
	}

	@Test
	public void testGetExpansionsWithoutAnEntity() {
		Assert.assertEquals(
			0,
			_getExpansions(
				false, false, StringPool.BLANK, IntentUtil.INTENT_CREATE, null,
				"/sites", "postSite").length);
		Assert.assertEquals(
			0,
			_getExpansions(
				false, false, "site", StringPool.BLANK, null, "/sites",
				"headSite").length);
	}

	@Test
	public void testGetOperationVariant() {
		Assert.assertEquals(
			MCPToolConstants.OPERATION_VARIANT_TRAVERSAL,
			_getOperationVariant(
				"Assignee",
				"/workflow-tasks/{workflowTaskId}/assignable-users"));
		Assert.assertEquals(
			MCPToolConstants.OPERATION_VARIANT_TRAVERSAL,
			_getOperationVariant("Attachment", "/products/{id}/images"));
		Assert.assertEquals(
			MCPToolConstants.OPERATION_VARIANT_NESTED,
			_getOperationVariant(
				"BlogPosting",
				"/sites/by-external-reference-code" +
					"/{siteExternalReferenceCode}/blog-postings" +
						"/by-external-reference-code/{externalReferenceCode}"));
		Assert.assertEquals(
			StringPool.BLANK,
			_getOperationVariant(
				"BlogPosting", "/sites/{siteId}/blog-postings"));
		Assert.assertEquals(
			StringPool.BLANK,
			_getOperationVariant(
				"ProcessMetric", "/processes/{processId}/metrics"));
	}

	@Test
	public void testGetPropertyCollection() {
		Assert.assertEquals(
			"contentstructures", _getPropertyCollection("contentStructureId"));
		Assert.assertNull(_getPropertyCollection("id"));
		Assert.assertEquals("sites", _getPropertyCollection("siteId"));
		Assert.assertEquals(
			"taxonomycategories",
			_getPropertyCollection("taxonomyCategoryIds"));
		Assert.assertNull(_getPropertyCollection("title"));
	}

	private void _assertExpansion(String[] expansions, String expected) {
		Assert.assertTrue(
			Arrays.toString(expansions),
			ArrayUtil.contains(expansions, expected));
	}

	private String _getDescription(String description, String summary) {
		return ReflectionTestUtil.invoke(
			MCPToolFactoryUtil.class, "_getDescription",
			new Class<?>[] {JSONObject.class},
			JSONUtil.put(
				"description", description
			).put(
				"summary", summary
			));
	}

	private String[] _getExpansions(
		boolean batch, boolean collection, String entityWords, String intent,
		String operationActionSuffix, String path, String toolName) {

		return ReflectionTestUtil.invoke(
			MCPToolFactoryUtil.class, "_getExpansions",
			new Class<?>[] {
				boolean.class, boolean.class, String.class, String.class,
				String.class, String.class, String.class
			},
			batch, collection, entityWords, intent, operationActionSuffix, path,
			toolName);
	}

	private String _getOperationVariant(String entityName, String path) {
		return ReflectionTestUtil.invoke(
			MCPToolFactoryUtil.class, "_getOperationVariant",
			new Class<?>[] {String.class, String.class}, entityName, path);
	}

	private String _getPropertyCollection(String propertyName) {
		return ReflectionTestUtil.invoke(
			MCPToolFactoryUtil.class, "_getPropertyCollection",
			new Class<?>[] {String.class}, propertyName);
	}

}