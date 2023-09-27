/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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