/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.index;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.elasticsearch7.internal.helper.SearchLogHelperUtil;
import com.liferay.portal.search.elasticsearch7.internal.util.ClassLoaderUtil;
import com.liferay.portal.search.engine.adapter.index.CreateIndexRequest;
import com.liferay.portal.search.engine.adapter.index.CreateIndexResponse;

import java.io.IOException;

import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.xcontent.XContentType;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 * @author Joshua Cords
 * @author Tibor Lipusz
 */
@Component(service = CreateIndexRequestExecutor.class)
public class CreateIndexRequestExecutorImpl
	implements CreateIndexRequestExecutor {

	@Override
	public CreateIndexResponse execute(CreateIndexRequest createIndexRequest) {
		org.elasticsearch.action.admin.indices.create.CreateIndexRequest
			elasticsearchCreateIndexRequest = createCreateIndexRequest(
				createIndexRequest);

		org.elasticsearch.action.admin.indices.create.CreateIndexResponse
			elasticsearchCreateIndexResponse = _getCreateIndexResponse(
				elasticsearchCreateIndexRequest, createIndexRequest);

		SearchLogHelperUtil.logActionResponse(
			_log, elasticsearchCreateIndexResponse);

		return new CreateIndexResponse(
			elasticsearchCreateIndexResponse.isAcknowledged(),
			elasticsearchCreateIndexResponse.index());
	}

	protected org.elasticsearch.action.admin.indices.create.CreateIndexRequest
		createCreateIndexRequest(CreateIndexRequest createIndexRequest) {

		org.elasticsearch.action.admin.indices.create.CreateIndexRequest
			elasticsearchCreateIndexRequest =
				new org.elasticsearch.action.admin.indices.create.
					CreateIndexRequest(createIndexRequest.getIndexName());

		if (createIndexRequest.getMappings() != null) {
			ClassLoaderUtil.getWithContextClassLoader(
				() -> elasticsearchCreateIndexRequest.mapping(
					"_doc", createIndexRequest.getMappings(),
					XContentType.JSON),
				getClass());
		}

		if (createIndexRequest.getSettings() != null) {
			ClassLoaderUtil.getWithContextClassLoader(
				() -> elasticsearchCreateIndexRequest.settings(
					createIndexRequest.getSettings(), XContentType.JSON),
				getClass());
		}

		if (createIndexRequest.getSource() != null) {
			ClassLoaderUtil.getWithContextClassLoader(
				() -> elasticsearchCreateIndexRequest.source(
					createIndexRequest.getSource(), XContentType.JSON),
				getClass());
		}

		return elasticsearchCreateIndexRequest;
	}

	private org.elasticsearch.action.admin.indices.create.CreateIndexResponse
		_getCreateIndexResponse(
			org.elasticsearch.action.admin.indices.create.CreateIndexRequest
				elasticsearchCreateIndexRequest,
			CreateIndexRequest createIndexRequest) {

		RestHighLevelClient restHighLevelClient =
			_elasticsearchClientResolver.getRestHighLevelClient(
				createIndexRequest.getConnectionId(),
				createIndexRequest.isPreferLocalCluster());

		IndicesClient indicesClient = restHighLevelClient.indices();

		try {
			return indicesClient.create(
				elasticsearchCreateIndexRequest, RequestOptions.DEFAULT);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CreateIndexRequestExecutorImpl.class);

	@Reference
	private ElasticsearchClientResolver _elasticsearchClientResolver;

}