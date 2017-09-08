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

package com.liferay.adaptive.media.journal.editor.configuration.internal;

import com.liferay.adaptive.media.image.item.selector.AMImageFileEntryItemSelectorReturnType;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.file.criterion.FileItemSelectorCriterion;
import com.liferay.item.selector.criteria.image.criterion.ImageItemSelectorCriterion;
import com.liferay.item.selector.criteria.upload.criterion.UploadItemSelectorCriterion;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.item.selector.criterion.JournalItemSelectorCriterion;
import com.liferay.portal.kernel.editor.configuration.BaseEditorConfigContributor;
import com.liferay.portal.kernel.editor.configuration.EditorConfigContributor;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(
	property = {
		"editor.name=alloyeditor", "editor.name=ckeditor",
		"javax.portlet.name=" + JournalPortletKeys.JOURNAL,
		"service.ranking:Integer=101"
	},
	service = EditorConfigContributor.class
)
public class AMJournalEditorConfigContributor
	extends BaseEditorConfigContributor {

	@Override
	public void populateConfigJSONObject(
		JSONObject jsonObject, Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		RequestBackedPortletURLFactory requestBackedPortletURLFactory) {

		String allowedContent = jsonObject.getString("allowedContent");

		if (Validator.isNotNull(allowedContent) &&
			!allowedContent.equals(Boolean.TRUE.toString())) {

			allowedContent += StringPool.SPACE + _IMG_TAG_RULE;

			jsonObject.put("allowedContent", allowedContent);
		}

		String itemSelectorURL = jsonObject.getString(
			"filebrowserImageBrowseLinkUrl");

		if (Validator.isNull(itemSelectorURL)) {
			return;
		}

		List<ItemSelectorCriterion> itemSelectorCriteria =
			_itemSelector.getItemSelectorCriteria(itemSelectorURL);

		boolean amImageURLItemSelectorReturnTypeAdded = false;

		for (ItemSelectorCriterion itemSelectorCriterion :
				itemSelectorCriteria) {

			if (itemSelectorCriterion instanceof FileItemSelectorCriterion ||
				itemSelectorCriterion instanceof ImageItemSelectorCriterion ||
				itemSelectorCriterion instanceof JournalItemSelectorCriterion ||
				itemSelectorCriterion instanceof UploadItemSelectorCriterion) {

				addAMImageFileEntryItemSelectorReturnType(
					itemSelectorCriterion);

				amImageURLItemSelectorReturnTypeAdded = true;
			}
		}

		if (!amImageURLItemSelectorReturnTypeAdded) {
			return;
		}

		String extraPlugins = jsonObject.getString("extraPlugins");

		if (Validator.isNotNull(extraPlugins)) {
			extraPlugins = extraPlugins + ",adaptivemedia";
		}
		else {
			extraPlugins = "adaptivemedia";
		}

		jsonObject.put("extraPlugins", extraPlugins);

		String itemSelectedEventName = _itemSelector.getItemSelectedEventName(
			itemSelectorURL);

		PortletURL itemSelectorPortletURL = _itemSelector.getItemSelectorURL(
			requestBackedPortletURLFactory, itemSelectedEventName,
			itemSelectorCriteria.toArray(
				new ItemSelectorCriterion[itemSelectorCriteria.size()]));

		jsonObject.put(
			"filebrowserImageBrowseLinkUrl", itemSelectorPortletURL.toString());
		jsonObject.put(
			"filebrowserImageBrowseUrl", itemSelectorPortletURL.toString());
	}

	@Reference(unbind = "-")
	public void setItemSelector(ItemSelector itemSelector) {
		_itemSelector = itemSelector;
	}

	protected void addAMImageFileEntryItemSelectorReturnType(
		ItemSelectorCriterion itemSelectorCriterion) {

		List<ItemSelectorReturnType> desiredItemSelectorReturnTypes =
			new ArrayList<>();

		desiredItemSelectorReturnTypes.add(
			new AMImageFileEntryItemSelectorReturnType());
		desiredItemSelectorReturnTypes.addAll(
			itemSelectorCriterion.getDesiredItemSelectorReturnTypes());

		itemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			desiredItemSelectorReturnTypes);
	}

	private static final String _IMG_TAG_RULE = "img[*](*){*};";

	private ItemSelector _itemSelector;

}