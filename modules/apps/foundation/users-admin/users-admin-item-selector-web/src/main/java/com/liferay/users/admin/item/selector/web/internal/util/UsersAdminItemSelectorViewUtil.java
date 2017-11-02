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

package com.liferay.users.admin.item.selector.web.internal.util;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.comparator.UserFirstNameComparator;

/**
 * @author Alessio Antonio Rendina
 */
public class UsersAdminItemSelectorViewUtil {

	public static OrderByComparator<User> getUserOrderByComparator(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<User> orderByComparator = null;

		if (orderByCol.equals("name")) {
			orderByComparator = new UserFirstNameComparator(orderByAsc);
		}

		return orderByComparator;
	}

}