/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.aggregation.bucket;

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.search.aggregation.bucket.DateHistogramAggregation;

import java.util.List;

import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.histogram.LongBounds;

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

		if (ListUtil.isNotEmpty(dateHistogramAggregation.getOrders())) {
			List<BucketOrder> bucketOrders = _orderTranslator.translate(
				dateHistogramAggregation.getOrders());

			dateHistogramAggregationBuilder.order(bucketOrders);
		}

		if ((dateHistogramAggregation.getMaxBound() != null) &&
			(dateHistogramAggregation.getMinBound() != null)) {

			LongBounds longBounds = new LongBounds(
				dateHistogramAggregation.getMinBound(),
				dateHistogramAggregation.getMaxBound());

			dateHistogramAggregationBuilder.extendedBounds(longBounds);
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