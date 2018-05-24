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

import com.liferay.portal.search.elasticsearch6.internal.connection.ElasticsearchConnectionManager;
import com.liferay.portal.search.engine.adapter.cluster.ClusterRequestExecutor;

/**
 * @author Dylan Rebelak
 */
public class ClusterRequestExecutorFixture {

	public ClusterRequestExecutorFixture(
		ElasticsearchConnectionManager elasticsearchConnectionManager) {

		_elasticsearchConnectionManager = elasticsearchConnectionManager;
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
				elasticsearchConnectionManager =
					_elasticsearchConnectionManager;
			}
		};
	}

	protected StateClusterRequestExecutor createStateClusterRequestExecutor() {
		return new StateClusterRequestExecutorImpl() {
			{
				elasticsearchConnectionManager =
					_elasticsearchConnectionManager;
			}
		};
	}

	protected StatsClusterRequestExecutor createStatsClusterRequestExecutor() {
		return new StatsClusterRequestExecutorImpl() {
			{
				clusterHealthStatusTranslator = _clusterHealthStatusTranslator;

				elasticsearchConnectionManager =
					_elasticsearchConnectionManager;
			}
		};
	}

	private final ClusterHealthStatusTranslator _clusterHealthStatusTranslator =
		new ClusterHealthStatusTranslatorImpl();
	private final ElasticsearchConnectionManager
		_elasticsearchConnectionManager;

}