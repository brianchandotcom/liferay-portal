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
import com.liferay.portal.search.aggregation.bucket.DateHistogramAggregation;

import java.util.List;

import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.histogram.ExtendedBounds;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(service = DateHistogramAggregationTranslator.class)
public class DateHistogramAggregationTranslatorImpl
	implements DateHistogramAggregationTranslator {

	@Override
	public DateHistogramAggregationBuilder translate(
		DateHistogramAggregation dateHistogramAggregation) {

		DateHistogramAggregationBuilder dateHistogramAggregationBuilder =
			AggregationBuilders.dateHistogram(
				dateHistogramAggregation.getName());

		if (!ListUtil.isEmpty(dateHistogramAggregation.getOrders())) {
			List<BucketOrder> bucketOrders = _orderTranslator.translate(
				dateHistogramAggregation.getOrders());

			dateHistogramAggregationBuilder.order(bucketOrders);
		}

		if ((dateHistogramAggregation.getMaxBound() != null) &&
			(dateHistogramAggregation.getMinBound() != null)) {

			ExtendedBounds extendedBounds = new ExtendedBounds(
				dateHistogramAggregation.getMinBound(),
				dateHistogramAggregation.getMaxBound());

			dateHistogramAggregationBuilder.extendedBounds(extendedBounds);
		}

		if (dateHistogramAggregation.getMinDocCount() != null) {
			dateHistogramAggregationBuilder.minDocCount(
				dateHistogramAggregation.getMinDocCount());
		}

		if (dateHistogramAggregation.getDateHistogramInterval() != null) {
			dateHistogramAggregationBuilder.dateHistogramInterval(
				new DateHistogramInterval(
					dateHistogramAggregation.getDateHistogramInterval()));
		}

		if (dateHistogramAggregation.getInterval() != null) {
			dateHistogramAggregationBuilder.interval(
				dateHistogramAggregation.getInterval());
		}

		if (dateHistogramAggregation.getOffset() != null) {
			dateHistogramAggregationBuilder.offset(
				dateHistogramAggregation.getOffset());
		}

		return dateHistogramAggregationBuilder;
	}

	private final OrderTranslator _orderTranslator = new OrderTranslator();

}