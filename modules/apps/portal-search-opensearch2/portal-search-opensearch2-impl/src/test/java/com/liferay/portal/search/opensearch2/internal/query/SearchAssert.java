/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.query;

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

import org.opensearch.client.json.JsonData;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch._types.query_dsl.FieldAndFormat;
import org.opensearch.client.opensearch._types.query_dsl.Query;
import org.opensearch.client.opensearch.core.SearchRequest;
import org.opensearch.client.opensearch.core.SearchResponse;
import org.opensearch.client.opensearch.core.search.Hit;
import org.opensearch.client.opensearch.core.search.HitsMetadata;

/**
 * @author André de Oliveira
 */
public class SearchAssert {

	public static void assertNoHits(
			OpenSearchClient openSearchClient, String field, Query query)
		throws Exception {

		assertSearch(openSearchClient, field, query, new String[0]);
	}

	public static void assertSearch(
			OpenSearchClient openSearchClient,
			SearchRequest.Builder searchRequestBuilder, String field,
			String... expectedValues)
		throws Exception {

		searchRequestBuilder.fields(
			FieldAndFormat.of(
				fieldAndFormat -> fieldAndFormat.field(StringPool.STAR)));

		SearchRequest searchRequest = searchRequestBuilder.build();

		assertSearch(
			() -> search(openSearchClient, searchRequest), field,
			expectedValues);
	}

	public static void assertSearch(
			OpenSearchClient openSearchClient, String field, Query query,
			String... expectedValues)
		throws Exception {

		assertSearch(
			() -> search(openSearchClient, query), field, expectedValues);
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
		OpenSearchClient openSearchClient, Query query) {

		SearchRequest.Builder searchRequestBuilder =
			new SearchRequest.Builder();

		searchRequestBuilder.fields(
			FieldAndFormat.of(
				fieldAndFormat -> fieldAndFormat.field(StringPool.STAR)));
		searchRequestBuilder.query(query);

		return search(openSearchClient, searchRequestBuilder.build());
	}

	protected static HitsMetadata<JsonData> search(
		OpenSearchClient openSearchClient, SearchRequest searchRequest) {

		try {
			SearchResponse<JsonData> searchResponse = openSearchClient.search(
				searchRequest, JsonData.class);

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