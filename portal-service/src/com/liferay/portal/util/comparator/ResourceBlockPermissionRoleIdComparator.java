/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.util.comparator;

import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.ResourceBlockPermission;

/**
 * @author Connor McKay
 */
public class ResourceBlockPermissionRoleIdComparator extends OrderByComparator {

	public static String ORDER_BY_ASC = "ResourceBlockPermission.roleId ASC";

	public static String[] ORDER_BY_FIELDS = {"roleId"};

	@Override
	public int compare(Object obj1, Object obj2) {
		ResourceBlockPermission resourceBlockPermission1 =
			(ResourceBlockPermission)obj1;
		ResourceBlockPermission resourceBlockPermission2 =
			(ResourceBlockPermission)obj2;

		long roleId1 =
			resourceBlockPermission1.getRoleId();
		long roleId2 =
			resourceBlockPermission2.getRoleId();

		if (roleId1 < roleId2) {
			return -1;
		}
		else if (roleId1 > roleId2) {
			return 1;
		}
		else {
			return 0;
		}
	}

	@Override
	public String getOrderBy() {
		return ORDER_BY_ASC;
	}

	@Override
	public String[] getOrderByFields() {
		return ORDER_BY_FIELDS;
	}

	@Override
	public boolean isAscending() {
		return true;
	}

}