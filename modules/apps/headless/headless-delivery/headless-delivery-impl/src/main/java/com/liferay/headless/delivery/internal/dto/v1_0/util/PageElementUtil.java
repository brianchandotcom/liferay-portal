/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.internal.dto.v1_0.util;

import com.liferay.headless.delivery.dto.v1_0.PageElement;
import com.liferay.headless.delivery.internal.dto.v1_0.mapper.LayoutStructureItemMapper;
import com.liferay.headless.delivery.internal.dto.v1_0.mapper.LayoutStructureItemMapperRegistry;
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
		LayoutStructureItemMapperRegistry layoutStructureItemMapperRegistry,
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
						layoutStructureItemMapperRegistry, saveInlineContent,
						saveMappingConfiguration));
			}
			else {
				pageElements.add(
					toPageElement(
						groupId, layoutStructure, childLayoutStructureItem,
						layoutStructureItemMapperRegistry, saveInlineContent,
						saveMappingConfiguration));
			}
		}

		PageElement pageElement = _toPageElement(
			groupId, layoutStructureItem, layoutStructureItemMapperRegistry,
			saveInlineContent, saveMappingConfiguration);

		if ((pageElement != null) && !pageElements.isEmpty()) {
			pageElement.setPageElements(
				pageElements.toArray(new PageElement[0]));
		}

		return pageElement;
	}

	private static PageElement _toPageElement(
		long groupId, LayoutStructureItem layoutStructureItem,
		LayoutStructureItemMapperRegistry layoutStructureItemMapperRegistry,
		boolean saveInlineContent, boolean saveMappingConfiguration) {

		Class<?> clazz = layoutStructureItem.getClass();

		LayoutStructureItemMapper layoutStructureItemMapper =
			layoutStructureItemMapperRegistry.getLayoutStructureItemMapper(
				clazz.getName());

		if (layoutStructureItemMapper == null) {
			return null;
		}

		return layoutStructureItemMapper.getPageElement(
			groupId, layoutStructureItem, saveInlineContent,
			saveMappingConfiguration);
	}

}