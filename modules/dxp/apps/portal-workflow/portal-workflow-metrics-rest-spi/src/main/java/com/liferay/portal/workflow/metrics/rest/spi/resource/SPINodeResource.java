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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.engine.adapter.search.SearchRequestExecutor;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.vulcan.pagination.Page;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Inácio Nery
 */
public class SPINodeResource<T> {

	public SPINodeResource(
		long companyId, Queries queries,
		SearchRequestExecutor searchRequestExecutor,
		UnsafeFunction<Document, T, SystemException> transformUnsafeFunction) {

		_companyId = companyId;
		_queries = queries;
		_searchRequestExecutor = searchRequestExecutor;
		_transformUnsafeFunction = transformUnsafeFunction;
	}

	public Page<T> getProcessNodesPage(Long processId) throws Exception {
		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.setIndexNames("workflow-metrics-nodes");

		BooleanQuery booleanQuery = _queries.booleanQuery();

		searchSearchRequest.setQuery(
			booleanQuery.addMustQueryClauses(
				_queries.term("companyId", _companyId),
				_queries.term("deleted", Boolean.FALSE),
				_queries.term("processId", processId),
				_queries.term(
					"version",
					_getLatestProcessVersion(_companyId, processId))));

		searchSearchRequest.setSize(10000);

		return Page.of(
			Stream.of(
				_searchRequestExecutor.executeSearchRequest(searchSearchRequest)
			).map(
				SearchSearchResponse::getSearchHits
			).map(
				SearchHits::getSearchHits
			).flatMap(
				List::stream
			).map(
				SearchHit::getDocument
			).map(
				_transformUnsafeFunction::apply
			).collect(
				Collectors.toList()
			));
	}

	private String _getLatestProcessVersion(long companyId, long processId) {
		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.setIndexNames("workflow-metrics-processes");

		BooleanQuery booleanQuery = _queries.booleanQuery();

		searchSearchRequest.setQuery(
			booleanQuery.addMustQueryClauses(
				_queries.term("companyId", companyId),
				_queries.term("processId", processId)));

		searchSearchRequest.setSelectedFieldNames("version");

		return Stream.of(
			_searchRequestExecutor.executeSearchRequest(searchSearchRequest)
		).map(
			SearchSearchResponse::getSearchHits
		).map(
			SearchHits::getSearchHits
		).flatMap(
			List::parallelStream
		).map(
			SearchHit::getDocument
		).findFirst(
		).map(
			document -> document.getString("version")
		).orElseGet(
			() -> StringPool.BLANK
		);
	}

	private final long _companyId;
	private final Queries _queries;
	private final SearchRequestExecutor _searchRequestExecutor;
	private final UnsafeFunction<Document, T, SystemException>
		_transformUnsafeFunction;

}