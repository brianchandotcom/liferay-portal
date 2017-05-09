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

package com.liferay.wiki.editor.link.browse.web.internal.editor.configuration;

import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.URLItemSelectorReturnType;
import com.liferay.portal.kernel.editor.configuration.BaseEditorConfigContributor;
import com.liferay.portal.kernel.editor.configuration.EditorConfigContributor;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.wiki.constants.WikiPortletKeys;
import com.liferay.wiki.item.selector.criterion.WikiAttachmentItemSelectorCriterion;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Díaz
 */
@Component(
	property = {
		"editor.config.key=contentEditor", "editor.name=alloyeditor",
		"editor.name=alloyeditor_creole",
		"javax.portlet.name=" + WikiPortletKeys.WIKI,
		"javax.portlet.name=" + WikiPortletKeys.WIKI_ADMIN,
		"javax.portlet.name=" + WikiPortletKeys.WIKI_DISPLAY,
		"service.ranking:Integer=1000"
	},
	service = EditorConfigContributor.class
)
public class WikiContentAlloyEditorLinkBrowseConfigContributor
	extends BaseEditorConfigContributor {

	@Override
	public void populateConfigJSONObject(
		JSONObject jsonObject, Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		RequestBackedPortletURLFactory requestBackedPortletURLFactory) {

		Map<String, String> fileBrowserParamsMap =
			(Map<String, String>)inputEditorTaglibAttributes.get(
				"liferay-ui:input-editor:fileBrowserParams");

		if (fileBrowserParamsMap != null) {
			long wikiPageResourcePrimKey = GetterUtil.getLong(
				fileBrowserParamsMap.get("wikiPageResourcePrimKey"));

			if (wikiPageResourcePrimKey != 0) {
				String documentBrowseLinkUrl = jsonObject.getString(
					"documentBrowseLinkUrl");

				PortletURL itemSelectorURL = null;

				if (documentBrowseLinkUrl == null) {
					String name = GetterUtil.getString(
						inputEditorTaglibAttributes.get(
							"liferay-ui:input-editor:name"));

					boolean inlineEdit = GetterUtil.getBoolean(
						inputEditorTaglibAttributes.get(
							"liferay-ui:input-editor:inlineEdit"));

					if (!inlineEdit) {
						String namespace = GetterUtil.getString(
							inputEditorTaglibAttributes.get(
								"liferay-ui:input-editor:namespace"));

						name = namespace + name;
					}

					itemSelectorURL = _itemSelector.getItemSelectorURL(
						requestBackedPortletURLFactory, name + "selectItem",
						getWikiAttachmentItemSelectorCriterion(
							wikiPageResourcePrimKey));
				}
				else {
					List<ItemSelectorCriterion> itemSelectorCriteria =
						_itemSelector.getItemSelectorCriteria(
							documentBrowseLinkUrl);

					String itemSelectedEventName =
						_itemSelector.getItemSelectedEventName(
							documentBrowseLinkUrl);

					itemSelectorCriteria.add(
						0,
						getWikiAttachmentItemSelectorCriterion(
							wikiPageResourcePrimKey));

					itemSelectorURL = _itemSelector.getItemSelectorURL(
						requestBackedPortletURLFactory, itemSelectedEventName,
						itemSelectorCriteria.toArray(
							new ItemSelectorCriterion[
								itemSelectorCriteria.size()]));
				}

				jsonObject.put(
					"documentBrowseLinkUrl", itemSelectorURL.toString());
			}
		}
	}

	protected WikiAttachmentItemSelectorCriterion
		getWikiAttachmentItemSelectorCriterion(long wikiPageResourcePrimKey) {

		WikiAttachmentItemSelectorCriterion itemSelectorCriterion =
			new WikiAttachmentItemSelectorCriterion(wikiPageResourcePrimKey);

		List<ItemSelectorReturnType> desiredItemSelectorReturnTypes =
			new ArrayList<>();

		desiredItemSelectorReturnTypes.add(new URLItemSelectorReturnType());

		itemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			desiredItemSelectorReturnTypes);

		return itemSelectorCriterion;
	}

	@Reference
	private ItemSelector _itemSelector;

}