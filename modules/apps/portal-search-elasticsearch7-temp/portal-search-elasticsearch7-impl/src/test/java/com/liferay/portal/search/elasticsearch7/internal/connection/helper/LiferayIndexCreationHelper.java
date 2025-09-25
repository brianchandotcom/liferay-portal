/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.connection.helper;

import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.elasticsearch7.internal.index.MappingsHelperImpl;
import com.liferay.portal.search.elasticsearch7.internal.index.constants.IndexSettingsConstants;
import com.liferay.portal.search.elasticsearch7.internal.settings.SettingsHelperImpl;
import com.liferay.portal.search.elasticsearch7.internal.util.ResourceUtil;
import com.liferay.portal.search.engine.SearchEngineInformation;

import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;

/**
 * @author André de Oliveira
 */
public class LiferayIndexCreationHelper implements IndexCreationHelper {

	public LiferayIndexCreationHelper(
		ElasticsearchClientResolver elasticsearchClientResolver,
		SearchEngineInformation searchEngineInformation) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
		_searchEngineInformation = searchEngineInformation;
	}

	@Override
	public void contribute(CreateIndexRequest createIndexRequest) {
		RestHighLevelClient restHighLevelClient =
			_elasticsearchClientResolver.getRestHighLevelClient();

		MappingsHelperImpl mappingsHelperImpl = new MappingsHelperImpl(
			null, restHighLevelClient.indices(), new JSONFactoryImpl(), null,
			_searchEngineInformation);

		mappingsHelperImpl.setDefaultOrOverrideMappings(createIndexRequest);
	}

	@Override
	public void contributeIndexSettings(SettingsHelperImpl settingsHelperImpl) {
		settingsHelperImpl.loadFromSource(
			ResourceUtil.getResourceAsString(
				getClass(), IndexSettingsConstants.INDEX_SETTINGS_FILE_NAME));
	}

	@Override
	public void whenIndexCreated(String indexName) {
	}

	private final ElasticsearchClientResolver _elasticsearchClientResolver;
	private final SearchEngineInformation _searchEngineInformation;

}