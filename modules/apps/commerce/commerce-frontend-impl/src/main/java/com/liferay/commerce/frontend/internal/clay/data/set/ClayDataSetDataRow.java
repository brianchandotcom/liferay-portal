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

package com.liferay.commerce.frontend.internal.clay.data.set;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import com.liferay.commerce.frontend.clay.data.set.ClayDataSetAction;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Marco Leo
 */
public class ClayDataSetDataRow {

	public ClayDataSetDataRow(Object item) {
		_item = item;
		_actionItems = new ArrayList<>();
	}

	public void addActionItems(List<ClayDataSetAction> actionItems) {
		_actionItems.addAll(actionItems);
	}

	public List<ClayDataSetAction> getActionItems() {
		return _actionItems;
	}

	@JsonUnwrapped
	public Object getItem() {
		return _item;
	}

	private final List<ClayDataSetAction> _actionItems;
	private final Object _item;

}