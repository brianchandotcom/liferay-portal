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
import com.liferay.layout.util.structure.ColumnLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Layout;
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
			Layout layout, LayoutStructure layoutStructure,
			PageElement pageElement, String parentItemId, int position,
			Set<String> warningMessages)
		throws Exception {

		ColumnLayoutStructureItem columnLayoutStructureItem =
			(ColumnLayoutStructureItem)
				layoutStructure.addColumnLayoutStructureItem(
					parentItemId, position);

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

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		if (columnViewportDefinitionMap.containsKey("size")) {
			jsonObject.put(
				"size",
				GetterUtil.getInteger(columnViewportDefinitionMap.get("size")));
		}

		columnLayoutStructureItem.setViewportConfiguration(
			columnViewportId, jsonObject);
	}

}