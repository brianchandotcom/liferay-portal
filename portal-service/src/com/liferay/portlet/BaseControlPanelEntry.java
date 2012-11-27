/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.model.Group;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.util.PortletCategoryKeys;

/**
 * @author Jorge Ferrer
 */
public abstract class BaseControlPanelEntry implements ControlPanelEntry {

	public boolean hasAccessPermission(
			PermissionChecker permissionChecker, Group group, Portlet portlet)
		throws Exception {

		if (hasAccessPermissionDenied(permissionChecker, group, portlet)) {
			return false;
		}

		if (hasAccessPermissionExplicit(permissionChecker, group, portlet)) {
			return true;
		}

		return hasPermissionImplicit(permissionChecker, group, portlet);
	}

	protected abstract boolean hasAccessPermissionDenied(
			PermissionChecker permissionChecker, Group group, Portlet portlet)
		throws Exception;

	protected boolean hasAccessPermissionExplicit(
			PermissionChecker permissionChecker, Group group, Portlet portlet)
		throws PortalException, SystemException {

		if (permissionChecker.isCompanyAdmin()) {
			return true;
		}

		String category = portlet.getControlPanelEntryCategory();

		if (category.equals(PortletCategoryKeys.CONTENT) &&
			permissionChecker.isGroupAdmin(group.getGroupId()) &&
			!group.isUser()) {

			return true;
		}

		long groupId = group.getGroupId();

		if (category.equals(PortletCategoryKeys.PORTAL) ||
			category.equals(PortletCategoryKeys.SERVER)) {

			groupId = 0;
		}

		if (PortletPermissionUtil.contains(
				permissionChecker, groupId, _getDefaultPlid(group, category),
				portlet.getPortletId(), ActionKeys.ACCESS_IN_CONTROL_PANEL,
				true)) {

			return true;
		}

		return false;
	}

	protected abstract boolean hasPermissionImplicit(
			PermissionChecker permissionChecker, Group group, Portlet portlet)
		throws Exception;

	private long _getDefaultPlid(Group group, String category) {
		long plid = LayoutConstants.DEFAULT_PLID;

		if (category.equals(PortletCategoryKeys.CONTENT)) {
			plid = group.getDefaultPublicPlid();

			if (plid == LayoutConstants.DEFAULT_PLID) {
				plid = group.getDefaultPrivatePlid();
			}
		}

		return plid;
	}

}