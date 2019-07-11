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

import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilder;

/**
 * @author André de Oliveira
 */
public class SingleFieldFixture {

	public SingleFieldFixture(Client client, IndexName indexName, String type) {
		_client = client;
		_type = type;

		_index = indexName.getName();
	}

	public void assertNoHits(String text) throws Exception {
		SearchAssert.assertNoHits(_client, _field, _createQueryBuilder(text));
	}

	public void assertSearch(String text, String... expected) throws Exception {
		SearchAssert.assertSearch(
			_client, _field, _createQueryBuilder(text), expected);
	}

	public void indexDocument(String value) {
		IndexRequestBuilder indexRequestBuilder = _client.prepareIndex(
			_index, _type);

		indexRequestBuilder.setSource(_field, value);

		indexRequestBuilder.get();
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

	private final Client _client;
	private String _field;
	private final String _index;
	private QueryBuilderFactory _queryBuilderFactory;
	private final String _type;

}