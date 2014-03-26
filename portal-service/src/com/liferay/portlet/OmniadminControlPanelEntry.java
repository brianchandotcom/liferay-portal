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

package com.liferay.portlet;

import com.liferay.portal.model.Group;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.security.permission.PermissionChecker;

/**
 * Represents the Omni Administrator control panel entry, which is used as a
 * {@link ControlPanelEntry} for portlets that is only visible/accessible to the
 * Omni Administrator and declared in <code>liferay-portlet.xml</code>.
 *
 * @author Jorge Ferrer
 */
public class OmniadminControlPanelEntry extends BaseControlPanelEntry {

	/**
	 * Returns <code>true</code> if the current user is an Omni Administrator.
	 *
	 * @param  permissionChecker the permission checker referencing a user
	 * @param  group the group
	 * @param  portlet the portlet being checked
	 * @return <code>true</code> if the current user is an Omni Administrator;
	 *         <code>false</code> otherwise
	 * @throws Exception if an exception occurred
	 */
	@Override
	public boolean hasAccessPermission(
			PermissionChecker permissionChecker, Group group, Portlet portlet)
		throws Exception {

		return permissionChecker.isOmniadmin();
	}

}