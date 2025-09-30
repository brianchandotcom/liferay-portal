/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		searchSourceBuilder.trackTotalHits(true);

		SearchResponse searchResponse = getSearchResponse(
			searchRequest, countSearchRequest);

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

	protected SearchResponse getSearchResponse(
		SearchRequest searchRequest, CountSearchRequest countSearchRequest) {

		RestHighLevelClient restHighLevelClient =
			_elasticsearchClientResolver.getRestHighLevelClient(
				countSearchRequest.getConnectionId(),
				countSearchRequest.isPreferLocalCluster());

		try {
			return restHighLevelClient.search(
				searchRequest, RequestOptions.DEFAULT);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CountSearchRequestExecutorImpl.class);

	@Reference
	private CommonSearchResponseAssembler _commonSearchResponseAssembler;

	@Reference
	private CommonSearchSourceBuilderAssembler
		_commonSearchSourceBuilderAssembler;

	@Reference
	private ElasticsearchClientResolver _elasticsearchClientResolver;

}