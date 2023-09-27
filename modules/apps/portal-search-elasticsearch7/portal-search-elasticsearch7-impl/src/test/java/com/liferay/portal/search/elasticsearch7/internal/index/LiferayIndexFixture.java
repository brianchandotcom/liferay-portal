/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.index;

import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchFixture;
import com.liferay.portal.search.elasticsearch7.internal.connection.IndexCreator;
import com.liferay.portal.search.elasticsearch7.internal.connection.IndexName;
import com.liferay.portal.search.elasticsearch7.internal.index.constants.LiferayTypeMappingsConstants;

import java.io.IOException;

import java.util.Map;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * @author André de Oliveira
 */
public class LiferayIndexFixture {

	public LiferayIndexFixture(String subdirName, IndexName indexName) {
		ElasticsearchFixture elasticsearchFixture = new ElasticsearchFixture();

		_elasticsearchFixture = elasticsearchFixture;

		_indexCreator = new IndexCreator() {
			{
				setElasticsearchClientResolver(elasticsearchFixture);
				setLiferayMappingsAddedToIndex(true);
			}
		};

		_indexName = indexName;
	}

	public void assertAnalyzer(String field, String analyzer) throws Exception {
		RestHighLevelClient restHighLevelClient = getRestHighLevelClient();

		FieldMappingAssert.assertAnalyzer(
			analyzer, field, LiferayTypeMappingsConstants.LIFERAY_DOCUMENT_TYPE,
			_indexName.getName(), restHighLevelClient.indices());
	}

	public void assertType(String field, String type) throws Exception {
		RestHighLevelClient restHighLevelClient = getRestHighLevelClient();

		FieldMappingAssert.assertType(
			type, field, LiferayTypeMappingsConstants.LIFERAY_DOCUMENT_TYPE,
			_indexName.getName(), restHighLevelClient.indices());
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
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	public void setUp() throws Exception {
		_elasticsearchFixture.setUp();

		_indexCreator.createIndex(_indexName);
	}

	public void tearDown() throws Exception {
		_indexCreator.deleteIndex(_indexName);

		_elasticsearchFixture.tearDown();
	}

	protected IndexRequest getIndexRequest() {
		return Requests.indexRequest(_indexName.getName());
	}

	private final ElasticsearchFixture _elasticsearchFixture;
	private final IndexCreator _indexCreator;
	private final IndexName _indexName;

}