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
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
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
			classNameId = String.valueOf(portal.getClassNameId(className));
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

		if (styles.containsKey("backgroundFragmentImage")) {
			JSONObject backgroundImageJSONObject =
				JSONFactoryUtil.createJSONObject();

			Map<String, Object> childStyleMap = (Map<String, Object>)styles.get(
				"backgroundFragmentImage");

			Map<String, Object> titleMap =
				(Map<String, Object>)childStyleMap.get("title");

			if (titleMap != null) {
				backgroundImageJSONObject.put(
					"title", getLocalizedValue(titleMap));
			}

			Map<String, Object> urlMap = (Map<String, Object>)childStyleMap.get(
				"url");

			if (urlMap != null) {
				backgroundImageJSONObject.put("url", getLocalizedValue(urlMap));

				processMapping(
					backgroundImageJSONObject,
					(Map<String, Object>)urlMap.get("mapping"));
			}

			jsonObject.put("backgroundImage", backgroundImageJSONObject);
		}

		return jsonObject.put(
			"borderColor", styles.get("borderColor")
		).put(
			"borderRadius", styles.get("borderRadius")
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
			"shadow", styles.get("shadow")
		).put(
			"textAlign", styles.get("textAlign")
		).put(
			"textColor", styles.get("textColor")
		).put(
			"width", styles.get("width")
		);
	}

	@Reference
	protected Portal portal;

	private static final Log _log = LogFactoryUtil.getLog(
		BaseLayoutStructureItemImporter.class);

	private static final ObjectMapper _objectMapper = new ObjectMapper();

}