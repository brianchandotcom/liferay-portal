/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.clay.sample.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;

import java.util.List;

/**
 * @author Chema Balsas
 */
public class DropdownsDisplayContext {

	public List<DropdownItem> getDefaultDropdownItems() {
		if (_defaultDropdownItems != null) {
			return _defaultDropdownItems;
		}

		_defaultDropdownItems = DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.setHref("#1");
				dropdownItem.setLabel("Option 1");
			}
		).add(
			dropdownItem -> {
				dropdownItem.setDisabled(true);
				dropdownItem.setHref("#2");
				dropdownItem.setLabel("Option 2");
			}
		).add(
			dropdownItem -> {
				dropdownItem.setActive(true);
				dropdownItem.setHref("#3");
				dropdownItem.setLabel("Option 3");
			}
		).add(
			dropdownItem -> {
				dropdownItem.setHref("#4");
				dropdownItem.setLabel("Option 4");
			}
		).build();

		return _defaultDropdownItems;
	}

	public List<DropdownItem> getGroupDropdownItems() {
		if (_groupDropdownItems != null) {
			return _groupDropdownItems;
		}

		_groupDropdownItems = DropdownItemListBuilder.addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					DropdownItemListBuilder.add(
						dropdownItem -> {
							dropdownItem.setHref("#1");
							dropdownItem.setLabel("Group 1 - Option 1");
						}
					).add(
						dropdownItem -> {
							dropdownItem.setHref("#2");
							dropdownItem.setLabel("Group 1 - Option 2");
						}
					).build());

				dropdownGroupItem.setLabel("Group 1");
				dropdownGroupItem.setSeparator(true);
			}
		).addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					DropdownItemListBuilder.add(
						dropdownItem -> {
							dropdownItem.setHref("#3");
							dropdownItem.setLabel("Group 2 - Option 1");
						}
					).add(
						dropdownItem -> {
							dropdownItem.setHref("#4");
							dropdownItem.setLabel("Group 2 - Option 2");
						}
					).build());

				dropdownGroupItem.setLabel("Group 2");
			}
		).build();

		return _groupDropdownItems;
	}

	public List<DropdownItem> getIconDropdownItems() {
		if (_iconDropdownItems != null) {
			return _iconDropdownItems;
		}

		_iconDropdownItems = DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.setHref("#1");
				dropdownItem.setIcon("check-circle-full");
				dropdownItem.setLabel("Option 1");
			}
		).add(
			dropdownItem -> {
				dropdownItem.setHref("#2");
				dropdownItem.setIcon("check-circle-full");
				dropdownItem.setLabel("Option 2");
			}
		).add(
			dropdownItem -> {
				dropdownItem.setHref("#3");
				dropdownItem.setIcon("check-circle-full");
				dropdownItem.setLabel("Option 3");
			}
		).add(
			dropdownItem -> {
				dropdownItem.setActive(true);
				dropdownItem.setHref("#4");
				dropdownItem.setLabel("Option 4");
			}
		).add(
			dropdownItem -> {
				dropdownItem.setDisabled(true);
				dropdownItem.setHref("#5");
				dropdownItem.setLabel("Option 5");
			}
		).add(
			dropdownItem -> {
				dropdownItem.setDisabled(true);
				dropdownItem.setHref("#6");
				dropdownItem.setLabel("Option 6");
			}
		).build();

		return _iconDropdownItems;
	}

	public List<DropdownItem> getInputDropdownItems() {
		if (_inputDropdownItems != null) {
			return _inputDropdownItems;
		}

		_inputDropdownItems = DropdownItemListBuilder.addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					DropdownItemListBuilder.addCheckbox(
						dropdownCheckboxItem -> {
							dropdownCheckboxItem.setInputName("checkbox1");
							dropdownCheckboxItem.setInputValue(
								"checkboxvalue1");
							dropdownCheckboxItem.setLabel("Group 1 - Option 1");
						}
					).addCheckbox(
						dropdownCheckboxItem -> {
							dropdownCheckboxItem.setInputName("checkbox2");
							dropdownCheckboxItem.setInputValue(
								"checkboxvalue2");
							dropdownCheckboxItem.setLabel("Group 1 - Option 2");
						}
					).build());

				dropdownGroupItem.setLabel("Group 1");
				dropdownGroupItem.setSeparator(true);
			}
		).addRadioGroup(
			dropdownRadioGroupItem -> {
				dropdownRadioGroupItem.setInputName("radiogroup");

				dropdownRadioGroupItem.setDropdownItems(
					DropdownItemListBuilder.addRadio(
						dropdownRadioItem -> {
							dropdownRadioItem.setHref("#3");
							dropdownRadioItem.setInputValue("radiovalue1");
							dropdownRadioItem.setLabel("Group 2 - Option 1");
						}
					).addRadio(
						dropdownRadioItem -> {
							dropdownRadioItem.setHref("#4");
							dropdownRadioItem.setInputValue("radiovalue2");
							dropdownRadioItem.setLabel("Group 2 - Option 2");
						}
					).build());

				dropdownRadioGroupItem.setLabel("Group 2");
			}
		).build();

		return _inputDropdownItems;
	}

	private List<DropdownItem> _defaultDropdownItems;
	private List<DropdownItem> _groupDropdownItems;
	private List<DropdownItem> _iconDropdownItems;
	private List<DropdownItem> _inputDropdownItems;

}