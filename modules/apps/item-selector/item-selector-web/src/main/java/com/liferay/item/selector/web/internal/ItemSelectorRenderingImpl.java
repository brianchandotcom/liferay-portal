/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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