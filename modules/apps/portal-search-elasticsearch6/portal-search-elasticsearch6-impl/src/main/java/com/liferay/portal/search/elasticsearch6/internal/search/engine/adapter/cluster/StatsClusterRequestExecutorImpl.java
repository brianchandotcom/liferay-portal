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
import com.liferay.portal.search.elasticsearch6.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.engine.adapter.cluster.StatsClusterRequest;
import com.liferay.portal.search.engine.adapter.cluster.StatsClusterResponse;

import java.io.IOException;

import org.elasticsearch.action.admin.cluster.stats.ClusterStatsAction;
import org.elasticsearch.action.admin.cluster.stats.ClusterStatsRequestBuilder;
import org.elasticsearch.action.admin.cluster.stats.ClusterStatsResponse;
import org.elasticsearch.cluster.health.ClusterHealthStatus;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.xcontent.ToXContent;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Dylan Rebelak
 */
@Component(immediate = true, service = StatsClusterRequestExecutor.class)
public class StatsClusterRequestExecutorImpl
	implements StatsClusterRequestExecutor {

	@Override
	public StatsClusterResponse execute(
		StatsClusterRequest statsClusterRequest) {

		ClusterStatsRequestBuilder clusterStatsRequestBuilder =
			createClusterStatsRequestBuilder(statsClusterRequest);

		ClusterStatsResponse clusterStatsResponse =
			clusterStatsRequestBuilder.get();

		try {
			XContentBuilder xContentBuilder = XContentFactory.jsonBuilder();

			xContentBuilder.startObject();

			xContentBuilder = clusterStatsResponse.toXContent(
				xContentBuilder, ToXContent.EMPTY_PARAMS);

			xContentBuilder.endObject();

			ClusterHealthStatus clusterHealthStatus =
				clusterStatsResponse.getStatus();

			StatsClusterResponse statsClusterResponse =
				new StatsClusterResponse(
					clusterHealthStatusTranslator.translate(
						clusterHealthStatus),
					Strings.toString(xContentBuilder));

			return statsClusterResponse;
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	protected ClusterStatsRequestBuilder createClusterStatsRequestBuilder(
		StatsClusterRequest statsClusterRequest) {

		ClusterStatsRequestBuilder clusterStatsRequestBuilder =
			ClusterStatsAction.INSTANCE.newRequestBuilder(
				elasticsearchClientResolver.getClient());

		return clusterStatsRequestBuilder;
	}

	@Reference
	protected ClusterHealthStatusTranslator clusterHealthStatusTranslator;

	@Reference
	protected ElasticsearchClientResolver elasticsearchClientResolver;

}