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

package com.liferay.layout.page.template.internal.importer.helper;

import com.liferay.headless.delivery.dto.v1_0.PageElement;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.util.constants.LayoutDataItemTypeConstants;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.layout.util.structure.RowLayoutStructureItem;

import java.util.Map;

/**
 * @author Jürgen Kappler
 */
public class RowLayoutStructureItemHelper implements LayoutStructureItemHelper {

	@Override
	public LayoutStructureItem addLayoutStructureItem(
		LayoutPageTemplateEntry layoutPageTemplateEntry,
		LayoutStructure layoutStructure, PageElement pageElement,
		String parentItemId, int position) {

		RowLayoutStructureItem rowLayoutStructureItem =
			(RowLayoutStructureItem)layoutStructure.addLayoutStructureItem(
				LayoutDataItemTypeConstants.TYPE_ROW, parentItemId, position);

		Map<String, Object> definitionMap =
			(Map<String, Object>)pageElement.getDefinition();

		if (definitionMap != null) {
			rowLayoutStructureItem.setGutters(
				(Boolean)definitionMap.get("gutters"));

			rowLayoutStructureItem.setNumberOfColumns(
				(Integer)definitionMap.get("numberOfColumns"));
		}

		return rowLayoutStructureItem;
	}

}