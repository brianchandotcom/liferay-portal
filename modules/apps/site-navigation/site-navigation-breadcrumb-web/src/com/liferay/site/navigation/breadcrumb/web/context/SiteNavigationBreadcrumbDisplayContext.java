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
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.display.template.PortletDisplayTemplate;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.site.navigation.breadcrumb.web.configuration.SiteNavigationBreadcrumbPortletInstanceConfiguration;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Julio Camarero
 */
public class SiteNavigationBreadcrumbDisplayContext {

	public SiteNavigationBreadcrumbDisplayContext(HttpServletRequest request)
		throws ConfigurationException {

		_request = request;

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			com.liferay.portal.util.WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		_siteNavigationBreadcrumbPortletInstanceConfiguration =
			portletDisplay.getPortletInstanceConfiguration(
				SiteNavigationBreadcrumbPortletInstanceConfiguration.class);
	}

	public String getDDMTemplateKey() {
		if (_ddmTemplateKey != null) {
			return _ddmTemplateKey;
		}

		String displayStyle = getDisplayStyle();

		if (displayStyle != null) {
			_ddmTemplateKey = getPortletDisplayTemplate().getDDMTemplateKey(
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
			_siteNavigationBreadcrumbPortletInstanceConfiguration.
				displayStyle());

		return _displayStyle;
	}

	public long getDisplayStyleGroupId() {
		if (_displayStyleGroupId != 0) {
			return _displayStyleGroupId;
		}

		_displayStyleGroupId =
			_siteNavigationBreadcrumbPortletInstanceConfiguration.
				displayStyleGroupId();

		_displayStyleGroupId = ParamUtil.getLong(
			_request, "displayStyleGroupId",
			_siteNavigationBreadcrumbPortletInstanceConfiguration.
				displayStyleGroupId());

		if (_displayStyleGroupId <= 0) {
			ThemeDisplay themeDisplay = (ThemeDisplay) _request.getAttribute(
				WebKeys.THEME_DISPLAY);

			_displayStyleGroupId = themeDisplay.getSiteGroupId();
		}

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
			_siteNavigationBreadcrumbPortletInstanceConfiguration.
				showCurrentGroup());

		return _showCurrentGroup;
	}

	public boolean isShowGuestGroup() {
		if (_showGuestGroup != null) {
			return _showGuestGroup;
		}

		_showGuestGroup = ParamUtil.getBoolean(
			_request, "showGuestGroup",
			_siteNavigationBreadcrumbPortletInstanceConfiguration.
				showGuestGroup());

		return _showGuestGroup;
	}

	public boolean isShowLayout() {
		if (_showLayout != null) {
			return _showLayout;
		}

		_showLayout = ParamUtil.getBoolean(
			_request, "showLayout",
			_siteNavigationBreadcrumbPortletInstanceConfiguration.showLayout());

		return _showLayout;
	}

	public boolean isShowParentGroups() {
		if (_showParentGroups != null) {
			return _showParentGroups;
		}

		_showParentGroups = ParamUtil.getBoolean(
			_request, "showParentGroups",
			_siteNavigationBreadcrumbPortletInstanceConfiguration.
				showParentGroups());

		return _showParentGroups;
	}

	public boolean isShowPortletBreadcrumb() {
		if (_showPortletBreadcrumb != null) {
			return _showPortletBreadcrumb;
		}

		_showPortletBreadcrumb = ParamUtil.getBoolean(
			_request, "showPortletBreadcrumb",
			_siteNavigationBreadcrumbPortletInstanceConfiguration.
				showPortletBreadcrumb());

		return _showPortletBreadcrumb;
	}

	protected PortletDisplayTemplate getPortletDisplayTemplate() {
		Registry registry = RegistryUtil.getRegistry();

		return registry.getService(PortletDisplayTemplate.class);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SiteNavigationBreadcrumbDisplayContext.class);

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
	private final SiteNavigationBreadcrumbPortletInstanceConfiguration
		_siteNavigationBreadcrumbPortletInstanceConfiguration;

}