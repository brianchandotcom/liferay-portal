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

package com.liferay.portal.search.elasticsearch7.internal.aggregation.bucket;

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.search.aggregation.AggregationTranslator;
import com.liferay.portal.search.aggregation.bucket.HistogramAggregation;
import com.liferay.portal.search.aggregation.pipeline.PipelineAggregationTranslator;
import com.liferay.portal.search.elasticsearch7.internal.aggregation.BaseFieldAggregationTranslator;

import java.util.List;

import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.PipelineAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.histogram.HistogramAggregationBuilder;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(service = HistogramAggregationTranslator.class)
public class HistogramAggregationTranslatorImpl
	implements HistogramAggregationTranslator {

	@Override
	public HistogramAggregationBuilder translate(
		HistogramAggregation histogramAggregation,
		AggregationTranslator<AggregationBuilder> aggregationTranslator,
		PipelineAggregationTranslator<PipelineAggregationBuilder>
			pipelineAggregationTranslator) {

		HistogramAggregationBuilder histogramAggregationBuilder =
			_baseFieldAggregationTranslator.translate(
				baseMetricsAggregation -> AggregationBuilders.histogram(
					baseMetricsAggregation.getName()),
				histogramAggregation, aggregationTranslator,
				pipelineAggregationTranslator);

		if (!ListUtil.isEmpty(histogramAggregation.getOrders())) {
			List<BucketOrder> bucketOrders = _orderTranslator.translate(
				histogramAggregation.getOrders());

			histogramAggregationBuilder.order(bucketOrders);
		}

		if ((histogramAggregation.getMaxBound() != null) &&
			(histogramAggregation.getMinBound() != null)) {

			histogramAggregationBuilder.extendedBounds(
				histogramAggregation.getMinBound(),
				histogramAggregation.getMaxBound());
		}

		if (histogramAggregation.getMinDocCount() != null) {
			histogramAggregationBuilder.minDocCount(
				histogramAggregation.getMinDocCount());
		}

		if (histogramAggregation.getInterval() != null) {
			histogramAggregationBuilder.interval(
				histogramAggregation.getInterval());
		}

		if (histogramAggregation.getOffset() != null) {
			histogramAggregationBuilder.offset(
				histogramAggregation.getOffset());
		}

		return histogramAggregationBuilder;
	}

	private final BaseFieldAggregationTranslator
		_baseFieldAggregationTranslator = new BaseFieldAggregationTranslator();
	private final OrderTranslator _orderTranslator = new OrderTranslator();

}