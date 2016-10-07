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

/**
 * @author Shuyang Zhou
 */
public abstract class OrderByComparatorAdapter<T, V>
	extends OrderByComparator<T> {

	public OrderByComparatorAdapter(OrderByComparator<V> orderByComparator) {
		_orderByComparator = orderByComparator;
	}

	public abstract V adapt(T t);

	@Override
	public int compare(T o1, T o2) {
		return _orderByComparator.compare(adapt(o1), adapt(o2));
	}

	public OrderByComparator<V> getAdaptedOrderByComparator() {
		return _orderByComparator;
	}

	@Override
	public String getOrderBy() {
		return _orderByComparator.getOrderBy();
	}

	@Override
	public String[] getOrderByConditionFields() {
		return _orderByComparator.getOrderByConditionFields();
	}

	@Override
	public Object[] getOrderByConditionValues(Object obj) {
		return _orderByComparator.getOrderByConditionValues(obj);
	}

	@Override
	public String[] getOrderByFields() {
		return _orderByComparator.getOrderByFields();
	}

	@Override
	public boolean isAscending() {
		return _orderByComparator.isAscending();
	}

	@Override
	public boolean isAscending(String field) {
		return _orderByComparator.isAscending(field);
	}

	@Override
	public String toString() {
		return _orderByComparator.toString();
	}

	private final OrderByComparator<V> _orderByComparator;

}