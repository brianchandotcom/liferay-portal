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

package com.liferay.portal.security.membershippolicy;

import com.liferay.portal.model.Role;

import java.io.Serializable;

import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Roberto Díaz
 * @author Sergio González
 */
public class DummyRoleMembershipPolicy extends BaseRoleMembershipPolicy {

	@Override
	public void checkRoles(
		long[] userIds, long[] addRoleIds, long[] removeRoleIds) {
	}

	@Override
	public boolean isRoleAllowed(long userId, long roleId) {
		return true;
	}

	@Override
	public boolean isRoleRequired(long userId, long roleId) {
		return false;
	}

	@Override
	public void propagateRoles(
		long[] userIds, long[] addRoleIds, long[] removeRoleIds) {
	}

	@Override
	public void verifyPolicy(Role role) {
	}

	@Override
	public void verifyPolicy(
		Role role, Role oldRole,
		Map<String, Serializable> oldExpandoAttributes) {
	}

}