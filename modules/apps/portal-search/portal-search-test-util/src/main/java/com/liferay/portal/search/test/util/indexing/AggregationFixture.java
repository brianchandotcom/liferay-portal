/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		histogramAggregation.setMinDocCount(0L);

		SumAggregation sumAggregation = _aggregations.sum(
			"sum", Field.PRIORITY);

		histogramAggregation.addChildAggregation(sumAggregation);

		return histogramAggregation;
	}

	private final Aggregations _aggregations = new AggregationsImpl();

}