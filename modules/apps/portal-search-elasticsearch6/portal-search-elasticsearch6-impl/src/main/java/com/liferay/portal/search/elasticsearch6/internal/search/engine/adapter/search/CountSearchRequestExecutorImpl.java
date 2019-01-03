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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.search.elasticsearch6.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.engine.adapter.search.CountSearchRequest;
import com.liferay.portal.search.engine.adapter.search.CountSearchResponse;

import org.elasticsearch.action.search.SearchAction;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.search.SearchHits;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = CountSearchRequestExecutor.class)
public class CountSearchRequestExecutorImpl
	implements CountSearchRequestExecutor {

	@Override
	public CountSearchResponse execute(CountSearchRequest countSearchRequest) {
		Client client = elasticsearchClientResolver.getClient();

		SearchRequestBuilder searchRequestBuilder =
			SearchAction.INSTANCE.newRequestBuilder(client);

		commonSearchRequestBuilderAssembler.assemble(
			searchRequestBuilder, countSearchRequest);

		searchRequestBuilder.setSize(0);
		searchRequestBuilder.setTrackScores(false);

		SearchResponse searchResponse = searchRequestBuilder.get();

		SearchHits searchHits = searchResponse.getHits();

		CountSearchResponse countSearchResponse = new CountSearchResponse();

		countSearchResponse.setCount(searchHits.totalHits);

		commonSearchResponseAssembler.assemble(
			searchRequestBuilder, searchResponse, countSearchRequest,
			countSearchResponse);

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"The search engine processed ",
					countSearchResponse.getSearchRequestString(), " in ",
					countSearchResponse.getExecutionTime() + " ms"));
		}

		return countSearchResponse;
	}

	@Reference
	protected CommonSearchRequestBuilderAssembler
		commonSearchRequestBuilderAssembler;

	@Reference
	protected CommonSearchResponseAssembler commonSearchResponseAssembler;

	@Reference
	protected ElasticsearchClientResolver elasticsearchClientResolver;

	private static final Log _log = LogFactoryUtil.getLog(
		CountSearchRequestExecutorImpl.class);

}