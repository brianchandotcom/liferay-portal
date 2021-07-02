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

package com.liferay.info.collection.provider.item.selector.criterion;

import com.liferay.item.selector.BaseItemSelectorCriterion;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Eudaldo Alonso
 */
public class InfoCollectionProviderItemSelectorCriterion
	extends BaseItemSelectorCriterion {

	public String getItemType() {
		if (ListUtil.isEmpty(_itemTypes)) {
			return null;
		}

		return _itemTypes.get(0);
	}

	public List<String> getItemTypes() {
		return _itemTypes;
	}

	public void setItemType(String itemType) {
		_itemTypes.add(itemType);
	}

	public void setItemTypes(List<String> itemTypes) {
		_itemTypes = itemTypes;
	}

	private List<String> _itemTypes = new ArrayList<>();

}