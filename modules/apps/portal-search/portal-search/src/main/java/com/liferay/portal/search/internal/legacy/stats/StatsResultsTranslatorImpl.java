/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.legacy.stats;

import com.liferay.portal.kernel.search.StatsResults;
import com.liferay.portal.search.legacy.stats.StatsResultsTranslator;
import com.liferay.portal.search.stats.StatsResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Bryan Engler
 */
@Component(service = StatsResultsTranslator.class)
public class StatsResultsTranslatorImpl implements StatsResultsTranslator {

	@Override
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

}