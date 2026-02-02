/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.document;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.json.JsonData;

import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.search.elasticsearch8.internal.connection.IndexName;
import com.liferay.portal.search.elasticsearch8.internal.query.QueryFactory;
import com.liferay.portal.search.elasticsearch8.internal.query.SearchAssert;

import java.io.IOException;

/**
 * @author André de Oliveira
 */
public class SingleFieldFixture {

	public SingleFieldFixture(
		ElasticsearchClient elasticsearchClient, IndexName indexName) {

		_elasticsearchClient = elasticsearchClient;

		_index = indexName.getName();
	}

	public void assertNoHits(String text) throws Exception {
		SearchAssert.assertNoHits(
			_elasticsearchClient, _field, _createQuery(text));
	}

	public void assertSearch(String text, String... expected) throws Exception {
		SearchAssert.assertSearch(
			_elasticsearchClient, _field, _createQuery(text), expected);
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
			_elasticsearchClient.index(indexRequestBuilder.build());
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	public void setField(String field) {
		_field = field;
	}

	public void setQueryFactory(QueryFactory queryFactory) {
		_queryFactory = queryFactory;
	}

	private Query _createQuery(String text) {
		return _queryFactory.create(_field, text);
	}

	private final ElasticsearchClient _elasticsearchClient;
	private String _field;
	private final String _index;
	private QueryFactory _queryFactory;

}