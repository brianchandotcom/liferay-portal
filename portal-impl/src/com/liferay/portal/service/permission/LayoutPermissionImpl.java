/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.permission;

import com.liferay.portal.NoSuchResourceException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.ResourceLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.sites.util.SitesUtil;

/**
 * @author Charles May
 * @author Brian Wing Shun Chan
 */
public class LayoutPermissionImpl implements LayoutPermission {

	public void check(
			PermissionChecker permissionChecker, Layout layout, String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, layout, actionId)) {
			throw new PrincipalException();
		}
	}

	public void check(
			PermissionChecker permissionChecker, long groupId,
			boolean privateLayout, long layoutId, String actionId)
		throws PortalException, SystemException {

		if (!contains(
				permissionChecker, groupId, privateLayout, layoutId,
				actionId)) {

			throw new PrincipalException();
		}
	}

	public void check(
			PermissionChecker permissionChecker, long plid, String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, plid, actionId)) {
			throw new PrincipalException();
		}
	}

	public boolean contains(
			PermissionChecker permissionChecker, Layout layout, String actionId)
		throws PortalException, SystemException {

		if ((layout.isPrivateLayout() &&
			 !PropsValues.LAYOUT_USER_PRIVATE_LAYOUTS_MODIFIABLE) ||
			(layout.isPublicLayout() &&
			 !PropsValues.LAYOUT_USER_PUBLIC_LAYOUTS_MODIFIABLE)) {

			if (actionId.equals(ActionKeys.UPDATE)) {
				Group group = GroupLocalServiceUtil.getGroup(
					layout.getGroupId());

				if (group.isUser()) {
					return false;
				}
			}
		}

		try {
			Group group = layout.getGroup();

			if (!group.isLayoutSetPrototype() &&
				attemptToModifyLockedLayout(layout, actionId)) {

				return false;
			}
		}
		catch (Exception e) {
		}

		if (GroupPermissionUtil.contains(
				permissionChecker, layout.getGroupId(),
				ActionKeys.MANAGE_LAYOUTS)) {

			return true;
		}

		try {
			if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 6) {
				if (ResourcePermissionLocalServiceUtil.
						getResourcePermissionsCount(
							layout.getCompanyId(), Layout.class.getName(),
							ResourceConstants.SCOPE_INDIVIDUAL,
							String.valueOf(layout.getPlid())) == 0) {

					throw new NoSuchResourceException();
				}
			}
			else {
				ResourceLocalServiceUtil.getResource(
					layout.getCompanyId(), Layout.class.getName(),
					ResourceConstants.SCOPE_INDIVIDUAL,
					String.valueOf(layout.getPlid()));
			}
		}
		catch (NoSuchResourceException nsre) {
			boolean addGroupPermission = true;
			boolean addGuestPermission = true;

			if (layout.isPrivateLayout()) {
				addGuestPermission = false;
			}

			ResourceLocalServiceUtil.addResources(
				layout.getCompanyId(), layout.getGroupId(), 0,
				Layout.class.getName(), layout.getPlid(), false,
				addGroupPermission, addGuestPermission);
		}

		return permissionChecker.hasPermission(
			layout.getGroupId(), Layout.class.getName(), layout.getPlid(),
			actionId);
	}

	public boolean contains(
			PermissionChecker permissionChecker, long groupId,
			boolean privateLayout, long layoutId, String actionId)
		throws PortalException, SystemException {

		if (layoutId == LayoutConstants.DEFAULT_PARENT_LAYOUT_ID) {

			Layout layout = LayoutLocalServiceUtil.getLayout(
					groupId, privateLayout, layoutId);

			if (attemptToModifyLockedLayout(layout, actionId)) {
				return false;
			}

			if (GroupPermissionUtil.contains(
					permissionChecker, groupId, ActionKeys.MANAGE_LAYOUTS)) {

				return true;
			}
			else {
				return false;
			}
		}
		else {
			Layout layout = LayoutLocalServiceUtil.getLayout(
				groupId, privateLayout, layoutId);

			if (attemptToModifyLockedLayout(layout, actionId)) {
				return false;
			}

			return contains(permissionChecker, layout, actionId);
		}
	}

	public boolean contains(
			PermissionChecker permissionChecker, long plid, String actionId)
		throws PortalException, SystemException {

		Layout layout = LayoutLocalServiceUtil.getLayout(plid);

		return contains(permissionChecker, layout, actionId);
	}

	protected boolean attemptToModifyLockedLayout(
		Layout layout, String actionId) {

		if (SitesUtil.isLayoutLocked(layout) &&
			(ActionKeys.CUSTOMIZE.equals(actionId) ||
				ActionKeys.UPDATE.equals(actionId))) {

			return true;
		}

		return false;
	}

}