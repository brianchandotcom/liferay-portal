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

import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType;
import com.liferay.item.selector.criteria.URLItemSelectorReturnType;
import com.liferay.item.selector.criteria.image.criterion.ImageItemSelectorCriterion;
import com.liferay.item.selector.criteria.upload.criterion.UploadItemSelectorCriterion;
import com.liferay.item.selector.criteria.url.criterion.URLItemSelectorCriterion;
import com.liferay.portal.kernel.editor.configuration.BaseEditorConfigContributor;
import com.liferay.portal.kernel.editor.configuration.EditorConfigContributor;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.wiki.constants.WikiPortletKeys;
import com.liferay.wiki.item.selector.criterion.WikiAttachmentItemSelectorCriterion;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.WikiPageLocalService;

import java.util.ArrayList;
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
		"editor.config.key=contentEditor",
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

		String removePlugins = jsonObject.getString("removePlugins");

		if (Validator.isNotNull(removePlugins)) {
			removePlugins += ",ae_addimages";
		}
		else {
			removePlugins = "ae_addimages";
		}

		jsonObject.put("removePlugins", removePlugins);

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
			return;
		}

		List<ItemSelectorReturnType> desiredItemSelectorReturnTypes =
			new ArrayList<>();

		desiredItemSelectorReturnTypes.add(
			new FileEntryItemSelectorReturnType());

		ItemSelectorCriterion attachmentItemSelectorCriterion =
			getWikiAttachmentItemSelectorCriterion(
				wikiPageResourcePrimKey, desiredItemSelectorReturnTypes);

		ItemSelectorCriterion urlItemSelectorCriterion =
			getURLItemSelectorCriterion();

		ItemSelectorCriterion uploadItemSelectorCriterion =
			getUploadItemSelectorCriterion(
				wikiPageResourcePrimKey, themeDisplay,
				requestBackedPortletURLFactory);

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

		String format = StringPool.BLANK;

		try {
			WikiPage wikiPage = _wikiPageLocalService.getPage(
				wikiPageResourcePrimKey);

			format = wikiPage.getFormat();
		}
		catch (PortalException pe) {
			_log.error(
				"Unable to get format for page " + wikiPageResourcePrimKey, pe);
		}

		PortletURL itemSelectorURL = null;

		if (format.equals("html")) {
			ItemSelectorCriterion imageItemSelectorCriterion =
				getImageItemSelectorCriterion(desiredItemSelectorReturnTypes);

			itemSelectorURL = _itemSelector.getItemSelectorURL(
				requestBackedPortletURLFactory, name + "selectItem",
				attachmentItemSelectorCriterion, imageItemSelectorCriterion,
				urlItemSelectorCriterion, uploadItemSelectorCriterion);
		}
		else {
			itemSelectorURL = _itemSelector.getItemSelectorURL(
				requestBackedPortletURLFactory, name + "selectItem",
				attachmentItemSelectorCriterion, urlItemSelectorCriterion,
				uploadItemSelectorCriterion);
		}

		jsonObject.put(
			"filebrowserImageBrowseLinkUrl", itemSelectorURL.toString());
		jsonObject.put("filebrowserImageBrowseUrl", itemSelectorURL.toString());
	}

	@Reference(unbind = "-")
	public void setItemSelector(ItemSelector itemSelector) {
		_itemSelector = itemSelector;
	}

	protected ItemSelectorCriterion getImageItemSelectorCriterion(
		List<ItemSelectorReturnType> desiredItemSelectorReturnTypes) {

		ItemSelectorCriterion imageItemSelectorCriterion =
			new ImageItemSelectorCriterion();

		imageItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			desiredItemSelectorReturnTypes);

		return imageItemSelectorCriterion;
	}

	protected ItemSelectorCriterion getUploadItemSelectorCriterion(
		long wikiPageResourcePrimKey, ThemeDisplay themeDisplay,
		RequestBackedPortletURLFactory requestBackedPortletURLFactory) {

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
			new FileEntryItemSelectorReturnType());

		uploadItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			uploadDesiredItemSelectorReturnTypes);

		return uploadItemSelectorCriterion;
	}

	protected ItemSelectorCriterion getURLItemSelectorCriterion() {
		ItemSelectorCriterion urlItemSelectorCriterion =
			new URLItemSelectorCriterion();

		List<ItemSelectorReturnType> urlDesiredItemSelectorReturnTypes =
			new ArrayList<>();

		urlDesiredItemSelectorReturnTypes.add(new URLItemSelectorReturnType());

		urlItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			urlDesiredItemSelectorReturnTypes);

		return urlItemSelectorCriterion;
	}

	protected ItemSelectorCriterion getWikiAttachmentItemSelectorCriterion(
		long wikiPageResourcePrimKey,
		List<ItemSelectorReturnType> desiredItemSelectorReturnTypes) {

		ItemSelectorCriterion attachmentItemSelectorCriterion =
			new WikiAttachmentItemSelectorCriterion(wikiPageResourcePrimKey);

		attachmentItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			desiredItemSelectorReturnTypes);

		return attachmentItemSelectorCriterion;
	}

	@Reference(unbind = "-")
	protected void setWikiPageLocalService(
		WikiPageLocalService wikiPageLocalService) {

		_wikiPageLocalService = wikiPageLocalService;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		WikiAttachmentEditorConfigContributor.class);

	private ItemSelector _itemSelector;
	private WikiPageLocalService _wikiPageLocalService;

}