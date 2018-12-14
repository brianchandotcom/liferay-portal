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

package com.liferay.portal.search.elasticsearch7.internal.index;

import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchFixture;
import com.liferay.portal.search.elasticsearch7.internal.connection.Index;
import com.liferay.portal.search.elasticsearch7.internal.connection.IndexCreator;
import com.liferay.portal.search.elasticsearch7.internal.connection.IndexName;

import java.io.IOException;

import java.util.Map;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * @author André de Oliveira
 */
public class LiferayIndexFixture {

	public LiferayIndexFixture(String subdirName, IndexName indexName) {
		_elasticsearchFixture = new ElasticsearchFixture(subdirName);
		_indexName = indexName;
	}

	public void assertAnalyzer(String field, String analyzer) throws Exception {
		RestHighLevelClient restHighLevelClient = getRestHighLevelClient();

		FieldMappingAssert.assertAnalyzer(
			analyzer, field, LiferayTypeMappingsConstants.LIFERAY_DOCUMENT_TYPE,
			_index.getName(), restHighLevelClient.indices());
	}

	public void assertType(String field, String type) throws Exception {
		RestHighLevelClient restHighLevelClient = getRestHighLevelClient();

		FieldMappingAssert.assertType(
			type, field, LiferayTypeMappingsConstants.LIFERAY_DOCUMENT_TYPE,
			_index.getName(), restHighLevelClient.indices());
	}

	public Index getIndex() {
		return _index;
	}

	public RestHighLevelClient getRestHighLevelClient() {
		return _elasticsearchFixture.getRestHighLevelClient();
	}

	public void index(Map<String, Object> map) {
		IndexRequest indexRequest = getIndexRequest();

		indexRequest.source(map);

		RestHighLevelClient restHighLevelClient = getRestHighLevelClient();

		try {
			restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	public void setUp() throws Exception {
		_elasticsearchFixture.setUp();

		_index = createIndex();
	}

	public void tearDown() throws Exception {
		_elasticsearchFixture.tearDown();
	}

	protected Index createIndex() {
		IndexCreator indexCreator = new IndexCreator() {
			{
				setElasticsearchClientResolver(_elasticsearchFixture);
				setLiferayMappingsAddedToIndex(true);
			}
		};

		return indexCreator.createIndex(_indexName);
	}

	protected IndexRequest getIndexRequest() {
		return new IndexRequest(
			_index.getName(),
			LiferayTypeMappingsConstants.LIFERAY_DOCUMENT_TYPE);
	}

	private final ElasticsearchFixture _elasticsearchFixture;
	private Index _index;
	private final IndexName _indexName;

}