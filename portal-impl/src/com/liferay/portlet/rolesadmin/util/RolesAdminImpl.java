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

package com.liferay.portlet.rolesadmin.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.roles.admin.kernel.util.RolesAdmin;

/**
 * @author Brian Wing Shun Chan
 */
public class RolesAdminImpl implements RolesAdmin {

	@Override
	public String getIconCssClass(Role role) {
		String iconCssClass = StringPool.BLANK;

		String roleName = role.getName();
		int roleType = role.getType();

		if (roleName.equals(RoleConstants.GUEST)) {
			iconCssClass = "user";
		}
		else if (roleType == RoleConstants.TYPE_ORGANIZATION) {
			iconCssClass = "globe";
		}
		else if (roleType == RoleConstants.TYPE_REGULAR) {
			iconCssClass = "user";
		}
		else if (roleType == RoleConstants.TYPE_SITE) {
			iconCssClass = "globe";
		}
		else if (role.isTeam()) {
			iconCssClass = "community";
		}

		return iconCssClass;
	}

}