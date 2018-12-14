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
import com.liferay.portal.search.engine.adapter.search.CountSearchRequest;
import com.liferay.portal.search.engine.adapter.search.CountSearchResponse;

import java.io.IOException;

import org.apache.lucene.search.TotalHits;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(service = CountSearchRequestExecutor.class)
public class CountSearchRequestExecutorImpl
	implements CountSearchRequestExecutor {

	@Override
	public CountSearchResponse execute(CountSearchRequest countSearchRequest) {
		SearchRequest searchRequest = new SearchRequest(
			countSearchRequest.getIndexNames());

		if (countSearchRequest.isRequestCache()) {
			searchRequest.requestCache(countSearchRequest.isRequestCache());
		}

		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

		_commonSearchSourceBuilderAssembler.assemble(
			searchSourceBuilder, countSearchRequest, searchRequest);

		searchSourceBuilder.size(0);
		searchSourceBuilder.trackScores(false);

		SearchResponse searchResponse = getSearchResponse(searchRequest);

		SearchHits searchHits = searchResponse.getHits();

		CountSearchResponse countSearchResponse = new CountSearchResponse();

		TotalHits totalHits = searchHits.getTotalHits();

		countSearchResponse.setCount(totalHits.value);

		_commonSearchResponseAssembler.assemble(
			searchSourceBuilder, searchResponse, countSearchRequest,
			countSearchResponse);

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"The search engine processed ",
					countSearchResponse.getSearchRequestString(), " in ",
					countSearchResponse.getExecutionTime(), " ms"));
		}

		return countSearchResponse;
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
	protected void setCommonSearchResponseAssembler(
		CommonSearchResponseAssembler commonSearchResponseAssembler) {

		_commonSearchResponseAssembler = commonSearchResponseAssembler;
	}

	@Reference(unbind = "-")
	protected void setCommonSearchSourceBuilderAssembler(
		CommonSearchSourceBuilderAssembler commonSearchSourceBuilderAssembler) {

		_commonSearchSourceBuilderAssembler =
			commonSearchSourceBuilderAssembler;
	}

	@Reference(unbind = "-")
	protected void setElasticsearchClientResolver(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CountSearchRequestExecutorImpl.class);

	private CommonSearchResponseAssembler _commonSearchResponseAssembler;
	private CommonSearchSourceBuilderAssembler
		_commonSearchSourceBuilderAssembler;
	private ElasticsearchClientResolver _elasticsearchClientResolver;

}