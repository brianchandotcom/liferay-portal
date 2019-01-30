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
import com.liferay.portal.search.stats.StatsResponseBuilder;

/**
 * @author André de Oliveira
 */
public class StatsResponseBuilderImpl implements StatsResponseBuilder {

	@Override
	public StatsResponse build() {
		return new StatsResponseImpl(
			_cardinality, _count, _field, _max, _mean, _min, _missing,
			_standardDeviation, _sum, _sumOfSquares);
	}

	@Override
	public StatsResponseBuilder cardinality(long cardinality) {
		_cardinality = cardinality;

		return this;
	}

	@Override
	public StatsResponseBuilder count(long count) {
		_count = count;

		return this;
	}

	@Override
	public StatsResponseBuilder field(String field) {
		_field = field;

		return this;
	}

	@Override
	public StatsResponseBuilder max(double max) {
		_max = max;

		return this;
	}

	@Override
	public StatsResponseBuilder mean(double mean) {
		_mean = mean;

		return this;
	}

	@Override
	public StatsResponseBuilder min(double min) {
		_min = min;

		return this;
	}

	@Override
	public StatsResponseBuilder missing(long missing) {
		_missing = missing;

		return this;
	}

	@Override
	public StatsResponseBuilder standardDeviation(double standardDeviation) {
		_standardDeviation = standardDeviation;

		return this;
	}

	@Override
	public StatsResponseBuilder sum(double sum) {
		_sum = sum;

		return this;
	}

	@Override
	public StatsResponseBuilder sumOfSquares(double sumOfSquares) {
		_sumOfSquares = sumOfSquares;

		return this;
	}

	private long _cardinality;
	private long _count;
	private String _field;
	private double _max;
	private double _mean;
	private double _min;
	private long _missing;
	private double _standardDeviation;
	private double _sum;
	private double _sumOfSquares;

}