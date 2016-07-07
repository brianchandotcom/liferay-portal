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

import com.liferay.portal.kernel.util.StringPool;

import java.util.List;

/**
 * @author Eudaldo Alonso
 */
public class MenuItemGroup {

	public MenuItemGroup(List<AddMenuItem> addMenuItems) {
		_addMenuItems = addMenuItems;

		_label = StringPool.BLANK;
		_showDivider = false;
	}

	public MenuItemGroup(String label, List<AddMenuItem> addMenuItems) {
		_label = label;
		_addMenuItems = addMenuItems;

		_showDivider = false;
	}

	public MenuItemGroup(List<AddMenuItem> addMenuItems, boolean showDivider) {
		_addMenuItems = addMenuItems;
		_showDivider = showDivider;

		_label = StringPool.BLANK;
	}

	public MenuItemGroup(
		String label, List<AddMenuItem> addMenuItems, boolean showDivider) {

		_label = label;
		_addMenuItems = addMenuItems;
		_showDivider = showDivider;
	}

	public List<AddMenuItem> getAddMenuItems() {
		return _addMenuItems;
	}

	public String getLabel() {
		return _label;
	}

	public boolean isShowDivider() {
		return _showDivider;
	}

	private final List<AddMenuItem> _addMenuItems;
	private final String _label;
	private final boolean _showDivider;

}