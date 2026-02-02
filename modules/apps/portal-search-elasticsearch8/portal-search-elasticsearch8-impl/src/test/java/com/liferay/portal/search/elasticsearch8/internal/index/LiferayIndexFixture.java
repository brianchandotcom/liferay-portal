/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.index;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.json.JsonData;

import com.liferay.portal.search.elasticsearch8.internal.connection.ElasticsearchFixture;
import com.liferay.portal.search.elasticsearch8.internal.connection.IndexCreator;
import com.liferay.portal.search.elasticsearch8.internal.connection.IndexName;
import com.liferay.portal.search.engine.SearchEngineInformation;

import java.io.IOException;

import java.util.Map;

import org.mockito.Mockito;

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
				setSearchEngineInformation(_createSearchEngineInformation());
			}
		};

		_indexName = indexName;
	}

	public void assertAnalyzer(String field, String analyzer) throws Exception {
		ElasticsearchClient elasticsearchClient = getElasticsearchClient();

		FieldMappingAssert.assertAnalyzer(
			analyzer, elasticsearchClient.indices(), field,
			_indexName.getName());
	}

	public void assertType(String field, String type) throws Exception {
		ElasticsearchClient elasticsearchClient = getElasticsearchClient();

		FieldMappingAssert.assertType(
			type, elasticsearchClient.indices(), field, _indexName.getName());
	}

	public ElasticsearchClient getElasticsearchClient() {
		return _elasticsearchFixture.getElasticsearchClient();
	}

	public void index(Map<String, Object> map) {
		ElasticsearchClient elasticsearchClient = getElasticsearchClient();

		try {
			elasticsearchClient.index(
				IndexRequest.of(
					indexRequest -> indexRequest.document(
						JsonData.of(map)
					).index(
						_indexName.getName()
					)));
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

	private SearchEngineInformation _createSearchEngineInformation() {
		SearchEngineInformation searchEngineInformation = Mockito.mock(
			SearchEngineInformation.class);

		Mockito.when(
			searchEngineInformation.getEmbeddingVectorDimensions()
		).thenReturn(
			new int[] {256}
		);

		return searchEngineInformation;
	}

	private final ElasticsearchFixture _elasticsearchFixture;
	private final IndexCreator _indexCreator;
	private final IndexName _indexName;

}