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

package com.liferay.layout.page.template.internal.importer.helper;

import com.liferay.headless.delivery.dto.v1_0.PageElement;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.util.structure.ContainerLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;

import java.util.Map;

/**
 * @author Jürgen Kappler
 */
public class ContainerLayoutStructureItemHelper
	implements LayoutStructureItemHelper {

	@Override
	public LayoutStructureItem addLayoutStructureItem(
		LayoutPageTemplateEntry layoutPageTemplateEntry,
		LayoutStructure layoutStructure, PageElement pageElement,
		String parentItemId, int position) {

		ContainerLayoutStructureItem containerLayoutStructureItem =
			(ContainerLayoutStructureItem)
				layoutStructure.addContainerLayoutStructureItem(
					parentItemId, position);

		Map<String, Object> definitionMap =
			(Map<String, Object>)pageElement.getDefinition();

		if (definitionMap != null) {
			containerLayoutStructureItem.setBackgroundColorCssClass(
				(String)definitionMap.get("backgroundColorCssClass"));

			Map<String, Object> backgroundImageMap =
				(Map<String, Object>)definitionMap.get("backgroundImage");

			if (backgroundImageMap != null) {
				JSONObject jsonObject = JSONUtil.put(
					"title", backgroundImageMap.get("title")
				).put(
					"url", backgroundImageMap.get("url")
				);

				containerLayoutStructureItem.setBackgroundImageJSONObject(
					jsonObject);
			}

			Map<String, Object> layout = (Map<String, Object>)definitionMap.get(
				"layout");

			if (layout != null) {
				containerLayoutStructureItem.setPaddingBottom(
					(Integer)layout.get("paddingBottom"));

				containerLayoutStructureItem.setPaddingHorizontal(
					(Integer)layout.get("paddingHorizontal"));

				containerLayoutStructureItem.setPaddingTop(
					(Integer)layout.get("paddingTop"));
			}
		}

		return containerLayoutStructureItem;
	}

}