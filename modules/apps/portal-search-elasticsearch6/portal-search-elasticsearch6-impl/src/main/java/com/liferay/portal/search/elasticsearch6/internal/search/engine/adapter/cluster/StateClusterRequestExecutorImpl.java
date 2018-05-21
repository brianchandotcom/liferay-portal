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

package com.liferay.portal.search.elasticsearch6.internal.search.engine.adapter.cluster;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.search.elasticsearch6.internal.connection.ElasticsearchConnectionManager;
import com.liferay.portal.search.engine.adapter.cluster.StateClusterRequest;
import com.liferay.portal.search.engine.adapter.cluster.StateClusterResponse;

import java.io.IOException;

import org.elasticsearch.action.admin.cluster.state.ClusterStateRequestBuilder;
import org.elasticsearch.action.admin.cluster.state.ClusterStateResponse;
import org.elasticsearch.client.ClusterAdminClient;
import org.elasticsearch.cluster.ClusterState;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.xpack.watcher.watch.Payload;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Dylan Rebelak
 */
@Component(immediate = true, service = StateClusterRequestExecutor.class)
public class StateClusterRequestExecutorImpl
	implements StateClusterRequestExecutor {

	@Override
	public StateClusterResponse execute(
		StateClusterRequest stateClusterRequest) {

		ClusterStateRequestBuilder clusterStateRequestBuilder =
			createClusterStateRequestBuilder(stateClusterRequest);

		ClusterStateResponse clusterStateResponse =
			clusterStateRequestBuilder.get();

		try {
			ClusterState clusterState = clusterStateResponse.getState();

			XContentBuilder xContentBuilder = XContentFactory.jsonBuilder();

			xContentBuilder.startObject();

			xContentBuilder = clusterState.toXContent(
				xContentBuilder, Payload.XContent.EMPTY_PARAMS);

			xContentBuilder.endObject();

			return new StateClusterResponse(xContentBuilder.string());
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	protected ClusterStateRequestBuilder createClusterStateRequestBuilder(
		StateClusterRequest stateClusterRequest) {

		ClusterAdminClient clusterAdminClient =
			elasticsearchConnectionManager.getClusterAdminClient();

		ClusterStateRequestBuilder clusterStateRequestBuilder =
			clusterAdminClient.prepareState();

		clusterStateRequestBuilder.setIndices(
			stateClusterRequest.getIndexNames());

		return clusterStateRequestBuilder;
	}

	@Reference
	protected ElasticsearchConnectionManager elasticsearchConnectionManager;

}