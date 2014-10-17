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
 * Represents a portal navigation item, providing access to layouts and metadata
 * from templates, which can be found in a theme's
 * <code>portal-normal.vm</code>.
 *
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class NavItem implements Serializable {

	/**
	 * Creates a single level of navigation items from the layouts. Navigation
	 * items for nested layouts are only created when they are accessed.
	 *
	 * <p>
	 * No permission checks are performed in this method. Permissions of child
	 * layouts are honored when accessing them via {@link #getChildren()}.
	 * </p>
	 *
	 * @param  request the currently served {@link HttpServletRequest}
	 * @param  layouts the layouts from which to create the navigation items
	 * @param  template the template to add navigation items to
	 * @return a single level of navigation items from the layouts, or
	 *         <code>null</code> if the collection of layouts was
	 *         <code>null</code>.
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
	 * Returns all of child layouts that the current user has permission to
	 * access from this navigation item's layout.
	 *
	 * @return the list of all child layouts that the current user has
	 *         permission to access from this navigation item's layout
	 * @throws Exception if an exception occurred
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
	 * Returns the navigation item's layout.
	 *
	 * @return the navigation item's layout
	 */
	public Layout getLayout() {
		return _layout;
	}

	/**
	 * Returns the ID of the navigation item's layout.
	 *
	 * @return the ID of the navigation item's layout
	 */
	public long getLayoutId() {
		return _layout.getLayoutId();
	}

	/**
	 * Returns the HTML-escaped name of the navigation item's layout.
	 *
	 * @return the HTML-escaped name of the navigation item's layout
	 */
	public String getName() {
		return HtmlUtil.escape(getUnescapedName());
	}

	/**
	 * Returns the full, absolute URL (including the portal's URL) of the
	 * navigation item's layout.
	 *
	 * @return the full, absolute URL of the navigation item's layout
	 * @throws Exception if an exception occurred
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
	 * Returns the regular URL of the navigation item's layout.
	 *
	 * @return the regular URL of the navigation item's layout
	 * @throws Exception if an exception occurred
	 */
	public String getRegularURL() throws Exception {
		return _layout.getRegularURL(_request);
	}

	public String getResetLayoutURL() throws Exception {
		return _layout.getResetLayoutURL(_request);
	}

	public String getResetMaxStateURL() throws Exception {
		return _layout.getResetMaxStateURL(_request);
	}

	/**
	 * Returns the target of the navigation item's layout.
	 *
	 * @return the target of the navigation item's layout
	 */
	public String getTarget() {
		return _layout.getTarget();
	}

	/**
	 * Returns the title of the navigation item's layout in the current
	 * request's locale.
	 *
	 * @return the title of the navigation item's layout in the current
	 *         request's locale
	 */
	public String getTitle() {
		return _layout.getTitle(_themeDisplay.getLocale());
	}

	/**
	 * Returns the unescaped name of the navigation item's layout in the current
	 * request's locale.
	 *
	 * @return the unescaped name of the navigation item's layout in the current
	 *         request's locale
	 */
	public String getUnescapedName() {
		return _layout.getName(_themeDisplay.getLocale());
	}

	/**
	 * Returns the URL of the navigation item's layout, in a format that makes
	 * it safe to use the URL as an HREF attribute value
	 *
	 * @return the URL of the navigation item's layout, in a format that makes
	 *         it safe to use the URL as an HREF attribute value
	 * @throws Exception if an exception occurred
	 */
	public String getURL() throws Exception {
		return HtmlUtil.escapeHREF(getRegularFullURL());
	}

	/**
	 * Returns <code>true</code> if the navigation item's layout has child
	 * layouts.
	 *
	 * @return <code>true</code> if the navigation item's layout has child
	 *         layouts; <code>false</code> otherwise
	 * @throws Exception if an exception occurred
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

	public boolean isSelected() throws Exception {
		return _layout.isSelected(
			_themeDisplay.isTilesSelectable(), _themeDisplay.getLayout(),
			_themeDisplay.getLayout().getAncestorPlid());
	}

	private List<NavItem> _children;
	private final Layout _layout;
	private final HttpServletRequest _request;
	private final Template _template;
	private final ThemeDisplay _themeDisplay;

}