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

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

/**
 * @author José Manuel Navarro
 */
public class TableNameOrderByComparator<T> extends OrderByComparator<T> {

	public TableNameOrderByComparator(
		OrderByComparator<T> orderByComparator, String tableName) {

		_orderByComparator = orderByComparator;

		setTableName(tableName);
	}

	@Override
	public int compare(T obj1, T obj2) {
		return _orderByComparator.compare(obj1, obj2);
	}

	@Override
	public String getOrderBy() {
		String orderBy = _orderByComparator.getOrderBy();

		if (_tableName == null) {
			return orderBy;
		}

		String[] columnNames = StringUtil.split(orderBy);

		StringBundler sb = new StringBundler((3 * columnNames.length) - 1);

		for (int i = 0; i < columnNames.length; ++i) {
			String columnName = columnNames[i];

			if (columnName.indexOf(CharPool.PERIOD) != -1) {
				columnName = StringUtil.split(columnName, CharPool.PERIOD)[1];
			}

			sb.append(_tableName);
			sb.append(StringUtil.trim(columnName));

			if (i < (columnNames.length - 1)) {
				sb.append(StringPool.COMMA_AND_SPACE);
			}
		}

		return sb.toString();
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

	public OrderByComparator<T> getWrappedOrderByComparator() {
		return _orderByComparator;
	}

	@Override
	public boolean isAscending() {
		return _orderByComparator.isAscending();
	}

	@Override
	public boolean isAscending(String field) {
		return _orderByComparator.isAscending(field);
	}

	public void setTableName(String tableName) {
		if (Validator.isNotNull(tableName)) {
			if (tableName.endsWith(StringPool.PERIOD)) {
				_tableName = tableName;
			}
			else {
				_tableName = tableName + CharPool.PERIOD;
			}
		}
		else {
			_tableName = null;
		}
	}

	@Override
	public String toString() {
		return _orderByComparator.toString();
	}

	private final OrderByComparator<T> _orderByComparator;
	private String _tableName;

}