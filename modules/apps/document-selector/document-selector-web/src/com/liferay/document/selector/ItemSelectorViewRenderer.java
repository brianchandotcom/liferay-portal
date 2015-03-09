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

package com.liferay.document.selector;

/**
 * @author Iván Zaera
 */
public class ItemSelectorViewRenderer<T extends ItemSelectorCriterion> {

	public ItemSelectorViewRenderer(
		ItemSelectorView<T> itemSelectorView, T itemSelectorCriterion) {

		_itemSelectorView = itemSelectorView;
		_itemSelectorCriterion = itemSelectorCriterion;
	}

	public String getHTML() {
		return _itemSelectorView.getHTML(_itemSelectorCriterion);
	}

	public T getItemSelectorCriterion() {
		return _itemSelectorCriterion;
	}

	public ItemSelectorView<T> getItemSelectorView() {
		return _itemSelectorView;
	}

	private final T _itemSelectorCriterion;
	private final ItemSelectorView<T> _itemSelectorView;

}