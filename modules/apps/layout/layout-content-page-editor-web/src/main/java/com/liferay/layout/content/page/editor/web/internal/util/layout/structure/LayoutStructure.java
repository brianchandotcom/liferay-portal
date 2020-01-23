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
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Víctor Galán
 */
public class LayoutStructure {

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

		String itemId = String.valueOf(UUID.randomUUID());

		LayoutStructureItem layoutStructureItem = new LayoutStructureItem(
			itemId, parentItemId, itemType);

		_layoutStructureItems.put(
			layoutStructureItem.getItemId(), layoutStructureItem);

		if (Validator.isNull(parentItemId)) {
			return layoutStructureItem;
		}

		LayoutStructureItem parentLayoutStructureItem =
			_layoutStructureItems.get(parentItemId);

		parentLayoutStructureItem.addChildrenItem(
			position, layoutStructureItem.getItemId());

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

	public LayoutStructureItem duplicateLayoutStructureItem(
		long fragmentEntryLinkId, String itemId) {

		LayoutStructureItem layoutStructureItem = _layoutStructureItems.get(
			itemId);

		LayoutStructureItem parentLayoutStructureItem =
			_layoutStructureItems.get(layoutStructureItem.getParentItemId());

		List<String> childrenItemIds =
			parentLayoutStructureItem.getChildrenItemIds();

		LayoutStructureItem duplicateLayoutStructureItem =
			addLayoutStructureItem(
				layoutStructureItem.getItemType(),
				layoutStructureItem.getParentItemId(),
				childrenItemIds.indexOf(itemId) + 1);

		duplicateLayoutStructureItem.updateItemConfigJSONObject(
			JSONUtil.put("fragmentEntryLinkId", fragmentEntryLinkId));

		return duplicateLayoutStructureItem;
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

		newParentLayoutStructureItem.addChildrenItem(position, itemId);

		layoutStructureItem.setParentItemId(parentItemId);

		return layoutStructureItem;
	}

	public JSONObject toJSONObject() {
		JSONObject layoutStructureItemsJSONObject =
			JSONFactoryUtil.createJSONObject();

		String dropZoneItemId = StringPool.BLANK;

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

	private static final int[][] _COLUMN_SIZES = {
		{12}, {6, 6}, {4, 4, 4}, {3, 3, 3, 3}, {2, 2, 4, 2, 2},
		{2, 2, 2, 2, 2, 2}, {1, 1, 1, 6, 1, 1, 1}, {1, 1, 1, 3, 3, 1, 1, 1},
		{1, 1, 1, 1, 4, 1, 1, 1, 1}, {1, 1, 1, 1, 2, 2, 1, 1, 1, 1},
		{1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
	};

	private static final int _DEFAULT_ROW_COLUMNS = 3;

	private static final int _MAX_COLUMNS = 12;

	private final Map<String, LayoutStructureItem> _layoutStructureItems;
	private final String _mainItemId;

}