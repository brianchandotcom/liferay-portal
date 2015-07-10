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

package com.liferay.blogs.editor.configuration;

import com.liferay.blogs.item.selector.criterion.BlogsItemSelectorCriterion;
import com.liferay.blogs.web.constants.BlogsPortletKeys;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.URLItemSelectorReturnType;
import com.liferay.item.selector.criteria.UploadableFileReturnType;
import com.liferay.item.selector.criteria.image.criterion.ImageItemSelectorCriterion;
import com.liferay.portal.kernel.editor.configuration.BaseEditorConfigContributor;
import com.liferay.portal.kernel.editor.configuration.EditorConfigContributor;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.theme.ThemeDisplay;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Díaz
 */
@Component(
	property = {
		"editor.config.key=contentEditor",
		"javax.portlet.name=" + BlogsPortletKeys.BLOGS,
		"javax.portlet.name=" + BlogsPortletKeys.BLOGS_ADMIN
	},
	service = EditorConfigContributor.class
)
public class BlogsContentEditorConfigContributor
	extends BaseEditorConfigContributor {

	@Override
	public void populateConfigJSONObject(
		JSONObject jsonObject, Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		LiferayPortletResponse liferayPortletResponse) {

		if (liferayPortletResponse != null) {
			String name =
				liferayPortletResponse.getNamespace() +
					GetterUtil.getString(
						(String)inputEditorTaglibAttributes.get(
							"liferay-ui:input-editor:name"));

			populateFileBrowserURL(
				jsonObject, liferayPortletResponse, name + "selectDocument");
		}
	}

	@Reference(unbind = "-")
	public void setItemSelector(ItemSelector itemSelector) {
		_itemSelector = itemSelector;
	}

	protected void populateFileBrowserURL(
		JSONObject jsonObject, LiferayPortletResponse liferayPortletResponse,
		String eventName) {

		Set<ItemSelectorReturnType>
			blogsContentEditorDesiredItemSelectorReturnTypes = new HashSet<>();

		blogsContentEditorDesiredItemSelectorReturnTypes.add(
			new UploadableFileReturnType());

		blogsContentEditorDesiredItemSelectorReturnTypes.add(
			new URLItemSelectorReturnType());

		ItemSelectorCriterion blogsItemSelectorCriterion =
			new BlogsItemSelectorCriterion();

		blogsItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			blogsContentEditorDesiredItemSelectorReturnTypes);

		ItemSelectorCriterion imageItemSelectorCriterion =
			new ImageItemSelectorCriterion();

		imageItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			blogsContentEditorDesiredItemSelectorReturnTypes);

		PortletURL itemSelectorURL = _itemSelector.getItemSelectorURL(
			liferayPortletResponse, eventName, blogsItemSelectorCriterion,
			imageItemSelectorCriterion);

		jsonObject.put(
			"filebrowserImageBrowseLinkUrl", itemSelectorURL.toString());
		jsonObject.put("filebrowserImageBrowseUrl", itemSelectorURL.toString());
	}

	private ItemSelector _itemSelector;

}