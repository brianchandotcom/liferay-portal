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

package com.liferay.portal.kernel.internal.security.permission.resource;

import com.liferay.petra.lang.HashUtil;

import java.util.Objects;

/**
 * @author Preston Crary
 */
public class PermissionCacheKey {

	public PermissionCacheKey(String name, long primaryKey, String actionId) {
		_name = name;
		_primaryKey = primaryKey;
		_actionId = actionId;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof PermissionCacheKey)) {
			return false;
		}

		PermissionCacheKey permissionCacheKey = (PermissionCacheKey)object;

		if (Objects.equals(_name, permissionCacheKey._name) &&
			(_primaryKey == permissionCacheKey._primaryKey) &&
			Objects.equals(_actionId, permissionCacheKey._actionId)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _name);

		hash = HashUtil.hash(hash, _primaryKey);

		return HashUtil.hash(hash, _actionId);
	}

	private final String _actionId;
	private final String _name;
	private final long _primaryKey;

}