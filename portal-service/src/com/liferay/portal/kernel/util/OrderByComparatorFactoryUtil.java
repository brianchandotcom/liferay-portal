/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

/**
 * @author Brian Wing Shun Chan
 */
public class OrderByComparatorFactoryUtil {

	public static OrderByComparator create(
		String tableName, Object... columns) {

		return getOrderByComparatorFactory().create(tableName, columns);
	}

	public static OrderByComparatorFactory getOrderByComparatorFactory() {
		PortalRuntimePermission.checkGetBeanProperty(
			OrderByComparatorFactoryUtil.class);

		return _orderByComparatorFactory;
	}

	public void setOrderByComparatorFactory(
		OrderByComparatorFactory orderByComparatorFactory) {

		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_orderByComparatorFactory = orderByComparatorFactory;
	}

	private static OrderByComparatorFactory _orderByComparatorFactory;

}