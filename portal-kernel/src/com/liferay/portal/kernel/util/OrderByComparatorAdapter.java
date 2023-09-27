/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	public Object[] getOrderByConditionValues(Object object) {
		return _orderByComparator.getOrderByConditionValues(object);
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