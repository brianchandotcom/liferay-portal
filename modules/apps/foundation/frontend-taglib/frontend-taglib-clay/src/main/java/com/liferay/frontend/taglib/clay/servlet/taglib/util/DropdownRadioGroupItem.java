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
public class DropdownRadioGroupItem implements Serializable {

	public String getInputName() {
		return _inputName;
	}

	public List<DropdownRadioItem> getItems() {
		return _items;
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

	public void setInputName(String inputName) {
		_inputName = inputName;
	}

	public void setItems(List<DropdownRadioItem> items) {
		_items = items;
	}

	public void setLabel(String label) {
		_label = label;
	}

	public void setSeparator(boolean separator) {
		_separator = separator;
	}

	private String _inputName;
	private List<DropdownRadioItem> _items;
	private String _label;
	private boolean _separator;
	private String _type = "radiogroup";

}