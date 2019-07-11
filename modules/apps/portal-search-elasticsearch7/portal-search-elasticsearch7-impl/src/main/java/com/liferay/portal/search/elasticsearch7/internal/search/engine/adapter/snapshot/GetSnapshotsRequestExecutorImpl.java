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

import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.engine.adapter.snapshot.GetSnapshotsRequest;
import com.liferay.portal.search.engine.adapter.snapshot.GetSnapshotsResponse;
import com.liferay.portal.search.engine.adapter.snapshot.SnapshotDetails;

import java.util.List;

import org.elasticsearch.action.admin.cluster.snapshots.get.GetSnapshotsAction;
import org.elasticsearch.action.admin.cluster.snapshots.get.GetSnapshotsRequestBuilder;
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
	public GetSnapshotsResponse execute(
		GetSnapshotsRequest getSnapshotsRequest) {

		GetSnapshotsRequestBuilder getSnapshotsRequestBuilder =
			createGetSnapshotsRequest(getSnapshotsRequest);

		org.elasticsearch.action.admin.cluster.snapshots.get.
			GetSnapshotsResponse elasticsearchGetSnapshotsResponse =
				getSnapshotsRequestBuilder.get();

		GetSnapshotsResponse getSnapshotsResponse = new GetSnapshotsResponse();

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

	protected GetSnapshotsRequestBuilder createGetSnapshotsRequest(
		GetSnapshotsRequest getSnapshotsRequest) {

		GetSnapshotsRequestBuilder getSnapshotsRequestBuilder =
			new GetSnapshotsRequestBuilder(
				_elasticsearchClientResolver.getClient(),
				GetSnapshotsAction.INSTANCE);

		getSnapshotsRequestBuilder.setIgnoreUnavailable(
			getSnapshotsRequest.isIgnoreUnavailable());
		getSnapshotsRequestBuilder.setRepository(
			getSnapshotsRequest.getRepositoryName());
		getSnapshotsRequestBuilder.setSnapshots(
			getSnapshotsRequest.getSnapshotNames());
		getSnapshotsRequestBuilder.setVerbose(getSnapshotsRequest.isVerbose());

		return getSnapshotsRequestBuilder;
	}

	@Reference(unbind = "-")
	protected void setElasticsearchClientResolver(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	private ElasticsearchClientResolver _elasticsearchClientResolver;

}