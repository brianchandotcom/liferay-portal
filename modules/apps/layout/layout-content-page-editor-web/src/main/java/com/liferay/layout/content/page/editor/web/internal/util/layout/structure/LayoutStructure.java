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
import java.util.Map;

/**
 * @author Víctor Galán
 */
public class LayoutStructure {

	public LayoutStructure(Map<String, Item> items, RootItem rootItem) {
		_items = items;
		_rootItem = rootItem;
	}

	public LayoutStructure addItem(
		Item item, String parentItemId, int position) {

		_items.put(item.getItemId(), item);

		if (parentItemId != null) {
			Item parentItem = _items.get(parentItemId);

			List<String> childrenItemIds = parentItem.getChildrenItemIds();

			childrenItemIds.add(position, item.getItemId());
		}

		return this;
	}

	public JSONObject toJSONObject() {
		JSONObject itemsJSONObject = JSONFactoryUtil.createJSONObject();

		for (Map.Entry<String, Item> entry : _items.entrySet()) {
			Item item = entry.getValue();

			itemsJSONObject.put(
				entry.getKey(),
				JSONUtil.put(
					"children", item.getChildrenItemIds()
				).put(
					"config", item.getItemConfigJSONObject()
				).put(
					"itemId", item.getItemId()
				).put(
					"parentId", item.getParentItemId()
				).put(
					"type", item.getItemType()
				));
		}

		return JSONUtil.put(
			"items", itemsJSONObject
		).put(
			"rootItems", JSONUtil.put("main", _rootItem.getMainItemId())
		).put(
			"version", 1
		);
	}

	public static class Item {

		public static Item create(
			JSONObject itemConfigJSONObject, String itemId, String parentItemId,
			String itemType) {

			return new Item(
				new ArrayList<>(), itemConfigJSONObject, itemId, parentItemId,
				itemType);
		}

		public Item(
			List<String> childrenItemIds, JSONObject itemConfigJSONObject,
			String itemId, String parentItemId, String itemType) {

			_childrenItemIds = childrenItemIds;
			_itemConfigJSONObject = itemConfigJSONObject;
			_itemId = itemId;
			_parentItemId = parentItemId;
			_itemType = itemType;
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

		private final List<String> _childrenItemIds;
		private final JSONObject _itemConfigJSONObject;
		private final String _itemId;
		private final String _itemType;
		private final String _parentItemId;

	}

	public static class RootItem {

		public RootItem(String mainItemId) {
			_mainItemId = mainItemId;
		}

		public String getMainItemId() {
			return _mainItemId;
		}

		private final String _mainItemId;

	}

	private final Map<String, Item> _items;
	private final RootItem _rootItem;

}