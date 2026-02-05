/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.stats;

import java.io.Serializable;

/**
 * @author André de Oliveira
 */
public class StatsResponse implements Serializable {

	public StatsResponse(
		long cardinality, long count, String field, double max, double mean,
		double min, long missing, double standardDeviation, double sum,
		double sumOfSquares) {

		_cardinality = cardinality;
		_count = count;
		_field = field;
		_max = max;
		_mean = mean;
		_min = min;
		_missing = missing;
		_standardDeviation = standardDeviation;
		_sum = sum;
		_sumOfSquares = sumOfSquares;
	}

	public long getCardinality() {
		return _cardinality;
	}

	public long getCount() {
		return _count;
	}

	public String getField() {
		return _field;
	}

	public double getMax() {
		return _max;
	}

	public double getMean() {
		return _mean;
	}

	public double getMin() {
		return _min;
	}

	public long getMissing() {
		return _missing;
	}

	public double getStandardDeviation() {
		return _standardDeviation;
	}

	public double getSum() {
		return _sum;
	}

	public double getSumOfSquares() {
		return _sumOfSquares;
	}

	private final long _cardinality;
	private final long _count;
	private final String _field;
	private final double _max;
	private final double _mean;
	private final double _min;
	private final long _missing;
	private final double _standardDeviation;
	private final double _sum;
	private final double _sumOfSquares;

}