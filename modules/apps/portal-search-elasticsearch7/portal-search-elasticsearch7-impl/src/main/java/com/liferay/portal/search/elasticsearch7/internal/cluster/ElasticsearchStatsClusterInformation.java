/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.cluster;

import com.liferay.portal.search.cluster.StatsClusterInformation;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.cluster.StatsClusterRequest;
import com.liferay.portal.search.engine.adapter.cluster.StatsClusterResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Felipe Lorenz
 */
@Component(service = StatsClusterInformation.class)
public class ElasticsearchStatsClusterInformation
	implements StatsClusterInformation {

	@Override
	public double getAvailableDiskSpace(String[] nodeIds) {
		StatsClusterResponse statsClusterResponse = _getStatsClusterResponse(
			nodeIds);

		return _convertToGigabytes(
			statsClusterResponse.getAvailableSpaceInBytes());
	}

	@Override
	public double getUsedDiskSpace(String[] nodeIds) {
		StatsClusterResponse statsClusterResponse = _getStatsClusterResponse(
			nodeIds);

		return _convertToGigabytes(statsClusterResponse.getUsedSpaceInBytes());
	}

	private double _convertToGigabytes(long value) {
		return (double)value / (1024 * 1024 * 1024);
	}

	private StatsClusterResponse _getStatsClusterResponse(String[] nodeIds) {
		StatsClusterRequest statsClusterRequest = new StatsClusterRequest(
			nodeIds);

		return _searchEngineAdapter.execute(statsClusterRequest);
	}

	@Reference
	private SearchEngineAdapter _searchEngineAdapter;

}