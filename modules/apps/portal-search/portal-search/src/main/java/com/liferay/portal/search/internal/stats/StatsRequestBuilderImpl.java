/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.stats;

import com.liferay.portal.search.stats.StatsRequest;
import com.liferay.portal.search.stats.StatsRequestBuilder;

/**
 * @author André de Oliveira
 */
public class StatsRequestBuilderImpl implements StatsRequestBuilder {

	@Override
	public StatsRequest build() {
		return new StatsRequestImpl(
			_cardinality, _count, _field, _max, _mean, _min, _missing,
			_standardDeviation, _sum, _sumOfSquares);
	}

	@Override
	public StatsRequestBuilderImpl cardinality(boolean cardinality) {
		_cardinality = cardinality;

		return this;
	}

	@Override
	public StatsRequestBuilderImpl count(boolean count) {
		_count = count;

		return this;
	}

	@Override
	public StatsRequestBuilderImpl field(String field) {
		_field = field;

		return this;
	}

	@Override
	public StatsRequestBuilderImpl max(boolean max) {
		_max = max;

		return this;
	}

	@Override
	public StatsRequestBuilderImpl mean(boolean mean) {
		_mean = mean;

		return this;
	}

	@Override
	public StatsRequestBuilderImpl min(boolean min) {
		_min = min;

		return this;
	}

	@Override
	public StatsRequestBuilderImpl missing(boolean missing) {
		_missing = missing;

		return this;
	}

	@Override
	public StatsRequestBuilderImpl standardDeviation(
		boolean standardDeviation) {

		_standardDeviation = standardDeviation;

		return this;
	}

	@Override
	public StatsRequestBuilderImpl sum(boolean sum) {
		_sum = sum;

		return this;
	}

	@Override
	public StatsRequestBuilderImpl sumOfSquares(boolean sumOfSquares) {
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