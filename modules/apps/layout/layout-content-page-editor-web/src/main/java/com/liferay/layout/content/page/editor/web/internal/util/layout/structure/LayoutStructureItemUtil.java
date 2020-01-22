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

import java.util.Objects;

/**
 * @author Eudaldo Alonso
 */
public class LayoutStructureItemUtil {

	public static LayoutStructureItem create(
		String itemType, String parentItemId) {

		if (Objects.equals(itemType, LayoutDataItemTypeConstants.TYPE_COLUMN)) {
			return new ColumnLayoutStructureItem(parentItemId);
		}

		if (Objects.equals(
				itemType, LayoutDataItemTypeConstants.TYPE_CONTAINER)) {

			return new ContainerLayoutStructureItem(parentItemId);
		}

		if (Objects.equals(
				itemType, LayoutDataItemTypeConstants.TYPE_DROP_ZONE)) {

			return new DropZoneLayoutStructureItem(parentItemId);
		}

		if (Objects.equals(
				itemType, LayoutDataItemTypeConstants.TYPE_FRAGMENT)) {

			return new FragmentLayoutStructureItem(parentItemId);
		}

		if (Objects.equals(itemType, LayoutDataItemTypeConstants.TYPE_ROOT)) {
			return new RootLayoutStructureItem();
		}

		if (Objects.equals(itemType, LayoutDataItemTypeConstants.TYPE_ROW)) {
			return new RowLayoutStructureItem(parentItemId);
		}

		return null;
	}

}