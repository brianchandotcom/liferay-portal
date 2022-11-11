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

/**
 * @author Eudaldo Alonso
 */
public class InfoCollectionProviderItemSelectorCriterion
	extends BaseItemSelectorCriterion {

	public String getItemSubtype() {
		return _itemSubtype;
	}

	public String getItemType() {
		return _itemType;
	}

	public Type getType() {
		return _type;
	}

	public void setItemSubtype(String itemSubtype) {
		_itemSubtype = itemSubtype;
	}

	public void setItemType(String itemType) {
		_itemType = itemType;

		_type = Type.SINGLE_COLLECTION;
	}

	public void setType(Type type) {
		_type = type;
	}

	public enum Type {

		ALL_COLLECTIONS, SINGLE_COLLECTION, SUPPORTED_INFO_FRAMEWORK_COLLECTIONS

	}

	private String _itemSubtype;
	private String _itemType;
	private Type _type = Type.ALL_COLLECTIONS;

}