/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.index;

import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.index.StatsIndexRequest;
import com.liferay.portal.search.engine.adapter.index.StatsIndexResponse;
import com.liferay.portal.search.index.StatsIndexInformation;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Felipe Lorenz
 */
@Component(service = StatsIndexInformation.class)
public class ElasticsearchStatsIndexInformation
	implements StatsIndexInformation {

	@Override
	public double getLargestIndexSize(String... indexNames) {
		long maxSize = 0;

		StatsIndexResponse statsIndexResponse = _searchEngineAdapter.execute(
			new StatsIndexRequest(indexNames));

		for (String indexName : indexNames) {
			long size = statsIndexResponse.getIndexSize(indexName);

			if (size > maxSize) {
				maxSize = size;
			}
		}

		return (double)maxSize / (1024 * 1024 * 1024);
	}

	@Reference
	private SearchEngineAdapter _searchEngineAdapter;

}