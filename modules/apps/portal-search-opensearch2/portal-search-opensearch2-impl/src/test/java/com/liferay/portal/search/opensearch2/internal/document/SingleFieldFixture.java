/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.document;

import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.search.opensearch2.internal.connection.IndexName;
import com.liferay.portal.search.opensearch2.internal.query.QueryFactory;
import com.liferay.portal.search.opensearch2.internal.query.SearchAssert;

import java.io.IOException;

import org.opensearch.client.json.JsonData;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch._types.query_dsl.Query;
import org.opensearch.client.opensearch.core.IndexRequest;

/**
 * @author André de Oliveira
 */
public class SingleFieldFixture {

	public SingleFieldFixture(
		OpenSearchClient openSearchClient, IndexName indexName) {

		_openSearchClient = openSearchClient;

		_index = indexName.getName();
	}

	public void assertNoHits(String text) throws Exception {
		SearchAssert.assertNoHits(
			_openSearchClient, _field, _createQuery(text));
	}

	public void assertSearch(String text, String... expected) throws Exception {
		SearchAssert.assertSearch(
			_openSearchClient, _field, _createQuery(text), expected);
	}

	public void indexDocument(Object value) {
		IndexRequest.Builder<JsonData> indexRequestBuilder =
			new IndexRequest.Builder<>();

		indexRequestBuilder.document(
			JsonData.of(
				HashMapBuilder.put(
					_field, value
				).build()));
		indexRequestBuilder.index(_index);

		try {
			_openSearchClient.index(indexRequestBuilder.build());
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	public void setField(String field) {
		_field = field;
	}

	public void setQueryBuilderFactory(QueryFactory queryFactory) {
		_queryFactory = queryFactory;
	}

	private Query _createQuery(String text) {
		return _queryFactory.create(_field, text);
	}

	private String _field;
	private final String _index;
	private final OpenSearchClient _openSearchClient;
	private QueryFactory _queryFactory;

}