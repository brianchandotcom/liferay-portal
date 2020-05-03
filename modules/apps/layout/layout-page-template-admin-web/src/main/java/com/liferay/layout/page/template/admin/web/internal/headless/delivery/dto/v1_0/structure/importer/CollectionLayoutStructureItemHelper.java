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

import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
import com.liferay.fragment.processor.FragmentEntryProcessorRegistry;
import com.liferay.fragment.renderer.FragmentRendererTracker;
import com.liferay.fragment.validator.FragmentEntryValidator;
import com.liferay.headless.delivery.dto.v1_0.PageElement;
import com.liferay.layout.util.structure.CollectionLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Layout;

import java.util.Map;

/**
 * @author Jürgen Kappler
 */
public class CollectionLayoutStructureItemHelper
	extends BaseLayoutStructureItemHelper implements LayoutStructureItemHelper {

	@Override
	public LayoutStructureItem addLayoutStructureItem(
			FragmentCollectionContributorTracker
				fragmentCollectionContributorTracker,
			FragmentEntryProcessorRegistry fragmentEntryProcessorRegistry,
			FragmentEntryValidator fragmentEntryValidator,
			FragmentRendererTracker fragmentRendererTracker, Layout layout,
			LayoutStructure layoutStructure, PageElement pageElement,
			String parentItemId, int position)
		throws Exception {

		CollectionLayoutStructureItem collectionLayoutStructureItem =
			(CollectionLayoutStructureItem)
				layoutStructure.addCollectionLayoutStructureItem(
					parentItemId, position);

		Map<String, Object> definitionMap = getDefinitionMap(
			pageElement.getDefinition());

		if (definitionMap != null) {
			Map<String, Object> collectionConfig =
				(Map<String, Object>)definitionMap.get("collectionConfig");

			if (collectionConfig != null) {
				collectionLayoutStructureItem.setCollectionJSONObject(
					_getCollectionConfigAsJSONObject(collectionConfig));
			}

			collectionLayoutStructureItem.setNumberOfColumns(
				(Integer)definitionMap.get("numberOfColumns"));
			collectionLayoutStructureItem.setNumberOfColumns(
				(Integer)definitionMap.get("numberOfItems"));
		}

		return collectionLayoutStructureItem;
	}

	private JSONObject _getCollectionConfigAsJSONObject(
		Map<String, Object> collectionConfig) {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		for (Map.Entry<String, Object> entry : collectionConfig.entrySet()) {
			jsonObject.put(entry.getKey(), entry.getValue());
		}

		return jsonObject;
	}

}