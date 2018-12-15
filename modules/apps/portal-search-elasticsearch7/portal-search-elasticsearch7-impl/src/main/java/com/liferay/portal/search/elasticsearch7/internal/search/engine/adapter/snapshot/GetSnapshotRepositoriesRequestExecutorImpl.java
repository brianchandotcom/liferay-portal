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

import org.elasticsearch.action.admin.cluster.repositories.get.GetRepositoriesRequest;
import org.elasticsearch.action.admin.cluster.repositories.get.GetRepositoriesResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.SnapshotClient;
import org.elasticsearch.cluster.metadata.RepositoryMetaData;
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
				getGetRepositoriesResponse(getRepositoriesRequest);

			List<RepositoryMetaData> repositoriesMetaDatas =
				elasticsearchGetRepositoriesResponse.repositories();

			repositoriesMetaDatas.forEach(
				repositoryMetaData -> {
					Settings repositoryMetadataSettings =
						repositoryMetaData.settings();

					SnapshotRepositoryDetails snapshotRepositoryDetails =
						new SnapshotRepositoryDetails(
							repositoryMetaData.name(),
							repositoryMetaData.type(),
							repositoryMetadataSettings.toString());

					getSnapshotRepositoriesResponse.
						addSnapshotRepositoryMetadata(
							snapshotRepositoryDetails);
				});
		}
		catch (RepositoryMissingException rme) {
			if (_log.isDebugEnabled()) {
				_log.debug(rme, rme);
			}
		}
		finally {
			return getSnapshotRepositoriesResponse;
		}
	}

	protected GetRepositoriesRequest createGetRepositoriesRequest(
		GetSnapshotRepositoriesRequest getSnapshotRepositoriesRequest) {

		GetRepositoriesRequest getRepositoriesRequest =
			new GetRepositoriesRequest();

		getRepositoriesRequest.repositories(
			getSnapshotRepositoriesRequest.getRepositoryNames());

		return getRepositoriesRequest;
	}

	protected GetRepositoriesResponse getGetRepositoriesResponse(
		GetRepositoriesRequest getRepositoriesRequest) {

		RestHighLevelClient restHighLevelClient =
			_elasticsearchClientResolver.getRestHighLevelClient();

		SnapshotClient snapshotClient = restHighLevelClient.snapshot();

		try {
			return snapshotClient.getRepository(
				getRepositoriesRequest, RequestOptions.DEFAULT);
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	@Reference(unbind = "-")
	protected void setElasticsearchClientResolver(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GetSnapshotRepositoriesRequestExecutorImpl.class);

	private ElasticsearchClientResolver _elasticsearchClientResolver;

}