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
import com.liferay.portal.kernel.theme.ThemeDisplay;

/**
 * @author Jorge Ferrer
 */
public interface ControlPanelEntry {

	public boolean hasAccessPermission(
			PermissionChecker permissionChecker, Group group, Portlet portlet)
		throws Exception;

	/**
	 * @deprecated As of 6.2.0, with no direct replacement.<p>This method was
	 *             originally defined to determine if a portlet should be
	 *             displayed in the Control Panel. In this version, this method
	 *             should always return <code>false</code> and remains only to
	 *             preserve binary compatibility. This method will be
	 *             permanently removed in a future version.</p><p>In lieu of
	 *             this method, the Control Panel now uses {@link
	 *             #hasAccessPermission} to determine if a portlet should be
	 *             displayed in the Control Panel.</p>
	 */
	@Deprecated
	public boolean isVisible(
			PermissionChecker permissionChecker, Portlet portlet)
		throws Exception;

	/**
	 * @deprecated As of 6.2.0, with no direct replacement.<p>This method was
	 *             originally defined to determine if a portlet should be
	 *             displayed in the Control Panel. In this version, this method
	 *             should always return <code>false</code> and remains only to
	 *             preserve binary compatibility. This method will be
	 *             permanently removed in a future version.</p><p>In lieu of
	 *             this method, the Control Panel now uses {@link
	 *             #hasAccessPermission} to determine if a portlet should be
	 *             displayed in the Control Panel.</p>
	 */
	@Deprecated
	public boolean isVisible(
			Portlet portlet, String category, ThemeDisplay themeDisplay)
		throws Exception;

}