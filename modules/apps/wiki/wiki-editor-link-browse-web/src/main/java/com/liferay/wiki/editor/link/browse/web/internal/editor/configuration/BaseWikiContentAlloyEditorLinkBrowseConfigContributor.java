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
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.wiki.item.selector.criterion.WikiAttachmentItemSelectorCriterion;
import com.liferay.wiki.item.selector.criterion.WikiPageItemSelectorCriterion;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Díaz
 */
public abstract class BaseWikiContentAlloyEditorLinkBrowseConfigContributor
	extends BaseEditorConfigContributor {

	@Override
	public void populateConfigJSONObject(
		JSONObject jsonObject, Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		RequestBackedPortletURLFactory requestBackedPortletURLFactory) {

		Map<String, String> fileBrowserParamsMap =
			(Map<String, String>)inputEditorTaglibAttributes.get(
				"liferay-ui:input-editor:fileBrowserParams");

		if (fileBrowserParamsMap == null) {
			return;
		}

		long nodeId = GetterUtil.getLong(fileBrowserParamsMap.get("nodeId"));
		long wikiPageResourcePrimKey = GetterUtil.getLong(
			fileBrowserParamsMap.get("wikiPageResourcePrimKey"));

		String documentBrowseLinkUrl = jsonObject.getString(
			"documentBrowseLinkUrl");

		List<ItemSelectorCriterion> itemSelectorCriteria = new ArrayList<>();

		String itemSelectedEventName = null;

		if (documentBrowseLinkUrl == null) {
			if (nodeId != 0) {
				itemSelectorCriteria.add(
					getWikiPageItemSelectorCriterion(nodeId));
			}

			if (wikiPageResourcePrimKey == 0) {
				itemSelectorCriteria.add(
					getWikiAttachmentItemSelectorCriterion(
						wikiPageResourcePrimKey));
			}

			if (itemSelectorCriteria.isEmpty()) {
				return;
			}

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

			itemSelectedEventName = name + "selectItem";
		}
		else {
			itemSelectorCriteria = itemSelector.getItemSelectorCriteria(
				documentBrowseLinkUrl);

			itemSelectedEventName = itemSelector.getItemSelectedEventName(
				documentBrowseLinkUrl);

			if (wikiPageResourcePrimKey != 0) {
				itemSelectorCriteria.add(
					0,
					getWikiAttachmentItemSelectorCriterion(
						wikiPageResourcePrimKey));
			}

			if (nodeId != 0) {
				itemSelectorCriteria.add(
					0, getWikiPageItemSelectorCriterion(nodeId));
			}
		}

		PortletURL itemSelectorURL = itemSelector.getItemSelectorURL(
			requestBackedPortletURLFactory, itemSelectedEventName,
			itemSelectorCriteria.toArray(new ItemSelectorCriterion[0]));

		jsonObject.put("documentBrowseLinkUrl", itemSelectorURL.toString());
	}

	protected abstract ItemSelectorReturnType getItemSelectorReturnType();

	protected ItemSelectorCriterion getWikiAttachmentItemSelectorCriterion(
		long wikiPageResourcePrimKey) {

		ItemSelectorCriterion itemSelectorCriterion =
			new WikiAttachmentItemSelectorCriterion(wikiPageResourcePrimKey);

		itemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new URLItemSelectorReturnType());

		return itemSelectorCriterion;
	}

	protected ItemSelectorCriterion getWikiPageItemSelectorCriterion(
		long nodeId) {

		ItemSelectorCriterion itemSelectorCriterion =
			new WikiPageItemSelectorCriterion(
				nodeId, WorkflowConstants.STATUS_APPROVED);

		itemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			getItemSelectorReturnType());

		return itemSelectorCriterion;
	}

	@Reference
	protected ItemSelector itemSelector;

}