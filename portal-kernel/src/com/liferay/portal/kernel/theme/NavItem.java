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

package com.liferay.portal.kernel.theme;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutType;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PredicateFilter;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.webserver.WebServerServletTokenUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
	 * @param      request the currently served {@link HttpServletRequest}
	 * @param      layouts the layouts from which to create the navigation items
	 * @return     a single level of navigation items from the layouts, or
	 *             <code>null</code> if the collection of layouts was
	 *             <code>null</code>.
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #fromLayouts(HttpServletRequest, ThemeDisplay, Map)}
	 */
	@Deprecated
	public static List<NavItem> fromLayouts(
		HttpServletRequest request, List<Layout> layouts,
		Map<String, Object> contextObjects) {

		if ((layouts == null) || layouts.isEmpty()) {
			return Collections.emptyList();
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		List<NavItem> navItems = new ArrayList<>(layouts.size());

		for (Layout layout : layouts) {
			navItems.add(
				new NavItem(request, themeDisplay, layout, contextObjects));
		}

		return navItems;
	}

	public static List<NavItem> fromLayouts(
			HttpServletRequest request, ThemeDisplay themeDisplay,
			Map<String, Object> contextObjects)
		throws PortalException {

		List<Layout> parentLayouts = themeDisplay.getLayouts();

		if (parentLayouts == null) {
			return Collections.emptyList();
		}

		Map<Long, List<Layout>> layoutChildLayouts =
			LayoutLocalServiceUtil.getLayoutChildLayouts(
				themeDisplay.getLayoutSet(), parentLayouts);

		for (List<Layout> childLayouts : layoutChildLayouts.values()) {
			Iterator<Layout> iterator = childLayouts.iterator();

			while (iterator.hasNext()) {
				Layout childLayout = iterator.next();

				if (childLayout.isHidden() ||
					!LayoutPermissionUtil.contains(
						themeDisplay.getPermissionChecker(), childLayout,
						ActionKeys.VIEW)) {

					iterator.remove();
				}
			}
		}

		List<NavItem> navItems = new ArrayList<>(parentLayouts.size());

		for (Layout parentLayout : parentLayouts) {
			List<Layout> childLayouts = layoutChildLayouts.get(
				parentLayout.getLayoutId());

			navItems.add(
				new NavItem(
					request, themeDisplay, parentLayout, childLayouts,
					contextObjects));
		}

		return navItems;
	}

	public NavItem(
		HttpServletRequest request, Layout layout,
		Map<String, Object> contextObjects) {

		this(
			request, (ThemeDisplay)request.getAttribute(WebKeys.THEME_DISPLAY),
			layout, contextObjects);
	}

	public NavItem(
		HttpServletRequest request, ThemeDisplay themeDisplay, Layout layout,
		Map<String, Object> contextObjects) {

		_request = request;
		_themeDisplay = themeDisplay;
		_layout = layout;
		_contextObjects = contextObjects;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof NavItem)) {
			return false;
		}

		NavItem navItem = (NavItem)obj;

		if (getLayoutId() == navItem.getLayoutId()) {
			return true;
		}

		return false;
	}

	/**
	 * Returns all of the browsable child layouts that the current user has
	 * permission to access from this navigation item's layout.
	 *
	 * @return the list of all browsable child layouts that the current user has
	 *         permission to access from this navigation item's layout
	 * @throws Exception if an exception occurred
	 */
	public List<NavItem> getBrowsableChildren() throws Exception {
		if (_browsableChildren == null) {
			List<NavItem> children = getChildren();

			_browsableChildren = ListUtil.filter(
				children,
				new PredicateFilter<NavItem>() {

					@Override
					public boolean filter(NavItem navItem) {
						return navItem.isBrowsable();
					}

				});
		}

		return _browsableChildren;
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

			_children = _fromLayouts(
				_request, _themeDisplay, layouts, _contextObjects);
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
		String portalURL = _themeDisplay.getPortalURL();

		String regularURL = getRegularURL();

		if (StringUtil.startsWith(regularURL, portalURL) ||
			Validator.isUrl(regularURL)) {

			return regularURL;
		}

		return portalURL.concat(regularURL);
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
		return _layout.getTitle(_themeDisplay.getLanguageId());
	}

	/**
	 * Returns the unescaped name of the navigation item's layout in the current
	 * request's locale.
	 *
	 * @return the unescaped name of the navigation item's layout in the current
	 *         request's locale
	 */
	public String getUnescapedName() {
		return _layout.getName(_themeDisplay.getLanguageId());
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
	 * Returns <code>true</code> if the navigation item's layout has browsable
	 * child layouts.
	 *
	 * @return <code>true</code> if the navigation item's layout has browsable
	 *         child layouts; <code>false</code> otherwise
	 * @throws Exception if an exception occurred
	 */
	public boolean hasBrowsableChildren() throws Exception {
		List<NavItem> browsableChildren = getBrowsableChildren();

		if (!browsableChildren.isEmpty()) {
			return true;
		}

		return false;
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

		return false;
	}

	@Override
	public int hashCode() {
		return _layout.hashCode();
	}

	public String iconURL() throws Exception {
		if ((_layout == null) || !_layout.isIconImage()) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(5);

		sb.append(_themeDisplay.getPathImage());
		sb.append("/layout_icon?img_id=");
		sb.append(_layout.getIconImageId());
		sb.append("&t=");
		sb.append(WebServerServletTokenUtil.getToken(_layout.getIconImageId()));

		return sb.toString();
	}

	public boolean isBrowsable() {
		LayoutType layoutType = _layout.getLayoutType();

		return layoutType.isBrowsable();
	}

	public boolean isChildSelected() throws PortalException {
		return _layout.isChildSelected(
			_themeDisplay.isTilesSelectable(), _themeDisplay.getLayout());
	}

	public boolean isInNavigation(List<NavItem> navItems) {
		if (navItems == null) {
			return false;
		}

		return navItems.contains(this);
	}

	public boolean isSelected() throws Exception {
		Layout layout = _themeDisplay.getLayout();

		return _layout.isSelected(
			_themeDisplay.isTilesSelectable(), _themeDisplay.getLayout(),
			layout.getAncestorPlid());
	}

	private static List<NavItem> _fromLayouts(
		HttpServletRequest request, ThemeDisplay themeDisplay,
		List<Layout> layouts, Map<String, Object> contextObjects) {

		if ((layouts == null) || layouts.isEmpty()) {
			return Collections.emptyList();
		}

		List<NavItem> navItems = new ArrayList<>(layouts.size());

		for (Layout layout : layouts) {
			navItems.add(
				new NavItem(request, themeDisplay, layout, contextObjects));
		}

		return navItems;
	}

	private NavItem(
		HttpServletRequest request, ThemeDisplay themeDisplay, Layout layout,
		List<Layout> childLayouts, Map<String, Object> contextObjects) {

		_request = request;
		_themeDisplay = themeDisplay;
		_layout = layout;
		_contextObjects = contextObjects;

		_children = _fromLayouts(
			request, themeDisplay, childLayouts, contextObjects);
	}

	private List<NavItem> _browsableChildren;
	private List<NavItem> _children;
	private final Map<String, Object> _contextObjects;
	private final Layout _layout;
	private final HttpServletRequest _request;
	private final ThemeDisplay _themeDisplay;

}