/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		if (pageElement.getDefinition() == null) {
			return fragmentDropZoneLayoutStructureItem;
		}

		Map<String, Object> definitionMap = getDefinitionMap(
			pageElement.getDefinition());

		if ((definitionMap == null) ||
			!definitionMap.containsKey("fragmentDropZoneId")) {

			return fragmentDropZoneLayoutStructureItem;
		}

		fragmentDropZoneLayoutStructureItem.setFragmentDropZoneId(
			GetterUtil.getString(
				definitionMap.get("fragmentDropZoneId"), null));

		return fragmentDropZoneLayoutStructureItem;
	}

	@Override
	public PageElement.Type getPageElementType() {
		return PageElement.Type.FRAGMENT_DROP_ZONE;
	}

}