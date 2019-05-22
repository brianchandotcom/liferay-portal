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

package com.liferay.portal.search.test.util.indexing;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.aggregation.Aggregations;
import com.liferay.portal.search.aggregation.bucket.HistogramAggregation;
import com.liferay.portal.search.aggregation.metrics.SumAggregation;
import com.liferay.portal.search.internal.aggregation.AggregationsImpl;

/**
 * @author Wade Cao
 */
public class AggregationFixture {

	public HistogramAggregation getDefaultHistogramAggregation() {
		HistogramAggregation histogramAggregation = _aggregations.histogram(
			"histogram", Field.PRIORITY);

		histogramAggregation.setInterval(5.0);
		histogramAggregation.setMinDocCount(1L);

		SumAggregation sumAggregation = _aggregations.sum(
			"sum", Field.PRIORITY);

		histogramAggregation.addChildAggregation(sumAggregation);

		return histogramAggregation;
	}

	private final Aggregations _aggregations = new AggregationsImpl();

}