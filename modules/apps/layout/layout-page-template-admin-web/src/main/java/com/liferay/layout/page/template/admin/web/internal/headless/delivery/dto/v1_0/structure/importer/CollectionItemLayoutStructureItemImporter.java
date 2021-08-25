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

package com.liferay.layout.page.template.admin.web.internal.headless.delivery.dto.v1_0.structure.importer;

import com.liferay.headless.delivery.dto.v1_0.PageElement;
import com.liferay.layout.util.constants.LayoutDataItemTypeConstants;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;

import java.util.Objects;
import java.util.Set;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jürgen Kappler
 */
@Component(service = LayoutStructureItemImporter.class)
public class CollectionItemLayoutStructureItemImporter
	implements LayoutStructureItemImporter {

	@Override
	public LayoutStructureItem addLayoutStructureItem(
			LayoutStructure layoutStructure,
			LayoutStructureItemImporterContext
				layoutStructureItemImporterContext,
			PageElement pageElement, Set<String> warningMessages)
		throws Exception {

		LayoutStructureItem layoutStructureItem =
			layoutStructure.getLayoutStructureItem(
				layoutStructureItemImporterContext.getParentItemId());

		for (String childItemId : layoutStructureItem.getChildrenItemIds()) {
			LayoutStructureItem childLayoutStructureItem =
				layoutStructure.getLayoutStructureItem(childItemId);

			if (Objects.equals(
					childLayoutStructureItem.getItemType(),
					LayoutDataItemTypeConstants.TYPE_COLLECTION_ITEM)) {

				return childLayoutStructureItem;
			}
		}

		return layoutStructure.addCollectionItemLayoutStructureItem(
			layoutStructureItemImporterContext.getParentItemId(),
			layoutStructureItemImporterContext.getPosition());
	}

	@Override
	public PageElement.Type getPageElementType() {
		return PageElement.Type.COLLECTION_ITEM;
	}

}