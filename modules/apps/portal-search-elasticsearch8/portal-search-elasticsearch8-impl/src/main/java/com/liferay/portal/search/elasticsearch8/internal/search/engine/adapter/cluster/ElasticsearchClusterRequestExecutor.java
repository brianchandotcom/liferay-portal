/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.cluster;

import com.liferay.portal.search.elasticsearch8.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.engine.adapter.cluster.ClusterRequest;
import com.liferay.portal.search.engine.adapter.cluster.ClusterRequestExecutor;
import com.liferay.portal.search.engine.adapter.cluster.ClusterResponse;
import com.liferay.portal.search.engine.adapter.cluster.HealthClusterRequest;
import com.liferay.portal.search.engine.adapter.cluster.HealthClusterResponse;
import com.liferay.portal.search.engine.adapter.cluster.StateClusterRequest;
import com.liferay.portal.search.engine.adapter.cluster.StateClusterResponse;
import com.liferay.portal.search.engine.adapter.cluster.StatsClusterRequest;
import com.liferay.portal.search.engine.adapter.cluster.StatsClusterResponse;
import com.liferay.portal.search.engine.adapter.cluster.UpdateSettingsClusterRequest;
import com.liferay.portal.search.engine.adapter.cluster.UpdateSettingsClusterResponse;

/**
 * @author Dylan Rebelak
 */
public class ElasticsearchClusterRequestExecutor
	implements ClusterRequestExecutor {

	public ElasticsearchClusterRequestExecutor(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_healthClusterRequestExecutor = new HealthClusterRequestExecutor(
			elasticsearchClientResolver);
		_stateClusterRequestExecutor = new StateClusterRequestExecutor(
			elasticsearchClientResolver);
		_statsClusterRequestExecutor = new StatsClusterRequestExecutor(
			elasticsearchClientResolver);
		_updateSettingsClusterRequestExecutor =
			new UpdateSettingsClusterRequestExecutor(
				elasticsearchClientResolver);
	}

	@Override
	public <T extends ClusterResponse> T execute(
		ClusterRequest<T> clusterRequest) {

		return clusterRequest.accept(this);
	}

	@Override
	public HealthClusterResponse executeClusterRequest(
		HealthClusterRequest healthClusterRequest) {

		return _healthClusterRequestExecutor.execute(healthClusterRequest);
	}

	@Override
	public StateClusterResponse executeClusterRequest(
		StateClusterRequest stateClusterRequest) {

		return _stateClusterRequestExecutor.execute(stateClusterRequest);
	}

	@Override
	public StatsClusterResponse executeClusterRequest(
		StatsClusterRequest statsClusterRequest) {

		return _statsClusterRequestExecutor.execute(statsClusterRequest);
	}

	@Override
	public UpdateSettingsClusterResponse executeClusterRequest(
		UpdateSettingsClusterRequest updateSettingsClusterRequest) {

		return _updateSettingsClusterRequestExecutor.execute(
			updateSettingsClusterRequest);
	}

	private final HealthClusterRequestExecutor _healthClusterRequestExecutor;
	private final StateClusterRequestExecutor _stateClusterRequestExecutor;
	private final StatsClusterRequestExecutor _statsClusterRequestExecutor;
	private final UpdateSettingsClusterRequestExecutor
		_updateSettingsClusterRequestExecutor;

}