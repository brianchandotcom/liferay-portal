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
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItemList;
import com.liferay.portal.kernel.security.RandomUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Julien Castelain
 */
public class CardsDisplayContext {

	public DropdownItemList getActionDropdownItems() {
		if (_actionDropdownItems != null) {
			return _actionDropdownItems;
		}

		_actionDropdownItems = new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.setHref("#1");
						dropdownItem.setLabel("Edit");
						dropdownItem.setSeparator(true);
					});

				add(
					dropdownItem -> {
						dropdownItem.setHref("#2");
						dropdownItem.setLabel("Save");
					});
			}
		};

		return _actionDropdownItems;
	}

	public LabelItemList getLabels() {
		return new LabelItemList() {
			{
				LabelItem label1 = new LabelItem();

				label1.setLabel("Approved");
				label1.setStyle("success");

				LabelItem label2 = new LabelItem();

				label2.setLabel("Pending");

				LabelItem label3 = new LabelItem();

				label3.setLabel("Canceled");
				label3.setStyle("danger");

				int numItems = 1 + RandomUtil.nextInt(3);

				if ((numItems == 0) || (numItems < 2)) {
					add(label1);
				}
				else if (numItems == 2) {
					add(label1);
					add(label2);
				}
				else if (numItems >= 3) {
					add(label1);
					add(label2);
					add(label3);
				}
			}
		};
	}

	public Map<String, Object> getLabelStylesMap() {
		if (_labelStylesMap != null) {
			return _labelStylesMap;
		}

		_labelStylesMap = new HashMap<>();

		_labelStylesMap.put("Pending", "warning");

		return _labelStylesMap;
	}

	private DropdownItemList _actionDropdownItems;
	private Map<String, Object> _labelStylesMap;

}