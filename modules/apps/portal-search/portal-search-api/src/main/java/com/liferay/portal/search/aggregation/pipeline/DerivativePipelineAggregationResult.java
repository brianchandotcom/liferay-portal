/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.aggregation.pipeline;

import com.liferay.portal.search.aggregation.AggregationResult;

/**
 * @author Michael C. Han
 */
public class DerivativePipelineAggregationResult extends AggregationResult {

	public DerivativePipelineAggregationResult(
		String name, double normalizedValue) {

		super(name);

		_normalizedValue = normalizedValue;
	}

	public double getNormalizedValue() {
		return _normalizedValue;
	}

	private final double _normalizedValue;

}