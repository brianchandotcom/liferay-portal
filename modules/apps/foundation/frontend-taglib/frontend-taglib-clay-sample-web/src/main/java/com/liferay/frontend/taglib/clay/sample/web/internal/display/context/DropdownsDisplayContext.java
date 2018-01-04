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
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItems;

import java.util.List;

/**
 * @author Chema Balsas
 */
public class DropdownsDisplayContext {

	public DropdownsDisplayContext() {
	}

	public List<NavigationItem> getDefaultDropdownItems() {
		if (_defaultDropdownItems != null) {
			return _defaultDropdownItems.get();
		}

		NavigationItems.Builder builder = new NavigationItems.Builder();

		_defaultDropdownItems = builder.item(
			itemBuilder -> itemBuilder.active(
				false
			).href(
				"#1"
			).label(
				"Option 1"
			).build()
		).item(
			itemBuilder -> itemBuilder.active(
				false
			).disabled(
				true
			).href(
				"#2"
			).label(
				"Option 2"
			).build()
		).item(
			itemBuilder -> itemBuilder.active(
				true
			).href(
				"#3"
			).label(
				"Option 3"
			).build()
		).item(
			itemBuilder -> itemBuilder.active(
				false
			).href(
				"#4"
			).label(
				"Option 4"
			).build()
		).build();

		return _defaultDropdownItems.get();
	}

	public List<NavigationItem> getGroupDropdownItems() {
		if (_groupDropdownItems != null) {
			return _groupDropdownItems.get();
		}

		NavigationItems.Builder builder = new NavigationItems.Builder();

		_groupDropdownItems = builder.item(
			itemBuilder -> itemBuilder.item(
				itemBuilder2 -> itemBuilder2.href(
					"#1"
				).label(
					"Option 1"
				).build()
			).item(
				itemBuilder2 -> itemBuilder2.href(
					"#2"
				).label(
					"Option 2"
				).build()
			).label(
				"Group 1"
			).separator(
				true
			).type(
				"group"
			).build()
		).item(
			itemBuilder -> itemBuilder.item(
				itemBuilder2 -> itemBuilder2.href(
					"#3"
				).label(
					"Option 3"
				).build()
			).item(
				itemBuilder2 -> itemBuilder2.href(
					"#4"
				).label(
					"Option 4"
				).build()
			).label(
				"Group 2"
			).type(
				"group"
			).build()
		).build();

		return _groupDropdownItems.get();
	}

	public List<NavigationItem> getIconDropdownItems() {
		if (_iconDropdownItems != null) {
			return _iconDropdownItems.get();
		}

		NavigationItems.Builder builder = new NavigationItems.Builder();

		_iconDropdownItems = builder.item(
			itemBuilder -> itemBuilder.href(
				"#1"
			).icon(
				"check-circle-full"
			).label(
				"Option 1"
			).build()
		).item(
			itemBuilder -> itemBuilder.href(
				"#2"
			).icon(
				"check-circle-full"
			).label(
				"Option 2"
			).build()
		).item(
			itemBuilder -> itemBuilder.active(
				true
			).href(
				"#3"
			).label(
				"Option 3"
			).build()
		).item(
			itemBuilder -> itemBuilder.disabled(
				true
			).href(
				"#4"
			).label(
				"Option 4"
			).build()
		).build();

		return _iconDropdownItems.get();
	}

	public List<NavigationItem> getInputDropdownItems() {
		if (_inputDropdownItems != null) {
			return _inputDropdownItems.get();
		}

		NavigationItems.Builder builder = new NavigationItems.Builder();

		_inputDropdownItems = builder.item(
			itemBuilder -> itemBuilder.item(
				itemBuilder2 -> itemBuilder2.href(
					"#1"
				).inputName(
					"checkbox1"
				).inputValue(
					"checkboxValue1"
				).label(
					"Option 1"
				).type(
					"checkbox"
				).build()
			).item(
				itemBuilder2 -> itemBuilder2.href(
					"#2"
				).inputName(
					"checkbox2"
				).inputValue(
					"checkboxValue2"
				).label(
					"Option 2"
				).type(
					"checkbox"
				).build()
			).label(
				"Group 1"
			).separator(
				true
			).type(
				"group"
			).build()
		).item(
			itemBuilder -> itemBuilder.inputName(
				"radiogroup"
			).item(
				itemBuilder2 -> itemBuilder2.href(
					"#3"
				).inputValue(
					"radio1"
				).label(
					"Option 3"
				).build()
			).item(
				itemBuilder2 -> itemBuilder2.href(
					"#4"
				).inputValue(
					"radio2"
				).label(
					"Option 4"
				).build()
			).label(
				"Group 2"
			).type(
				"radiogroup"
			).build()
		).build();

		return _inputDropdownItems.get();
	}

	private NavigationItems _defaultDropdownItems;
	private NavigationItems _groupDropdownItems;
	private NavigationItems _iconDropdownItems;
	private NavigationItems _inputDropdownItems;

}