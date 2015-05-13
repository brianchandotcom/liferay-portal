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

package com.liferay.site.navigation.site.map.web.display.context;

import com.liferay.portal.kernel.settings.SettingsException;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Layout;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.site.navigation.site.map.web.configuration.SiteMapPortletInstanceConfiguration;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Juergen Kappler
 */
public class SitesMapDisplayContext {

	public SitesMapDisplayContext(HttpServletRequest request)
		throws SettingsException {

		_request = request;

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		_siteMapPortletInstanceConfiguration =
			portletDisplay.getPortletInstanceConfiguration(
				SiteMapPortletInstanceConfiguration.class);
	}

	public Integer getDisplayDepth() {
		if (_displayDepth != null) {
			return _displayDepth;
		}

		_displayDepth = _siteMapPortletInstanceConfiguration.displayDepth();

		return _displayDepth;
	}

	public String getDisplayStyle() {
		if (_displayStyle != null) {
			return _displayStyle;
		}

		_displayStyle = _siteMapPortletInstanceConfiguration.displayStyle();

		return _displayStyle;
	}

	public Long getDisplayStyleGroupId() {
		if (_displayStyleGroupId != null) {
			return _displayStyleGroupId;
		}

		_displayStyleGroupId =
			_siteMapPortletInstanceConfiguration.displayStyleGroupId();

		if (_displayStyleGroupId <= 0) {
			ThemeDisplay themeDisplay = (ThemeDisplay) _request.getAttribute(
				WebKeys.THEME_DISPLAY);

			_displayStyleGroupId = themeDisplay.getSiteGroupId();
		}

		return _displayStyleGroupId;
	}

	public String getRootLayoutUuid() {
		if (_rootLayoutUuid != null) {
			return _rootLayoutUuid;
		}

		_rootLayoutUuid = _siteMapPortletInstanceConfiguration.rootLayoutUuid();

		return _rootLayoutUuid;
	}

	public Boolean isIncludeRootInTree() {
		if (_includeRootInTree != null) {
			return _includeRootInTree;
		}

		_includeRootInTree =
			_siteMapPortletInstanceConfiguration.includeRootInTree();

		return _includeRootInTree;
	}

	public Boolean isShowCurrentPage() {
		if (_showCurrentPage != null) {
			return _showCurrentPage;
		}

		_showCurrentPage =
			_siteMapPortletInstanceConfiguration.showCurrentPage();

		return _showCurrentPage;
	}

	public Boolean isShowHiddenPages() {
		if (_showHiddenPages != null) {
			return _showHiddenPages;
		}

		_showHiddenPages =
			_siteMapPortletInstanceConfiguration.showHiddenPages();

		return _showHiddenPages;
	}

	public Boolean isUseHtmlTitle() {
		if (_useHtmlTitle != null) {
			return _useHtmlTitle;
		}

		_useHtmlTitle = _siteMapPortletInstanceConfiguration.useHtmlTitle();

		return _useHtmlTitle;
	}

	private Integer _displayDepth;
	private String _displayStyle;
	private Long _displayStyleGroupId;
	private Boolean _includeRootInTree;
	private final HttpServletRequest _request;
	private String _rootLayoutUuid;
	private Boolean _showCurrentPage;
	private Boolean _showHiddenPages;
	private final SiteMapPortletInstanceConfiguration
		_siteMapPortletInstanceConfiguration;
	private Boolean _useHtmlTitle;

}