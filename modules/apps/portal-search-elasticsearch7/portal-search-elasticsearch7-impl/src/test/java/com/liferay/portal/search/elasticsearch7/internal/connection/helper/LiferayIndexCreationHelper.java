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

package com.liferay.portal.search.elasticsearch7.internal.connection.helper;

import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.elasticsearch7.internal.index.LiferayDocumentTypeFactory;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;

/**
 * @author André de Oliveira
 */
public class LiferayIndexCreationHelper implements IndexCreationHelper {

	public LiferayIndexCreationHelper(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	@Override
	public void contribute(CreateIndexRequest createIndexRequest) {
		LiferayDocumentTypeFactory liferayDocumentTypeFactory =
			_getLiferayDocumentTypeFactory();

		liferayDocumentTypeFactory.createRequiredDefaultTypeMappings(
			createIndexRequest);
	}

	@Override
	public void contributeIndexSettings(Settings.Builder builder) {
		LiferayDocumentTypeFactory liferayDocumentTypeFactory =
			_getLiferayDocumentTypeFactory();

		liferayDocumentTypeFactory.createRequiredDefaultAnalyzers(builder);
	}

	@Override
	public void whenIndexCreated(String indexName) {
		LiferayDocumentTypeFactory liferayDocumentTypeFactory =
			_getLiferayDocumentTypeFactory();

		liferayDocumentTypeFactory.createOptionalDefaultTypeMappings(indexName);
	}

	private LiferayDocumentTypeFactory _getLiferayDocumentTypeFactory() {
		RestHighLevelClient restHighLevelClient =
			_elasticsearchClientResolver.getRestHighLevelClient();

		return new LiferayDocumentTypeFactory(
			restHighLevelClient.indices(), new JSONFactoryImpl());
	}

	private final ElasticsearchClientResolver _elasticsearchClientResolver;

}