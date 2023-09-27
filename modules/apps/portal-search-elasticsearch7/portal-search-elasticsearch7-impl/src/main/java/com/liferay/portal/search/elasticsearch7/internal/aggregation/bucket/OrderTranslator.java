/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.aggregation.bucket;

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