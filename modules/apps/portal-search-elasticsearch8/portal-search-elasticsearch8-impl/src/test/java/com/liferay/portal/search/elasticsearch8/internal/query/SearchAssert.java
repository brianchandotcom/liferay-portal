/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.query;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.FieldAndFormat;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.HitsMetadata;
import co.elastic.clients.json.JsonData;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.search.test.util.IdempotentRetryAssert;

import jakarta.json.JsonArray;
import jakarta.json.JsonString;
import jakarta.json.JsonValue;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import org.junit.Assert;

/**
 * @author André de Oliveira
 */
public class SearchAssert {

	public static void assertNoHits(
			ElasticsearchClient elasticsearchClient, String field, Query query)
		throws Exception {

		assertSearch(elasticsearchClient, field, query, new String[0]);
	}

	public static void assertSearch(
			ElasticsearchClient elasticsearchClient,
			SearchRequest.Builder searchRequestBuilder, String field,
			String... expectedValues)
		throws Exception {

		searchRequestBuilder.fields(
			FieldAndFormat.of(
				fieldAndFormat -> fieldAndFormat.field(StringPool.STAR)));

		SearchRequest searchRequest = searchRequestBuilder.build();

		assertSearch(
			() -> search(elasticsearchClient, searchRequest), field,
			expectedValues);
	}

	public static void assertSearch(
			ElasticsearchClient elasticsearchClient, String field, Query query,
			String... expectedValues)
		throws Exception {

		assertSearch(
			() -> search(elasticsearchClient, query), field, expectedValues);
	}

	protected static void assertSearch(
			Supplier<HitsMetadata<JsonData>> supplier, String field,
			String... expectedValues)
		throws Exception {

		IdempotentRetryAssert.retryAssert(
			10, TimeUnit.SECONDS,
			() -> Assert.assertEquals(
				_sort(Arrays.asList(expectedValues)),
				_sort(getValues(supplier.get(), field))));
	}

	protected static List<String> getValues(
		HitsMetadata<JsonData> hitsMetadata, String field) {

		List<String> values = new ArrayList<>();

		for (Hit<JsonData> hit : hitsMetadata.hits()) {
			Map<String, JsonData> fields = hit.fields();

			values.add(_toStringValue(fields.get(field)));
		}

		return values;
	}

	protected static HitsMetadata<JsonData> search(
		ElasticsearchClient elasticsearchClient, Query query) {

		SearchRequest.Builder searchRequestBuilder =
			new SearchRequest.Builder();

		searchRequestBuilder.fields(
			FieldAndFormat.of(
				fieldAndFormat -> fieldAndFormat.field(StringPool.STAR)));
		searchRequestBuilder.query(query);

		return search(elasticsearchClient, searchRequestBuilder.build());
	}

	protected static HitsMetadata<JsonData> search(
		ElasticsearchClient elasticsearchClient, SearchRequest searchRequest) {

		try {
			SearchResponse<JsonData> searchResponse =
				elasticsearchClient.search(searchRequest, JsonData.class);

			return searchResponse.hits();
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

	private static String _toStringValue(JsonData jsonData) {
		JsonValue jsonValue = jsonData.toJson();

		JsonValue.ValueType valueType = jsonValue.getValueType();

		if (valueType == JsonValue.ValueType.STRING) {
			JsonString jsonString = (JsonString)jsonValue;

			return jsonString.getString();
		}
		else if (valueType == JsonValue.ValueType.ARRAY) {
			JsonArray jsonArray = jsonValue.asJsonArray();

			return jsonArray.getString(0);
		}

		return jsonValue.toString();
	}

}