/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.connection.helper;

import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.elasticsearch7.internal.index.LiferayDocumentTypeFactory;
import com.liferay.portal.search.elasticsearch7.internal.settings.SettingsBuilder;

import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;

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
			_getLiferayDocumentTypeFactory(null);

		liferayDocumentTypeFactory.createRequiredDefaultTypeMappings(
			createIndexRequest);
	}

	@Override
	public void contributeIndexSettings(SettingsBuilder settingsBuilder) {
		LiferayDocumentTypeFactory liferayDocumentTypeFactory =
			_getLiferayDocumentTypeFactory(null);

		liferayDocumentTypeFactory.createRequiredDefaultAnalyzers(
			settingsBuilder);
	}

	@Override
	public void whenIndexCreated(String indexName) {
		LiferayDocumentTypeFactory liferayDocumentTypeFactory =
			_getLiferayDocumentTypeFactory(indexName);

		liferayDocumentTypeFactory.putDefaultTypeMappingTemplate();
	}

	private LiferayDocumentTypeFactory _getLiferayDocumentTypeFactory(
		String indexName) {

		RestHighLevelClient restHighLevelClient =
			_elasticsearchClientResolver.getRestHighLevelClient();

		return new LiferayDocumentTypeFactory(
			indexName, restHighLevelClient.indices(), new JSONFactoryImpl());
	}

	private final ElasticsearchClientResolver _elasticsearchClientResolver;

}