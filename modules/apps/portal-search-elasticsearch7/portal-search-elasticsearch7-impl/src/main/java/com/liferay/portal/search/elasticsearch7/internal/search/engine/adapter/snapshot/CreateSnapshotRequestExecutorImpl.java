/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.snapshot;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.engine.adapter.snapshot.SnapshotDetails;

import java.io.IOException;

import org.elasticsearch.action.admin.cluster.snapshots.create.CreateSnapshotRequest;
import org.elasticsearch.action.admin.cluster.snapshots.create.CreateSnapshotResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.SnapshotClient;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(service = CreateSnapshotRequestExecutor.class)
public class CreateSnapshotRequestExecutorImpl
	implements CreateSnapshotRequestExecutor {

	@Override
	public
		com.liferay.portal.search.engine.adapter.snapshot.CreateSnapshotResponse
			execute(
				com.liferay.portal.search.engine.adapter.snapshot.
					CreateSnapshotRequest createSnapshotRequest) {

		CreateSnapshotRequest elasticsearchCreateSnapshotRequest =
			createCreateSnapshotRequest(createSnapshotRequest);

		CreateSnapshotResponse elasticsearchCreateSnapshotResponse =
			_getCreateSnapshotResponse(
				elasticsearchCreateSnapshotRequest, createSnapshotRequest);

		SnapshotDetails snapshotDetails = SnapshotInfoConverter.convert(
			elasticsearchCreateSnapshotResponse.getSnapshotInfo());

		return new com.liferay.portal.search.engine.adapter.snapshot.
			CreateSnapshotResponse(snapshotDetails);
	}

	protected CreateSnapshotRequest createCreateSnapshotRequest(
		com.liferay.portal.search.engine.adapter.snapshot.CreateSnapshotRequest
			createSnapshotRequest) {

		CreateSnapshotRequest elasticsearchCreateSnapshotRequest =
			new CreateSnapshotRequest();

		if (ArrayUtil.isNotEmpty(createSnapshotRequest.getIndexNames())) {
			elasticsearchCreateSnapshotRequest.indices(
				createSnapshotRequest.getIndexNames());
		}

		elasticsearchCreateSnapshotRequest.repository(
			createSnapshotRequest.getRepositoryName());
		elasticsearchCreateSnapshotRequest.snapshot(
			createSnapshotRequest.getSnapshotName());
		elasticsearchCreateSnapshotRequest.waitForCompletion(
			createSnapshotRequest.isWaitForCompletion());

		return elasticsearchCreateSnapshotRequest;
	}

	private CreateSnapshotResponse _getCreateSnapshotResponse(
		CreateSnapshotRequest elasticsearchCreateSnapshotRequest,
		com.liferay.portal.search.engine.adapter.snapshot.CreateSnapshotRequest
			createSnapshotRequest) {

		RestHighLevelClient restHighLevelClient =
			_elasticsearchClientResolver.getRestHighLevelClient(
				createSnapshotRequest.getConnectionId(),
				createSnapshotRequest.isPreferLocalCluster());

		SnapshotClient snapshotClient = restHighLevelClient.snapshot();

		try {
			return snapshotClient.create(
				elasticsearchCreateSnapshotRequest, RequestOptions.DEFAULT);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	@Reference
	private ElasticsearchClientResolver _elasticsearchClientResolver;

}