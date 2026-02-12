/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.legacy.stats;

import com.liferay.portal.kernel.search.Stats;
import com.liferay.portal.search.stats.StatsRequestBuilder;

/**
 * @author Bryan Engler
 */
public class StatsRequestBuilderFactory {

	/**
	 * Creates a new stats request builder from a legacy {@code Stats} object.
	 *
	 * @param  stats the legacy {@code Stats} object to be converted
	 * @return the converted stats request builder
	 */
	public static StatsRequestBuilder getStatsRequestBuilder(Stats stats) {
		StatsRequestBuilder statsRequestBuilder = new StatsRequestBuilder();

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