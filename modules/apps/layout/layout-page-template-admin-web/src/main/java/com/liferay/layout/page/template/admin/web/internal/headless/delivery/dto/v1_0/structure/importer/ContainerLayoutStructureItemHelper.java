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
import com.liferay.layout.page.template.util.PaddingConverter;
import com.liferay.layout.util.structure.ContainerLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;

/**
 * @author Jürgen Kappler
 */
public class ContainerLayoutStructureItemHelper
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

		ContainerLayoutStructureItem containerLayoutStructureItem =
			(ContainerLayoutStructureItem)
				layoutStructure.addContainerLayoutStructureItem(
					parentItemId, position);

		Map<String, Object> definitionMap = getDefinitionMap(
			pageElement.getDefinition());

		if (definitionMap != null) {
			containerLayoutStructureItem.setBackgroundColorCssClass(
				(String)definitionMap.get("backgroundColor"));

			Map<String, Object> backgroundImageMap =
				(Map<String, Object>)definitionMap.get("backgroundImage");

			if (backgroundImageMap != null) {
				JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

				Map<String, Object> titleMap =
					(Map<String, Object>)backgroundImageMap.get("title");

				if (titleMap != null) {
					jsonObject.put("title", _getLocalizedValue(titleMap));
				}

				Map<String, Object> urlMap =
					(Map<String, Object>)backgroundImageMap.get("url");

				if (urlMap != null) {
					jsonObject.put("url", _getLocalizedValue(urlMap));

					_processMapping(
						jsonObject, (Map<String, String>)urlMap.get("mapping"));
				}

				containerLayoutStructureItem.setBackgroundImageJSONObject(
					jsonObject);
			}

			Map<String, Object> containerLayout =
				(Map<String, Object>)definitionMap.get("layout");

			if (layout != null) {
				containerLayoutStructureItem.setContainerType(
					StringUtil.toLowerCase(
						(String)containerLayout.get("containerType")));
				containerLayoutStructureItem.setPaddingBottom(
					PaddingConverter.convertToInternalValue(
						(Integer)containerLayout.get("paddingBottom")));
				containerLayoutStructureItem.setPaddingHorizontal(
					PaddingConverter.convertToInternalValue(
						(Integer)containerLayout.get("paddingHorizontal")));
				containerLayoutStructureItem.setPaddingTop(
					PaddingConverter.convertToInternalValue(
						(Integer)containerLayout.get("paddingTop")));
			}
		}

		return containerLayoutStructureItem;
	}

	private Object _getLocalizedValue(Map<String, Object> map) {
		Map<String, Object> localizedValuesMap = (Map<String, Object>)map.get(
			"value_i18n");

		if (localizedValuesMap != null) {
			JSONObject localizedValueJSONObject =
				JSONFactoryUtil.createJSONObject();

			for (Map.Entry<String, Object> entry :
					localizedValuesMap.entrySet()) {

				localizedValueJSONObject.put(entry.getKey(), entry.getValue());
			}

			return localizedValueJSONObject;
		}

		return map.get("value");
	}

	private void _processMapping(
		JSONObject jsonObject, Map<String, String> map) {

		if (map != null) {
			String fieldKey = map.get("fieldKey");

			if (Validator.isNull(fieldKey)) {
				return;
			}

			String itemKey = map.get("itemKey");

			if (Validator.isNull(itemKey)) {
				jsonObject.put("mappedField", fieldKey);

				return;
			}

			String[] itemKeyParts = itemKey.split(StringPool.POUND);

			if (itemKeyParts.length == 2) {
				jsonObject.put(
					"classNameId", itemKeyParts[0]
				).put(
					"classPK", itemKeyParts[1]
				).put(
					"fieldId", fieldKey
				);
			}
		}
	}

}