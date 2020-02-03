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

import com.liferay.layout.util.constants.LayoutDataItemTypeConstants;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Víctor Galán
 */
public class LayoutStructure {

	public static LayoutStructure of(String layoutStructure) {
		try {
			JSONObject layoutStructureJSONObject =
				JSONFactoryUtil.createJSONObject(layoutStructure);

			JSONObject rootItemsJSONObject =
				layoutStructureJSONObject.getJSONObject("rootItems");

			JSONObject itemsJSONObject =
				layoutStructureJSONObject.getJSONObject("items");

			Map<String, LayoutStructureItem> layoutStructureItems =
				new HashMap<>(itemsJSONObject.length());

			for (String key : itemsJSONObject.keySet()) {
				layoutStructureItems.put(
					key,
					LayoutStructureItem.of(itemsJSONObject.getJSONObject(key)));
			}

			return new LayoutStructure(
				layoutStructureItems, rootItemsJSONObject.getString("main"));
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to parse JSON", jsonException);
			}
		}

		return new LayoutStructure(new HashMap<>(), StringPool.BLANK);
	}

	public LayoutStructure(
		Map<String, LayoutStructureItem> layoutStructureItems,
		String mainItemId) {

		_layoutStructureItems = layoutStructureItems;
		_mainItemId = mainItemId;
	}

	public LayoutStructureItem addFragmentLayoutStructureItem(
		long fragmentEntryLinkId, String parentItemId, int position) {

		LayoutStructureItem layoutStructureItem = addLayoutStructureItem(
			LayoutDataItemTypeConstants.TYPE_FRAGMENT, parentItemId, position);

		layoutStructureItem.updateItemConfigJSONObject(
			JSONUtil.put("fragmentEntryLinkId", fragmentEntryLinkId));

		return layoutStructureItem;
	}

	public LayoutStructureItem addLayoutStructureItem(
		String itemType, String parentItemId, int position) {

		LayoutStructureItem layoutStructureItem =
			LayoutStructureItemUtil.create(itemType, parentItemId);

		_updateLayoutStructure(layoutStructureItem, position);

		return layoutStructureItem;
	}

	public LayoutStructureItem addRowLayoutStructureItem(
		String parentItemId, int position) {

		LayoutStructureItem parentLayoutStructureItem =
			_layoutStructureItems.get(parentItemId);

		int newPosition = position;

		if (Objects.equals(
				parentLayoutStructureItem.getItemType(),
				LayoutDataItemTypeConstants.TYPE_ROOT)) {

			LayoutStructureItem containerLayoutStructureItem =
				addLayoutStructureItem(
					LayoutDataItemTypeConstants.TYPE_CONTAINER, parentItemId,
					position);

			parentItemId = containerLayoutStructureItem.getItemId();

			newPosition = 0;
		}

		LayoutStructureItem layoutStructureItem = addLayoutStructureItem(
			LayoutDataItemTypeConstants.TYPE_ROW, parentItemId, newPosition);

		for (int i = 0; i < _DEFAULT_ROW_COLUMNS; i++) {
			_addColumnLayoutStructureItem(
				layoutStructureItem.getItemId(), i, 4);
		}

		layoutStructureItem.updateItemConfigJSONObject(
			JSONUtil.put("numberOfColumns", _DEFAULT_ROW_COLUMNS));

		return layoutStructureItem;
	}

	public List<LayoutStructureItem> deleteLayoutStructureItem(String itemId) {
		List<LayoutStructureItem> deletedLayoutStructureItems =
			new ArrayList<>();

		LayoutStructureItem layoutStructureItem = _layoutStructureItems.get(
			itemId);

		if (Objects.equals(
				layoutStructureItem.getItemType(),
				LayoutDataItemTypeConstants.TYPE_DROP_ZONE)) {

			throw new UnsupportedOperationException(
				"Removing the drop zone of a layout structure is not allowed");
		}

		List<String> childrenItemIds = new ArrayList<>(
			layoutStructureItem.getChildrenItemIds());

		for (String childrenItemId : childrenItemIds) {
			deletedLayoutStructureItems.addAll(
				deleteLayoutStructureItem(childrenItemId));
		}

		deletedLayoutStructureItems.add(layoutStructureItem);

		LayoutStructureItem parentLayoutStructureItem =
			_layoutStructureItems.get(layoutStructureItem.getParentItemId());

		parentLayoutStructureItem.deleteChildrenItem(itemId);

		_layoutStructureItems.remove(itemId);

		return deletedLayoutStructureItems;
	}

	public List<LayoutStructureItem> duplicateLayoutStructureItem(
		String itemId) {

		LayoutStructureItem layoutStructureItem = _layoutStructureItems.get(
			itemId);

		LayoutStructureItem parentLayoutStructureItem =
			_layoutStructureItems.get(layoutStructureItem.getParentItemId());

		List<String> childrenItemIds =
			parentLayoutStructureItem.getChildrenItemIds();

		int position = childrenItemIds.indexOf(itemId) + 1;

		return _duplicateLayoutStructureItem(
			itemId, layoutStructureItem.getParentItemId(), position);
	}

	public LayoutStructureItem moveLayoutStructureItem(
		String itemId, String parentItemId, int position) {

		LayoutStructureItem layoutStructureItem = _layoutStructureItems.get(
			itemId);

		LayoutStructureItem oldParentLayoutStructureItem =
			_layoutStructureItems.get(layoutStructureItem.getParentItemId());

		oldParentLayoutStructureItem.deleteChildrenItem(itemId);

		LayoutStructureItem newParentLayoutStructureItem =
			_layoutStructureItems.get(parentItemId);

		if (!Objects.equals(
				layoutStructureItem.getItemType(),
				LayoutDataItemTypeConstants.TYPE_ROW) ||
			!Objects.equals(
				newParentLayoutStructureItem.getItemType(),
				LayoutDataItemTypeConstants.TYPE_ROOT)) {

			newParentLayoutStructureItem.addChildrenItem(position, itemId);

			layoutStructureItem.setParentItemId(parentItemId);

			return layoutStructureItem;
		}

		LayoutStructureItem containerLayoutStructureItem =
			addLayoutStructureItem(
				LayoutDataItemTypeConstants.TYPE_CONTAINER, parentItemId,
				position);

		containerLayoutStructureItem.addChildrenItem(itemId);

		layoutStructureItem.setParentItemId(
			containerLayoutStructureItem.getItemId());

		return layoutStructureItem;
	}

	public JSONObject toJSONObject() {
		String dropZoneItemId = StringPool.BLANK;
		JSONObject layoutStructureItemsJSONObject =
			JSONFactoryUtil.createJSONObject();

		for (Map.Entry<String, LayoutStructureItem> entry :
				_layoutStructureItems.entrySet()) {

			LayoutStructureItem layoutStructureItem = entry.getValue();

			if (Objects.equals(
					layoutStructureItem.getItemType(),
					LayoutDataItemTypeConstants.TYPE_DROP_ZONE)) {

				dropZoneItemId = layoutStructureItem.getItemId();
			}

			layoutStructureItemsJSONObject.put(
				entry.getKey(), layoutStructureItem.toJSONObject());
		}

		return JSONUtil.put(
			"items", layoutStructureItemsJSONObject
		).put(
			"rootItems",
			JSONUtil.put(
				"dropZone", dropZoneItemId
			).put(
				"main", _mainItemId
			)
		).put(
			"version", 1
		);
	}

	public LayoutStructureItem updateItemConfig(
		JSONObject itemConfigJSONObject, String itemId) {

		LayoutStructureItem layoutStructureItem = _layoutStructureItems.get(
			itemId);

		layoutStructureItem.updateItemConfigJSONObject(itemConfigJSONObject);

		return layoutStructureItem;
	}

	public List<LayoutStructureItem> updateRowColumnsLayoutStructureItem(
		String itemId, int numberOfColumns) {

		if (numberOfColumns > _MAX_COLUMNS) {
			return Collections.emptyList();
		}

		LayoutStructureItem layoutStructureItem = _layoutStructureItems.get(
			itemId);

		JSONObject itemConfigJSONObject =
			layoutStructureItem.getItemConfigJSONObject();

		int oldNumberOfColumns = itemConfigJSONObject.getInt("numberOfColumns");

		if (oldNumberOfColumns == numberOfColumns) {
			return Collections.emptyList();
		}

		layoutStructureItem.updateItemConfigJSONObject(
			JSONUtil.put("numberOfColumns", numberOfColumns));

		List<String> childrenItemIds = new ArrayList<>(
			layoutStructureItem.getChildrenItemIds());

		if (oldNumberOfColumns < numberOfColumns) {
			for (int i = 0; i < oldNumberOfColumns; i++) {
				String childrenItemId = childrenItemIds.get(i);

				LayoutStructureItem childLayoutStructureItem =
					_layoutStructureItems.get(childrenItemId);

				childLayoutStructureItem.updateItemConfigJSONObject(
					JSONUtil.put(
						"size", _COLUMN_SIZES[numberOfColumns - 1][i]));
			}

			for (int i = oldNumberOfColumns; i < numberOfColumns; i++) {
				_addColumnLayoutStructureItem(
					itemId, i, _COLUMN_SIZES[numberOfColumns - 1][i]);
			}

			return Collections.emptyList();
		}

		for (int i = 0; i < numberOfColumns; i++) {
			String childrenItemId = childrenItemIds.get(i);

			LayoutStructureItem childLayoutStructureItem =
				_layoutStructureItems.get(childrenItemId);

			childLayoutStructureItem.updateItemConfigJSONObject(
				JSONUtil.put("size", _COLUMN_SIZES[numberOfColumns - 1][i]));
		}

		List<LayoutStructureItem> deletedLayoutStructureItems =
			new ArrayList<>();

		for (int i = numberOfColumns; i < oldNumberOfColumns; i++) {
			String childrenItemId = childrenItemIds.get(i);

			deletedLayoutStructureItems.addAll(
				deleteLayoutStructureItem(childrenItemId));
		}

		return deletedLayoutStructureItems;
	}

	private void _addColumnLayoutStructureItem(
		String parentItemId, int position, int size) {

		LayoutStructureItem layoutStructureItem = addLayoutStructureItem(
			LayoutDataItemTypeConstants.TYPE_COLUMN, parentItemId, position);

		layoutStructureItem.updateItemConfigJSONObject(
			JSONUtil.put("size", size));
	}

	private List<LayoutStructureItem> _duplicateLayoutStructureItem(
		String itemId, String parentItemId, int position) {

		LayoutStructureItem layoutStructureItem = _layoutStructureItems.get(
			itemId);

		LayoutStructureItem newLayoutStructureItem =
			LayoutStructureItemUtil.create(
				layoutStructureItem.getItemType(), parentItemId);

		if (newLayoutStructureItem == null) {
			return Collections.emptyList();
		}

		List<LayoutStructureItem> duplicatedLayoutStructureItems =
			new ArrayList<>();

		JSONObject newItemConfigJSONObject = JSONFactoryUtil.createJSONObject();

		JSONObject itemConfigJSONObject =
			layoutStructureItem.getItemConfigJSONObject();

		for (String key : itemConfigJSONObject.keySet()) {
			newItemConfigJSONObject.put(key, itemConfigJSONObject.get(key));
		}

		newLayoutStructureItem.setItemConfigJSONObject(newItemConfigJSONObject);

		newLayoutStructureItem.setItemId(String.valueOf(UUID.randomUUID()));
		newLayoutStructureItem.setParentItemId(parentItemId);

		_updateLayoutStructure(newLayoutStructureItem, position);

		duplicatedLayoutStructureItems.add(newLayoutStructureItem);

		for (String childrenItemId : layoutStructureItem.getChildrenItemIds()) {
			duplicatedLayoutStructureItems.addAll(
				_duplicateLayoutStructureItem(
					childrenItemId, newLayoutStructureItem.getItemId(), -1));
		}

		return duplicatedLayoutStructureItems;
	}

	private void _updateLayoutStructure(
		LayoutStructureItem layoutStructureItem, int position) {

		_layoutStructureItems.put(
			layoutStructureItem.getItemId(), layoutStructureItem);

		if (Validator.isNull(layoutStructureItem.getParentItemId())) {
			return;
		}

		LayoutStructureItem parentLayoutStructureItem =
			_layoutStructureItems.get(layoutStructureItem.getParentItemId());

		if (position >= 0) {
			parentLayoutStructureItem.addChildrenItem(
				position, layoutStructureItem.getItemId());
		}
		else {
			parentLayoutStructureItem.addChildrenItem(
				layoutStructureItem.getItemId());
		}
	}

	private static final int[][] _COLUMN_SIZES = {
		{12}, {6, 6}, {4, 4, 4}, {3, 3, 3, 3}, {2, 2, 4, 2, 2},
		{2, 2, 2, 2, 2, 2}, {1, 1, 1, 6, 1, 1, 1}, {1, 1, 1, 3, 3, 1, 1, 1},
		{1, 1, 1, 1, 4, 1, 1, 1, 1}, {1, 1, 1, 1, 2, 2, 1, 1, 1, 1},
		{1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
	};

	private static final int _DEFAULT_ROW_COLUMNS = 3;

	private static final int _MAX_COLUMNS = 12;

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutStructure.class);

	private final Map<String, LayoutStructureItem> _layoutStructureItems;
	private final String _mainItemId;

}