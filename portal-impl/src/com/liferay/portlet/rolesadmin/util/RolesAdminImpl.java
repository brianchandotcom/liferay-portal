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
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.roles.admin.kernel.util.RolesAdmin;

/**
 * @author Brian Wing Shun Chan
 */
@DoPrivileged
public class RolesAdminImpl implements RolesAdmin {

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public String getCssClassName(Role role) {
		String cssClassName = StringPool.BLANK;

		String roleName = role.getName();
		int roleType = role.getType();

		if (roleName.equals(RoleConstants.GUEST)) {
			cssClassName = "lfr-role-guest";
		}
		else if (roleType == RoleConstants.TYPE_ORGANIZATION) {
			cssClassName = "lfr-role-organization";
		}
		else if (roleType == RoleConstants.TYPE_REGULAR) {
			cssClassName = "lfr-role-regular";
		}
		else if (roleType == RoleConstants.TYPE_SITE) {
			cssClassName = "lfr-role-site";
		}
		else if (role.isTeam()) {
			cssClassName = "lfr-role-team";
		}

		return "lfr-role " + cssClassName;
	}

	@Override
	public String getIconCssClass(Role role) {
		String iconCssClass = StringPool.BLANK;

		String roleName = role.getName();
		int roleType = role.getType();

		if (roleName.equals(RoleConstants.GUEST)) {
			iconCssClass = "icon-user guest";
		}
		else if (roleType == RoleConstants.TYPE_ORGANIZATION) {
			iconCssClass = "icon-globe";
		}
		else if (roleType == RoleConstants.TYPE_REGULAR) {
			iconCssClass = "icon-user";
		}
		else if (roleType == RoleConstants.TYPE_SITE) {
			iconCssClass = "icon-globe";
		}
		else if (role.isTeam()) {
			iconCssClass = "icon-group";
		}

		return iconCssClass;
	}

}