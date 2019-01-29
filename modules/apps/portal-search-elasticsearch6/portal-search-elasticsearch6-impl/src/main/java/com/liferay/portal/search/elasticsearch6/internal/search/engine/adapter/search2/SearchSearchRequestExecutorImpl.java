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

package com.liferay.portal.search.elasticsearch6.internal.search.engine.adapter.search2;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.search.elasticsearch6.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.engine.adapter.search2.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search2.SearchSearchResponse;

import org.elasticsearch.action.search.SearchAction;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;

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

		Client client = _elasticsearchClientResolver.getClient();

		SearchRequestBuilder searchRequestBuilder =
			SearchAction.INSTANCE.newRequestBuilder(client);

		_searchSearchRequestAssembler.assemble(
			searchRequestBuilder, searchSearchRequest);

		SearchResponse searchResponse = searchRequestBuilder.get();

		SearchSearchResponse searchSearchResponse = new SearchSearchResponse();

		_searchSearchResponseAssembler.assemble(
			searchRequestBuilder, searchResponse, searchSearchRequest,
			searchSearchResponse);

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"The search engine processed ",
					searchSearchResponse.getSearchRequestString(), " in ",
					searchSearchResponse.getExecutionTime() + " ms"));
		}

		return searchSearchResponse;
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

	private static final Log _log = LogFactoryUtil.getLog(
		SearchSearchRequestExecutorImpl.class);

	private ElasticsearchClientResolver _elasticsearchClientResolver;
	private SearchSearchRequestAssembler _searchSearchRequestAssembler;
	private SearchSearchResponseAssembler _searchSearchResponseAssembler;

}