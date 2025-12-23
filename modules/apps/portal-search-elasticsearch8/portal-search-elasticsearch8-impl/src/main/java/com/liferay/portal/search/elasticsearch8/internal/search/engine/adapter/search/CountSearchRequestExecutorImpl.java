/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.search;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.HitsMetadata;
import co.elastic.clients.elasticsearch.core.search.TotalHits;
import co.elastic.clients.elasticsearch.core.search.TrackHits;
import co.elastic.clients.json.JsonData;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.search.elasticsearch8.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.engine.adapter.search.CountSearchRequest;
import com.liferay.portal.search.engine.adapter.search.CountSearchResponse;

import java.io.IOException;

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
		SearchRequest.Builder builder = new SearchRequest.Builder();

		_commonSearchRequestBuilderAssembler.assemble(
			countSearchRequest, builder);

		builder.requestCache(countSearchRequest.isRequestCache());
		builder.size(0);
		builder.trackScores(false);
		builder.trackTotalHits(
			TrackHits.of(trackHits -> trackHits.enabled(true)));

		String indexNames = ArrayUtil.toString(
			countSearchRequest.getIndexNames(), "");

		if (_log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					"Stack trace for [", indexNames, "]: ",
					DebugStringsUtil.getStackTraceString()));
		}

		String searchRequestString = DebugStringsUtil.getSearchRequestString(
			builder);

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Search request string for [", indexNames, "]: ",
					searchRequestString));
		}

		CountSearchResponse countSearchResponse = new CountSearchResponse();

		SearchRequest searchRequest = builder.build();

		SearchResponse<JsonData> searchResponse = getSearchResponse(
			countSearchRequest, searchRequest);

		HitsMetadata<JsonData> hitsMetadata = searchResponse.hits();

		TotalHits totalHits = hitsMetadata.total();

		countSearchResponse.setCount(totalHits.value());

		_commonSearchResponseAssembler.assemble(
			countSearchRequest, countSearchResponse, searchResponse,
			searchRequestString);

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"The search engine processed the request in ",
					countSearchResponse.getExecutionTime(), " ms"));
		}

		return countSearchResponse;
	}

	protected SearchResponse<JsonData> getSearchResponse(
		CountSearchRequest countSearchRequest, SearchRequest searchRequest) {

		ElasticsearchClient elasticsearchClient =
			_elasticsearchClientResolver.getElasticsearchClient(
				countSearchRequest.getConnectionId(),
				countSearchRequest.isPreferLocalCluster());

		try {
			return elasticsearchClient.search(searchRequest, JsonData.class);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CountSearchRequestExecutorImpl.class);

	@Reference
	private CommonSearchRequestBuilderAssembler
		_commonSearchRequestBuilderAssembler;

	@Reference
	private CommonSearchResponseAssembler _commonSearchResponseAssembler;

	@Reference
	private ElasticsearchClientResolver _elasticsearchClientResolver;

}