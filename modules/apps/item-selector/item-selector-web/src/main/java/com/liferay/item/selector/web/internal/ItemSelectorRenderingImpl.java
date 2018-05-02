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

package com.liferay.item.selector.web.internal;

import com.liferay.item.selector.ItemSelectorRendering;
import com.liferay.item.selector.ItemSelectorViewRenderer;

import java.util.List;

/**
 * @author Iván Zaera
 */
public class ItemSelectorRenderingImpl implements ItemSelectorRendering {

	public ItemSelectorRenderingImpl(
		String itemSelectedEventName, String selectedTab,
		List<ItemSelectorViewRenderer> itemSelectorViewRenderers) {

		_itemSelectedEventName = itemSelectedEventName;
		_selectedTab = selectedTab;
		_itemSelectorViewRenderers = itemSelectorViewRenderers;
	}

	@Override
	public String getItemSelectedEventName() {
		return _itemSelectedEventName;
	}

	@Override
	public List<ItemSelectorViewRenderer> getItemSelectorViewRenderers() {
		return _itemSelectorViewRenderers;
	}

	@Override
	public String getSelectedTab() {
		return _selectedTab;
	}

	private final String _itemSelectedEventName;
	private final List<ItemSelectorViewRenderer> _itemSelectorViewRenderers;
	private final String _selectedTab;

}