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

package com.liferay.commerce.frontend;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Marco Leo
 */
public class ClayCreationMenu {

	public ClayCreationMenu() {
		_clayCreationMenuActionItems = new ArrayList<>();
	}

	public void addClayCreationMenuActionItem(
		ClayCreationMenuActionItem clayCreationMenuActionItem) {

		_clayCreationMenuActionItems.add(clayCreationMenuActionItem);
	}

	public void addClayCreationMenuActionItem(String url, String label) {
		addClayCreationMenuActionItem(
			url, label, ClayMenuActionItem.CLAY_MENU_ACTION_ITEM_TARGET_LINK);
	}

	public void addClayCreationMenuActionItem(
		String url, String label, String target) {

		_clayCreationMenuActionItems.add(
			new ClayCreationMenuActionItem(url, label, target));
	}

	public List<ClayCreationMenuActionItem> getClayCreationMenuActionItems() {
		return _clayCreationMenuActionItems;
	}

	private final List<ClayCreationMenuActionItem> _clayCreationMenuActionItems;

}