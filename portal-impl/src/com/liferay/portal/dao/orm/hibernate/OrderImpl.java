/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.dao.orm.hibernate;

import com.liferay.portal.kernel.dao.orm.Order;

/**
 * @author Brian Wing Shun Chan
 */
public class OrderImpl implements Order {

	public OrderImpl(boolean ascending, String propertyName) {
		_ascending = ascending;
		_propertyName = propertyName;
	}

	public String getPropertyName() {
		return _propertyName;
	}

	public boolean isAscending() {
		return _ascending;
	}

	@Override
	public String toString() {
		if (_ascending) {
			return _propertyName + " asc";
		}

		return _propertyName + " desc";
	}

	private final boolean _ascending;
	private final String _propertyName;

}