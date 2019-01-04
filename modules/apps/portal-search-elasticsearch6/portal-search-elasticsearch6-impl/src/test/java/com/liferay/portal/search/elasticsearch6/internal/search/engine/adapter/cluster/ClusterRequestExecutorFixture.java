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

	public ClusterRequestExecutor getClusterRequestExecutor() {
		return _clusterRequestExecutor;
	}

	public void setUp() {
		ClusterHealthStatusTranslator clusterHealthStatusTranslator =
			new ClusterHealthStatusTranslatorImpl();

		_clusterRequestExecutor = new ElasticsearchClusterRequestExecutor() {
			{
				healthClusterRequestExecutor =
					createHealthClusterRequestExecutor(
						clusterHealthStatusTranslator,
						elasticsearchClientResolver);
				stateClusterRequestExecutor = createStateClusterRequestExecutor(
					elasticsearchClientResolver);
				statsClusterRequestExecutor = createStatsClusterRequestExecutor(
					clusterHealthStatusTranslator, elasticsearchClientResolver);
			}
		};
	}

	protected static HealthClusterRequestExecutor
		createHealthClusterRequestExecutor(
			ClusterHealthStatusTranslator clusterHealthStatusTranslator1,
			ElasticsearchClientResolver elasticsearchClientResolver1) {

		return new HealthClusterRequestExecutorImpl() {
			{
				clusterHealthStatusTranslator = clusterHealthStatusTranslator1;
				elasticsearchClientResolver = elasticsearchClientResolver1;
			}
		};
	}

	protected static StateClusterRequestExecutor
		createStateClusterRequestExecutor(
			ElasticsearchClientResolver elasticsearchClientResolver1) {

		return new StateClusterRequestExecutorImpl() {
			{
				elasticsearchClientResolver = elasticsearchClientResolver1;
			}
		};
	}

	protected static StatsClusterRequestExecutor
		createStatsClusterRequestExecutor(
			ClusterHealthStatusTranslator clusterHealthStatusTranslator1,
			ElasticsearchClientResolver elasticsearchClientResolver1) {

		return new StatsClusterRequestExecutorImpl() {
			{
				clusterHealthStatusTranslator = clusterHealthStatusTranslator1;
				elasticsearchClientResolver = elasticsearchClientResolver1;
			}
		};
	}

	protected ElasticsearchClientResolver elasticsearchClientResolver;

	private ClusterRequestExecutor _clusterRequestExecutor;

}