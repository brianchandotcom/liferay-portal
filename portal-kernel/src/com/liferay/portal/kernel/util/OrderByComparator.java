/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

import com.liferay.petra.sql.dsl.query.sort.OrderByInfo;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.bean.BeanPropertiesUtil;

import java.io.Serializable;

import java.util.Comparator;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public abstract class OrderByComparator<T>
	implements Comparator<T>, OrderByInfo, Serializable {

	public String getOrderBy() {
		return null;
	}

	public String[] getOrderByConditionFields() {
		return getOrderByFields();
	}

	public Object[] getOrderByConditionValues(Object object) {
		String[] fields = getOrderByConditionFields();

		Object[] values = new Object[fields.length];

		for (int i = 0; i < fields.length; i++) {
			values[i] = BeanPropertiesUtil.getObject(object, fields[i]);
		}

		return values;
	}

	@Override
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

	@Override
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