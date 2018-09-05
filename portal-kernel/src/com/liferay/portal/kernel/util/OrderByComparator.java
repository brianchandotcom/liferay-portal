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
import com.liferay.portal.kernel.bean.BeanPropertiesUtil;

import java.io.Serializable;

import java.util.Comparator;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public abstract class OrderByComparator<T>
	implements Comparator<T>, Serializable {

	public String getOrderBy() {
		return null;
	}

	public String[] getOrderByConditionFields() {
		return getOrderByFields();
	}

	public Object[] getOrderByConditionValues(Object obj) {
		String[] fields = getOrderByConditionFields();

		Object[] values = new Object[fields.length];

		for (int i = 0; i < fields.length; i++) {
			values[i] = BeanPropertiesUtil.getObject(obj, fields[i]);
		}

		return values;
	}

	public String[] getOrderByFields() {
		String orderBy = getOrderBy();

		if (orderBy == null) {
			return null;
		}

		String[] parts = StringUtil.split(orderBy);

		String[] fields = new String[parts.length];

		for (int i = 0; i < parts.length; i++) {
			String part = parts[i];

			int x = part.indexOf(CharPool.PERIOD);

			int y = part.indexOf(CharPool.SPACE, x);

			if (y == -1) {
				y = part.length();
			}

			fields[i] = part.substring(x + 1, y);
		}

		return fields;
	}

	public boolean isAscending() {
		String orderBy = StringUtil.toUpperCase(getOrderBy());

		if ((orderBy == null) || orderBy.endsWith(_ORDER_BY_DESC)) {
			return false;
		}

		return true;
	}

	public boolean isAscending(String field) {
		return isAscending();
	}

	@Override
	public String toString() {
		String orderBy = getOrderBy();

		if (orderBy == null) {
			return super.toString();
		}

		return orderBy;
	}

	private static final String _ORDER_BY_DESC = " DESC";

}