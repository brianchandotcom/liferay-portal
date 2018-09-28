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
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
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
import com.liferay.site.navigation.taglib.internal.util.SiteNavigationMenuNavItem;
import com.liferay.site.navigation.type.SiteNavigationMenuItemType;
import com.liferay.taglib.util.IncludeTag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
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
				branchNavItems = Collections.emptyList();

				List<NavItem> branchMenuItems = getBranchMenuItems();

				navItems = getMenuItems(branchMenuItems);
			}
			else {
				branchNavItems = getBranchNavItems(request);

				navItems = getNavItems(branchNavItems);
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

	protected List<NavItem> getBranchMenuItems() throws PortalException {
		List<NavItem> navItems = new ArrayList<>();

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		long siteNavigationMenuItemId = _getRelativeSiteNavigationMenuItemId(
			layout);

		SiteNavigationMenuItem siteNavigationMenuItem =
			SiteNavigationMenuItemLocalServiceUtil.getSiteNavigationMenuItem(
				siteNavigationMenuItemId);

		List<SiteNavigationMenuItem> ancestors =
			siteNavigationMenuItem.getAncestors();

		ListIterator<SiteNavigationMenuItem> listIterator =
			ancestors.listIterator(ancestors.size());

		while (listIterator.hasPrevious()) {
			SiteNavigationMenuItem ancestor = listIterator.previous();

			navItems.add(
				new SiteNavigationMenuNavItem(request, themeDisplay, ancestor));
		}

		navItems.add(
			new SiteNavigationMenuNavItem(
				request, themeDisplay, siteNavigationMenuItem));

		return navItems;
	}

	protected List<NavItem> getBranchNavItems(HttpServletRequest request)
		throws PortalException {

		List<NavItem> navItems = new ArrayList<>();

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		if (layout.isRootLayout()) {
			return Collections.singletonList(
				new NavItem(request, themeDisplay, layout, null));
		}

		List<Layout> ancestorLayouts = layout.getAncestors();

		ListIterator<Layout> listIterator = ancestorLayouts.listIterator(
			ancestorLayouts.size());

		while (listIterator.hasPrevious()) {
			Layout ancestorLayout = listIterator.previous();

			navItems.add(
				new NavItem(request, themeDisplay, ancestorLayout, null));
		}

		navItems.add(new NavItem(request, themeDisplay, layout, null));

		return navItems;
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

	protected List<NavItem> getMenuItems() {
		try {
			return getMenuItems(new ArrayList<NavItem>());
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return new ArrayList<>();
	}

	protected List<NavItem> getMenuItems(List<NavItem> branchMenuItems)
		throws Exception {

		List<NavItem> navItems = new ArrayList<>();

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		NavItem rootMenuItem = null;

		long parentSiteNavigationMenuItemId = GetterUtil.getLong(_rootItemId);

		if (_rootItemType.equals("relative")) {
			if ((_rootItemLevel >= 0) &&
				(_rootItemLevel < branchMenuItems.size())) {

				rootMenuItem = branchMenuItems.get(_rootItemLevel);
			}
		}
		else if (_rootItemType.equals("absolute")) {
			if (_rootItemLevel == 0) {
				navItems = _getMenuItemsFromParentSiteNavigationMenuItem(
					themeDisplay, 0);
			}
			else if (branchMenuItems.size() >= _rootItemLevel) {
				rootMenuItem = branchMenuItems.get(_rootItemLevel - 1);
			}
		}
		else if (_rootItemType.equals("select")) {
			if (Validator.isNotNull(_rootItemId)) {
				SiteNavigationMenuItem parentSiteNavigationMenuItem =
					SiteNavigationMenuItemLocalServiceUtil.
						getSiteNavigationMenuItem(
							parentSiteNavigationMenuItemId);

				rootMenuItem = new SiteNavigationMenuNavItem(
					request, themeDisplay, parentSiteNavigationMenuItem);
			}
			else {
				navItems = _getMenuItemsFromParentSiteNavigationMenuItem(
					themeDisplay, parentSiteNavigationMenuItemId);
			}
		}

		if (rootMenuItem != null) {
			navItems = rootMenuItem.getChildren();
		}

		return navItems;
	}

	protected List<NavItem> getNavItems(List<NavItem> branchNavItems)
		throws Exception {

		List<NavItem> navItems = new ArrayList<>();

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		NavItem rootNavItem = null;

		if (_rootItemType.equals("relative")) {
			if ((_rootItemLevel >= 0) &&
				(_rootItemLevel < branchNavItems.size())) {

				rootNavItem = branchNavItems.get(_rootItemLevel);
			}
		}
		else if (_rootItemType.equals("absolute")) {
			if (_rootItemLevel == 0) {
				navItems = NavItem.fromLayouts(request, themeDisplay, null);
			}
			else if (branchNavItems.size() >= _rootItemLevel) {
				rootNavItem = branchNavItems.get(_rootItemLevel - 1);
			}
		}
		else if (_rootItemType.equals("select")) {
			Layout layout = themeDisplay.getLayout();

			if (Validator.isNotNull(_rootItemId)) {
				Layout rootLayout =
					LayoutLocalServiceUtil.getLayoutByUuidAndGroupId(
						_rootItemId, layout.getGroupId(),
						layout.isPrivateLayout());

				rootNavItem = new NavItem(
					request, themeDisplay, rootLayout, null);
			}
			else {
				navItems = NavItem.fromLayouts(request, themeDisplay, null);
			}
		}

		if (rootNavItem != null) {
			navItems = rootNavItem.getChildren();
		}

		return navItems;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
	}

	private List<NavItem> _getMenuItemsFromParentSiteNavigationMenuItem(
		ThemeDisplay themeDisplay, long parentSiteNavigationMenuItemId) {

		List<NavItem> navItems = new ArrayList<>();

		List<SiteNavigationMenuItem> siteNavigationMenuItems =
			SiteNavigationMenuItemLocalServiceUtil.getSiteNavigationMenuItems(
				_siteNavigationMenuId, parentSiteNavigationMenuItemId);

		for (SiteNavigationMenuItem siteNavigationMenuItem :
				siteNavigationMenuItems) {

			SiteNavigationMenuItemType siteNavigationMenuItemType =
				ServletContextUtil.getSiteNavigationMenuItemType(
					siteNavigationMenuItem.getType());

			try {
				if (!siteNavigationMenuItemType.hasPermission(
						themeDisplay.getPermissionChecker(),
						siteNavigationMenuItem)) {

					continue;
				}

				navItems.add(
					new SiteNavigationMenuNavItem(
						request, themeDisplay, siteNavigationMenuItem));
			}
			catch (PortalException pe) {
				if (_log.isDebugEnabled()) {
					_log.debug(pe, pe);
				}
			}
		}

		return navItems;
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