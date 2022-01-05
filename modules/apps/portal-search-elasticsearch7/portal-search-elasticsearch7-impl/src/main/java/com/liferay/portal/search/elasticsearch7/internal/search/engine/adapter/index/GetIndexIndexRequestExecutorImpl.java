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

package com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.index;

import com.carrotsearch.hppc.cursors.ObjectObjectCursor;

import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.engine.adapter.index.GetIndexIndexRequest;
import com.liferay.portal.search.engine.adapter.index.GetIndexIndexResponse;

import java.io.IOException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.cluster.metadata.MappingMetadata;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.common.compress.CompressedXContent;
import org.elasticsearch.common.settings.Settings;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(service = GetIndexIndexRequestExecutor.class)
public class GetIndexIndexRequestExecutorImpl
	implements GetIndexIndexRequestExecutor {

	@Override
	public GetIndexIndexResponse execute(
		GetIndexIndexRequest getIndexIndexRequest) {

		GetIndexRequest getIndexRequest = createGetIndexRequest(
			getIndexIndexRequest);

		GetIndexResponse getIndexResponse = _getGetIndexResponse(
			getIndexRequest, getIndexIndexRequest);

		GetIndexIndexResponse getIndexIndexResponse =
			new GetIndexIndexResponse();

		getIndexIndexResponse.setIndexNames(getIndexResponse.getIndices());

		ImmutableOpenMap<String, ImmutableOpenMap<String, MappingMetadata>>
			indicesMappings = getIndexResponse.getMappings();

		getIndexIndexResponse.setMappings(_convertMappings(indicesMappings));

		ImmutableOpenMap<String, Settings> indicesSettings =
			getIndexResponse.getSettings();

		getIndexIndexResponse.setSettings(_convertSettings(indicesSettings));

		return getIndexIndexResponse;
	}

	protected GetIndexRequest createGetIndexRequest(
		GetIndexIndexRequest getIndexIndexRequest) {

		GetIndexRequest getIndexRequest = new GetIndexRequest();

		getIndexRequest.indices(getIndexIndexRequest.getIndexNames());

		return getIndexRequest;
	}

	@Reference(unbind = "-")
	protected void setElasticsearchClientResolver(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	private Map<String, Map<String, String>> _convertMappings(
		ImmutableOpenMap<String, ImmutableOpenMap<String, MappingMetadata>>
			indicesMappings) {

		Iterator
			<ObjectObjectCursor
				<String, ImmutableOpenMap<String, MappingMetadata>>> iterator =
					indicesMappings.iterator();

		Map<String, Map<String, String>> indexMappings = new HashMap<>();

		while (iterator.hasNext()) {
			ObjectObjectCursor
				<String, ImmutableOpenMap<String, MappingMetadata>>
					objectObjectCursor = iterator.next();

			ImmutableOpenMap<String, MappingMetadata> typeMappingsData =
				objectObjectCursor.value;

			Map<String, String> indiceTypeMappings = new HashMap<>();

			indexMappings.put(objectObjectCursor.key, indiceTypeMappings);

			Iterator<ObjectObjectCursor<String, MappingMetadata>>
				typeMappingsIterator = typeMappingsData.iterator();

			while (typeMappingsIterator.hasNext()) {
				ObjectObjectCursor<String, MappingMetadata>
					typeMappingsObjectObjectCursor =
						typeMappingsIterator.next();

				MappingMetadata mappingMetadata =
					typeMappingsObjectObjectCursor.value;

				CompressedXContent mappingContent = mappingMetadata.source();

				indiceTypeMappings.put(
					typeMappingsObjectObjectCursor.key,
					mappingContent.toString());
			}
		}

		return indexMappings;
	}

	private Map<String, String> _convertSettings(
		ImmutableOpenMap<String, Settings> indicesSettings) {

		Iterator<ObjectObjectCursor<String, Settings>> iterator =
			indicesSettings.iterator();

		Map<String, String> indicesSettingsMap = new HashMap<>();

		while (iterator.hasNext()) {
			ObjectObjectCursor<String, Settings> objectObjectCursor =
				iterator.next();

			Settings settings = objectObjectCursor.value;

			indicesSettingsMap.put(objectObjectCursor.key, settings.toString());
		}

		return indicesSettingsMap;
	}

	private GetIndexResponse _getGetIndexResponse(
		GetIndexRequest getIndexRequest,
		GetIndexIndexRequest getIndexIndexRequest) {

		RestHighLevelClient restHighLevelClient =
			_elasticsearchClientResolver.getRestHighLevelClient(
				getIndexIndexRequest.getConnectionId(),
				getIndexIndexRequest.isPreferLocalCluster());

		IndicesClient indicesClient = restHighLevelClient.indices();

		try {
			return indicesClient.get(getIndexRequest, RequestOptions.DEFAULT);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private ElasticsearchClientResolver _elasticsearchClientResolver;

}