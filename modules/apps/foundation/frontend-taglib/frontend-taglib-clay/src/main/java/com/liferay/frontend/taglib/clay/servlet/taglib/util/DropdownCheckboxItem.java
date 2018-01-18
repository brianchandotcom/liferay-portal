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

import java.util.List;

/**
 * @author Carlos Lancha
 */
public class DropdownCheckboxItem implements Serializable {

	public boolean getActive() {
		return _active;
	}

	public boolean getChecked() {
		return _checked;
	}

	public boolean getDisabled() {
		return _disabled;
	}

	public String getInputName() {
		return _inputName;
	}

	public String getInputValue() {
		return _inputValue;
	}

	public String getLabel() {
		return _label;
	}

	public boolean getSeparator() {
		return _separator;
	}

	public String getType() {
		return _type;
	}

	public void setActive(boolean active) {
		_active = active;
	}

	public void setChecked(boolean checked) {
		_checked = checked;
	}

	public void setDisabled(boolean disabled) {
		_disabled = disabled;
	}

	public void setInputName(String inputName) {
		_inputName = inputName;
	}

	public void setInputValue(String inputValue) {
		_inputValue = inputValue;
	}

	public void setLabel(String label) {
		_label = label;
	}

	public void setSeparator(boolean separator) {
		_separator = separator;
	}

	private boolean _active;
	private boolean _checked;
	private boolean _disabled;
	private String _inputName;
	private String _inputValue;
	private String _label;
	private boolean _separator;
	private String _type = "checkbox";

}