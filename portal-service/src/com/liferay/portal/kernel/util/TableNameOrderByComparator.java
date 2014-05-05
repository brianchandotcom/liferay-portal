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
 * @author José Manuel Navarro
 */
public class TableNameOrderByComparator extends OrderByComparator {

	public TableNameOrderByComparator(
		OrderByComparator decoratedComparator, String tableName) {

		super();

		_decoratedComparator = decoratedComparator;

		setTableName(tableName);
	}

	@Override
	public int compare(Object obj1, Object obj2) {
		return _decoratedComparator.compare(obj1, obj2);
	}

	public OrderByComparator getDecoratedComparator() {
		return _decoratedComparator;
	}

	@Override
	public String getOrderBy() {
		String originalOrderBy = _decoratedComparator.getOrderBy();

		if (_tableName == null) {
			return originalOrderBy;
		}

		String[] sortedFields = StringUtil.split(originalOrderBy);

		StringBundler finalOrderBy = new StringBundler(
			(3 * sortedFields.length) - 1);

		for (int i = 0; i < sortedFields.length; ++i) {
			String field = sortedFields[i];

			if (field.indexOf(CharPool.PERIOD) == -1) {
				finalOrderBy.append(_tableName);
			}

			finalOrderBy.append(StringUtil.trim(field));

			if (i < (sortedFields.length - 1)) {
				finalOrderBy.append(_ORDER_BY_SEPARATOR);
			}
		}

		return finalOrderBy.toString();
	}

	@Override
	public String[] getOrderByConditionFields() {
		return _decoratedComparator.getOrderByConditionFields();
	}

	@Override
	public Object[] getOrderByConditionValues(Object obj) {
		return _decoratedComparator.getOrderByConditionValues(obj);
	}

	@Override
	public String[] getOrderByFields() {
		return _decoratedComparator.getOrderByFields();
	}

	@Override
	public boolean isAscending() {
		return _decoratedComparator.isAscending();
	}

	@Override
	public boolean isAscending(String field) {
		return _decoratedComparator.isAscending(field);
	}

	public void setTableName(String tableName) {
		if ((tableName != null) && (tableName.length() > 0)) {
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
		return _decoratedComparator.toString();
	}

	private static final String _ORDER_BY_SEPARATOR = ", ";

	private OrderByComparator _decoratedComparator;
	private String _tableName;

}