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

	public LayoutStructure(
		Map<String, Item> layoutStructureItems, Root layoutStructureRoot) {

		_layoutStructureItems = layoutStructureItems;
		_layoutStructureRoot = layoutStructureRoot;
	}

	public LayoutStructure addItem(
		Item layoutStructureItem, String parentId, int position) {

		_layoutStructureItems.put(
			layoutStructureItem.getItemId(), layoutStructureItem);

		if (parentId != null) {
			Item parentLayoutStructureItem = _layoutStructureItems.get(
				parentId);

			List<String> childrenItemIds =
				parentLayoutStructureItem.getChildrenItemIds();

			childrenItemIds.add(position, layoutStructureItem.getItemId());
		}

		return this;
	}

	public JSONObject toJSONObject() {
		JSONObject itemsJSONObject = JSONFactoryUtil.createJSONObject();

		for (Map.Entry<String, Item> entry : _layoutStructureItems.entrySet()) {
			Item layoutStructureItem = entry.getValue();

			itemsJSONObject.put(
				entry.getKey(),
				JSONUtil.put(
					"children", layoutStructureItem.getChildrenItemIds()
				).put(
					"config", layoutStructureItem.getConfig()
				).put(
					"itemId", layoutStructureItem.getItemId()
				).put(
					"parentId", layoutStructureItem.getParentId()
				).put(
					"type", layoutStructureItem.getType()
				));
		}

		return JSONUtil.put(
			"items", itemsJSONObject
		).put(
			"rootItems", JSONUtil.put("main", _layoutStructureRoot.getMain())
		).put(
			"version", 1
		);
	}

	public static class Item {

		public static Item create(
			JSONObject config, String itemId, String parentId, String type) {

			return new Item(new ArrayList<>(), config, itemId, parentId, type);
		}

		public Item(
			List<String> childrenItemIds, JSONObject config, String itemId,
			String parentId, String type) {

			_childrenItemIds = childrenItemIds;
			_config = config;
			_itemId = itemId;
			_parentId = parentId;
			_type = type;
		}

		public List<String> getChildrenItemIds() {
			return _childrenItemIds;
		}

		public JSONObject getConfig() {
			return _config;
		}

		public String getItemId() {
			return _itemId;
		}

		public String getParentId() {
			return _parentId;
		}

		public String getType() {
			return _type;
		}

		private final List<String> _childrenItemIds;
		private final JSONObject _config;
		private final String _itemId;
		private final String _parentId;
		private final String _type;

	}

	public static class Root {

		public Root(String main) {
			_main = main;
		}

		public String getMain() {
			return _main;
		}

		private final String _main;

	}

	private final Map<String, Item> _layoutStructureItems;
	private final Root _layoutStructureRoot;

}