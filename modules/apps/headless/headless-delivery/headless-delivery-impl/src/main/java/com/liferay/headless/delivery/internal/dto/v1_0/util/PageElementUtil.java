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

package com.liferay.headless.delivery.internal.dto.v1_0.util;

import com.liferay.headless.delivery.dto.v1_0.PageElement;
import com.liferay.headless.delivery.internal.dto.v1_0.mapper.LayoutStructureItemMapper;
import com.liferay.headless.delivery.internal.dto.v1_0.mapper.LayoutStructureItemMapperTracker;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jürgen Kappler
 * @author Javier de Arcos
 */
public class PageElementUtil {

	public static PageElement toPageElement(
		long groupId, LayoutStructure layoutStructure,
		LayoutStructureItem layoutStructureItem,
		LayoutStructureItemMapperTracker layoutStructureItemMapperTracker,
		boolean saveInlineContent, boolean saveMappingConfiguration) {

		List<PageElement> pageElements = new ArrayList<>();

		List<String> childrenItemIds = layoutStructureItem.getChildrenItemIds();

		for (String childItemId : childrenItemIds) {
			LayoutStructureItem childLayoutStructureItem =
				layoutStructure.getLayoutStructureItem(childItemId);

			List<String> grandChildrenItemIds =
				childLayoutStructureItem.getChildrenItemIds();

			if (grandChildrenItemIds.isEmpty()) {
				pageElements.add(
					_toPageElement(
						groupId, childLayoutStructureItem,
						layoutStructureItemMapperTracker, saveInlineContent,
						saveMappingConfiguration));
			}
			else {
				pageElements.add(
					toPageElement(
						groupId, layoutStructure, childLayoutStructureItem,
						layoutStructureItemMapperTracker, saveInlineContent,
						saveMappingConfiguration));
			}
		}

		PageElement pageElement = _toPageElement(
			groupId, layoutStructureItem, layoutStructureItemMapperTracker,
			saveInlineContent, saveMappingConfiguration);

		if ((pageElement != null) && !pageElements.isEmpty()) {
			pageElement.setPageElements(
				pageElements.toArray(new PageElement[0]));
		}

		return pageElement;
	}

	private static PageElement _toPageElement(
		long groupId, LayoutStructureItem layoutStructureItem,
		LayoutStructureItemMapperTracker layoutStructureItemMapperTracker,
		boolean saveInlineContent, boolean saveMappingConfiguration) {

		Class<?> clazz = layoutStructureItem.getClass();

		LayoutStructureItemMapper layoutStructureItemMapper =
			layoutStructureItemMapperTracker.getLayoutStructureItemMapper(
				clazz.getName());

		if (layoutStructureItemMapper == null) {
			return null;
		}

		return layoutStructureItemMapper.getPageElement(
			groupId, layoutStructureItem, saveInlineContent,
			saveMappingConfiguration);
	}

}