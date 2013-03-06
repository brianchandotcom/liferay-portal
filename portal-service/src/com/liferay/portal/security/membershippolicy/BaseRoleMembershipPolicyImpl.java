/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.service.RoleLocalServiceUtil;

import java.util.List;

/**
 * @author Roberto Díaz
 * @author Sergio González
 */
public abstract class BaseRoleMembershipPolicyImpl
	implements RoleMembershipPolicy {

	public boolean isRoleAllowed(long userId, long roleId)
		throws PortalException, SystemException {

		try {
			checkRoles(new long[] {userId}, new long[] {roleId}, null);
		}
		catch (Exception e) {
			return false;
		}

		return true;
	}

	public boolean isRoleRequired(long userId, long roleId)
		throws PortalException, SystemException {

		try {
			checkRoles(new long[] {userId}, null, new long[] {roleId});
		}
		catch (Exception e) {
			return true;
		}

		return false;
	}

	public void verifyPolicy() throws PortalException, SystemException {
		int start = 0;
		int end = SEARCH_INTERVAL;

		int total = RoleLocalServiceUtil.getTypeRolesCount(
			RoleConstants.TYPE_REGULAR);

		while (start <= total) {
			List<Role> roles = RoleLocalServiceUtil.getTypeRoles(
				RoleConstants.TYPE_REGULAR, start, end);

			for (Role role : roles) {
				verifyPolicy(role);
			}

			start = end;
			end += end;
		}
	}

	public void verifyPolicy(Role role)
		throws PortalException, SystemException {

		verifyPolicy(role, null, null);
	}

}