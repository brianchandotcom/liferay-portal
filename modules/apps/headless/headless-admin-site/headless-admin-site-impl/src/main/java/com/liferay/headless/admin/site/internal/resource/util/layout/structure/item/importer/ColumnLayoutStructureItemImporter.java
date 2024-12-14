/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.resource.util.layout.structure.item.importer;

import com.liferay.headless.admin.site.dto.v1_0.PageColumnDefinition;
import com.liferay.headless.admin.site.dto.v1_0.PageElement;
import com.liferay.headless.admin.site.internal.resource.util.layout.structure.LayoutStructureUtil;
import com.liferay.headless.admin.site.internal.resource.util.layout.structure.item.importer.context.LayoutStructureItemImporterContext;
import com.liferay.layout.util.structure.ColumnLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;

/**
 * @author Eudaldo Alonso
 */
public class ColumnLayoutStructureItemImporter
	implements LayoutStructureItemImporter {

	@Override
	public LayoutStructureItem addLayoutStructureItem(
			LayoutStructure layoutStructure,
			LayoutStructureItemImporterContext
				layoutStructureItemImporterContext,
			PageElement pageElement)
		throws Exception {

		ColumnLayoutStructureItem columnLayoutStructureItem =
			(ColumnLayoutStructureItem)
				layoutStructure.addColumnLayoutStructureItem(
					pageElement.getExternalReferenceCode(),
					LayoutStructureUtil.getParentExternalReferenceCode(
						pageElement, layoutStructure),
					pageElement.getPosition());

		PageColumnDefinition pageColumnDefinition =
			(PageColumnDefinition)pageElement.getDefinition();

		if (pageColumnDefinition == null) {
			return columnLayoutStructureItem;
		}

		columnLayoutStructureItem.setSize(pageColumnDefinition.getSize());

		return columnLayoutStructureItem;
	}

}