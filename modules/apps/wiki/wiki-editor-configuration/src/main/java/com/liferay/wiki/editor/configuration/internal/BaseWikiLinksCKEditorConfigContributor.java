/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
				0, _getWikiPageItemSelectorCriterion(nodeId));
		}

		jsonObject.put(
			"filebrowserBrowseUrl",
			String.valueOf(
				itemSelector.getItemSelectorURL(
					requestBackedPortletURLFactory, itemSelectedEventName,
					itemSelectorCriteria.toArray(
						new ItemSelectorCriterion[0]))));
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

	@Reference
	protected ItemSelector itemSelector;

	@Reference(
		target = "(item.selector.view.key=" + WikiItemSelectorViewConstants.ITEM_SELECTOR_VIEW_KEY + ")"
	)
	protected ItemSelectorViewReturnTypeProvider
		itemSelectorViewReturnTypeProvider;

	private ItemSelectorCriterion _getWikiPageItemSelectorCriterion(
		long nodeId) {

		ItemSelectorCriterion itemSelectorCriterion =
			new WikiPageItemSelectorCriterion(
				nodeId, WorkflowConstants.STATUS_APPROVED);

		itemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			getItemSelectorReturnType());

		return itemSelectorCriterion;
	}

}