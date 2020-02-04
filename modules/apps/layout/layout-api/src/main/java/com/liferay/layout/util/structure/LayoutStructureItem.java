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

package com.liferay.layout.util.structure;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Víctor Galán
 */
public abstract class LayoutStructureItem {

	public static LayoutStructureItem of(JSONObject jsonObject) {
		String parentId = jsonObject.getString("parentId");
		String type = jsonObject.getString("type");

		LayoutStructureItem layoutStructureItem =
			LayoutStructureItemUtil.create(type, parentId);

		if (layoutStructureItem == null) {
			return null;
		}

		List<String> childrenItemIds = new ArrayList<>();

		JSONUtil.addToStringCollection(
			childrenItemIds, jsonObject.getJSONArray("children"));

		layoutStructureItem.setChildrenItemIds(childrenItemIds);

		layoutStructureItem.setItemId(jsonObject.getString("itemId"));
		layoutStructureItem.setParentItemId(parentId);

		layoutStructureItem.updateItemConfig(
			jsonObject.getJSONObject("config"));

		return layoutStructureItem;
	}

	public LayoutStructureItem() {
		_childrenItemIds = new ArrayList<>();
	}

	public LayoutStructureItem(String parentItemId) {
		_parentItemId = parentItemId;

		_itemId = String.valueOf(UUID.randomUUID());
		_childrenItemIds = new ArrayList<>();
	}

	public void addChildrenItem(int position, String itemId) {
		_childrenItemIds.add(position, itemId);
	}

	public void addChildrenItem(String itemId) {
		_childrenItemIds.add(itemId);
	}

	public void deleteChildrenItem(String itemId) {
		_childrenItemIds.remove(itemId);
	}

	public List<String> getChildrenItemIds() {
		return _childrenItemIds;
	}

	public String getItemId() {
		return _itemId;
	}

	public abstract String getItemType();

	public String getParentItemId() {
		return _parentItemId;
	}

	public void setChildrenItemIds(List<String> childrenItemIds) {
		_childrenItemIds = childrenItemIds;
	}

	public void setItemId(String itemId) {
		_itemId = itemId;
	}

	public void setParentItemId(String parentItemId) {
		_parentItemId = parentItemId;
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

	public abstract void updateItemConfig(JSONObject itemConfigJSONObject);

	protected abstract JSONObject getItemConfigJSONObject();

	private List<String> _childrenItemIds;
	private String _itemId;
	private String _parentItemId;

}