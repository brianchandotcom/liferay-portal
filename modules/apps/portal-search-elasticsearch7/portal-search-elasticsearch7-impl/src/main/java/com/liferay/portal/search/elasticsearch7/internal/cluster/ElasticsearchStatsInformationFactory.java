/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.cluster;

import com.liferay.portal.search.cluster.StatsInformation;
import com.liferay.portal.search.cluster.StatsInformationFactory;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.cluster.StatsClusterRequest;
import com.liferay.portal.search.engine.adapter.cluster.StatsClusterResponse;
import com.liferay.portal.search.engine.adapter.index.StatsIndexRequest;
import com.liferay.portal.search.engine.adapter.index.StatsIndexResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryan Engler
 */
@Component(service = StatsInformationFactory.class)
public class ElasticsearchStatsInformationFactory
	implements StatsInformationFactory {

	@Override
	public StatsInformation getStatsInformation() {
		return getStatsInformation(null, null);
	}

	@Override
	public StatsInformation getStatsInformation(
		String[] nodeIds, String[] indexNames) {

		StatsClusterResponse statsClusterResponse =
			_searchEngineAdapter.execute(new StatsClusterRequest(nodeIds));

		StatsIndexResponse statsIndexResponse = _searchEngineAdapter.execute(
			new StatsIndexRequest(indexNames));

		return new StatsInformationImpl(
			statsClusterResponse.getAvailableSpaceInBytes(),
			statsIndexResponse.getSizeOfLargestIndexInBytes(),
			statsClusterResponse.getUsedSpaceInBytes());
	}

	@Reference
	private SearchEngineAdapter _searchEngineAdapter;

	private class StatsInformationImpl implements StatsInformation {

		public StatsInformationImpl(
			long availableSpaceInBytes, long sizeOfLargestIndexInBytes,
			long usedSpaceInBytes) {

			_availableDiskSpace = _convertToGigabytes(availableSpaceInBytes);
			_sizeOfLargestIndex = _convertToGigabytes(
				sizeOfLargestIndexInBytes);
			_usedDiskSpace = _convertToGigabytes(usedSpaceInBytes);
		}

		@Override
		public double getAvailableDiskSpace() {
			return _availableDiskSpace;
		}

		@Override
		public double getSizeOfLargestIndex() {
			return _sizeOfLargestIndex;
		}

		@Override
		public double getUsedDiskSpace() {
			return _usedDiskSpace;
		}

		private double _convertToGigabytes(long value) {
			return (double)value / (1024 * 1024 * 1024);
		}

		private final double _availableDiskSpace;
		private final double _sizeOfLargestIndex;
		private final double _usedDiskSpace;

	}

}