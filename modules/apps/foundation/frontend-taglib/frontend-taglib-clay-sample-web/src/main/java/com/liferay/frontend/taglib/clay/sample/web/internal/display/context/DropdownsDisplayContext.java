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

import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Chema Balsas
 */
public class DropdownsDisplayContext {

	public List<NavigationItem> getDefaultDropdownItems() {
		if (_defaultDropdownItems != null) {
			return _defaultDropdownItems;
		}

		_defaultDropdownItems = new ArrayList<>();

		for (int i = 0; i < 4; i++) {
			NavigationItem navigationItem = new NavigationItem();

			if (i == 1) {
				navigationItem.setDisabled(true);
			}
			else if (i == 2) {
				navigationItem.setActive(true);
			}

			navigationItem.setHref("#" + i);
			navigationItem.setLabel("Option " + i);

			_defaultDropdownItems.add(navigationItem);
		}

		return _defaultDropdownItems;
	}

	public List<NavigationItem> getGroupDropdownItems() {
		if (_groupDropdownItems != null) {
			return _groupDropdownItems;
		}

		_groupDropdownItems = new ArrayList<>();

		NavigationItem group1NavigationItem = new NavigationItem();

		List<NavigationItem> group1NavigationItemItems = new ArrayList<>();

		group1NavigationItem.setItems(group1NavigationItemItems);

		group1NavigationItem.setLabel("Group 1");
		group1NavigationItem.setSeparator(true);
		group1NavigationItem.setType(NavigationItem.TYPE_GROUP);

		for (int i = 0; i < 2; i++) {
			NavigationItem navigationItem = new NavigationItem();

			navigationItem.setHref("#" + i);
			navigationItem.setLabel("Group 1 - Option " + i);

			group1NavigationItemItems.add(navigationItem);
		}

		_groupDropdownItems.add(group1NavigationItem);

		NavigationItem group2NavigationItem = new NavigationItem();

		List<NavigationItem> group2NavigationItemItems = new ArrayList<>();

		group2NavigationItem.setItems(group2NavigationItemItems);

		group2NavigationItem.setLabel("Group 2");
		group2NavigationItem.setType(NavigationItem.TYPE_GROUP);

		for (int i = 0; i < 2; i++) {
			NavigationItem navigationItem = new NavigationItem();

			navigationItem.setHref("#" + i);
			navigationItem.setLabel("Group 2 - Option " + i);

			group2NavigationItemItems.add(navigationItem);
		}

		_groupDropdownItems.add(group2NavigationItem);

		return _groupDropdownItems;
	}

	public List<NavigationItem> getIconDropdownItems() {
		if (_iconDropdownItems != null) {
			return _iconDropdownItems;
		}

		_iconDropdownItems = new ArrayList<>();

		for (int i = 0; i < 6; i++) {
			NavigationItem navigationItem = new NavigationItem();

			if (i < 3) {
				navigationItem.setIcon("check-circle-full");
			}
			else if (i == 3) {
				navigationItem.setActive(true);
			}
			else {
				navigationItem.setDisabled(true);
			}

			navigationItem.setHref("#" + i);
			navigationItem.setLabel("Option " + i);

			_iconDropdownItems.add(navigationItem);
		}

		return _iconDropdownItems;
	}

	public List<NavigationItem> getInputDropdownItems() {
		if (_inputDropdownItems != null) {
			return _inputDropdownItems;
		}

		_inputDropdownItems = new ArrayList<>();

		NavigationItem group1NavigationItem = new NavigationItem();

		List<NavigationItem> group1NavigationItemItems = new ArrayList<>();

		group1NavigationItem.setItems(group1NavigationItemItems);

		group1NavigationItem.setLabel("Group 1");
		group1NavigationItem.setSeparator(true);
		group1NavigationItem.setType(NavigationItem.TYPE_GROUP);

		for (int i = 0; i < 2; i++) {
			NavigationItem navigationItem = new NavigationItem();

			navigationItem.setInputName("checkbox" + i);
			navigationItem.setInputValue("checkboxValue" + i);
			navigationItem.setLabel("Group 1 - Option " + i);
			navigationItem.setType(NavigationItem.TYPE_CHECKBOX);

			group1NavigationItemItems.add(navigationItem);
		}

		_inputDropdownItems.add(group1NavigationItem);

		NavigationItem group2NavigationItem = new NavigationItem();

		group2NavigationItem.setInputName("radiogroup");

		List<NavigationItem> group2NavigationItemItems = new ArrayList<>();

		group2NavigationItem.setItems(group2NavigationItemItems);

		group2NavigationItem.setLabel("Group 2");
		group2NavigationItem.setType(NavigationItem.TYPE_RADIOGROUP);

		for (int i = 0; i < 2; i++) {
			NavigationItem navigationItem = new NavigationItem();

			navigationItem.setInputValue("radioValue" + i);
			navigationItem.setLabel("Group 2 - Option " + i);

			group2NavigationItemItems.add(navigationItem);
		}

		_inputDropdownItems.add(group2NavigationItem);

		return _inputDropdownItems;
	}

	private List<NavigationItem> _defaultDropdownItems;
	private List<NavigationItem> _groupDropdownItems;
	private List<NavigationItem> _iconDropdownItems;
	private List<NavigationItem> _inputDropdownItems;

}