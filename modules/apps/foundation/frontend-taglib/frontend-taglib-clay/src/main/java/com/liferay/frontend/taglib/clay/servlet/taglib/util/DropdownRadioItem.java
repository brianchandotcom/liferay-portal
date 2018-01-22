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
public class DropdownRadioItem implements Serializable {

	public boolean getActive() {
		return _active;
	}

	public boolean getChecked() {
		return _checked;
	}

	public boolean getDisabled() {
		return _disabled;
	}

	public String getInputValue() {
		return _inputValue;
	}

	public String getLabel() {
		return _label;
	}

	public String getType() {
		return _TYPE;
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

	public void setInputValue(String inputValue) {
		_inputValue = inputValue;
	}

	public void setLabel(String label) {
		_label = label;
	}

	private static final String _TYPE = "radio";

	private boolean _active;
	private boolean _checked;
	private boolean _disabled;
	private String _inputValue;
	private String _label;

}