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

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;

import java.util.List;

/**
 * @author Chema Balsas
 */
public class DropdownsDisplayContext {

	public List<DropdownItem> getDefaultDropdownItems() {
		if (_defaultDropdownItems != null) {
			return _defaultDropdownItems;
		}

		_defaultDropdownItems = new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.setHref("#1");
						dropdownItem.setLabel("Option 1");
					});

				add(
					dropdownItem -> {
						dropdownItem.setDisabled(true);
						dropdownItem.setHref("#2");
						dropdownItem.setLabel("Option 2");
					});

				add(
					dropdownItem -> {
						dropdownItem.setActive(true);
						dropdownItem.setHref("#3");
						dropdownItem.setLabel("Option 3");
					});

				add(
					dropdownItem -> {
						dropdownItem.setHref("#4");
						dropdownItem.setLabel("Option 4");
					});
			}
		};

		return _defaultDropdownItems;
	}

	public List<DropdownItem> getGroupDropdownItems() {
		if (_groupDropdownItems != null) {
			return _groupDropdownItems;
		}

		List<DropdownItem> group1DropdownItems = new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.setHref("#1");
						dropdownItem.setLabel("Group 1 - Option 1");
					});

				add(
					dropdownItem -> {
						dropdownItem.setHref("#2");
						dropdownItem.setLabel("Group 1 - Option 2");
					});
			}
		};

		List<DropdownItem> group2DropdownItems = new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.setHref("#3");
						dropdownItem.setLabel("Group 2 - Option 1");
					});

				add(
					dropdownItem -> {
						dropdownItem.setHref("#4");
						dropdownItem.setLabel("Group 2 - Option 2");
					});
			}
		};

		_groupDropdownItems = new DropdownItemList() {
			{
				addGroup(
					dropdownGroupItem -> {
						dropdownGroupItem.setDropdownItems(group1DropdownItems);
						dropdownGroupItem.setLabel("Group 1");
						dropdownGroupItem.setSeparator(true);
					});

				addGroup(
					dropdownGroupItem -> {
						dropdownGroupItem.setDropdownItems(group2DropdownItems);
						dropdownGroupItem.setLabel("Group 2");
					});
			}
		};

		return _groupDropdownItems;
	}

	public List<DropdownItem> getIconDropdownItems() {
		if (_iconDropdownItems != null) {
			return _iconDropdownItems;
		}

		_iconDropdownItems = new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.setHref("#1");
						dropdownItem.setIcon("check-circle-full");
						dropdownItem.setLabel("Option 1");
					});

				add(
					dropdownItem -> {
						dropdownItem.setHref("#2");
						dropdownItem.setIcon("check-circle-full");
						dropdownItem.setLabel("Option 2");
					});

				add(
					dropdownItem -> {
						dropdownItem.setHref("#3");
						dropdownItem.setIcon("check-circle-full");
						dropdownItem.setLabel("Option 3");
					});

				add(
					dropdownItem -> {
						dropdownItem.setActive(true);
						dropdownItem.setHref("#4");
						dropdownItem.setLabel("Option 4");
					});

				add(
					dropdownItem -> {
						dropdownItem.setDisabled(true);
						dropdownItem.setHref("#5");
						dropdownItem.setLabel("Option 5");
					});

				add(
					dropdownItem -> {
						dropdownItem.setDisabled(true);
						dropdownItem.setHref("#6");
						dropdownItem.setLabel("Option 6");
					});
			}
		};

		return _iconDropdownItems;
	}

	public List<DropdownItem> getInputDropdownItems() {
		if (_inputDropdownItems != null) {
			return _inputDropdownItems;
		}

		List<DropdownItem> group1DropdownItems = new DropdownItemList() {
			{
				addCheckbox(
					dropdownCheckboxItem -> {
						dropdownCheckboxItem.setInputName("checkbox1");
						dropdownCheckboxItem.setInputValue("checkboxvalue1");
						dropdownCheckboxItem.setLabel("Group 1 - Option 1");
					});

				addCheckbox(
					dropdownCheckboxItem -> {
						dropdownCheckboxItem.setInputName("checkbox2");
						dropdownCheckboxItem.setInputValue("checkboxvalue2");
						dropdownCheckboxItem.setLabel("Group 1 - Option 2");
					});
			}
		};

		List<DropdownItem> group2DropdownItems = new DropdownItemList() {
			{
				addRadio(
					dropdownRadioItem -> {
						dropdownRadioItem.setHref("#3");
						dropdownRadioItem.setInputValue("radiovalue1");
						dropdownRadioItem.setLabel("Group 2 - Option 1");
					});

				addRadio(
					dropdownRadioItem -> {
						dropdownRadioItem.setHref("#4");
						dropdownRadioItem.setInputValue("radiovalue2");
						dropdownRadioItem.setLabel("Group 2 - Option 2");
					});
			}
		};

		_inputDropdownItems = new DropdownItemList() {
			{
				addGroup(
					dropdownGroupItem -> {
						dropdownGroupItem.setDropdownItems(group1DropdownItems);
						dropdownGroupItem.setLabel("Group 1");
						dropdownGroupItem.setSeparator(true);
					});

				addRadioGroup(
					dropdownRadioGroupItem -> {
						dropdownRadioGroupItem.setInputName("radiogroup");
						dropdownRadioGroupItem.setDropdownItems(
							group2DropdownItems);
						dropdownRadioGroupItem.setLabel("Group 2");
					});
			}
		};

		return _inputDropdownItems;
	}

	private List<DropdownItem> _defaultDropdownItems;
	private List<DropdownItem> _groupDropdownItems;
	private List<DropdownItem> _iconDropdownItems;
	private List<DropdownItem> _inputDropdownItems;

}