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

package com.liferay.portal.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.security.permission.PermissionChecker;

import java.util.Collection;

import javax.portlet.PortletMode;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public interface PortletPermission {

	public void check(
			PermissionChecker permissionChecker, Layout layout,
			String portletId, String actionId)
		throws PortalException, SystemException;

	public void check(
			PermissionChecker permissionChecker, Layout layout,
			String portletId, String actionId, boolean strict)
		throws PortalException, SystemException;

	public void check(
			PermissionChecker permissionChecker, long groupId, Layout layout,
			String portletId, String actionId)
		throws PortalException, SystemException;

	public void check(
			PermissionChecker permissionChecker, long groupId, Layout layout,
			String portletId, String actionId, boolean strict)
		throws PortalException, SystemException;

	public void check(
			PermissionChecker permissionChecker, long groupId, long plid,
			String portletId, String actionId)
		throws PortalException, SystemException;

	public void check(
			PermissionChecker permissionChecker, long groupId, long plid,
			String portletId, String actionId, boolean strict)
		throws PortalException, SystemException;

	public void check(
			PermissionChecker permissionChecker, long plid, String portletId,
			String actionId)
		throws PortalException, SystemException;

	public void check(
			PermissionChecker permissionChecker, long plid, String portletId,
			String actionId, boolean strict)
		throws PortalException, SystemException;

	public void check(
			PermissionChecker permissionChecker, String portletId,
			String actionId)
		throws PortalException, SystemException;

	public boolean contains(
			PermissionChecker permissionChecker, Layout layout, Portlet portlet,
			String actionId)
		throws PortalException, SystemException;

	public boolean contains(
			PermissionChecker permissionChecker, Layout layout, Portlet portlet,
			String actionId, boolean strict)
		throws PortalException, SystemException;

	public boolean contains(
			PermissionChecker permissionChecker, Layout layout,
			String portletId, String actionId)
		throws PortalException, SystemException;

	public boolean contains(
			PermissionChecker permissionChecker, Layout layout,
			String portletId, String actionId, boolean strict)
		throws PortalException, SystemException;

	public boolean contains(
			PermissionChecker permissionChecker, long groupId, Layout layout,
			Portlet portlet, String actionId)
		throws PortalException, SystemException;

	public boolean contains(
			PermissionChecker permissionChecker, long groupId, Layout layout,
			Portlet portlet, String actionId, boolean strict)
		throws PortalException, SystemException;

	public boolean contains(
			PermissionChecker permissionChecker, long groupId, Layout layout,
			String portletId, String actionId)
		throws PortalException, SystemException;

	public boolean contains(
			PermissionChecker permissionChecker, long groupId, Layout layout,
			String portletId, String actionId, boolean strict)
		throws PortalException, SystemException;

	public boolean contains(
			PermissionChecker permissionChecker, long groupId, long plid,
			Portlet portlet, String actionId, boolean strict)
		throws PortalException, SystemException;

	public boolean contains(
			PermissionChecker permissionChecker, long groupId, long plid,
			String portletId, String actionId, boolean strict)
		throws PortalException, SystemException;

	public boolean contains(
			PermissionChecker permissionChecker, long plid, Portlet portlet,
			String actionId)
		throws PortalException, SystemException;

	public boolean contains(
			PermissionChecker permissionChecker, long plid, Portlet portlet,
			String actionId, boolean strict)
		throws PortalException, SystemException;

	public boolean contains(
			PermissionChecker permissionChecker, long plid, String portletId,
			String actionId)
		throws PortalException, SystemException;

	public boolean contains(
			PermissionChecker permissionChecker, long plid, String portletId,
			String actionId, boolean strict)
		throws PortalException, SystemException;

	public boolean contains(
			PermissionChecker permissionChecker, String portletId,
			String actionId)
		throws PortalException, SystemException;

	public String getPrimaryKey(long plid, String portletId);

	public boolean hasAccessPermission(
			PermissionChecker permissionChecker, long scopeGroupId,
			Layout layout, Portlet portlet, PortletMode portletMode)
		throws PortalException, SystemException;

	public boolean hasAnyConfigurationPermission(
			PermissionChecker permissionChecker, long groupId, Layout layout,
			String actionId)
		throws PortalException, SystemException;

	public boolean hasControlPanelAccessPermission(
			PermissionChecker permissionChecker, long scopeGroupId,
			Collection<Portlet> portlets)
		throws PortalException, SystemException;

	public boolean hasControlPanelAccessPermission(
			PermissionChecker permissionChecker, long scopeGroupId,
			Portlet portlet)
		throws PortalException, SystemException;

	public boolean hasControlPanelAccessPermission(
			PermissionChecker permissionChecker, long scopeGroupId,
			String portletId)
		throws PortalException, SystemException;

	public boolean hasLayoutManagerPermission(
		String portletId, String actionId);

}