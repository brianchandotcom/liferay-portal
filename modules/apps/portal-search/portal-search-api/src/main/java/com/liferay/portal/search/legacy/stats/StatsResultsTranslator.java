/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.legacy.stats;

import com.liferay.portal.kernel.search.StatsResults;
import com.liferay.portal.search.stats.StatsResponse;

/**
 * @author Bryan Engler
 */
public class StatsResultsTranslator {

	public static final StatsResultsTranslator INSTANCE =
		new StatsResultsTranslator();

	/**
	 * Creates a legacy {@code StatsResults} object from a {@code StatsResponse}
	 * object.
	 *
	 * @param  statsResponse the {@code StatsResponse} object to be converted
	 * @return the converted legacy {@code StatsResults} object
	 */
	public StatsResults translate(StatsResponse statsResponse) {
		StatsResults statsResults = new StatsResults(statsResponse.getField());

		statsResults.setCount(statsResponse.getCount());
		statsResults.setMax(statsResponse.getMax());
		statsResults.setMean(statsResponse.getMean());
		statsResults.setMin(statsResponse.getMin());
		statsResults.setMissing((int)statsResponse.getMissing());
		statsResults.setStandardDeviation(statsResponse.getStandardDeviation());
		statsResults.setSum(statsResponse.getSum());
		statsResults.setSumOfSquares(statsResponse.getSumOfSquares());

		return statsResults;
	}

	private StatsResultsTranslator() {
	}

}