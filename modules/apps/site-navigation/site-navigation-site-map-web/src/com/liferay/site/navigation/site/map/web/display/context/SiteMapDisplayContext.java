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
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.permission.LayoutPermissionUtil;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.LayoutDescription;
import com.liferay.portal.util.LayoutListUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.site.navigation.site.map.web.configuration.SiteMapPortletInstanceConfiguration;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Juergen Kappler
 */
public class SiteMapDisplayContext {

	public SiteMapDisplayContext(HttpServletRequest request)
		throws SettingsException {

		_request = request;

		_themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

		_siteMapPortletInstanceConfiguration =
			portletDisplay.getPortletInstanceConfiguration(
				SiteMapPortletInstanceConfiguration.class);
	}

	public String buildSiteMap() throws Exception {
		StringBundler sb = new StringBundler();

		_buildSiteMap(
			_themeDisplay.getLayout(), getRootLayouts(), getRootLayout(),
			isIncludeRootInTree(),
			_siteMapPortletInstanceConfiguration.displayDepth(),
			_siteMapPortletInstanceConfiguration.showCurrentPage(),
			_siteMapPortletInstanceConfiguration.useHtmlTitle(),
			_siteMapPortletInstanceConfiguration.showHiddenPages(), 1,
			_themeDisplay, sb);

		return sb.toString();
	}

	public Long getDisplayStyleGroupId() {
		if (_displayStyleGroupId != null) {
			return _displayStyleGroupId;
		}

		_displayStyleGroupId =
			_siteMapPortletInstanceConfiguration.displayStyleGroupId();

		if (_displayStyleGroupId <= 0) {
			_displayStyleGroupId = _themeDisplay.getSiteGroupId();
		}

		return _displayStyleGroupId;
	}

	public List<LayoutDescription> getLayoutDescriptions() {
		Layout layout = _themeDisplay.getLayout();

		String rootNodeName = StringPool.BLANK;

		return LayoutListUtil.getLayoutDescriptions(
			layout.getGroupId(), layout.isPrivateLayout(), rootNodeName,
			_themeDisplay.getLocale());
	}

	public Layout getRootLayout() {
		if (_rootLayout != null) {
			return _rootLayout;
		}

		String rootLayoutUuid =
			_siteMapPortletInstanceConfiguration.rootLayoutUuid();

		if (Validator.isNotNull(rootLayoutUuid)) {
			Layout layout = _themeDisplay.getLayout();

			_rootLayout = LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
				rootLayoutUuid, _themeDisplay.getScopeGroupId(),
				layout.isPrivateLayout());
		}

		return _rootLayout;
	}

	public long getRootLayoutId() {
		if (_rootLayoutId != null) {
			return _rootLayoutId;
		}

		Layout rootLayout = getRootLayout();

		if (Validator.isNotNull(
				_siteMapPortletInstanceConfiguration.rootLayoutUuid()) &&
			Validator.isNotNull(rootLayout)) {

			_rootLayoutId = rootLayout.getLayoutId();
		}
		else {
			_rootLayoutId = LayoutConstants.DEFAULT_PARENT_LAYOUT_ID;
		}

		return _rootLayoutId;
	}

	public List<Layout> getRootLayouts() {
		Layout layout = _themeDisplay.getLayout();

		return LayoutLocalServiceUtil.getLayouts(
			layout.getGroupId(), layout.isPrivateLayout(), getRootLayoutId());
	}

	public SiteMapPortletInstanceConfiguration
		getSiteMapPortletInstanceConfiguration() {

		return _siteMapPortletInstanceConfiguration;
	}

	public Boolean isIncludeRootInTree() {
		if (_includeRootInTree != null) {
			return _includeRootInTree;
		}

		_includeRootInTree =
			_siteMapPortletInstanceConfiguration.includeRootInTree();

		if (Validator.isNull(
				_siteMapPortletInstanceConfiguration.rootLayoutUuid()) ||
			(getRootLayoutId() == LayoutConstants.DEFAULT_PARENT_LAYOUT_ID)) {

			_includeRootInTree = false;
		}

		return _includeRootInTree;
	}

	private void _buildLayoutView(
			Layout layout, String cssClass, boolean useHtmlTitle,
			ThemeDisplay themeDisplay, StringBundler sb)
		throws Exception {

		String layoutURL = PortalUtil.getLayoutURL(layout, themeDisplay);
		String target = PortalUtil.getLayoutTarget(layout);

		sb.append("<a href=\"");
		sb.append(layoutURL);
		sb.append("\" ");
		sb.append(target);

		if (Validator.isNotNull(cssClass)) {
			sb.append(" class=\"");
			sb.append(cssClass);
			sb.append("\" ");
		}

		sb.append("> ");

		String layoutName = HtmlUtil.escape(
			layout.getName(themeDisplay.getLocale()));

		if (useHtmlTitle) {
			layoutName = HtmlUtil.escape(
				layout.getHTMLTitle(themeDisplay.getLocale()));
		}

		sb.append(layoutName);
		sb.append("</a>");
	}

	private void _buildSiteMap(
			Layout layout, List<Layout> layouts, Layout rootLayout,
			boolean includeRootInTree, int displayDepth,
			boolean showCurrentPage, boolean useHtmlTitle,
			boolean showHiddenPages, int curDepth, ThemeDisplay themeDisplay,
			StringBundler sb)
		throws Exception {

		if (layouts.isEmpty()) {
			return;
		}

		sb.append("<ul>");

		if (includeRootInTree && (rootLayout != null) && (curDepth == 1)) {
			sb.append("<li>");

			String cssClass = "root";

			if (rootLayout.getPlid() == layout.getPlid()) {
				cssClass += " current";
			}

			_buildLayoutView(
				rootLayout, cssClass, useHtmlTitle, themeDisplay, sb);

			_buildSiteMap(
				layout, layouts, rootLayout, includeRootInTree, displayDepth,
				showCurrentPage, useHtmlTitle, showHiddenPages, curDepth + 1,
				themeDisplay, sb);

			sb.append("</li>");
		}
		else {
			for (Layout curLayout : layouts) {
				if ((showHiddenPages || !curLayout.isHidden()) &&
					LayoutPermissionUtil.contains(
						themeDisplay.getPermissionChecker(), curLayout,
						ActionKeys.VIEW)) {

					sb.append("<li>");

					String cssClass = StringPool.BLANK;

					if (curLayout.getPlid() == layout.getPlid()) {
						cssClass = "current";
					}

					_buildLayoutView(
						curLayout, cssClass, useHtmlTitle, themeDisplay, sb);

					if ((displayDepth == 0) || (displayDepth > curDepth)) {
						if (showHiddenPages) {
							_buildSiteMap(
								layout, curLayout.getChildren(), rootLayout,
								includeRootInTree, displayDepth,
								showCurrentPage, useHtmlTitle, showHiddenPages,
								curDepth + 1, themeDisplay, sb);
						}
						else {
							_buildSiteMap(
								layout,
								curLayout.getChildren(
									themeDisplay.getPermissionChecker()),
								rootLayout, includeRootInTree, displayDepth,
								showCurrentPage, useHtmlTitle, showHiddenPages,
								curDepth + 1, themeDisplay, sb);
						}
					}

					sb.append("</li>");
				}
			}
		}

		sb.append("</ul>");
	}

	private Long _displayStyleGroupId;
	private Boolean _includeRootInTree;
	private final HttpServletRequest _request;
	private Layout _rootLayout;
	private Long _rootLayoutId;
	private final SiteMapPortletInstanceConfiguration
		_siteMapPortletInstanceConfiguration;
	private final ThemeDisplay _themeDisplay;

}