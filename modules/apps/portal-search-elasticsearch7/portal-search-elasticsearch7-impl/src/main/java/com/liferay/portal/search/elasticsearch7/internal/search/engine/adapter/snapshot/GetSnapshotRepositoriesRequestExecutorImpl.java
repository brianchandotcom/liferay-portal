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

package com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.snapshot;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.engine.adapter.snapshot.GetSnapshotRepositoriesRequest;
import com.liferay.portal.search.engine.adapter.snapshot.GetSnapshotRepositoriesResponse;
import com.liferay.portal.search.engine.adapter.snapshot.SnapshotRepositoryDetails;

import java.io.IOException;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import org.elasticsearch.ElasticsearchStatusException;
import org.elasticsearch.action.admin.cluster.repositories.get.GetRepositoriesRequest;
import org.elasticsearch.action.admin.cluster.repositories.get.GetRepositoriesResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.SnapshotClient;
import org.elasticsearch.cluster.metadata.RepositoryMetadata;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.repositories.RepositoryMissingException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(service = GetSnapshotRepositoriesRequestExecutor.class)
public class GetSnapshotRepositoriesRequestExecutorImpl
	implements GetSnapshotRepositoriesRequestExecutor {

	@Override
	public GetSnapshotRepositoriesResponse execute(
		GetSnapshotRepositoriesRequest getSnapshotRepositoriesRequest) {

		GetRepositoriesRequest getRepositoriesRequest =
			createGetRepositoriesRequest(getSnapshotRepositoriesRequest);

		GetSnapshotRepositoriesResponse getSnapshotRepositoriesResponse =
			new GetSnapshotRepositoriesResponse();

		try {
			GetRepositoriesResponse elasticsearchGetRepositoriesResponse =
				_getGetRepositoriesResponse(
					getRepositoriesRequest, getSnapshotRepositoriesRequest);

			List<RepositoryMetadata> repositoriesMetadatas =
				elasticsearchGetRepositoriesResponse.repositories();

			repositoriesMetadatas.forEach(
				repositoryMetadata -> {
					Settings repositoryMetadataSettings =
						repositoryMetadata.settings();

					SnapshotRepositoryDetails snapshotRepositoryDetails =
						new SnapshotRepositoryDetails(
							repositoryMetadata.name(),
							repositoryMetadata.type(),
							repositoryMetadataSettings.toString());

					getSnapshotRepositoriesResponse.
						addSnapshotRepositoryMetadata(
							snapshotRepositoryDetails);
				});
		}
		catch (RepositoryMissingException repositoryMissingException) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					repositoryMissingException, repositoryMissingException);
			}
		}

		return getSnapshotRepositoriesResponse;
	}

	protected GetRepositoriesRequest createGetRepositoriesRequest(
		GetSnapshotRepositoriesRequest getSnapshotRepositoriesRequest) {

		GetRepositoriesRequest getRepositoriesRequest =
			new GetRepositoriesRequest();

		getRepositoriesRequest.repositories(
			getSnapshotRepositoriesRequest.getRepositoryNames());

		return getRepositoriesRequest;
	}

	@Reference(unbind = "-")
	protected void setElasticsearchClientResolver(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	private GetRepositoriesResponse _getGetRepositoriesResponse(
		GetRepositoriesRequest getRepositoriesRequest,
		GetSnapshotRepositoriesRequest getSnapshotRepositoriesRequest) {

		RestHighLevelClient restHighLevelClient =
			_elasticsearchClientResolver.getRestHighLevelClient(
				getSnapshotRepositoriesRequest.getConnectionId(),
				getSnapshotRepositoriesRequest.isPreferLocalCluster());

		SnapshotClient snapshotClient = restHighLevelClient.snapshot();

		try {
			return snapshotClient.getRepository(
				getRepositoriesRequest, RequestOptions.DEFAULT);
		}
		catch (ElasticsearchStatusException elasticsearchStatusException) {
			String message = elasticsearchStatusException.getMessage();

			if (message.contains("type=repository_missing_exception")) {
				throw new RepositoryMissingException(
					StringUtils.substringBetween(
						message, "reason=[", "] missing"));
			}

			throw elasticsearchStatusException;
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GetSnapshotRepositoriesRequestExecutorImpl.class);

	private ElasticsearchClientResolver _elasticsearchClientResolver;

}