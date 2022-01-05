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

package com.liferay.portal.search.elasticsearch7.internal.connection;

import com.liferay.portal.search.elasticsearch7.internal.connection.helper.IndexCreationHelper;
import com.liferay.portal.search.elasticsearch7.internal.connection.helper.LiferayIndexCreationHelper;

import java.io.IOException;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;

import org.mockito.Mockito;

/**
 * @author André de Oliveira
 */
public class IndexCreator {

	public Index createIndex(IndexName indexName) {
		IndicesClient indicesClient = _getIndicesClient();

		String name = indexName.getName();

		deleteIndex(indicesClient, name);

		CreateIndexRequest createIndexRequest = new CreateIndexRequest(name);

		IndexCreationHelper indexCreationHelper = _getIndexCreationHelper();

		indexCreationHelper.contribute(createIndexRequest);

		Settings.Builder builder = Settings.builder();

		builder.put("index.number_of_replicas", 0);
		builder.put("index.number_of_shards", 1);

		indexCreationHelper.contributeIndexSettings(builder);

		createIndexRequest.settings(builder);

		try {
			indicesClient.create(createIndexRequest, RequestOptions.DEFAULT);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		indexCreationHelper.whenIndexCreated(name);

		return new Index(indexName);
	}

	public void deleteIndex(IndexName indexName) {
		deleteIndex(_getIndicesClient(), indexName.getName());
	}

	protected void deleteIndex(IndicesClient indicesClient, String name) {
		DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(name);

		deleteIndexRequest.indicesOptions(IndicesOptions.lenientExpandOpen());

		try {
			indicesClient.delete(deleteIndexRequest, RequestOptions.DEFAULT);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	protected void setElasticsearchClientResolver(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	protected void setIndexCreationHelper(
		IndexCreationHelper indexCreationHelper) {

		_indexCreationHelper = indexCreationHelper;
	}

	protected void setLiferayMappingsAddedToIndex(
		boolean liferayMappingsAddedToIndex) {

		_liferayMappingsAddedToIndex = liferayMappingsAddedToIndex;
	}

	private IndexCreationHelper _getIndexCreationHelper() {
		if (!_liferayMappingsAddedToIndex) {
			if (_indexCreationHelper != null) {
				return _indexCreationHelper;
			}

			return Mockito.mock(IndexCreationHelper.class);
		}

		LiferayIndexCreationHelper liferayIndexCreationHelper =
			new LiferayIndexCreationHelper(_elasticsearchClientResolver);

		if (_indexCreationHelper == null) {
			return liferayIndexCreationHelper;
		}

		return new IndexCreationHelper() {

			@Override
			public void contribute(CreateIndexRequest createIndexRequest) {
				_indexCreationHelper.contribute(createIndexRequest);

				liferayIndexCreationHelper.contribute(createIndexRequest);
			}

			@Override
			public void contributeIndexSettings(Settings.Builder builder) {
				_indexCreationHelper.contributeIndexSettings(builder);

				liferayIndexCreationHelper.contributeIndexSettings(builder);
			}

			@Override
			public void whenIndexCreated(String indexName) {
				_indexCreationHelper.whenIndexCreated(indexName);

				liferayIndexCreationHelper.whenIndexCreated(indexName);
			}

		};
	}

	private final IndicesClient _getIndicesClient() {
		RestHighLevelClient restHighLevelClient =
			_elasticsearchClientResolver.getRestHighLevelClient();

		return restHighLevelClient.indices();
	}

	private ElasticsearchClientResolver _elasticsearchClientResolver;
	private IndexCreationHelper _indexCreationHelper;
	private boolean _liferayMappingsAddedToIndex;

}