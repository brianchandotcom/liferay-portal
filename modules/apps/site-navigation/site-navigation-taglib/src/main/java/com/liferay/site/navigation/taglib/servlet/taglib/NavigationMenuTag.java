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

package com.liferay.site.navigation.taglib.servlet.taglib;

import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portletdisplaytemplate.PortletDisplayTemplateManagerUtil;
import com.liferay.portal.kernel.theme.NavItem;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.display.template.PortletDisplayTemplate;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;
import com.liferay.site.navigation.service.SiteNavigationMenuItemLocalServiceUtil;
import com.liferay.site.navigation.taglib.internal.portlet.display.template.PortletDisplayTemplateUtil;
import com.liferay.site.navigation.taglib.internal.servlet.NavItemClassNameIdUtil;
import com.liferay.site.navigation.taglib.internal.servlet.ServletContextUtil;
import com.liferay.site.navigation.taglib.internal.util.NavItemUtil;
import com.liferay.site.navigation.taglib.internal.util.SiteNavigationMenuNavItem;
import com.liferay.taglib.util.IncludeTag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

/**
 * @author Pavel Savinov
 */
public class NavigationMenuTag extends IncludeTag {

	@Override
	public int processEndTag() throws Exception {
		PortletDisplayTemplate portletDisplayTemplate =
			PortletDisplayTemplateUtil.getPortletDisplayTemplate();

		if (portletDisplayTemplate == null) {
			return EVAL_PAGE;
		}

		DDMTemplate portletDisplayDDMTemplate =
			portletDisplayTemplate.getPortletDisplayTemplateDDMTemplate(
				getDisplayStyleGroupId(),
				NavItemClassNameIdUtil.getNavItemClassNameId(),
				getDisplayStyle(), true);

		if (portletDisplayDDMTemplate == null) {
			return EVAL_PAGE;
		}

		List<NavItem> branchNavItems = null;
		List<NavItem> navItems = null;

		try {
			if (_siteNavigationMenuId > 0) {
				branchNavItems = _getBranchNavItems();

				navItems = _getMenuNavItems(request, branchNavItems);
			}
			else {
				branchNavItems = getBranchNavItems(request);

				navItems = NavItemUtil.getNavItems(
					request, _rootItemType, _rootItemLevel, _rootItemId,
					branchNavItems);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		HttpServletResponse response =
			(HttpServletResponse)pageContext.getResponse();

		Map<String, Object> contextObjects = new HashMap<>();

		contextObjects.put("branchNavItems", branchNavItems);
		contextObjects.put("displayDepth", _displayDepth);
		contextObjects.put("includedLayouts", _expandedLevels);
		contextObjects.put("preview", _preview);
		contextObjects.put("rootLayoutLevel", _rootItemLevel);
		contextObjects.put("rootLayoutType", _rootItemType);

		String result = portletDisplayTemplate.renderDDMTemplate(
			request, response, portletDisplayDDMTemplate, navItems,
			contextObjects);

		JspWriter jspWriter = pageContext.getOut();

		jspWriter.write(result);

		return EVAL_PAGE;
	}

	public void setDdmTemplateGroupId(long ddmTemplateGroupId) {
		_ddmTemplateGroupId = ddmTemplateGroupId;
	}

	public void setDdmTemplateKey(String ddmTemplateKey) {
		_ddmTemplateKey = ddmTemplateKey;
	}

	public void setDisplayDepth(int displayDepth) {
		_displayDepth = displayDepth;
	}

	public void setExpandedLevels(String expandedLevels) {
		_expandedLevels = expandedLevels;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	public void setPreview(boolean preview) {
		_preview = preview;
	}

	public void setRootItemId(String rootItemId) {
		_rootItemId = rootItemId;
	}

	public void setRootItemLevel(int rootItemLevel) {
		_rootItemLevel = rootItemLevel;
	}

	public void setRootItemType(String rootItemType) {
		_rootItemType = rootItemType;
	}

	public void setSiteNavigationMenuId(long siteNavigationMenuId) {
		_siteNavigationMenuId = siteNavigationMenuId;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_ddmTemplateGroupId = 0;
		_ddmTemplateKey = null;
		_displayDepth = 0;
		_expandedLevels = "auto";
		_preview = false;
		_rootItemId = null;
		_rootItemLevel = 1;
		_rootItemType = "absolute";
		_siteNavigationMenuId = 0;
	}

	protected List<NavItem> getBranchNavItems(HttpServletRequest request)
		throws PortalException {

		return NavItemUtil.getBranchNavItems(request);
	}

	protected String getDisplayStyle() {
		if (Validator.isNotNull(_ddmTemplateKey)) {
			return PortletDisplayTemplateManagerUtil.getDisplayStyle(
				_ddmTemplateKey);
		}

		return StringPool.BLANK;
	}

	protected long getDisplayStyleGroupId() {
		if (_ddmTemplateGroupId > 0) {
			return _ddmTemplateGroupId;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		return themeDisplay.getScopeGroupId();
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	protected List<NavItem> getMenuItems() {
		try {
			return _getMenuNavItems(request, new ArrayList<NavItem>());
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return new ArrayList<>();
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	protected List<NavItem> getNavItems(List<NavItem> branchNavItems)
		throws Exception {

		return NavItemUtil.getNavItems(
			request, _rootItemType, _rootItemLevel, _rootItemId,
			branchNavItems);
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
	}

	private List<NavItem> _getBranchNavItems() throws PortalException {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		long siteNavigationMenuItemId = _getRelativeSiteNavigationMenuItemId(
			layout);

		SiteNavigationMenuItem siteNavigationMenuItem =
			SiteNavigationMenuItemLocalServiceUtil.fetchSiteNavigationMenuItem(
				siteNavigationMenuItemId);

		if (siteNavigationMenuItem == null) {
			return new ArrayList<>();
		}

		SiteNavigationMenuItem originalSiteNavigationMenuItem =
			siteNavigationMenuItem;

		List<SiteNavigationMenuItem> ancestorSiteNavigationMenuItems =
			new ArrayList<>();

		while (siteNavigationMenuItem.getParentSiteNavigationMenuItemId() !=
					0) {

			siteNavigationMenuItem =
				SiteNavigationMenuItemLocalServiceUtil.
					getSiteNavigationMenuItem(
						siteNavigationMenuItem.
							getParentSiteNavigationMenuItemId());

			ancestorSiteNavigationMenuItems.add(siteNavigationMenuItem);
		}

		List<NavItem> navItems = new ArrayList<>(
			ancestorSiteNavigationMenuItems.size() + 1);

		for (int i = ancestorSiteNavigationMenuItems.size() - 1; i >= 0; i--) {
			SiteNavigationMenuItem ancestorSiteNavigationMenuItem =
				ancestorSiteNavigationMenuItems.get(i);

			navItems.add(
				new SiteNavigationMenuNavItem(
					request, themeDisplay, ancestorSiteNavigationMenuItem));
		}

		navItems.add(
			new SiteNavigationMenuNavItem(
				request, themeDisplay, originalSiteNavigationMenuItem));

		return navItems;
	}

	private List<NavItem> _getMenuNavItems(
			HttpServletRequest request, List<NavItem> branchNavItems)
		throws Exception {

		if (_rootItemType.equals("absolute")) {
			if (_rootItemLevel == 0) {
				return NavItemUtil.getChildNavItems(
					request, _siteNavigationMenuId, 0);
			}
			else if (branchNavItems.size() >= _rootItemLevel) {
				NavItem rootNavItem = branchNavItems.get(_rootItemLevel - 1);

				return rootNavItem.getChildren();
			}
		}
		else if (_rootItemType.equals("relative") && (_rootItemLevel >= 0) &&
				 (_rootItemLevel < branchNavItems.size() + 1)) {

			int absoluteLevel = branchNavItems.size() - 1 - _rootItemLevel;

			if (absoluteLevel == -1) {
				return NavItemUtil.getChildNavItems(
					request, _siteNavigationMenuId, 0);
			}
			else if ((absoluteLevel >= 0) &&
					 (absoluteLevel < branchNavItems.size())) {

				NavItem rootNavItem = branchNavItems.get(absoluteLevel);

				return rootNavItem.getChildren();
			}
		}
		else if (_rootItemType.equals("select")) {
			return NavItemUtil.getChildNavItems(
				request, _siteNavigationMenuId,
				GetterUtil.getLong(_rootItemId));
		}

		return new ArrayList<>();
	}

	private long _getRelativeSiteNavigationMenuItemId(Layout layout) {
		List<SiteNavigationMenuItem> siteNavigationMenuItems =
			SiteNavigationMenuItemLocalServiceUtil.getSiteNavigationMenuItems(
				_siteNavigationMenuId);

		for (SiteNavigationMenuItem siteNavigationMenuItem :
				siteNavigationMenuItems) {

			UnicodeProperties unicodeProperties = new UnicodeProperties();

			unicodeProperties.fastLoad(
				siteNavigationMenuItem.getTypeSettings());

			String itemLayoutUuid = unicodeProperties.getProperty("layoutUuid");

			if (Objects.equals(layout.getUuid(), itemLayoutUuid)) {
				return siteNavigationMenuItem.getSiteNavigationMenuItemId();
			}
		}

		return 0;
	}

	private static final String _PAGE = "/navigation/page.jsp";

	private static final Log _log = LogFactoryUtil.getLog(
		NavigationMenuTag.class);

	private long _ddmTemplateGroupId;
	private String _ddmTemplateKey;
	private int _displayDepth;
	private String _expandedLevels = "auto";
	private boolean _preview;
	private String _rootItemId;
	private int _rootItemLevel = 1;
	private String _rootItemType = "absolute";
	private long _siteNavigationMenuId;

}