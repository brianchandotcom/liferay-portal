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

import com.liferay.headless.delivery.dto.v1_0.ContextReference;
import com.liferay.headless.delivery.dto.v1_0.PageElement;
import com.liferay.layout.page.template.util.AlignConverter;
import com.liferay.layout.page.template.util.BorderRadiusConverter;
import com.liferay.layout.page.template.util.JustifyConverter;
import com.liferay.layout.page.template.util.MarginConverter;
import com.liferay.layout.page.template.util.PaddingConverter;
import com.liferay.layout.page.template.util.ShadowConverter;
import com.liferay.layout.util.structure.ContainerLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

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

		ContainerLayoutStructureItem containerLayoutStructureItem =
			(ContainerLayoutStructureItem)
				layoutStructure.addContainerLayoutStructureItem(
					parentItemId, position);

		Map<String, Object> definitionMap = getDefinitionMap(
			pageElement.getDefinition());

		if (definitionMap != null) {
			containerLayoutStructureItem.setBackgroundColorCssClass(
				(String)definitionMap.get("backgroundColor"));

			Map<String, Object> backgroundFragmentImageMap =
				(Map<String, Object>)definitionMap.get(
					"backgroundFragmentImage");

			if (backgroundFragmentImageMap == null) {
				backgroundFragmentImageMap =
					(Map<String, Object>)definitionMap.get("backgroundImage");
			}

			if (backgroundFragmentImageMap != null) {
				JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

				Map<String, Object> titleMap =
					(Map<String, Object>)backgroundFragmentImageMap.get(
						"title");

				if (titleMap != null) {
					jsonObject.put("title", _getLocalizedValue(titleMap));
				}

				Map<String, Object> urlMap =
					(Map<String, Object>)backgroundFragmentImageMap.get("url");

				if (urlMap != null) {
					jsonObject.put("url", _getLocalizedValue(urlMap));

					_processMapping(
						jsonObject, (Map<String, Object>)urlMap.get("mapping"));
				}

				containerLayoutStructureItem.setBackgroundImageJSONObject(
					jsonObject);
			}

			Map<String, Object> containerLayout =
				(Map<String, Object>)definitionMap.get("layout");

			if (containerLayout != null) {
				containerLayoutStructureItem.setAlign(
					AlignConverter.convertToInternalValue(
						(String)containerLayout.get("align")));
				containerLayoutStructureItem.setBorderColor(
					(String)containerLayout.get("borderColor"));
				containerLayoutStructureItem.setBorderRadius(
					BorderRadiusConverter.convertToInternalValue(
						(String)containerLayout.get("borderRadius")));

				Integer borderWidth = (Integer)containerLayout.get(
					"borderWidth");

				if (borderWidth != null) {
					containerLayoutStructureItem.setBorderWidth(borderWidth);
				}

				containerLayoutStructureItem.setContentDisplay(
					StringUtil.toLowerCase(
						(String)containerLayout.get("contentDisplay")));
				containerLayoutStructureItem.setJustify(
					JustifyConverter.convertToInternalValue(
						(String)containerLayout.get("justify")));
				Integer marginBottom = MarginConverter.convertToInternalValue(
					(Integer)containerLayout.get("marginBottom"));

				if (marginBottom != null) {
					containerLayoutStructureItem.setMarginBottom(marginBottom);
				}

				Integer marginLeft = MarginConverter.convertToInternalValue(
					(Integer)containerLayout.get("marginLeft"));

				if (marginLeft != null) {
					containerLayoutStructureItem.setMarginLeft(marginLeft);
				}

				Integer marginRight = MarginConverter.convertToInternalValue(
					(Integer)containerLayout.get("marginRight"));

				if (marginRight != null) {
					containerLayoutStructureItem.setMarginRight(marginRight);
				}

				Integer marginTop = MarginConverter.convertToInternalValue(
					(Integer)containerLayout.get("marginTop"));

				if (marginTop != null) {
					containerLayoutStructureItem.setMarginTop(marginTop);
				}

				Integer opacity = (Integer)containerLayout.get("opacity");

				if (opacity != null) {
					containerLayoutStructureItem.setOpacity(opacity);
				}

				Integer paddingBottom = PaddingConverter.convertToInternalValue(
					(Integer)containerLayout.get("paddingBottom"));

				if (paddingBottom != null) {
					containerLayoutStructureItem.setPaddingBottom(
						paddingBottom);
				}

				Integer paddingHorizontal =
					PaddingConverter.convertToInternalValue(
						(Integer)containerLayout.get("paddingHorizontal"));
				Integer paddingLeft = PaddingConverter.convertToInternalValue(
					(Integer)containerLayout.get("paddingLeft"));
				Integer paddingRight = PaddingConverter.convertToInternalValue(
					(Integer)containerLayout.get("paddingRight"));

				if (paddingLeft != null) {
					containerLayoutStructureItem.setPaddingLeft(paddingLeft);
				}
				else if (paddingHorizontal != null) {
					containerLayoutStructureItem.setPaddingLeft(
						paddingHorizontal);
				}

				if (paddingRight != null) {
					containerLayoutStructureItem.setPaddingRight(paddingRight);
				}
				else if (paddingHorizontal != null) {
					containerLayoutStructureItem.setPaddingRight(
						paddingHorizontal);
				}

				Integer paddingTop = PaddingConverter.convertToInternalValue(
					(Integer)containerLayout.get("paddingTop"));

				if (paddingTop != null) {
					containerLayoutStructureItem.setPaddingTop(paddingTop);
				}

				containerLayoutStructureItem.setShadow(
					ShadowConverter.convertToInternalValue(
						(String)containerLayout.get("shadow")));

				String containerType = StringUtil.toLowerCase(
					(String)containerLayout.get("containerType"));
				String widthType = StringUtil.toLowerCase(
					(String)containerLayout.get("widthType"));

				if (widthType != null) {
					containerLayoutStructureItem.setWidthType(widthType);
				}
				else if (containerType != null) {
					containerLayoutStructureItem.setWidthType(containerType);
				}
			}

			Map<String, Object> styles = (Map<String, Object>)definitionMap.get(
				"styles");

			if (styles != null) {
				JSONObject jsonObject = JSONUtil.put(
					"styles", _toStylesJSONObject(styles));

				containerLayoutStructureItem.updateItemConfig(jsonObject);
			}

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

					_processMapping(
						jsonObject,
						(Map<String, Object>)hrefMap.get("mapping"));
				}

				String target = (String)fragmentLinkMap.get("target");

				if (target != null) {
					jsonObject.put(
						"target",
						StringPool.UNDERLINE + StringUtil.toLowerCase(target));
				}

				containerLayoutStructureItem.setLinkJSONObject(jsonObject);
			}
		}

		return containerLayoutStructureItem;
	}

	@Override
	public PageElement.Type getPageElementType() {
		return PageElement.Type.SECTION;
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
		JSONObject jsonObject, Map<String, Object> map) {

		if (map == null) {
			return;
		}

		String fieldKey = (String)map.get("fieldKey");

		if (Validator.isNull(fieldKey)) {
			return;
		}

		Map<String, Object> itemReferenceMap = (Map<String, Object>)map.get(
			"itemReference");

		if (itemReferenceMap == null) {
			return;
		}

		String contextSource = (String)itemReferenceMap.get("contextSource");

		if (Objects.equals(
				ContextReference.ContextSource.COLLECTION_ITEM.getValue(),
				contextSource)) {

			jsonObject.put("collectionFieldId", fieldKey);

			return;
		}

		if (Objects.equals(
				ContextReference.ContextSource.DISPLAY_PAGE_ITEM.getValue(),
				contextSource)) {

			jsonObject.put("mappedField", fieldKey);

			return;
		}

		jsonObject.put("fieldId", fieldKey);

		String classNameId = null;

		String className = (String)itemReferenceMap.get("className");

		try {
			classNameId = String.valueOf(_portal.getClassNameId(className));
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to process mapping because class name ID could " +
						"not be obtained for class name " + className);
			}

			return;
		}

		String classPK = String.valueOf(itemReferenceMap.get("classPK"));

		if (Validator.isNotNull(classNameId) && Validator.isNotNull(classPK)) {
			jsonObject.put(
				"classNameId", classNameId
			).put(
				"classPK", classPK
			);
		}
	}

	private JSONObject _toStylesJSONObject(Map<String, Object> styles) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		for (Map.Entry<String, Object> entry : styles.entrySet()) {
			if (entry.getValue() instanceof HashMap) {
				Map<String, Object> childStyleMap =
					(Map<String, Object>)entry.getValue();

				if (Objects.equals(entry.getKey(), "backgroundImage")) {
					JSONObject backgroundImageJSONObject =
						JSONFactoryUtil.createJSONObject();

					Map<String, Object> titleMap =
						(Map<String, Object>)childStyleMap.get("title");

					if (titleMap != null) {
						backgroundImageJSONObject.put(
							"title", _getLocalizedValue(titleMap));
					}

					Map<String, Object> urlMap =
						(Map<String, Object>)childStyleMap.get("url");

					if (urlMap != null) {
						backgroundImageJSONObject.put(
							"url", _getLocalizedValue(urlMap));

						_processMapping(
							backgroundImageJSONObject,
							(Map<String, Object>)urlMap.get("mapping"));
					}

					jsonObject.put(entry.getKey(), backgroundImageJSONObject);
				}
				else {
					jsonObject.put(
						entry.getKey(), _toStylesJSONObject(childStyleMap));
				}
			}
			else {
				jsonObject.put(entry.getKey(), entry.getValue());
			}
		}

		return jsonObject;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ContainerLayoutStructureItemImporter.class);

	@Reference
	private Portal _portal;

}