/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.connection.helper;

import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.search.opensearch2.internal.connection.OpenSearchConnectionManager;
import com.liferay.portal.search.opensearch2.internal.index.MappingsHelperImpl;
import com.liferay.portal.search.opensearch2.internal.index.constants.IndexSettingsConstants;
import com.liferay.portal.search.opensearch2.internal.settings.SettingsHelperImpl;
import com.liferay.portal.search.opensearch2.internal.util.ResourceUtil;

import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch.indices.CreateIndexRequest;

/**
 * @author André de Oliveira
 * @author Petteri Karttunen
 */
public class LiferayIndexCreationHelper implements IndexCreationHelper {

	public LiferayIndexCreationHelper(
		OpenSearchConnectionManager openSearchConnectionManager) {

		_openSearchConnectionManager = openSearchConnectionManager;
	}

	@Override
	public void contribute(CreateIndexRequest.Builder builder) {
		OpenSearchClient openSearchClient =
			_openSearchConnectionManager.getOpenSearchClient();

		MappingsHelperImpl mappingsHelperImpl = new MappingsHelperImpl(
			null, new JSONFactoryImpl(), openSearchClient.indices(), null);

		mappingsHelperImpl.setDefaultOrOverrideMappings(
			builder, _openSearchConnectionManager.getJsonpMapper(null));
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

	private final OpenSearchConnectionManager _openSearchConnectionManager;

}