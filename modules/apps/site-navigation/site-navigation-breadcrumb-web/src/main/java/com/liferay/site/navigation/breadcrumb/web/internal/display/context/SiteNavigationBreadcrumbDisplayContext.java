/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.navigation.breadcrumb.web.internal.display.context;

import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.servlet.taglib.ui.BreadcrumbEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.display.template.util.PortletDisplayTemplateUtil;
import com.liferay.site.navigation.breadcrumb.web.internal.configuration.SiteNavigationBreadcrumbPortletInstanceConfiguration;
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

		_siteNavigationBreadcrumbPortletInstanceConfiguration =
			ConfigurationProviderUtil.getPortletInstanceConfiguration(
				SiteNavigationBreadcrumbPortletInstanceConfiguration.class,
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY));
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
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		String displayStyleGroupKey = getDisplayStyleGroupKey();

		if (Validator.isNotNull(displayStyleGroupKey)) {
			Group group = GroupLocalServiceUtil.fetchGroup(
				themeDisplay.getCompanyId(), displayStyleGroupKey);

			if (group != null) {
				return group.getGroupId();
			}
		}

		return themeDisplay.getScopeGroupId();
	}

	public String getDisplayStyleGroupKey() {
		if (Validator.isNotNull(_displayStyleGroupKey)) {
			return _displayStyleGroupKey;
		}

		String displayStyleGroupKey =
			_siteNavigationBreadcrumbPortletInstanceConfiguration.
				displayStyleGroupKey();

		if (Validator.isNotNull(displayStyleGroupKey)) {
			_displayStyleGroupKey = displayStyleGroupKey;

			return _displayStyleGroupKey;
		}

		long displayStyleGroupId = ParamUtil.getLong(
			_httpServletRequest, "displayStyleGroupId",
			_siteNavigationBreadcrumbPortletInstanceConfiguration.
				displayStyleGroupId());

		if (displayStyleGroupId <= 0) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)_httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			displayStyleGroupId = themeDisplay.getScopeGroupId();
		}

		Group group = GroupLocalServiceUtil.fetchGroup(displayStyleGroupId);

		if (group != null) {
			_displayStyleGroupKey = group.getGroupKey();
		}

		return _displayStyleGroupKey;
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
		DDMTemplate portletDisplayDDMTemplate =
			PortletDisplayTemplateUtil.getPortletDisplayTemplateDDMTemplate(
				getDisplayStyleGroupId(),
				PortalUtil.getClassNameId(BreadcrumbEntry.class),
				getDisplayStyle(), true);

		if (portletDisplayDDMTemplate != null) {
			return PortletDisplayTemplateUtil.renderDDMTemplate(
				_httpServletRequest, _httpServletResponse,
				portletDisplayDDMTemplate.getTemplateId(),
				getBreadcrumbEntries(), new HashMap<>());
		}

		return StringPool.BLANK;
	}

	private List<BreadcrumbEntry> _breadcrumbEntries;
	private String _displayStyle;
	private String _displayStyleGroupKey;
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