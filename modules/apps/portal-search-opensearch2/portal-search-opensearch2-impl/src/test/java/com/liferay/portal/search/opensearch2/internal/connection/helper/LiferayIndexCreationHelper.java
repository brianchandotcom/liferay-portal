/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.connection.helper;

import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.search.opensearch2.internal.configuration.OpenSearchConfigurationWrapper;
import com.liferay.portal.search.opensearch2.internal.configuration.OpenSearchConfigurationWrapperImpl;
import com.liferay.portal.search.opensearch2.internal.connection.OpenSearchConnectionManager;
import com.liferay.portal.search.opensearch2.internal.index.MappingsFactory;
import com.liferay.portal.search.opensearch2.internal.index.SettingsFactory;

import jakarta.json.spi.JsonProvider;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.nio.charset.StandardCharsets;

import org.mockito.Mockito;

import org.opensearch.client.json.JsonpMapper;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch._types.mapping.TypeMapping;
import org.opensearch.client.opensearch.indices.CreateIndexRequest;
import org.opensearch.client.opensearch.indices.IndexSettings;

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
		JsonpMapper jsonpMapper = _openSearchConnectionManager.getJsonpMapper(
			null);

		JsonProvider jsonProvider = jsonpMapper.jsonProvider();

		MappingsFactory mappingsFactory = _getMappingsFactory();

		String mappings = String.valueOf(
			mappingsFactory.getMappingsJSONObject());

		try (InputStream inputStream = new ByteArrayInputStream(
				mappings.getBytes(StandardCharsets.UTF_8))) {

			builder.mappings(
				TypeMapping._DESERIALIZER.deserialize(
					jsonProvider.createParser(inputStream), jsonpMapper));
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	@Override
	public void contributeIndexSettings(CreateIndexRequest.Builder builder) {
		JsonpMapper jsonpMapper = _openSearchConnectionManager.getJsonpMapper(
			null);

		JsonProvider jsonProvider = jsonpMapper.jsonProvider();

		SettingsFactory settingsFactory = new SettingsFactory(
			new JSONFactoryImpl(), _openSearchConfigurationWrapper);

		JSONObject settingsJSONObject = settingsFactory.getSettingsJSONObject();

		settingsJSONObject.put("max_result_window", 10000);

		String settings = String.valueOf(settingsJSONObject);

		try (InputStream inputStream = new ByteArrayInputStream(
				settings.getBytes(StandardCharsets.UTF_8))) {

			builder.settings(
				IndexSettings._DESERIALIZER.deserialize(
					jsonProvider.createParser(inputStream), jsonpMapper));
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	@Override
	public void whenIndexCreated(String indexName) {
		MappingsFactory mappingsFactory = _getMappingsFactory();

		mappingsFactory.addOptionalDefaultMappings(indexName);
	}

	private MappingsFactory _getMappingsFactory() {
		OpenSearchClient openSearchClient =
			_openSearchConnectionManager.getOpenSearchClient();

		return new MappingsFactory(
			new JSONFactoryImpl(), openSearchClient.indices(),
			_openSearchConfigurationWrapper);
	}

	private final OpenSearchConfigurationWrapper
		_openSearchConfigurationWrapper = Mockito.mock(
			OpenSearchConfigurationWrapperImpl.class);
	private final OpenSearchConnectionManager _openSearchConnectionManager;

}