/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.aggregation.metrics;

import com.liferay.portal.search.aggregation.metrics.ExtendedStatsAggregationResult;

/**
 * @author Michael C. Han
 */
public class ExtendedStatsAggregationResultImpl
	extends StatsAggregationResultImpl
	implements ExtendedStatsAggregationResult {

	public ExtendedStatsAggregationResultImpl(
		String name, double avg, long count, double min, double max, double sum,
		double sumOfSquares, double variance, double stdDeviation) {

		super(name, avg, count, min, max, sum);

		_sumOfSquares = sumOfSquares;
		_variance = variance;
		_stdDeviation = stdDeviation;
	}

	@Override
	public double getStdDeviation() {
		return _stdDeviation;
	}

	@Override
	public double getSumOfSquares() {
		return _sumOfSquares;
	}

	@Override
	public double getVariance() {
		return _variance;
	}

	@Override
	public void setStdDeviation(double stdDeviation) {
		_stdDeviation = stdDeviation;
	}

	@Override
	public void setSumOfSquares(double sumOfSquares) {
		_sumOfSquares = sumOfSquares;
	}

	@Override
	public void setVariance(double variance) {
		_variance = variance;
	}

	private double _stdDeviation;
	private double _sumOfSquares;
	private double _variance;

}