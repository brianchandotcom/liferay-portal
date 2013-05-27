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

package com.liferay.portlet;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletCategoryKeys;

import java.util.List;

/**
 * @author Jorge Ferrer
 */
public abstract class BaseControlPanelEntry implements ControlPanelEntry {

	@Override
	public boolean hasAccessPermission(
			PermissionChecker permissionChecker, Group group, Portlet portlet)
		throws Exception {

		if (hasAccessPermissionDenied(permissionChecker, group, portlet)) {
			return false;
		}

		if (hasAccessPermissionExplicitlyGranted(
				permissionChecker, group, portlet)) {

			return true;
		}

		return hasPermissionImplicitlyGranted(
			permissionChecker, group, portlet);
	}

	/**
	 * @deprecated As of 6.2, with no direct replacement.<p>This method was
	 *             originally defined to determine if a portlet should be
	 *             displayed in the Control Panel. In this version, this method
	 *             should always return <code>false</code> and remains only to
	 *             preserve binary compatibility. This method will be
	 *             permanently removed in a future version.</p><p>In lieu of
	 *             this method, the Control Panel now uses {@link
	 *             #hasAccessPermission} to determine if a portlet should be
	 *             displayed in the Control Panel.</p>
	 */
	@Override
	public boolean isVisible(
			PermissionChecker permissionChecker, Portlet portlet)
		throws Exception {

		return false;
	}

	/**
	 * @deprecated As of 6.2, with no direct replacement.<p>This method was
	 *             originally defined to determine if a portlet should be
	 *             displayed in the Control Panel. In this version, this method
	 *             should always return <code>false</code> and remains only to
	 *             preserve binary compatibility. This method will be
	 *             permanently removed in a future version.</p><p>In lieu of
	 *             this method, the Control Panel now uses {@link
	 *             #hasAccessPermission} to determine if a portlet should be
	 *             displayed in the Control Panel.</p>
	 */
	@Override
	public boolean isVisible(
			Portlet portlet, String category, ThemeDisplay themeDisplay)
		throws Exception {

		return false;
	}

	protected long getDefaultPlid(Group group, String category) {
		long plid = LayoutConstants.DEFAULT_PLID;

		if (category.startsWith(PortletCategoryKeys.SITE_ADMINISTRATION)) {
			plid = group.getDefaultPublicPlid();

			if (plid == LayoutConstants.DEFAULT_PLID) {
				plid = group.getDefaultPrivatePlid();
			}
		}

		return plid;
	}

	protected boolean hasAccessPermissionDenied(
			PermissionChecker permissionChecker, Group group, Portlet portlet)
		throws Exception {

		return false;
	}

	protected boolean hasAccessPermissionExplicitlyGranted(
			PermissionChecker permissionChecker, Group group, Portlet portlet)
		throws PortalException, SystemException {

		if (permissionChecker.isCompanyAdmin()) {
			return true;
		}

		String category = portlet.getControlPanelEntryCategory();

		if (category == null) {
			category = StringPool.BLANK;
		}

		if (category.startsWith(PortletCategoryKeys.SITE_ADMINISTRATION)) {
			if (group.isLayout() && !portlet.isScopeable()) {
				return false;
			}

			if (permissionChecker.isGroupAdmin(group.getGroupId()) &&
				!group.isUser()) {

				return true;
			}
		}

		long groupId = group.getGroupId();

		if (category.equals(PortletCategoryKeys.APPS) ||
			category.equals(PortletCategoryKeys.CONFIGURATION) ||
			category.equals(PortletCategoryKeys.SITES) ||
			category.equals(PortletCategoryKeys.USERS)) {

			groupId = 0;
		}

		List<String> actions = ResourceActionsUtil.getResourceActions(
			portlet.getPortletId());

		if (actions.contains(ActionKeys.ACCESS_IN_CONTROL_PANEL) &&
			PortletPermissionUtil.contains(
				permissionChecker, groupId, getDefaultPlid(group, category),
				portlet.getPortletId(), ActionKeys.ACCESS_IN_CONTROL_PANEL,
				true)) {

			return true;
		}

		return false;
	}

	protected boolean hasPermissionImplicitlyGranted(
			PermissionChecker permissionChecker, Group group, Portlet portlet)
		throws Exception {

		return false;
	}

}