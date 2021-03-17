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

package com.liferay.wiki.editor.configuration.internal;

import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType;
import com.liferay.item.selector.criteria.URLItemSelectorReturnType;
import com.liferay.item.selector.criteria.image.criterion.ImageItemSelectorCriterion;
import com.liferay.item.selector.criteria.upload.criterion.UploadItemSelectorCriterion;
import com.liferay.item.selector.criteria.url.criterion.URLItemSelectorCriterion;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.editor.configuration.BaseEditorConfigContributor;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;
import com.liferay.wiki.configuration.WikiFileUploadConfiguration;
import com.liferay.wiki.constants.WikiPortletKeys;
import com.liferay.wiki.item.selector.criterion.WikiAttachmentItemSelectorCriterion;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Sergio González
 * @author Roberto Díaz
 */
public abstract class BaseWikiAttachmentImageEditorConfigContributor
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
			String removePlugins = jsonObject.getString("removePlugins");

			if (Validator.isNotNull(removePlugins)) {
				removePlugins += ",ae_addimages";
			}
			else {
				removePlugins = "ae_addimages";
			}

			jsonObject.put("removePlugins", removePlugins);
		}

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

		String itemSelectorURL = getItemSelectorURL(
			requestBackedPortletURLFactory, name + "selectItem",
			wikiPageResourcePrimKey, themeDisplay);

		jsonObject.put(
			"filebrowserImageBrowseLinkUrl", itemSelectorURL
		).put(
			"filebrowserImageBrowseUrl", itemSelectorURL
		);
	}

	protected ItemSelectorCriterion getImageItemSelectorCriterion(
		ItemSelectorReturnType... desiredItemSelectorReturnTypes) {

		ItemSelectorCriterion imageItemSelectorCriterion =
			new ImageItemSelectorCriterion();

		imageItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			desiredItemSelectorReturnTypes);

		return imageItemSelectorCriterion;
	}

	protected abstract String getItemSelectorURL(
		RequestBackedPortletURLFactory requestBackedPortletURLFactory,
		String itemSelectedEventName, long wikiPageResourcePrimKey,
		ThemeDisplay themeDisplay);

	protected ItemSelectorCriterion getUploadItemSelectorCriterion(
		long wikiPageResourcePrimKey, ThemeDisplay themeDisplay,
		RequestBackedPortletURLFactory requestBackedPortletURLFactory) {

		ItemSelectorCriterion itemSelectorCriterion =
			new UploadItemSelectorCriterion(
				WikiPortletKeys.WIKI,
				PortletURLBuilder.create(
					requestBackedPortletURLFactory.createActionURL(
						WikiPortletKeys.WIKI)
				).setActionName(
					"/wiki/upload_page_attachment"
				).setParameter(
					"mimeTypes", _getMimeTypes()
				).setParameter(
					"resourcePrimKey", wikiPageResourcePrimKey
				).buildString(),
				LanguageUtil.get(themeDisplay.getLocale(), "page-attachments"));

		itemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new FileEntryItemSelectorReturnType());

		return itemSelectorCriterion;
	}

	protected ItemSelectorCriterion getURLItemSelectorCriterion() {
		ItemSelectorCriterion itemSelectorCriterion =
			new URLItemSelectorCriterion();

		itemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new URLItemSelectorReturnType());

		return itemSelectorCriterion;
	}

	protected ItemSelectorCriterion getWikiAttachmentItemSelectorCriterion(
		long wikiPageResourcePrimKey,
		ItemSelectorReturnType... desiredItemSelectorReturnTypes) {

		ItemSelectorCriterion itemSelectorCriterion =
			new WikiAttachmentItemSelectorCriterion(
				wikiPageResourcePrimKey, _getMimeTypes());

		itemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			desiredItemSelectorReturnTypes);

		return itemSelectorCriterion;
	}

	protected abstract WikiFileUploadConfiguration
		getWikiFileUploadConfiguration();

	private String[] _getMimeTypes() {
		String[] dlFileEntryPreviewImageMimeTypes =
			PropsValues.DL_FILE_ENTRY_PREVIEW_IMAGE_MIME_TYPES;

		WikiFileUploadConfiguration wikiFileUploadConfiguration =
			getWikiFileUploadConfiguration();

		List<String> wikiAttachmentMimeTypes = ListUtil.fromArray(
			wikiFileUploadConfiguration.attachmentMimeTypes());

		if (wikiAttachmentMimeTypes.contains(StringPool.STAR)) {
			return dlFileEntryPreviewImageMimeTypes;
		}

		List<String> mimeTypes = new ArrayList<>();

		for (String mimeType : dlFileEntryPreviewImageMimeTypes) {
			if (wikiAttachmentMimeTypes.contains(mimeType)) {
				mimeTypes.add(mimeType);
			}
		}

		return mimeTypes.toArray(new String[0]);
	}

}