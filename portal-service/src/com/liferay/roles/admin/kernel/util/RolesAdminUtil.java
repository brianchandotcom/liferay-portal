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

package com.liferay.roles.admin.kernel.util;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.model.Role;

/**
 * @author Brian Wing Shun Chan
 */
public class RolesAdminUtil {

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public static String getCssClassName(Role role) {
		return getRolesAdmin().getCssClassName(role);
	}

	public static String getIconCssClass(Role role) {
		return getRolesAdmin().getIconCssClass(role);
	}

	public static RolesAdmin getRolesAdmin() {
		PortalRuntimePermission.checkGetBeanProperty(RolesAdminUtil.class);

		return _rolesAdmin;
	}

	public void setRolesAdmin(RolesAdmin rolesAdmin) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_rolesAdmin = rolesAdmin;
	}

	private static RolesAdmin _rolesAdmin;

}