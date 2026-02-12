/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.stats;

import com.liferay.petra.string.StringBundler;

import java.io.Serializable;

/**
 * @author André de Oliveira
 */
public class StatsRequest implements Serializable {

	public StatsRequest(
		boolean cardinality, boolean count, String field, boolean max,
		boolean mean, boolean min, boolean missing, boolean standardDeviation,
		boolean sum, boolean sumOfSquares) {

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

	public String getField() {
		return _field;
	}

	public boolean isCardinality() {
		return _cardinality;
	}

	public boolean isCount() {
		return _count;
	}

	public boolean isMax() {
		return _max;
	}

	public boolean isMean() {
		return _mean;
	}

	public boolean isMin() {
		return _min;
	}

	public boolean isMissing() {
		return _missing;
	}

	public boolean isStandardDeviation() {
		return _standardDeviation;
	}

	public boolean isSum() {
		return _sum;
	}

	public boolean isSumOfSquares() {
		return _sumOfSquares;
	}

	@Override
	public String toString() {
		return StringBundler.concat(
			"{_cardinality=", _cardinality, ", _count=", _count, ", field=",
			_field, ", max=", _max, ", mean=", _mean, ", min=", _min,
			", missing=", _missing, ", standardDeviation=", _standardDeviation,
			", sum=", _sum, ", sumOfSquares=", _sumOfSquares, "}");
	}

	private final boolean _cardinality;
	private final boolean _count;
	private final String _field;
	private final boolean _max;
	private final boolean _mean;
	private final boolean _min;
	private final boolean _missing;
	private final boolean _standardDeviation;
	private final boolean _sum;
	private final boolean _sumOfSquares;

}