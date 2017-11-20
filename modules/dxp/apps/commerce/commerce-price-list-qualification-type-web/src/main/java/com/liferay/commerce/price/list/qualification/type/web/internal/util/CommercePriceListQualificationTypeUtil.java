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

package com.liferay.commerce.price.list.qualification.type.web.internal.util;

import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.comparator.OrganizationNameComparator;
import com.liferay.portal.kernel.util.comparator.RoleNameComparator;
import com.liferay.portal.kernel.util.comparator.UserFirstNameComparator;
import com.liferay.portal.kernel.util.comparator.UserGroupNameComparator;

/**
 * @author Alessio Antonio Rendina
 */
public class CommercePriceListQualificationTypeUtil {

	public static OrderByComparator<Organization>
		getOrganizationOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<Organization> orderByComparator = null;

		if (orderByCol.equals("name")) {
			orderByComparator = new OrganizationNameComparator(orderByAsc);
		}

		return orderByComparator;
	}

	public static OrderByComparator<Role> getRoleOrderByComparator(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<Role> orderByComparator = null;

		if (orderByCol.equals("name")) {
			orderByComparator = new RoleNameComparator(orderByAsc);
		}

		return orderByComparator;
	}

	public static OrderByComparator<UserGroup> getUserGroupOrderByComparator(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<UserGroup> orderByComparator = null;

		if (orderByCol.equals("name")) {
			orderByComparator = new UserGroupNameComparator(orderByAsc);
		}

		return orderByComparator;
	}

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