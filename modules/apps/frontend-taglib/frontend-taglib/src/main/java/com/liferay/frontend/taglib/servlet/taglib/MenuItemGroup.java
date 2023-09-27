/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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