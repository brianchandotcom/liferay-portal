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
import com.liferay.portal.search.engine.adapter.search.MultisearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.MultisearchSearchResponse;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.elasticsearch.action.search.MultiSearchRequest;
import org.elasticsearch.action.search.MultiSearchResponse;
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
@Component(service = MultisearchSearchRequestExecutor.class)
public class MultisearchSearchRequestExecutorImpl
	implements MultisearchSearchRequestExecutor {

	@Override
	public MultisearchSearchResponse execute(
		MultisearchSearchRequest multisearchSearchRequest) {

		MultiSearchRequest multiSearchRequest = new MultiSearchRequest();

		List<SearchSearchRequest> searchSearchRequests =
			multisearchSearchRequest.getSearchSearchRequests();

		List<SearchRequestHolder> searchRequestHolders = new ArrayList<>(
			searchSearchRequests.size());

		searchSearchRequests.forEach(
			searchSearchRequest -> {
				SearchRequest searchRequest = new SearchRequest(
					searchSearchRequest.getIndexNames());

				SearchSourceBuilder searchSourceBuilder =
					new SearchSourceBuilder();

				_searchSearchRequestAssembler.assemble(
					searchSourceBuilder, searchSearchRequest, searchRequest);

				SearchRequestHolder searchRequestHolder =
					new SearchRequestHolder(
						searchSearchRequest, searchSourceBuilder);

				searchRequestHolders.add(searchRequestHolder);

				multiSearchRequest.add(searchRequest);
			});

		MultiSearchResponse multiSearchResponse = _getMultiSearchResponse(
			multiSearchRequest, multisearchSearchRequest);

		Iterator<MultiSearchResponse.Item> iterator =
			multiSearchResponse.iterator();

		MultisearchSearchResponse multisearchSearchResponse =
			new MultisearchSearchResponse();

		int counter = 0;

		while (iterator.hasNext()) {
			MultiSearchResponse.Item multiSearchResponseItem = iterator.next();

			SearchResponse searchResponse =
				multiSearchResponseItem.getResponse();

			SearchSearchResponse searchSearchResponse =
				new SearchSearchResponse();

			SearchRequestHolder searchRequestHolder = searchRequestHolders.get(
				counter);

			SearchSearchRequest searchSearchRequest =
				searchRequestHolder.getSearchSearchRequest();

			_searchSearchResponseAssembler.assemble(
				searchRequestHolder.getSearchSourceBuilder(), searchResponse,
				searchSearchRequest, searchSearchResponse);

			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"The search engine processed ",
						searchSearchResponse.getSearchRequestString(), " in ",
						searchSearchResponse.getExecutionTime(), " ms"));
			}

			if (searchSearchRequest.isIncludeResponseString()) {
				searchSearchResponse.setSearchResponseString(
					searchResponse.toString());
			}

			counter++;
		}

		return multisearchSearchResponse;
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

	private MultiSearchResponse _getMultiSearchResponse(
		MultiSearchRequest multiSearchRequest,
		MultisearchSearchRequest multisearchSearchRequest) {

		RestHighLevelClient restHighLevelClient =
			_elasticsearchClientResolver.getRestHighLevelClient(
				multisearchSearchRequest.getConnectionId(),
				multisearchSearchRequest.isPreferLocalCluster());

		try {
			return restHighLevelClient.msearch(
				multiSearchRequest, RequestOptions.DEFAULT);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MultisearchSearchRequestExecutorImpl.class);

	private ElasticsearchClientResolver _elasticsearchClientResolver;
	private SearchSearchRequestAssembler _searchSearchRequestAssembler;
	private SearchSearchResponseAssembler _searchSearchResponseAssembler;

	private class SearchRequestHolder {

		public SearchRequestHolder(
			SearchSearchRequest searchSearchRequest,
			SearchSourceBuilder searchSourceBuilder) {

			_searchSearchRequest = searchSearchRequest;
			_searchSourceBuilder = searchSourceBuilder;
		}

		public SearchSearchRequest getSearchSearchRequest() {
			return _searchSearchRequest;
		}

		public SearchSourceBuilder getSearchSourceBuilder() {
			return _searchSourceBuilder;
		}

		private final SearchSearchRequest _searchSearchRequest;
		private final SearchSourceBuilder _searchSourceBuilder;

	}

}