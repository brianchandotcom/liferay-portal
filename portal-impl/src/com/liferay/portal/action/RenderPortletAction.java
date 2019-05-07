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

package com.liferay.portal.action;

import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortletContainerUtil;
import com.liferay.portal.kernel.portlet.WindowStateFactory;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.struts.Action;
import com.liferay.portal.struts.model.ActionForward;
import com.liferay.portal.struts.model.ActionMapping;

import javax.portlet.WindowState;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 */
public class RenderPortletAction implements Action {

	@Override
	public ActionForward execute(
			ActionMapping actionMapping, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		themeDisplay.setAjax(true);

		String ajaxId = httpServletRequest.getParameter("ajax_id");

		long companyId = PortalUtil.getCompanyId(httpServletRequest);
		User user = PortalUtil.getUser(httpServletRequest);
		Layout layout = (Layout)httpServletRequest.getAttribute(WebKeys.LAYOUT);
		String portletId = ParamUtil.getString(httpServletRequest, "p_p_id");

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			companyId, portletId);

		String columnId = ParamUtil.getString(httpServletRequest, "p_p_col_id");
		int columnPos = ParamUtil.getInteger(httpServletRequest, "p_p_col_pos");
		int columnCount = ParamUtil.getInteger(
			httpServletRequest, "p_p_col_count");

		Boolean boundary = null;

		String boundaryParam = ParamUtil.getString(
			httpServletRequest, "p_p_boundary", null);

		if (boundaryParam != null) {
			boundary = GetterUtil.getBoolean(boundaryParam);
		}

		Boolean decorate = null;

		String decorateParam = ParamUtil.getString(
			httpServletRequest, "p_p_decorate", null);

		if (decorateParam != null) {
			decorate = GetterUtil.getBoolean(decorateParam);
		}

		boolean staticPortlet = ParamUtil.getBoolean(
			httpServletRequest, "p_p_static");

		if (staticPortlet) {
			portlet = (Portlet)portlet.clone();

			portlet.setStatic(true);

			boolean staticStartPortlet = ParamUtil.getBoolean(
				httpServletRequest, "p_p_static_start");

			portlet.setStaticStart(staticStartPortlet);
		}

		if (ajaxId != null) {
			httpServletResponse.setHeader("Ajax-ID", ajaxId);
		}

		WindowState windowState = WindowStateFactory.getWindowState(
			ParamUtil.getString(httpServletRequest, "p_p_state"));

		PortalUtil.updateWindowState(
			portletId, user, layout, windowState, httpServletRequest);

		httpServletRequest = PortletContainerUtil.setupOptionalRenderParameters(
			httpServletRequest, null, columnId, columnPos, columnCount,
			boundary, decorate);

		PortletContainerUtil.processPublicRenderParameters(
			httpServletRequest, themeDisplay.getLayout());

		PortletContainerUtil.render(
			httpServletRequest, httpServletResponse, portlet);

		return null;
	}

}