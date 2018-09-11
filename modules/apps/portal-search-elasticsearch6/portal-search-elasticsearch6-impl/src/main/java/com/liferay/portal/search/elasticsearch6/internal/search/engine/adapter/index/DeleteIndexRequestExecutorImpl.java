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

import com.liferay.portal.search.elasticsearch6.internal.connection.ElasticsearchConnectionManager;
import com.liferay.portal.search.engine.adapter.index.DeleteIndexRequest;
import com.liferay.portal.search.engine.adapter.index.DeleteIndexResponse;

import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequestBuilder;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.client.AdminClient;
import org.elasticsearch.client.IndicesAdminClient;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(service = DeleteIndexRequestExecutor.class)
public class DeleteIndexRequestExecutorImpl
	implements DeleteIndexRequestExecutor {

	@Override
	public DeleteIndexResponse execute(DeleteIndexRequest deleteIndexRequest) {
		DeleteIndexRequestBuilder deleteIndexRequestBuilder =
			createDeleteIndexRequestBuilder(deleteIndexRequest);

		org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse
			elasticsearchDeleteIndexResponse = deleteIndexRequestBuilder.get();

		DeleteIndexResponse deleteIndexResponse = new DeleteIndexResponse(
			elasticsearchDeleteIndexResponse.isAcknowledged());

		return deleteIndexResponse;
	}

	protected IndicesOptions convert(
		com.liferay.portal.search.engine.adapter.index.IndicesOptions
			indicesOptions) {

		if (indicesOptions == null) {
			return IndicesOptions.fromOptions(false, true, true, true);
		}

		return IndicesOptions.fromOptions(
			indicesOptions.isIgnoreUnavailable(),
			indicesOptions.isAllowNoIndices(),
			indicesOptions.isExpandToOpenIndices(),
			indicesOptions.isExpandToClosedIndices());
	}

	protected DeleteIndexRequestBuilder createDeleteIndexRequestBuilder(
		DeleteIndexRequest deleteIndexRequest) {

		AdminClient adminClient =
			elasticsearchConnectionManager.getAdminClient();

		IndicesAdminClient indicesAdminClient = adminClient.indices();

		DeleteIndexRequestBuilder deleteIndexRequestBuilder =
			indicesAdminClient.prepareDelete(
				deleteIndexRequest.getIndexNames());

		IndicesOptions indicesOptions = indicesOptionsTranslator.translate(
			deleteIndexRequest.getIndicesOptions());

		deleteIndexRequestBuilder.setIndicesOptions(indicesOptions);

		return deleteIndexRequestBuilder;
	}

	@Reference
	protected ElasticsearchConnectionManager elasticsearchConnectionManager;

	@Reference
	protected IndicesOptionsTranslator indicesOptionsTranslator;

}