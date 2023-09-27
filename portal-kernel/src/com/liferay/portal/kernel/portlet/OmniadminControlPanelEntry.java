/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.portlet;

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

/**
 * Represents the omni administrator control panel entry for portlets that shall
 * only be visible and accessible to the omni administrator. In a portlet's
 * <code>liferay-portlet.xml</code> file, this class can be specified as the
 * value for the <a
 * href="http://docs.liferay.com/portal/7.1/definitions/liferay-portlet-app_7_1_0.dtd.html#control-panel-entry-class"
 * > control-panel-entry-class </a> element.
 *
 * @author Jorge Ferrer
 */
public class OmniadminControlPanelEntry extends BaseControlPanelEntry {

	/**
	 * Returns <code>true</code> if the current user is an omni administrator.
	 *
	 * @param  permissionChecker the permission checker referencing the current
	 *         user
	 * @param  group the group
	 * @param  portlet the portlet being checked
	 * @return <code>true</code> if the current user is an omni administrator;
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