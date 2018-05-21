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

package com.liferay.portal.search.elasticsearch6.internal.adapter.cluster;

import com.liferay.portal.search.elasticsearch6.internal.connection.ElasticsearchConnectionManager;

/**
 * @author Dylan Rebelak
 */
public class ClusterRequestExecutorFixture {

	public ClusterRequestExecutorFixture(
		ElasticsearchConnectionManager elasticsearchConnectionManager) {

		_elasticsearchConnectionManager = elasticsearchConnectionManager;
	}

	public ElasticsearchClusterRequestExecutor createExecutor() {
		ElasticsearchClusterRequestExecutor clusterRequestExecutor =
			new ElasticsearchClusterRequestExecutor();

		HealthClusterRequestExecutorImpl healthClusterRequestExecutorImpl =
			new HealthClusterRequestExecutorImpl();

		healthClusterRequestExecutorImpl.elasticsearchConnectionManager =
			_elasticsearchConnectionManager;
		clusterRequestExecutor.healthClusterRequestExecutor =
			healthClusterRequestExecutorImpl;

		StateClusterRequestExecutorImpl stateClusterRequestExecutorImpl =
			new StateClusterRequestExecutorImpl();

		stateClusterRequestExecutorImpl.elasticsearchConnectionManager =
			_elasticsearchConnectionManager;
		clusterRequestExecutor.stateClusterRequestExecutor =
			stateClusterRequestExecutorImpl;

		StatsClusterRequestExecutorImpl statsClusterRequestExecutorImpl =
			new StatsClusterRequestExecutorImpl();

		statsClusterRequestExecutorImpl.elasticsearchConnectionManager =
			_elasticsearchConnectionManager;
		clusterRequestExecutor.statsClusterRequestExecutor =
			statsClusterRequestExecutorImpl;

		return clusterRequestExecutor;
	}

	private final ElasticsearchConnectionManager
		_elasticsearchConnectionManager;

}