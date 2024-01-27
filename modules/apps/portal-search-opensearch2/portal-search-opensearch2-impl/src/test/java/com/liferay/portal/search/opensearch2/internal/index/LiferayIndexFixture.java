/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.index;

import com.liferay.portal.search.opensearch2.internal.OpenSearchIndexingFixture;
import com.liferay.portal.search.opensearch2.internal.OpenSearchIndexingFixtureFactory;
import com.liferay.portal.search.opensearch2.internal.connection.IndexCreator;
import com.liferay.portal.search.opensearch2.internal.connection.IndexName;
import com.liferay.portal.search.opensearch2.internal.connection.OpenSearchConnectionManager;

import java.io.IOException;

import java.util.Map;

import org.opensearch.client.json.JsonData;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch.core.IndexRequest;

/**
 * @author André de Oliveira
 */
public class LiferayIndexFixture {

	public LiferayIndexFixture(IndexName indexName) {
		OpenSearchIndexingFixture openSearchIndexingFixture =
			OpenSearchIndexingFixtureFactory.getInstance();

		_openSearchConnectionManager =
			openSearchIndexingFixture.getOpenSearchConnectionManager();

		_indexCreator = new IndexCreator() {
			{
				setLiferayMappingsAddedToIndex(true);
				setOpenSearchConnectionManager(_openSearchConnectionManager);
			}
		};

		_indexName = indexName;
	}

	public void assertAnalyzer(String analyzer, String field) throws Exception {
		OpenSearchClient openSearchClient = getOpenSearchClient();

		FieldMappingAssert.assertAnalyzer(
			analyzer, field, _indexName.getName(), openSearchClient.indices());
	}

	public void assertType(String field, String type) throws Exception {
		OpenSearchClient openSearchClient = getOpenSearchClient();

		FieldMappingAssert.assertType(
			type, field, _indexName.getName(), openSearchClient.indices());
	}

	public OpenSearchClient getOpenSearchClient() {
		return _openSearchConnectionManager.getOpenSearchClient();
	}

	public void index(Map<String, Object> map) {
		OpenSearchClient openSearchClient = getOpenSearchClient();

		try {
			openSearchClient.index(
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
		_indexCreator.createIndex(_indexName);
	}

	public void tearDown() throws Exception {
		_indexCreator.deleteIndex(_indexName);
	}

	private final IndexCreator _indexCreator;
	private final IndexName _indexName;
	private final OpenSearchConnectionManager _openSearchConnectionManager;

}