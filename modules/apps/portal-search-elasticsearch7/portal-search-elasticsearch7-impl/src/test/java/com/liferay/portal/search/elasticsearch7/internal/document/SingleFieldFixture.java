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

package com.liferay.portal.search.elasticsearch7.internal.document;

import com.liferay.portal.search.elasticsearch7.internal.connection.IndexName;
import com.liferay.portal.search.elasticsearch7.internal.query.QueryBuilderFactory;
import com.liferay.portal.search.elasticsearch7.internal.query.SearchAssert;

import java.io.IOException;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;

/**
 * @author André de Oliveira
 */
public class SingleFieldFixture {

	public SingleFieldFixture(
		RestHighLevelClient restHighLevelClient, IndexName indexName,
		String type) {

		_restHighLevelClient = restHighLevelClient;
		_type = type;

		_index = indexName.getName();
	}

	public void assertNoHits(String text) throws Exception {
		SearchAssert.assertNoHits(
			_restHighLevelClient, _field, _createQueryBuilder(text));
	}

	public void assertSearch(String text, String... expected) throws Exception {
		SearchAssert.assertSearch(
			_restHighLevelClient, _field, _createQueryBuilder(text), expected);
	}

	public void indexDocument(String value) {
		IndexRequest indexRequest = new IndexRequest(_index, _type);

		indexRequest.source(_field, value);

		try {
			_restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	public void setField(String field) {
		_field = field;
	}

	public void setQueryBuilderFactory(
		QueryBuilderFactory queryBuilderFactory) {

		_queryBuilderFactory = queryBuilderFactory;
	}

	private QueryBuilder _createQueryBuilder(String text) {
		return _queryBuilderFactory.create(_field, text);
	}

	private String _field;
	private final String _index;
	private QueryBuilderFactory _queryBuilderFactory;
	private final RestHighLevelClient _restHighLevelClient;
	private final String _type;

}