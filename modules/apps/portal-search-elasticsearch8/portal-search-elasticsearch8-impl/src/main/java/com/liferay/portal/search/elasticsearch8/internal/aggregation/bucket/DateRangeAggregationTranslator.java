/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.aggregation.bucket;

import com.liferay.portal.search.aggregation.AggregationTranslator;
import com.liferay.portal.search.aggregation.bucket.DateRangeAggregation;
import com.liferay.portal.search.aggregation.pipeline.PipelineAggregationTranslator;
import com.liferay.portal.search.elasticsearch8.internal.aggregation.BaseFieldAggregationTranslator;

import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.PipelineAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.range.DateRangeAggregationBuilder;

/**
 * @author Michael C. Han
 */
public class DateRangeAggregationTranslator
	extends RangeAggregationTranslatorImpl {

	public DateRangeAggregationBuilder translate(
		DateRangeAggregation dateRangeAggregation,
		AggregationTranslator<AggregationBuilder> aggregationTranslator,
		PipelineAggregationTranslator<PipelineAggregationBuilder>
			pipelineAggregationTranslator) {

		DateRangeAggregationBuilder dateRangeAggregationBuilder =
			_baseFieldAggregationTranslator.translate(
				baseMetricsAggregation -> AggregationBuilders.dateRange(
					baseMetricsAggregation.getName()),
				dateRangeAggregation, aggregationTranslator,
				pipelineAggregationTranslator);

		populateRangeAggregationBuilder(
			dateRangeAggregation, dateRangeAggregationBuilder);

		return dateRangeAggregationBuilder;
	}

	private final BaseFieldAggregationTranslator
		_baseFieldAggregationTranslator = new BaseFieldAggregationTranslator();

}