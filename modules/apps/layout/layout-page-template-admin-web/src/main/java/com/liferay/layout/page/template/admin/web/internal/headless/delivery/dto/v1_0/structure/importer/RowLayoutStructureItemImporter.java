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
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.util.PropsUtil;

import java.util.HashSet;
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
			LayoutStructure layoutStructure,
			LayoutStructureItemImporterContext
				layoutStructureItemImporterContext,
			PageElement pageElement, Set<String> warningMessages)
		throws Exception {

		RowStyledLayoutStructureItem rowStyledLayoutStructureItem =
			(RowStyledLayoutStructureItem)
				layoutStructure.addLayoutStructureItem(
					LayoutDataItemTypeConstants.TYPE_ROW,
					layoutStructureItemImporterContext.getParentItemId(),
					layoutStructureItemImporterContext.getPosition());

		Map<String, Object> definitionMap = getDefinitionMap(
			pageElement.getDefinition());

		if (definitionMap == null) {
			return rowStyledLayoutStructureItem;
		}

		if (GetterUtil.getBoolean(PropsUtil.get("feature.flag.LPS-147511")) &&
			definitionMap.containsKey("cssClasses")) {

			List<String> cssClasses = (List<String>)definitionMap.get(
				"cssClasses");

			rowStyledLayoutStructureItem.setCssClasses(
				new HashSet<>(cssClasses));
		}

		rowStyledLayoutStructureItem.setGutters(
			(Boolean)definitionMap.get("gutters"));

		if (definitionMap.containsKey("indexed")) {
			rowStyledLayoutStructureItem.setIndexed(
				GetterUtil.getBoolean(definitionMap.get("indexed")));
		}

		rowStyledLayoutStructureItem.setNumberOfColumns(
			(Integer)definitionMap.get("numberOfColumns"));

		if (definitionMap.containsKey("reverseOrder")) {
			rowStyledLayoutStructureItem.setModulesPerRow(
				(Integer)definitionMap.get("modulesPerRow"));
			rowStyledLayoutStructureItem.setReverseOrder(
				(Boolean)definitionMap.get("reverseOrder"));
		}

		if (definitionMap.containsKey("verticalAlignment")) {
			rowStyledLayoutStructureItem.setVerticalAlignment(
				(String)definitionMap.get("verticalAlignment"));
		}

		if (definitionMap.containsKey("rowViewports")) {
			List<Map<String, Object>> rowViewports =
				(List<Map<String, Object>>)definitionMap.get("rowViewports");

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

		Map<String, Object> fragmentStyleMap =
			(Map<String, Object>)definitionMap.get("fragmentStyle");

		if (fragmentStyleMap != null) {
			JSONObject jsonObject = JSONUtil.put(
				"styles",
				toStylesJSONObject(
					layoutStructureItemImporterContext, fragmentStyleMap));

			rowStyledLayoutStructureItem.updateItemConfig(jsonObject);
		}

		if (definitionMap.containsKey("fragmentViewports")) {
			List<Map<String, Object>> fragmentViewports =
				(List<Map<String, Object>>)definitionMap.get(
					"fragmentViewports");

			for (Map<String, Object> fragmentViewport : fragmentViewports) {
				JSONObject jsonObject = JSONUtil.put(
					(String)fragmentViewport.get("id"),
					toFragmentViewportStylesJSONObject(fragmentViewport));

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

		rowStyledLayoutStructureItem.setViewportConfiguration(
			rowViewportId,
			JSONUtil.put(
				"modulesPerRow",
				() -> {
					if (rowViewportDefinitionMap.containsKey("modulesPerRow")) {
						return GetterUtil.getInteger(
							rowViewportDefinitionMap.get("modulesPerRow"));
					}

					return null;
				}
			).put(
				"reverseOrder",
				() -> {
					if (rowViewportDefinitionMap.containsKey("reverseOrder")) {
						return GetterUtil.getBoolean(
							rowViewportDefinitionMap.get("reverseOrder"));
					}

					return null;
				}
			).put(
				"verticalAlignment",
				() -> {
					if (rowViewportDefinitionMap.containsKey(
							"verticalAlignment")) {

						return GetterUtil.getString(
							rowViewportDefinitionMap.get("verticalAlignment"));
					}

					return null;
				}
			));
	}

}