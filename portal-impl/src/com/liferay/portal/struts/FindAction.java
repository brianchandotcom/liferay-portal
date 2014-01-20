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
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
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
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.sites.util.SitesUtil;

import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Brian Wing Shun Chan
 */
public abstract class FindAction extends Action {

	public FindAction() {
		_portletIds = initPortletIds();

		if (ArrayUtil.isEmpty(_portletIds)) {
			throw new RuntimeException("Portlet IDs cannot be null or empty");
		}
	}

	@Override
	public ActionForward execute(
			ActionMapping actionMapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		try {
			long plid = ParamUtil.getLong(request, "p_l_id");
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

			Object[] plidAndPortletId = getPlidAndPortletId(
				themeDisplay, groupId, plid, _portletIds);

			plid = (Long)plidAndPortletId[0];

			setTargetGroup(request, groupId, plid);

			String portletId = (String)plidAndPortletId[1];

			PortletURL portletURL = PortletURLFactoryUtil.create(
				request, portletId, plid, PortletRequest.RENDER_PHASE);

			portletURL.setParameter(
				"struts_action", getStrutsAction(request, portletId));

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

			return null;
		}
		catch (Exception e) {
			String noSuchEntryRedirect = ParamUtil.getString(
				request, "noSuchEntryRedirect");

			noSuchEntryRedirect = PortalUtil.escapeRedirect(
				noSuchEntryRedirect);

			if (Validator.isNotNull(noSuchEntryRedirect) &&
				(e instanceof NoSuchLayoutException)) {

				response.sendRedirect(noSuchEntryRedirect);
			}
			else {
				PortalUtil.sendError(e, request, response);
			}

			return null;
		}
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

	protected static Object[] getPlidAndPortletId(
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
					if (!layoutTypePortlet.hasPortletId(portletId, false) ||
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

	protected static String getPortletId(
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

	protected static void setTargetGroup(
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

	protected abstract long getGroupId(long primaryKey) throws Exception;

	protected abstract String getPrimaryKeyParameterName();

	protected abstract String getStrutsAction(
		HttpServletRequest request, String portletId);

	protected abstract String[] initPortletIds();

	protected PortletURL processPortletURL(
			HttpServletRequest request, PortletURL portletURL)
		throws Exception {

		return portletURL;
	}

	protected void setPrimaryKeyParameter(
			PortletURL portletURL, long primaryKey)
		throws Exception {

		portletURL.setParameter(
			getPrimaryKeyParameterName(), String.valueOf(primaryKey));
	}

	private static Log _log = LogFactoryUtil.getLog(FindAction.class);

	private String[] _portletIds;

}