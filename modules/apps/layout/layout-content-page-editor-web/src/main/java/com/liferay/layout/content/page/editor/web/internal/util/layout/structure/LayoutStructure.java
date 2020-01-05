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
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
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

	public void addFragmentLayoutStructureItem(
		long fragmentEntryLinkId, String parentItemId, int position) {

		addLayoutStructureItem(
			JSONUtil.put("fragmentEntryLinkId", fragmentEntryLinkId),
			String.valueOf(UUID.randomUUID()),
			LayoutDataItemTypeConstants.TYPE_FRAGMENT, parentItemId, position);
	}

	public void addLayoutStructureItem(
		JSONObject itemConfigJSONObject, String itemId, String itemType,
		String parentItemId, int position) {

		LayoutStructureItem layoutStructureItem = new LayoutStructureItem(
			itemConfigJSONObject, itemId, parentItemId, itemType);

		addLayoutStructureItem(layoutStructureItem, parentItemId, position);
	}

	public void addLayoutStructureItem(
		LayoutStructureItem layoutStructureItem, String parentItemId,
		int position) {

		_layoutStructureItems.put(
			layoutStructureItem.getItemId(), layoutStructureItem);

		if (Validator.isNull(parentItemId)) {
			return;
		}

		LayoutStructureItem parentLayoutStructureItem =
			_layoutStructureItems.get(parentItemId);

		parentLayoutStructureItem.addChildrenItem(
			position, layoutStructureItem.getItemId());
	}

	public void addRowLayoutStructureItem(
		JSONObject itemConfigJSONObject, String itemId, String parentItemId,
		int position) {

		addLayoutStructureItem(
			itemConfigJSONObject, itemId, LayoutDataItemTypeConstants.TYPE_ROW,
			parentItemId, position);

		_addColumnLayoutStructureItem(itemId, 0, 4);
		_addColumnLayoutStructureItem(itemId, 1, 4);
		_addColumnLayoutStructureItem(itemId, 2, 4);
	}

	public LayoutStructureItem deleteLayoutStructureItem(String itemId) {
		LayoutStructureItem layoutStructureItem = _layoutStructureItems.get(
			itemId);

		LayoutStructureItem parentLayoutStructureItem =
			_layoutStructureItems.get(layoutStructureItem.getParentItemId());

		parentLayoutStructureItem.deleteChildrenItem(itemId);

		_layoutStructureItems.remove(itemId);

		return layoutStructureItem;
	}

	public void duplicateLayoutStructureItem(
		long fragmentEntryLinkId, String itemId) {

		LayoutStructureItem layoutStructureItem = getLayoutStructureItem(
			itemId);

		LayoutStructureItem parentLayoutStructureItem = getLayoutStructureItem(
			layoutStructureItem.getParentItemId());

		List<String> childrenItemIds =
			parentLayoutStructureItem.getChildrenItemIds();

		addLayoutStructureItem(
			JSONUtil.put("fragmentEntryLinkId", fragmentEntryLinkId),
			String.valueOf(UUID.randomUUID()),
			layoutStructureItem.getItemType(),
			layoutStructureItem.getParentItemId(),
			childrenItemIds.indexOf(itemId) + 1);
	}

	public LayoutStructureItem getLayoutStructureItem(String itemId) {
		return _layoutStructureItems.get(itemId);
	}

	public void moveLayoutStructureItem(
		String itemId, String parentItemId, int position) {

		LayoutStructureItem layoutStructureItem = _layoutStructureItems.get(
			itemId);

		LayoutStructureItem oldParentLayoutStructureItem =
			_layoutStructureItems.get(layoutStructureItem.getParentItemId());

		oldParentLayoutStructureItem.deleteChildrenItem(itemId);

		LayoutStructureItem newParentLayoutStructureItem =
			_layoutStructureItems.get(parentItemId);

		newParentLayoutStructureItem.addChildrenItem(position, itemId);
	}

	public JSONObject toJSONObject() {
		JSONObject layoutStructureItemsJSONObject =
			JSONFactoryUtil.createJSONObject();

		for (Map.Entry<String, LayoutStructureItem> entry :
				_layoutStructureItems.entrySet()) {

			LayoutStructureItem layoutStructureItem = entry.getValue();

			layoutStructureItemsJSONObject.put(
				entry.getKey(), layoutStructureItem.toJSONObject());
		}

		return JSONUtil.put(
			"items", layoutStructureItemsJSONObject
		).put(
			"rootItems", JSONUtil.put("main", _mainItemId)
		).put(
			"version", 1
		);
	}

	public void updateItemConfig(
		JSONObject itemConfigJSONObject, String itemId) {

		LayoutStructureItem layoutStructureItem = _layoutStructureItems.get(
			itemId);

		layoutStructureItem.updateItemConfigJSONObject(itemConfigJSONObject);
	}

	public List<LayoutStructureItem> updateRowColumnsLayoutStructureItem(
		String itemId, int numberOfColumns) {

		if (numberOfColumns > _MAX_COLUMNS) {
			return Collections.emptyList();
		}

		LayoutStructureItem layoutStructureItem = _layoutStructureItems.get(
			itemId);

		List<String> childrenItemIds = new ArrayList<>(
			layoutStructureItem.getChildrenItemIds());

		int childrenItemIdsSize = childrenItemIds.size();

		if (childrenItemIdsSize == numberOfColumns) {
			return Collections.emptyList();
		}

		if (childrenItemIdsSize < numberOfColumns) {
			for (int i = 0; i < childrenItemIdsSize; i++) {
				String childrenItemId = childrenItemIds.get(i);

				LayoutStructureItem childLayoutStructureItem =
					_layoutStructureItems.get(childrenItemId);

				childLayoutStructureItem.updateItemConfigJSONObject(
					JSONUtil.put("size", _COLUMN_SIZES[numberOfColumns][i]));
			}

			for (int i = childrenItemIdsSize; i < numberOfColumns; i++) {
				_addColumnLayoutStructureItem(
					itemId, i, _COLUMN_SIZES[numberOfColumns][i]);
			}

			return Collections.emptyList();
		}

		for (int i = 0; i < numberOfColumns; i++) {
			String childrenItemId = childrenItemIds.get(i);

			LayoutStructureItem childLayoutStructureItem =
				_layoutStructureItems.get(childrenItemId);

			childLayoutStructureItem.updateItemConfigJSONObject(
				JSONUtil.put("size", _COLUMN_SIZES[numberOfColumns][i]));
		}

		List<LayoutStructureItem> deletedLayoutStructureItems =
			new ArrayList<>();

		for (int i = numberOfColumns; i < childrenItemIdsSize; i++) {
			String childrenItemId = childrenItemIds.get(i);

			LayoutStructureItem deletedLayoutStructureItem =
				deleteLayoutStructureItem(childrenItemId);

			deletedLayoutStructureItems.add(deletedLayoutStructureItem);
		}

		return deletedLayoutStructureItems;
	}

	private void _addColumnLayoutStructureItem(
		String parentItemId, int position, int size) {

		addLayoutStructureItem(
			JSONUtil.put("size", size), String.valueOf(UUID.randomUUID()),
			LayoutDataItemTypeConstants.TYPE_COLUMN, parentItemId, position);
	}

	private static final int[][] _COLUMN_SIZES = {
		{12}, {6, 6}, {4, 4, 4}, {3, 3, 3, 3}, {2, 2, 4, 2, 2},
		{2, 2, 2, 2, 2, 2}, {1, 1, 1, 6, 1, 1, 1}, {1, 1, 1, 3, 3, 1, 1, 1},
		{1, 1, 1, 1, 4, 1, 1, 1, 1}, {1, 1, 1, 1, 2, 2, 1, 1, 1, 1},
		{1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
	};

	private static final int _MAX_COLUMNS = 12;

	private final Map<String, LayoutStructureItem> _layoutStructureItems;
	private final String _mainItemId;

}