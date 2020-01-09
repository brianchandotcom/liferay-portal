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

package com.liferay.sharing.web.internal.item.selector;

import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.ItemSelectorReturnType;

import java.util.Arrays;
import java.util.List;

/**
 * @author Alejandro Tardín
 */
public class SharedAssetsFilterItemItemSelectorCriterion
	implements ItemSelectorCriterion {

	@Override
	public List<ItemSelectorReturnType> getDesiredItemSelectorReturnTypes() {
		return _itemSelectorReturnTypes;
	}

	@Override
	public void setDesiredItemSelectorReturnTypes(
		ItemSelectorReturnType... desiredItemSelectorReturnTypes) {

		_itemSelectorReturnTypes = Arrays.asList(
			desiredItemSelectorReturnTypes);
	}

	@Override
	public void setDesiredItemSelectorReturnTypes(
		List<ItemSelectorReturnType> desiredItemSelectorReturnTypes) {

		_itemSelectorReturnTypes = desiredItemSelectorReturnTypes;
	}

	private List<ItemSelectorReturnType> _itemSelectorReturnTypes =
		Arrays.asList(new SharedAssetsFilterItemItemSelectorReturnType());

}