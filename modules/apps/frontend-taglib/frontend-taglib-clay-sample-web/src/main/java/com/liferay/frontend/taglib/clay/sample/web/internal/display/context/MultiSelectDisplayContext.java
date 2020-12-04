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

import com.liferay.frontend.taglib.clay.servlet.taglib.util.MultiSelectItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.MultiSelectItemListBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.MultiSelectLocator;

import java.util.List;

/**
 * @author Kresimir Coko
 */
public class MultiSelectDisplayContext {

	public List<MultiSelectItem> getCustomSelectedItems() {
		if (_customSelectedItems != null) {
			return _customSelectedItems;
		}

		_customSelectedItems = MultiSelectItemListBuilder.add(
			multiSelectItem -> {
				multiSelectItem.put("data", "1");
				multiSelectItem.put("name", "one");
			}
		).add(
			multiSelectItem -> {
				multiSelectItem.put("data", "2");
				multiSelectItem.put("name", "two");
			}
		).build();

		return _customSelectedItems;
	}

	public List<MultiSelectItem> getCustomSourceItems() {
		if (_customSourceItems != null) {
			return _customSourceItems;
		}

		_customSourceItems = MultiSelectItemListBuilder.add(
			multiSelectItem -> {
				multiSelectItem.put("data", "1");
				multiSelectItem.put("name", "one");
			}
		).add(
			multiSelectItem -> {
				multiSelectItem.put("data", "2");
				multiSelectItem.put("name", "two");
			}
		).add(
			multiSelectItem -> {
				multiSelectItem.put("data", "3");
				multiSelectItem.put("name", "three");
			}
		).add(
			multiSelectItem -> {
				multiSelectItem.put("data", "4");
				multiSelectItem.put("name", "four");
			}
		).build();

		return _customSourceItems;
	}

	public MultiSelectLocator getLocator() {
		if (_locator != null) {
			return _locator;
		}

		_locator = new MultiSelectLocator();

		_locator.setLabel("name");
		_locator.setValue("data");

		return _locator;
	}

	public List<MultiSelectItem> getSelectedItems() {
		if (_selectedItems != null) {
			return _selectedItems;
		}

		_selectedItems = MultiSelectItemListBuilder.add(
			multiSelectItem -> {
				multiSelectItem.setLabel("one");
				multiSelectItem.setValue("1");
			}
		).add(
			multiSelectItem -> {
				multiSelectItem.setLabel("two");
				multiSelectItem.setValue("2");
			}
		).build();

		return _selectedItems;
	}

	public List<MultiSelectItem> getSourceItems() {
		if (_sourceItems != null) {
			return _sourceItems;
		}

		_sourceItems = MultiSelectItemListBuilder.add(
			multiSelectItem -> {
				multiSelectItem.setLabel("one");
				multiSelectItem.setValue("1");
			}
		).add(
			multiSelectItem -> {
				multiSelectItem.setLabel("two");
				multiSelectItem.setValue("2");
			}
		).add(
			multiSelectItem -> {
				multiSelectItem.setLabel("three");
				multiSelectItem.setValue("3");
			}
		).add(
			multiSelectItem -> {
				multiSelectItem.setLabel("four");
				multiSelectItem.setValue("4");
			}
		).build();

		return _sourceItems;
	}

	public void setCustomSelectedItems(
		List<MultiSelectItem> customSelectedItems) {

		_customSelectedItems = customSelectedItems;
	}

	public void setCustomSourceItems(List<MultiSelectItem> customSourceItems) {
		_customSourceItems = customSourceItems;
	}

	public void setLocator(MultiSelectLocator locator) {
		_locator = locator;
	}

	public void setSelectedItems(List<MultiSelectItem> selectedItems) {
		_selectedItems = selectedItems;
	}

	public void setSourceItems(List<MultiSelectItem> sourceItems) {
		_sourceItems = sourceItems;
	}

	private List<MultiSelectItem> _customSelectedItems;
	private List<MultiSelectItem> _customSourceItems;
	private MultiSelectLocator _locator;
	private List<MultiSelectItem> _selectedItems;
	private List<MultiSelectItem> _sourceItems;

}