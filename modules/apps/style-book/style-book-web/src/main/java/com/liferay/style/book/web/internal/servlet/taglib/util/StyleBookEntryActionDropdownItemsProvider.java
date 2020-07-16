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

package com.liferay.style.book.web.internal.servlet.taglib.util;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType;
import com.liferay.item.selector.criteria.upload.criterion.UploadItemSelectorCriterion;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadServletRequestConfigurationHelperUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.style.book.constants.StyleBookPortletKeys;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.service.StyleBookEntryLocalServiceUtil;
import com.liferay.style.book.web.internal.constants.StyleBookWebKeys;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class StyleBookEntryActionDropdownItemsProvider {

	public StyleBookEntryActionDropdownItemsProvider(
		StyleBookEntry styleBookEntry, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		_styleBookEntry = styleBookEntry;
		_renderResponse = renderResponse;

		_httpServletRequest = PortalUtil.getHttpServletRequest(renderRequest);
		_itemSelector = (ItemSelector)renderRequest.getAttribute(
			StyleBookWebKeys.ITEM_SELECTOR);
		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<DropdownItem> getActionDropdownItems() {
		return DropdownItemListBuilder.add(
			_getEditStyleBookEntryActionUnsafeConsumer()
		).add(
			_getRenameStyleBookEntrytActionUnsafeConsumer()
		).add(
			_getCopyStyleBookEntryActionUnsafeConsumer()
		).add(
			_getUpdateStyleBookEntryPreviewActionUnsafeConsumer()
		).add(
			() -> _styleBookEntry.getPreviewFileEntryId() > 0,
			_getDeleteStyleBookEntryPreviewActionUnsafeConsumer()
		).add(
			_getExportStyleBookEntryActionUnsafeConsumer()
		).add(
			_getMarkAsDefaultStyleBookEntryActionUnsafeConsumer()
		).add(
			() -> {
				StyleBookEntry draftStyleBookEntry =
					StyleBookEntryLocalServiceUtil.fetchDraft(_styleBookEntry);

				if (draftStyleBookEntry != null) {
					return true;
				}

				return false;
			},
			_getDiscardDraftStyleBookEntryActionUnsafeConsumer()
		).add(
			_getDeleteStyleBookEntryActionUnsafeConsumer()
		).build();
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getCopyStyleBookEntryActionUnsafeConsumer() {

		PortletURL copyStyleBookEntryURL = _renderResponse.createActionURL();

		copyStyleBookEntryURL.setParameter(
			ActionRequest.ACTION_NAME, "/style_book/copy_style_book_entry");

		copyStyleBookEntryURL.setParameter(
			"redirect", _themeDisplay.getURLCurrent());
		copyStyleBookEntryURL.setParameter(
			"styleBookEntryIds",
			String.valueOf(_styleBookEntry.getStyleBookEntryId()));

		return dropdownItem -> {
			dropdownItem.putData("action", "copyStyleBookEntry");
			dropdownItem.putData(
				"copyStyleBookEntryURL", copyStyleBookEntryURL.toString());
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "make-a-copy"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getDeleteStyleBookEntryActionUnsafeConsumer() {

		PortletURL deleteStyleBookEntryURL = _renderResponse.createActionURL();

		deleteStyleBookEntryURL.setParameter(
			ActionRequest.ACTION_NAME, "/style_book/delete_style_book_entry");

		deleteStyleBookEntryURL.setParameter(
			"redirect", _themeDisplay.getURLCurrent());
		deleteStyleBookEntryURL.setParameter(
			"styleBookEntryId",
			String.valueOf(_styleBookEntry.getStyleBookEntryId()));

		return dropdownItem -> {
			dropdownItem.putData("action", "deleteStyleBookEntry");
			dropdownItem.putData(
				"deleteStyleBookEntryURL", deleteStyleBookEntryURL.toString());
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "delete"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getDeleteStyleBookEntryPreviewActionUnsafeConsumer() {

		PortletURL deleteStyleBookEntryPreviewURL =
			_renderResponse.createActionURL();

		deleteStyleBookEntryPreviewURL.setParameter(
			ActionRequest.ACTION_NAME,
			"/style_book/delete_style_book_entry_preview");

		deleteStyleBookEntryPreviewURL.setParameter(
			"redirect", _themeDisplay.getURLCurrent());
		deleteStyleBookEntryPreviewURL.setParameter(
			"styleBookEntryId",
			String.valueOf(_styleBookEntry.getStyleBookEntryId()));

		return dropdownItem -> {
			dropdownItem.putData("action", "deleteStyleBookEntryPreview");
			dropdownItem.putData(
				"deleteStyleBookEntryPreviewURL",
				deleteStyleBookEntryPreviewURL.toString());
			dropdownItem.putData(
				"styleBookEntryId",
				String.valueOf(_styleBookEntry.getStyleBookEntryId()));
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "remove-thumbnail"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getDiscardDraftStyleBookEntryActionUnsafeConsumer() {

		PortletURL discardDraftStyleBookEntryURL =
			_renderResponse.createActionURL();

		discardDraftStyleBookEntryURL.setParameter(
			ActionRequest.ACTION_NAME,
			"/style_book/discard_draft_style_book_entry");

		discardDraftStyleBookEntryURL.setParameter(
			"redirect", _themeDisplay.getURLCurrent());
		discardDraftStyleBookEntryURL.setParameter(
			"styleBookEntryId",
			String.valueOf(_styleBookEntry.getStyleBookEntryId()));

		return dropdownItem -> {
			dropdownItem.putData("action", "discardDraftStyleBookEntry");
			dropdownItem.putData(
				"discardDraftStyleBookEntryURL",
				discardDraftStyleBookEntryURL.toString());
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "discard-draft"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getEditStyleBookEntryActionUnsafeConsumer() {

		return dropdownItem -> {
			dropdownItem.setHref(
				_renderResponse.createRenderURL(), "mvcRenderCommandName",
				"/style_book/edit_style_book_entry", "redirect",
				_themeDisplay.getURLCurrent(), "styleBookEntryId",
				_styleBookEntry.getStyleBookEntryId());
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "edit"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getExportStyleBookEntryActionUnsafeConsumer() {

		ResourceURL exportStyleBookEntryURL =
			_renderResponse.createResourceURL();

		exportStyleBookEntryURL.setParameter(
			"styleBookEntryId",
			String.valueOf(_styleBookEntry.getStyleBookEntryId()));
		exportStyleBookEntryURL.setResourceID(
			"/style_book/export_style_book_entries");

		return dropdownItem -> {
			dropdownItem.setHref(exportStyleBookEntryURL);
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "export"));
		};
	}

	private String _getItemSelectorURL() {
		PortletURL uploadURL = _renderResponse.createActionURL();

		uploadURL.setParameter(
			ActionRequest.ACTION_NAME,
			"/style_book/upload_style_book_entry_preview");
		uploadURL.setParameter(
			"styleBookEntryId",
			String.valueOf(_styleBookEntry.getStyleBookEntryId()));

		ItemSelectorCriterion itemSelectorCriterion =
			new UploadItemSelectorCriterion(
				StyleBookPortletKeys.STYLE_BOOK, uploadURL.toString(),
				LanguageUtil.get(_httpServletRequest, "style-book"),
				UploadServletRequestConfigurationHelperUtil.getMaxSize());

		itemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new FileEntryItemSelectorReturnType());

		PortletURL itemSelectorURL = _itemSelector.getItemSelectorURL(
			RequestBackedPortletURLFactoryUtil.create(_httpServletRequest),
			_renderResponse.getNamespace() + "changePreview",
			itemSelectorCriterion);

		return itemSelectorURL.toString();
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getMarkAsDefaultStyleBookEntryActionUnsafeConsumer() {

		PortletURL markAsDefaultStyleBookEntryURL =
			_renderResponse.createActionURL();

		markAsDefaultStyleBookEntryURL.setParameter(
			ActionRequest.ACTION_NAME,
			"/style_book/update_style_book_entry_default");

		markAsDefaultStyleBookEntryURL.setParameter(
			"redirect", _themeDisplay.getURLCurrent());
		markAsDefaultStyleBookEntryURL.setParameter(
			"styleBookEntryId",
			String.valueOf(_styleBookEntry.getStyleBookEntryId()));
		markAsDefaultStyleBookEntryURL.setParameter(
			"defaultStyleBookEntry",
			String.valueOf(!_styleBookEntry.isDefaultStyleBookEntry()));

		return dropdownItem -> {
			dropdownItem.putData("action", "markAsDefaultStyleBookEntry");
			dropdownItem.putData(
				"markAsDefaultStyleBookEntryURL",
				markAsDefaultStyleBookEntryURL.toString());

			String message = StringPool.BLANK;

			StyleBookEntry defaultLStyleBookEntry =
				StyleBookEntryLocalServiceUtil.fetchDefaultStyleBookEntry(
					_styleBookEntry.getGroupId());

			if (defaultLStyleBookEntry != null) {
				long defaultLStyleBookEntryId =
					defaultLStyleBookEntry.getStyleBookEntryId();
				long styleBookEntryId = _styleBookEntry.getStyleBookEntryId();

				if (defaultLStyleBookEntryId != styleBookEntryId) {
					message = LanguageUtil.format(
						_httpServletRequest,
						"do-you-want-to-replace-x-for-x-as-the-default-style-" +
							"book",
						new String[] {
							_styleBookEntry.getName(),
							defaultLStyleBookEntry.getName()
						});
				}
			}

			dropdownItem.putData("message", message);

			String label = "mark-as-default";

			if (_styleBookEntry.isDefaultStyleBookEntry()) {
				label = "unmark-as-default";
			}

			dropdownItem.setLabel(LanguageUtil.get(_httpServletRequest, label));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getRenameStyleBookEntrytActionUnsafeConsumer() {

		PortletURL updateStyleBookEntryURL = _renderResponse.createActionURL();

		updateStyleBookEntryURL.setParameter(
			ActionRequest.ACTION_NAME,
			"/style_book/update_style_book_entry_name");

		updateStyleBookEntryURL.setParameter(
			"redirect", _themeDisplay.getURLCurrent());
		updateStyleBookEntryURL.setParameter(
			"styleBookEntryId",
			String.valueOf(_styleBookEntry.getStyleBookEntryId()));

		return dropdownItem -> {
			dropdownItem.putData("action", "renameStyleBookEntry");
			dropdownItem.putData(
				"styleBookEntryId",
				String.valueOf(_styleBookEntry.getStyleBookEntryId()));
			dropdownItem.putData(
				"styleBookEntryName", _styleBookEntry.getName());
			dropdownItem.putData(
				"updateStyleBookEntryURL", updateStyleBookEntryURL.toString());
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "rename"));
		};
	}

	private UnsafeConsumer<DropdownItem, Exception>
		_getUpdateStyleBookEntryPreviewActionUnsafeConsumer() {

		return dropdownItem -> {
			dropdownItem.putData("action", "updateStyleBookEntryPreview");
			dropdownItem.putData("itemSelectorURL", _getItemSelectorURL());
			dropdownItem.putData(
				"styleBookEntryId",
				String.valueOf(_styleBookEntry.getStyleBookEntryId()));
			dropdownItem.setLabel(
				LanguageUtil.get(_httpServletRequest, "change-thumbnail"));
		};
	}

	private final HttpServletRequest _httpServletRequest;
	private final ItemSelector _itemSelector;
	private final RenderResponse _renderResponse;
	private final StyleBookEntry _styleBookEntry;
	private final ThemeDisplay _themeDisplay;

}