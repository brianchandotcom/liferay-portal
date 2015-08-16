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

package com.liferay.site.navigation.menu.web.display.context;

import com.liferay.portal.kernel.configuration.module.ConfigurationException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.display.template.PortletDisplayTemplate;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.site.navigation.menu.web.configuration.SiteNavigationMenuPortletInstanceConfiguration;
import com.liferay.site.navigation.menu.web.configuration.SiteNavigationMenuWebConfiguration;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Juergen Kappler
 */
public class SiteNavigationMenuDisplayContext {

	public SiteNavigationMenuDisplayContext(
			HttpServletRequest request,
			SiteNavigationMenuWebConfiguration navigationMenuWebConfiguration)
		throws ConfigurationException {

		_request = request;
		_siteNavigationMenuWebConfiguration = navigationMenuWebConfiguration;

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		_siteNavigationMenuPortletInstanceConfiguration =
			portletDisplay.getPortletInstanceConfiguration(
				SiteNavigationMenuPortletInstanceConfiguration.class);
	}

	public String getBulletStyle() {
		if (_bulletStyle != null) {
			return _bulletStyle;
		}

		_bulletStyle = ParamUtil.getString(
			_request, "bulletStyle",
			_siteNavigationMenuPortletInstanceConfiguration.bulletStyle());

		if (Validator.isNull(_bulletStyle)) {
			ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
				WebKeys.THEME_DISPLAY);

			_bulletStyle = GetterUtil.getString(
				themeDisplay.getThemeSetting("bullet-style"),
				_siteNavigationMenuWebConfiguration.defaultBulletStyle());
		}

		return _bulletStyle;
	}

	public String getDDMTemplateKey() {
		if (_ddmTemplateKey != null) {
			return _ddmTemplateKey;
		}

		String displayStyle = getDisplayStyle();

		if (displayStyle != null) {
			PortletDisplayTemplate portletDisplayTemplate =
				getPortletDisplayTemplate();

			_ddmTemplateKey = portletDisplayTemplate.getDDMTemplateKey(
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
			_siteNavigationMenuPortletInstanceConfiguration.displayStyle());

		if (Validator.isNull(_displayStyle )) {
			_displayStyle =
				_siteNavigationMenuWebConfiguration.defaultDisplayStyle();
		}

		return _displayStyle;
	}

	public long getDisplayStyleGroupId() {
		if (_displayStyleGroupId != 0) {
			return _displayStyleGroupId;
		}

		_displayStyleGroupId = ParamUtil.getLong(
			_request, "displayStyleGroupId",
			_siteNavigationMenuPortletInstanceConfiguration.
				displayStyleGroupId());

		if (_displayStyleGroupId <= 0) {
			ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
				WebKeys.THEME_DISPLAY);

			_displayStyleGroupId = themeDisplay.getSiteGroupId();
		}

		return _displayStyleGroupId;
	}

	public String getHeaderType() {
		if (_headerType != null) {
			return _headerType;
		}

		_headerType = ParamUtil.getString(
			_request, "headerType",
			_siteNavigationMenuPortletInstanceConfiguration.headerType());

		return _headerType;
	}

	public String getIncludedLayouts() {
		if (_includedLayouts != null) {
			return _includedLayouts;
		}

		_includedLayouts = ParamUtil.getString(
			_request, "includedLayouts",
			_siteNavigationMenuPortletInstanceConfiguration.includedLayouts());

		return _includedLayouts;
	}

	public int getRootLayoutLevel() {
		if (_rootLayoutLevel != null) {
			return _rootLayoutLevel;
		}

		_rootLayoutLevel = ParamUtil.getInteger(
			_request, "rootLayoutLevel",
			_siteNavigationMenuPortletInstanceConfiguration.rootLayoutLevel());

		return _rootLayoutLevel;
	}

	public String getRootLayoutType() {
		if (_rootLayoutType != null) {
			return _rootLayoutType;
		}

		_rootLayoutType = ParamUtil.getString(
			_request, "rootLayoutType",
			_siteNavigationMenuPortletInstanceConfiguration.rootLayoutType());

		return _rootLayoutType;
	}

	public boolean isNestedChildren() {
		if (_nestedChildren != null) {
			return _nestedChildren;
		}

		_nestedChildren = ParamUtil.getBoolean(
			_request, "nestedChildren",
			_siteNavigationMenuPortletInstanceConfiguration.nestedChildren());

		return _nestedChildren;
	}

	public boolean isPreview() {
		if (_preview != null) {
			return _preview;
		}

		_preview = ParamUtil.getBoolean(
			_request, "preview",
			_siteNavigationMenuPortletInstanceConfiguration.preview());

		return _preview;
	}

	protected PortletDisplayTemplate getPortletDisplayTemplate() {
		Registry registry = RegistryUtil.getRegistry();

		return registry.getService(PortletDisplayTemplate.class);
	}

	private String _bulletStyle;
	private String _ddmTemplateKey;
	private String _displayStyle;
	private long _displayStyleGroupId;
	private String _headerType;
	private String _includedLayouts;
	private Boolean _nestedChildren;
	private Boolean _preview;
	private final HttpServletRequest _request;
	private Integer _rootLayoutLevel;
	private String _rootLayoutType;
	private final SiteNavigationMenuPortletInstanceConfiguration
		_siteNavigationMenuPortletInstanceConfiguration;
	private final SiteNavigationMenuWebConfiguration
		_siteNavigationMenuWebConfiguration;

}