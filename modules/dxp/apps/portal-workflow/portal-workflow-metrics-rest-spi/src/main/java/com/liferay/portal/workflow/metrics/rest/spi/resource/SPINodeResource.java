/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.workflow.metrics.rest.spi.resource;

import com.liferay.petra.function.UnsafeFunction;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.engine.adapter.search.SearchRequestExecutor;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.index.IndexNameBuilder;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.workflow.metrics.search.index.constants.WorkflowMetricsIndexEntityNameConstants;

import java.util.List;

/**
 * @author Inácio Nery
 */
public class SPINodeResource<T> {

	public SPINodeResource(
		long companyId, IndexNameBuilder indexNameBuilder, Queries queries,
		SearchRequestExecutor searchRequestExecutor,
		UnsafeFunction<Document, T, SystemException> transformUnsafeFunction) {

		_companyId = companyId;
		_indexNameBuilder = indexNameBuilder;
		_queries = queries;
		_searchRequestExecutor = searchRequestExecutor;
		_transformUnsafeFunction = transformUnsafeFunction;
	}

	public Page<T> getProcessNodesPage(Long processId) throws Exception {
		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.setIndexNames(
			_indexNameBuilder.getIndexName(_companyId) +
				WorkflowMetricsIndexEntityNameConstants.SUFFIX_NODE);

		BooleanQuery booleanQuery = _queries.booleanQuery();

		searchSearchRequest.setQuery(
			booleanQuery.addMustQueryClauses(
				_queries.term("companyId", _companyId),
				_queries.term("deleted", Boolean.FALSE),
				_queries.term("processId", processId),
				_queries.term("version", _getLatestProcessVersion(processId))));

		searchSearchRequest.setSize(10000);

		SearchSearchResponse searchSearchResponse =
			_searchRequestExecutor.executeSearchRequest(searchSearchRequest);

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
				WorkflowMetricsIndexEntityNameConstants.SUFFIX_PROCESS);

		BooleanQuery booleanQuery = _queries.booleanQuery();

		searchSearchRequest.setQuery(
			booleanQuery.addMustQueryClauses(
				_queries.term("companyId", _companyId),
				_queries.term("processId", processId)));

		searchSearchRequest.setSelectedFieldNames("version");

		SearchSearchResponse searchSearchResponse =
			_searchRequestExecutor.executeSearchRequest(searchSearchRequest);

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
	private final Queries _queries;
	private final SearchRequestExecutor _searchRequestExecutor;
	private final UnsafeFunction<Document, T, SystemException>
		_transformUnsafeFunction;

}