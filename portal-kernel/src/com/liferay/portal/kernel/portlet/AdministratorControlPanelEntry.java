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

package com.liferay.portal.kernel.portlet;

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

/**
 * Represents the administrator control panel entry for portlets that shall only
 * be visible and accessible to the company's administrator. In a portlet's
 * <code>liferay-portlet.xml</code> file, this class can be specified as the
 * value for the <a
 * href="http://docs.liferay.com/portal/7.1/definitions/liferay-portlet-app_7_1_0.dtd.html#control-panel-entry-class"
 * > control-panel-entry-class </a> element.
 *
 * @author Brian Wing Shun Chan
 */
public class AdministratorControlPanelEntry extends BaseControlPanelEntry {

	/**
	 * Returns <code>true</code> if the current user is the company's
	 * administrator.
	 *
	 * @param  permissionChecker the permission checker referencing the current
	 *         user
	 * @param  group the group
	 * @param  portlet the portlet being checked
	 * @return <code>true</code> if the current user is the company's
	 *         administrator
	 * @throws Exception if an exception occurred
	 */
	@Override
	public boolean hasAccessPermission(
			PermissionChecker permissionChecker, Group group, Portlet portlet)
		throws Exception {

		return permissionChecker.isCompanyAdmin();
	}

}