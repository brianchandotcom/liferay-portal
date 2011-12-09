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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.staging.permission.StagingPermissionUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.impl.VirtualLayout;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.sites.util.SitesUtil;

import java.util.Collection;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class PortletPermissionImpl implements PortletPermission {

	public static final boolean DEFAULT_STRICT = false;

	public void check(
			PermissionChecker permissionChecker, Layout layout,
			String portletId, String actionId)
		throws PortalException, SystemException {

		if (!contains(
				permissionChecker, 0, layout, portletId, actionId,
				DEFAULT_STRICT)) {

			throw new PrincipalException();
		}
	}

	public void check(
			PermissionChecker permissionChecker, Layout layout,
			String portletId, String actionId, boolean strict)
		throws PortalException, SystemException {

		if (!contains(
				permissionChecker, 0, layout, portletId, actionId, strict)) {

			throw new PrincipalException();
		}
	}

	public void check(
			PermissionChecker permissionChecker, long groupId, Layout layout,
			String portletId, String actionId)
		throws PortalException, SystemException {

		if (!contains(
				permissionChecker, groupId, layout, portletId, actionId,
				DEFAULT_STRICT)) {

			throw new PrincipalException();
		}
	}

	public void check(
			PermissionChecker permissionChecker, long groupId, Layout layout,
			String portletId, String actionId, boolean strict)
		throws PortalException, SystemException {

		if (!contains(
				permissionChecker, groupId, layout, portletId, actionId,
				strict)) {

			throw new PrincipalException();
		}
	}

	public void check(
			PermissionChecker permissionChecker, long groupId, long plid,
			String portletId, String actionId)
		throws PortalException, SystemException {

		check(
			permissionChecker, groupId, plid, portletId, actionId,
			DEFAULT_STRICT);
	}

	public void check(
			PermissionChecker permissionChecker, long groupId, long plid,
			String portletId, String actionId, boolean strict)
		throws PortalException, SystemException {

		if (!contains(
				permissionChecker, groupId, plid, portletId, actionId,
				strict)) {

			throw new PrincipalException();
		}
	}

	public void check(
			PermissionChecker permissionChecker, long plid, String portletId,
			String actionId)
		throws PortalException, SystemException {

		check(permissionChecker, plid, portletId, actionId, DEFAULT_STRICT);
	}

	public void check(
			PermissionChecker permissionChecker, long plid, String portletId,
			String actionId, boolean strict)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, plid, portletId, actionId, strict)) {
			throw new PrincipalException();
		}
	}

	public void check(
			PermissionChecker permissionChecker, String portletId,
			String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, portletId, actionId)) {
			throw new PrincipalException();
		}
	}

	public boolean contains(
			PermissionChecker permissionChecker, Layout layout, Portlet portlet,
			String actionId)
		throws PortalException, SystemException {

		return contains(
			permissionChecker, layout, portlet, actionId, DEFAULT_STRICT);
	}

	public boolean contains(
			PermissionChecker permissionChecker, Layout layout, Portlet portlet,
			String actionId, boolean strict)
		throws PortalException, SystemException {

		return contains(
			permissionChecker, 0, layout, portlet, actionId, strict);
	}

	public boolean contains(
			PermissionChecker permissionChecker, Layout layout,
			String portletId, String actionId)
		throws PortalException, SystemException {

		return contains(
			permissionChecker, layout, portletId, actionId, DEFAULT_STRICT);
	}

	public boolean contains(
			PermissionChecker permissionChecker, Layout layout,
			String portletId, String actionId, boolean strict)
		throws PortalException, SystemException {

		return contains(
			permissionChecker, 0, layout, portletId, actionId, strict);
	}

	public boolean contains(
			PermissionChecker permissionChecker, long groupId, Layout layout,
			Portlet portlet, String actionId)
		throws PortalException, SystemException {

		return contains(
			permissionChecker, groupId, layout, portlet, actionId,
			DEFAULT_STRICT);
	}

	public boolean contains(
			PermissionChecker permissionChecker, long groupId, Layout layout,
			Portlet portlet, String actionId, boolean strict)
		throws PortalException, SystemException {

		if (portlet.isUndeployedPortlet()) {
			return false;
		}

		boolean value = contains(
			permissionChecker, groupId, layout, portlet.getPortletId(),
			actionId, strict);

		if (value) {
			return true;
		}
		else {
			if (portlet.isSystem() && actionId.equals(ActionKeys.VIEW)) {
				return true;
			}
			else {
				return false;
			}
		}
	}

	public boolean contains(
			PermissionChecker permissionChecker, long groupId, Layout layout,
			String portletId, String actionId)
		throws PortalException, SystemException {

		return contains(
			permissionChecker, groupId, layout, portletId, actionId,
			DEFAULT_STRICT);
	}

	public boolean contains(
			PermissionChecker permissionChecker, long groupId, Layout layout,
			String portletId, String actionId, boolean strict)
		throws PortalException, SystemException {

		String name = null;
		String primKey = null;

		if (layout == null) {
			name = portletId;
			primKey = portletId;

			return permissionChecker.hasPermission(
				groupId, name, primKey, actionId);
		}

		Group group = layout.getGroup();

		groupId = group.getGroupId();

		name = PortletConstants.getRootPortletId(portletId);
		primKey = getPrimaryKey(layout.getPlid(), portletId);

		if (!actionId.equals(ActionKeys.VIEW) &&
			(layout instanceof VirtualLayout)) {

			return hasCustomizePermission(
				permissionChecker, layout, portletId, actionId);
		}

		if (!group.isLayoutSetPrototype() &&
			!SitesUtil.isLayoutUpdateable(layout) &&
			actionId.equals(ActionKeys.CONFIGURATION)) {

			return false;
		}

		Boolean hasPermission = StagingPermissionUtil.hasPermission(
			permissionChecker, groupId, name, groupId, name, actionId);

		if (hasPermission != null) {
			return hasPermission.booleanValue();
		}

		if ((layout.isPrivateLayout() &&
			 !PropsValues.LAYOUT_USER_PRIVATE_LAYOUTS_MODIFIABLE) ||
			(layout.isPublicLayout() &&
			 !PropsValues.LAYOUT_USER_PUBLIC_LAYOUTS_MODIFIABLE)) {

			if (actionId.equals(ActionKeys.CONFIGURATION) &&
				group.isUser()) {

				return false;
			}
		}

		if (actionId.equals(ActionKeys.VIEW) && group.isControlPanel()) {
			return true;
		}

		if (strict) {
			return permissionChecker.hasPermission(
				groupId, name, primKey, actionId);
		}

		if (hasConfigurePermission(
				permissionChecker, layout, portletId, actionId) ||
			hasCustomizePermission(
				permissionChecker, layout, portletId, actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			groupId, name, primKey, actionId);
	}

	public boolean contains(
		PermissionChecker permissionChecker, long groupId, long plid,
		Collection<Portlet> portlets, String actionId) {

		for (Portlet portlet : portlets) {
			if (permissionChecker.hasPermission(
					groupId, portlet.getPortletId(), portlet.getPortletId(),
					ActionKeys.ACCESS_IN_CONTROL_PANEL)) {

				return true;
			}
		}

		return false;
	}

	public boolean contains(
			PermissionChecker permissionChecker, long groupId, long plid,
			Portlet portlet, String actionId)
		throws PortalException, SystemException {

		Layout layout = LayoutLocalServiceUtil.fetchLayout(plid);

		return contains(
			permissionChecker, groupId, layout, portlet, actionId,
			DEFAULT_STRICT);
	}

	public boolean contains(
			PermissionChecker permissionChecker, long groupId, long plid,
			Portlet portlet, String actionId, boolean strict)
		throws PortalException, SystemException {

		Layout layout = LayoutLocalServiceUtil.fetchLayout(plid);

		return contains(
			permissionChecker, groupId, layout, portlet, actionId, strict);
	}

	public boolean contains(
			PermissionChecker permissionChecker, long groupId, long plid,
			String portletId, String actionId)
		throws PortalException, SystemException {

		Layout layout = LayoutLocalServiceUtil.fetchLayout(plid);

		return contains(
			permissionChecker, groupId, layout, portletId, actionId,
			DEFAULT_STRICT);
	}

	public boolean contains(
			PermissionChecker permissionChecker, long groupId, long plid,
			String portletId, String actionId, boolean strict)
		throws PortalException, SystemException {

		Layout layout = LayoutLocalServiceUtil.fetchLayout(plid);

		return contains(
			permissionChecker, groupId, layout, portletId, actionId, strict);
	}

	public boolean contains(
			PermissionChecker permissionChecker, long plid, Portlet portlet,
			String actionId)
		throws PortalException, SystemException {

		Layout layout = LayoutLocalServiceUtil.fetchLayout(plid);

		return contains(
			permissionChecker, layout, portlet, actionId, DEFAULT_STRICT);
	}

	public boolean contains(
			PermissionChecker permissionChecker, long plid, Portlet portlet,
			String actionId, boolean strict)
		throws PortalException, SystemException {

		Layout layout = LayoutLocalServiceUtil.fetchLayout(plid);

		return contains(
			permissionChecker, 0, layout, portlet, actionId, strict);
	}

	public boolean contains(
			PermissionChecker permissionChecker, long plid, String portletId,
			String actionId)
		throws PortalException, SystemException {

		Layout layout = LayoutLocalServiceUtil.fetchLayout(plid);

		return contains(
			permissionChecker, layout, portletId, actionId, DEFAULT_STRICT);
	}

	public boolean contains(
			PermissionChecker permissionChecker, long plid, String portletId,
			String actionId, boolean strict)
		throws PortalException, SystemException {

		Layout layout = LayoutLocalServiceUtil.fetchLayout(plid);

		return contains(
			permissionChecker, 0, layout, portletId, actionId, strict);
	}

	public boolean contains(
			PermissionChecker permissionChecker, String portletId,
			String actionId)
		throws PortalException, SystemException {

		return contains(permissionChecker, 0, portletId, actionId);
	}

	public String getPrimaryKey(long plid, String portletId) {
		return String.valueOf(plid).concat(
			PortletConstants.LAYOUT_SEPARATOR).concat(portletId);
	}

	public boolean hasLayoutManagerPermission(
		String portletId, String actionId) {

		try {
			portletId = PortletConstants.getRootPortletId(portletId);

			List<String> layoutManagerActions =
				ResourceActionsUtil.getPortletResourceLayoutManagerActions(
					portletId);

			return layoutManagerActions.contains(actionId);
		}
		catch (Exception e) {
			_log.error(e, e);

			return false;
		}
	}

	protected boolean hasConfigurePermission(
			PermissionChecker permissionChecker, Layout layout,
			String portletId, String actionId)
		throws PortalException, SystemException {

		if (!actionId.equals(ActionKeys.CONFIGURATION)) {
			return false;
		}

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			layout.getCompanyId(), portletId);

		if (portlet.isPreferencesUniquePerLayout()) {
			return LayoutPermissionUtil.contains(
				permissionChecker, layout, ActionKeys.CONFIGURE_PORTLETS);
		}

		return GroupPermissionUtil.contains(
			permissionChecker, layout.getGroupId(),
			ActionKeys.CONFIGURE_PORTLETS);
	}

	protected boolean hasCustomizePermission(
			PermissionChecker permissionChecker, Layout layout,
			String portletId, String actionId)
		throws PortalException, SystemException {

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		if (layoutTypePortlet.isCustomizedView() &&
			layoutTypePortlet.isPortletCustomizable(portletId) &&
			LayoutPermissionUtil.contains(
				permissionChecker, layout, ActionKeys.CUSTOMIZE)) {

			if (actionId.equals(ActionKeys.VIEW)) {
				return true;
			}
			else if (actionId.equals(ActionKeys.CONFIGURATION)) {
				Portlet portlet = PortletLocalServiceUtil.getPortletById(
					layout.getCompanyId(), portletId);

				if (portlet.isPreferencesUniquePerLayout()) {
					return true;
				}
			}
		}

		return false;
	}

	private static Log _log = LogFactoryUtil.getLog(
		PortletPermissionImpl.class);

}