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
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.common.settings.Settings;

import org.mockito.Mockito;

/**
 * @author André de Oliveira
 */
public class IndexCreator {

	public IndexCreator(IndicesAdminClientSupplier indicesAdminClientSupplier) {
		_indicesAdminClientSupplier = indicesAdminClientSupplier;
	}

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

		IndexCreationHelper indexCreationHelper = _indexCreationHelper;

		if (indexCreationHelper == null) {
			indexCreationHelper = Mockito.mock(IndexCreationHelper.class);
		}

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

	public void setIndexCreationHelper(
		IndexCreationHelper indexCreationHelper) {

		_indexCreationHelper = indexCreationHelper;
	}

	protected final IndicesAdminClient getIndicesAdminClient() {
		return _indicesAdminClientSupplier.getIndicesAdminClient();
	}

	private IndexCreationHelper _indexCreationHelper;
	private final IndicesAdminClientSupplier _indicesAdminClientSupplier;

}