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

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownGroupItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Carlos Lancha
 */
public class ManagementToolbarsDisplayContext {

	public List<DropdownItem> getActionItems() {
		if (_actionItems != null) {
			return _actionItems;
		}

		_actionItems = new ArrayList<>();

		DropdownItem editItem = new DropdownItem();

		editItem.setLabel("Edit");

		DropdownItem downloadItem = new DropdownItem();

		downloadItem.setLabel("Download");
		downloadItem.setIcon("download");
		downloadItem.setQuickAction(true);

		DropdownItem deleteItem = new DropdownItem();

		deleteItem.setLabel("Delete");
		deleteItem.setIcon("trash");
		deleteItem.setQuickAction(true);

		_actionItems.add(editItem);
		_actionItems.add(downloadItem);
		_actionItems.add(deleteItem);

		return _actionItems;
	}

	public Map<String, Object> getCreationMenu() {
		if (_creationMenu != null) {
			return _creationMenu;
		}

		Map<String, Object> _creationMenu = new HashMap<>();

		List<Map<String, Object>> items = new ArrayList<>();

		for (int i = 0; i < 4; i++) {
			Map<String, Object> item = new HashMap<>();

			item.put("href", "#" + i);
			item.put("label", "Sample " + i);

			items.add(item);
		}

		_creationMenu.put("items", items);

		return _creationMenu;
	}

	public List<DropdownGroupItem> getFilterItems() {
		if (_filterItems != null) {
			return _filterItems;
		}

		_filterItems = new ArrayList<>();

		DropdownGroupItem filterByDropdownGroupItem = new DropdownGroupItem();

		List<Object> filterByDropdownGroupItems = new ArrayList<>();

		filterByDropdownGroupItem.setItems(filterByDropdownGroupItems);
		filterByDropdownGroupItem.setLabel("Filter By");

		for (int i = 0; i < 2; i++) {
			DropdownItem dropdownFilterByItem = new DropdownItem();

			dropdownFilterByItem.setHref("#" + i);
			dropdownFilterByItem.setLabel("Filter " + i);

			filterByDropdownGroupItems.add(dropdownFilterByItem);
		}

		_filterItems.add(filterByDropdownGroupItem);

		DropdownGroupItem orderByDropdownGroupItem = new DropdownGroupItem();

		List<Object> orderByDropdownGroupItems = new ArrayList<>();

		orderByDropdownGroupItem.setItems(orderByDropdownGroupItems);
		orderByDropdownGroupItem.setLabel("Order By");

		for (int i = 0; i < 2; i++) {
			DropdownItem dropdownOrderByItem = new DropdownItem();

			dropdownOrderByItem.setHref("#" + i);
			dropdownOrderByItem.setLabel("Filter " + i);

			orderByDropdownGroupItems.add(dropdownOrderByItem);
		}

		_filterItems.add(orderByDropdownGroupItem);

		return _filterItems;
	}

	public List<DropdownItem> getViewTypesItems() {
		if (_viewTypes != null) {
			return _viewTypes;
		}

		_viewTypes = new ArrayList<>();

		DropdownItem cardViewType = new DropdownItem();

		cardViewType.setActive(true);
		cardViewType.setIcon("cards2");
		cardViewType.setLabel("Card");

		DropdownItem listViewType = new DropdownItem();

		listViewType.setIcon("list");
		listViewType.setLabel("List");

		DropdownItem tableViewType = new DropdownItem();

		tableViewType.setIcon("table");
		tableViewType.setLabel("Table");

		_viewTypes.add(cardViewType);
		_viewTypes.add(listViewType);
		_viewTypes.add(tableViewType);

		return _viewTypes;
	}

	private List<DropdownItem> _actionItems;
	private Map<String, Object> _creationMenu;
	private List<DropdownGroupItem> _filterItems;
	private List<DropdownItem> _viewTypes;

}