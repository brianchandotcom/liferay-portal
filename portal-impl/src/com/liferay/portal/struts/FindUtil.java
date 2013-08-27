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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
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

	protected Object[] fetchPlidAndPortletId(
			PermissionChecker permissionChecker, long groupId)
		throws Exception {

		for (String portletId : _portletIds) {
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

	protected Object[] getPlidAndPortletId(
			HttpServletRequest request, long plid, long primaryKey)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		long groupId = ParamUtil.getLong(
			request, "groupId", themeDisplay.getScopeGroupId());

		if (primaryKey > 0) {
			try {
				groupId = getGroupId(primaryKey);
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug(e, e);
				}
			}
		}

		if ((plid != LayoutConstants.DEFAULT_PLID) &&
			(groupId == themeDisplay.getScopeGroupId())) {

			try {
				Layout layout = LayoutLocalServiceUtil.getLayout(plid);

				LayoutTypePortlet layoutTypePortlet =
					(LayoutTypePortlet)layout.getLayoutType();

				for (String portletId : _portletIds) {
					if (!layoutTypePortlet.hasPortletId(portletId) ||
						!LayoutPermissionUtil.contains(
							permissionChecker, layout, ActionKeys.VIEW)) {

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
			permissionChecker, groupId);

		if ((plidAndPortletId == null) &&
			SitesUtil.isUserGroupLayoutSetViewable(
				permissionChecker, themeDisplay.getScopeGroup())) {

			plidAndPortletId = fetchPlidAndPortletId(
				permissionChecker, themeDisplay.getScopeGroupId());
		}

		if (plidAndPortletId != null) {
			return plidAndPortletId;
		}

		throw new NoSuchLayoutException();
	}

	protected String getPortletId(
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

	protected void setTargetGroup(
			HttpServletRequest request, long plid, long primaryKey)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		long entityGroupId = getGroupId(primaryKey);

		Layout layout = LayoutLocalServiceUtil.getLayout(plid);

		if ((entityGroupId == layout.getGroupId()) ||
			(layout.isPrivateLayout() &&
				!SitesUtil.isUserGroupLayoutSetViewable(
					permissionChecker, layout.getGroup()))) {

			return;
		}

		Group targetGroup = GroupLocalServiceUtil.getGroup(entityGroupId);

		layout = new VirtualLayout(layout, targetGroup);

		request.setAttribute(WebKeys.LAYOUT, layout);
	}

	private static Log _log = LogFactoryUtil.getLog(FindUtil.class);

}