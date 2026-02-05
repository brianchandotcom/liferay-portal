/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.stats;

/**
 * @author André de Oliveira
 */
public class StatsResponseBuilder {

	public StatsResponse build() {
		return new StatsResponse(
			_cardinality, _count, _field, _max, _mean, _min, _missing,
			_standardDeviation, _sum, _sumOfSquares);
	}

	public StatsResponseBuilder cardinality(long cardinality) {
		_cardinality = cardinality;

		return this;
	}

	public StatsResponseBuilder count(long count) {
		_count = count;

		return this;
	}

	public StatsResponseBuilder field(String field) {
		_field = field;

		return this;
	}

	public StatsResponseBuilder max(double max) {
		_max = max;

		return this;
	}

	public StatsResponseBuilder mean(double mean) {
		_mean = mean;

		return this;
	}

	public StatsResponseBuilder min(double min) {
		_min = min;

		return this;
	}

	public StatsResponseBuilder missing(long missing) {
		_missing = missing;

		return this;
	}

	public StatsResponseBuilder standardDeviation(double standardDeviation) {
		_standardDeviation = standardDeviation;

		return this;
	}

	public StatsResponseBuilder sum(double sum) {
		_sum = sum;

		return this;
	}

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