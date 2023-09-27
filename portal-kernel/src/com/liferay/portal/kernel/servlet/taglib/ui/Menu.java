/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.servlet.taglib.ui;

import com.liferay.petra.string.StringPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Sergio González
 */
public class Menu extends BaseUIItem {

	public String getCssClass() {
		return _cssClass;
	}

	public Map<String, Object> getData() {
		if (_data == null) {
			_data = new HashMap<>();
		}

		return _data;
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

	public String getMarkupView() {
		return _markupView;
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

	public void setData(Map<String, Object> data) {
		_data = data;
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

	public void setMarkupView(String markupView) {
		_markupView = markupView;
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

	private String _cssClass = StringPool.BLANK;
	private Map<String, Object> _data;
	private String _direction = "left";
	private boolean _extended = true;
	private String _icon = StringPool.BLANK;
	private String _label = StringPool.BLANK;
	private String _markupView = StringPool.BLANK;
	private List<MenuItem> _menuItems;
	private String _message = StringPool.BLANK;
	private boolean _scroll = true;
	private boolean _showArrow = true;
	private boolean _showExpanded;
	private boolean _showWhenSingleIcon;
	private String _triggerCssClass = StringPool.BLANK;

}