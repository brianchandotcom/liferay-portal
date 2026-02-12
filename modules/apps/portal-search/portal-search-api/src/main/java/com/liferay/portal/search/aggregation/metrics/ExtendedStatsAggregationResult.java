/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.aggregation.metrics;

/**
 * @author Michael C. Han
 */
public class ExtendedStatsAggregationResult extends StatsAggregationResult {

	public ExtendedStatsAggregationResult(
		String name, double avg, long count, double min, double max, double sum,
		double sumOfSquares, double variance, double stdDeviation) {

		super(name, avg, count, min, max, sum);

		_sumOfSquares = sumOfSquares;
		_variance = variance;
		_stdDeviation = stdDeviation;
	}

	public double getStdDeviation() {
		return _stdDeviation;
	}

	public double getSumOfSquares() {
		return _sumOfSquares;
	}

	public double getVariance() {
		return _variance;
	}

	public void setStdDeviation(double stdDeviation) {
		_stdDeviation = stdDeviation;
	}

	public void setSumOfSquares(double sumOfSquares) {
		_sumOfSquares = sumOfSquares;
	}

	public void setVariance(double variance) {
		_variance = variance;
	}

	private double _stdDeviation;
	private double _sumOfSquares;
	private double _variance;

}