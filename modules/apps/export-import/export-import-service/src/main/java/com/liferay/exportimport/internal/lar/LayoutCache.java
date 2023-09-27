/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
					_log.debug(noSuchRoleException);
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
					_log.debug(noSuchRoleException);
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