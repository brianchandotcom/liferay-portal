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

package com.liferay.site.navigation.breadcrumb.web.context;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.portletdisplaytemplate.util.PortletDisplayTemplateUtil;
import com.liferay.site.navigation.breadcrumb.web.configuration.BreadcrumbPortletInstanceConfiguration;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Julio Camarero
 */
public class BreadcrumbDisplayContext {

	public BreadcrumbDisplayContext(
		HttpServletRequest request, BreadcrumbPortletInstanceConfiguration
			breadcrumbPortletInstanceSettings) {

		_request = request;
		_breadcrumbPortletInstanceSettings = breadcrumbPortletInstanceSettings;
	}

	public String getDDMTemplateKey() {
		if (_ddmTemplateKey != null) {
			return _ddmTemplateKey;
		}

		String displayStyle = getDisplayStyle();

		if (displayStyle != null) {
			_ddmTemplateKey = PortletDisplayTemplateUtil.getDDMTemplateKey(
				displayStyle);
		}

		return _ddmTemplateKey;
	}

	public String getDisplayStyle() {
		if (_displayStyle != null) {
			return _displayStyle;
		}

		_displayStyle = ParamUtil.getString(
			_request, "displayStyle",
			_breadcrumbPortletInstanceSettings.displayStyle());

		return _displayStyle;
	}

	public long getDisplayStyleGroupId() {
		if (_displayStyleGroupId != 0) {
			return _displayStyleGroupId;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		_displayStyleGroupId = ParamUtil.getLong(
			_request, "displayStyleGroupId",
			_breadcrumbPortletInstanceSettings.displayStyleGroupId(
				themeDisplay.getSiteGroupId()));

		return _displayStyleGroupId;
	}

	public String getPortletResource() {
		if (_portletResource != null) {
			return _portletResource;
		}

		_portletResource = ParamUtil.getString(_request, "portletResource");

		return _portletResource;
	}

	public boolean isShowCurrentGroup() {
		if (_showCurrentGroup != null) {
			return _showCurrentGroup;
		}

		_showCurrentGroup = ParamUtil.getBoolean(
			_request, "showCurrentGroup",
			_breadcrumbPortletInstanceSettings.showCurrentGroup());

		return _showCurrentGroup;
	}

	public boolean isShowGuestGroup() {
		if (_showGuestGroup != null) {
			return _showGuestGroup;
		}

		_showGuestGroup = ParamUtil.getBoolean(
			_request, "showGuestGroup",
			_breadcrumbPortletInstanceSettings.showGuestGroup());

		return _showGuestGroup;
	}

	public boolean isShowLayout() {
		if (_showLayout != null) {
			return _showLayout;
		}

		_showLayout = ParamUtil.getBoolean(
			_request, "showLayout",
			_breadcrumbPortletInstanceSettings.showLayout());

		return _showLayout;
	}

	public boolean isShowParentGroups() {
		if (_showParentGroups != null) {
			return _showParentGroups;
		}

		_showParentGroups = ParamUtil.getBoolean(
			_request, "showParentGroups",
			_breadcrumbPortletInstanceSettings.showParentGroups());

		return _showParentGroups;
	}

	public boolean isShowPortletBreadcrumb() {
		if (_showPortletBreadcrumb != null) {
			return _showPortletBreadcrumb;
		}

		_showPortletBreadcrumb = ParamUtil.getBoolean(
			_request, "showPortletBreadcrumb",
			_breadcrumbPortletInstanceSettings.showPortletBreadcrumb());

		return _showPortletBreadcrumb;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BreadcrumbDisplayContext.class);

	private final BreadcrumbPortletInstanceConfiguration
		_breadcrumbPortletInstanceSettings;
	private String _ddmTemplateKey;
	private String _displayStyle;
	private long _displayStyleGroupId;
	private String _portletResource;
	private final HttpServletRequest _request;
	private Boolean _showCurrentGroup;
	private Boolean _showGuestGroup;
	private Boolean _showLayout;
	private Boolean _showParentGroups;
	private Boolean _showPortletBreadcrumb;

}