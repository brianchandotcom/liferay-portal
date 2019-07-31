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

package com.liferay.portal.search.elasticsearch6.internal.connection;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequestBuilder;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.client.AdminClient;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.common.settings.Settings;

import org.mockito.Mockito;

/**
 * @author André de Oliveira
 */
public class IndexCreator {

	public Index createIndex(IndexName indexName) {
		IndicesAdminClient indicesAdminClient = getIndicesAdminClient();

		String name = indexName.getName();

		DeleteIndexRequestBuilder deleteIndexRequestBuilder =
			indicesAdminClient.prepareDelete(name);

		deleteIndexRequestBuilder.setIndicesOptions(
			IndicesOptions.lenientExpandOpen());

		deleteIndexRequestBuilder.get();

		CreateIndexRequestBuilder createIndexRequestBuilder =
			indicesAdminClient.prepareCreate(name);

		IndexCreationHelper indexCreationHelper = getIndexCreationHelper();

		indexCreationHelper.contribute(createIndexRequestBuilder);

		Settings.Builder builder = Settings.builder();

		builder.put("index.number_of_replicas", 0);
		builder.put("index.number_of_shards", 1);

		indexCreationHelper.contributeIndexSettings(builder);

		createIndexRequestBuilder.setSettings(builder);

		createIndexRequestBuilder.get();

		indexCreationHelper.whenIndexCreated(name);

		return new Index(indexName);
	}

	protected IndexCreationHelper getIndexCreationHelper() {
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
			public void contribute(
				CreateIndexRequestBuilder createIndexRequestBuilder) {

				_indexCreationHelper.contribute(createIndexRequestBuilder);

				liferayIndexCreationHelper.contribute(
					createIndexRequestBuilder);
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

	protected final IndicesAdminClient getIndicesAdminClient() {
		Client client = _elasticsearchClientResolver.getClient();

		AdminClient adminClient = client.admin();

		return adminClient.indices();
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

	private ElasticsearchClientResolver _elasticsearchClientResolver;
	private IndexCreationHelper _indexCreationHelper;
	private boolean _liferayMappingsAddedToIndex;

}