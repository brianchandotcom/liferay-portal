/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.search.engine.adapter.search;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.opensearch2.internal.connection.OpenSearchConnectionManager;
import com.liferay.portal.search.opensearch2.internal.util.JsonpUtil;
import com.liferay.portal.search.opensearch2.internal.util.SetterUtil;

import java.io.IOException;

import org.opensearch.client.json.JsonData;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch._types.Time;
import org.opensearch.client.opensearch._types.TimeUnit;
import org.opensearch.client.opensearch.core.ScrollRequest;
import org.opensearch.client.opensearch.core.SearchRequest;
import org.opensearch.client.opensearch.core.SearchResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 * @author Petteri Karttunen
 */
@Component(service = SearchSearchRequestExecutor.class)
public class SearchSearchRequestExecutorImpl
	implements SearchSearchRequestExecutor {

	@Override
	public SearchSearchResponse execute(
		SearchSearchRequest searchSearchRequest) {

		SearchRequest.Builder searchRequestBuilder =
			new SearchRequest.Builder();

		SetterUtil.setNotNullBoolean(
			searchRequestBuilder::requestCache,
			searchSearchRequest.isRequestCache());

		if (searchSearchRequest.getSearchAfter() != null) {
			searchSearchRequest.setStart(null);
		}

		_searchSearchRequestAssembler.assemble(
			searchRequestBuilder, searchSearchRequest);

		SearchRequest searchRequest = searchRequestBuilder.build();

		if (_log.isTraceEnabled()) {
			_log.trace("Search query: " + JsonpUtil.toString(searchRequest));
		}

		SearchResponse<JsonData> searchResponse = null;

		if (searchSearchRequest.getScrollId() != null) {
			searchResponse = _getScrollSearchResponse(searchSearchRequest);
		}
		else {
			searchResponse = _getSearchResponse(
				searchRequest, searchSearchRequest);
		}

		SearchSearchResponse searchSearchResponse = new SearchSearchResponse();

		_searchSearchResponseAssembler.assemble(
			searchRequest, searchResponse, searchSearchRequest,
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

	private SearchResponse<JsonData> _getScrollSearchResponse(
		SearchSearchRequest searchSearchRequest) {

		OpenSearchClient openSearchClient =
			_openSearchConnectionManager.getOpenSearchClient(
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
			return openSearchClient.scroll(
				scrollRequestBuilder.build(), JsonData.class);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private SearchResponse<JsonData> _getSearchResponse(
		SearchRequest searchRequest, SearchSearchRequest searchSearchRequest) {

		OpenSearchClient openSearchClient =
			_openSearchConnectionManager.getOpenSearchClient(
				searchSearchRequest.getConnectionId(),
				searchSearchRequest.isPreferLocalCluster());

		try {
			return openSearchClient.search(searchRequest, JsonData.class);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SearchSearchRequestExecutorImpl.class);

	@Reference
	private OpenSearchConnectionManager _openSearchConnectionManager;

	@Reference
	private SearchSearchRequestAssembler _searchSearchRequestAssembler;

	@Reference
	private SearchSearchResponseAssembler _searchSearchResponseAssembler;

}