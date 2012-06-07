/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.theme;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.templateparser.TemplateContext;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.MethodCache;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Layout;
import com.liferay.portal.util.PortalUtil;

import java.io.Serializable;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class NavItem implements Serializable {

	public static List<NavItem> fromLayouts(
		HttpServletRequest request, List<Layout> layouts,
		TemplateContext templateContext) {

		if (layouts == null) {
			return null;
		}

		List<NavItem> navItems = new ArrayList<NavItem>(layouts.size());

		for (Layout layout : layouts) {
			navItems.add(new NavItem(request, layout, templateContext));
		}

		return navItems;
	}

	public NavItem(
		HttpServletRequest request, Layout layout,
		TemplateContext templateContext) {

		_request = request;
		_themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);
		_layout = layout;
		_templateContext = templateContext;
	}

	public List<NavItem> getChildren() throws Exception {
		if (_children == null) {
			List<Layout> layouts = _layout.getChildren(
				_themeDisplay.getPermissionChecker());

			_children = fromLayouts(_request, layouts, _templateContext);
		}

		return _children;
	}

	public Layout getLayout() {
		return _layout;
	}

	public String getName() {
		return HtmlUtil.escape(getUnescapedName());
	}

	public String getRegularFullURL() throws Exception {
		String portalURL = PortalUtil.getPortalURL(_request);

		String regularURL = getRegularURL();

		if (StringUtil.startsWith(regularURL, portalURL) ||
			Validator.isUrl(regularURL)) {

			return regularURL;
		}
		else {
			return portalURL.concat(regularURL);
		}
	}

	public String getRegularURL() throws Exception {
		return _layout.getRegularURL(_request);
	}

	public String getResetLayoutURL() throws Exception {
		return _layout.getResetLayoutURL(_request);
	}

	public String getResetMaxStateURL() throws Exception {
		return _layout.getResetMaxStateURL(_request);
	}

	public String getTarget() {
		return _layout.getTarget();
	}

	public String getTitle() {
		return _layout.getTitle(_themeDisplay.getLocale());
	}

	public String getUnescapedName() {
		return _layout.getName(_themeDisplay.getLocale());
	}

	public String getURL() throws Exception {
		return HtmlUtil.escape(HtmlUtil.escapeHREF(getRegularFullURL()));
	}

	public boolean hasChildren() throws Exception {
		if (getChildren().size() > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public void icon() throws Exception {
		Object velocityTaglib = _templateContext.get("theme");

		Method method = MethodCache.get(_methodKey);

		method.invoke(velocityTaglib, _layout);
	}

	public boolean isChildSelected() throws PortalException, SystemException {
		return _layout.isChildSelected(
			_themeDisplay.isTilesSelectable(), _themeDisplay.getLayout());
	}

	public boolean isSelected() throws Exception {
		return _layout.isSelected(
			_themeDisplay.isTilesSelectable(), _themeDisplay.getLayout(),
			_themeDisplay.getLayout().getAncestorPlid());
	}

	private static MethodKey _methodKey = new MethodKey(
		"com.liferay.taglib.util.VelocityTaglib", "layoutIcon",
		new Class[] {Layout.class});

	private List<NavItem> _children;
	private Layout _layout;
	private HttpServletRequest _request;
	private TemplateContext _templateContext;
	private ThemeDisplay _themeDisplay;

}