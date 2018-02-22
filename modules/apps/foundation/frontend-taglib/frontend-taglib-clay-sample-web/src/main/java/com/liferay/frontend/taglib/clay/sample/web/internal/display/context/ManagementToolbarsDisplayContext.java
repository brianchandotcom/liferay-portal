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

package com.liferay.frontend.taglib.clay.sample.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Carlos Lancha
 */
public class ManagementToolbarsDisplayContext {

	public DropdownItemList getActionDropdownItems() {
		if (_actionDropdownItems != null) {
			return _actionDropdownItems;
		}

		_actionDropdownItems = new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.setLabel("Edit");
					});

				add(
					dropdownItem -> {
						dropdownItem.setIcon("download");
						dropdownItem.setLabel("Download");
						dropdownItem.setQuickAction(true);
					});

				add(
					dropdownItem -> {
						dropdownItem.setLabel("Delete");
						dropdownItem.setIcon("trash");
						dropdownItem.setQuickAction(true);
					});
			}
		};

		return _actionDropdownItems;
	}

	public Map<String, Object> getCreationMenu() {
		if (_creationMenu != null) {
			return _creationMenu;
		}

		_creationMenu = new HashMap<>();

		DropdownItemList creationMenuItems = new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.setHref("#1");
						dropdownItem.setLabel("Sample 1");
					});

				add(
					dropdownItem -> {
						dropdownItem.setHref("#2");
						dropdownItem.setLabel("Sample 2");
					});

				add(
					dropdownItem -> {
						dropdownItem.setHref("#3");
						dropdownItem.setLabel("Sample 3");
					});

				add(
					dropdownItem -> {
						dropdownItem.setHref("#4");
						dropdownItem.setLabel("Sample 4");
					});
			}
		};

		_creationMenu.put("items", creationMenuItems);

		return _creationMenu;
	}

	public DropdownItemList getFilterItems() {
		if (_filterItems != null) {
			return _filterItems;
		}

		DropdownItemList filterByItems = new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.setHref("#1");
						dropdownItem.setLabel("Filter 1");
					});

				add(
					dropdownItem -> {
						dropdownItem.setHref("#2");
						dropdownItem.setLabel("Filter 2");
					});
			}
		};

		DropdownItemList orderByItems = new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.setHref("#3");
						dropdownItem.setLabel("Order 1");
					});

				add(
					dropdownItem -> {
						dropdownItem.setHref("#4");
						dropdownItem.setLabel("Order 2");
					});
			}
		};

		_filterItems = new DropdownItemList() {
			{
				addGroup(
					dropdownGroupItem -> {
						dropdownGroupItem.setDropdownItems(filterByItems);
						dropdownGroupItem.setLabel("Filter By");
					});

				addGroup(
					dropdownGroupItem -> {
						dropdownGroupItem.setDropdownItems(orderByItems);
						dropdownGroupItem.setLabel("Order By");
					});
			}
		};

		return _filterItems;
	}

	public DropdownItemList getViewTypesItems() {
		if (_viewTypes != null) {
			return _viewTypes;
		}

		_viewTypes = new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.setActive(true);
						dropdownItem.setIcon("cards2");
						dropdownItem.setLabel("Card");
					});

				add(
					dropdownItem -> {
						dropdownItem.setIcon("list");
						dropdownItem.setLabel("List");
					});

				add(
					dropdownItem -> {
						dropdownItem.setIcon("table");
						dropdownItem.setLabel("Table");
					});
			}
		};

		return _viewTypes;
	}

	private DropdownItemList _actionDropdownItems;
	private Map<String, Object> _creationMenu;
	private DropdownItemList _filterItems;
	private DropdownItemList _viewTypes;

}