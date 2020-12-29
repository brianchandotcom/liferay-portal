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

import com.liferay.frontend.taglib.clay.servlet.taglib.util.MultiselectItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.MultiselectItemListBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.MultiselectLocator;

import java.util.List;

/**
 * @author Kresimir Coko
 */
public class MultiselectDisplayContext {

	public List<MultiselectItem> getCustomSelectedItems() {
		if (_customSelectedItems != null) {
			return _customSelectedItems;
		}

		_customSelectedItems = MultiselectItemListBuilder.add(
			multiselectItem -> {
				multiselectItem.put("data", "1");
				multiselectItem.put("name", "one");
			}
		).add(
			multiselectItem -> {
				multiselectItem.put("data", "2");
				multiselectItem.put("name", "two");
			}
		).build();

		return _customSelectedItems;
	}

	public List<MultiselectItem> getCustomSourceItems() {
		if (_customSourceItems != null) {
			return _customSourceItems;
		}

		_customSourceItems = MultiselectItemListBuilder.add(
			multiselectItem -> {
				multiselectItem.put("data", "1");
				multiselectItem.put("name", "one");
			}
		).add(
			multiselectItem -> {
				multiselectItem.put("data", "2");
				multiselectItem.put("name", "two");
			}
		).add(
			multiselectItem -> {
				multiselectItem.put("data", "3");
				multiselectItem.put("name", "three");
			}
		).add(
			multiselectItem -> {
				multiselectItem.put("data", "4");
				multiselectItem.put("name", "four");
			}
		).build();

		return _customSourceItems;
	}

	public MultiselectLocator getLocator() {
		if (_locator != null) {
			return _locator;
		}

		_locator = new MultiselectLocator();

		_locator.setLabel("name");
		_locator.setValue("data");

		return _locator;
	}

	public List<MultiselectItem> getSelectedItems() {
		if (_selectedItems != null) {
			return _selectedItems;
		}

		_selectedItems = MultiselectItemListBuilder.add(
			multiselectItem -> {
				multiselectItem.setLabel("one");
				multiselectItem.setValue("1");
			}
		).add(
			multiselectItem -> {
				multiselectItem.setLabel("two");
				multiselectItem.setValue("2");
			}
		).build();

		return _selectedItems;
	}

	public List<MultiselectItem> getSourceItems() {
		if (_sourceItems != null) {
			return _sourceItems;
		}

		_sourceItems = MultiselectItemListBuilder.add(
			multiselectItem -> {
				multiselectItem.setLabel("one");
				multiselectItem.setValue("1");
			}
		).add(
			multiselectItem -> {
				multiselectItem.setLabel("two");
				multiselectItem.setValue("2");
			}
		).add(
			multiselectItem -> {
				multiselectItem.setLabel("three");
				multiselectItem.setValue("3");
			}
		).add(
			multiselectItem -> {
				multiselectItem.setLabel("four");
				multiselectItem.setValue("4");
			}
		).build();

		return _sourceItems;
	}

	public void setCustomSelectedItems(
		List<MultiselectItem> customSelectedItems) {

		_customSelectedItems = customSelectedItems;
	}

	public void setCustomSourceItems(List<MultiselectItem> customSourceItems) {
		_customSourceItems = customSourceItems;
	}

	public void setLocator(MultiselectLocator locator) {
		_locator = locator;
	}

	public void setSelectedItems(List<MultiselectItem> selectedItems) {
		_selectedItems = selectedItems;
	}

	public void setSourceItems(List<MultiselectItem> sourceItems) {
		_sourceItems = sourceItems;
	}

	private List<MultiselectItem> _customSelectedItems;
	private List<MultiselectItem> _customSourceItems;
	private MultiselectLocator _locator;
	private List<MultiselectItem> _selectedItems;
	private List<MultiselectItem> _sourceItems;

}