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

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownCheckboxItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownGroupItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownRadioGroupItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownRadioItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Chema Balsas
 */
public class DropdownsDisplayContext {

	public List<DropdownItem> getDefaultDropdownItems() {
		if (_defaultDropdownItems != null) {
			return _defaultDropdownItems;
		}

		_defaultDropdownItems = new ArrayList<>();

		for (int i = 0; i < 4; i++) {
			DropdownItem dropdownItem = new DropdownItem();

			if (i == 1) {
				dropdownItem.setDisabled(true);
			}
			else if (i == 2) {
				dropdownItem.setActive(true);
			}

			dropdownItem.setHref("#" + i);
			dropdownItem.setLabel("Option " + i);

			_defaultDropdownItems.add(dropdownItem);
		}

		return _defaultDropdownItems;
	}

	public List<DropdownGroupItem> getGroupDropdownItems() {
		if (_groupDropdownItems != null) {
			return _groupDropdownItems;
		}

		_groupDropdownItems = new ArrayList<>();

		DropdownGroupItem group1DropdownGroupItem = new DropdownGroupItem();

		List<Object> group1DropdownGroupItems = new ArrayList<>();

		group1DropdownGroupItem.setItems(group1DropdownGroupItems);

		group1DropdownGroupItem.setLabel("Group 1");
		group1DropdownGroupItem.setSeparator(true);

		for (int i = 0; i < 2; i++) {
			DropdownItem dropdownItem = new DropdownItem();

			dropdownItem.setHref("#" + i);
			dropdownItem.setLabel("Group 1 - Option " + i);

			group1DropdownGroupItems.add(dropdownItem);
		}

		_groupDropdownItems.add(group1DropdownGroupItem);

		DropdownGroupItem group2DropdownGroupItem = new DropdownGroupItem();

		List<Object> group2DropdownGroupItems = new ArrayList<>();

		group2DropdownGroupItem.setItems(group2DropdownGroupItems);

		group2DropdownGroupItem.setLabel("Group 2");
		group2DropdownGroupItem.setSeparator(true);

		for (int i = 0; i < 2; i++) {
			DropdownItem dropdownItem = new DropdownItem();

			dropdownItem.setHref("#" + i);
			dropdownItem.setLabel("Group 2 - Option " + i);

			group2DropdownGroupItems.add(dropdownItem);
		}

		_groupDropdownItems.add(group2DropdownGroupItem);

		System.out.println("Group 1 items size:");
		System.out.println(_groupDropdownItems.get(0).getItems().size());
		System.out.println("End of Group 1 items size:");

		return _groupDropdownItems;
	}

	public List<DropdownItem> getIconDropdownItems() {
		if (_iconDropdownItems != null) {
			return _iconDropdownItems;
		}

		_iconDropdownItems = new ArrayList<>();

		for (int i = 0; i < 6; i++) {
			DropdownItem dropdownItem = new DropdownItem();

			if (i < 3) {
				dropdownItem.setIcon("check-circle-full");
			}
			else if (i == 3) {
				dropdownItem.setActive(true);
			}
			else {
				dropdownItem.setDisabled(true);
			}

			dropdownItem.setHref("#" + i);
			dropdownItem.setLabel("Option " + i);

			_iconDropdownItems.add(dropdownItem);
		}

		return _iconDropdownItems;
	}

	public List<Object> getInputDropdownItems() {
		if (_inputDropdownItems != null) {
			return _inputDropdownItems;
		}

		_inputDropdownItems = new ArrayList<>();

		DropdownGroupItem group1DropdownGroupItem = new DropdownGroupItem();

		List<Object> group1DropdownGroupItems = new ArrayList<>();

		group1DropdownGroupItem.setItems(group1DropdownGroupItems);

		group1DropdownGroupItem.setLabel("Group 1");
		group1DropdownGroupItem.setSeparator(true);

		for (int i = 0; i < 2; i++) {
			DropdownCheckboxItem dropdownCheckboxItem = new DropdownCheckboxItem();

			dropdownCheckboxItem.setInputName("checkbox" + i);
			dropdownCheckboxItem.setInputValue("checkboxValue" + i);
			dropdownCheckboxItem.setLabel("Group 1 - Option " + i);

			group1DropdownGroupItems.add(dropdownCheckboxItem);
		}

		_inputDropdownItems.add(group1DropdownGroupItem);

		DropdownRadioGroupItem group2DropdownRadioGroupItem = new DropdownRadioGroupItem();

		List<DropdownRadioItem> group2DropdownRadioGroupItems = new ArrayList<>();

		group2DropdownRadioGroupItem.setItems(group2DropdownRadioGroupItems);

		group2DropdownRadioGroupItem.setInputName("radiogroup");
		group2DropdownRadioGroupItem.setLabel("Group 2");
		group2DropdownRadioGroupItem.setSeparator(true);

		for (int i = 0; i < 2; i++) {
			DropdownRadioItem dropdownRadioItem = new DropdownRadioItem();

			dropdownRadioItem.setInputValue("radioValue" + i);
			dropdownRadioItem.setLabel("Group 2 - Option " + i);

			group2DropdownRadioGroupItems.add(dropdownRadioItem);
		}

		_inputDropdownItems.add(group2DropdownRadioGroupItem);

		return _inputDropdownItems;
	}

	private List<DropdownItem> _defaultDropdownItems;
	private List<DropdownGroupItem> _groupDropdownItems;
	private List<DropdownItem> _iconDropdownItems;
	private List<Object> _inputDropdownItems;

}