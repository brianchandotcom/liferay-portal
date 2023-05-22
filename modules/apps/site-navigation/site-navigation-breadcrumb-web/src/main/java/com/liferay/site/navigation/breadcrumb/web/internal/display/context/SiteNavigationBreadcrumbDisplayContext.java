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

package com.liferay.site.navigation.breadcrumb.web.internal.display.context;

import com.liferay.dynamic.data.mapping.kernel.DDMTemplate;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.portletdisplaytemplate.PortletDisplayTemplateManagerUtil;
import com.liferay.portal.kernel.servlet.taglib.ui.BreadcrumbEntry;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.navigation.breadcrumb.web.internal.configuration.SiteNavigationBreadcrumbPortletInstanceConfiguration;
import com.liferay.site.navigation.breadcrumb.web.internal.portlet.display.template.PortletDisplayTemplateUtil;
import com.liferay.site.navigation.taglib.servlet.taglib.util.BreadcrumbEntriesUtil;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Julio Camarero
 */
public class SiteNavigationBreadcrumbDisplayContext {

	public SiteNavigationBreadcrumbDisplayContext(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws ConfigurationException {

		_httpServletRequest = httpServletRequest;
		_httpServletResponse = httpServletResponse;

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		_siteNavigationBreadcrumbPortletInstanceConfiguration =
			portletDisplay.getPortletInstanceConfiguration(
				SiteNavigationBreadcrumbPortletInstanceConfiguration.class);
	}

	public List<BreadcrumbEntry> getBreadcrumbEntries() {
		if (_breadcrumbEntries != null) {
			return _breadcrumbEntries;
		}

		_breadcrumbEntries = BreadcrumbEntriesUtil.getBreadcrumbEntries(
			_httpServletRequest, isShowCurrentGroup(), isShowGuestGroup(),
			isShowLayout(), isShowParentGroups(), isShowPortletBreadcrumb());

		return _breadcrumbEntries;
	}

	public String getDisplayStyle() {
		if (_displayStyle != null) {
			return _displayStyle;
		}

		_displayStyle = ParamUtil.getString(
			_httpServletRequest, "displayStyle",
			_siteNavigationBreadcrumbPortletInstanceConfiguration.
				displayStyle());

		return _displayStyle;
	}

	public long getDisplayStyleGroupId() {
		if (_displayStyleGroupId != 0) {
			return _displayStyleGroupId;
		}

		_displayStyleGroupId = ParamUtil.getLong(
			_httpServletRequest, "displayStyleGroupId",
			_siteNavigationBreadcrumbPortletInstanceConfiguration.
				displayStyleGroupId());

		if (_displayStyleGroupId <= 0) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)_httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			_displayStyleGroupId = themeDisplay.getSiteGroupId();
		}

		return _displayStyleGroupId;
	}

	public String getPortletResource() {
		if (_portletResource != null) {
			return _portletResource;
		}

		_portletResource = ParamUtil.getString(
			_httpServletRequest, "portletResource");

		return _portletResource;
	}

	public boolean isShowCurrentGroup() {
		if (_showCurrentGroup != null) {
			return _showCurrentGroup;
		}

		_showCurrentGroup = ParamUtil.getBoolean(
			_httpServletRequest, "showCurrentGroup",
			_siteNavigationBreadcrumbPortletInstanceConfiguration.
				showCurrentGroup());

		return _showCurrentGroup;
	}

	public boolean isShowGuestGroup() {
		if (_showGuestGroup != null) {
			return _showGuestGroup;
		}

		_showGuestGroup = ParamUtil.getBoolean(
			_httpServletRequest, "showGuestGroup",
			_siteNavigationBreadcrumbPortletInstanceConfiguration.
				showGuestGroup());

		return _showGuestGroup;
	}

	public boolean isShowLayout() {
		if (_showLayout != null) {
			return _showLayout;
		}

		_showLayout = ParamUtil.getBoolean(
			_httpServletRequest, "showLayout",
			_siteNavigationBreadcrumbPortletInstanceConfiguration.showLayout());

		return _showLayout;
	}

	public boolean isShowParentGroups() {
		if (_showParentGroups != null) {
			return _showParentGroups;
		}

		_showParentGroups = ParamUtil.getBoolean(
			_httpServletRequest, "showParentGroups",
			_siteNavigationBreadcrumbPortletInstanceConfiguration.
				showParentGroups());

		return _showParentGroups;
	}

	public boolean isShowPortletBreadcrumb() {
		if (_showPortletBreadcrumb != null) {
			return _showPortletBreadcrumb;
		}

		_showPortletBreadcrumb = ParamUtil.getBoolean(
			_httpServletRequest, "showPortletBreadcrumb",
			_siteNavigationBreadcrumbPortletInstanceConfiguration.
				showPortletBreadcrumb());

		return _showPortletBreadcrumb;
	}

	public String renderDDMTemplate() throws Exception {
		PortletDisplayTemplate portletDisplayTemplate =
			PortletDisplayTemplateUtil.getPortletDisplayTemplate();

		DDMTemplate portletDisplayDDMTemplate =
			portletDisplayTemplate.getPortletDisplayTemplateDDMTemplate(
				getDisplayStyleGroupId(),
				PortalUtil.getClassNameId(BreadcrumbEntry.class),
				getDisplayStyle(), true);

		if (portletDisplayDDMTemplate != null) {
			return portletDisplayTemplate.renderDDMTemplate(
				_httpServletRequest, _httpServletResponse,
				portletDisplayDDMTemplate.getTemplateId(),
				getBreadcrumbEntries(), new HashMap<>());
		}

		return StringPool.BLANK;
	}

	private List<BreadcrumbEntry> _breadcrumbEntries;
	private String _displayStyle;
	private long _displayStyleGroupId;
	private final HttpServletRequest _httpServletRequest;
	private final HttpServletResponse _httpServletResponse;
	private String _portletResource;
	private Boolean _showCurrentGroup;
	private Boolean _showGuestGroup;
	private Boolean _showLayout;
	private Boolean _showParentGroups;
	private Boolean _showPortletBreadcrumb;
	private final SiteNavigationBreadcrumbPortletInstanceConfiguration
		_siteNavigationBreadcrumbPortletInstanceConfiguration;

}