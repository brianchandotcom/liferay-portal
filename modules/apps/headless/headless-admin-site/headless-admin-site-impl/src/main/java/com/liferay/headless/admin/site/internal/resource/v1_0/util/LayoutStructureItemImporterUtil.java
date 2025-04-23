/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.resource.v1_0.util;

import com.liferay.headless.admin.site.dto.v1_0.PageDefinition;
import com.liferay.headless.admin.site.internal.resource.v1_0.layout.structure.item.importer.CollectionItemLayoutStructureItemImporter;
import com.liferay.headless.admin.site.internal.resource.v1_0.layout.structure.item.importer.CollectionLayoutStructureItemImporter;
import com.liferay.headless.admin.site.internal.resource.v1_0.layout.structure.item.importer.ColumnLayoutStructureItemImporter;
import com.liferay.headless.admin.site.internal.resource.v1_0.layout.structure.item.importer.ContainerLayoutStructureItemImporter;
import com.liferay.headless.admin.site.internal.resource.v1_0.layout.structure.item.importer.DropZoneLayoutStructureItemImporter;
import com.liferay.headless.admin.site.internal.resource.v1_0.layout.structure.item.importer.FormLayoutStructureItemImporter;
import com.liferay.headless.admin.site.internal.resource.v1_0.layout.structure.item.importer.FormStepContainerLayoutStructureItemImporter;
import com.liferay.headless.admin.site.internal.resource.v1_0.layout.structure.item.importer.FormStepItemLayoutStructureItemImporter;
import com.liferay.headless.admin.site.internal.resource.v1_0.layout.structure.item.importer.FragmentDropZoneLayoutStructureItemImporter;
import com.liferay.headless.admin.site.internal.resource.v1_0.layout.structure.item.importer.FragmentLayoutStructureItemImporter;
import com.liferay.headless.admin.site.internal.resource.v1_0.layout.structure.item.importer.LayoutStructureItemImporter;
import com.liferay.headless.admin.site.internal.resource.v1_0.layout.structure.item.importer.RowLayoutStructureItemImporter;

import java.util.EnumMap;

/**
 * @author Lourdes Fernández Besada
 */
public class LayoutStructureItemImporterUtil {

	public static LayoutStructureItemImporter getLayoutStructureItemImporter(
		PageDefinition pageDefinition) {

		return _layoutStructureItemImporters.get(pageDefinition.getType());
	}

	private static final EnumMap
		<PageDefinition.Type, LayoutStructureItemImporter>
			_layoutStructureItemImporters;

	static {
		_layoutStructureItemImporters = new EnumMap<>(
			PageDefinition.Type.class);

		_layoutStructureItemImporters.put(
			PageDefinition.Type.COLLECTION,
			new CollectionLayoutStructureItemImporter());
		_layoutStructureItemImporters.put(
			PageDefinition.Type.COLLECTION_ITEM,
			new CollectionItemLayoutStructureItemImporter());
		_layoutStructureItemImporters.put(
			PageDefinition.Type.COLUMN,
			new ColumnLayoutStructureItemImporter());
		_layoutStructureItemImporters.put(
			PageDefinition.Type.CONTAINER,
			new ContainerLayoutStructureItemImporter());
		_layoutStructureItemImporters.put(
			PageDefinition.Type.DROP_ZONE,
			new DropZoneLayoutStructureItemImporter());
		_layoutStructureItemImporters.put(
			PageDefinition.Type.FORM, new FormLayoutStructureItemImporter());
		_layoutStructureItemImporters.put(
			PageDefinition.Type.FORM_STEP,
			new FormStepItemLayoutStructureItemImporter());
		_layoutStructureItemImporters.put(
			PageDefinition.Type.FORM_STEP_CONTAINER,
			new FormStepContainerLayoutStructureItemImporter());
		_layoutStructureItemImporters.put(
			PageDefinition.Type.FRAGMENT_DROP_ZONE,
			new FragmentDropZoneLayoutStructureItemImporter());
		_layoutStructureItemImporters.put(
			PageDefinition.Type.FRAGMENT,
			new FragmentLayoutStructureItemImporter());
		_layoutStructureItemImporters.put(
			PageDefinition.Type.ROW, new RowLayoutStructureItemImporter());
	}

}