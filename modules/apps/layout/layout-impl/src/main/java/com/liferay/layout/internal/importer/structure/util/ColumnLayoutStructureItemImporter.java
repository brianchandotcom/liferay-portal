/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.importer.structure.util;

import com.liferay.headless.delivery.dto.v1_0.PageElement;
import com.liferay.layout.internal.importer.LayoutStructureItemImporterContext;
import com.liferay.layout.util.structure.ColumnLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jürgen Kappler
 */
@Component(service = LayoutStructureItemImporter.class)
public class ColumnLayoutStructureItemImporter
	extends BaseLayoutStructureItemImporter
	implements LayoutStructureItemImporter {

	@Override
	public LayoutStructureItem addLayoutStructureItem(
			LayoutStructure layoutStructure,
			LayoutStructureItemImporterContext
				layoutStructureItemImporterContext,
			PageElement pageElement, Set<String> warningMessages)
		throws Exception {

		ColumnLayoutStructureItem columnLayoutStructureItem =
			(ColumnLayoutStructureItem)
				layoutStructure.addColumnLayoutStructureItem(
					layoutStructureItemImporterContext.getParentItemId(),
					layoutStructureItemImporterContext.getPosition());

		Map<String, Object> definitionMap = getDefinitionMap(
			pageElement.getDefinition());

		if (definitionMap == null) {
			return columnLayoutStructureItem;
		}

		columnLayoutStructureItem.setSize((Integer)definitionMap.get("size"));

		if (definitionMap.containsKey("columnViewports")) {
			List<Map<String, Object>> columnViewports =
				(List<Map<String, Object>>)definitionMap.get("columnViewports");

			for (Map<String, Object> columnViewport : columnViewports) {
				_processColumnViewportDefinition(
					columnLayoutStructureItem,
					(Map<String, Object>)columnViewport.get(
						"columnViewportDefinition"),
					(String)columnViewport.get("id"));
			}
		}
		else if (definitionMap.containsKey("columnViewportConfig")) {
			Map<String, Object> columnViewportConfigurations =
				(Map<String, Object>)definitionMap.get("columnViewportConfig");

			for (Map.Entry<String, Object> entry :
					columnViewportConfigurations.entrySet()) {

				_processColumnViewportDefinition(
					columnLayoutStructureItem,
					(Map<String, Object>)entry.getValue(), entry.getKey());
			}
		}

		return columnLayoutStructureItem;
	}

	@Override
	public PageElement.Type getPageElementType() {
		return PageElement.Type.COLUMN;
	}

	private void _processColumnViewportDefinition(
		ColumnLayoutStructureItem columnLayoutStructureItem,
		Map<String, Object> columnViewportDefinitionMap,
		String columnViewportId) {

		columnLayoutStructureItem.setViewportConfiguration(
			columnViewportId,
			JSONUtil.put(
				"size",
				() -> {
					if (columnViewportDefinitionMap.containsKey("size")) {
						return GetterUtil.getInteger(
							columnViewportDefinitionMap.get("size"));
					}

					return null;
				}));
	}

}