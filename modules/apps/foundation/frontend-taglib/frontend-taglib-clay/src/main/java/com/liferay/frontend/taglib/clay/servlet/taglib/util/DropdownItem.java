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

package com.liferay.frontend.taglib.clay.servlet.taglib.util;

/**
 * @author Carlos Lancha
 */
public class DropdownItem extends NavigationItem {

	public DropdownItem() {
		this("item");
	}

	public DropdownItem(String type) {
		_type = type;
	}

	public String getIcon() {
		return _icon;
	}

	public String getType() {
		return _type;
	}

	public boolean isQuickAction() {
		return _quickAction;
	}

	public boolean isSeparator() {
		return _separator;
	}

	public void setIcon(String icon) {
		_icon = icon;
	}

	public void setQuickAction(boolean quickAction) {
		_quickAction = quickAction;
	}

	public void setSeparator(boolean separator) {
		_separator = separator;
	}

	private String _icon;
	private boolean _quickAction;
	private boolean _separator;
	private String _type;

}