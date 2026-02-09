/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.aggregation.bucket;

import com.liferay.portal.search.aggregation.bucket.DateRangeAggregation;
import com.liferay.portal.search.elasticsearch7.internal.aggregation.BaseFieldAggregationTranslator;

import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.range.DateRangeAggregationBuilder;

/**
 * @author Michael C. Han
 */
public class DateRangeAggregationTranslator extends RangeAggregationTranslator {

	public DateRangeAggregationBuilder translate(
		DateRangeAggregation dateRangeAggregation) {

		DateRangeAggregationBuilder dateRangeAggregationBuilder =
			_baseFieldAggregationTranslator.translate(
				baseMetricsAggregation -> AggregationBuilders.dateRange(
					baseMetricsAggregation.getName()),
				dateRangeAggregation);

		populateRangeAggregationBuilder(
			dateRangeAggregation, dateRangeAggregationBuilder);

		return dateRangeAggregationBuilder;
	}

	private final BaseFieldAggregationTranslator
		_baseFieldAggregationTranslator = new BaseFieldAggregationTranslator();

}