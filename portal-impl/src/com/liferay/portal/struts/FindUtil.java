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

package com.liferay.portal.struts;

import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.impl.VirtualLayout;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.permission.LayoutPermissionUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.sites.util.SitesUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Julio Camarero
 */
public class FindUtil {

	public static Object[] getPlidAndPortletId(
			ThemeDisplay themeDisplay, long groupId, long plid,
			String[] portletIds)
		throws Exception {

		if ((plid != LayoutConstants.DEFAULT_PLID) &&
			(groupId == themeDisplay.getScopeGroupId())) {

			try {
				Layout layout = LayoutLocalServiceUtil.getLayout(plid);

				LayoutTypePortlet layoutTypePortlet =
					(LayoutTypePortlet)layout.getLayoutType();

				for (String portletId : portletIds) {
					if (!layoutTypePortlet.hasPortletId(portletId) ||
						!LayoutPermissionUtil.contains(
							themeDisplay.getPermissionChecker(), layout,
							ActionKeys.VIEW)) {

						continue;
					}

					portletId = getPortletId(layoutTypePortlet, portletId);

					return new Object[] {plid, portletId};
				}
			}
			catch (NoSuchLayoutException nsle) {
			}
		}

		Object[] plidAndPortletId = fetchPlidAndPortletId(
			themeDisplay.getPermissionChecker(), groupId, portletIds);

		if ((plidAndPortletId == null) &&
			SitesUtil.isUserGroupLayoutSetViewable(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroup())) {

			plidAndPortletId = fetchPlidAndPortletId(
				themeDisplay.getPermissionChecker(),
				themeDisplay.getScopeGroupId(), portletIds);
		}

		if (plidAndPortletId != null) {
			return plidAndPortletId;
		}

		throw new NoSuchLayoutException();
	}

	public static String getPortletId(
		LayoutTypePortlet layoutTypePortlet, String portletId) {

		for (String curPortletId : layoutTypePortlet.getPortletIds()) {
			String curRootPortletId = PortletConstants.getRootPortletId(
				curPortletId);

			if (portletId.equals(curRootPortletId)) {
				return curPortletId;
			}
		}

		return portletId;
	}

	public static void setTargetGroup(
			HttpServletRequest request, long groupId, long plid)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		Layout layout = LayoutLocalServiceUtil.getLayout(plid);

		if ((groupId == layout.getGroupId()) ||
			(layout.isPrivateLayout() &&
			 !SitesUtil.isUserGroupLayoutSetViewable(
					permissionChecker, layout.getGroup()))) {

			return;
		}

		Group targetGroup = GroupLocalServiceUtil.getGroup(groupId);

		layout = new VirtualLayout(layout, targetGroup);

		request.setAttribute(WebKeys.LAYOUT, layout);
	}

	protected static Object[] fetchPlidAndPortletId(
			PermissionChecker permissionChecker, long groupId,
			String[] portletIds)
		throws Exception {

		for (String portletId : portletIds) {
			long plid = PortalUtil.getPlidFromPortletId(groupId, portletId);

			if (plid == LayoutConstants.DEFAULT_PLID) {
				continue;
			}

			Layout layout = LayoutLocalServiceUtil.getLayout(plid);

			if (!LayoutPermissionUtil.contains(
					permissionChecker, layout, ActionKeys.VIEW)) {

				continue;
			}

			LayoutTypePortlet layoutTypePortlet =
				(LayoutTypePortlet)layout.getLayoutType();

			portletId = getPortletId(layoutTypePortlet, portletId);

			return new Object[] {plid, portletId};
		}

		return null;
	}

}