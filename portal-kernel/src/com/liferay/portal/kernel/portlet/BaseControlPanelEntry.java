/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		if (category.equals(StringPool.BLANK)) {
			Portlet rootPortlet = portlet.getRootPortlet();

			if (rootPortlet != null) {
				category = rootPortlet.getControlPanelEntryCategory();

				if (category == null) {
					category = StringPool.BLANK;
				}
			}
		}

		if (category.startsWith(PortletCategoryKeys.SITE_ADMINISTRATION) &&
			permissionChecker.isGroupAdmin(group.getGroupId()) &&
			!group.isUser()) {

			return true;
		}

		long groupId = 0L;

		if (category.startsWith(PortletCategoryKeys.SITE_ADMINISTRATION)) {
			groupId = group.getGroupId();
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