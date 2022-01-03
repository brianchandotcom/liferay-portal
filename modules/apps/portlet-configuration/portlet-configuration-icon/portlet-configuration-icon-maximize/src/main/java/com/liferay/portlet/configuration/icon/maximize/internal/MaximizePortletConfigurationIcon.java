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

package com.liferay.portlet.configuration.icon.maximize.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutTypeController;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.portlet.configuration.icon.PortletConfigurationIcon;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsValues;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.WindowState;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = PortletConfigurationIcon.class)
public class MaximizePortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	@Override
	public String getCssClass() {
		return "portlet-maximize portlet-maximize-icon";
	}

	@Override
	public String getMessage(PortletRequest portletRequest) {
		return LanguageUtil.get(
			getResourceBundle(getLocale(portletRequest)), "maximize");
	}

	@Override
	public String getMethod() {
		return "get";
	}

	@Override
	public String getOnClick(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		return StringBundler.concat(
			"submitForm(document.hrefFm, '",
			HtmlUtil.escapeJS(portletDisplay.getURLMax()), "'); return false;");
	}

	@Override
	public String getURL(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		return portletDisplay.getURLMax();
	}

	@Override
	public double getWeight() {
		return 7.0;
	}

	@Override
	public boolean isShow(PortletRequest portletRequest) {
		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (!GetterUtil.getBoolean(
				themeDisplay.getThemeSetting(
					"show-maximize-minimize-application-links"))) {

			return false;
		}

		Layout layout = themeDisplay.getLayout();

		if (!layout.isTypePortlet()) {
			return false;
		}

		LayoutTypePortlet layoutTypePortlet =
			themeDisplay.getLayoutTypePortlet();

		LayoutTypeController layoutTypeController =
			layoutTypePortlet.getLayoutTypeController();

		if (layoutTypeController.isFullPageDisplayable()) {
			return false;
		}

		Portlet portlet = (Portlet)portletRequest.getAttribute(
			WebKeys.RENDER_PORTLET);

		if (!portlet.hasWindowState(
				portletRequest.getResponseContentType(),
				WindowState.MAXIMIZED)) {

			return false;
		}

		Group group = themeDisplay.getScopeGroup();

		if ((!themeDisplay.isSignedIn() ||
			 (group.hasStagingGroup() && !group.isStagingGroup()) ||
			 !_hasUpdateLayoutPermission(themeDisplay)) &&
			!PropsValues.LAYOUT_GUEST_SHOW_MAX_ICON) {

			return false;
		}

		return true;
	}

	@Override
	public boolean isToolTip() {
		return false;
	}

	private boolean _hasUpdateLayoutPermission(ThemeDisplay themeDisplay) {
		try {
			return LayoutPermissionUtil.contains(
				themeDisplay.getPermissionChecker(), themeDisplay.getLayout(),
				ActionKeys.UPDATE);
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);

			return false;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MaximizePortletConfigurationIcon.class);

}