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
import com.liferay.portal.kernel.security.RandomUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

	public List<Object> getLabels() {
		Map<String, Object> label1 = new HashMap<>();

		label1.put("label", "Approved");
		label1.put("style", "success");

		String label2 = "Pending";

		Map<String, Object> label3 = new HashMap<>();

		label3.put("label", "Canceled");
		label3.put("style", "danger");

		List<Object> labels = new ArrayList<>();

		int numItems = 1 + RandomUtil.nextInt(3);

		if ((numItems == 0) || (numItems < 2)) {
			labels.add(label1);
		}
		else if (numItems == 2) {
			labels.add(label1);
			labels.add(label2);
		}
		else if (numItems >= 3) {
			labels.add(label1);
			labels.add(label2);
			labels.add(label3);
		}

		return labels;
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