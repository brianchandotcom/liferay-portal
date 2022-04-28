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

import com.liferay.layout.util.constants.LayoutDataItemTypeConstants;

import java.util.Objects;

/**
 * @author Eudaldo Alonso
 */
public class LayoutStructureItemUtil {

	public static LayoutStructureItem create(
		String itemType, String parentItemId) {

		if (Objects.equals(
				itemType, LayoutDataItemTypeConstants.TYPE_COLLECTION)) {

			return new CollectionStyledLayoutStructureItem(parentItemId);
		}

		if (Objects.equals(
				itemType, LayoutDataItemTypeConstants.TYPE_COLLECTION_ITEM)) {

			return new CollectionItemLayoutStructureItem(parentItemId);
		}

		if (Objects.equals(itemType, LayoutDataItemTypeConstants.TYPE_COLUMN)) {
			return new ColumnLayoutStructureItem(parentItemId);
		}

		if (Objects.equals(
				itemType, LayoutDataItemTypeConstants.TYPE_CONTAINER)) {

			return new ContainerStyledLayoutStructureItem(parentItemId);
		}

		if (Objects.equals(
				itemType, LayoutDataItemTypeConstants.TYPE_DROP_ZONE)) {

			return new DropZoneLayoutStructureItem(parentItemId);
		}

		if (Objects.equals(itemType, LayoutDataItemTypeConstants.TYPE_FORM)) {
			return new FormStyledLayoutStructureItem(parentItemId);
		}

		if (Objects.equals(
				itemType, LayoutDataItemTypeConstants.TYPE_FRAGMENT)) {

			return new FragmentStyledLayoutStructureItem(parentItemId);
		}

		if (Objects.equals(
				itemType,
				LayoutDataItemTypeConstants.TYPE_FRAGMENT_DROP_ZONE)) {

			return new FragmentDropZoneLayoutStructureItem(parentItemId);
		}

		if (Objects.equals(itemType, LayoutDataItemTypeConstants.TYPE_ROOT)) {
			return new RootLayoutStructureItem();
		}

		if (Objects.equals(itemType, LayoutDataItemTypeConstants.TYPE_ROW)) {
			return new RowStyledLayoutStructureItem(parentItemId);
		}

		return null;
	}

	public static LayoutStructureItem getAncestor(
		String itemId, String itemType, LayoutStructure layoutStructure) {

		LayoutStructureItem layoutStructureItem =
			layoutStructure.getLayoutStructureItem(itemId);

		LayoutStructureItem parentLayoutStructureItem =
			layoutStructure.getLayoutStructureItem(
				layoutStructureItem.getParentItemId());

		if (parentLayoutStructureItem == null) {
			return null;
		}

		if (Objects.equals(parentLayoutStructureItem.getItemType(), itemType)) {
			return parentLayoutStructureItem;
		}

		if (Objects.equals(
				parentLayoutStructureItem.getItemType(),
				LayoutDataItemTypeConstants.TYPE_ROOT)) {

			return null;
		}

		return getAncestor(
			parentLayoutStructureItem.getItemId(), itemType, layoutStructure);
	}

	public static boolean hasAncestor(
		String itemId, String itemType, LayoutStructure layoutStructure) {

		LayoutStructureItem layoutStructureItem =
			layoutStructure.getLayoutStructureItem(itemId);

		LayoutStructureItem parentLayoutStructureItem =
			layoutStructure.getLayoutStructureItem(
				layoutStructureItem.getParentItemId());

		if (parentLayoutStructureItem == null) {
			return false;
		}

		if (Objects.equals(parentLayoutStructureItem.getItemType(), itemType)) {
			return true;
		}

		if (Objects.equals(
				parentLayoutStructureItem.getItemType(),
				LayoutDataItemTypeConstants.TYPE_ROOT)) {

			return false;
		}

		return hasAncestor(
			parentLayoutStructureItem.getItemId(), itemType, layoutStructure);
	}

}