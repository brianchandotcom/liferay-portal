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

package com.liferay.layout.list.retriever;

import com.liferay.portal.kernel.json.JSONObject;

/**
 * @author Eudaldo Alonso
 */
public class KeyListObjectReference implements ListObjectReference {

	public KeyListObjectReference(JSONObject jsonObject) {
		_key = jsonObject.getString("key");
		_title = jsonObject.getString("title");
		_itemType = jsonObject.getString("itemType");
	}

	@Override
	public String getItemType() {
		return _itemType;
	}

	public String getKey() {
		return _key;
	}

	public String getTitle() {
		return _title;
	}

	private final String _itemType;
	private final String _key;
	private final String _title;

}