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
import com.liferay.layout.util.structure.RowStyledLayoutStructureItem;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
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
public class RowLayoutStructureItemImporter
	extends BaseLayoutStructureItemImporter
	implements LayoutStructureItemImporter {

	@Override
	public LayoutStructureItem addLayoutStructureItem(
			Layout layout, LayoutStructure layoutStructure,
			PageElement pageElement, String parentItemId, int position,
			Set<String> warningMessages)
		throws Exception {

		RowStyledLayoutStructureItem rowStyledLayoutStructureItem =
			(RowStyledLayoutStructureItem)
				layoutStructure.addLayoutStructureItem(
					LayoutDataItemTypeConstants.TYPE_ROW, parentItemId,
					position);

		Map<String, Object> definitionMap = getDefinitionMap(
			pageElement.getDefinition());

		if (definitionMap != null) {
			rowStyledLayoutStructureItem.setGutters(
				(Boolean)definitionMap.get("gutters"));

			if (definitionMap.containsKey("reverseOrder")) {
				rowStyledLayoutStructureItem.setModulesPerRow(
					(Integer)definitionMap.get("modulesPerRow"));
			}

			rowStyledLayoutStructureItem.setNumberOfColumns(
				(Integer)definitionMap.get("numberOfColumns"));

			if (definitionMap.containsKey("reverseOrder")) {
				rowStyledLayoutStructureItem.setReverseOrder(
					(Boolean)definitionMap.get("reverseOrder"));
			}

			if (definitionMap.containsKey("verticalAlignment")) {
				rowStyledLayoutStructureItem.setVerticalAlignment(
					(String)definitionMap.get("verticalAlignment"));
			}

			if (definitionMap.containsKey("rowViewports")) {
				List<Map<String, Object>> rowViewports =
					(List<Map<String, Object>>)definitionMap.get(
						"rowViewports");

				for (Map<String, Object> rowViewport : rowViewports) {
					_processRowViewportDefinition(
						rowStyledLayoutStructureItem,
						(Map<String, Object>)rowViewport.get(
							"rowViewportDefinition"),
						(String)rowViewport.get("id"));
				}
			}
			else if (definitionMap.containsKey("rowViewportConfig")) {
				Map<String, Object> rowViewportConfigurations =
					(Map<String, Object>)definitionMap.get("rowViewportConfig");

				for (Map.Entry<String, Object> entry :
						rowViewportConfigurations.entrySet()) {

					_processRowViewportDefinition(
						rowStyledLayoutStructureItem,
						(Map<String, Object>)entry.getValue(), entry.getKey());
				}
			}

			Map<String, Object> styles = (Map<String, Object>)definitionMap.get(
				"styles");

			if (styles != null) {
				JSONObject jsonObject = JSONUtil.put(
					"styles", toStylesJSONObject(styles));

				rowStyledLayoutStructureItem.updateItemConfig(jsonObject);
			}
		}

		return rowStyledLayoutStructureItem;
	}

	@Override
	public PageElement.Type getPageElementType() {
		return PageElement.Type.ROW;
	}

	private void _processRowViewportDefinition(
		RowStyledLayoutStructureItem rowStyledLayoutStructureItem,
		Map<String, Object> rowViewportDefinitionMap, String rowViewportId) {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		if (rowViewportDefinitionMap.containsKey("modulesPerRow")) {
			jsonObject.put(
				"modulesPerRow",
				GetterUtil.getInteger(
					rowViewportDefinitionMap.get("modulesPerRow")));
		}

		if (rowViewportDefinitionMap.containsKey("reverseOrder")) {
			jsonObject.put(
				"reverseOrder",
				GetterUtil.getBoolean(
					rowViewportDefinitionMap.get("reverseOrder")));
		}

		if (rowViewportDefinitionMap.containsKey("verticalAlignment")) {
			jsonObject.put(
				"verticalAlignment",
				GetterUtil.getString(
					rowViewportDefinitionMap.get("verticalAlignment")));
		}

		rowStyledLayoutStructureItem.setViewportConfiguration(
			rowViewportId, jsonObject);
	}

}