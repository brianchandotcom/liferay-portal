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

package com.liferay.portal.kernel.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

import java.util.Collection;

import javax.portlet.PortletMode;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
@ProviderType
public interface PortletPermission {

	public void check(
			PermissionChecker permissionChecker, Layout layout,
			String portletId, String actionId)
		throws PortalException;

	public void check(
			PermissionChecker permissionChecker, Layout layout,
			String portletId, String actionId, boolean strict)
		throws PortalException;

	public void check(
			PermissionChecker permissionChecker, long groupId, Layout layout,
			String portletId, String actionId)
		throws PortalException;

	public void check(
			PermissionChecker permissionChecker, long groupId, Layout layout,
			String portletId, String actionId, boolean strict)
		throws PortalException;

	public void check(
			PermissionChecker permissionChecker, long groupId, Layout layout,
			String portletId, String actionId, boolean strict,
			boolean checkStagingPermission)
		throws PortalException;

	public void check(
			PermissionChecker permissionChecker, long groupId, long plid,
			String portletId, String actionId)
		throws PortalException;

	public void check(
			PermissionChecker permissionChecker, long groupId, long plid,
			String portletId, String actionId, boolean strict)
		throws PortalException;

	public void check(
			PermissionChecker permissionChecker, long plid, String portletId,
			String actionId)
		throws PortalException;

	public void check(
			PermissionChecker permissionChecker, long plid, String portletId,
			String actionId, boolean strict)
		throws PortalException;

	public void check(
			PermissionChecker permissionChecker, String portletId,
			String actionId)
		throws PortalException;

	public boolean contains(
			PermissionChecker permissionChecker, Layout layout, Portlet portlet,
			String actionId)
		throws PortalException;

	public boolean contains(
			PermissionChecker permissionChecker, Layout layout, Portlet portlet,
			String actionId, boolean strict)
		throws PortalException;

	public boolean contains(
			PermissionChecker permissionChecker, Layout layout,
			String portletId, String actionId)
		throws PortalException;

	public boolean contains(
			PermissionChecker permissionChecker, Layout layout,
			String portletId, String actionId, boolean strict)
		throws PortalException;

	public boolean contains(
			PermissionChecker permissionChecker, long groupId, Layout layout,
			Portlet portlet, String actionId)
		throws PortalException;

	public boolean contains(
			PermissionChecker permissionChecker, long groupId, Layout layout,
			Portlet portlet, String actionId, boolean strict)
		throws PortalException;

	public boolean contains(
			PermissionChecker permissionChecker, long groupId, Layout layout,
			Portlet portlet, String actionId, boolean strict,
			boolean checkStagingPermission)
		throws PortalException;

	public boolean contains(
			PermissionChecker permissionChecker, long groupId, Layout layout,
			String portletId, String actionId)
		throws PortalException;

	public boolean contains(
			PermissionChecker permissionChecker, long groupId, Layout layout,
			String portletId, String actionId, boolean strict)
		throws PortalException;

	public boolean contains(
			PermissionChecker permissionChecker, long groupId, Layout layout,
			String portletId, String actionId, boolean strict,
			boolean checkStagingPermission)
		throws PortalException;

	public boolean contains(
			PermissionChecker permissionChecker, long groupId, long plid,
			Portlet portlet, String actionId, boolean strict)
		throws PortalException;

	public boolean contains(
			PermissionChecker permissionChecker, long groupId, long plid,
			String portletId, String actionId, boolean strict)
		throws PortalException;

	public boolean contains(
			PermissionChecker permissionChecker, long plid, Portlet portlet,
			String actionId)
		throws PortalException;

	public boolean contains(
			PermissionChecker permissionChecker, long plid, Portlet portlet,
			String actionId, boolean strict)
		throws PortalException;

	public boolean contains(
			PermissionChecker permissionChecker, long plid, String portletId,
			String actionId)
		throws PortalException;

	public boolean contains(
			PermissionChecker permissionChecker, long plid, String portletId,
			String actionId, boolean strict)
		throws PortalException;

	public boolean contains(
			PermissionChecker permissionChecker, String portletId,
			String actionId)
		throws PortalException;

	public String getPrimaryKey(long plid, String portletId);

	public boolean hasAccessPermission(
			PermissionChecker permissionChecker, long scopeGroupId,
			Layout layout, Portlet portlet, PortletMode portletMode)
		throws PortalException;

	public boolean hasConfigurationPermission(
			PermissionChecker permissionChecker, long groupId, Layout layout,
			String actionId)
		throws PortalException;

	public boolean hasControlPanelAccessPermission(
			PermissionChecker permissionChecker, long scopeGroupId,
			Collection<Portlet> portlets)
		throws PortalException;

	public boolean hasControlPanelAccessPermission(
			PermissionChecker permissionChecker, long scopeGroupId,
			Portlet portlet)
		throws PortalException;

	public boolean hasControlPanelAccessPermission(
			PermissionChecker permissionChecker, long scopeGroupId,
			String portletId)
		throws PortalException;

	public boolean hasLayoutManagerPermission(
		String portletId, String actionId);

}