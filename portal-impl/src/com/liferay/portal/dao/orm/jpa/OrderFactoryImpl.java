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

package com.liferay.portal.dao.orm.jpa;

import com.liferay.portal.kernel.dao.orm.Order;
import com.liferay.portal.kernel.dao.orm.OrderFactory;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;

/**
 * @author Prashant Dighe
 * @author Brian Wing Shun Chan
 */
@DoPrivileged
public class OrderFactoryImpl implements OrderFactory {

	@Override
	public Order asc(String propertyName) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Order desc(String propertyName) {
		throw new UnsupportedOperationException();
	}

}