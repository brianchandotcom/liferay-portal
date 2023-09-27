/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.journal.editor.configuration.internal;

import com.liferay.adaptive.media.image.html.constants.AMImageHTMLConstants;
import com.liferay.adaptive.media.image.item.selector.AMImageFileEntryItemSelectorReturnType;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.file.criterion.FileItemSelectorCriterion;
import com.liferay.item.selector.criteria.image.criterion.ImageItemSelectorCriterion;
import com.liferay.item.selector.criteria.upload.criterion.UploadItemSelectorCriterion;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.item.selector.criterion.JournalItemSelectorCriterion;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.editor.configuration.BaseEditorConfigContributor;
import com.liferay.portal.kernel.editor.configuration.EditorConfigContributor;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;

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

		jsonObject.put(
			"adaptiveMediaFileEntryAttributeName",
			AMImageHTMLConstants.ATTRIBUTE_NAME_FILE_ENTRY_ID);

		String extraPlugins = jsonObject.getString("extraPlugins");

		if (Validator.isNotNull(extraPlugins)) {
			extraPlugins = extraPlugins + ",adaptivemedia";
		}
		else {
			extraPlugins = "adaptivemedia";
		}

		jsonObject.put("extraPlugins", extraPlugins);

		PortletURL itemSelectorPortletURL = _itemSelector.getItemSelectorURL(
			requestBackedPortletURLFactory,
			_itemSelector.getItemSelectedEventName(itemSelectorURL),
			itemSelectorCriteria.toArray(new ItemSelectorCriterion[0]));

		jsonObject.put(
			"filebrowserImageBrowseLinkUrl", itemSelectorPortletURL.toString()
		).put(
			"filebrowserImageBrowseUrl", itemSelectorPortletURL.toString()
		);
	}

	protected void addAMImageFileEntryItemSelectorReturnType(
		ItemSelectorCriterion itemSelectorCriterion) {

		List<ItemSelectorReturnType> desiredItemSelectorReturnTypes =
			itemSelectorCriterion.getDesiredItemSelectorReturnTypes();

		desiredItemSelectorReturnTypes.add(
			new AMImageFileEntryItemSelectorReturnType());
	}

	private static final String _IMG_TAG_RULE = "img[*](*){*};";

	@Reference
	private ItemSelector _itemSelector;

}