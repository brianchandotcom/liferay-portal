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

package com.liferay.portal.kernel.security.permission;

/**
 * @author Preston Crary
 */
public class RolePermissions {

	public RolePermissions(
		String name, int scope, String actionId, long roleId) {

		_name = name;
		_scope = scope;
		_actionId = actionId;
		_roleId = roleId;
	}

	public String getActionId() {
		return _actionId;
	}

	public String getName() {
		return _name;
	}

	public long getRoleId() {
		return _roleId;
	}

	public int getScope() {
		return _scope;
	}

	private final String _actionId;
	private final String _name;
	private final long _roleId;
	private final int _scope;

}