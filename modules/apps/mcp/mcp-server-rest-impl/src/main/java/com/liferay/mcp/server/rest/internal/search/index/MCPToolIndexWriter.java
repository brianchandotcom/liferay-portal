/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mcp.server.rest.internal.search.index;

import com.liferay.mcp.server.rest.internal.search.index.constants.MCPToolFields;
import com.liferay.mcp.server.rest.internal.util.OpenAPIUtil;
import com.liferay.mcp.server.rest.internal.util.ToolSetUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.DigesterUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.aggregation.AggregationResult;
import com.liferay.portal.search.aggregation.Aggregations;
import com.liferay.portal.search.aggregation.bucket.Bucket;
import com.liferay.portal.search.aggregation.bucket.TermsAggregation;
import com.liferay.portal.search.aggregation.bucket.TermsAggregationResult;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.document.DocumentBuilder;
import com.liferay.portal.search.document.DocumentBuilderFactory;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.document.BulkDocumentItemResponse;
import com.liferay.portal.search.engine.adapter.document.BulkDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.BulkDocumentResponse;
import com.liferay.portal.search.engine.adapter.document.DeleteByQueryDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.QueriesUtil;
import com.liferay.portal.search.query.TermsQuery;
import com.liferay.portal.vulcan.application.HeadlessApplicationProvider;

import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(service = MCPToolIndexWriter.class)
public class MCPToolIndexWriter {

	public void deleteIndex(long companyId) {
		MCPToolIndexState mcpToolIndexState = _getIndexState(companyId);

		synchronized (mcpToolIndexState) {
			_mcpToolIndexCreator.deleteIfExists(companyId);

			mcpToolIndexState.reset();
		}
	}

	public void invalidate(long companyId) {
		MCPToolIndexState mcpToolIndexState = _getIndexState(companyId);

		mcpToolIndexState.markAllToolSetsStale();
	}

	public void invalidate(long companyId, String toolSetName) {
		MCPToolIndexState mcpToolIndexState = _getIndexState(companyId);

		mcpToolIndexState.markToolSetStale(toolSetName);
	}

	public void updateIfStale(
		long companyId, HttpServletRequest httpServletRequest) {

		MCPToolIndexState mcpToolIndexState = _getIndexState(companyId);

		Map<String, HeadlessApplicationProvider.OpenAPIDocument>
			openAPIDocuments = ToolSetUtil.getOpenAPIDocuments();

		openAPIDocuments.remove(ToolSetUtil.TOOL_SET_NAME);

		Set<String> toolSetNames = openAPIDocuments.keySet();

		if (!mcpToolIndexState.isStale(toolSetNames)) {
			return;
		}

		synchronized (mcpToolIndexState) {

			// Ensure state was not updated by the previous thread

			if (!mcpToolIndexState.isStale(toolSetNames)) {
				return;
			}

			_update(
				companyId, httpServletRequest, mcpToolIndexState,
				openAPIDocuments, toolSetNames);
		}
	}

	private void _deleteDocuments(
		String indexName, Map<String, String> toolSetHashes,
		Set<String> toolSetNames) {

		if (toolSetNames.isEmpty()) {
			return;
		}

		TermsQuery toolSetNamesTermsQuery = QueriesUtil.terms(
			MCPToolFields.TOOL_SET_NAME);

		toolSetNamesTermsQuery.addValues(toolSetNames.toArray());

		BooleanQuery booleanQuery = QueriesUtil.booleanQuery();

		booleanQuery.addMustQueryClauses(toolSetNamesTermsQuery);

		if (!toolSetHashes.isEmpty()) {
			TermsQuery toolSetHashesTermsQuery = QueriesUtil.terms(
				MCPToolFields.TOOL_SET_HASH);

			Collection<String> values = toolSetHashes.values();

			toolSetHashesTermsQuery.addValues(values.toArray());

			booleanQuery.addMustNotQueryClauses(toolSetHashesTermsQuery);
		}

		DeleteByQueryDocumentRequest deleteByQueryDocumentRequest =
			new DeleteByQueryDocumentRequest(booleanQuery, indexName);

		deleteByQueryDocumentRequest.setRefresh(true);

		_searchEngineAdapter.execute(deleteByQueryDocumentRequest);
	}

	private Set<String> _getChangedToolSetNames(
		Set<String> indexedToolSetNames, Map<String, Object> staleToolSetTokens,
		Set<String> toolSetNames) {

		Set<String> changedToolSetNames = new HashSet<>(toolSetNames);

		if (staleToolSetTokens.containsKey(StringPool.STAR)) {
			return changedToolSetNames;
		}

		changedToolSetNames.removeAll(indexedToolSetNames);

		for (String staleToolSetName : staleToolSetTokens.keySet()) {
			if (toolSetNames.contains(staleToolSetName)) {
				changedToolSetNames.add(staleToolSetName);
			}
		}

		return changedToolSetNames;
	}

	private String _getCollectionSegment(MCPTool mcpTool) {
		if (!mcpTool.isCollection() ||
			Validator.isNotNull(mcpTool.getOperationVariant()) ||
			!Objects.equals(mcpTool.getMethod(), "get")) {

			return null;
		}

		String[] segments = StringUtil.split(mcpTool.getPath(), CharPool.SLASH);

		String segment = segments[segments.length - 1];

		if (OpenAPIUtil.isPathParameter(segment)) {
			return null;
		}

		return segment;
	}

	private Document _getDocument(
		MCPTool mcpTool, String toolSetHash, int toolSetSize) {

		DocumentBuilder documentBuilder = DocumentBuilderFactory.builder();

		String collectionSegment = _getCollectionSegment(mcpTool);

		if (collectionSegment != null) {
			documentBuilder.setString(
				MCPToolFields.COLLECTION_SEGMENT, collectionSegment);
		}

		documentBuilder.setValue(
			MCPToolFields.DEPRECATED, mcpTool.isDeprecated());
		documentBuilder.setString(
			MCPToolFields.DESCRIPTION, mcpTool.getDescription());
		documentBuilder.setString(
			MCPToolFields.ENTITY_NAME, mcpTool.getEntityName());
		documentBuilder.setStrings(
			MCPToolFields.EXPANSION, mcpTool.getExpansions());
		documentBuilder.setString(MCPToolFields.INTENT, mcpTool.getIntent());
		documentBuilder.setString(MCPToolFields.METHOD, mcpTool.getMethod());
		documentBuilder.setString(
			MCPToolFields.OPERATION_VARIANT, mcpTool.getOperationVariant());
		documentBuilder.setStrings(
			MCPToolFields.PARAMETERS, mcpTool.getParameters());

		String path = mcpTool.getPath();

		documentBuilder.setString(MCPToolFields.PATH, path);
		documentBuilder.setValue(MCPToolFields.PATH_LENGTH, path.length());
		documentBuilder.setValue(
			MCPToolFields.PATH_PARAMETER_COUNT,
			StringUtil.count(path, CharPool.OPEN_CURLY_BRACE));
		documentBuilder.setValue(
			MCPToolFields.PATH_SEGMENT_COUNT,
			StringUtil.count(path, CharPool.SLASH));

		documentBuilder.setStrings(
			MCPToolFields.REQUIRED_REFERENCES, mcpTool.getRequiredReferences());
		documentBuilder.setStrings(
			MCPToolFields.SCHEMA_PROPERTIES, mcpTool.getSchemaProperties());
		documentBuilder.setString(
			MCPToolFields.TOOL_NAME, mcpTool.getToolName());
		documentBuilder.setString(MCPToolFields.TOOL_SET_HASH, toolSetHash);
		documentBuilder.setString(
			MCPToolFields.TOOL_SET_NAME, mcpTool.getToolSetName());
		documentBuilder.setValue(MCPToolFields.TOOL_SET_SIZE, toolSetSize);
		documentBuilder.setString(MCPToolFields.UID, mcpTool.getUID());

		return documentBuilder.build();
	}

	private MCPToolIndexState _getIndexState(long companyId) {
		return _mcpToolIndexStates.computeIfAbsent(
			companyId, key -> new MCPToolIndexState());
	}

	private Set<String> _getIndexedToolSetNames(long companyId) {
		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		TermsAggregation termsAggregation = _aggregations.terms(
			MCPToolFields.TOOL_SET_NAME, MCPToolFields.TOOL_SET_NAME);

		termsAggregation.setSize(_INDEXED_TOOL_SET_NAMES_MAX);

		searchSearchRequest.addAggregation(termsAggregation);

		searchSearchRequest.setIndexNames(
			_mcpToolIndexCreator.getIndexName(companyId));
		searchSearchRequest.setQuery(QueriesUtil.matchAll());
		searchSearchRequest.setSize(0);

		SearchSearchResponse searchSearchResponse =
			_searchEngineAdapter.execute(searchSearchRequest);

		Map<String, AggregationResult> aggregationResultsMap =
			searchSearchResponse.getAggregationResultsMap();

		TermsAggregationResult termsAggregationResult =
			(TermsAggregationResult)aggregationResultsMap.get(
				MCPToolFields.TOOL_SET_NAME);

		Set<String> indexedToolSetNames = new HashSet<>();

		for (Bucket bucket : termsAggregationResult.getBuckets()) {
			indexedToolSetNames.add(bucket.getKey());
		}

		return indexedToolSetNames;
	}

	private List<MCPTool> _getMCPTools(
		Set<String> changedToolSetNames, Set<String> failedToolSetNames,
		HttpServletRequest httpServletRequest,
		Map<String, HeadlessApplicationProvider.OpenAPIDocument>
			openAPIDocuments) {

		List<MCPTool> mcpTools = new ArrayList<>();

		for (String toolSetName : changedToolSetNames) {
			HeadlessApplicationProvider.OpenAPIDocument openAPIDocument =
				openAPIDocuments.get(toolSetName);

			try {
				mcpTools.addAll(
					MCPToolFactoryUtil.getMCPTools(
						httpServletRequest, openAPIDocument, toolSetName));
			}
			catch (Exception exception) {
				failedToolSetNames.add(toolSetName);

				_log.error(
					"Unable to build the tools of tool set \"" + toolSetName +
						"\"",
					exception);
			}
		}

		return mcpTools;
	}

	private Map<String, String> _getToolSetHashes(
		Collection<MCPTool> mcpTools) {

		Map<String, String> toolSetHashes = new HashMap<>();

		Map<String, Set<String>> toolSetUIDsMap = new HashMap<>();

		for (MCPTool mcpTool : mcpTools) {
			Set<String> toolSetUIDs = toolSetUIDsMap.computeIfAbsent(
				mcpTool.getToolSetName(), key -> new TreeSet<>());

			toolSetUIDs.add(mcpTool.getUID());
		}

		for (Map.Entry<String, Set<String>> entry : toolSetUIDsMap.entrySet()) {
			String toolSetUIDsString = StringUtil.merge(
				entry.getValue(), StringPool.POUND);

			toolSetHashes.put(
				entry.getKey(), DigesterUtil.digest(toolSetUIDsString));
		}

		return toolSetHashes;
	}

	private Map<String, Integer> _getToolSetSizes(
		Collection<MCPTool> mcpTools) {

		Map<String, Integer> toolSetSizes = new HashMap<>();

		for (MCPTool mcpTool : mcpTools) {
			int toolSetSize = GetterUtil.getInteger(
				toolSetSizes.get(mcpTool.getToolSetName()));

			toolSetSizes.put(mcpTool.getToolSetName(), toolSetSize + 1);
		}

		return toolSetSizes;
	}

	private Set<String> _index(
		String indexName, Collection<MCPTool> mcpTools,
		Map<String, String> toolSetHashes) {

		if (mcpTools.isEmpty()) {
			return Collections.emptySet();
		}

		Map<String, Integer> toolSetSizes = _getToolSetSizes(mcpTools);

		BulkDocumentRequest bulkDocumentRequest = new BulkDocumentRequest();

		for (MCPTool mcpTool : mcpTools) {
			bulkDocumentRequest.addBulkableDocumentRequest(
				new IndexDocumentRequest(
					indexName, mcpTool.getUID(),
					_getDocument(
						mcpTool, toolSetHashes.get(mcpTool.getToolSetName()),
						GetterUtil.getInteger(
							toolSetSizes.get(mcpTool.getToolSetName())))));
		}

		// The triggering thread needs the results immediately

		bulkDocumentRequest.setRefresh(true);

		BulkDocumentResponse bulkDocumentResponse =
			_searchEngineAdapter.execute(bulkDocumentRequest);

		if (!bulkDocumentResponse.hasErrors()) {
			return Collections.emptySet();
		}

		Set<String> failedToolSetNames = new HashSet<>();

		for (BulkDocumentItemResponse bulkDocumentItemResponse :
				bulkDocumentResponse.getBulkDocumentItemResponses()) {

			String failureMessage =
				bulkDocumentItemResponse.getFailureMessage();

			if (failureMessage == null) {
				continue;
			}

			String toolKey = bulkDocumentItemResponse.getId();

			_log.error(
				StringBundler.concat(
					"Unable to index tool \"", toolKey, "\": ",
					failureMessage));

			failedToolSetNames.add(
				toolKey.substring(0, toolKey.indexOf(CharPool.POUND)));
		}

		return failedToolSetNames;
	}

	private void _update(
		long companyId, HttpServletRequest httpServletRequest,
		MCPToolIndexState mcpToolIndexState,
		Map<String, HeadlessApplicationProvider.OpenAPIDocument>
			openAPIDocuments,
		Set<String> toolSetNames) {

		Set<String> indexedToolSetNames = new HashSet<>();

		if (_mcpToolIndexCreator.indexExists(companyId)) {
			indexedToolSetNames = mcpToolIndexState.getIndexedToolSetNames();

			if (indexedToolSetNames.isEmpty()) {
				indexedToolSetNames = _getIndexedToolSetNames(companyId);
			}
		}

		_mcpToolIndexCreator.createIfNotExists(companyId);

		Map<String, Object> staleToolSetTokens =
			mcpToolIndexState.getStaleToolSetTokens();

		Set<String> changedToolSetNames = _getChangedToolSetNames(
			indexedToolSetNames, staleToolSetTokens, toolSetNames);

		Set<String> failedToolSetNames = new HashSet<>();

		List<MCPTool> mcpTools = _getMCPTools(
			changedToolSetNames, failedToolSetNames, httpServletRequest,
			openAPIDocuments);

		changedToolSetNames.removeAll(failedToolSetNames);

		failedToolSetNames.addAll(
			_updateIndex(
				changedToolSetNames, companyId, indexedToolSetNames, mcpTools,
				toolSetNames));

		_updateIndexState(
			failedToolSetNames, mcpToolIndexState, staleToolSetTokens,
			toolSetNames);
	}

	private Set<String> _updateIndex(
		Set<String> changedToolSetNames, long companyId,
		Set<String> indexedToolSetNames, List<MCPTool> mcpTools,
		Set<String> toolSetNames) {

		String indexName = _mcpToolIndexCreator.getIndexName(companyId);

		Map<String, String> changedToolSetHashes = _getToolSetHashes(mcpTools);

		Set<String> failedToolSetNames = _index(
			indexName, mcpTools, changedToolSetHashes);

		_deleteDocuments(indexName, changedToolSetHashes, changedToolSetNames);

		Set<String> removedToolSetNames = new HashSet<>(indexedToolSetNames);

		removedToolSetNames.removeAll(toolSetNames);

		_deleteDocuments(
			indexName, Collections.emptyMap(), removedToolSetNames);

		return failedToolSetNames;
	}

	private void _updateIndexState(
		Set<String> failedToolSetNames, MCPToolIndexState mcpToolIndexState,
		Map<String, Object> staleToolSetTokens, Set<String> toolSetNames) {

		mcpToolIndexState.setIndexedToolSetNames(toolSetNames);

		for (Map.Entry<String, Object> entry : staleToolSetTokens.entrySet()) {
			if (!failedToolSetNames.contains(entry.getKey())) {
				mcpToolIndexState.clearStaleToolSet(
					entry.getKey(), entry.getValue());
			}
		}

		for (String failedToolSetName : failedToolSetNames) {
			mcpToolIndexState.markToolSetFailed(failedToolSetName);
		}
	}

	private static final long _FAILED_TOOL_SET_RETRY_INTERVAL = Time.MINUTE;

	private static final int _INDEXED_TOOL_SET_NAMES_MAX = 1000;

	private static final Log _log = LogFactoryUtil.getLog(
		MCPToolIndexWriter.class);

	@Reference
	private Aggregations _aggregations;

	@Reference
	private MCPToolIndexCreator _mcpToolIndexCreator;

	private final Map<Long, MCPToolIndexState> _mcpToolIndexStates =
		new ConcurrentHashMap<>();

	@Reference
	private SearchEngineAdapter _searchEngineAdapter;

	private static class MCPToolIndexState {

		public void clearStaleToolSet(String toolSetName, Object token) {
			_staleToolSetTokens.remove(toolSetName, token);
		}

		public Set<String> getIndexedToolSetNames() {
			return new HashSet<>(_indexedToolSetNames);
		}

		public Map<String, Object> getStaleToolSetTokens() {
			Map<String, Object> staleToolSetTokens = new HashMap<>();

			long time = System.currentTimeMillis();

			for (Map.Entry<String, Object> entry :
					_staleToolSetTokens.entrySet()) {

				if (_isDue(entry.getKey(), time)) {
					staleToolSetTokens.put(entry.getKey(), entry.getValue());
				}
			}

			return staleToolSetTokens;
		}

		public boolean isStale(Set<String> toolSetNames) {
			if (!_indexedToolSetNames.equals(toolSetNames)) {
				return true;
			}

			for (String staleToolSetName : _staleToolSetTokens.keySet()) {
				if (_isDue(staleToolSetName, System.currentTimeMillis())) {
					return true;
				}
			}

			return false;
		}

		public void markAllToolSetsStale() {
			_failedToolSetRetryTimes.clear();

			_staleToolSetTokens.put(StringPool.STAR, new Object());
		}

		public void markToolSetFailed(String toolSetName) {
			_failedToolSetRetryTimes.put(
				toolSetName,
				System.currentTimeMillis() + _FAILED_TOOL_SET_RETRY_INTERVAL);

			_staleToolSetTokens.putIfAbsent(toolSetName, new Object());
		}

		public void markToolSetStale(String toolSetName) {
			_failedToolSetRetryTimes.remove(toolSetName);

			_staleToolSetTokens.put(toolSetName, new Object());
		}

		public void reset() {
			_indexedToolSetNames.clear();

			markAllToolSetsStale();
		}

		public void setIndexedToolSetNames(Set<String> toolSetNames) {
			_indexedToolSetNames.clear();
			_indexedToolSetNames.addAll(toolSetNames);
		}

		private boolean _isDue(String toolSetName, long time) {
			Long retryTime = _failedToolSetRetryTimes.get(toolSetName);

			if ((retryTime == null) || (retryTime <= time)) {
				return true;
			}

			return false;
		}

		private final Map<String, Long> _failedToolSetRetryTimes =
			new ConcurrentHashMap<>();
		private final Set<String> _indexedToolSetNames =
			ConcurrentHashMap.newKeySet();
		private final Map<String, Object> _staleToolSetTokens =
			new ConcurrentHashMap<>();

	}

}