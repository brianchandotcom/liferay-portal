/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.snapshot;

import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.engine.adapter.snapshot.SnapshotDetails;

import java.io.IOException;

import java.util.List;

import org.elasticsearch.action.admin.cluster.snapshots.get.GetSnapshotsRequest;
import org.elasticsearch.action.admin.cluster.snapshots.get.GetSnapshotsResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.SnapshotClient;
import org.elasticsearch.snapshots.SnapshotInfo;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(service = GetSnapshotsRequestExecutor.class)
public class GetSnapshotsRequestExecutorImpl
	implements GetSnapshotsRequestExecutor {

	@Override
	public
		com.liferay.portal.search.engine.adapter.snapshot.GetSnapshotsResponse
			execute(
				com.liferay.portal.search.engine.adapter.snapshot.
					GetSnapshotsRequest getSnapshotsRequest) {

		GetSnapshotsRequest elasticsearchGetSnapshotsRequest =
			createGetSnapshotsRequest(getSnapshotsRequest);

		GetSnapshotsResponse elasticsearchGetSnapshotsResponse =
			_getGetSnapshotsResponse(
				elasticsearchGetSnapshotsRequest, getSnapshotsRequest);

		com.liferay.portal.search.engine.adapter.snapshot.GetSnapshotsResponse
			getSnapshotsResponse =
				new com.liferay.portal.search.engine.adapter.snapshot.
					GetSnapshotsResponse();

		List<SnapshotInfo> snapshotInfos =
			elasticsearchGetSnapshotsResponse.getSnapshots();

		snapshotInfos.forEach(
			snapshotInfo -> {
				SnapshotDetails snapshotDetails = SnapshotInfoConverter.convert(
					snapshotInfo);

				getSnapshotsResponse.addSnapshotInfo(snapshotDetails);
			});

		return getSnapshotsResponse;
	}

	protected GetSnapshotsRequest createGetSnapshotsRequest(
		com.liferay.portal.search.engine.adapter.snapshot.GetSnapshotsRequest
			getSnapshotsRequest) {

		GetSnapshotsRequest elasticsearchGetSnapshotsRequest =
			new GetSnapshotsRequest();

		elasticsearchGetSnapshotsRequest.ignoreUnavailable(
			getSnapshotsRequest.isIgnoreUnavailable());
		elasticsearchGetSnapshotsRequest.repository(
			getSnapshotsRequest.getRepositoryName());
		elasticsearchGetSnapshotsRequest.snapshots(
			getSnapshotsRequest.getSnapshotNames());
		elasticsearchGetSnapshotsRequest.verbose(
			getSnapshotsRequest.isVerbose());

		return elasticsearchGetSnapshotsRequest;
	}

	private GetSnapshotsResponse _getGetSnapshotsResponse(
		GetSnapshotsRequest elasticsearchGetSnapshotsRequest,
		com.liferay.portal.search.engine.adapter.snapshot.GetSnapshotsRequest
			getSnapshotsRequest) {

		RestHighLevelClient restHighLevelClient =
			_elasticsearchClientResolver.getRestHighLevelClient(
				getSnapshotsRequest.getConnectionId(),
				getSnapshotsRequest.isPreferLocalCluster());

		SnapshotClient snapshotClient = restHighLevelClient.snapshot();

		try {
			return snapshotClient.get(
				elasticsearchGetSnapshotsRequest, RequestOptions.DEFAULT);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	@Reference
	private ElasticsearchClientResolver _elasticsearchClientResolver;

}