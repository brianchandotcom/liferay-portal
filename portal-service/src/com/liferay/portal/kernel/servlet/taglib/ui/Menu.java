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

import com.liferay.portal.kernel.util.StringPool;

import java.util.List;

/**
 * @author Sergio González
 */
public class Menu extends BaseUIItem {

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

	public String getView() {
		return _view;
	}

	public boolean isExtended() {
		return _extended;
	}

	public boolean isScroll() {
		return _scroll;
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

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	public void setDirection(String direction) {
		_direction = direction;
	}

	public void setExtended(boolean extended) {
		_extended = extended;
	}

	public void setIcon(String icon) {
		_icon = icon;
	}

	public void setLabel(String label) {
		_label = label;
	}

	public void setMenuItems(List<MenuItem> menuItems) {
		_menuItems = menuItems;
	}

	public void setMessage(String message) {
		_message = message;
	}

	public void setScroll(boolean scroll) {
		_scroll = scroll;
	}

	public void setShowArrow(boolean showArrow) {
		_showArrow = showArrow;
	}

	public void setShowExpanded(boolean showExpanded) {
		_showExpanded = showExpanded;
	}

	public void setShowWhenSingleIcon(boolean showWhenSingleIcon) {
		_showWhenSingleIcon = showWhenSingleIcon;
	}

	public void setTriggerCssClass(String triggerCssClass) {
		_triggerCssClass = triggerCssClass;
	}

	public void setView(String view) {
		_view = view;
	}

	private String _cssClass = StringPool.BLANK;
	private String _direction = "left";
	private boolean _extended = true;
	private String _icon = StringPool.BLANK;
	private String _label = StringPool.BLANK;
	private List<MenuItem> _menuItems;
	private String _message = StringPool.BLANK;
	private boolean _scroll = true;
	private boolean _showArrow = true;
	private boolean _showExpanded;
	private boolean _showWhenSingleIcon;
	private String _triggerCssClass = StringPool.BLANK;
	private String _view = StringPool.BLANK;

}