/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.snapshot;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.snapshot.ElasticsearchSnapshotClient;
import co.elastic.clients.elasticsearch.snapshot.GetRepositoryRequest;
import co.elastic.clients.elasticsearch.snapshot.GetRepositoryResponse;
import co.elastic.clients.elasticsearch.snapshot.Repository;
import co.elastic.clients.elasticsearch.snapshot.SharedFileSystemRepository;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.search.elasticsearch8.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.elasticsearch8.internal.util.JsonpUtil;
import com.liferay.portal.search.engine.adapter.snapshot.GetSnapshotRepositoriesRequest;
import com.liferay.portal.search.engine.adapter.snapshot.GetSnapshotRepositoriesResponse;
import com.liferay.portal.search.engine.adapter.snapshot.SnapshotRepositoryDetails;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;

import org.elasticsearch.ElasticsearchStatusException;
import org.elasticsearch.repositories.RepositoryMissingException;

/**
 * @author Michael C. Han
 */
public class GetSnapshotRepositoriesRequestExecutor {

	public GetSnapshotRepositoriesRequestExecutor(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	public GetSnapshotRepositoriesResponse execute(
		GetSnapshotRepositoriesRequest getSnapshotRepositoriesRequest) {

		GetSnapshotRepositoriesResponse getSnapshotRepositoriesResponse =
			new GetSnapshotRepositoriesResponse();

		try {
			GetRepositoryResponse getRepositoryResponse =
				_getGetRepositoryResponse(
					_createGetRepositoryRequest(getSnapshotRepositoriesRequest),
					getSnapshotRepositoriesRequest);

			MapUtil.isNotEmptyForEach(
				getRepositoryResponse.result(),
				(name, repository) -> {
					Repository.Kind kind = repository._kind();
					SharedFileSystemRepository sharedFileSystemRepository =
						repository.fs();

					getSnapshotRepositoriesResponse.
						addSnapshotRepositoryMetadata(
							new SnapshotRepositoryDetails(
								name, kind.jsonValue(),
								JsonpUtil.toString(
									sharedFileSystemRepository.settings())));
				});
		}
		catch (RepositoryMissingException repositoryMissingException) {
			if (_log.isDebugEnabled()) {
				_log.debug(repositoryMissingException);
			}
		}

		return getSnapshotRepositoriesResponse;
	}

	private GetRepositoryRequest _createGetRepositoryRequest(
		GetSnapshotRepositoriesRequest getSnapshotRepositoriesRequest) {

		return GetRepositoryRequest.of(
			getRepositoryRequest -> getRepositoryRequest.name(
				ListUtil.fromArray(
					getSnapshotRepositoriesRequest.getRepositoryNames())));
	}

	private GetRepositoryResponse _getGetRepositoryResponse(
		GetRepositoryRequest getRepositoryRequest,
		GetSnapshotRepositoriesRequest getSnapshotRepositoriesRequest) {

		ElasticsearchClient elasticsearchClient =
			_elasticsearchClientResolver.getElasticsearchClient(
				getSnapshotRepositoriesRequest.getConnectionId(),
				getSnapshotRepositoriesRequest.isPreferLocalCluster());

		ElasticsearchSnapshotClient elasticsearchSnapshotClient =
			elasticsearchClient.snapshot();

		try {
			return elasticsearchSnapshotClient.getRepository(
				getRepositoryRequest);
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
		GetSnapshotRepositoriesRequestExecutor.class);

	private final ElasticsearchClientResolver _elasticsearchClientResolver;

}