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

package com.liferay.exportimport.internal.lar;

import com.liferay.portal.kernel.exception.NoSuchRoleException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Charles May
 */
public class LayoutCache {

	public Role getNameRole(long companyId, String roleName)
		throws PortalException {

		Role role = nameRolesMap.get(roleName);

		if (role == null) {
			try {
				role = RoleLocalServiceUtil.getRole(companyId, roleName);

				nameRolesMap.put(roleName, role);
			}
			catch (NoSuchRoleException noSuchRoleException) {

				// LPS-52675

				if (_log.isDebugEnabled()) {
					_log.debug(noSuchRoleException, noSuchRoleException);
				}
			}
		}

		return role;
	}

	public Role getUuidRole(long companyId, String uuid)
		throws PortalException {

		Role role = uuidRolesMap.get(uuid);

		if (role == null) {
			try {
				role = RoleLocalServiceUtil.getRoleByUuidAndCompanyId(
					uuid, companyId);

				uuidRolesMap.put(uuid, role);
			}
			catch (NoSuchRoleException noSuchRoleException) {

				// LPS-52675

				if (_log.isDebugEnabled()) {
					_log.debug(noSuchRoleException, noSuchRoleException);
				}
			}
		}

		return role;
	}

	protected Map<Long, List<Role>> groupRolesMap = new HashMap<>();
	protected Map<Long, List<User>> groupUsersMap = new HashMap<>();
	protected Map<String, Role> nameRolesMap = new HashMap<>();
	protected Map<Long, List<Role>> userRolesMap = new HashMap<>();
	protected Map<String, Role> uuidRolesMap = new HashMap<>();

	private static final Log _log = LogFactoryUtil.getLog(LayoutCache.class);

}