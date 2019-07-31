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

package com.liferay.portal.search.elasticsearch6.internal.aggregation.bucket;

import com.liferay.portal.search.aggregation.bucket.Order;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.search.aggregations.BucketOrder;

/**
 * @author Michael C. Han
 */
public class OrderTranslator {

	public List<BucketOrder> translate(List<Order> orders) {
		List<BucketOrder> bucketOrders = new ArrayList<>(orders.size());

		orders.forEach(
			order -> {
				BucketOrder bucketOrder = _convert(order);

				bucketOrders.add(bucketOrder);
			});

		return bucketOrders;
	}

	private BucketOrder _convert(Order order) {
		if (Order.COUNT_METRIC_NAME.equals(order.getMetricName())) {
			return BucketOrder.count(order.isAscending());
		}
		else if (Order.KEY_METRIC_NAME.equals(order.getMetricName())) {
			return BucketOrder.key(order.isAscending());
		}
		else if (order.getMetricName() == null) {
			return BucketOrder.aggregation(
				order.getPath(), order.isAscending());
		}

		return BucketOrder.aggregation(
			order.getPath(), order.getMetricName(), order.isAscending());
	}

}