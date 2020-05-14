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
import com.liferay.layout.page.template.util.PaddingConverter;
import com.liferay.layout.util.structure.ContainerLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;
import java.util.Objects;

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
			PageElement pageElement, String parentItemId, int position)
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
						jsonObject, (Map<String, Object>)urlMap.get("mapping"));
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
					"Mapping could not be processed since no class name ID " +
						"could be obtained for class name " + className);
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

	private static final Log _log = LogFactoryUtil.getLog(
		ContainerLayoutStructureItemImporter.class);

	@Reference
	private Portal _portal;

}