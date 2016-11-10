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

package com.liferay.portal.search.elasticsearch.internal.index;

import com.liferay.portal.search.elasticsearch.internal.connection.ElasticsearchFixture;
import com.liferay.portal.search.elasticsearch.internal.connection.Index;
import com.liferay.portal.search.elasticsearch.internal.connection.IndexCreator;
import com.liferay.portal.search.elasticsearch.internal.connection.IndexName;
import com.liferay.portal.search.elasticsearch.internal.connection.LiferayIndexCreator;

import org.elasticsearch.client.Client;

/**
 * @author André de Oliveira
 */
public class LiferayIndexFixture {

	public LiferayIndexFixture(String subdirName, IndexName indexName) {
		_elasticsearchFixture = new ElasticsearchFixture(subdirName);
		_indexName = indexName;
	}

	public Client getClient() {
		return _elasticsearchFixture.getClient();
	}

	public ElasticsearchFixture getElasticsearchFixture() {
		return _elasticsearchFixture;
	}

	public Index getIndex() {
		return _index;
	}

	public void setUp() throws Exception {
		_elasticsearchFixture.setUp();

		_index = createIndex();
	}

	public void tearDown() throws Exception {
		_elasticsearchFixture.tearDown();
	}

	protected Index createIndex() {
		IndexCreator indexCreator = new LiferayIndexCreator(
			_elasticsearchFixture);

		return indexCreator.createIndex(_indexName);
	}

	private final ElasticsearchFixture _elasticsearchFixture;
	private Index _index;
	private final IndexName _indexName;

}