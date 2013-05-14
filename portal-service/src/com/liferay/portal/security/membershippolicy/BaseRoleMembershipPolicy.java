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

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Role;
import com.liferay.portal.service.persistence.RoleActionableDynamicQuery;

/**
 * @author Roberto Díaz
 * @author Sergio González
 */
public abstract class BaseRoleMembershipPolicy implements RoleMembershipPolicy {

	@Override
	@SuppressWarnings("unused")
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

	@Override
	@SuppressWarnings("unused")
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

	@Override
	public void verifyPolicy() throws PortalException, SystemException {
		ActionableDynamicQuery actionableDynamicQuery =
			new RoleActionableDynamicQuery() {

			@Override
			protected void performAction(Object object)
				throws PortalException, SystemException {

				Role role = (Role)object;

				verifyPolicy(role);
			}

		};

		actionableDynamicQuery.performActions();
	}

	@Override
	public void verifyPolicy(Role role)
		throws PortalException, SystemException {

		verifyPolicy(role, null, null);
	}

}