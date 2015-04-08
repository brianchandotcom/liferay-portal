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

package com.liferay.document.selector.impl;

import com.liferay.document.selector.ItemSelectorRendering;
import com.liferay.document.selector.ItemSelectorViewRenderer;

import java.util.List;

/**
 * @author Iván Zaera
 */
public class ItemSelectorRenderingImpl implements ItemSelectorRendering {

	public ItemSelectorRenderingImpl(
		String itemSelectedCallback,
		List<ItemSelectorViewRenderer> itemSelectorViewRenderers) {

		_itemSelectedCallback = itemSelectedCallback;
		_itemSelectorViewRenderers = itemSelectorViewRenderers;
	}

	public String getItemSelectedCallback() {
		return _itemSelectedCallback;
	}

	public List<ItemSelectorViewRenderer> getItemSelectorViewRenderers() {
		return _itemSelectorViewRenderers;
	}

	private final String _itemSelectedCallback;
	private final List<ItemSelectorViewRenderer> _itemSelectorViewRenderers;

}