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

package com.liferay.portal.kernel.model;

import com.liferay.portal.kernel.util.Digester;
import com.liferay.portal.kernel.util.DigesterUtil;

import java.nio.ByteBuffer;

import java.util.Map;
import java.util.Set;
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

	/**
	 * Returns the permissions hash of the resource permissions. The permissions
	 * hash is a representation of all the roles with access to the resource
	 * along with the actions they can perform.
	 *
	 * @return the permissions hash of the resource permissions
	 */
	public String getPermissionsHash() {

		// long is 8 bytes, there are 2 longs per permission, so preallocate
		// byte buffer to 16 * the number of permissions.

		ByteBuffer byteBuffer = ByteBuffer.allocate(_permissions.size() * 16);

		for (Map.Entry<Long, Long> entry : _permissions.entrySet()) {
			byteBuffer.putLong(entry.getKey());
			byteBuffer.putLong(entry.getValue());
		}

		byteBuffer.flip();

		return DigesterUtil.digestHex(Digester.SHA_1, byteBuffer);
	}

	public Set<Long> getRoleIds() {
		return _permissions.keySet();
	}

	public boolean hasPermission(long roleId, long actionIdsLong) {
		if ((getActionIds(roleId) & actionIdsLong) == actionIdsLong) {
			return true;
		}

		return false;
	}

	public void removePermission(long roleId, long actionIdsLong) {
		actionIdsLong = getActionIds(roleId) & ~actionIdsLong;

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

	private final SortedMap<Long, Long> _permissions = new TreeMap<>();

}