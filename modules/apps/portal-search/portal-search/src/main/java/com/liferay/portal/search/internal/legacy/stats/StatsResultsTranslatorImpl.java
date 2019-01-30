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

package com.liferay.portal.search.internal.legacy.stats;

import com.liferay.portal.kernel.search.StatsResults;
import com.liferay.portal.search.legacy.stats.StatsResultsTranslator;
import com.liferay.portal.search.stats.StatsResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Bryan Engler
 */
@Component(immediate = true, service = StatsResultsTranslator.class)
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