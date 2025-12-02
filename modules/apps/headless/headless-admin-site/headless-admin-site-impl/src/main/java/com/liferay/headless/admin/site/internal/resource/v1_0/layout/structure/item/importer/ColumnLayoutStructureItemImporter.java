/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.resource.v1_0.layout.structure.item.importer;

import com.liferay.headless.admin.site.dto.v1_0.ModulePageElementDefinition;
import com.liferay.headless.admin.site.dto.v1_0.ModuleViewport;
import com.liferay.headless.admin.site.dto.v1_0.ModuleViewportDefinition;
import com.liferay.headless.admin.site.dto.v1_0.PageElement;
import com.liferay.headless.admin.site.internal.dto.v1_0.util.ViewportIdUtil;
import com.liferay.headless.admin.site.internal.resource.v1_0.layout.structure.item.importer.context.LayoutStructureItemImporterContext;
import com.liferay.headless.admin.site.internal.resource.v1_0.util.LayoutStructureUtil;
import com.liferay.layout.responsive.ViewportSize;
import com.liferay.layout.util.structure.ColumnLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.Objects;

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

		ModulePageElementDefinition modulePageElementDefinition =
			(ModulePageElementDefinition)pageElement.getPageElementDefinition();

		if (modulePageElementDefinition == null) {
			return columnLayoutStructureItem;
		}

		ModuleViewport[] moduleViewports =
			modulePageElementDefinition.getModuleViewports();

		if (ArrayUtil.isEmpty(moduleViewports)) {
			columnLayoutStructureItem.setViewportConfiguration(
				ViewportSize.MOBILE_LANDSCAPE.getViewportSizeId(),
				JSONUtil.put("size", 12));
		}
		else {
			_updateItemConfig(
				columnLayoutStructureItem, JSONUtil.put("size", 4),
				ModuleViewport.Id.DESKTOP, moduleViewports);
			_updateItemConfig(
				columnLayoutStructureItem, JSONUtil.put("size", 12),
				ModuleViewport.Id.LANDSCAPE_MOBILE, moduleViewports);
			_updateItemConfig(
				columnLayoutStructureItem, JSONFactoryUtil.createJSONObject(),
				ModuleViewport.Id.PORTRAIT_MOBILE, moduleViewports);
			_updateItemConfig(
				columnLayoutStructureItem, JSONFactoryUtil.createJSONObject(),
				ModuleViewport.Id.TABLET, moduleViewports);
		}

		return columnLayoutStructureItem;
	}

	private ModuleViewport _getModuleViewport(
		ModuleViewport.Id moduleViewportId, ModuleViewport[] moduleViewports) {

		for (ModuleViewport moduleViewport : moduleViewports) {
			if (Objects.equals(moduleViewportId, moduleViewport.getId())) {
				return moduleViewport;
			}
		}

		return null;
	}

	private JSONObject _toViewportJSONObject(ModuleViewport moduleViewport) {
		if (moduleViewport == null) {
			return JSONFactoryUtil.createJSONObject();
		}

		ModuleViewportDefinition moduleViewportDefinition =
			moduleViewport.getModuleViewportDefinition();

		if (moduleViewportDefinition.getSize() == null) {
			return JSONFactoryUtil.createJSONObject();
		}

		return JSONUtil.put("size", moduleViewportDefinition.getSize());
	}

	private void _updateItemConfig(
		ColumnLayoutStructureItem columnLayoutStructureItem,
		JSONObject defaultViewportJSONObject,
		ModuleViewport.Id moduleViewportId, ModuleViewport[] moduleViewports) {

		ModuleViewport moduleViewport = _getModuleViewport(
			moduleViewportId, moduleViewports);

		JSONObject viewportJSONObject = defaultViewportJSONObject;

		if (moduleViewport != null) {
			viewportJSONObject = _toViewportJSONObject(moduleViewport);
		}

		if (!Objects.equals(moduleViewportId, ModuleViewport.Id.DESKTOP)) {
			viewportJSONObject = JSONUtil.put(
				ViewportIdUtil.toInternalValue(moduleViewportId.getValue()),
				viewportJSONObject);
		}

		columnLayoutStructureItem.updateItemConfig(viewportJSONObject);
	}

}