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

package com.liferay.portal.search.elasticsearch6.internal.search.engine.adapter.search;

import com.liferay.portal.search.elasticsearch6.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;

import org.elasticsearch.action.search.SearchAction;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = SearchSearchRequestExecutor.class)
public class SearchSearchRequestExecutorImpl
	implements SearchSearchRequestExecutor {

	@Override
	public SearchSearchResponse execute(
		SearchSearchRequest searchSearchRequest) {

		Client client = elasticsearchClientResolver.getClient();

		SearchRequestBuilder searchRequestBuilder =
			SearchAction.INSTANCE.newRequestBuilder(client);

		searchSearchRequestAssembler.assemble(
			searchRequestBuilder, searchSearchRequest);

		SearchResponse searchResponse = searchRequestBuilder.get();

		SearchSearchResponse searchSearchResponse = new SearchSearchResponse();

		searchSearchResponseAssembler.assemble(
			searchRequestBuilder, searchResponse, searchSearchRequest,
			searchSearchResponse);

		return searchSearchResponse;
	}

	@Reference
	protected ElasticsearchClientResolver elasticsearchClientResolver;

	@Reference
	protected SearchSearchRequestAssembler searchSearchRequestAssembler;

	@Reference
	protected SearchSearchResponseAssembler searchSearchResponseAssembler;

}