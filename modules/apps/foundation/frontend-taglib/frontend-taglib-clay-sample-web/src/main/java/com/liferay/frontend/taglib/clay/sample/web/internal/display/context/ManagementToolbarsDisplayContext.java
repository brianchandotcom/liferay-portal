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

import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItemList;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Carlos Lancha
 */
public class ManagementToolbarsDisplayContext {

	public ManagementToolbarsDisplayContext(HttpServletRequest request) {
		_request = request;
	}

	public DropdownItemList getActionDropdownItemList() {
		if (_actionDropdownItemList != null) {
			return _actionDropdownItemList;
		}

		_actionDropdownItemList = new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.setHref("#edit");
						dropdownItem.setLabel("Edit");
					});

				add(
					dropdownItem -> {
						dropdownItem.setHref("#download");
						dropdownItem.setIcon("download");
						dropdownItem.setLabel("Download");
						dropdownItem.setQuickAction(true);
					});

				add(
					dropdownItem -> {
						dropdownItem.setHref("#delete");
						dropdownItem.setLabel("Delete");
						dropdownItem.setIcon("trash");
						dropdownItem.setQuickAction(true);
					});
			}
		};

		return _actionDropdownItemList;
	}

	public CreationMenu getCreationMenu() {
		if (_creationMenu != null) {
			return _creationMenu;
		}

		_creationMenu = new CreationMenu(_request) {
			{
				addPrimaryDropdownItem(
					dropdownItem -> {
						dropdownItem.setHref("#1");
						dropdownItem.setLabel("Sample 1");
					});

				addPrimaryDropdownItem(
					dropdownItem -> {
						dropdownItem.setHref("#2");
						dropdownItem.setLabel("Sample 2");
					});

				addFavoriteDropdownItem(
					dropdownItem -> {
						dropdownItem.setHref("#3");
						dropdownItem.setLabel("Favorite 1");
					});

				addFavoriteDropdownItem(
					dropdownItem -> {
						dropdownItem.setHref("#4");
						dropdownItem.setLabel("Other item");
					});
			}
		};

		return _creationMenu;
	}

	public DropdownItemList getFilterItemList() {
		if (_filterItemList != null) {
			return _filterItemList;
		}

		DropdownItemList filterByItemList = new DropdownItemList() {
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

		DropdownItemList orderByItemList = new DropdownItemList() {
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

		_filterItemList = new DropdownItemList() {
			{
				addGroup(
					dropdownGroupItem -> {
						dropdownGroupItem.setDropdownItems(filterByItemList);
						dropdownGroupItem.setLabel("Filter By");
					});

				addGroup(
					dropdownGroupItem -> {
						dropdownGroupItem.setDropdownItems(orderByItemList);
						dropdownGroupItem.setLabel("Order By");
					});
			}
		};

		return _filterItemList;
	}

	public ViewTypeItemList getViewTypesItemList() {
		if (_viewTypesItemList != null) {
			return _viewTypesItemList;
		}

		_viewTypesItemList = new ViewTypeItemList() {
			{
				addCardViewType(
					viewTypeItem -> {
						viewTypeItem.setActive(true);
						viewTypeItem.setLabel("Card");
					});

				addListViewType(
					viewTypeItem -> {
						viewTypeItem.setLabel("List");
					});

				addTableViewType(
					viewTypeItem -> {
						viewTypeItem.setLabel("Table");
					});
			}
		};

		return _viewTypesItemList;
	}

	private DropdownItemList _actionDropdownItemList;
	private CreationMenu _creationMenu;
	private DropdownItemList _filterItemList;
	private final HttpServletRequest _request;
	private ViewTypeItemList _viewTypesItemList;

}