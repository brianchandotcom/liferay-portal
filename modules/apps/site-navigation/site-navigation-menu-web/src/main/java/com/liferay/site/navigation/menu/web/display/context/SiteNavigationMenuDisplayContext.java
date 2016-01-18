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

import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Layout;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.LayoutDescription;
import com.liferay.portal.util.LayoutListUtil;
import com.liferay.portlet.display.template.PortletDisplayTemplate;
import com.liferay.site.navigation.menu.web.configuration.SiteNavigationMenuPortletInstanceConfiguration;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Juergen Kappler
 */
public class SiteNavigationMenuDisplayContext {

	public SiteNavigationMenuDisplayContext(HttpServletRequest request)
		throws ConfigurationException {

		_request = request;

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		_siteNavigationMenuPortletInstanceConfiguration =
			portletDisplay.getPortletInstanceConfiguration(
				SiteNavigationMenuPortletInstanceConfiguration.class);
	}

	public String getDDMTemplateKey() {
		if (_ddmTemplateKey != null) {
			return _ddmTemplateKey;
		}

		String displayStyle = getDisplayStyle();

		if (displayStyle != null) {
			PortletDisplayTemplate portletDisplayTemplate =
				(PortletDisplayTemplate)_request.getAttribute(
					WebKeys.PORTLET_DISPLAY_TEMPLATE);

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

	public String getIncludedLayouts() {
		if (_includedLayouts != null) {
			return _includedLayouts;
		}

		_includedLayouts = ParamUtil.getString(
			_request, "includedLayouts",
			_siteNavigationMenuPortletInstanceConfiguration.includedLayouts());

		return _includedLayouts;
	}

	public List<LayoutDescription> getLayoutDescriptions() {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		return LayoutListUtil.getLayoutDescriptions(
			layout.getGroupId(), layout.isPrivateLayout(), StringPool.BLANK,
			themeDisplay.getLocale());
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

	public String getRootLayoutUuid() {
		if (_rootLayoutUuid != null) {
			return _rootLayoutUuid;
		}

		_rootLayoutUuid = ParamUtil.getString(
			_request, "rootLayoutUuid",
			_siteNavigationMenuPortletInstanceConfiguration.rootLayoutUuid());

		return _rootLayoutUuid;
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

	private String _ddmTemplateKey;
	private String _displayStyle;
	private long _displayStyleGroupId;
	private String _includedLayouts;
	private Boolean _preview;
	private final HttpServletRequest _request;
	private Integer _rootLayoutLevel;
	private String _rootLayoutType;
	private String _rootLayoutUuid;
	private final SiteNavigationMenuPortletInstanceConfiguration
		_siteNavigationMenuPortletInstanceConfiguration;

}