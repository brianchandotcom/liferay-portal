/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.resource.v1_0.layout.structure.item.importer;

import com.liferay.headless.admin.site.dto.v1_0.ContainerPageElementDefinition;
import com.liferay.headless.admin.site.dto.v1_0.PageElement;
import com.liferay.headless.admin.site.internal.resource.v1_0.layout.structure.item.importer.context.LayoutStructureItemImporterContext;
import com.liferay.headless.admin.site.internal.resource.v1_0.util.LayoutStructureUtil;
import com.liferay.layout.util.structure.ContainerStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;

/**
 * @author Eudaldo Alonso
 */
public class ContainerLayoutStructureItemImporter
	implements LayoutStructureItemImporter {

	@Override
	public LayoutStructureItem addLayoutStructureItem(
			LayoutStructure layoutStructure,
			LayoutStructureItemImporterContext
				layoutStructureItemImporterContext,
			PageElement pageElement)
		throws Exception {

		ContainerStyledLayoutStructureItem containerStyledLayoutStructureItem =
			(ContainerStyledLayoutStructureItem)
				layoutStructure.addContainerStyledLayoutStructureItem(
					pageElement.getExternalReferenceCode(),
					LayoutStructureUtil.getParentExternalReferenceCode(
						pageElement, layoutStructure),
					pageElement.getPosition());

		ContainerPageElementDefinition containerPageElementDefinition =
			(ContainerPageElementDefinition)pageElement.getDefinition();

		if (containerPageElementDefinition == null) {
			return containerStyledLayoutStructureItem;
		}

		containerStyledLayoutStructureItem.setIndexed(
			containerPageElementDefinition.getIndexed());
		containerStyledLayoutStructureItem.setName(
			containerPageElementDefinition.getName());

		return containerStyledLayoutStructureItem;
	}

}