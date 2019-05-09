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
import com.liferay.item.selector.ItemSelectorViewReturnTypeProvider;
import com.liferay.item.selector.criteria.URLItemSelectorReturnType;
import com.liferay.portal.kernel.editor.configuration.BaseEditorConfigContributor;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.wiki.item.selector.constants.WikiItemSelectorViewConstants;
import com.liferay.wiki.item.selector.criterion.WikiAttachmentItemSelectorCriterion;
import com.liferay.wiki.item.selector.criterion.WikiPageItemSelectorCriterion;

import java.util.List;
import java.util.Map;

import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Díaz
 */
public abstract class BaseWikiLinksCKEditorConfigContributor
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

		String filebrowserBrowseUrl = jsonObject.getString(
			"filebrowserBrowseUrl");

		String itemSelectedEventName = itemSelector.getItemSelectedEventName(
			filebrowserBrowseUrl);

		List<ItemSelectorCriterion> itemSelectorCriteria =
			itemSelector.getItemSelectorCriteria(filebrowserBrowseUrl);

		long wikiPageResourcePrimKey = GetterUtil.getLong(
			fileBrowserParamsMap.get("wikiPageResourcePrimKey"));

		if (wikiPageResourcePrimKey != 0) {
			itemSelectorCriteria.add(
				0,
				getWikiAttachmentItemSelectorCriterion(
					wikiPageResourcePrimKey));
		}

		long nodeId = GetterUtil.getLong(fileBrowserParamsMap.get("nodeId"));

		if (nodeId != 0) {
			itemSelectorCriteria.add(
				0, getWikiPageItemSelectorCriterion(nodeId));
		}

		PortletURL itemSelectorURL = itemSelector.getItemSelectorURL(
			requestBackedPortletURLFactory, itemSelectedEventName,
			itemSelectorCriteria.toArray(new ItemSelectorCriterion[0]));

		jsonObject.put("filebrowserBrowseUrl", itemSelectorURL.toString());
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

	@Reference(
		target = "(item.selector.view.key=" + WikiItemSelectorViewConstants.ITEM_SELECTOR_VIEW_KEY + ")"
	)
	protected ItemSelectorViewReturnTypeProvider
		itemSelectorViewReturnTypeProvider;

}