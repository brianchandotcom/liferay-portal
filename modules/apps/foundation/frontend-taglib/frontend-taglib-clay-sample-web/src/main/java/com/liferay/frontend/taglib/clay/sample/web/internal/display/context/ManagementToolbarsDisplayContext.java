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

import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuDropdown;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItemList;
import com.liferay.portal.kernel.util.Validator;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Carlos Lancha
 */
public class ManagementToolbarsDisplayContext {

	public ManagementToolbarsDisplayContext(HttpServletRequest request) {
		_request = request;
	}

	public DropdownItemList getActionDropdownItems() {
		if (_actionDropdownItems != null) {
			return _actionDropdownItems;
		}

		_actionDropdownItems = new DropdownItemList() {
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

		return _actionDropdownItems;
	}

	public CreationMenuDropdown getCreationMenu() {
		if (Validator.isNotNull(_creationMenuDropdown)) {
			return _creationMenuDropdown;
		}

		_creationMenuDropdown = new CreationMenuDropdown(_request) {
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

		return _creationMenuDropdown;
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

	public ViewTypeItemList getViewTypesItems() {
		if (Validator.isNotNull(_viewTypes)) {
			return _viewTypes;
		}

		_viewTypes = new ViewTypeItemList() {
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

		return _viewTypes;
	}

	private DropdownItemList _actionDropdownItems;
	private CreationMenuDropdown _creationMenuDropdown;
	private DropdownItemList _filterItems;
	private final HttpServletRequest _request;
	private ViewTypeItemList _viewTypes;

}