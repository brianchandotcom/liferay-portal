/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.search;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.Time;
import co.elastic.clients.elasticsearch._types.TimeUnit;
import co.elastic.clients.elasticsearch.core.ScrollRequest;
import co.elastic.clients.elasticsearch.core.ScrollResponse;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.json.JsonData;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.search.elasticsearch8.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;

import java.io.IOException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(service = SearchSearchRequestExecutor.class)
public class SearchSearchRequestExecutorImpl
	implements SearchSearchRequestExecutor {

	@Override
	public SearchSearchResponse execute(
		SearchSearchRequest searchSearchRequest) {

		SearchRequest.Builder searchRequestBuilder =
			new SearchRequest.Builder();

		_searchSearchRequestAssembler.assemble(
			searchRequestBuilder, searchSearchRequest);

		String indexNames = ArrayUtil.toString(
			searchSearchRequest.getIndexNames(), "");

		if (_log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					"Stack trace for [", indexNames, "]: ",
					DebugStringsUtil.getStackTraceString()));
		}

		String searchRequestString = DebugStringsUtil.getSearchRequestString(
			searchRequestBuilder);

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Search request string for [", indexNames, "]: ",
					searchRequestString));
		}

		SearchResponse<JsonData> searchResponse = null;

		if (searchSearchRequest.getScrollId() != null) {
			_getScrollResponse(searchSearchRequest);
		}
		else {
			searchResponse = _getSearchResponse(
				searchRequestBuilder.build(), searchSearchRequest);
		}

		SearchSearchResponse searchSearchResponse = new SearchSearchResponse();

		_searchSearchResponseAssembler.assemble(
			searchResponse, searchRequestString, searchSearchRequest,
			searchSearchResponse);

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"The search engine processed the request in ",
					searchSearchResponse.getExecutionTime(), " ms"));
		}

		return searchSearchResponse;
	}

	private ScrollResponse<JsonData> _getScrollResponse(
		SearchSearchRequest searchSearchRequest) {

		ElasticsearchClient elasticsearchClient =
			_elasticsearchClientResolver.getElasticsearchClient(
				searchSearchRequest.getConnectionId(),
				searchSearchRequest.isPreferLocalCluster());

		ScrollRequest.Builder scrollRequestBuilder =
			new ScrollRequest.Builder();

		if (searchSearchRequest.getScrollKeepAliveMinutes() > 0) {
			String scrollKeepAliveMinutes = String.valueOf(
				searchSearchRequest.getScrollKeepAliveMinutes());

			scrollRequestBuilder.scroll(
				Time.of(
					time -> time.time(
						scrollKeepAliveMinutes +
							TimeUnit.Minutes.jsonValue())));
		}

		scrollRequestBuilder.scrollId(searchSearchRequest.getScrollId());

		try {
			return elasticsearchClient.scroll(
				scrollRequestBuilder.build(), JsonData.class);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private SearchResponse _getSearchResponse(
		SearchRequest searchRequest, SearchSearchRequest searchSearchRequest) {

		ElasticsearchClient elasticsearchClient =
			_elasticsearchClientResolver.getElasticsearchClient(
				searchSearchRequest.getConnectionId(),
				searchSearchRequest.isPreferLocalCluster());

		try {
			return elasticsearchClient.search(searchRequest);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SearchSearchRequestExecutorImpl.class);

	@Reference
	private ElasticsearchClientResolver _elasticsearchClientResolver;

	@Reference
	private SearchSearchRequestAssembler _searchSearchRequestAssembler;

	@Reference
	private SearchSearchResponseAssembler _searchSearchResponseAssembler;

}