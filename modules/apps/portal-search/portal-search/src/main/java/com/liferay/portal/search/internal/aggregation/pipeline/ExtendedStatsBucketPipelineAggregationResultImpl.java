/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.aggregation.pipeline;

import com.liferay.portal.search.aggregation.pipeline.ExtendedStatsBucketPipelineAggregationResult;
import com.liferay.portal.search.internal.aggregation.metrics.ExtendedStatsAggregationResultImpl;

/**
 * @author Michael C. Han
 */
public class ExtendedStatsBucketPipelineAggregationResultImpl
	extends ExtendedStatsAggregationResultImpl
	implements ExtendedStatsBucketPipelineAggregationResult {

	public ExtendedStatsBucketPipelineAggregationResultImpl(
		String name, double avg, long count, double min, double max, double sum,
		double sumOfSquares, double variance, double stdDeviation) {

		super(
			name, avg, count, min, max, sum, sumOfSquares, variance,
			stdDeviation);
	}

}