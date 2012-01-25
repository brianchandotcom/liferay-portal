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
 */
public class NavItem implements Serializable {

	public static NavItem fromLayout(RequestVars vars, Layout layout) {
		return new NavItem(vars, layout);
	}

	public static List<NavItem> fromLayouts(
		RequestVars vars, List<Layout> layouts) {

		if (layouts == null) {
			return null;
		}

		List<NavItem> navItems = new ArrayList<NavItem>(layouts.size());

		for (Layout layout : layouts) {
			navItems.add(fromLayout(vars, layout));
		}

		return navItems;
	}

	public NavItem(RequestVars vars, Layout layout) {
		_vars = vars;
		_layout = layout;
	}

	public List<NavItem> getChildren() throws Exception {
		if (_children == null) {
			ThemeDisplay themeDisplay = _vars.getThemeDisplay();

			List<Layout> layouts = _layout.getChildren(
				themeDisplay.getPermissionChecker());

			_children = fromLayouts(_vars, layouts);
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
		String portalURL = PortalUtil.getPortalURL(_vars.getRequest());

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
		return _layout.getRegularURL(_vars.getRequest());
	}

	public String getResetLayoutURL() throws Exception {
		return _layout.getResetLayoutURL(_vars.getRequest());
	}

	public String getResetMaxStateURL() throws Exception {
		return _layout.getResetMaxStateURL(_vars.getRequest());
	}

	public String getTarget() {
		return _layout.getTarget();
	}

	public String getTitle() {
		return _layout.getTitle(_vars.getThemeDisplay().getLocale());
	}

	public String getUnescapedName() {
		return _layout.getName(_vars.getThemeDisplay().getLocale());
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
		HttpServletRequest request = _vars.getRequest();

		Object velocityTaglib = request.getAttribute(WebKeys.VELOCITY_TAGLIB);

		Method method = MethodCache.get(_methodKey);

		method.invoke(velocityTaglib, _layout);
	}

	public boolean isChildSelected() throws PortalException, SystemException {
		ThemeDisplay themeDisplay = _vars.getThemeDisplay();

		return _layout.isChildSelected(
			themeDisplay.isTilesSelectable(), themeDisplay.getLayout());
	}

	public boolean isSelected() {
		ThemeDisplay themeDisplay = _vars.getThemeDisplay();

		return _layout.isSelected(
			themeDisplay.isTilesSelectable(), themeDisplay.getLayout(),
			_vars.getAncestorPlid());
	}

	private static MethodKey _methodKey = new MethodKey(
		"com.liferay.taglib.util.VelocityTaglib", "layoutIcon",
		new Class[] {Layout.class});

	private List<NavItem> _children;
	private Layout _layout;
	private RequestVars _vars;

}