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

package com.liferay.portal.kernel.util;

import com.liferay.petra.string.StringPool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Carlos Sierra Andrés
 */
public class AggregateOrderByComparator<T> extends OrderByComparator<T> {

	public AggregateOrderByComparator(
		List<OrderByComparator<T>> orderByComparators) {

		_orderByComparators = new ArrayList<>(orderByComparators);
	}

	public AggregateOrderByComparator(
		OrderByComparator<T>... orderByComparators) {

		_orderByComparators = new ArrayList<>(
			Arrays.asList(orderByComparators));
	}

	@Override
	public int compare(T t1, T t2) {
		for (OrderByComparator<T> orderByComparators : _orderByComparators) {
			int value = orderByComparators.compare(t1, t2);

			if (value != 0) {
				return value;
			}
		}

		return 0;
	}

	@Override
	public String getOrderBy() {
		return StringUtil.merge(
			ListUtil.toList(_orderByComparators, OrderByComparator::getOrderBy),
			StringPool.COMMA);
	}

	@Override
	public Comparator<T> reversed() {
		return Collections.reverseOrder(this);
	}

	private final List<OrderByComparator<T>> _orderByComparators;

}