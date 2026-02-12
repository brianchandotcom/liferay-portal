/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.aggregation.metrics;

import com.liferay.portal.search.aggregation.AggregationResult;

/**
 * @author Michael C. Han
 */
public class StatsAggregationResult extends AggregationResult {

	public StatsAggregationResult(
		String name, double avg, long count, double min, double max,
		double sum) {

		super(name);

		_avg = avg;
		_count = count;
		_min = min;
		_max = max;
		_sum = sum;
	}

	public double getAvg() {
		return _avg;
	}

	public long getCount() {
		return _count;
	}

	public double getMax() {
		return _max;
	}

	public double getMin() {
		return _min;
	}

	public double getSum() {
		return _sum;
	}

	private final double _avg;
	private final long _count;
	private final double _max;
	private final double _min;
	private final double _sum;

}