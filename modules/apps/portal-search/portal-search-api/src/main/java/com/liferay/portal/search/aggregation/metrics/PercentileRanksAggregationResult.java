/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.aggregation.metrics;

import com.liferay.portal.search.aggregation.AggregationResult;

import java.util.Map;

/**
 * @author Michael C. Han
 */
public abstract class PercentileRanksAggregationResult
	extends AggregationResult {

	public PercentileRanksAggregationResult(String name) {
		super(name);
	}

	public abstract void addPercentile(double value, double percent);

	public abstract Map<Double, Double> getPercentiles();

}