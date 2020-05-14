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

import com.liferay.portal.kernel.exception.NoSuchLayoutException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.impl.VirtualLayout;
import com.liferay.portal.kernel.portlet.PortletLayoutFinder;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
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
public abstract class FindStrutsAction implements StrutsAction {

	@Override
	public String execute(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		try {
			long primaryKey = ParamUtil.getLong(
				httpServletRequest, getPrimaryKeyParameterName());

			long groupId = ParamUtil.getLong(
				httpServletRequest, "groupId", themeDisplay.getScopeGroupId());

			if (primaryKey > 0) {
				try {
					long overrideGroupId = getGroupId(primaryKey);

					if (overrideGroupId > 0) {
						groupId = overrideGroupId;
					}
				}
				catch (Exception exception) {
					if (_log.isDebugEnabled()) {
						_log.debug(exception, exception);
					}
				}
			}

			PortletLayoutFinder portletLayoutFinder = getPortletLayoutFinder();

			PortletLayoutFinder.Result result = portletLayoutFinder.find(
				themeDisplay, groupId);

			Layout layout = _setTargetLayout(
				httpServletRequest, groupId, result.getPlid());

			if (!LayoutPermissionUtil.contains(
					themeDisplay.getPermissionChecker(), layout, true,
					ActionKeys.VIEW)) {

				if (!themeDisplay.isSignedIn() && result.isSignInRequired()) {
					String redirect = HttpUtil.addParameter(
						PortalUtil.getPathMain() + "/portal/login", "redirect",
						PortalUtil.getCurrentCompleteURL(httpServletRequest));

					httpServletResponse.sendRedirect(redirect);

					return null;
				}

				throw new PrincipalException.MustHavePermission(
					themeDisplay.getPermissionChecker(), Layout.class.getName(),
					layout.getLayoutId(), ActionKeys.VIEW);
			}

			String portletId = result.getPortletId();

			PortletURL portletURL = PortletURLFactoryUtil.create(
				httpServletRequest, portletId, layout,
				PortletRequest.RENDER_PHASE);

			addRequiredParameters(httpServletRequest, portletId, portletURL);

			boolean inheritRedirect = ParamUtil.getBoolean(
				httpServletRequest, "inheritRedirect");

			String redirect = null;

			if (inheritRedirect) {
				String noSuchEntryRedirect = ParamUtil.getString(
					httpServletRequest, "noSuchEntryRedirect");

				redirect = HttpUtil.getParameter(
					noSuchEntryRedirect, "redirect", false);

				redirect = HttpUtil.decodeURL(redirect);
			}
			else {
				redirect = ParamUtil.getString(httpServletRequest, "redirect");
			}

			if (Validator.isNotNull(redirect)) {
				portletURL.setParameter("redirect", redirect);
			}

			setPrimaryKeyParameter(portletURL, primaryKey);

			portletURL.setPortletMode(PortletMode.VIEW);
			portletURL.setWindowState(WindowState.NORMAL);

			portletURL = processPortletURL(httpServletRequest, portletURL);

			httpServletResponse.sendRedirect(portletURL.toString());
		}
		catch (Exception exception) {
			String noSuchEntryRedirect = ParamUtil.getString(
				httpServletRequest, "noSuchEntryRedirect");

			noSuchEntryRedirect = PortalUtil.escapeRedirect(
				noSuchEntryRedirect);

			if (Validator.isNotNull(noSuchEntryRedirect) &&
				(exception instanceof NoSuchLayoutException ||
				 exception instanceof PrincipalException)) {

				httpServletResponse.sendRedirect(noSuchEntryRedirect);
			}
			else {
				PortalUtil.sendError(
					exception, httpServletRequest, httpServletResponse);
			}
		}

		return null;
	}

	protected abstract void addRequiredParameters(
		HttpServletRequest httpServletRequest, String portletId,
		PortletURL portletURL);

	protected abstract long getGroupId(long primaryKey) throws Exception;

	protected abstract PortletLayoutFinder getPortletLayoutFinder();

	protected abstract String getPrimaryKeyParameterName();

	protected PortletURL processPortletURL(
			HttpServletRequest httpServletRequest, PortletURL portletURL)
		throws Exception {

		return portletURL;
	}

	protected void setPrimaryKeyParameter(
			PortletURL portletURL, long primaryKey)
		throws Exception {

		portletURL.setParameter(
			getPrimaryKeyParameterName(), String.valueOf(primaryKey));
	}

	private static Layout _setTargetLayout(
			HttpServletRequest httpServletRequest, long groupId, long plid)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
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

		httpServletRequest.setAttribute(WebKeys.LAYOUT, layout);

		return layout;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FindStrutsAction.class);

}