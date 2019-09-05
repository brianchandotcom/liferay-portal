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

package com.liferay.portal.search.internal.stats;

import com.liferay.portal.search.stats.StatsResponse;

import java.io.Serializable;

/**
 * @author Michael C. Han
 */
public class StatsResponseImpl implements Serializable, StatsResponse {

	public StatsResponseImpl(
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

	@Override
	public long getCardinality() {
		return _cardinality;
	}

	@Override
	public long getCount() {
		return _count;
	}

	@Override
	public String getField() {
		return _field;
	}

	@Override
	public double getMax() {
		return _max;
	}

	@Override
	public double getMean() {
		return _mean;
	}

	@Override
	public double getMin() {
		return _min;
	}

	@Override
	public long getMissing() {
		return _missing;
	}

	@Override
	public double getStandardDeviation() {
		return _standardDeviation;
	}

	@Override
	public double getSum() {
		return _sum;
	}

	@Override
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