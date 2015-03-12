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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.taglib.ui.BreadcrumbEntry;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PrefsParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.portlet.portletdisplaytemplate.util.PortletDisplayTemplate;
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

	public DDMTemplate getDDMTemplate() {
		if (_ddmTemplate != null) {
			return _ddmTemplate;
		}

		if (_displayStyle == null) {
			_displayStyle = PrefsParamUtil.getString(
				_portletPreferences, _request, "displayStyle");

			if (Validator.isNull(_displayStyle) &&
				Validator.isNotNull(
					PropsValues.BREADCRUMB_DDM_TEMPLATE_KEY_DEFAULT)) {

				try {
					_ddmTemplate = DDMTemplateLocalServiceUtil.fetchTemplate(
						getDisplayStyleGroupId(),
						PortalUtil.getClassNameId(
							BreadcrumbEntry.class.getName()),
						PropsValues.BREADCRUMB_DDM_TEMPLATE_KEY_DEFAULT, true);
				}
				catch (PortalException e) {
					_log.error(
						"Error obtaining DDM Template with key " +
						PropsValues.BREADCRUMB_DDM_TEMPLATE_KEY_DEFAULT);
				}
			}
		}

		if ((_ddmTemplate == null) && Validator.isNotNull(_displayStyle)) {
			ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
				WebKeys.THEME_DISPLAY);

			_displayStyleGroupId = PrefsParamUtil.getLong(
				_portletPreferences, _request, "displayStyleGroupId",
				themeDisplay.getSiteGroupId());

			_ddmTemplate = PortletDisplayTemplateUtil.fetchDDMTemplate(
				_displayStyleGroupId, _displayStyle);
		}

		return _ddmTemplate;
	}

	public String getDDMTemplateKey() {
		DDMTemplate ddmTemplate = getDDMTemplate();

		if (ddmTemplate != null) {
			return ddmTemplate.getTemplateKey();
		}

		return null;
	}

	public String getDisplayStyle() {
		if (_displayStyle != null) {
			return _displayStyle;
		}

		DDMTemplate ddmTemplate = getDDMTemplate();

		if (ddmTemplate != null) {
			_displayStyle =
				PortletDisplayTemplate.DISPLAY_STYLE_PREFIX +
					ddmTemplate.getUuid();
		}

		return _displayStyle;
	}

	public long getDisplayStyleGroupId() {
		if (_displayStyleGroupId != 0) {
			return _displayStyleGroupId;
		}

		DDMTemplate ddmTemplate = getDDMTemplate();

		if (ddmTemplate != null) {
			_displayStyleGroupId = ddmTemplate.getGroupId();
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

	private DDMTemplate _ddmTemplate;
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