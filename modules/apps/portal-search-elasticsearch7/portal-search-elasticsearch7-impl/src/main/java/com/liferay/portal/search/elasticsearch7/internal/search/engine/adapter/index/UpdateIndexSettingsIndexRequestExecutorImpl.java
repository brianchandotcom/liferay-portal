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

import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.engine.adapter.index.IndicesOptions;
import com.liferay.portal.search.engine.adapter.index.UpdateIndexSettingsIndexRequest;
import com.liferay.portal.search.engine.adapter.index.UpdateIndexSettingsIndexResponse;

import java.io.IOException;

import org.elasticsearch.action.admin.indices.settings.put.UpdateSettingsRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.xcontent.XContentType;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(service = UpdateIndexSettingsIndexRequestExecutor.class)
public class UpdateIndexSettingsIndexRequestExecutorImpl
	implements UpdateIndexSettingsIndexRequestExecutor {

	@Override
	public UpdateIndexSettingsIndexResponse execute(
		UpdateIndexSettingsIndexRequest updateIndexSettingsIndexRequest) {

		UpdateSettingsRequest updateSettingsRequest =
			createUpdateSettingsRequest(updateIndexSettingsIndexRequest);

		AcknowledgedResponse acknowledgedResponse = getAcknowledgedResponse(
			updateSettingsRequest, updateIndexSettingsIndexRequest);

		return new UpdateIndexSettingsIndexResponse(
			acknowledgedResponse.isAcknowledged());
	}

	protected UpdateSettingsRequest createUpdateSettingsRequest(
		UpdateIndexSettingsIndexRequest updateIndexSettingsIndexRequest) {

		UpdateSettingsRequest updateSettingsRequest = new UpdateSettingsRequest(
			updateIndexSettingsIndexRequest.getIndexNames());

		updateSettingsRequest.settings(
			updateIndexSettingsIndexRequest.getSettings(), XContentType.JSON);

		IndicesOptions indicesOptions =
			updateIndexSettingsIndexRequest.getIndicesOptions();

		if (indicesOptions != null) {
			updateSettingsRequest.indicesOptions(
				_indicesOptionsTranslator.translate(indicesOptions));
		}

		return updateSettingsRequest;
	}

	protected AcknowledgedResponse getAcknowledgedResponse(
		UpdateSettingsRequest updateSettingsRequest,
		UpdateIndexSettingsIndexRequest updateIndexSettingsIndexRequest) {

		RestHighLevelClient restHighLevelClient =
			_elasticsearchClientResolver.getRestHighLevelClient(
				updateIndexSettingsIndexRequest.getConnectionId(),
				updateIndexSettingsIndexRequest.isPreferLocalCluster());

		IndicesClient indicesClient = restHighLevelClient.indices();

		try {
			return indicesClient.putSettings(
				updateSettingsRequest, RequestOptions.DEFAULT);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	@Reference(unbind = "-")
	protected void setElasticsearchClientResolver(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	@Reference(unbind = "-")
	protected void setIndicesOptionsTranslator(
		IndicesOptionsTranslator indicesOptionsTranslator) {

		_indicesOptionsTranslator = indicesOptionsTranslator;
	}

	private ElasticsearchClientResolver _elasticsearchClientResolver;
	private IndicesOptionsTranslator _indicesOptionsTranslator;

}