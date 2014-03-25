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

package com.liferay.portal.theme;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.util.HtmlUtil;
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
 * This is a convenience class that allows access to layouts, their names and
 * metadata from templates - e.g. in a theme's portal-normal.vm - where
 * accessing the full API is clumsy.
 *
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class NavItem implements Serializable {

	/**
	 * Create a single level of NavItems from the list of given layouts.
	 * NavItems for nested layouts are created lazily, e.g. only when they will
	 * be accessed.
	 *
	 * On this level, no permission checks are performed - e.g. the resulting
	 * list will contain all layouts passed as parameters. Children of these
	 * NavItems will honor permissions though.
	 *
	 * @param request the currently served HttpServletRequest
	 * @param layouts the layouts that we need the NavItems for
	 * @param template the template that this NavItem object is being used on
	 * @return
	 */
	public static List<NavItem> fromLayouts(
		HttpServletRequest request, List<Layout> layouts, Template template) {

		if (layouts == null) {
			return null;
		}

		List<NavItem> navItems = new ArrayList<NavItem>(layouts.size());

		for (Layout layout : layouts) {
			navItems.add(new NavItem(request, layout, template));
		}

		return navItems;
	}

	public NavItem(
		HttpServletRequest request, Layout layout, Template template) {

		_request = request;
		_themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);
		_layout = layout;
		_template = template;
	}

	/**
	 * retrieve the child layouts that the current user has access to
	 *
	 * @return a list of the child pages, accessible by the current user
	 * @throws Exception
	 */
	public List<NavItem> getChildren() throws Exception {
		if (_children == null) {
			List<Layout> layouts = _layout.getChildren(
				_themeDisplay.getPermissionChecker());

			_children = fromLayouts(_request, layouts, _template);
		}

		return _children;
	}

	/**
	 * retrieve the underlying layout
	 *
	 * @return the layout represented by this navItem
	 */
	public Layout getLayout() {
		return _layout;
	}

	/**
	 * retrieve the underlying layout's ID
	 *
	 * @return the layoutId represented by this navItem
	 */
	public long getLayoutId() {
		return _layout.getLayoutId();
	}

	/**
	 * Retrieve the current page's name, properly HTML-escaped
	 *
	 * @return this layout's name, escaped for safe use in HTML
	 */
	public String getName() {
		return HtmlUtil.escape(getUnescapedName());
	}

	/**
	 * The underlying layout's full, absolute, URL (including the portal's URL
	 *
	 * @return the underlying layout's absolute URL
	 * @throws Exception
	 */
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

	/**
	 * get the underlying layout's regular URL.
	 *
	 * @return the underlying layout's URL
	 * @throws Exception
	 * @see    Layout#getRegularURL(request)
	 * @see    #getRegularFullURL()
	 */
	public String getRegularURL() throws Exception {
		return _layout.getRegularURL(_request);
	}

	/**
	 * @return
	 * @throws Exception
	 * @see    Layout#getResetLayoutURL(HttpServletRequest)
	 */
	public String getResetLayoutURL() throws Exception {
		return _layout.getResetLayoutURL(_request);
	}

	/**
	 * @return
	 * @throws Exception
	 * @see    Layout#getResetMaxStateURL(HttpServletRequest)
	 */
	public String getResetMaxStateURL() throws Exception {
		return _layout.getResetMaxStateURL(_request);
	}

	/**
	 * the underlying layout's target
	 *
	 * @return the underlying layout's target
	 * @see    Layout#getTarget()
	 */
	public String getTarget() {
		return _layout.getTarget();
	}

	/**
	 * the underlying layout's title in the locale that the current request is
	 * served in.
	 *
	 * @return title of the layout this navItem represents
	 */
	public String getTitle() {
		return _layout.getTitle(_themeDisplay.getLocale());
	}

	/**
	 * the raw, unescaped, name of the represented layout in the locale that the
	 * current request is served in
	 *
	 * @return the unescaped name of the layout this navItem represents
	 * @see    NavItem#getName()
	 */
	public String getUnescapedName() {
		return _layout.getName(_themeDisplay.getLocale());
	}

	/**
	 * The URL of the layout that this navItem represents, escaped for use in a
	 * href element.
	 *
	 * @return the URL of the page represented by this navItem
	 * @throws Exception
	 */
	public String getURL() throws Exception {
		return HtmlUtil.escapeHREF(getRegularFullURL());
	}

	/**
	 * information on the existence of child layouts for the layout represented
	 * by this navItem.
	 *
	 * @return
	 * @throws Exception
	 */
	public boolean hasChildren() throws Exception {
		List<NavItem> children = getChildren();

		if (!children.isEmpty()) {
			return true;
		}
		else {
			return false;
		}
	}

	public void icon() throws Exception {
		Object velocityTaglib = _template.get("theme");

		Method method = (Method)_template.get("velocityTaglib_layoutIcon");

		method.invoke(velocityTaglib, _layout);
	}

	public boolean isChildSelected() throws PortalException {
		return _layout.isChildSelected(
			_themeDisplay.isTilesSelectable(), _themeDisplay.getLayout());
	}

	/**
	 * @return
	 * @throws Exception
	 * @see    Layout#isSelected(boolean, Layout, long)
	 */
	public boolean isSelected() throws Exception {
		return _layout.isSelected(
			_themeDisplay.isTilesSelectable(), _themeDisplay.getLayout(),
			_themeDisplay.getLayout().getAncestorPlid());
	}

	private List<NavItem> _children;
	private Layout _layout;
	private HttpServletRequest _request;
	private Template _template;
	private ThemeDisplay _themeDisplay;

}