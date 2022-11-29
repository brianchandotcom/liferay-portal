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

package com.liferay.layout.internal.importer.structure.util;

import com.liferay.headless.delivery.dto.v1_0.PageElement;
import com.liferay.layout.internal.importer.LayoutStructureItemImporterContext;
import com.liferay.layout.util.structure.FragmentDropZoneLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jürgen Kappler
 */
@Component(service = LayoutStructureItemImporter.class)
public class FragmentDropZoneLayoutStructureItemImporter
	extends BaseLayoutStructureItemImporter
	implements LayoutStructureItemImporter {

	@Override
	public LayoutStructureItem addLayoutStructureItem(
			LayoutStructure layoutStructure,
			LayoutStructureItemImporterContext
				layoutStructureItemImporterContext,
			PageElement pageElement, Set<String> warningMessages)
		throws Exception {

		FragmentDropZoneLayoutStructureItem
			fragmentDropZoneLayoutStructureItem =
				(FragmentDropZoneLayoutStructureItem)
					layoutStructure.addFragmentDropZoneLayoutStructureItem(
						layoutStructureItemImporterContext.getParentItemId(),
						layoutStructureItemImporterContext.getPosition());

		Map<String, Object> definitionMap = getDefinitionMap(
			pageElement.getDefinition());

		if ((definitionMap == null) ||
			!definitionMap.containsKey("fragmentDropZoneId")) {

			return fragmentDropZoneLayoutStructureItem;
		}

		fragmentDropZoneLayoutStructureItem.setFragmentDropZoneId(
			GetterUtil.getString(definitionMap.get("fragmentDropZoneId")));

		return fragmentDropZoneLayoutStructureItem;
	}

	@Override
	public PageElement.Type getPageElementType() {
		return PageElement.Type.FRAGMENT_DROP_ZONE;
	}

}