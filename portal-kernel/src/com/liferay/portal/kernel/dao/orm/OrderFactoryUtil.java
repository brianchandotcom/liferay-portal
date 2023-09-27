/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.dao.orm;

import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Brian Wing Shun Chan
 */
public class OrderFactoryUtil {

	public static void addOrderByComparator(
		DynamicQuery dynamicQuery, OrderByComparator<?> orderByComparator) {

		if (orderByComparator == null) {
			return;
		}

		String[] orderByFields = orderByComparator.getOrderByFields();

		for (String orderByField : orderByFields) {
			if (orderByComparator.isAscending(orderByField)) {
				dynamicQuery.addOrder(asc(orderByField));
			}
			else {
				dynamicQuery.addOrder(desc(orderByField));
			}
		}
	}

	public static Order asc(String propertyName) {
		return _orderFactory.asc(propertyName);
	}

	public static Order desc(String propertyName) {
		return _orderFactory.desc(propertyName);
	}

	public static OrderFactory getOrderFactory() {
		return _orderFactory;
	}

	public void setOrderFactory(OrderFactory orderFactory) {
		_orderFactory = orderFactory;
	}

	private static OrderFactory _orderFactory;

}