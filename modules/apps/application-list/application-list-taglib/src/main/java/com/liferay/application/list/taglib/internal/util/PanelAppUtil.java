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

package com.liferay.application.list.taglib.internal.util;

import com.liferay.application.list.PanelApp;
import com.liferay.application.list.taglib.internal.servlet.ServletContextUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class PanelAppUtil {

	public static String getLabel(
		HttpServletRequest httpServletRequest, PanelApp panelApp) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		String label = HtmlUtil.escape(
			panelApp.getLabel(themeDisplay.getLocale()));

		if (Validator.isNull(label)) {
			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				themeDisplay.getCompanyId(), panelApp.getPortletId());

			label = HtmlUtil.escape(
				PortalUtil.getPortletTitle(
					portlet, ServletContextUtil.getServletContext(),
					themeDisplay.getLocale()));
		}

		return label;
	}

	public static String getURL(
		HttpServletRequest httpServletRequest, PanelApp panelApp) {

		try {
			return String.valueOf(panelApp.getPortletURL(httpServletRequest));
		}
		catch (PortalException portalException) {
			_log.error("Unable to get portlet URL", portalException);
		}

		return null;
	}

	public static boolean isActive(
		HttpServletRequest httpServletRequest, PanelApp panelApp) {

		HttpServletRequest originalHttpServletRequest =
			PortalUtil.getOriginalServletRequest(httpServletRequest);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		String parameterName =
			PortalUtil.getPortletNamespace(themeDisplay.getPpid()) +
				"portletResource";

		String portletResource = ParamUtil.getString(
			originalHttpServletRequest, parameterName);

		boolean active = Objects.equals(
			portletResource, panelApp.getPortletId());

		if (Validator.isNull(portletResource)) {
			active = Objects.equals(
				ParamUtil.getString(
					httpServletRequest, "selPpid", themeDisplay.getPpid()),
				panelApp.getPortletId());
		}

		return active;
	}

	private static final Log _log = LogFactoryUtil.getLog(PanelAppUtil.class);

}