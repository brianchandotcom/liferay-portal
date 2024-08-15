/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.search.engine.adapter.search;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.search.engine.adapter.search.MultisearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.MultisearchSearchResponse;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.opensearch2.internal.connection.OpenSearchConnectionManager;
import com.liferay.portal.search.opensearch2.internal.util.JsonpUtil;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import org.opensearch.client.json.JsonData;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch.core.MsearchRequest;
import org.opensearch.client.opensearch.core.MsearchResponse;
import org.opensearch.client.opensearch.core.SearchRequest;
import org.opensearch.client.opensearch.core.msearch.MultiSearchItem;
import org.opensearch.client.opensearch.core.msearch.MultiSearchResponseItem;
import org.opensearch.client.opensearch.core.msearch.MultisearchBody;
import org.opensearch.client.opensearch.core.msearch.MultisearchHeader;
import org.opensearch.client.opensearch.core.msearch.RequestItem;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 * @author Petteri Karttunen
 */
@Component(service = MultisearchSearchRequestExecutor.class)
public class MultisearchSearchRequestExecutorImpl
	implements MultisearchSearchRequestExecutor {

	@Override
	public MultisearchSearchResponse execute(
		MultisearchSearchRequest multisearchSearchRequest) {

		MsearchRequest.Builder msearchRequestBuilder =
			new MsearchRequest.Builder();

		List<SearchSearchRequest> searchSearchRequests =
			multisearchSearchRequest.getSearchSearchRequests();

		List<SearchRequestHolder> searchRequestHolders = new ArrayList<>(
			searchSearchRequests.size());

		searchSearchRequests.forEach(
			searchSearchRequest -> {
				SearchRequest.Builder searchRequestBuilder =
					new SearchRequest.Builder();

				searchRequestBuilder.index(
					ListUtil.fromArray(searchSearchRequest.getIndexNames()));

				_searchSearchRequestAssembler.assemble(
					searchRequestBuilder, searchSearchRequest);

				SearchRequest searchRequest = searchRequestBuilder.build();

				searchRequestHolders.add(
					new SearchRequestHolder(
						searchRequest, searchSearchRequest));

				msearchRequestBuilder.searches(_toRequestItem(searchRequest));
			});

		return _createMultisearchSearchResponse(
			_getMsearchResponse(
				msearchRequestBuilder.build(), multisearchSearchRequest),
			searchRequestHolders);
	}

	private MultisearchSearchResponse _createMultisearchSearchResponse(
		MsearchResponse<JsonData> msearchResponse,
		List<SearchRequestHolder> searchRequestHolders) {

		MultisearchSearchResponse multisearchSearchResponse =
			new MultisearchSearchResponse();

		List<MultiSearchResponseItem<JsonData>> multiSearchResponseItems =
			msearchResponse.responses();

		for (int i = 0; i < multiSearchResponseItems.size(); i++) {
			MultiSearchResponseItem<JsonData> multiSearchResponseItem =
				multiSearchResponseItems.get(i);

			MultiSearchItem<JsonData> multiSearchItem =
				multiSearchResponseItem.result();

			SearchSearchResponse searchSearchResponse =
				new SearchSearchResponse();

			SearchRequestHolder searchRequestHolder = searchRequestHolders.get(
				i);

			SearchSearchRequest searchSearchRequest =
				searchRequestHolder.getSearchSearchRequest();

			_searchSearchResponseAssembler.assemble(
				searchRequestHolder.getSearchRequest(), multiSearchItem,
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
					JsonpUtil.toString(multiSearchItem));
			}

			multisearchSearchResponse.addSearchResponse(searchSearchResponse);
		}

		return multisearchSearchResponse;
	}

	private MsearchResponse<JsonData> _getMsearchResponse(
		MsearchRequest msearchRequest,
		MultisearchSearchRequest multisearchSearchRequest) {

		OpenSearchClient openSearchClient =
			_openSearchConnectionManager.getOpenSearchClient(
				multisearchSearchRequest.getConnectionId(),
				multisearchSearchRequest.isPreferLocalCluster());

		try {
			return openSearchClient.msearch(msearchRequest, JsonData.class);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private RequestItem _toRequestItem(SearchRequest searchRequest) {
		MultisearchBody.Builder builder = new MultisearchBody.Builder();

		builder.aggregations(searchRequest.aggregations());
		builder.from(searchRequest.from());
		builder.highlight(searchRequest.highlight());
		builder.minScore(searchRequest.minScore());
		builder.postFilter(searchRequest.postFilter());
		builder.query(searchRequest.query());
		builder.searchAfter(searchRequest.searchAfter());
		builder.size(searchRequest.size());
		builder.sort(searchRequest.sort());
		builder.source(searchRequest.source());
		builder.suggest(searchRequest.suggest());
		builder.trackScores(searchRequest.trackScores());
		builder.trackTotalHits(searchRequest.trackTotalHits());

		return RequestItem.of(
			requestItem -> requestItem.body(
				builder.build()
			).header(
				MultisearchHeader.of(multisearchHeader -> multisearchHeader)
			));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MultisearchSearchRequestExecutorImpl.class);

	@Reference
	private OpenSearchConnectionManager _openSearchConnectionManager;

	@Reference
	private SearchSearchRequestAssembler _searchSearchRequestAssembler;

	@Reference
	private SearchSearchResponseAssembler _searchSearchResponseAssembler;

	private class SearchRequestHolder {

		public SearchRequestHolder(
			SearchRequest searchRequest,
			SearchSearchRequest searchSearchRequest) {

			_searchRequest = searchRequest;
			_searchSearchRequest = searchSearchRequest;
		}

		public SearchRequest getSearchRequest() {
			return _searchRequest;
		}

		public SearchSearchRequest getSearchSearchRequest() {
			return _searchSearchRequest;
		}

		private final SearchRequest _searchRequest;
		private final SearchSearchRequest _searchSearchRequest;

	}

}