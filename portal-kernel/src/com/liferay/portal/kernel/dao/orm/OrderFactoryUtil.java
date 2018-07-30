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

package com.liferay.portal.kernel.dao.orm;

import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Brian Wing Shun Chan
 */
public class OrderFactoryUtil {

	public static void addOrderByComparator(
		DynamicQuery dynamicQuery, OrderByComparator<?> obc) {

		if (obc == null) {
			return;
		}

		String[] orderByFields = obc.getOrderByFields();

		for (String orderByField : orderByFields) {
			if (obc.isAscending(orderByField)) {
				dynamicQuery.addOrder(asc(orderByField));
			}
			else {
				dynamicQuery.addOrder(desc(orderByField));
			}
		}
	}

	public static Order asc(String propertyName) {
		return getOrderFactory().asc(propertyName);
	}

	public static Order desc(String propertyName) {
		return getOrderFactory().desc(propertyName);
	}

	public static OrderFactory getOrderFactory() {
		return _orderFactory;
	}

	public void setOrderFactory(OrderFactory orderFactory) {
		_orderFactory = orderFactory;
	}

	private static OrderFactory _orderFactory;

}