/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.aggregation.metrics;

import com.liferay.portal.search.aggregation.metrics.PercentilesAggregationResult;
import com.liferay.portal.search.internal.aggregation.BaseAggregationResult;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Michael C. Han
 */
public class PercentilesAggregationResultImpl
	extends BaseAggregationResult implements PercentilesAggregationResult {

	public PercentilesAggregationResultImpl(String name) {
		super(name);
	}

	@Override
	public void addPercentile(double percent, double percentile) {
		_percentiles.put(percent, percentile);
	}

	@Override
	public Map<Double, Double> getPercentiles() {
		return Collections.unmodifiableMap(_percentiles);
	}

	private final Map<Double, Double> _percentiles = new HashMap<>();

}