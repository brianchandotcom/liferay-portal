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

import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.elasticsearch7.internal.configuration.ElasticsearchConfigurationWrapper;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchConnectionManager;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchConnectionNotInitializedException;
import com.liferay.portal.search.elasticsearch7.internal.connection.IndexName;
import com.liferay.portal.search.index.IndexNameBuilder;

import java.util.HashMap;

import org.elasticsearch.client.RestHighLevelClient;

import org.mockito.Mockito;

/**
 * @author Adam Brandizzi
 */
public class CompanyIndexFactoryFixture {

	public CompanyIndexFactoryFixture(
		ElasticsearchClientResolver elasticsearchClientResolver,
		String indexName) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
		_indexName = indexName;

		_elasticsearchConnectionManager = Mockito.mock(
			ElasticsearchConnectionManager.class);

		Mockito.when(
			_elasticsearchConnectionManager.getRestHighLevelClient()
		).thenThrow(
			ElasticsearchConnectionNotInitializedException.class
		);
	}

	public void createIndices() {
		CompanyIndexFactory companyIndexFactory = getCompanyIndexFactory();

		RestHighLevelClient restHighLevelClient =
			_elasticsearchClientResolver.getRestHighLevelClient();

		companyIndexFactory.createIndices(
			restHighLevelClient.indices(), RandomTestUtil.randomLong());
	}

	public void deleteIndices() {
		CompanyIndexFactory companyIndexFactory = getCompanyIndexFactory();

		RestHighLevelClient restHighLevelClient =
			_elasticsearchClientResolver.getRestHighLevelClient();

		companyIndexFactory.deleteIndices(
			restHighLevelClient.indices(), RandomTestUtil.randomLong());
	}

	public CompanyIndexFactory getCompanyIndexFactory() {
		return new CompanyIndexFactory() {
			{
				setIndexNameBuilder(new TestIndexNameBuilder());
				setJsonFactory(new JSONFactoryImpl());
				setElasticsearchConfigurationWrapper(
					createElasticsearchConfigurationWrapper());
				setElasticsearchConnectionManager(
					_elasticsearchConnectionManager);
			}
		};
	}

	public String getIndexName() {
		IndexName indexName = new IndexName(_indexName);

		return indexName.getName();
	}

	protected ElasticsearchConfigurationWrapper
		createElasticsearchConfigurationWrapper() {

		return new ElasticsearchConfigurationWrapper() {
			{
				activate(new HashMap<>());
			}
		};
	}

	protected class TestIndexNameBuilder implements IndexNameBuilder {

		@Override
		public String getIndexName(long companyId) {
			return CompanyIndexFactoryFixture.this.getIndexName();
		}

	}

	private final ElasticsearchClientResolver _elasticsearchClientResolver;
	private final ElasticsearchConnectionManager
		_elasticsearchConnectionManager;
	private final String _indexName;

}