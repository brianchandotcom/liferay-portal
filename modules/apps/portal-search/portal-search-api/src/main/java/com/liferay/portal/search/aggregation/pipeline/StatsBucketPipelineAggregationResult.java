/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.aggregation.pipeline;

import com.liferay.portal.search.aggregation.metrics.StatsAggregationResult;

/**
 * @author Michael C. Han
 */
public class StatsBucketPipelineAggregationResult
	extends StatsAggregationResult {

	public StatsBucketPipelineAggregationResult(
		String name, double avg, long count, double min, double max,
		double sum) {

		super(name, avg, count, min, max, sum);
	}

}