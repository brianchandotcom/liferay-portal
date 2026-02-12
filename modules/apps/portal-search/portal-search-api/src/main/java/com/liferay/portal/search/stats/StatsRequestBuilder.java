/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.stats;

/**
 * @author André de Oliveira
 */
public class StatsRequestBuilder {

	public StatsRequest build() {
		return new StatsRequest(
			_cardinality, _count, _field, _max, _mean, _min, _missing,
			_standardDeviation, _sum, _sumOfSquares);
	}

	public StatsRequestBuilder cardinality(boolean cardinality) {
		_cardinality = cardinality;

		return this;
	}

	public StatsRequestBuilder count(boolean count) {
		_count = count;

		return this;
	}

	public StatsRequestBuilder field(String field) {
		_field = field;

		return this;
	}

	public StatsRequestBuilder max(boolean max) {
		_max = max;

		return this;
	}

	public StatsRequestBuilder mean(boolean mean) {
		_mean = mean;

		return this;
	}

	public StatsRequestBuilder min(boolean min) {
		_min = min;

		return this;
	}

	public StatsRequestBuilder missing(boolean missing) {
		_missing = missing;

		return this;
	}

	public StatsRequestBuilder standardDeviation(boolean standardDeviation) {
		_standardDeviation = standardDeviation;

		return this;
	}

	public StatsRequestBuilder sum(boolean sum) {
		_sum = sum;

		return this;
	}

	public StatsRequestBuilder sumOfSquares(boolean sumOfSquares) {
		_sumOfSquares = sumOfSquares;

		return this;
	}

	private boolean _cardinality;
	private boolean _count;
	private String _field;
	private boolean _max;
	private boolean _mean;
	private boolean _min;
	private boolean _missing;
	private boolean _standardDeviation;
	private boolean _sum;
	private boolean _sumOfSquares;

}