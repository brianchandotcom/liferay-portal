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

package com.liferay.frontend.taglib.clay.internal.data.set;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Marco Leo
 */
public class ClayDataSetDataRow {

	public ClayDataSetDataRow(Object item) {
		_item = item;
		_actionDropdownItems = new ArrayList<>();
	}

	public void addActionDropdownItems(List<DropdownItem> actionDropdownItems) {
		this._actionDropdownItems.addAll(actionDropdownItems);
	}

	public List<DropdownItem> getActionDropdownItems() {
		return _actionDropdownItems;
	}

	@JsonUnwrapped
	public Object getItem() {
		return _item;
	}

	private final List<DropdownItem> _actionDropdownItems;
	private final Object _item;

}