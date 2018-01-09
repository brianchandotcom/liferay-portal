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

package com.liferay.site.navigation.taglib.internal.util;

import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.theme.NavItem;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Pavel Savinov
 */
public class SiteNavigationMenuNavItem extends NavItem {

	public SiteNavigationMenuNavItem(
		HttpServletRequest request, Layout layout, String label, String url,
		List<NavItem> children) {

		super(request, layout, new HashMap<String, Object>());

		_label = label;
		_url = url;
		_children = children;
	}

	@Override
	public List<NavItem> getBrowsableChildren() {
		return _children;
	}

	@Override
	public List<NavItem> getChildren() {
		return _children;
	}

	@Override
	public String getName() {
		return _label;
	}

	@Override
	public String getURL() {
		return _url;
	}

	@Override
	public boolean isInNavigation(List<NavItem> navItems) {
		if (ListUtil.isEmpty(navItems)) {
			return true;
		}

		return super.isInNavigation(navItems);
	}

	private final List<NavItem> _children;
	private final String _label;
	private final String _url;

}