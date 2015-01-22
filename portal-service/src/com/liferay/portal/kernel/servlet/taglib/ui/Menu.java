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

package com.liferay.portal.kernel.servlet.taglib.ui;

import java.util.List;

/**
 * @author Sergio González
 */
public class Menu extends BaseUIItem {

	public Menu(
		String cssClass, String direction, boolean extended, String icon,
		String label, List<MenuItem> menuItems, String message,
		boolean showArrow, boolean showExpanded, boolean showWhenSingleIcon,
		String triggerCssClass) {

		_cssClass = cssClass;
		_direction = direction;
		_extended = extended;
		_icon = icon;
		_label = label;
		_menuItems = menuItems;
		_message = message;
		_showArrow = showArrow;
		_showExpanded = showExpanded;
		_showWhenSingleIcon = showWhenSingleIcon;
		_triggerCssClass = triggerCssClass;
	}

	public String getCssClass() {
		return _cssClass;
	}

	public String getDirection() {
		return _direction;
	}

	public String getIcon() {
		return _icon;
	}

	public String getLabel() {
		return _label;
	}

	public List<MenuItem> getMenuItems() {
		return _menuItems;
	}

	public String getMessage() {
		return _message;
	}

	public String getTriggerCssClass() {
		return _triggerCssClass;
	}

	public boolean isExtended() {
		return _extended;
	}

	public boolean isShowArrow() {
		return _showArrow;
	}

	public boolean isShowExpanded() {
		return _showExpanded;
	}

	public boolean isShowWhenSingleIcon() {
		return _showWhenSingleIcon;
	}

	private final String _cssClass;
	private final String _direction;
	private final boolean _extended;
	private final String _icon;
	private final String _label;
	private final List<MenuItem> _menuItems;
	private final String _message;
	private final boolean _showArrow;
	private final boolean _showExpanded;
	private final boolean _showWhenSingleIcon;
	private final String _triggerCssClass;

}