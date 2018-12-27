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

import com.liferay.portal.search.elasticsearch6.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.engine.adapter.cluster.ClusterRequestExecutor;

/**
 * @author Dylan Rebelak
 */
public class ClusterRequestExecutorFixture {

	public ClusterRequestExecutorFixture(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	public ClusterRequestExecutor createExecutor() {
		return new ElasticsearchClusterRequestExecutor() {
			{
				healthClusterRequestExecutor =
					createHealthClusterRequestExecutor();
				stateClusterRequestExecutor =
					createStateClusterRequestExecutor();
				statsClusterRequestExecutor =
					createStatsClusterRequestExecutor();
			}
		};
	}

	protected HealthClusterRequestExecutor
		createHealthClusterRequestExecutor() {

		return new HealthClusterRequestExecutorImpl() {
			{
				clusterHealthStatusTranslator = _clusterHealthStatusTranslator;
				elasticsearchClientResolver = _elasticsearchClientResolver;
			}
		};
	}

	protected StateClusterRequestExecutor createStateClusterRequestExecutor() {
		return new StateClusterRequestExecutorImpl() {
			{
				elasticsearchClientResolver = _elasticsearchClientResolver;
			}
		};
	}

	protected StatsClusterRequestExecutor createStatsClusterRequestExecutor() {
		return new StatsClusterRequestExecutorImpl() {
			{
				clusterHealthStatusTranslator = _clusterHealthStatusTranslator;
				elasticsearchClientResolver = _elasticsearchClientResolver;
			}
		};
	}

	private final ClusterHealthStatusTranslator _clusterHealthStatusTranslator =
		new ClusterHealthStatusTranslatorImpl();
	private final ElasticsearchClientResolver _elasticsearchClientResolver;

}