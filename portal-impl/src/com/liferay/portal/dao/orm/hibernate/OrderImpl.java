/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.dao.orm.hibernate;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.Order;

/**
 * @author Brian Wing Shun Chan
 */
public class OrderImpl implements Order {

	public OrderImpl(org.hibernate.criterion.Order order) {
		_order = order;
	}

	public org.hibernate.criterion.Order getWrappedOrder() {
		return _order;
	}

	@Override
	public String toString() {
		return StringBundler.concat("{_order=", _order, "}");
	}

	private final org.hibernate.criterion.Order _order;

}