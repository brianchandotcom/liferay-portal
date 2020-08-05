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

package com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.cluster;

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

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Dylan Rebelak
 */
@Component(
	immediate = true, property = "search.engine.impl=Elasticsearch",
	service = ClusterRequestExecutor.class
)
public class ElasticsearchClusterRequestExecutor
	implements ClusterRequestExecutor {

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

	@Reference(unbind = "-")
	protected void setHealthClusterRequestExecutor(
		HealthClusterRequestExecutor healthClusterRequestExecutor) {

		_healthClusterRequestExecutor = healthClusterRequestExecutor;
	}

	@Reference(unbind = "-")
	protected void setStateClusterRequestExecutor(
		StateClusterRequestExecutor stateClusterRequestExecutor) {

		_stateClusterRequestExecutor = stateClusterRequestExecutor;
	}

	@Reference(unbind = "-")
	protected void setStatsClusterRequestExecutor(
		StatsClusterRequestExecutor statsClusterRequestExecutor) {

		_statsClusterRequestExecutor = statsClusterRequestExecutor;
	}

	@Reference(unbind = "-")
	protected void setUpdateSettingsClusterRequestExecutor(
		UpdateSettingsClusterRequestExecutor
			updateSettingsClusterRequestExecutor) {

		_updateSettingsClusterRequestExecutor =
			updateSettingsClusterRequestExecutor;
	}

	private HealthClusterRequestExecutor _healthClusterRequestExecutor;
	private StateClusterRequestExecutor _stateClusterRequestExecutor;
	private StatsClusterRequestExecutor _statsClusterRequestExecutor;
	private UpdateSettingsClusterRequestExecutor
		_updateSettingsClusterRequestExecutor;

}