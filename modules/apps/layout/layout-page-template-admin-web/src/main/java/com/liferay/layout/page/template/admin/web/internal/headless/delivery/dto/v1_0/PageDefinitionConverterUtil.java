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

package com.liferay.layout.page.template.admin.web.internal.headless.delivery.dto.v1_0;

import com.liferay.headless.delivery.dto.v1_0.FragmentImage;
import com.liferay.headless.delivery.dto.v1_0.PageDefinition;
import com.liferay.headless.delivery.dto.v1_0.PageElement;
import com.liferay.headless.delivery.dto.v1_0.SectionDefinition;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.page.template.util.LayoutDataConverter;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rubén Pulido
 */
@Component(immediate = true, service = PageDefinitionConverterUtil.class)
public class PageDefinitionConverterUtil {

	public PageDefinition toPageDefinition(Layout layout) throws JSONException {
		return new PageDefinition() {
			{
				dateCreated = layout.getCreateDate();
				dateModified = layout.getModifiedDate();
				name = layout.getName();
				pageElements = _toPageElements(layout);
			}
		};
	}

	private PageElement _toPageElement(
		JSONObject itemsJSONObject, JSONObject jsonObject) {

		List<PageElement> childrenPageElements = new ArrayList<>();

		JSONArray childrenJSONArray = jsonObject.getJSONArray("children");

		for (int i = 0; i < childrenJSONArray.length(); i++) {
			String childUUID = childrenJSONArray.getString(i);

			JSONObject childJSONObject = itemsJSONObject.getJSONObject(
				childUUID);

			JSONArray grandChildrenJSONArray = childJSONObject.getJSONArray(
				"children");

			if (grandChildrenJSONArray.length() == 0) {
				childrenPageElements.add(
					_toPageElement(childJSONObject.getString("type")));
			}
			else {
				childrenPageElements.add(
					_toPageElement(itemsJSONObject, childJSONObject));
			}
		}

		PageElement pageElement = _toPageElement(jsonObject.getString("type"));

		pageElement.setPageElements(
			childrenPageElements.toArray(new PageElement[0]));

		return pageElement;
	}

	private PageElement _toPageElement(String type) {
		if (type.equals("column")) {
			return new PageElement() {
				{
					type = PageElement.Type.COLUMN;
				}
			};
		}
		else if (type.equals("container")) {
			return new PageElement() {
				{
					type = PageElement.Type.SECTION;
				}
			};
		}
		else if (type.equals("fragment")) {
			return new PageElement() {
				{
					type = PageElement.Type.FRAGMENT;
				}
			};
		}
		else if (type.equals("row")) {
			return new PageElement() {
				{
					type = PageElement.Type.ROW;
				}
			};
		}

		return null;
	}

	private PageElement[] _toPageElements(Layout layout) throws JSONException {
		List<PageElement> pageElements = new ArrayList<>();

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					layout.getGroupId(), _portal.getClassNameId(Layout.class),
					layout.getPlid());

		String layoutData = LayoutDataConverter.convert(
			layoutPageTemplateStructure.getData(0L));

		JSONObject layoutDataJSONObject = JSONFactoryUtil.createJSONObject(
			layoutData);

		JSONObject rootItemsJSONObject = layoutDataJSONObject.getJSONObject(
			"rootItems");

		String mainUUID = rootItemsJSONObject.getString("main");

		JSONObject itemsJSONObject = layoutDataJSONObject.getJSONObject(
			"items");

		JSONObject mainJSONObject = itemsJSONObject.getJSONObject(mainUUID);

		JSONArray childrenJSONArray = mainJSONObject.getJSONArray("children");

		for (int i = 0; i < childrenJSONArray.length(); i++) {
			String childUUID = childrenJSONArray.getString(i);

			pageElements.add(
				_toPageElement(
					itemsJSONObject, itemsJSONObject.getJSONObject(childUUID)));
		}

		return pageElements.toArray(new PageElement[0]);
	}

	private SectionDefinition _toSectionDefinition(
		JSONObject configJSONObject) {

		return new SectionDefinition() {
			{
				backgroundColorCssClass = configJSONObject.getString(
					"backgroundColorCssClass", null);

				containerType = ContainerType.valueOf(
					StringUtil.toUpperCase(configJSONObject.getString("type")));

				setBackgroundImage(
					() -> {
						JSONObject backgroundImageJSONObject =
							configJSONObject.getJSONObject("backgroundImage");

						if ((backgroundImageJSONObject == null) ||
							(backgroundImageJSONObject.length() == 0)) {

							return null;
						}

						return new FragmentImage() {
							{
								setTitle(
									_toValueMap(
										backgroundImageJSONObject, "title"));
								setUrl(
									_toValueMap(
										backgroundImageJSONObject, "url"));
							}
						};
					});

				setPaddingBottom(
					() -> {
						if (configJSONObject.isNull("paddingBottom")) {
							return null;
						}

						return configJSONObject.getInt("paddingBottom");
					});

				setPaddingHorizontal(
					() -> {
						if (configJSONObject.isNull("paddingHorizontal")) {
							return null;
						}

						return configJSONObject.getInt("paddingHorizontal");
					});

				setPaddingTop(
					() -> {
						if (configJSONObject.isNull("paddingTop")) {
							return null;
						}

						return configJSONObject.getInt("paddingTop");
					});
			}
		};
	}

	private Map<String, String> _toValueMap(
		JSONObject jsonObject, String name) {

		if (jsonObject == null) {
			return null;
		}

		return HashMapBuilder.put(
			"value", jsonObject.getString(name)
		).build();
	}

	@Reference
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Reference
	private Portal _portal;

}