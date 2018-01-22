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

import java.io.Serializable;

/**
 * @author Carlos Lancha
 */
public class DropdownItem implements Serializable {

	public boolean getActive() {
		return _active;
	}

	public boolean getDisabled() {
		return _disabled;
	}

	public String getHref() {
		return _href;
	}

	public String getIcon() {
		return _icon;
	}

	public String getLabel() {
		return _label;
	}

	public boolean getQuickAction() {
		return _quickAction;
	}

	public boolean getSeparator() {
		return _separator;
	}

	public String getType() {
		return _TYPE;
	}

	public void setActive(boolean active) {
		_active = active;
	}

	public void setDisabled(boolean disabled) {
		_disabled = disabled;
	}

	public void setHref(String href) {
		_href = href;
	}

	public void setIcon(String icon) {
		_icon = icon;
	}

	public void setLabel(String label) {
		_label = label;
	}

	public void setQuickAction(boolean quickAction) {
		_quickAction = quickAction;
	}

	public void setSeparator(boolean separator) {
		_separator = separator;
	}

	private static final String _TYPE = "link";

	private boolean _active;
	private boolean _disabled;
	private String _href;
	private String _icon;
	private String _label;
	private boolean _quickAction;
	private boolean _separator;

}