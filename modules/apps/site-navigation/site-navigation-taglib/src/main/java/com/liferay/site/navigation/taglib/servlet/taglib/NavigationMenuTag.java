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
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.theme.NavItem;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.display.template.PortletDisplayTemplate;
import com.liferay.portlet.display.template.util.PortletDisplayTemplateUtil;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;
import com.liferay.site.navigation.service.SiteNavigationMenuItemLocalServiceUtil;
import com.liferay.site.navigation.taglib.internal.servlet.ServletContextUtil;
import com.liferay.site.navigation.taglib.internal.util.NavItemUtil;
import com.liferay.site.navigation.taglib.internal.util.SiteNavigationMenuNavItemImpl;
import com.liferay.taglib.util.IncludeTag;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

/**
 * @author Pavel Savinov
 */
public class NavigationMenuTag extends IncludeTag {

	public long getDdmTemplateGroupId() {
		return _ddmTemplateGroupId;
	}

	public String getDdmTemplateKey() {
		return _ddmTemplateKey;
	}

	public int getDisplayDepth() {
		return _displayDepth;
	}

	public String getExpandedLevels() {
		return _expandedLevels;
	}

	public NavigationMenuMode getNavigationMenuMode() {
		return _navigationMenuMode;
	}

	public String getRootItemId() {
		return _rootItemId;
	}

	public int getRootItemLevel() {
		return _rootItemLevel;
	}

	public String getRootItemType() {
		return _rootItemType;
	}

	public long getSiteNavigationMenuId() {
		return _siteNavigationMenuId;
	}

	public boolean isPreview() {
		return _preview;
	}

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
				ClassNameLocalServiceUtil.getClassNameId(NavItem.class),
				getDisplayStyle(), true);

		if (portletDisplayDDMTemplate == null) {
			return EVAL_PAGE;
		}

		List<NavItem> branchNavItems = null;
		List<NavItem> navItems = null;

		HttpServletRequest httpServletRequest = getRequest();

		try {
			if (_siteNavigationMenuId > 0) {
				branchNavItems = _getBranchNavItems();

				navItems = _getMenuNavItems(httpServletRequest, branchNavItems);
			}
			else {
				branchNavItems = getBranchNavItems(httpServletRequest);

				navItems = NavItemUtil.getNavItems(
					_navigationMenuMode, httpServletRequest, _rootItemType,
					_rootItemLevel, _rootItemId, branchNavItems);
			}
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		HttpServletResponse httpServletResponse =
			(HttpServletResponse)pageContext.getResponse();

		String result = portletDisplayTemplate.renderDDMTemplate(
			httpServletRequest, httpServletResponse, portletDisplayDDMTemplate,
			navItems,
			HashMapBuilder.<String, Object>put(
				"branchNavItems", branchNavItems
			).put(
				"displayDepth", _displayDepth
			).put(
				"includedLayouts", _expandedLevels
			).put(
				"preview", _preview
			).put(
				"rootLayoutLevel", _rootItemLevel
			).put(
				"rootLayoutType", _rootItemType
			).build());

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

	public void setNavigationMenuMode(NavigationMenuMode navigationMenuMode) {
		_navigationMenuMode = navigationMenuMode;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		setServletContext(ServletContextUtil.getServletContext());
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
		_navigationMenuMode = NavigationMenuMode.DEFAULT;
		_preview = false;
		_rootItemId = null;
		_rootItemLevel = 1;
		_rootItemType = "absolute";
		_siteNavigationMenuId = 0;
	}

	protected List<NavItem> getBranchNavItems(
			HttpServletRequest httpServletRequest)
		throws PortalException {

		return NavItemUtil.getBranchNavItems(httpServletRequest);
	}

	protected String getDisplayStyle() {
		PortletDisplayTemplate portletDisplayTemplate =
			ServletContextUtil.getPortletDisplayTemplate();

		if (Validator.isNotNull(_ddmTemplateKey)) {
			return portletDisplayTemplate.getDisplayStyle(_ddmTemplateKey);
		}

		return StringPool.BLANK;
	}

	protected long getDisplayStyleGroupId() {
		if (_ddmTemplateGroupId > 0) {
			return _ddmTemplateGroupId;
		}

		HttpServletRequest httpServletRequest = getRequest();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return themeDisplay.getScopeGroupId();
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
	}

	private List<NavItem> _getBranchNavItems() throws PortalException {
		HttpServletRequest httpServletRequest = getRequest();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		long siteNavigationMenuItemId = _getRelativeSiteNavigationMenuItemId(
			themeDisplay.getLayout());

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
				new SiteNavigationMenuNavItemImpl(
					httpServletRequest, themeDisplay,
					ancestorSiteNavigationMenuItem));
		}

		navItems.add(
			new SiteNavigationMenuNavItemImpl(
				httpServletRequest, themeDisplay,
				originalSiteNavigationMenuItem));

		return navItems;
	}

	private List<NavItem> _getMenuNavItems(
			HttpServletRequest httpServletRequest, List<NavItem> branchNavItems)
		throws Exception {

		if (_rootItemType.equals("absolute")) {
			if (_rootItemLevel == 0) {
				return NavItemUtil.getChildNavItems(
					httpServletRequest, _siteNavigationMenuId, 0);
			}
			else if (branchNavItems.size() >= _rootItemLevel) {
				NavItem rootNavItem = branchNavItems.get(_rootItemLevel - 1);

				return rootNavItem.getChildren();
			}
		}
		else if (_rootItemType.equals("relative") && (_rootItemLevel >= 0) &&
				 (_rootItemLevel < (branchNavItems.size() + 1))) {

			int absoluteLevel = branchNavItems.size() - 1 - _rootItemLevel;

			if (absoluteLevel == -1) {
				return NavItemUtil.getChildNavItems(
					httpServletRequest, _siteNavigationMenuId, 0);
			}
			else if ((absoluteLevel >= 0) &&
					 (absoluteLevel < branchNavItems.size())) {

				NavItem rootNavItem = branchNavItems.get(absoluteLevel);

				return rootNavItem.getChildren();
			}
		}
		else if (_rootItemType.equals("select")) {
			return NavItemUtil.getChildNavItems(
				httpServletRequest, _siteNavigationMenuId,
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

			UnicodeProperties unicodeProperties =
				UnicodePropertiesBuilder.fastLoad(
					siteNavigationMenuItem.getTypeSettings()
				).build();

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
	private NavigationMenuMode _navigationMenuMode = NavigationMenuMode.DEFAULT;
	private boolean _preview;
	private String _rootItemId;
	private int _rootItemLevel = 1;
	private String _rootItemType = "absolute";
	private long _siteNavigationMenuId;

}