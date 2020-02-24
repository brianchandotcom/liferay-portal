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

package com.liferay.layout.page.template.internal.importer;

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
		String parentId, int position) {

		ContainerLayoutStructureItem containerLayoutStructureItem =
			(ContainerLayoutStructureItem)
				layoutStructure.addContainerLayoutStructureItem(
					parentId, position);

		Map<String, Object> definition =
			(Map<String, Object>)pageElement.getDefinition();

		if (definition != null) {
			containerLayoutStructureItem.setBackgroundColorCssClass(
				(String)definition.get("backgroundColorCssClass"));

			Map<String, Object> backgroundImage =
				(Map<String, Object>)definition.get("backgroundImage");

			if (backgroundImage != null) {
				JSONObject jsonObject = JSONUtil.put(
					"title", backgroundImage.get("title")
				).put(
					"url", backgroundImage.get("url")
				);

				containerLayoutStructureItem.setBackgroundImageJSONObject(
					jsonObject);
			}

			Map<String, Object> layout = (Map<String, Object>)definition.get(
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