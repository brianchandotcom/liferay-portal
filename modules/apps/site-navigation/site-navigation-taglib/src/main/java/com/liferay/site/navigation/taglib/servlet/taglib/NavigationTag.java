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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portletdisplaytemplate.PortletDisplayTemplateManagerUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.theme.NavItem;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.site.navigation.taglib.servlet.ServletContextUtil;
import com.liferay.taglib.util.IncludeTag;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 * @author Tibor Lipusz
 */
public class NavigationTag extends IncludeTag {

	public void setDdmTemplateGroupId(long ddmTemplateGroupId) {
		_ddmTemplateGroupId = ddmTemplateGroupId;
	}

	public void setDdmTemplateKey(String ddmTemplateKey) {
		_ddmTemplateKey = ddmTemplateKey;
	}

	public void setIncludedLayouts(String includedLayouts) {
		_includedLayouts = includedLayouts;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	public void setPreview(boolean preview) {
		_preview = preview;
	}

	public void setRootLayoutLevel(int rootLayoutLevel) {
		_rootLayoutLevel = rootLayoutLevel;
	}

	public void setRootLayoutType(String rootLayoutType) {
		_rootLayoutType = rootLayoutType;
	}

	public void setRootLayoutUuid(String rootLayoutUuid) {
		_rootLayoutUuid = rootLayoutUuid;
	}

	@Override
	protected void cleanUp() {
		_ddmTemplateGroupId = 0;
		_ddmTemplateKey = null;
		_includedLayouts = "auto";
		_preview = false;
		_rootLayoutLevel = 1;
		_rootLayoutType = "absolute";
		_rootLayoutUuid = null;
	}

	protected List<NavItem> getBranchNavItems(HttpServletRequest request)
		throws PortalException {

		List<NavItem> navItems = new ArrayList<>();

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		NavItem navItem = new NavItem(request, layout, null);

		navItems.add(navItem);

		for (Layout ancestorLayout : layout.getAncestors()) {
			navItems.add(0, new NavItem(request, ancestorLayout, null));
		}

		return navItems;
	}

	protected String getDisplayStyle() {
		if (Validator.isNotNull(_ddmTemplateKey)) {
			return PortletDisplayTemplateManagerUtil.getDisplayStyle(
				_ddmTemplateKey);
		}

		return null;
	}

	protected long getDisplayStyleGroupId() {
		if (_ddmTemplateGroupId > 0) {
			return _ddmTemplateGroupId;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		return themeDisplay.getScopeGroupId();
	}

	protected List<NavItem> getNavItems(List<NavItem> branchNavItems)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		List<NavItem> navItems = new ArrayList<>();

		NavItem rootNavItem = null;

		if (_rootLayoutType.equals("relative")) {
			if ((_rootLayoutLevel >= 0) &&
				(_rootLayoutLevel < branchNavItems.size())) {

				rootNavItem = branchNavItems.get(_rootLayoutLevel);
			}
		}
		else if (_rootLayoutType.equals("absolute")) {
			int ancestorIndex = branchNavItems.size() - _rootLayoutLevel;

			if ((ancestorIndex >= 0) &&
				(ancestorIndex < branchNavItems.size())) {

				rootNavItem = branchNavItems.get(ancestorIndex);
			}
			else if (ancestorIndex == branchNavItems.size()) {
				navItems = NavItem.fromLayouts(
					request, themeDisplay.getLayouts(), null);
			}
		}
		else if (_rootLayoutType.equals("select")) {
			Layout layout = themeDisplay.getLayout();

			if (Validator.isNotNull(_rootLayoutUuid)) {
				Layout rootLayout =
					LayoutLocalServiceUtil.getLayoutByUuidAndGroupId(
						_rootLayoutUuid, layout.getGroupId(),
						layout.isPrivateLayout());

				rootNavItem = new NavItem(request, rootLayout, null);
			}
			else {
				navItems = NavItem.fromLayouts(
					request, themeDisplay.getLayouts(), null);
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
		request.setAttribute(
			"liferay-site-navigation:navigation:displayStyle",
			getDisplayStyle());
		request.setAttribute(
			"liferay-site-navigation:navigation:displayStyleGroupId",
			String.valueOf(getDisplayStyleGroupId()));
		request.setAttribute(
			"liferay-site-navigation:navigation:includedLayouts",
			_includedLayouts);

		try {
			List<NavItem> branchNavItems = getBranchNavItems(request);

			request.setAttribute(
				"liferay-site-navigation:navigation:branchNavItems",
				branchNavItems);
			request.setAttribute(
				"liferay-site-navigation:navigation:navItems",
				getNavItems(branchNavItems));
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		request.setAttribute(
			"liferay-site-navigation:navigation:preview",
			String.valueOf(_preview));
		request.setAttribute(
			"liferay-site-navigation:navigation:rootLayoutLevel",
			String.valueOf(_rootLayoutLevel));
		request.setAttribute(
			"liferay-site-navigation:navigation:rootLayoutType",
			_rootLayoutType);
	}

	private static final String _PAGE = "/navigation/page.jsp";

	private static final Log _log = LogFactoryUtil.getLog(NavigationTag.class);

	private long _ddmTemplateGroupId;
	private String _ddmTemplateKey;
	private String _includedLayouts = "auto";
	private boolean _preview;
	private int _rootLayoutLevel = 1;
	private String _rootLayoutType = "absolute";
	private String _rootLayoutUuid;

}