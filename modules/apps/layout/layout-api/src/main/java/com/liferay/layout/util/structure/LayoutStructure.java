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

import com.liferay.layout.responsive.ViewportSize;
import com.liferay.layout.util.constants.LayoutStructureConstants;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.util.PropsUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * @author Víctor Galán
 */
public class LayoutStructure {

	public static LayoutStructure of(String layoutStructure) {
		if (Validator.isNull(layoutStructure)) {
			return new LayoutStructure();
		}

		try {
			JSONObject layoutStructureJSONObject =
				JSONFactoryUtil.createJSONObject(layoutStructure);

			JSONObject rootItemsJSONObject =
				layoutStructureJSONObject.getJSONObject("rootItems");

			JSONObject itemsJSONObject =
				layoutStructureJSONObject.getJSONObject("items");

			Map<Long, LayoutStructureItem> fragmentLayoutStructureItems =
				new HashMap<>(itemsJSONObject.length());

			Map<String, LayoutStructureItem> layoutStructureItems =
				new HashMap<>(itemsJSONObject.length());

			for (String key : itemsJSONObject.keySet()) {
				LayoutStructureItem layoutStructureItem =
					LayoutStructureItem.of(itemsJSONObject.getJSONObject(key));

				layoutStructureItems.put(key, layoutStructureItem);

				if (layoutStructureItem instanceof
						FragmentStyledLayoutStructureItem) {

					FragmentStyledLayoutStructureItem
						fragmentStyledLayoutStructureItem =
							(FragmentStyledLayoutStructureItem)
								layoutStructureItem;

					fragmentLayoutStructureItems.put(
						fragmentStyledLayoutStructureItem.
							getFragmentEntryLinkId(),
						fragmentStyledLayoutStructureItem);
				}
			}

			JSONArray deletedLayoutStructureItemJSONArray = Optional.ofNullable(
				layoutStructureJSONObject.getJSONArray("deletedItems")
			).orElse(
				JSONFactoryUtil.createJSONArray()
			);

			Map<String, DeletedLayoutStructureItem>
				deletedLayoutStructureItems = new HashMap<>(
					deletedLayoutStructureItemJSONArray.length());

			deletedLayoutStructureItemJSONArray.forEach(
				deletedLayoutStructureItemJSONObject -> {
					DeletedLayoutStructureItem deletedLayoutStructureItem =
						DeletedLayoutStructureItem.of(
							(JSONObject)deletedLayoutStructureItemJSONObject);

					deletedLayoutStructureItems.put(
						deletedLayoutStructureItem.getItemId(),
						deletedLayoutStructureItem);
				});

			return new LayoutStructure(
				deletedLayoutStructureItems, fragmentLayoutStructureItems,
				layoutStructureItems, rootItemsJSONObject.getString("main"));
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to parse JSON", jsonException);
			}
		}

		return new LayoutStructure();
	}

	public LayoutStructure() {
		_fragmentLayoutStructureItems = new HashMap<>();
		_deletedLayoutStructureItems = new HashMap<>();
		_layoutStructureItems = new HashMap<>();
		_mainItemId = StringPool.BLANK;
	}

	public LayoutStructureItem addCollectionItemLayoutStructureItem(
		String parentItemId, int position) {

		CollectionItemLayoutStructureItem collectionItemLayoutStructureItem =
			new CollectionItemLayoutStructureItem(parentItemId);

		_updateLayoutStructure(collectionItemLayoutStructureItem, position);

		return collectionItemLayoutStructureItem;
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 #addCollectionStyledLayoutStructureItem(String, int)}
	 */
	@Deprecated
	public LayoutStructureItem addCollectionLayoutStructureItem(
		String parentItemId, int position) {

		return addCollectionStyledLayoutStructureItem(parentItemId, position);
	}

	public LayoutStructureItem addCollectionStyledLayoutStructureItem(
		String parentItemId, int position) {

		CollectionStyledLayoutStructureItem
			collectionStyledLayoutStructureItem =
				new CollectionStyledLayoutStructureItem(parentItemId);

		_updateLayoutStructure(collectionStyledLayoutStructureItem, position);

		addCollectionItemLayoutStructureItem(
			collectionStyledLayoutStructureItem.getItemId(), 0);

		return collectionStyledLayoutStructureItem;
	}

	public LayoutStructureItem addColumnLayoutStructureItem(
		String parentItemId, int position) {

		ColumnLayoutStructureItem columnLayoutStructureItem =
			new ColumnLayoutStructureItem(parentItemId);

		columnLayoutStructureItem.setSize(_MAX_COLUMNS);

		_updateLayoutStructure(columnLayoutStructureItem, position);

		return columnLayoutStructureItem;
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 #addContainerStyledLayoutStructureItem(String, int)}
	 */
	@Deprecated
	public LayoutStructureItem addContainerLayoutStructureItem(
		String parentItemId, int position) {

		return addContainerStyledLayoutStructureItem(parentItemId, position);
	}

	public LayoutStructureItem addContainerStyledLayoutStructureItem(
		String parentItemId, int position) {

		ContainerStyledLayoutStructureItem containerStyledLayoutStructureItem =
			new ContainerStyledLayoutStructureItem(parentItemId);

		_updateLayoutStructure(containerStyledLayoutStructureItem, position);

		return containerStyledLayoutStructureItem;
	}

	public LayoutStructureItem addDropZoneLayoutStructureItem(
		String parentItemId, int position) {

		DropZoneLayoutStructureItem dropZoneLayoutStructureItem =
			new DropZoneLayoutStructureItem(parentItemId);

		_updateLayoutStructure(dropZoneLayoutStructureItem, position);

		return dropZoneLayoutStructureItem;
	}

	public LayoutStructureItem addFragmentDropZoneLayoutStructureItem(
		String parentItemId, int position) {

		FragmentDropZoneLayoutStructureItem
			fragmentDropZoneLayoutStructureItem =
				new FragmentDropZoneLayoutStructureItem(parentItemId);

		_updateLayoutStructure(fragmentDropZoneLayoutStructureItem, position);

		return fragmentDropZoneLayoutStructureItem;
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 #addFragmentStyledLayoutStructureItem(long, String, int)}
	 */
	@Deprecated
	public LayoutStructureItem addFragmentLayoutStructureItem(
		long fragmentEntryLinkId, String parentItemId, int position) {

		return addFragmentStyledLayoutStructureItem(
			fragmentEntryLinkId, parentItemId, position);
	}

	public LayoutStructureItem addFragmentStyledLayoutStructureItem(
		long fragmentEntryLinkId, String parentItemId, int position) {

		FragmentStyledLayoutStructureItem fragmentStyledLayoutStructureItem =
			new FragmentStyledLayoutStructureItem(parentItemId);

		_updateLayoutStructure(fragmentStyledLayoutStructureItem, position);

		fragmentStyledLayoutStructureItem.setFragmentEntryLinkId(
			fragmentEntryLinkId);

		return fragmentStyledLayoutStructureItem;
	}

	public LayoutStructureItem addLayoutStructureItem(
		LayoutStructureItem layoutStructureItem) {

		_layoutStructureItems.put(
			layoutStructureItem.getItemId(), layoutStructureItem);

		return layoutStructureItem;
	}

	public LayoutStructureItem addLayoutStructureItem(
		String itemType, String parentItemId, int position) {

		LayoutStructureItem layoutStructureItem =
			LayoutStructureItemUtil.create(itemType, parentItemId);

		_updateLayoutStructure(layoutStructureItem, position);

		return layoutStructureItem;
	}

	public LayoutStructureItem addRootLayoutStructureItem() {
		RootLayoutStructureItem rootLayoutStructureItem =
			new RootLayoutStructureItem();

		_updateLayoutStructure(rootLayoutStructureItem, 0);

		if (Validator.isNull(_mainItemId)) {
			_mainItemId = rootLayoutStructureItem.getItemId();
		}

		return rootLayoutStructureItem;
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 #addRowStyledLayoutStructureItem(String, int, int)}
	 */
	@Deprecated
	public LayoutStructureItem addRowLayoutStructureItem(
		String parentItemId, int position, int numberOfColumns) {

		return addRowStyledLayoutStructureItem(
			parentItemId, position, numberOfColumns);
	}

	public LayoutStructureItem addRowStyledLayoutStructureItem(
		String parentItemId, int position, int numberOfColumns) {

		RowStyledLayoutStructureItem rowStyledLayoutStructureItem =
			new RowStyledLayoutStructureItem(parentItemId);

		_updateLayoutStructure(rowStyledLayoutStructureItem, position);

		rowStyledLayoutStructureItem.setNumberOfColumns(numberOfColumns);

		return rowStyledLayoutStructureItem;
	}

	public List<LayoutStructureItem> deleteLayoutStructureItem(String itemId) {
		LayoutStructureItem layoutStructureItem = _layoutStructureItems.get(
			itemId);

		if (layoutStructureItem instanceof DropZoneLayoutStructureItem) {
			throw new UnsupportedOperationException(
				"Removing the drop zone of a layout structure is not allowed");
		}

		List<LayoutStructureItem> deletedLayoutStructureItems =
			new ArrayList<>();

		List<String> childrenItemIds = new ArrayList<>(
			layoutStructureItem.getChildrenItemIds());

		for (String childrenItemId : childrenItemIds) {
			deletedLayoutStructureItems.addAll(
				deleteLayoutStructureItem(childrenItemId));
		}

		deletedLayoutStructureItems.add(layoutStructureItem);

		if (Validator.isNotNull(layoutStructureItem.getParentItemId())) {
			LayoutStructureItem parentLayoutStructureItem =
				_layoutStructureItems.get(
					layoutStructureItem.getParentItemId());

			if (parentLayoutStructureItem != null) {
				parentLayoutStructureItem.deleteChildrenItem(itemId);
			}
		}

		_deletedLayoutStructureItems.remove(itemId);
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

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof LayoutStructure)) {
			return false;
		}

		LayoutStructure layoutStructure = (LayoutStructure)object;

		if (Objects.equals(_mainItemId, layoutStructure._mainItemId) &&
			Objects.equals(
				_layoutStructureItems, layoutStructure._layoutStructureItems)) {

			return true;
		}

		return false;
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
	 */
	@Deprecated
	public int getColumnSize(int size, int column) {
		return LayoutStructureConstants.COLUMN_SIZES[size][column];
	}

	public List<DeletedLayoutStructureItem> getDeletedLayoutStructureItems() {
		return ListUtil.fromCollection(_deletedLayoutStructureItems.values());
	}

	public LayoutStructureItem getDropZoneLayoutStructureItem() {
		for (LayoutStructureItem layoutStructureItem :
				getLayoutStructureItems()) {

			if (layoutStructureItem instanceof DropZoneLayoutStructureItem) {
				return layoutStructureItem;
			}
		}

		return null;
	}

	public Map<Long, LayoutStructureItem> getFragmentLayoutStructureItems() {
		return _fragmentLayoutStructureItems;
	}

	public LayoutStructureItem getLayoutStructureItem(String itemId) {
		return _layoutStructureItems.get(itemId);
	}

	public LayoutStructureItem getLayoutStructureItemByFragmentEntryLinkId(
		long fragmentEntryLinkId) {

		return _fragmentLayoutStructureItems.get(fragmentEntryLinkId);
	}

	public List<LayoutStructureItem> getLayoutStructureItems() {
		return ListUtil.fromCollection(_layoutStructureItems.values());
	}

	public String getMainItemId() {
		return _mainItemId;
	}

	public LayoutStructureItem getMainLayoutStructureItem() {
		return _layoutStructureItems.get(_mainItemId);
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, getMainItemId());
	}

	public void markLayoutStructureItemForDeletion(
		String itemId, List<String> portletIds) {

		LayoutStructureItem layoutStructureItem = _layoutStructureItems.get(
			itemId);

		if (layoutStructureItem instanceof DropZoneLayoutStructureItem) {
			throw new UnsupportedOperationException(
				"Removing the drop zone of a layout structure is not allowed");
		}

		if (Validator.isNotNull(layoutStructureItem.getParentItemId())) {
			LayoutStructureItem parentLayoutStructureItem =
				_layoutStructureItems.get(
					layoutStructureItem.getParentItemId());

			List<String> childrenItemIds =
				parentLayoutStructureItem.getChildrenItemIds();

			int position = childrenItemIds.indexOf(itemId);

			childrenItemIds.remove(itemId);

			_deletedLayoutStructureItems.put(
				itemId,
				new DeletedLayoutStructureItem(
					itemId, portletIds, position, _getChildrenItemIds(itemId)));
		}
		else {
			_deletedLayoutStructureItems.put(
				itemId,
				new DeletedLayoutStructureItem(
					itemId, portletIds, 0, _getChildrenItemIds(itemId)));
		}
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

	public void setMainItemId(String mainItemId) {
		_mainItemId = mainItemId;
	}

	public JSONObject toJSONObject() {
		String dropZoneItemId = StringPool.BLANK;
		JSONObject layoutStructureItemsJSONObject =
			JSONFactoryUtil.createJSONObject();

		for (Map.Entry<String, LayoutStructureItem> entry :
				_layoutStructureItems.entrySet()) {

			LayoutStructureItem layoutStructureItem = entry.getValue();

			if (layoutStructureItem instanceof DropZoneLayoutStructureItem) {
				dropZoneItemId = layoutStructureItem.getItemId();
			}

			if (layoutStructureItem == null) {
				throw new RuntimeException(
					"Invalid layout structure item for key " + entry.getKey());
			}

			layoutStructureItemsJSONObject.put(
				entry.getKey(), layoutStructureItem.toJSONObject());
		}

		JSONArray deletedLayoutStructureItemsJSONArray =
			JSONFactoryUtil.createJSONArray();

		for (DeletedLayoutStructureItem deletedLayoutStructureItem :
				_deletedLayoutStructureItems.values()) {

			deletedLayoutStructureItemsJSONArray.put(
				deletedLayoutStructureItem.toJSONObject());
		}

		return JSONUtil.put(
			"deletedItems", deletedLayoutStructureItemsJSONArray
		).put(
			"items", layoutStructureItemsJSONObject
		).put(
			"rootItems",
			JSONUtil.put(
				"dropZone", dropZoneItemId
			).put(
				"main", _mainItemId
			)
		).put(
			"version", LayoutStructureConstants.LATEST_PAGE_DEFINITION_VERSION
		);
	}

	@Override
	public String toString() {
		JSONObject jsonObject = toJSONObject();

		return jsonObject.toJSONString();
	}

	public void unmarkLayoutStructureItemForDeletion(String itemId) {
		DeletedLayoutStructureItem deletedLayoutStructureItem =
			_deletedLayoutStructureItems.get(itemId);

		if (deletedLayoutStructureItem == null) {
			return;
		}

		LayoutStructureItem layoutStructureItem = _layoutStructureItems.get(
			itemId);

		LayoutStructureItem parentLayoutStructureItemId =
			_layoutStructureItems.get(layoutStructureItem.getParentItemId());

		parentLayoutStructureItemId.addChildrenItem(
			deletedLayoutStructureItem.getPosition(),
			deletedLayoutStructureItem.getItemId());

		_deletedLayoutStructureItems.remove(itemId);
	}

	public LayoutStructureItem updateItemConfig(
		JSONObject itemConfigJSONObject, String itemId) {

		LayoutStructureItem layoutStructureItem = _layoutStructureItems.get(
			itemId);

		layoutStructureItem.updateItemConfig(itemConfigJSONObject);

		if (layoutStructureItem instanceof RowStyledLayoutStructureItem) {
			RowStyledLayoutStructureItem rowStyledLayoutStructureItem =
				(RowStyledLayoutStructureItem)layoutStructureItem;

			int modulesPerRow = itemConfigJSONObject.getInt("modulesPerRow");

			if (modulesPerRow > 0) {
				_updateColumnSizes(
					rowStyledLayoutStructureItem,
					ViewportSize.DESKTOP.getViewportSizeId(), modulesPerRow,
					true);
			}

			for (ViewportSize viewportSize : ViewportSize.values()) {
				if (viewportSize.equals(ViewportSize.DESKTOP) ||
					!itemConfigJSONObject.has(
						viewportSize.getViewportSizeId())) {

					continue;
				}

				JSONObject viewportItemConfigJSONObject =
					itemConfigJSONObject.getJSONObject(
						viewportSize.getViewportSizeId());

				modulesPerRow = viewportItemConfigJSONObject.getInt(
					"modulesPerRow");

				if (modulesPerRow == 0) {
					continue;
				}

				_updateColumnSizes(
					rowStyledLayoutStructureItem,
					viewportSize.getViewportSizeId(), modulesPerRow, true);
			}
		}

		return layoutStructureItem;
	}

	public List<LayoutStructureItem> updateRowColumnsLayoutStructureItem(
		String itemId, int numberOfColumns) {

		if (numberOfColumns > _MAX_COLUMNS) {
			return Collections.emptyList();
		}

		RowStyledLayoutStructureItem rowStyledLayoutStructureItem =
			(RowStyledLayoutStructureItem)_layoutStructureItems.get(itemId);

		int oldNumberOfColumns =
			rowStyledLayoutStructureItem.getNumberOfColumns();

		if (oldNumberOfColumns == numberOfColumns) {
			return Collections.emptyList();
		}

		rowStyledLayoutStructureItem.setModulesPerRow(numberOfColumns);
		rowStyledLayoutStructureItem.setNumberOfColumns(numberOfColumns);

		for (ViewportSize viewportSize : ViewportSize.values()) {
			if (viewportSize.equals(ViewportSize.DESKTOP)) {
				continue;
			}

			_updateNumberOfColumns(
				rowStyledLayoutStructureItem, viewportSize.getViewportSizeId(),
				numberOfColumns);
		}

		List<String> childrenItemIds = new ArrayList<>(
			rowStyledLayoutStructureItem.getChildrenItemIds());

		if (oldNumberOfColumns < numberOfColumns) {
			for (int i = 0; i < oldNumberOfColumns; i++) {
				String childrenItemId = childrenItemIds.get(i);

				ColumnLayoutStructureItem columnLayoutStructureItem =
					(ColumnLayoutStructureItem)_layoutStructureItems.get(
						childrenItemId);

				columnLayoutStructureItem.setSize(
					LayoutStructureConstants.COLUMN_SIZES[numberOfColumns - 1]
						[i]);
			}

			for (int i = oldNumberOfColumns; i < numberOfColumns; i++) {
				_addColumnLayoutStructureItem(
					itemId, i,
					LayoutStructureConstants.COLUMN_SIZES[numberOfColumns - 1]
						[i]);
			}

			return Collections.emptyList();
		}

		for (int i = 0; i < numberOfColumns; i++) {
			String childrenItemId = childrenItemIds.get(i);

			ColumnLayoutStructureItem columnLayoutStructureItem =
				(ColumnLayoutStructureItem)_layoutStructureItems.get(
					childrenItemId);

			columnLayoutStructureItem.setSize(
				LayoutStructureConstants.COLUMN_SIZES[numberOfColumns - 1][i]);
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

	private LayoutStructure(
		Map<String, DeletedLayoutStructureItem> deletedLayoutStructureItems,
		Map<Long, LayoutStructureItem> fragmentLayoutStructureItems,
		Map<String, LayoutStructureItem> layoutStructureItems,
		String mainItemId) {

		_deletedLayoutStructureItems = deletedLayoutStructureItems;
		_fragmentLayoutStructureItems = fragmentLayoutStructureItems;
		_layoutStructureItems = layoutStructureItems;
		_mainItemId = mainItemId;
	}

	private void _addColumnLayoutStructureItem(
		String parentItemId, int position, int size) {

		ColumnLayoutStructureItem columnLayoutStructureItem =
			new ColumnLayoutStructureItem(parentItemId);

		columnLayoutStructureItem.setSize(size);

		if (GetterUtil.getBoolean(PropsUtil.get("feature.flag.LPS-119551"))) {
			columnLayoutStructureItem.setViewportConfiguration(
				ViewportSize.MOBILE_LANDSCAPE.getViewportSizeId(),
				JSONUtil.put("size", 12));
		}

		_updateLayoutStructure(columnLayoutStructureItem, position);
	}

	private List<LayoutStructureItem> _duplicateLayoutStructureItem(
		String itemId, String parentItemId, int position) {

		LayoutStructureItem layoutStructureItem = _layoutStructureItems.get(
			itemId);

		LayoutStructureItem newLayoutStructureItem =
			LayoutStructureItemUtil.create(
				layoutStructureItem.getItemType(), parentItemId);

		List<LayoutStructureItem> duplicatedLayoutStructureItems =
			new ArrayList<>();

		newLayoutStructureItem.setItemId(PortalUUIDUtil.generate());

		newLayoutStructureItem.updateItemConfig(
			layoutStructureItem.getItemConfigJSONObject());

		_updateLayoutStructure(newLayoutStructureItem, position);

		duplicatedLayoutStructureItems.add(newLayoutStructureItem);

		for (String childrenItemId : layoutStructureItem.getChildrenItemIds()) {
			duplicatedLayoutStructureItems.addAll(
				_duplicateLayoutStructureItem(
					childrenItemId, newLayoutStructureItem.getItemId(), -1));
		}

		return duplicatedLayoutStructureItems;
	}

	private Set<String> _getChildrenItemIds(String itemId) {
		LayoutStructureItem layoutStructureItem = _layoutStructureItems.get(
			itemId);

		Set<String> childrenItemIds = new HashSet<>(
			layoutStructureItem.getChildrenItemIds());

		for (String childrenItemId : layoutStructureItem.getChildrenItemIds()) {
			childrenItemIds.addAll(_getChildrenItemIds(childrenItemId));
		}

		return childrenItemIds;
	}

	private void _updateColumnSizes(
		RowStyledLayoutStructureItem rowStyledLayoutStructureItem,
		String viewportSizeId, int modulesPerRow, boolean updateEmpty) {

		int[] defaultSizes = LayoutStructureConstants.COLUMN_SIZES
			[rowStyledLayoutStructureItem.getNumberOfColumns() - 1];

		if (rowStyledLayoutStructureItem.getNumberOfColumns() !=
				modulesPerRow) {

			defaultSizes =
				_MODULE_SIZES
					[rowStyledLayoutStructureItem.getNumberOfColumns() - 2]
					[modulesPerRow - 1];
		}

		int position = 0;

		for (String childItemId :
				rowStyledLayoutStructureItem.getChildrenItemIds()) {

			LayoutStructureItem layoutStructureItem = getLayoutStructureItem(
				childItemId);

			if (!(layoutStructureItem instanceof ColumnLayoutStructureItem)) {
				continue;
			}

			ColumnLayoutStructureItem columnLayoutStructureItem =
				(ColumnLayoutStructureItem)layoutStructureItem;

			if (position > (defaultSizes.length - 1)) {
				position = 0;
			}

			int columnSize = defaultSizes[position++];

			if (Objects.equals(
					viewportSizeId, ViewportSize.DESKTOP.getViewportSizeId())) {

				columnLayoutStructureItem.setSize(columnSize);

				continue;
			}

			if (!updateEmpty &&
				GetterUtil.getBoolean(
					PropsUtil.get("feature.flag.LPS-119551")) &&
				Objects.equals(
					ViewportSize.MOBILE_LANDSCAPE.getViewportSizeId(),
					viewportSizeId)) {

				columnLayoutStructureItem.setViewportConfiguration(
					viewportSizeId, JSONUtil.put("size", 12));

				continue;
			}

			Map<String, JSONObject> columnViewportConfigurations =
				columnLayoutStructureItem.getViewportConfigurations();

			if (!columnViewportConfigurations.containsKey(viewportSizeId)) {
				continue;
			}

			JSONObject columnViewportConfigurationJSONObject =
				columnViewportConfigurations.get(viewportSizeId);

			if (!columnViewportConfigurationJSONObject.has("size") &&
				!updateEmpty) {

				continue;
			}

			if (columnViewportConfigurationJSONObject.has("size") &&
				!updateEmpty &&
				GetterUtil.getBoolean(
					PropsUtil.get("feature.flag.LPS-119551")) &&
				Objects.equals(
					ViewportSize.PORTRAIT_MOBILE.getViewportSizeId(),
					viewportSizeId)) {

				columnViewportConfigurationJSONObject.remove("size");

				continue;
			}

			columnViewportConfigurationJSONObject.put("size", columnSize);
		}
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

	private void _updateNumberOfColumns(
		RowStyledLayoutStructureItem rowStyledLayoutStructureItem,
		String viewportSizeId, int numberOfColumns) {

		Map<String, JSONObject> rowViewportConfigurations =
			rowStyledLayoutStructureItem.getViewportConfigurations();

		JSONObject viewportConfigurationJSONObject =
			rowViewportConfigurations.getOrDefault(
				viewportSizeId, JSONFactoryUtil.createJSONObject());

		viewportConfigurationJSONObject.put("numberOfColumns", numberOfColumns);

		if (GetterUtil.getBoolean(PropsUtil.get("feature.flag.LPS-119551")) &&
			Objects.equals(
				ViewportSize.MOBILE_LANDSCAPE.getViewportSizeId(),
				viewportSizeId)) {

			viewportConfigurationJSONObject.put("modulesPerRow", 1);
		}
		else if (GetterUtil.getBoolean(
					PropsUtil.get("feature.flag.LPS-119551")) &&
				 Objects.equals(
					 ViewportSize.PORTRAIT_MOBILE.getViewportSizeId(),
					 viewportSizeId) &&
				 viewportConfigurationJSONObject.has("modulesPerRow")) {

			viewportConfigurationJSONObject.remove("modulesPerRow");
		}
		else if (viewportConfigurationJSONObject.has("modulesPerRow")) {
			viewportConfigurationJSONObject.put(
				"modulesPerRow", numberOfColumns);
		}

		_updateColumnSizes(
			rowStyledLayoutStructureItem, viewportSizeId, numberOfColumns,
			false);
	}

	private static final int _MAX_COLUMNS = 12;

	private static final int[][][] _MODULE_SIZES = {
		{{12}}, {{12}}, {{12}, {6, 6}}, {{12}, {6, 6, 4, 4, 4}},
		{{12}, {6, 6}, {4, 4, 4}}, {}, {}, {}, {}, {},
		{{12}, {6, 6}, {4, 4, 4}, {}, {}, {2, 2, 2, 2, 2, 2}}
	};

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutStructure.class);

	private final Map<String, DeletedLayoutStructureItem>
		_deletedLayoutStructureItems;
	private final Map<Long, LayoutStructureItem> _fragmentLayoutStructureItems;
	private final Map<String, LayoutStructureItem> _layoutStructureItems;
	private String _mainItemId;

}