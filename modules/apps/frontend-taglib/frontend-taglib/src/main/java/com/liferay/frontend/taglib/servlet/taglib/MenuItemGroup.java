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

package com.liferay.frontend.taglib.servlet.taglib;

import com.liferay.petra.string.StringPool;

import java.util.List;

/**
 * @author Eudaldo Alonso
 */
public class MenuItemGroup {

	public MenuItemGroup(List<MenuItem> menuItems) {
		_menuItems = menuItems;

		_label = StringPool.BLANK;
		_showDivider = false;
	}

	public MenuItemGroup(List<MenuItem> menuItems, boolean showDivider) {
		_menuItems = menuItems;
		_showDivider = showDivider;

		_label = StringPool.BLANK;
	}

	public MenuItemGroup(String label, List<MenuItem> menuItems) {
		_label = label;
		_menuItems = menuItems;

		_showDivider = false;
	}

	public MenuItemGroup(
		String label, List<MenuItem> menuItems, boolean showDivider) {

		_label = label;
		_menuItems = menuItems;
		_showDivider = showDivider;
	}

	public String getLabel() {
		return _label;
	}

	public List<MenuItem> getMenuItems() {
		return _menuItems;
	}

	public boolean isShowDivider() {
		return _showDivider;
	}

	private final String _label;
	private final List<MenuItem> _menuItems;
	private final boolean _showDivider;

}