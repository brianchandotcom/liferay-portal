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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Carlos Sierra Andrés
 */
public class AggregateOrderByComparator<T> extends OrderByComparator<T> {

	public AggregateOrderByComparator(List<OrderByComparator<T>> comparators) {
		_comparators = new ArrayList<>(comparators);
	}

	public AggregateOrderByComparator(OrderByComparator<T> ... comparators) {
		_comparators = new ArrayList<>(Arrays.asList(comparators));
	}

	@Override
	public int compare(T o1, T o2) {
		for (OrderByComparator<T> comparator : _comparators) {
			int compare = comparator.compare(o1, o2);

			if (compare != 0) {
				return compare;
			}
		}

		return 0;
	}

	@Override
	public String getOrderBy() {
		return StringUtil.merge(
			ListUtil.toList(_comparators, OrderByComparator::getOrderBy), ",");
	}

	@Override
	public Comparator<T> reversed() {
		return Collections.reverseOrder(this);
	}

	private final List<OrderByComparator<T>> _comparators;

}