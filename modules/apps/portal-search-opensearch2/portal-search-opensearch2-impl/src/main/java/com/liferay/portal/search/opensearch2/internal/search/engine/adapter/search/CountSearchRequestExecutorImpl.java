/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.search.engine.adapter.search;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.search.engine.adapter.search.CountSearchRequest;
import com.liferay.portal.search.engine.adapter.search.CountSearchResponse;
import com.liferay.portal.search.opensearch2.internal.connection.OpenSearchConnectionManager;

import java.io.IOException;

import org.opensearch.client.json.JsonData;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch.core.SearchRequest;
import org.opensearch.client.opensearch.core.SearchResponse;
import org.opensearch.client.opensearch.core.search.HitsMetadata;
import org.opensearch.client.opensearch.core.search.TotalHits;
import org.opensearch.client.opensearch.core.search.TrackHits;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 * @author Petteri Karttunen
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

		CountSearchResponse countSearchResponse = new CountSearchResponse();

		SearchRequest searchRequest = builder.build();

		SearchResponse<JsonData> searchResponse = getSearchResponse(
			countSearchRequest, searchRequest);

		HitsMetadata<JsonData> hitsMetadata = searchResponse.hits();

		TotalHits totalHits = hitsMetadata.total();

		countSearchResponse.setCount(totalHits.value());

		_commonSearchResponseAssembler.assemble(
			countSearchRequest, countSearchResponse, searchRequest,
			searchResponse);

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"The search engine processed ",
					countSearchResponse.getSearchRequestString(), " in ",
					countSearchResponse.getExecutionTime(), " ms"));
		}

		return countSearchResponse;
	}

	protected SearchResponse<JsonData> getSearchResponse(
		CountSearchRequest countSearchRequest, SearchRequest searchRequest) {

		OpenSearchClient openSearchClient =
			_openSearchConnectionManager.getOpenSearchClient(
				countSearchRequest.getConnectionId(),
				countSearchRequest.isPreferLocalCluster());

		try {
			return openSearchClient.search(searchRequest, JsonData.class);
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
	private OpenSearchConnectionManager _openSearchConnectionManager;

}