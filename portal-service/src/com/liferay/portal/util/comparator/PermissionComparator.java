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
import com.liferay.portal.model.Permission;

/**
 * @author Alexander Chow
 */
public class PermissionComparator extends OrderByComparator {

	public static final String ORDER_BY_DESC = "Permission_.permissionId DESC";

	public static final String[] ORDER_BY_FIELDS = {"permissionId"};

	@Override
	public int compare(Object obj1, Object obj2) {
		Permission perm1 = (Permission)obj1;
		Permission perm2 = (Permission)obj2;

		long permissionId1 = perm1.getPermissionId();
		long permissionId2 = perm2.getPermissionId();

		if (permissionId1 > permissionId2) {
			return -1;
		}
		else if (permissionId1 < permissionId2) {
			return 1;
		}
		else {
			return 0;
		}
	}

	@Override
	public String getOrderBy() {
		return ORDER_BY_DESC;
	}

	@Override
	public String[] getOrderByFields() {
		return ORDER_BY_FIELDS;
	}

	@Override
	public boolean isAscending() {
		return false;
	}

}