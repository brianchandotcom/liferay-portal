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

package com.liferay.item.selector.url.web.internal.display.context;

import com.liferay.item.selector.url.web.internal.ItemSelectorURLView;

import java.util.Locale;

/**
 * @author Roberto Díaz
 */
public class ItemSelectorURLViewDisplayContext {

	public ItemSelectorURLViewDisplayContext(
		ItemSelectorURLView itemSelectorURLView, String itemSelectedEventName) {

		_itemSelectorURLView = itemSelectorURLView;
		_itemSelectedEventName = itemSelectedEventName;
	}

	public String getItemSelectedEventName() {
		return _itemSelectedEventName;
	}

	public String getTitle(Locale locale) {
		return _itemSelectorURLView.getTitle(locale);
	}

	private final String _itemSelectedEventName;
	private final ItemSelectorURLView _itemSelectorURLView;

}