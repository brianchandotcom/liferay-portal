/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.search;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.elasticsearch7.internal.util.JSONUtil;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;

import java.io.IOException;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.builder.SearchSourceBuilder;

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

		SearchRequest searchRequest = new SearchRequest(
			searchSearchRequest.getIndexNames());

		if (searchSearchRequest.isRequestCache()) {
			searchRequest.requestCache(searchSearchRequest.isRequestCache());
		}

		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

		_searchSearchRequestAssembler.assemble(
			searchSourceBuilder, searchSearchRequest, searchRequest);

		if (_log.isTraceEnabled()) {
			String prettyPrintedRequestString = _getPrettyPrintedRequestString(
				searchSourceBuilder);

			_log.trace("Search query: " + prettyPrintedRequestString);
		}

		SearchResponse searchResponse = getSearchResponse(searchRequest);

		SearchSearchResponse searchSearchResponse = new SearchSearchResponse();

		_searchSearchResponseAssembler.assemble(
			searchSourceBuilder, searchResponse, searchSearchRequest,
			searchSearchResponse);

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"The search engine processed ",
					searchSearchResponse.getSearchRequestString(), " in ",
					searchSearchResponse.getExecutionTime(), " ms"));
		}

		return searchSearchResponse;
	}

	protected SearchResponse getSearchResponse(SearchRequest searchRequest) {
		RestHighLevelClient restHighLevelClient =
			_elasticsearchClientResolver.getRestHighLevelClient();

		try {
			return restHighLevelClient.search(
				searchRequest, RequestOptions.DEFAULT);
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	@Reference(unbind = "-")
	protected void setElasticsearchClientResolver(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	@Reference(unbind = "-")
	protected void setSearchSearchRequestAssembler(
		SearchSearchRequestAssembler searchSearchRequestAssembler) {

		_searchSearchRequestAssembler = searchSearchRequestAssembler;
	}

	@Reference(unbind = "-")
	protected void setSearchSearchResponseAssembler(
		SearchSearchResponseAssembler searchSearchResponseAssembler) {

		_searchSearchResponseAssembler = searchSearchResponseAssembler;
	}

	private String _getPrettyPrintedRequestString(
		SearchSourceBuilder searchSourceBuilder) {

		try {
			return JSONUtil.getPrettyPrintedJSONString(searchSourceBuilder);
		}
		catch (Exception e) {
			return e.getMessage();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SearchSearchRequestExecutorImpl.class);

	private ElasticsearchClientResolver _elasticsearchClientResolver;
	private SearchSearchRequestAssembler _searchSearchRequestAssembler;
	private SearchSearchResponseAssembler _searchSearchResponseAssembler;

}