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

package com.liferay.wiki.editor.configuration;

import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType;
import com.liferay.item.selector.criteria.UploadableFileReturnType;
import com.liferay.item.selector.criteria.upload.criterion.UploadItemSelectorCriterion;
import com.liferay.portal.kernel.editor.configuration.BaseEditorConfigContributor;
import com.liferay.portal.kernel.editor.configuration.EditorConfigContributor;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.wiki.constants.WikiPortletKeys;
import com.liferay.wiki.item.selector.criterion.WikiAttachmentItemSelectorCriterion;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 * @author Roberto Díaz
 */
@Component(
	property = {
		"javax.portlet.name=" + WikiPortletKeys.WIKI,
		"javax.portlet.name=" + WikiPortletKeys.WIKI_ADMIN,
		"javax.portlet.name=" + WikiPortletKeys.WIKI_DISPLAY,
		"service.ranking:Integer=100"
	},
	service = EditorConfigContributor.class
)
public class WikiAttachmentEditorConfigContributor
	extends BaseEditorConfigContributor {

	@Override
	public void populateConfigJSONObject(
		JSONObject jsonObject, Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		RequestBackedPortletURLFactory requestBackedPortletURLFactory) {

		boolean allowBrowseDocuments = GetterUtil.getBoolean(
			inputEditorTaglibAttributes.get(
				"liferay-ui:input-editor:allowBrowseDocuments"));

		if (!allowBrowseDocuments) {
			return;
		}

		Map<String, String> fileBrowserParamsMap =
			(Map<String, String>)inputEditorTaglibAttributes.get(
				"liferay-ui:input-editor:fileBrowserParams");

		long wikiPageResourcePrimKey = 0;

		if (fileBrowserParamsMap != null) {
			wikiPageResourcePrimKey = GetterUtil.getLong(
				fileBrowserParamsMap.get("wikiPageResourcePrimKey"));
		}

		if (wikiPageResourcePrimKey == 0) {
			removeImageButton(jsonObject);

			return;
		}

		ItemSelectorCriterion attachmentItemSelectorCriterion =
			new WikiAttachmentItemSelectorCriterion(wikiPageResourcePrimKey);

		List<ItemSelectorReturnType> desiredItemSelectorReturnTypes =
			new ArrayList<>();

		desiredItemSelectorReturnTypes.add(new UploadableFileReturnType());
		desiredItemSelectorReturnTypes.add(
			new FileEntryItemSelectorReturnType());

		attachmentItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			desiredItemSelectorReturnTypes);

		PortletURL uploadURL = requestBackedPortletURLFactory.createActionURL(
			WikiPortletKeys.WIKI);

		uploadURL.setParameter(
			ActionRequest.ACTION_NAME, "/wiki/upload_page_attachment");
		uploadURL.setParameter(
			"resourcePrimKey", String.valueOf(wikiPageResourcePrimKey));

		ItemSelectorCriterion uploadItemSelectorCriterion =
			new UploadItemSelectorCriterion(
				uploadURL.toString(),
				LanguageUtil.get(themeDisplay.getLocale(), "page-attachments"));

		List<ItemSelectorReturnType> uploadDesiredItemSelectorReturnTypes =
			new ArrayList<>();

		uploadDesiredItemSelectorReturnTypes.add(
			new UploadableFileReturnType());

		uploadItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			uploadDesiredItemSelectorReturnTypes);

		String name = GetterUtil.getString(
			inputEditorTaglibAttributes.get("liferay-ui:input-editor:name"));

		boolean inlineEdit = GetterUtil.getBoolean(
			inputEditorTaglibAttributes.get(
				"liferay-ui:input-editor:inlineEdit"));

		if (!inlineEdit) {
			String namespace = GetterUtil.getString(
				inputEditorTaglibAttributes.get(
					"liferay-ui:input-editor:namespace"));

			name = namespace + name;
		}

		PortletURL itemSelectorURL = _itemSelector.getItemSelectorURL(
			requestBackedPortletURLFactory, name + "selectItem",
			attachmentItemSelectorCriterion, uploadItemSelectorCriterion);

		jsonObject.put(
			"filebrowserImageBrowseLinkUrl", itemSelectorURL.toString());
		jsonObject.put("filebrowserImageBrowseUrl", itemSelectorURL.toString());
	}

	@Reference(unbind = "-")
	public void setItemSelector(ItemSelector itemSelector) {
		_itemSelector = itemSelector;
	}

	protected void removeImageButton(JSONObject jsonObject) {
		JSONObject toolbarsJSONObject = jsonObject.getJSONObject("toolbars");

		if (toolbarsJSONObject == null) {
			return;
		}

		JSONObject addJSONObject = toolbarsJSONObject.getJSONObject("add");

		if (addJSONObject == null) {
			return;
		}

		JSONArray buttonsJSONArray = addJSONObject.getJSONArray("buttons");

		if (buttonsJSONArray == null) {
			return;
		}

		JSONArray newButtonsJSONArray = JSONFactoryUtil.createJSONArray();

		Iterator iterator = buttonsJSONArray.iterator();

		while (iterator.hasNext()) {
			Object buttonObject = iterator.next();

			if (buttonObject instanceof String) {
				String buttonString = (String)buttonObject;

				if (!buttonString.equals("image")) {
					newButtonsJSONArray.put(buttonString);
				}
			}
			else {
				newButtonsJSONArray.put(buttonObject);
			}
		}

		addJSONObject.put("buttons", newButtonsJSONArray);
	}

	private ItemSelector _itemSelector;

}