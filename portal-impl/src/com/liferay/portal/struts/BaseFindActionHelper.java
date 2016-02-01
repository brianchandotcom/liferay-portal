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

package com.liferay.portal.struts;

import com.liferay.portal.exception.NoSuchLayoutException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletLayoutFinder;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.impl.VirtualLayout;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.permission.LayoutPermissionUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.sites.kernel.util.SitesUtil;

import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Adolfo Pérez
 */
public abstract class BaseFindActionHelper implements FindActionHelper {

	@Override
	public void execute(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		try {
			long primaryKey = ParamUtil.getLong(
				request, getPrimaryKeyParameterName());

			long groupId = ParamUtil.getLong(
				request, "groupId", themeDisplay.getScopeGroupId());

			if (primaryKey > 0) {
				try {
					long overrideGroupId = getGroupId(primaryKey);

					if (overrideGroupId > 0) {
						groupId = overrideGroupId;
					}
				}
				catch (Exception e) {
					if (_log.isDebugEnabled()) {
						_log.debug(e, e);
					}
				}
			}

			PortletLayoutFinder portletLayoutFinder = getPortletLayoutFinder();

			PortletLayoutFinder.Result result = portletLayoutFinder.find(
				themeDisplay, groupId);

			long plid = result.getPlid();

			Layout layout = setTargetLayout(request, groupId, plid);

			LayoutPermissionUtil.check(
				themeDisplay.getPermissionChecker(), layout, true,
				ActionKeys.VIEW);

			String portletId = result.getPortletId();

			PortletURL portletURL = PortletURLFactoryUtil.create(
				request, portletId, plid, PortletRequest.RENDER_PHASE);

			addRequiredParameters(request, portletId, portletURL);

			boolean inheritRedirect = ParamUtil.getBoolean(
				request, "inheritRedirect");

			String redirect = null;

			if (inheritRedirect) {
				String noSuchEntryRedirect = ParamUtil.getString(
					request, "noSuchEntryRedirect");

				redirect = HttpUtil.getParameter(
					noSuchEntryRedirect, "redirect", false);

				redirect = HttpUtil.decodeURL(redirect);
			}
			else {
				redirect = ParamUtil.getString(request, "redirect");
			}

			if (Validator.isNotNull(redirect)) {
				portletURL.setParameter("redirect", redirect);
			}

			setPrimaryKeyParameter(portletURL, primaryKey);

			portletURL.setPortletMode(PortletMode.VIEW);
			portletURL.setWindowState(WindowState.NORMAL);

			portletURL = processPortletURL(request, portletURL);

			response.sendRedirect(portletURL.toString());
		}
		catch (Exception e) {
			String noSuchEntryRedirect = ParamUtil.getString(
				request, "noSuchEntryRedirect");

			noSuchEntryRedirect = PortalUtil.escapeRedirect(
				noSuchEntryRedirect);

			if (Validator.isNotNull(noSuchEntryRedirect) &&
				(e instanceof NoSuchLayoutException ||
				 e instanceof PrincipalException)) {

				response.sendRedirect(noSuchEntryRedirect);
			}
			else {
				PortalUtil.sendError(e, request, response);
			}
		}
	}

	@Override
	public abstract long getGroupId(long primaryKey) throws Exception;

	@Override
	public abstract String getPrimaryKeyParameterName();

	@Override
	public abstract PortletURL processPortletURL(
			HttpServletRequest request, PortletURL portletURL)
		throws Exception;

	@Override
	public abstract void setPrimaryKeyParameter(
			PortletURL portletURL, long primaryKey)
		throws Exception;

	protected static Layout setTargetLayout(
			HttpServletRequest request, long groupId, long plid)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		Group group = GroupLocalServiceUtil.getGroup(groupId);
		Layout layout = LayoutLocalServiceUtil.getLayout(plid);

		if ((groupId == layout.getGroupId()) ||
			(group.getParentGroupId() == layout.getGroupId()) ||
			(layout.isPrivateLayout() &&
			 !SitesUtil.isUserGroupLayoutSetViewable(
				 permissionChecker, layout.getGroup()))) {

			return layout;
		}

		layout = new VirtualLayout(layout, group);

		request.setAttribute(WebKeys.LAYOUT, layout);

		return layout;
	}

	protected abstract void addRequiredParameters(
		HttpServletRequest request, String portletId, PortletURL portletURL);

	protected abstract PortletLayoutFinder getPortletLayoutFinder();

	private static final Log _log = LogFactoryUtil.getLog(
		BaseFindActionHelper.class);

}