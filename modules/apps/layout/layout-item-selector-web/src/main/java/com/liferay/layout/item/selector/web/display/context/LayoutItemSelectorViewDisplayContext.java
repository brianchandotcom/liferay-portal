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

package com.liferay.layout.item.selector.web.display.context;

import com.liferay.layout.item.selector.criterion.LayoutItemSelectorCriterion;

/**
 * @author Roberto Díaz
 */
public class LayoutItemSelectorViewDisplayContext {

	public LayoutItemSelectorViewDisplayContext(
		LayoutItemSelectorCriterion layoutItemSelectorCriterion,
		String itemSelectedEventName) {

		_layoutItemSelectorCriterion = layoutItemSelectorCriterion;
		_itemSelectedEventName = itemSelectedEventName;
	}

	public String getItemSelectedEventName() {
		return _itemSelectedEventName;
	}

	public LayoutItemSelectorCriterion getLayoutItemSelectorCriterion() {
		return _layoutItemSelectorCriterion;
	}

	private final String _itemSelectedEventName;
	private final LayoutItemSelectorCriterion _layoutItemSelectorCriterion;

}