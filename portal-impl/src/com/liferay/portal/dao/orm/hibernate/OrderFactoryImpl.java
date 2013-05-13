/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.dao.orm.hibernate;

import com.liferay.portal.kernel.dao.orm.Order;
import com.liferay.portal.kernel.dao.orm.OrderFactory;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;

/**
 * @author Brian Wing Shun Chan
 */
@DoPrivileged
public class OrderFactoryImpl implements OrderFactory {

	@Override
	public Order asc(String propertyName) {
		return new OrderImpl(org.hibernate.criterion.Order.asc(propertyName));
	}

	@Override
	public Order desc(String propertyName) {
		return new OrderImpl(org.hibernate.criterion.Order.desc(propertyName));
	}

}