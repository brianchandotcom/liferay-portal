/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mcp.server.rest.internal.search.index;

import com.liferay.mcp.server.rest.internal.search.index.constants.MCPToolFields;
import com.liferay.mcp.server.rest.internal.util.ToolSetUtil;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.search.aggregation.Aggregations;
import com.liferay.portal.search.aggregation.bucket.Bucket;
import com.liferay.portal.search.aggregation.bucket.TermsAggregation;
import com.liferay.portal.search.aggregation.bucket.TermsAggregationResult;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.document.BulkDocumentItemResponse;
import com.liferay.portal.search.engine.adapter.document.BulkDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.BulkDocumentResponse;
import com.liferay.portal.search.engine.adapter.document.BulkableDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.DeleteByQueryDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.TermsQuery;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.vulcan.application.HeadlessApplicationProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
	public void setUp() throws Exception {
		Mockito.when(
			_aggregations.terms(Mockito.anyString(), Mockito.anyString())
		).thenReturn(
			Mockito.mock(TermsAggregation.class)
		);

		Mockito.when(
			_mcpToolIndexCreator.getIndexName(_COMPANY_ID)
		).thenReturn(
			RandomTestUtil.randomString()
		);

		Mockito.when(
			_mcpToolIndexCreator.hasIndex(_COMPANY_ID)
		).thenReturn(
			true
		);

		_mockIndexedToolSetHashes(Collections.emptyMap());

		Mockito.when(
			_mcpToolsUnsafeFunction.apply(Mockito.anyString())
		).thenAnswer(
			invocation -> _mcpTools.get(invocation.getArgument(0))
		);

		Mockito.when(
			_searchEngineAdapter.execute(Mockito.any(BulkDocumentRequest.class))
		).thenAnswer(
			invocation -> {
				_bulkDocumentRequests.add(invocation.getArgument(0));

				return _bulkDocumentResponse;
			}
		);

		Mockito.when(
			_searchEngineAdapter.execute(
				Mockito.any(DeleteByQueryDocumentRequest.class))
		).thenAnswer(
			invocation -> {
				_deleteByQueryDocumentRequests.add(invocation.getArgument(0));

				return null;
			}
		);

		ReflectionTestUtil.setFieldValue(
			_mcpToolIndexWriter, "_aggregations", _aggregations);
		ReflectionTestUtil.setFieldValue(
			_mcpToolIndexWriter, "_bundleVersion", "1.0.0");
		ReflectionTestUtil.setFieldValue(
			_mcpToolIndexWriter, "_mcpToolIndexCreator", _mcpToolIndexCreator);
		ReflectionTestUtil.setFieldValue(
			_mcpToolIndexWriter, "_searchEngineAdapter", _searchEngineAdapter);
	}

	@Test
	public void testDeleteIndex() throws Exception {
		_updateIfStale();

		_mcpToolIndexWriter.deleteIndex(_COMPANY_ID);

		Mockito.verify(
			_mcpToolIndexCreator
		).deleteIfExists(
			_COMPANY_ID
		);

		_updateIfStale();

		_verifyUpdated(2, _TOOL_SET_NAME_1);
		_verifyUpdated(2, _TOOL_SET_NAME_2);
	}

	@Test
	public void testGetToolSetHashes() {
		HeadlessApplicationProvider.Application application = _mockApplication(
			"GET /items", "POST /items");

		HeadlessApplicationProvider.OpenAPIDocument openAPIDocument =
			_mockOpenAPIDocument(application);

		String toolSetHash = _getToolSetHash(openAPIDocument);

		Assert.assertEquals(toolSetHash, _getToolSetHash(openAPIDocument));

		Mockito.verify(
			application
		).getResourceMethods();

		Assert.assertEquals(
			toolSetHash,
			_getToolSetHash(
				_mockOpenAPIDocument(
					_mockApplication("POST /items", "GET /items"))));
		Assert.assertNotEquals(
			toolSetHash,
			_getToolSetHash(
				_mockOpenAPIDocument(
					_mockApplication(
						"DELETE /items/{id}", "GET /items", "POST /items"))));

		ReflectionTestUtil.setFieldValue(
			_mcpToolIndexWriter, "_bundleVersion", "1.0.1");

		Assert.assertNotEquals(
			toolSetHash,
			_getToolSetHash(
				_mockOpenAPIDocument(
					_mockApplication("GET /items", "POST /items"))));
	}

	@Test
	public void testUpdateIfStale() throws Exception {
		_updateIfStale();

		_verifyUpdated(1, _TOOL_SET_NAME_1);
		_verifyUpdated(1, _TOOL_SET_NAME_2);

		Map<String, Document> documents = _getIndexedDocuments();

		Assert.assertEquals(
			_getToolKeys(_TOOL_SET_NAME_1, _TOOL_SET_NAME_2),
			documents.keySet());

		Document document = documents.get(
			ToolSetUtil.getToolKey(_TOOL_NAME_1, _TOOL_SET_NAME_1));

		Assert.assertEquals(
			_TOOL_SET_HASH_1, document.getString(MCPToolFields.TOOL_SET_HASH));
		Assert.assertEquals(
			Integer.valueOf(2),
			document.getInteger(MCPToolFields.TOOL_SET_SIZE));

		_assertDeleteByQueryDocumentRequest(
			_getToolKeys(_TOOL_SET_NAME_1, _TOOL_SET_NAME_2),
			SetUtil.fromArray(_TOOL_SET_NAME_1, _TOOL_SET_NAME_2));
	}

	@Test
	public void testUpdateIfStaleWhenAllToolSetsAreInvalidated()
		throws Exception {

		_updateIfStale();

		_mcpToolIndexWriter.invalidate(_COMPANY_ID);

		_updateIfStale();

		_verifyUpdated(2, _TOOL_SET_NAME_1);
		_verifyUpdated(2, _TOOL_SET_NAME_2);
	}

	@Test
	public void testUpdateIfStaleWhenDocumentIsRejected() throws Exception {
		BulkDocumentItemResponse bulkDocumentItemResponse =
			new BulkDocumentItemResponse();

		bulkDocumentItemResponse.setFailureMessage("strict mapping");
		bulkDocumentItemResponse.setId(
			ToolSetUtil.getToolKey(_TOOL_NAME_1, _TOOL_SET_NAME_2));

		_bulkDocumentResponse.addBulkDocumentItemResponse(
			bulkDocumentItemResponse);

		_bulkDocumentResponse.setErrors(true);

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				MCPToolIndexWriter.class.getName(), LoggerTestUtil.ERROR)) {

			_updateIfStale();

			_assertLogEntry(
				logCapture,
				"Unable to index tool \"" +
					ToolSetUtil.getToolKey(_TOOL_NAME_1, _TOOL_SET_NAME_2) +
						"\": strict mapping");
		}

		_bulkDocumentResponse = new BulkDocumentResponse(0);

		_updateIfStale();

		_verifyUpdated(1, _TOOL_SET_NAME_2);

		_mcpToolIndexWriter.invalidate(_COMPANY_ID, _TOOL_SET_NAME_2);

		_updateIfStale();

		_verifyUpdated(1, _TOOL_SET_NAME_1);
		_verifyUpdated(2, _TOOL_SET_NAME_2);
	}

	@Test
	public void testUpdateIfStaleWhenIndexIsCurrent() throws Exception {
		_mockIndexedToolSetHashes(
			HashMapBuilder.put(
				_TOOL_SET_NAME_1, Arrays.asList(_TOOL_SET_HASH_1)
			).put(
				_TOOL_SET_NAME_2, Arrays.asList(_TOOL_SET_HASH_2)
			).build());

		_updateIfStale();

		_verifyNoUpdates();

		Assert.assertEquals(
			_bulkDocumentRequests.toString(), 0, _bulkDocumentRequests.size());
	}

	@Test
	public void testUpdateIfStaleWhenNothingIsStale() throws Exception {
		_updateIfStale();
		_updateIfStale();

		_verifyUpdated(1, _TOOL_SET_NAME_1);
		_verifyUpdated(1, _TOOL_SET_NAME_2);
	}

	@Test
	public void testUpdateIfStaleWhenToolSetCannotBeBuilt() throws Exception {
		Mockito.when(
			_mcpToolsUnsafeFunction.apply(_TOOL_SET_NAME_2)
		).thenThrow(
			new Exception()
		);

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				MCPToolIndexWriter.class.getName(), LoggerTestUtil.ERROR)) {

			_updateIfStale();

			_assertLogEntry(
				logCapture,
				"Unable to build the tools of tool set \"" + _TOOL_SET_NAME_2 +
					"\"");
		}

		Assert.assertEquals(
			_getToolKeys(_TOOL_SET_NAME_1), _getIndexedDocuments().keySet());

		_assertDeleteByQueryDocumentRequest(
			_getToolKeys(_TOOL_SET_NAME_1),
			SetUtil.fromArray(_TOOL_SET_NAME_1));

		_updateIfStale();

		_verifyUpdated(1, _TOOL_SET_NAME_1);
		_verifyUpdated(1, _TOOL_SET_NAME_2);
	}

	@Test
	public void testUpdateIfStaleWhenToolSetHasDifferentHashes()
		throws Exception {

		_mockIndexedToolSetHashes(
			HashMapBuilder.put(
				_TOOL_SET_NAME_1, Arrays.asList(_TOOL_SET_HASH_1)
			).put(
				_TOOL_SET_NAME_2,
				Arrays.asList(_TOOL_SET_HASH_2, RandomTestUtil.randomString())
			).build());

		_updateIfStale();

		_verifyUpdated(0, _TOOL_SET_NAME_1);
		_verifyUpdated(1, _TOOL_SET_NAME_2);
	}

	@Test
	public void testUpdateIfStaleWhenToolSetHashChanged() throws Exception {
		_mockIndexedToolSetHashes(
			HashMapBuilder.put(
				_TOOL_SET_NAME_1, Arrays.asList(_TOOL_SET_HASH_1)
			).put(
				_TOOL_SET_NAME_2, Arrays.asList(RandomTestUtil.randomString())
			).build());

		_updateIfStale();

		_verifyUpdated(0, _TOOL_SET_NAME_1);
		_verifyUpdated(1, _TOOL_SET_NAME_2);

		_assertDeleteByQueryDocumentRequest(
			_getToolKeys(_TOOL_SET_NAME_2),
			SetUtil.fromArray(_TOOL_SET_NAME_2));
	}

	@Test
	public void testUpdateIfStaleWhenToolSetIsInvalidated() throws Exception {
		_updateIfStale();

		_mcpToolIndexWriter.invalidate(_COMPANY_ID, _TOOL_SET_NAME_1);

		_updateIfStale();

		_verifyUpdated(2, _TOOL_SET_NAME_1);
		_verifyUpdated(1, _TOOL_SET_NAME_2);
	}

	@Test
	public void testUpdateIfStaleWhenToolSetIsInvalidatedDuringUpdate()
		throws Exception {

		_updateIfStale();

		_mcpToolIndexWriter.invalidate(_COMPANY_ID, _TOOL_SET_NAME_1);

		Mockito.when(
			_mcpToolsUnsafeFunction.apply(_TOOL_SET_NAME_1)
		).thenAnswer(
			invocation -> {
				_mcpToolIndexWriter.invalidate(_COMPANY_ID, _TOOL_SET_NAME_1);

				return _mcpTools.get(_TOOL_SET_NAME_1);
			}
		).thenReturn(
			_mcpTools.get(_TOOL_SET_NAME_1)
		);

		_updateIfStale();
		_updateIfStale();

		_verifyUpdated(3, _TOOL_SET_NAME_1);
		_verifyUpdated(1, _TOOL_SET_NAME_2);
	}

	@Test
	public void testUpdateIfStaleWhenToolSetWasRemoved() throws Exception {
		String removedToolSetName = RandomTestUtil.randomString();

		_mockIndexedToolSetHashes(
			HashMapBuilder.put(
				_TOOL_SET_NAME_1, Arrays.asList(_TOOL_SET_HASH_1)
			).put(
				_TOOL_SET_NAME_2, Arrays.asList(_TOOL_SET_HASH_2)
			).put(
				removedToolSetName, Arrays.asList(RandomTestUtil.randomString())
			).build());

		_updateIfStale();

		_verifyNoUpdates();

		_assertDeleteByQueryDocumentRequest(
			Collections.emptySet(), SetUtil.fromArray(removedToolSetName));
	}

	private void _assertDeleteByQueryDocumentRequest(
		Set<String> toolKeys, Set<String> toolSetNames) {

		DeleteByQueryDocumentRequest deleteByQueryDocumentRequest =
			_deleteByQueryDocumentRequests.getFirst();

		BooleanQuery booleanQuery =
			(BooleanQuery)deleteByQueryDocumentRequest.getPortalSearchQuery();

		List<?> mustQueryClauses = booleanQuery.getMustQueryClauses();

		TermsQuery termsQuery = (TermsQuery)mustQueryClauses.getFirst();

		Assert.assertEquals(
			toolSetNames, SetUtil.fromArray(termsQuery.getValues()));

		List<?> mustNotQueryClauses = booleanQuery.getMustNotQueryClauses();

		if (toolKeys.isEmpty()) {
			Assert.assertEquals(
				mustNotQueryClauses.toString(), 0, mustNotQueryClauses.size());

			return;
		}

		termsQuery = (TermsQuery)mustNotQueryClauses.getFirst();

		Assert.assertEquals(
			toolKeys, SetUtil.fromArray(termsQuery.getValues()));
	}

	private void _assertLogEntry(LogCapture logCapture, String message) {
		List<LogEntry> logEntries = logCapture.getLogEntries();

		Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

		LogEntry logEntry = logEntries.getFirst();

		Assert.assertEquals(message, logEntry.getMessage());
	}

	private Map<String, Document> _getIndexedDocuments() {
		Map<String, Document> documents = new HashMap<>();

		for (BulkDocumentRequest bulkDocumentRequest : _bulkDocumentRequests) {
			for (BulkableDocumentRequest<?> bulkableDocumentRequest :
					bulkDocumentRequest.getBulkableDocumentRequests()) {

				IndexDocumentRequest indexDocumentRequest =
					(IndexDocumentRequest)bulkableDocumentRequest;

				documents.put(
					indexDocumentRequest.getUid(),
					indexDocumentRequest.getDocument());
			}
		}

		return documents;
	}

	private MCPTool _getMCPTool(String toolName, String toolSetName) {
		return new MCPTool(
			null, false, StringPool.BLANK, "Entity", new String[0], "create",
			"post", StringPool.BLANK, new String[0], "/path", new String[0],
			new String[0], toolName, toolSetName);
	}

	private Set<String> _getToolKeys(String... toolSetNames) {
		Set<String> toolKeys = new HashSet<>();

		for (String toolSetName : toolSetNames) {
			for (MCPTool mcpTool : _mcpTools.get(toolSetName)) {
				toolKeys.add(mcpTool.getToolKey());
			}
		}

		return toolKeys;
	}

	private String _getToolSetHash(
		HeadlessApplicationProvider.OpenAPIDocument openAPIDocument) {

		Map<String, String> toolSetHashes = ReflectionTestUtil.invoke(
			_mcpToolIndexWriter, "_getToolSetHashes",
			new Class<?>[] {Map.class},
			Collections.singletonMap("toolSet", openAPIDocument));

		return toolSetHashes.get("toolSet");
	}

	private HeadlessApplicationProvider.Application _mockApplication(
		String... resourceMethods) {

		HeadlessApplicationProvider.Application application = Mockito.mock(
			HeadlessApplicationProvider.Application.class);

		List<HeadlessApplicationProvider.ResourceMethod> mockResourceMethods =
			new ArrayList<>();

		for (String resourceMethod : resourceMethods) {
			HeadlessApplicationProvider.ResourceMethod mockResourceMethod =
				Mockito.mock(HeadlessApplicationProvider.ResourceMethod.class);

			String[] parts = resourceMethod.split(StringPool.SPACE);

			Mockito.when(
				mockResourceMethod.getMethod()
			).thenReturn(
				parts[0]
			);

			Mockito.when(
				mockResourceMethod.getPath()
			).thenReturn(
				parts[1]
			);

			mockResourceMethods.add(mockResourceMethod);
		}

		Mockito.when(
			application.getResourceMethods()
		).thenReturn(
			mockResourceMethods
		);

		return application;
	}

	private void _mockIndexedToolSetHashes(
		Map<String, List<String>> indexedToolSetHashes) {

		TermsAggregationResult termsAggregationResult =
			new TermsAggregationResult(MCPToolFields.TOOL_SET_NAME, 0, 0);

		for (Map.Entry<String, List<String>> entry :
				indexedToolSetHashes.entrySet()) {

			Bucket bucket = termsAggregationResult.addBucket(entry.getKey(), 1);

			TermsAggregationResult toolSetHashesTermsAggregationResult =
				new TermsAggregationResult(MCPToolFields.TOOL_SET_HASH, 0, 0);

			for (String toolSetHash : entry.getValue()) {
				toolSetHashesTermsAggregationResult.addBucket(toolSetHash, 1);
			}

			bucket.addChildAggregationResult(
				toolSetHashesTermsAggregationResult);
		}

		SearchSearchResponse searchSearchResponse = new SearchSearchResponse();

		searchSearchResponse.addAggregationResult(termsAggregationResult);

		Mockito.when(
			_searchEngineAdapter.execute(Mockito.any(SearchSearchRequest.class))
		).thenReturn(
			searchSearchResponse
		);
	}

	private HeadlessApplicationProvider.OpenAPIDocument _mockOpenAPIDocument(
		HeadlessApplicationProvider.Application application) {

		HeadlessApplicationProvider.OpenAPIDocument openAPIDocument =
			Mockito.mock(HeadlessApplicationProvider.OpenAPIDocument.class);

		Mockito.when(
			openAPIDocument.getApplication()
		).thenReturn(
			application
		);

		Mockito.when(
			openAPIDocument.getVersion()
		).thenReturn(
			"v1.0"
		);

		return openAPIDocument;
	}

	private void _updateIfStale() {
		_mcpToolIndexWriter.updateIfStale(
			_COMPANY_ID, _mcpToolsUnsafeFunction, _toolSetHashes);
	}

	private void _verifyNoUpdates() throws Exception {
		Mockito.verify(
			_mcpToolsUnsafeFunction, Mockito.never()
		).apply(
			Mockito.anyString()
		);
	}

	private void _verifyUpdated(int times, String toolSetName)
		throws Exception {

		Mockito.verify(
			_mcpToolsUnsafeFunction, Mockito.times(times)
		).apply(
			toolSetName
		);
	}

	private static final long _COMPANY_ID = RandomTestUtil.randomLong();

	private static final String _TOOL_NAME_1 = RandomTestUtil.randomString();

	private static final String _TOOL_NAME_2 = RandomTestUtil.randomString();

	private static final String _TOOL_SET_HASH_1 =
		RandomTestUtil.randomString();

	private static final String _TOOL_SET_HASH_2 =
		RandomTestUtil.randomString();

	private static final String _TOOL_SET_NAME_1 =
		RandomTestUtil.randomString();

	private static final String _TOOL_SET_NAME_2 =
		RandomTestUtil.randomString();

	private final Aggregations _aggregations = Mockito.mock(Aggregations.class);
	private final List<BulkDocumentRequest> _bulkDocumentRequests =
		new ArrayList<>();
	private BulkDocumentResponse _bulkDocumentResponse =
		new BulkDocumentResponse(0);
	private final List<DeleteByQueryDocumentRequest>
		_deleteByQueryDocumentRequests = new ArrayList<>();
	private final MCPToolIndexCreator _mcpToolIndexCreator = Mockito.mock(
		MCPToolIndexCreator.class);
	private final MCPToolIndexWriter _mcpToolIndexWriter =
		new MCPToolIndexWriter();
	private final Map<String, List<MCPTool>> _mcpTools =
		HashMapBuilder.<String, List<MCPTool>>put(
			_TOOL_SET_NAME_1,
			Arrays.asList(
				_getMCPTool(_TOOL_NAME_1, _TOOL_SET_NAME_1),
				_getMCPTool(_TOOL_NAME_2, _TOOL_SET_NAME_1))
		).put(
			_TOOL_SET_NAME_2,
			Arrays.asList(_getMCPTool(_TOOL_NAME_1, _TOOL_SET_NAME_2))
		).build();

	@SuppressWarnings("unchecked")
	private final UnsafeFunction<String, List<MCPTool>, Exception>
		_mcpToolsUnsafeFunction = Mockito.mock(UnsafeFunction.class);

	private final SearchEngineAdapter _searchEngineAdapter = Mockito.mock(
		SearchEngineAdapter.class);
	private final Map<String, String> _toolSetHashes = HashMapBuilder.put(
		_TOOL_SET_NAME_1, _TOOL_SET_HASH_1
	).put(
		_TOOL_SET_NAME_2, _TOOL_SET_HASH_2
	).build();

}