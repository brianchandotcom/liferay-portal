/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mcp.server.rest.internal.search.index;

import com.liferay.mcp.server.rest.internal.search.index.constants.MCPToolFields;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.search.aggregation.Aggregations;
import com.liferay.portal.search.aggregation.bucket.TermsAggregation;
import com.liferay.portal.search.aggregation.bucket.TermsAggregationResult;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.document.BulkDocumentItemResponse;
import com.liferay.portal.search.engine.adapter.document.BulkDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.BulkDocumentResponse;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Petteri Karttunen
 */
public class MCPToolIndexWriterTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_companyId = RandomTestUtil.randomLong();

		_mcpToolIndexCreator = Mockito.mock(MCPToolIndexCreator.class);

		_mcpToolIndexWriter = new MCPToolIndexWriter();

		ReflectionTestUtil.setFieldValue(
			_mcpToolIndexWriter, "_mcpToolIndexCreator", _mcpToolIndexCreator);
	}

	@Test
	public void testClearStaleToolSet() {
		String toolSetName = RandomTestUtil.randomString();

		_mcpToolIndexWriter.invalidate(_companyId, toolSetName);

		Map<String, Object> staleToolSetTokens = _getStaleToolSetTokens();

		_clearStaleToolSet(toolSetName, staleToolSetTokens.get(toolSetName));

		Assert.assertFalse(_hasStaleToolSets());
	}

	@Test
	public void testClearStaleToolSetWhenTheToolSetWasMarkedAgain() {
		String toolSetName = RandomTestUtil.randomString();

		_mcpToolIndexWriter.invalidate(_companyId, toolSetName);

		Map<String, Object> staleToolSetTokens = _getStaleToolSetTokens();

		_mcpToolIndexWriter.invalidate(_companyId, toolSetName);

		Object token = staleToolSetTokens.get(toolSetName);

		_clearStaleToolSet(toolSetName, token);

		Map<String, Object> currentStaleToolSetTokens =
			_getStaleToolSetTokens();

		Assert.assertEquals(
			currentStaleToolSetTokens.toString(), 1,
			currentStaleToolSetTokens.size());
		Assert.assertNotSame(token, currentStaleToolSetTokens.get(toolSetName));
	}

	@Test
	public void testDeleteIndex() {
		Object mcpToolIndexState = ReflectionTestUtil.invoke(
			_mcpToolIndexWriter, "_getIndexState", new Class<?>[] {long.class},
			_companyId);

		ReflectionTestUtil.invoke(
			mcpToolIndexState, "setIndexedToolSetNames",
			new Class<?>[] {Set.class}, SetUtil.fromArray("toolSet"));

		Assert.assertFalse(_hasStaleToolSets());

		_mcpToolIndexWriter.deleteIndex(_companyId);

		Assert.assertSame(mcpToolIndexState, _getIndexState());
		Assert.assertTrue(_hasStaleToolSets());

		Set<String> indexedToolSetNames = ReflectionTestUtil.getFieldValue(
			mcpToolIndexState, "_indexedToolSetNames");

		Assert.assertTrue(indexedToolSetNames.isEmpty());

		Mockito.verify(
			_mcpToolIndexCreator
		).deleteIfExists(
			_companyId
		);
	}

	@Test
	public void testGetChangedToolSetNamesWhenAllToolSetsAreStale() {
		_mcpToolIndexWriter.invalidate(_companyId);

		Set<String> toolSetNames = SetUtil.fromArray("new", "unchanged");

		Assert.assertEquals(
			toolSetNames,
			_getChangedToolSetNames(
				SetUtil.fromArray("unchanged"), toolSetNames));
	}

	@Test
	public void testGetChangedToolSetNamesWhenOneIsStale() {
		_mcpToolIndexWriter.invalidate(_companyId, "staleToolSet");
		_mcpToolIndexWriter.invalidate(_companyId, "dummyToolSet");

		Set<String> changedToolSetNames = _getChangedToolSetNames(
			SetUtil.fromArray("indexedToolSet", "staleToolSet"),
			SetUtil.fromArray("newToolSet", "staleToolSet"));

		Assert.assertEquals(
			changedToolSetNames.toString(),
			SetUtil.fromArray("newToolSet", "staleToolSet"),
			changedToolSetNames);
	}

	@Test
	public void testGetIndexedToolSetNames() {
		Aggregations aggregations = Mockito.mock(Aggregations.class);

		ReflectionTestUtil.setFieldValue(
			_mcpToolIndexWriter, "_aggregations", aggregations);

		Mockito.when(
			aggregations.terms(Mockito.anyString(), Mockito.anyString())
		).thenReturn(
			Mockito.mock(TermsAggregation.class)
		);

		SearchEngineAdapter searchEngineAdapter = Mockito.mock(
			SearchEngineAdapter.class);

		ReflectionTestUtil.setFieldValue(
			_mcpToolIndexWriter, "_searchEngineAdapter", searchEngineAdapter);

		SearchSearchResponse searchSearchResponse = new SearchSearchResponse();

		TermsAggregationResult termsAggregationResult =
			new TermsAggregationResult(MCPToolFields.TOOL_SET_NAME, 0, 0);

		termsAggregationResult.addBucket("headless-delivery-v1.0", 503);
		termsAggregationResult.addBucket("object-admin-v1.0", 97);

		searchSearchResponse.addAggregationResult(termsAggregationResult);

		Mockito.when(
			searchEngineAdapter.execute(Mockito.any(SearchSearchRequest.class))
		).thenReturn(
			searchSearchResponse
		);

		Set<String> indexedToolSetNames = ReflectionTestUtil.invoke(
			_mcpToolIndexWriter, "_getIndexedToolSetNames",
			new Class<?>[] {long.class}, _companyId);

		Assert.assertEquals(
			SetUtil.fromArray("headless-delivery-v1.0", "object-admin-v1.0"),
			indexedToolSetNames);
	}

	@Test
	public void testIndexWhenADocumentIsRejected() {
		SearchEngineAdapter searchEngineAdapter = Mockito.mock(
			SearchEngineAdapter.class);

		ReflectionTestUtil.setFieldValue(
			_mcpToolIndexWriter, "_searchEngineAdapter", searchEngineAdapter);

		BulkDocumentResponse bulkDocumentResponse = new BulkDocumentResponse(0);

		bulkDocumentResponse.setErrors(true);

		BulkDocumentItemResponse bulkDocumentItemResponse =
			new BulkDocumentItemResponse();

		bulkDocumentItemResponse.setId("rejectedToolSet#tool");
		bulkDocumentItemResponse.setFailureMessage("strict mapping");

		bulkDocumentResponse.addBulkDocumentItemResponse(
			bulkDocumentItemResponse);

		bulkDocumentItemResponse = new BulkDocumentItemResponse();

		bulkDocumentItemResponse.setId("indexedToolSet#tool");

		bulkDocumentResponse.addBulkDocumentItemResponse(
			bulkDocumentItemResponse);

		Mockito.when(
			searchEngineAdapter.execute(Mockito.any(BulkDocumentRequest.class))
		).thenReturn(
			bulkDocumentResponse
		);

		Set<String> failedToolSetNames = ReflectionTestUtil.invoke(
			_mcpToolIndexWriter, "_index",
			new Class<?>[] {String.class, Collection.class, Map.class}, "index",
			Collections.singletonList(_getMCPTool("rejectedToolSet")),
			Collections.emptyMap());

		Assert.assertEquals(
			SetUtil.fromArray("rejectedToolSet"), failedToolSetNames);
	}

	@Test
	public void testInvalidate() {
		_mcpToolIndexWriter.invalidate(_companyId);

		Assert.assertTrue(_hasStaleToolSets());

		Map<String, Object> staleToolSetTokens = _getStaleToolSetTokens();

		Assert.assertTrue(staleToolSetTokens.containsKey(StringPool.STAR));
	}

	@Test
	public void testInvalidateWithAToolSetName() {
		String toolSetName = RandomTestUtil.randomString();

		_mcpToolIndexWriter.invalidate(_companyId, toolSetName);

		Map<String, Object> staleToolSetTokens = _getStaleToolSetTokens();

		Assert.assertEquals(
			staleToolSetTokens.toString(), 1, staleToolSetTokens.size());
		Assert.assertTrue(staleToolSetTokens.containsKey(toolSetName));
	}

	@Test
	public void testMarkToolSetFailed() {
		String toolSetName = RandomTestUtil.randomString();

		_mcpToolIndexWriter.invalidate(_companyId, toolSetName);

		_markToolSetFailed(toolSetName);

		Assert.assertFalse(_hasStaleToolSets());

		Map<String, Object> staleToolSetTokens = _getStaleToolSetTokens();

		Assert.assertFalse(staleToolSetTokens.containsKey(toolSetName));
	}

	@Test
	public void testMarkToolSetFailedWhenAllToolSetsAreInvalidated() {
		String toolSetName = RandomTestUtil.randomString();

		_mcpToolIndexWriter.invalidate(_companyId, toolSetName);

		_markToolSetFailed(toolSetName);

		_mcpToolIndexWriter.invalidate(_companyId);

		Map<String, Object> staleToolSetTokens = _getStaleToolSetTokens();

		Assert.assertTrue(staleToolSetTokens.containsKey(StringPool.STAR));
		Assert.assertTrue(staleToolSetTokens.containsKey(toolSetName));
	}

	@Test
	public void testMarkToolSetFailedWhenTheRetryTimeHasPassed() {
		String toolSetName = RandomTestUtil.randomString();

		_mcpToolIndexWriter.invalidate(_companyId, toolSetName);

		_markToolSetFailed(toolSetName);

		Map<String, Long> failedToolSetRetryTimes =
			ReflectionTestUtil.getFieldValue(
				_getIndexState(), "_failedToolSetRetryTimes");

		failedToolSetRetryTimes.put(toolSetName, 0L);

		Assert.assertTrue(_hasStaleToolSets());
	}

	@Test
	public void testMarkToolSetFailedWhenTheToolSetIsInvalidatedAgain() {
		String toolSetName = RandomTestUtil.randomString();

		_mcpToolIndexWriter.invalidate(_companyId, toolSetName);

		_markToolSetFailed(toolSetName);

		_mcpToolIndexWriter.invalidate(_companyId, toolSetName);

		Assert.assertTrue(_hasStaleToolSets());
	}

	private void _clearStaleToolSet(String toolSetName, Object token) {
		ReflectionTestUtil.invoke(
			_getIndexState(), "clearStaleToolSet",
			new Class<?>[] {String.class, Object.class}, toolSetName, token);
	}

	private Set<String> _getChangedToolSetNames(
		Set<String> indexedToolSetNames, Set<String> toolSetNames) {

		return ReflectionTestUtil.invoke(
			_mcpToolIndexWriter, "_getChangedToolSetNames",
			new Class<?>[] {Set.class, Map.class, Set.class},
			indexedToolSetNames, _getStaleToolSetTokens(), toolSetNames);
	}

	private Object _getIndexState() {
		Map<Long, Object> mcpToolIndexStates = ReflectionTestUtil.getFieldValue(
			_mcpToolIndexWriter, "_mcpToolIndexStates");

		return mcpToolIndexStates.get(_companyId);
	}

	private MCPTool _getMCPTool(String toolSetName) {
		return new MCPTool(
			false, false, StringPool.BLANK, "Entity", new String[0], "create",
			"post", StringPool.BLANK, new String[0], "/path", new String[0],
			new String[0], "tool", toolSetName);
	}

	private Map<String, Object> _getStaleToolSetTokens() {
		Object mcpToolIndexState = _getIndexState();

		if (mcpToolIndexState == null) {
			return new HashMap<>();
		}

		return ReflectionTestUtil.invoke(
			mcpToolIndexState, "getStaleToolSetTokens", new Class<?>[0]);
	}

	private boolean _hasStaleToolSets() {
		Object mcpToolIndexState = _getIndexState();

		Set<String> indexedToolSetNames = ReflectionTestUtil.getFieldValue(
			mcpToolIndexState, "_indexedToolSetNames");

		return ReflectionTestUtil.invoke(
			mcpToolIndexState, "isStale", new Class<?>[] {Set.class},
			indexedToolSetNames);
	}

	private void _markToolSetFailed(String toolSetName) {
		ReflectionTestUtil.invoke(
			_getIndexState(), "markToolSetFailed",
			new Class<?>[] {String.class}, toolSetName);
	}

	private long _companyId;
	private MCPToolIndexCreator _mcpToolIndexCreator;
	private MCPToolIndexWriter _mcpToolIndexWriter;

}