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

package com.liferay.portal.search.elasticsearch7.internal.query;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.search.test.util.IdempotentRetryAssert;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.document.DocumentField;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import org.junit.Assert;

/**
 * @author André de Oliveira
 */
public class SearchAssert {

	public static void assertNoHits(
			RestHighLevelClient restHighLevelClient, String field,
			QueryBuilder queryBuilder)
		throws Exception {

		assertSearch(restHighLevelClient, field, queryBuilder, new String[0]);
	}

	public static void assertSearch(
			RestHighLevelClient restHighLevelClient,
			SearchSourceBuilder searchSourceBuilder,
			SearchRequest searchRequest, String field, String... expectedValues)
		throws Exception {

		assertSearch(
			() -> search(
				restHighLevelClient, searchSourceBuilder, searchRequest),
			field, expectedValues);
	}

	public static void assertSearch(
			RestHighLevelClient restHighLevelClient, String field,
			QueryBuilder queryBuilder, String... expectedValues)
		throws Exception {

		assertSearch(
			() -> search(restHighLevelClient, queryBuilder), field,
			expectedValues);
	}

	protected static void assertSearch(
			Supplier<SearchHits> supplier, String field,
			String... expectedValues)
		throws Exception {

		IdempotentRetryAssert.retryAssert(
			10, TimeUnit.SECONDS,
			() -> Assert.assertEquals(
				_sort(Arrays.asList(expectedValues)),
				_sort(getValues(supplier.get(), field))));
	}

	protected static List<String> getValues(
		SearchHits searchHits, String field) {

		List<String> values = new ArrayList<>();

		for (SearchHit searchHit : searchHits.getHits()) {
			DocumentField documentField = searchHit.field(field);

			values.add(documentField.getValue());
		}

		return values;
	}

	protected static SearchHits search(
		RestHighLevelClient restHighLevelClient, QueryBuilder queryBuilder) {

		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

		searchSourceBuilder.query(queryBuilder);

		return search(
			restHighLevelClient, searchSourceBuilder, new SearchRequest());
	}

	protected static SearchHits search(
		RestHighLevelClient restHighLevelClient,
		SearchSourceBuilder searchSourceBuilder, SearchRequest searchRequest) {

		searchSourceBuilder.storedField(StringPool.STAR);

		searchRequest.source(searchSourceBuilder);

		try {
			SearchResponse searchResponse = restHighLevelClient.search(
				searchRequest, RequestOptions.DEFAULT);

			return searchResponse.getHits();
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private static String _sort(Collection<String> collection) {
		List<String> list = new ArrayList<>(collection);

		Collections.sort(list);

		return list.toString();
	}

}