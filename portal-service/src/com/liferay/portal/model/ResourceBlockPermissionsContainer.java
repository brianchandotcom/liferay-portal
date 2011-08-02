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

package com.liferay.portal.model;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Manages a list of the roles with permission to access a resource block and
 * the actions they can perform.
 *
 * @author Connor McKay
 */
public class ResourceBlockPermissionsContainer {

	public void addPermission(long roleId, long actionIdsLong) {
		actionIdsLong |= getActionIds(roleId);

		setPermissions(roleId, actionIdsLong);
	}

	public long getActionIds(long roleId) {
		Long actionIdsLong = _permissions.get(roleId);

		if (actionIdsLong == null) {
			actionIdsLong = 0L;
		}

		return actionIdsLong;
	}

	public SortedMap<Long, Long> getPermissions() {
		return _permissions;
	}

	public void removePermission(long roleId, long actionIdsLong) {
		actionIdsLong = getActionIds(roleId) & (~actionIdsLong);

		setPermissions(roleId, actionIdsLong);
	}

	public void setPermissions(long roleId, long actionIdsLong) {
		if (actionIdsLong == 0) {
			_permissions.remove(roleId);
		}
		else {
			_permissions.put(roleId, actionIdsLong);
		}
	}

	private SortedMap<Long, Long> _permissions = new TreeMap<Long, Long>();

}