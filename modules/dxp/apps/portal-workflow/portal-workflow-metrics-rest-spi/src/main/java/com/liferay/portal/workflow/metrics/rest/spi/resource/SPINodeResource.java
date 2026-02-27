/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.rest.spi.resource;

import com.liferay.petra.function.UnsafeFunction;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.index.IndexNameBuilder;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.QueriesUtil;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.workflow.metrics.search.index.constants.WorkflowMetricsIndexNameConstants;

import java.util.List;

/**
 * @author Inácio Nery
 */
public class SPINodeResource<T> {

	public SPINodeResource(
		long companyId, IndexNameBuilder indexNameBuilder,
		SearchEngineAdapter searchEngineAdapter,
		UnsafeFunction<Document, T, SystemException> transformUnsafeFunction) {

		_companyId = companyId;
		_indexNameBuilder = indexNameBuilder;
		_searchEngineAdapter = searchEngineAdapter;
		_transformUnsafeFunction = transformUnsafeFunction;
	}

	public Page<T> getProcessNodesPage(Long processId) throws Exception {
		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.setIndexNames(
			_indexNameBuilder.getIndexName(_companyId) +
				WorkflowMetricsIndexNameConstants.SUFFIX_NODE);

		BooleanQuery booleanQuery = QueriesUtil.booleanQuery();

		searchSearchRequest.setQuery(
			booleanQuery.addMustQueryClauses(
				QueriesUtil.term("companyId", _companyId),
				QueriesUtil.term("deleted", Boolean.FALSE),
				QueriesUtil.term("processId", processId),
				QueriesUtil.term(
					"version", _getLatestProcessVersion(processId))));

		searchSearchRequest.setSize(10000);

		SearchSearchResponse searchSearchResponse =
			_searchEngineAdapter.execute(searchSearchRequest);

		SearchHits searchHits = searchSearchResponse.getSearchHits();

		return Page.of(
			TransformUtil.transform(
				searchHits.getSearchHits(),
				searchHit -> _transformUnsafeFunction.apply(
					searchHit.getDocument())));
	}

	private String _getLatestProcessVersion(long processId) {
		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.setIndexNames(
			_indexNameBuilder.getIndexName(_companyId) +
				WorkflowMetricsIndexNameConstants.SUFFIX_PROCESS);

		BooleanQuery booleanQuery = QueriesUtil.booleanQuery();

		searchSearchRequest.setQuery(
			booleanQuery.addMustQueryClauses(
				QueriesUtil.term("companyId", _companyId),
				QueriesUtil.term("processId", processId)));

		searchSearchRequest.setSelectedFieldNames("version");

		SearchSearchResponse searchSearchResponse =
			_searchEngineAdapter.execute(searchSearchRequest);

		SearchHits searchHits = searchSearchResponse.getSearchHits();

		List<SearchHit> searchHitsList = searchHits.getSearchHits();

		if (ListUtil.isEmpty(searchHitsList)) {
			return StringPool.BLANK;
		}

		SearchHit searchHit = searchHitsList.get(0);

		Document document = searchHit.getDocument();

		return GetterUtil.getString(document.getString("version"));
	}

	private final long _companyId;
	private final IndexNameBuilder _indexNameBuilder;
	private final SearchEngineAdapter _searchEngineAdapter;
	private final UnsafeFunction<Document, T, SystemException>
		_transformUnsafeFunction;

}