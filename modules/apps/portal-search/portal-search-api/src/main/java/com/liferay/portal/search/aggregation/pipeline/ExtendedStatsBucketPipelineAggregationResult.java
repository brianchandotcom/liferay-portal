/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.aggregation.pipeline;

import com.liferay.portal.search.aggregation.metrics.ExtendedStatsAggregationResult;

/**
 * @author Michael C. Han
 */
public class ExtendedStatsBucketPipelineAggregationResult
	extends ExtendedStatsAggregationResult {

	public ExtendedStatsBucketPipelineAggregationResult(
		String name, double avg, long count, double min, double max, double sum,
		double sumOfSquares, double variance, double stdDeviation) {

		super(
			name, avg, count, min, max, sum, sumOfSquares, variance,
			stdDeviation);
	}

}