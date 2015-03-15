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

package com.liferay.breadcrumb.web.context;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PrefsParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.portletdisplaytemplate.util.PortletDisplayTemplateUtil;

import javax.portlet.PortletPreferences;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Julio Camarero
 */
public class BreadcrumbDisplayContext {

	public BreadcrumbDisplayContext(
		HttpServletRequest request, PortletPreferences portletPreferences) {

		_request = request;
		_portletPreferences = portletPreferences;
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

		_displayStyle = PrefsParamUtil.getString(
			_portletPreferences, _request, "displayStyle");

		return _displayStyle;
	}

	public long getDisplayStyleGroupId() {
		if (_displayStyleGroupId != 0) {
			return _displayStyleGroupId;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		_displayStyleGroupId = PrefsParamUtil.getLong(
			_portletPreferences, _request, "displayStyleGroupId",
			themeDisplay.getSiteGroupId());

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

		_showCurrentGroup = PrefsParamUtil.getBoolean(
			_portletPreferences, _request, "showCurrentGroup", true);

		return _showCurrentGroup;
	}

	public boolean isShowGuestGroup() {
		if (_showGuestGroup != null) {
			return _showGuestGroup;
		}

		_showGuestGroup = PrefsParamUtil.getBoolean(
			_portletPreferences, _request, "showGuestGroup",
			PropsValues.BREADCRUMB_SHOW_GUEST_GROUP);

		return _showGuestGroup;
	}

	public boolean isShowLayout() {
		if (_showLayout != null) {
			return _showLayout;
		}

		_showLayout = PrefsParamUtil.getBoolean(
			_portletPreferences, _request, "showLayout", true);

		return _showLayout;
	}

	public boolean isShowParentGroups() {
		if (_showParentGroups != null) {
			return _showParentGroups;
		}

		String showParentGroupsString = PrefsParamUtil.getString(
			_portletPreferences, _request, "showParentGroups", null);

		if (Validator.isNotNull(showParentGroupsString)) {
			_showParentGroups = GetterUtil.getBoolean(showParentGroupsString);

			return _showParentGroups;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		try {
			Layout layout = themeDisplay.getLayout();

			Group group = layout.getGroup();

			_showParentGroups = GetterUtil.getBoolean(
				group.getTypeSettingsProperty("breadcrumbShowParentGroups"),
				PropsValues.BREADCRUMB_SHOW_PARENT_GROUPS);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return _showParentGroups;
	}

	public boolean isShowPortletBreadcrumb() {
		if (_showPortletBreadcrumb != null) {
			return _showPortletBreadcrumb;
		}

		_showPortletBreadcrumb = PrefsParamUtil.getBoolean(
			_portletPreferences, _request, "showPortletBreadcrumb", true);

		return _showPortletBreadcrumb;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BreadcrumbDisplayContext.class);

	private String _ddmTemplateKey;
	private String _displayStyle;
	private long _displayStyleGroupId;
	private final PortletPreferences _portletPreferences;
	private String _portletResource;
	private final HttpServletRequest _request;
	private Boolean _showCurrentGroup;
	private Boolean _showGuestGroup;
	private Boolean _showLayout;
	private Boolean _showParentGroups;
	private Boolean _showPortletBreadcrumb;

}