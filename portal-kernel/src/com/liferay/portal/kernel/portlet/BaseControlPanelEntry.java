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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.util.PortletCategoryKeys;

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

		String category = portlet.getControlPanelEntryCategory();

		if (category.startsWith(PortletCategoryKeys.SITE_ADMINISTRATION) &&
			group.isLayoutPrototype()) {

			return true;
		}

		if (category.startsWith(PortletCategoryKeys.SITE_ADMINISTRATION) &&
			group.isLayout() && !portlet.isScopeable()) {

			return true;
		}

		return false;
	}

	protected boolean hasAccessPermissionExplicitlyGranted(
			PermissionChecker permissionChecker, Group group, Portlet portlet)
		throws PortalException {

		if (permissionChecker.isCompanyAdmin()) {
			return true;
		}

		String category = portlet.getControlPanelEntryCategory();

		if (category == null) {
			category = StringPool.BLANK;
		}

		if (category.startsWith(PortletCategoryKeys.SITE_ADMINISTRATION) &&
			permissionChecker.isGroupAdmin(group.getGroupId()) &&
			!group.isUser()) {

			return true;
		}

		long groupId = group.getGroupId();

		if (category.equals(PortletCategoryKeys.CONTROL_PANEL_APPS) ||
			category.equals(PortletCategoryKeys.CONTROL_PANEL_CONFIGURATION) ||
			category.equals(PortletCategoryKeys.CONTROL_PANEL_SITES) ||
			category.equals(PortletCategoryKeys.CONTROL_PANEL_SYSTEM) ||
			category.equals(PortletCategoryKeys.CONTROL_PANEL_USERS) ||
			category.equals(PortletCategoryKeys.CONTROL_PANEL_WORKFLOW)) {

			groupId = 0;
		}

		List<String> resourceActions = ResourceActionsUtil.getResourceActions(
			portlet.getPortletId());

		if (resourceActions.contains(ActionKeys.ACCESS_IN_CONTROL_PANEL) &&
			PortletPermissionUtil.contains(
				permissionChecker, groupId, 0, portlet.getRootPortletId(),
				ActionKeys.ACCESS_IN_CONTROL_PANEL, true)) {

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