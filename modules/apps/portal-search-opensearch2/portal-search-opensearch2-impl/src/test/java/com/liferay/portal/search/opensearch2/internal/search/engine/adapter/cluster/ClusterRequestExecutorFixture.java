/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.search.engine.adapter.cluster;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.engine.adapter.cluster.ClusterRequestExecutor;
import com.liferay.portal.search.opensearch2.internal.connection.OpenSearchConnectionManager;

/**
 * @author Dylan Rebelak
 */
public class ClusterRequestExecutorFixture {

	public ClusterRequestExecutor getClusterRequestExecutor() {
		return _clusterRequestExecutor;
	}

	public void setUp() {
		_clusterRequestExecutor = new OpenSearchClusterRequestExecutor();

		ReflectionTestUtil.setFieldValue(
			_clusterRequestExecutor, "_healthClusterRequestExecutor",
			_createHealthClusterRequestExecutor(_openSearchConnectionManager));
		ReflectionTestUtil.setFieldValue(
			_clusterRequestExecutor, "_stateClusterRequestExecutor",
			new StateClusterRequestExecutor(_openSearchConnectionManager));
		ReflectionTestUtil.setFieldValue(
			_clusterRequestExecutor, "_statsClusterRequestExecutor",
			_createStatsClusterRequestExecutor(_openSearchConnectionManager));
	}

	protected void setOpenSearchConnectionManager(
		OpenSearchConnectionManager openSearchConnectionManager) {

		_openSearchConnectionManager = openSearchConnectionManager;
	}

	private HealthClusterRequestExecutor _createHealthClusterRequestExecutor(
		OpenSearchConnectionManager openSearchConnectionManager) {

		HealthClusterRequestExecutor healthClusterRequestExecutor =
			new HealthClusterRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			healthClusterRequestExecutor, "_openSearchConnectionManager",
			openSearchConnectionManager);

		return healthClusterRequestExecutor;
	}

	private StatsClusterRequestExecutor _createStatsClusterRequestExecutor(
		OpenSearchConnectionManager openSearchConnectionManager) {

		StatsClusterRequestExecutor statsClusterRequestExecutor =
			new StatsClusterRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			statsClusterRequestExecutor, "_openSearchConnectionManager",
			openSearchConnectionManager);

		return statsClusterRequestExecutor;
	}

	private ClusterRequestExecutor _clusterRequestExecutor;
	private OpenSearchConnectionManager _openSearchConnectionManager;

}