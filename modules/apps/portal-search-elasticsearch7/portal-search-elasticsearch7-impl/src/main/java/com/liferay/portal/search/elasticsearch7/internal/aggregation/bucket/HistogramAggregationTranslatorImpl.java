/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		if (ListUtil.isNotEmpty(histogramAggregation.getOrders())) {
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