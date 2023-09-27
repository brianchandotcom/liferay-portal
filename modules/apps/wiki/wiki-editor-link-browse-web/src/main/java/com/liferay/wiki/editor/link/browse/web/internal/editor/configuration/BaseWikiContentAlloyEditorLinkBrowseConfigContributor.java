/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
					_getWikiPageItemSelectorCriterion(nodeId));
			}

			if (wikiPageResourcePrimKey == 0) {
				itemSelectorCriteria.add(
					_getWikiAttachmentItemSelectorCriterion(
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
					_getWikiAttachmentItemSelectorCriterion(
						wikiPageResourcePrimKey));
			}

			if (nodeId != 0) {
				itemSelectorCriteria.add(
					0, _getWikiPageItemSelectorCriterion(nodeId));
			}
		}

		jsonObject.put(
			"documentBrowseLinkUrl",
			String.valueOf(
				itemSelector.getItemSelectorURL(
					requestBackedPortletURLFactory, itemSelectedEventName,
					itemSelectorCriteria.toArray(
						new ItemSelectorCriterion[0]))));
	}

	protected abstract ItemSelectorReturnType getItemSelectorReturnType();

	@Reference
	protected ItemSelector itemSelector;

	private ItemSelectorCriterion _getWikiAttachmentItemSelectorCriterion(
		long wikiPageResourcePrimKey) {

		ItemSelectorCriterion itemSelectorCriterion =
			new WikiAttachmentItemSelectorCriterion(wikiPageResourcePrimKey);

		itemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new URLItemSelectorReturnType());

		return itemSelectorCriterion;
	}

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