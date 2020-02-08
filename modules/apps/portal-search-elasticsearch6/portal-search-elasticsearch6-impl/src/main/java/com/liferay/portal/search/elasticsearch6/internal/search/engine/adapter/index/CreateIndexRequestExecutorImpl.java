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
import com.liferay.portal.search.elasticsearch6.internal.settings.IndexSettingsContributorHelper;
import com.liferay.portal.search.engine.adapter.index.CreateIndexRequest;
import com.liferay.portal.search.engine.adapter.index.CreateIndexResponse;

import org.elasticsearch.action.admin.indices.create.CreateIndexAction;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(service = CreateIndexRequestExecutor.class)
public class CreateIndexRequestExecutorImpl
	implements CreateIndexRequestExecutor {

	@Override
	public CreateIndexResponse execute(CreateIndexRequest createIndexRequest) {
		CreateIndexRequestBuilder createIndexRequestBuilder =
			createCreateIndexRequestBuilder(createIndexRequest);

		org.elasticsearch.action.admin.indices.create.CreateIndexResponse
			elasticsearchCreateIndexResponse = createIndexRequestBuilder.get();

		return new CreateIndexResponse(
			elasticsearchCreateIndexResponse.isAcknowledged());
	}

	protected CreateIndexRequestBuilder createCreateIndexRequestBuilder(
		CreateIndexRequest createIndexRequest) {

		CreateIndexRequestBuilder createIndexRequestBuilder =
			CreateIndexAction.INSTANCE.newRequestBuilder(
				_elasticsearchClientResolver.getClient());

		createIndexRequestBuilder.setIndex(createIndexRequest.getIndexName());
		createIndexRequestBuilder.setSource(
			createIndexRequest.getSource(), XContentType.JSON);

		org.elasticsearch.action.admin.indices.create.CreateIndexRequest
			elasticsearchCreateIndexRequest =
				createIndexRequestBuilder.request();

		Settings.Builder builder = Settings.builder();

		builder.put(elasticsearchCreateIndexRequest.settings());

		_indexSettingsContributorHelper.loadIndexSettingsContributors(builder);

		elasticsearchCreateIndexRequest.settings(builder);

		return createIndexRequestBuilder;
	}

	@Reference(unbind = "-")
	protected void setElasticsearchClientResolver(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	private ElasticsearchClientResolver _elasticsearchClientResolver;

	@Reference
	private IndexSettingsContributorHelper _indexSettingsContributorHelper;

}