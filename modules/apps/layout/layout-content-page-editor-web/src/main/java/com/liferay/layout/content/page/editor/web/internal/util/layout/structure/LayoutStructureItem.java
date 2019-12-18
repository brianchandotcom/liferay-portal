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

package com.liferay.layout.content.page.editor.web.internal.util.layout.structure;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Víctor Galán
 */
public class LayoutStructureItem {

	public LayoutStructureItem(
		JSONObject itemConfigJSONObject, String itemId, String parentItemId,
		String itemType) {

		_itemConfigJSONObject = itemConfigJSONObject;
		_itemId = itemId;
		_parentItemId = parentItemId;
		_itemType = itemType;

		_childrenItemIds = new ArrayList<>();
	}

	public LayoutStructureItem(
		List<String> childrenItemIds, JSONObject itemConfigJSONObject,
		String itemId, String parentItemId, String itemType) {

		_childrenItemIds = childrenItemIds;
		_itemConfigJSONObject = itemConfigJSONObject;
		_itemId = itemId;
		_parentItemId = parentItemId;
		_itemType = itemType;
	}

	public void addChildrenItem(int position, String itemId) {
		List<String> childrenItemIds = getChildrenItemIds();

		childrenItemIds.add(position, itemId);
	}

	public void deleteChildrenItem(String itemId) {
		_childrenItemIds.remove(itemId);
	}

	public List<String> getChildrenItemIds() {
		return _childrenItemIds;
	}

	public JSONObject getItemConfigJSONObject() {
		return _itemConfigJSONObject;
	}

	public String getItemId() {
		return _itemId;
	}

	public String getItemType() {
		return _itemType;
	}

	public String getParentItemId() {
		return _parentItemId;
	}

	public JSONObject toJSONObject() {
		return JSONUtil.put(
			"children", JSONFactoryUtil.createJSONArray(getChildrenItemIds())
		).put(
			"config", getItemConfigJSONObject()
		).put(
			"itemId", getItemId()
		).put(
			"parentId", getParentItemId()
		).put(
			"type", getItemType()
		);
	}

	private final List<String> _childrenItemIds;
	private final JSONObject _itemConfigJSONObject;
	private final String _itemId;
	private final String _itemType;
	private final String _parentItemId;

}