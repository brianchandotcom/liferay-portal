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

package com.liferay.portal.search.elasticsearch6.internal.search.engine.adapter.index;

import com.liferay.portal.search.elasticsearch6.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.engine.adapter.index.IndicesOptions;
import com.liferay.portal.search.engine.adapter.index.UpdateIndexSettingsIndexRequest;
import com.liferay.portal.search.engine.adapter.index.UpdateIndexSettingsIndexResponse;

import org.elasticsearch.action.admin.indices.settings.put.UpdateSettingsAction;
import org.elasticsearch.action.admin.indices.settings.put.UpdateSettingsRequestBuilder;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentType;

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

		UpdateSettingsRequestBuilder updateSettingsRequestBuilder =
			createUpdateSettingsRequestBuilder(updateIndexSettingsIndexRequest);

		AcknowledgedResponse acknowledgedResponse =
			updateSettingsRequestBuilder.get();

		return new UpdateIndexSettingsIndexResponse(
			acknowledgedResponse.isAcknowledged());
	}

	protected UpdateSettingsRequestBuilder createUpdateSettingsRequestBuilder(
		UpdateIndexSettingsIndexRequest updateIndexSettingsIndexRequest) {

		Client client = elasticsearchClientResolver.getClient();

		UpdateSettingsRequestBuilder updateSettingsRequestBuilder =
			UpdateSettingsAction.INSTANCE.newRequestBuilder(client);

		updateSettingsRequestBuilder.setIndices(
			updateIndexSettingsIndexRequest.getIndexNames());

		updateSettingsRequestBuilder.setSettings(
			updateIndexSettingsIndexRequest.getSettings(), XContentType.JSON);

		IndicesOptions indicesOptions =
			updateIndexSettingsIndexRequest.getIndicesOptions();

		if (indicesOptions != null) {
			updateSettingsRequestBuilder.setIndicesOptions(
				indicesOptionsTranslator.translate(indicesOptions));
		}

		return updateSettingsRequestBuilder;
	}

	@Reference
	protected ElasticsearchClientResolver elasticsearchClientResolver;

	@Reference
	protected IndicesOptionsTranslator indicesOptionsTranslator;

}