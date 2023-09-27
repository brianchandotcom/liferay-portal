/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.importer.structure.util;

import com.liferay.headless.delivery.dto.v1_0.PageElement;
import com.liferay.layout.internal.importer.LayoutStructureItemImporterContext;
import com.liferay.layout.util.constants.LayoutDataItemTypeConstants;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.layout.util.structure.RowStyledLayoutStructureItem;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.GetterUtil;

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

		if (definitionMap.containsKey("cssClasses")) {
			List<String> cssClasses = (List<String>)definitionMap.get(
				"cssClasses");

			rowStyledLayoutStructureItem.setCssClasses(
				new HashSet<>(cssClasses));
		}

		if (definitionMap.containsKey("customCSS")) {
			rowStyledLayoutStructureItem.setCustomCSS(
				String.valueOf(definitionMap.get("customCSS")));
		}

		if (definitionMap.containsKey("customCSSViewports")) {
			List<Map<String, Object>> customCSSViewports =
				(List<Map<String, Object>>)definitionMap.get(
					"customCSSViewports");

			for (Map<String, Object> customCSSViewport : customCSSViewports) {
				rowStyledLayoutStructureItem.setCustomCSSViewport(
					(String)customCSSViewport.get("id"),
					(String)customCSSViewport.get("customCSS"));
			}
		}

		rowStyledLayoutStructureItem.setGutters(
			(Boolean)definitionMap.get("gutters"));

		if (definitionMap.containsKey("indexed")) {
			rowStyledLayoutStructureItem.setIndexed(
				GetterUtil.getBoolean(definitionMap.get("indexed")));
		}

		if (definitionMap.containsKey("name")) {
			rowStyledLayoutStructureItem.setName(
				GetterUtil.getString(definitionMap.get("name")));
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