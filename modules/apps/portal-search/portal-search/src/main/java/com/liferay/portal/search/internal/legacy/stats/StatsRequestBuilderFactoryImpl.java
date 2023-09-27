/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.legacy.stats;

import com.liferay.portal.kernel.search.Stats;
import com.liferay.portal.search.internal.stats.StatsRequestBuilderImpl;
import com.liferay.portal.search.legacy.stats.StatsRequestBuilderFactory;
import com.liferay.portal.search.stats.StatsRequestBuilder;

import org.osgi.service.component.annotations.Component;

/**
 * @author Bryan Engler
 */
@Component(service = StatsRequestBuilderFactory.class)
public class StatsRequestBuilderFactoryImpl
	implements StatsRequestBuilderFactory {

	@Override
	public StatsRequestBuilder getStatsRequestBuilder(Stats stats) {
		StatsRequestBuilder statsRequestBuilder = new StatsRequestBuilderImpl();

		return statsRequestBuilder.count(
			stats.isCount()
		).field(
			stats.getField()
		).max(
			stats.isMax()
		).mean(
			stats.isMean()
		).min(
			stats.isMin()
		).missing(
			stats.isMissing()
		).standardDeviation(
			stats.isStandardDeviation()
		).sum(
			stats.isSum()
		).sumOfSquares(
			stats.isSumOfSquares()
		);
	}

}