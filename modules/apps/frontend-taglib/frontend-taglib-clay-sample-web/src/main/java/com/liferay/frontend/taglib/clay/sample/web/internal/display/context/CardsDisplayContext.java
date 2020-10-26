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
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItemListBuilder;
import com.liferay.portal.kernel.security.RandomUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.List;
import java.util.Map;

/**
 * @author Julien Castelain
 */
public class CardsDisplayContext {

	public List<DropdownItem> getActionDropdownItems() {
		if (_actionDropdownItems != null) {
			return _actionDropdownItems;
		}

		_actionDropdownItems = DropdownItemListBuilder.addGroup(
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
					).add(
						dropdownItem -> dropdownItem.setType("divider")
					).build());

				dropdownGroupItem.setLabel("Group 1");
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

		return _actionDropdownItems;
	}

	public List<LabelItem> getLabelItems() {
		int numItems = 1 + RandomUtil.nextInt(3);

		return LabelItemListBuilder.add(
			labelItem -> {
				labelItem.setLabel("Approved");
				labelItem.setStyle("success");
			}
		).add(
			() -> numItems > 1, labelItem -> labelItem.setLabel("Pending")
		).add(
			() -> numItems > 2,
			labelItem -> {
				labelItem.setLabel("Canceled");
				labelItem.setStyle("danger");
			}
		).build();
	}

	public Map<String, String> getLabelStylesMap() {
		if (_labelStylesMap != null) {
			return _labelStylesMap;
		}

		_labelStylesMap = HashMapBuilder.put(
			"Pending", "warning"
		).build();

		return _labelStylesMap;
	}

	private List<DropdownItem> _actionDropdownItems;
	private Map<String, String> _labelStylesMap;

}