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

package com.liferay.portal.search.elasticsearch6.internal.document;

import com.liferay.portal.search.elasticsearch6.internal.connection.IndexName;
import com.liferay.portal.search.elasticsearch6.internal.search.engine.adapter.search.CommonSearchRequestBuilderAssembler;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.test.util.IdempotentRetryAssert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import org.junit.Assert;

/**
 * @author Wade Cao
 */
public class MultipleFieldFixture {

	public MultipleFieldFixture(
		Client client, IndexName indexName, String type) {

		_client = client;
		_type = type;

		_index = indexName.getName();
	}

	public void assertSearch(
			SearchRequestBuilder searchRequestBuilder,
			SearchSearchRequest searchSearchRequest, String field,
			String... expected)
		throws Exception {

		IdempotentRetryAssert.retryAssert(
			10, TimeUnit.SECONDS,
			new Callable<Void>() {

				@Override
				public Void call() throws Exception {
					Assert.assertEquals(
						sort(Arrays.asList(expected)),
						sort(
							getValues(
								searchRequestBuilder, searchSearchRequest,
								field)));

					return null;
				}

			});
	}

	public void index(Map<String, Object> map) {
		IndexRequestBuilder indexRequestBuilder = _client.prepareIndex(
			_index, _type);

		indexRequestBuilder.setSource(map);

		indexRequestBuilder.get();
	}

	public void setCommonSearchRequestBuilderAssembler(
		CommonSearchRequestBuilderAssembler
			commonSearchRequestBuilderAssembler) {

		_commonSearchRequestBuilderAssembler =
			commonSearchRequestBuilderAssembler;
	}

	protected static String sort(Collection<String> collection) {
		List<String> list = new ArrayList<>(collection);

		Collections.sort(list);

		return list.toString();
	}

	protected List<String> getValues(
		SearchRequestBuilder searchRequestBuilder,
		SearchSearchRequest searchSearchRequest, String field) {

		_commonSearchRequestBuilderAssembler.assemble(
			searchRequestBuilder, searchSearchRequest);

		SearchResponse searchResponse = searchRequestBuilder.get();

		SearchHits searchHits = searchResponse.getHits();

		List<String> values = new ArrayList<>();

		for (SearchHit searchHit : searchHits.getHits()) {
			Map<String, Object> map = searchHit.getSourceAsMap();

			String value = (String)map.get(field);

			values.add(value);
		}

		return values;
	}

	private final Client _client;
	private CommonSearchRequestBuilderAssembler
		_commonSearchRequestBuilderAssembler;
	private final String _index;
	private final String _type;

}