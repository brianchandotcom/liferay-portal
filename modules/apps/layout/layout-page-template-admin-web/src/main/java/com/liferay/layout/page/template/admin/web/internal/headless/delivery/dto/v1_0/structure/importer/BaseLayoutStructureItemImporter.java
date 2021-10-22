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

import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.headless.delivery.dto.v1_0.ContextReference;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
public abstract class BaseLayoutStructureItemImporter {

	protected Map<String, Object> getDefinitionMap(Object definition)
		throws Exception {

		Map<String, Object> definitionMap = null;

		if (definition instanceof Map) {
			definitionMap = (Map<String, Object>)definition;
		}
		else {
			definitionMap = _objectMapper.readValue(
				definition.toString(), Map.class);
		}

		return definitionMap;
	}

	protected Object getLocalizedValue(Map<String, Object> map) {
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

	protected void processMapping(
		JSONObject jsonObject, Map<String, Object> map) {

		if (map == null) {
			return;
		}

		String fieldKey = (String)map.get("fieldKey");

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

		if (Validator.isNotNull(fieldKey)) {
			jsonObject.put("fieldId", fieldKey);
		}

		String className = (String)itemReferenceMap.get("className");

		if (Objects.equals(className, Layout.class.getName()) &&
			Objects.equals(itemReferenceMap.get("fieldName"), "plid")) {

			String fieldValue = (String)itemReferenceMap.get("fieldValue");

			Layout layout = layoutLocalService.fetchLayout(
				GetterUtil.getLong(fieldValue));

			if (layout == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to process mapping because layout could not " +
							"be obtained for PLID " + fieldValue);
				}

				return;
			}

			jsonObject.put(
				"layout",
				JSONUtil.put(
					"groupId", String.valueOf(layout.getGroupId())
				).put(
					"id", layout.getUuid()
				).put(
					"layoutId", String.valueOf(layout.getLayoutId())
				).put(
					"layoutUuid", layout.getUuid()
				).put(
					"privateLayout", layout.isPrivateLayout()
				).put(
					"title", layout.getName(LocaleUtil.getMostRelevantLocale())
				).put(
					"value", layout.getFriendlyURL()
				));

			return;
		}

		String classNameId = null;

		try {
			classNameId = String.valueOf(portal.getClassNameId(className));
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to process mapping because class name ID could " +
						"not be obtained for class name " + className,
					exception);
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

	protected JSONObject toFragmentViewportStylesJSONObject(
		Map<String, Object> fragmentViewport) {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		if (MapUtil.isEmpty(fragmentViewport)) {
			return jsonObject;
		}

		Map<String, Object> fragmentViewportStyle =
			(Map<String, Object>)fragmentViewport.get("fragmentViewportStyle");

		if (MapUtil.isEmpty(fragmentViewportStyle)) {
			return jsonObject;
		}

		Object hidden = fragmentViewportStyle.get("hidden");

		if (hidden != null) {
			if (GetterUtil.getBoolean(hidden)) {
				jsonObject.put("display", "none");
			}
			else {
				jsonObject.put("display", "block");
			}
		}

		return JSONUtil.put(
			"styles",
			jsonObject.put(
				"marginBottom", fragmentViewportStyle.get("marginBottom")
			).put(
				"marginLeft", fragmentViewportStyle.get("marginLeft")
			).put(
				"marginRight", fragmentViewportStyle.get("marginRight")
			).put(
				"marginTop", fragmentViewportStyle.get("marginTop")
			).put(
				"maxHeight", fragmentViewportStyle.get("maxHeight")
			).put(
				"paddingBottom", fragmentViewportStyle.get("paddingBottom")
			).put(
				"paddingLeft", fragmentViewportStyle.get("paddingLeft")
			).put(
				"paddingRight", fragmentViewportStyle.get("paddingRight")
			).put(
				"paddingTop", fragmentViewportStyle.get("paddingTop")
			));
	}

	protected JSONObject toStylesJSONObject(Map<String, Object> styles) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		if (MapUtil.isEmpty(styles)) {
			return jsonObject;
		}

		jsonObject.put("backgroundColor", styles.get("backgroundColor"));

		if (styles.containsKey("backgroundFragmentImage") ||
			styles.containsKey("backgroundImage")) {

			JSONObject backgroundImageJSONObject =
				JSONFactoryUtil.createJSONObject();

			Map<String, Object> childStyleMap = (Map<String, Object>)styles.get(
				"backgroundFragmentImage");

			if (MapUtil.isEmpty(childStyleMap)) {
				childStyleMap = (Map<String, Object>)styles.get(
					"backgroundImage");
			}

			if (MapUtil.isNotEmpty(childStyleMap)) {
				Map<String, Object> titleMap =
					(Map<String, Object>)childStyleMap.get("title");

				if (titleMap != null) {
					backgroundImageJSONObject.put(
						"title", getLocalizedValue(titleMap));
				}

				Map<String, Object> urlMap =
					(Map<String, Object>)childStyleMap.get("url");

				if (urlMap != null) {
					backgroundImageJSONObject.put(
						"url", getLocalizedValue(urlMap));

					processMapping(
						backgroundImageJSONObject,
						(Map<String, Object>)urlMap.get("mapping"));
				}

				jsonObject.put("backgroundImage", backgroundImageJSONObject);
			}
		}

		Object borderColor = styles.get("borderColor");

		if (borderColor instanceof String) {
			borderColor = _colors.getOrDefault(
				borderColor, (String)borderColor);
		}

		String borderRadius = GetterUtil.getString(styles.get("borderRadius"));

		boolean hidden = GetterUtil.getBoolean(styles.get("hidden"));

		if (hidden) {
			jsonObject.put("display", "none");
		}

		Object shadow = styles.getOrDefault("boxShadow", styles.get("shadow"));

		String textAlign = GetterUtil.getString(styles.get("textAlign"));

		if (Validator.isNull(textAlign)) {
			for (String alignKey : _ALIGN_KEYS) {
				if (styles.containsKey(alignKey)) {
					textAlign = GetterUtil.getString(styles.get(alignKey));

					break;
				}
			}
		}

		Object textColor = styles.get("textColor");

		if (textColor instanceof String) {
			textColor = _colors.getOrDefault(textColor, (String)textColor);
		}

		return jsonObject.put(
			"borderColor", borderColor
		).put(
			"borderRadius",
			_borderRadiuses.getOrDefault(borderRadius, borderRadius)
		).put(
			"borderWidth", styles.get("borderWidth")
		).put(
			"fontFamily", styles.get("fontFamily")
		).put(
			"fontSize", styles.get("fontSize")
		).put(
			"fontWeight", styles.get("fontWeight")
		).put(
			"height", styles.get("height")
		).put(
			"marginBottom", styles.get("marginBottom")
		).put(
			"marginLeft", styles.get("marginLeft")
		).put(
			"marginRight", styles.get("marginRight")
		).put(
			"marginTop", styles.get("marginTop")
		).put(
			"maxHeight", styles.get("maxHeight")
		).put(
			"maxWidth", styles.get("maxWidth")
		).put(
			"minHeight", styles.get("minHeight")
		).put(
			"minWidth", styles.get("minWidth")
		).put(
			"opacity", styles.get("opacity")
		).put(
			"overflow", styles.get("overflow")
		).put(
			"paddingBottom", styles.get("paddingBottom")
		).put(
			"paddingLeft", styles.get("paddingLeft")
		).put(
			"paddingRight", styles.get("paddingRight")
		).put(
			"paddingTop", styles.get("paddingTop")
		).put(
			"shadow",
			_shadows.getOrDefault(shadow, GetterUtil.getString(shadow))
		).put(
			"textAlign", textAlign
		).put(
			"textColor", textColor
		).put(
			"width", styles.get("width")
		);
	}

	@Reference
	protected LayoutLocalService layoutLocalService;

	@Reference
	protected Portal portal;

	private static final String[] _ALIGN_KEYS = {
		"buttonAlign", "contentAlign", "imageAlign", "textAlign"
	};

	private static final Log _log = LogFactoryUtil.getLog(
		BaseLayoutStructureItemImporter.class);

	private static final Map<String, String> _borderRadiuses =
		HashMapBuilder.put(
			"lg", "0.375rem"
		).put(
			"none", StringPool.BLANK
		).put(
			"sm", "0.1875rem"
		).build();
	private static final Map<String, String> _colors = HashMapBuilder.put(
		"danger", "#DA1414"
	).put(
		"dark", "#272833"
	).put(
		"gray-dark", "#393A4A"
	).put(
		"info", "#2E5AAC"
	).put(
		"light", "#F1F2F5"
	).put(
		"lighter", "#F7F8F9"
	).put(
		"primary", "#0B5FFF"
	).put(
		"secondary", "#6B6C7E"
	).put(
		"success", "#287D3C"
	).put(
		"warning", "#B95000"
	).put(
		"white", "#FFFFFF"
	).build();
	private static final ObjectMapper _objectMapper = new ObjectMapper();
	private static final Map<String, String> _shadows = HashMapBuilder.put(
		"lg", "0 1rem 3rem rgba(0, 0, 0, .175)"
	).put(
		"sm", "0 .125rem .25rem rgba(0, 0, 0, .075)"
	).build();

}