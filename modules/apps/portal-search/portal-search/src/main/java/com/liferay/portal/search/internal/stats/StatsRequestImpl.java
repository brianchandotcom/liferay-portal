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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.search.stats.StatsRequest;

import java.io.Serializable;

/**
 * @author Michael C. Han
 * @author André de Oliveira
 */
public class StatsRequestImpl implements Serializable, StatsRequest {

	public StatsRequestImpl(
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

	@Override
	public String getField() {
		return _field;
	}

	@Override
	public boolean isCardinality() {
		return _cardinality;
	}

	@Override
	public boolean isCount() {
		return _count;
	}

	@Override
	public boolean isMax() {
		return _max;
	}

	@Override
	public boolean isMean() {
		return _mean;
	}

	@Override
	public boolean isMin() {
		return _min;
	}

	@Override
	public boolean isMissing() {
		return _missing;
	}

	@Override
	public boolean isStandardDeviation() {
		return _standardDeviation;
	}

	@Override
	public boolean isSum() {
		return _sum;
	}

	@Override
	public boolean isSumOfSquares() {
		return _sumOfSquares;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("{_cardinality=");
		sb.append(_cardinality);
		sb.append(", _count=");
		sb.append(_count);
		sb.append(", field=");
		sb.append(_field);
		sb.append(", max=");
		sb.append(_max);
		sb.append(", mean=");
		sb.append(_mean);
		sb.append(", min=");
		sb.append(_min);
		sb.append(", missing=");
		sb.append(_missing);
		sb.append(", standardDeviation=");
		sb.append(_standardDeviation);
		sb.append(", sum=");
		sb.append(_sum);
		sb.append(", sumOfSquares=");
		sb.append(_sumOfSquares);
		sb.append("}");

		return sb.toString();
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