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

import com.liferay.headless.delivery.dto.v1_0.FragmentLink;
import com.liferay.headless.delivery.dto.v1_0.PageElement;
import com.liferay.layout.page.template.util.BorderRadiusConverter;
import com.liferay.layout.page.template.util.MarginConverter;
import com.liferay.layout.page.template.util.PaddingConverter;
import com.liferay.layout.page.template.util.ShadowConverter;
import com.liferay.layout.util.structure.ContainerStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jürgen Kappler
 */
@Component(service = LayoutStructureItemImporter.class)
public class ContainerLayoutStructureItemImporter
	extends BaseLayoutStructureItemImporter
	implements LayoutStructureItemImporter {

	@Override
	public LayoutStructureItem addLayoutStructureItem(
			Layout layout, LayoutStructure layoutStructure,
			PageElement pageElement, String parentItemId, int position,
			Set<String> warningMessages)
		throws Exception {

		ContainerStyledLayoutStructureItem containerStyledLayoutStructureItem =
			(ContainerStyledLayoutStructureItem)
				layoutStructure.addContainerLayoutStructureItem(
					parentItemId, position);

		JSONObject stylesJSONObject = JSONFactoryUtil.createJSONObject();

		Map<String, Object> definitionMap = getDefinitionMap(
			pageElement.getDefinition());

		if (definitionMap != null) {
			stylesJSONObject.put(
				"backgroundColor", definitionMap.get("backgroundColor"));

			Map<String, Object> backgroundFragmentImageMap =
				(Map<String, Object>)definitionMap.get(
					"backgroundFragmentImage");

			if (MapUtil.isEmpty(backgroundFragmentImageMap)) {
				backgroundFragmentImageMap =
					(Map<String, Object>)definitionMap.get("backgroundImage");
			}

			if (backgroundFragmentImageMap != null) {
				JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

				Map<String, Object> titleMap =
					(Map<String, Object>)backgroundFragmentImageMap.get(
						"title");

				if (titleMap != null) {
					jsonObject.put("title", getLocalizedValue(titleMap));
				}

				Map<String, Object> urlMap =
					(Map<String, Object>)backgroundFragmentImageMap.get("url");

				if (urlMap != null) {
					jsonObject.put("url", getLocalizedValue(urlMap));

					processMapping(
						jsonObject, (Map<String, Object>)urlMap.get("mapping"));
				}

				stylesJSONObject.put("backgroundImage", jsonObject);
			}

			Map<String, Object> containerLayout =
				(Map<String, Object>)definitionMap.get("layout");

			if (containerLayout != null) {
				stylesJSONObject.put(
					"borderColor", (String)containerLayout.get("borderColor")
				).put(
					"borderRadius",
					BorderRadiusConverter.convertToInternalValue(
						(String)containerLayout.get("borderRadius"))
				);

				Integer borderWidth = (Integer)containerLayout.get(
					"borderWidth");

				if (borderWidth != null) {
					stylesJSONObject.put("borderWidth", borderWidth);
				}

				Integer marginBottom = MarginConverter.convertToInternalValue(
					(Integer)containerLayout.get("marginBottom"));

				if (marginBottom != null) {
					stylesJSONObject.put("marginBottom", marginBottom);
				}

				Integer marginLeft = MarginConverter.convertToInternalValue(
					(Integer)containerLayout.get("marginLeft"));

				if (marginLeft != null) {
					stylesJSONObject.put("marginLeft", marginLeft);
				}

				Integer marginRight = MarginConverter.convertToInternalValue(
					(Integer)containerLayout.get("marginRight"));

				if (marginRight != null) {
					stylesJSONObject.put("marginRight", marginRight);
				}

				Integer marginTop = MarginConverter.convertToInternalValue(
					(Integer)containerLayout.get("marginTop"));

				if (marginTop != null) {
					stylesJSONObject.put("marginTop", marginTop);
				}

				Integer opacity = (Integer)containerLayout.get("opacity");

				if (opacity != null) {
					stylesJSONObject.put("opacity", opacity);
				}

				Integer paddingBottom = PaddingConverter.convertToInternalValue(
					(Integer)containerLayout.get("paddingBottom"));

				if (paddingBottom != null) {
					stylesJSONObject.put("paddingBottom", paddingBottom);
				}

				Integer paddingHorizontal =
					PaddingConverter.convertToInternalValue(
						(Integer)containerLayout.get("paddingHorizontal"));
				Integer paddingLeft = PaddingConverter.convertToInternalValue(
					(Integer)containerLayout.get("paddingLeft"));
				Integer paddingRight = PaddingConverter.convertToInternalValue(
					(Integer)containerLayout.get("paddingRight"));

				if (paddingLeft != null) {
					stylesJSONObject.put("paddingLeft", paddingLeft);
				}
				else if (paddingHorizontal != null) {
					stylesJSONObject.put("paddingLeft", paddingHorizontal);
				}

				if (paddingRight != null) {
					stylesJSONObject.put("paddingRight", paddingRight);
				}
				else if (paddingHorizontal != null) {
					stylesJSONObject.put("paddingRight", paddingHorizontal);
				}

				Integer paddingTop = PaddingConverter.convertToInternalValue(
					(Integer)containerLayout.get("paddingTop"));

				if (paddingTop != null) {
					stylesJSONObject.put("paddingTop", paddingTop);
				}

				stylesJSONObject.put(
					"shadow",
					ShadowConverter.convertToInternalValue(
						(String)containerLayout.get("shadow")));

				String containerType = StringUtil.toLowerCase(
					(String)containerLayout.get("containerType"));
				String widthType = StringUtil.toLowerCase(
					(String)containerLayout.get("widthType"));

				if (widthType != null) {
					containerStyledLayoutStructureItem.setWidthType(widthType);
				}
				else if (containerType != null) {
					containerStyledLayoutStructureItem.setWidthType(
						containerType);
				}
			}

			containerStyledLayoutStructureItem.updateItemConfig(
				JSONUtil.put("styles", stylesJSONObject));

			Map<String, Object> fragmentLinkMap =
				(Map<String, Object>)definitionMap.get("fragmentLink");

			if (fragmentLinkMap != null) {
				JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

				Map<String, Object> hrefMap =
					(Map<String, Object>)fragmentLinkMap.get("href");

				if (hrefMap != null) {
					String hrefValue = (String)hrefMap.get("value");

					if (hrefValue != null) {
						jsonObject.put("href", hrefValue);
					}

					processMapping(
						jsonObject,
						(Map<String, Object>)hrefMap.get("mapping"));
				}

				String target = (String)fragmentLinkMap.get("target");

				if (target != null) {
					if (Objects.equals(
							target, FragmentLink.Target.PARENT.getValue()) ||
						Objects.equals(
							target, FragmentLink.Target.TOP.getValue())) {

						target = FragmentLink.Target.SELF.getValue();
					}

					jsonObject.put(
						"target",
						StringPool.UNDERLINE + StringUtil.toLowerCase(target));
				}

				containerStyledLayoutStructureItem.setLinkJSONObject(
					jsonObject);
			}

			Map<String, Object> fragmentStyleMap =
				(Map<String, Object>)definitionMap.get("fragmentStyle");

			if (fragmentStyleMap != null) {
				JSONObject jsonObject = JSONUtil.put(
					"styles", toStylesJSONObject(fragmentStyleMap));

				containerStyledLayoutStructureItem.updateItemConfig(jsonObject);
			}

			if (definitionMap.containsKey("fragmentViewports")) {
				List<Map<String, Object>> fragmentViewports =
					(List<Map<String, Object>>)definitionMap.get(
						"fragmentViewports");

				for (Map<String, Object> fragmentViewport : fragmentViewports) {
					JSONObject jsonObject = JSONUtil.put(
						(String)fragmentViewport.get("id"),
						toFragmentViewportStylesJSONObject(fragmentViewport));

					containerStyledLayoutStructureItem.updateItemConfig(
						jsonObject);
				}
			}
		}

		return containerStyledLayoutStructureItem;
	}

	@Override
	public PageElement.Type getPageElementType() {
		return PageElement.Type.SECTION;
	}

}