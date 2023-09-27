/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.importer.structure.util;

import com.liferay.headless.delivery.dto.v1_0.FragmentLink;
import com.liferay.headless.delivery.dto.v1_0.PageElement;
import com.liferay.layout.converter.AlignConverter;
import com.liferay.layout.converter.BorderRadiusConverter;
import com.liferay.layout.converter.ContentDisplayConverter;
import com.liferay.layout.converter.ContentVisibilityConverter;
import com.liferay.layout.converter.FlexWrapConverter;
import com.liferay.layout.converter.HtmlTagConverter;
import com.liferay.layout.converter.JustifyConverter;
import com.liferay.layout.converter.MarginConverter;
import com.liferay.layout.converter.PaddingConverter;
import com.liferay.layout.converter.ShadowConverter;
import com.liferay.layout.internal.importer.LayoutStructureItemImporterContext;
import com.liferay.layout.util.structure.ContainerStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashSet;
import java.util.List;
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
			LayoutStructure layoutStructure,
			LayoutStructureItemImporterContext
				layoutStructureItemImporterContext,
			PageElement pageElement, Set<String> warningMessages)
		throws Exception {

		ContainerStyledLayoutStructureItem containerStyledLayoutStructureItem =
			(ContainerStyledLayoutStructureItem)
				layoutStructure.addContainerStyledLayoutStructureItem(
					layoutStructureItemImporterContext.getParentItemId(),
					layoutStructureItemImporterContext.getPosition());

		Map<String, Object> definitionMap = getDefinitionMap(
			pageElement.getDefinition());

		if (definitionMap == null) {
			return containerStyledLayoutStructureItem;
		}

		JSONObject stylesJSONObject = JSONUtil.put(
			"backgroundColor", definitionMap.get("backgroundColor"));

		Map<String, Object> backgroundFragmentImageMap =
			(Map<String, Object>)definitionMap.get("backgroundFragmentImage");

		if (MapUtil.isEmpty(backgroundFragmentImageMap)) {
			backgroundFragmentImageMap = (Map<String, Object>)definitionMap.get(
				"backgroundImage");
		}

		if (backgroundFragmentImageMap != null) {
			JSONObject jsonObject = _jsonFactory.createJSONObject();

			Map<String, Object> titleMap =
				(Map<String, Object>)backgroundFragmentImageMap.get("title");

			if (titleMap != null) {
				jsonObject.put("title", getLocalizedValue(titleMap));
			}

			Map<String, Object> urlMap =
				(Map<String, Object>)backgroundFragmentImageMap.get("url");

			if (urlMap != null) {
				jsonObject.put("url", getLocalizedValue(urlMap));

				processMapping(
					jsonObject, layoutStructureItemImporterContext,
					(Map<String, Object>)urlMap.get("mapping"));
			}

			stylesJSONObject.put("backgroundImage", jsonObject);
		}

		String contentVisibility = String.valueOf(
			definitionMap.getOrDefault("contentVisibility", StringPool.BLANK));

		if (Validator.isNotNull(contentVisibility)) {
			containerStyledLayoutStructureItem.setContentVisibility(
				ContentVisibilityConverter.convertToInternalValue(
					contentVisibility));
		}

		if (definitionMap.containsKey("cssClasses")) {
			List<String> cssClasses = (List<String>)definitionMap.get(
				"cssClasses");

			containerStyledLayoutStructureItem.setCssClasses(
				new HashSet<>(cssClasses));
		}

		if (definitionMap.containsKey("customCSS")) {
			containerStyledLayoutStructureItem.setCustomCSS(
				String.valueOf(definitionMap.get("customCSS")));
		}

		if (definitionMap.containsKey("customCSSViewports")) {
			List<Map<String, Object>> customCSSViewports =
				(List<Map<String, Object>>)definitionMap.get(
					"customCSSViewports");

			for (Map<String, Object> customCSSViewport : customCSSViewports) {
				containerStyledLayoutStructureItem.setCustomCSSViewport(
					(String)customCSSViewport.get("id"),
					(String)customCSSViewport.get("customCSS"));
			}
		}

		Map<String, Object> containerHtmlProperties =
			(Map<String, Object>)definitionMap.get("htmlProperties");

		if (containerHtmlProperties != null) {
			String htmlTag = String.valueOf(
				containerHtmlProperties.getOrDefault(
					"htmlTag", StringPool.BLANK));

			if (Validator.isNotNull(htmlTag)) {
				containerStyledLayoutStructureItem.setHtmlTag(
					HtmlTagConverter.convertToInternalValue(htmlTag));
			}
		}

		Map<String, Object> containerLayout =
			(Map<String, Object>)definitionMap.get("layout");

		if (containerLayout != null) {
			String align = String.valueOf(
				containerLayout.getOrDefault("align", StringPool.BLANK));

			if (Validator.isNotNull(align)) {
				containerStyledLayoutStructureItem.setAlign(
					AlignConverter.convertToInternalValue(align));
			}

			stylesJSONObject.put(
				"borderColor", (String)containerLayout.get("borderColor")
			).put(
				"borderRadius",
				BorderRadiusConverter.convertToInternalValue(
					(String)containerLayout.get("borderRadius"))
			);

			String borderWidth = String.valueOf(
				containerLayout.getOrDefault("borderWidth", StringPool.BLANK));

			if (Validator.isNotNull(borderWidth)) {
				stylesJSONObject.put("borderWidth", borderWidth);
			}

			String contentDisplay = String.valueOf(
				containerLayout.getOrDefault(
					"contentDisplay", StringPool.BLANK));

			if (Validator.isNotNull(contentDisplay)) {
				containerStyledLayoutStructureItem.setContentDisplay(
					ContentDisplayConverter.convertToInternalValue(
						contentDisplay));
			}

			String flexWrap = String.valueOf(
				containerLayout.getOrDefault("flexWrap", StringPool.BLANK));

			if (Validator.isNotNull(flexWrap)) {
				containerStyledLayoutStructureItem.setFlexWrap(
					FlexWrapConverter.convertToInternalValue(flexWrap));
			}

			String justify = String.valueOf(
				containerLayout.getOrDefault("justify", StringPool.BLANK));

			if (Validator.isNotNull(justify)) {
				containerStyledLayoutStructureItem.setJustify(
					JustifyConverter.convertToInternalValue(justify));
			}

			String marginBottom = MarginConverter.convertToInternalValue(
				String.valueOf(
					containerLayout.getOrDefault(
						"marginBottom", StringPool.BLANK)));

			if (Validator.isNotNull(marginBottom)) {
				stylesJSONObject.put("marginBottom", marginBottom);
			}

			String marginLeft = MarginConverter.convertToInternalValue(
				String.valueOf(
					containerLayout.getOrDefault(
						"marginLeft", StringPool.BLANK)));

			if (Validator.isNotNull(marginLeft)) {
				stylesJSONObject.put("marginLeft", marginLeft);
			}

			String marginRight = MarginConverter.convertToInternalValue(
				String.valueOf(
					containerLayout.getOrDefault(
						"marginRight", StringPool.BLANK)));

			if (Validator.isNotNull(marginRight)) {
				stylesJSONObject.put("marginRight", marginRight);
			}

			String marginTop = MarginConverter.convertToInternalValue(
				String.valueOf(
					containerLayout.getOrDefault(
						"marginTop", StringPool.BLANK)));

			if (Validator.isNotNull(marginTop)) {
				stylesJSONObject.put("marginTop", marginTop);
			}

			String opacity = String.valueOf(
				containerLayout.getOrDefault("opacity", StringPool.BLANK));

			if (Validator.isNotNull(opacity)) {
				stylesJSONObject.put("opacity", opacity);
			}

			String paddingBottom = PaddingConverter.convertToInternalValue(
				String.valueOf(
					containerLayout.getOrDefault(
						"paddingBottom", StringPool.BLANK)));

			if (Validator.isNotNull(paddingBottom)) {
				stylesJSONObject.put("paddingBottom", paddingBottom);
			}

			String paddingHorizontal = PaddingConverter.convertToInternalValue(
				String.valueOf(
					containerLayout.getOrDefault(
						"paddingHorizontal", StringPool.BLANK)));
			String paddingLeft = PaddingConverter.convertToInternalValue(
				String.valueOf(
					containerLayout.getOrDefault(
						"paddingLeft", StringPool.BLANK)));
			String paddingRight = PaddingConverter.convertToInternalValue(
				String.valueOf(
					containerLayout.getOrDefault(
						"paddingRight", StringPool.BLANK)));

			if (Validator.isNotNull(paddingLeft)) {
				stylesJSONObject.put("paddingLeft", paddingLeft);
			}
			else if (Validator.isNotNull(paddingHorizontal)) {
				stylesJSONObject.put("paddingLeft", paddingHorizontal);
			}

			if (Validator.isNotNull(paddingRight)) {
				stylesJSONObject.put("paddingRight", paddingRight);
			}
			else if (Validator.isNotNull(paddingHorizontal)) {
				stylesJSONObject.put("paddingRight", paddingHorizontal);
			}

			String paddingTop = PaddingConverter.convertToInternalValue(
				String.valueOf(
					containerLayout.getOrDefault(
						"paddingTop", StringPool.BLANK)));

			if (Validator.isNotNull(paddingTop)) {
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
				containerStyledLayoutStructureItem.setWidthType(containerType);
			}
		}

		containerStyledLayoutStructureItem.updateItemConfig(
			JSONUtil.put("styles", stylesJSONObject));

		Map<String, Object> fragmentLinkMap =
			(Map<String, Object>)definitionMap.get("fragmentLink");

		if (fragmentLinkMap != null) {
			JSONObject jsonObject = _jsonFactory.createJSONObject();

			Map<String, Object> hrefMap =
				(Map<String, Object>)fragmentLinkMap.get("href");

			if (hrefMap != null) {
				Object localizedValue = getLocalizedValue(hrefMap);

				if (localizedValue != null) {
					jsonObject.put("href", localizedValue);
				}

				processMapping(
					jsonObject, layoutStructureItemImporterContext,
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

			containerStyledLayoutStructureItem.setLinkJSONObject(jsonObject);
		}

		Map<String, Object> fragmentStyleMap =
			(Map<String, Object>)definitionMap.get("fragmentStyle");

		if (fragmentStyleMap != null) {
			JSONObject jsonObject = JSONUtil.put(
				"styles",
				toStylesJSONObject(
					layoutStructureItemImporterContext, fragmentStyleMap));

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

				containerStyledLayoutStructureItem.updateItemConfig(jsonObject);
			}
		}

		if (definitionMap.containsKey("indexed")) {
			containerStyledLayoutStructureItem.setIndexed(
				GetterUtil.getBoolean(definitionMap.get("indexed")));
		}

		if (definitionMap.containsKey("name")) {
			containerStyledLayoutStructureItem.setName(
				GetterUtil.getString(definitionMap.get("name")));
		}

		return containerStyledLayoutStructureItem;
	}

	@Override
	public PageElement.Type getPageElementType() {
		return PageElement.Type.SECTION;
	}

	@Reference
	private JSONFactory _jsonFactory;

}