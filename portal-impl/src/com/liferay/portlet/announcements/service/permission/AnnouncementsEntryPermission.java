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

package com.liferay.portlet.announcements.service.permission;

import com.liferay.announcements.kernel.model.AnnouncementsEntry;
import com.liferay.announcements.kernel.service.AnnouncementsEntryLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.impl.VirtualLayout;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;

/**
 * @author Raymond Augé
 */
public class AnnouncementsEntryPermission {

	public static void check(
			PermissionChecker permissionChecker, AnnouncementsEntry entry,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, entry, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, AnnouncementsEntry.class.getName(),
				entry.getEntryId(), actionId);
		}
	}

	public static void check(
			PermissionChecker permissionChecker, Layout layout, String name,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, layout, name, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, AnnouncementsEntry.class.getName(), name,
				actionId);
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long entryId, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, entryId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, AnnouncementsEntry.class.getName(), entryId,
				actionId);
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long plid, String portletId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, plid, portletId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, AnnouncementsEntry.class.getName(),
				portletId, actionId);
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, AnnouncementsEntry entry,
			String actionId)
		throws PortalException {

		if (permissionChecker.hasOwnerPermission(
				entry.getCompanyId(), AnnouncementsEntry.class.getName(),
				entry.getEntryId(), entry.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			entry.getGroupId(), AnnouncementsEntry.class.getName(),
			entry.getEntryId(), actionId);
	}

	public static boolean contains(
		PermissionChecker permissionChecker, Layout layout, String portletId,
		String actionId) {

		if (layout instanceof VirtualLayout) {
			VirtualLayout virtualLayout = (VirtualLayout)layout;

			layout = virtualLayout.getSourceLayout();
		}

		String primKey = PortletPermissionUtil.getPrimaryKey(
			layout.getPlid(), portletId);

		return permissionChecker.hasPermission(
			layout.getGroupId(), portletId, primKey, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long entryId, String actionId)
		throws PortalException {

		return contains(
			permissionChecker,
			AnnouncementsEntryLocalServiceUtil.getEntry(entryId), actionId);
	}

	public static boolean contains(
		PermissionChecker permissionChecker, long plid, String name,
		String actionId) {

		Layout layout = LayoutLocalServiceUtil.fetchLayout(plid);

		return contains(permissionChecker, layout, name, actionId);
	}

}