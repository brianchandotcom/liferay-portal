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
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.style.book.model.StyleBookEntry;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

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

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public List<DropdownItem> getActionDropdownItems() {
		return DropdownItemListBuilder.add(
			_getRenameStyleBookEntrytActionUnsafeConsumer()
		).add(
			_getDeleteStyleBookEntryActionUnsafeConsumer()
		).build();
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
		_getRenameStyleBookEntrytActionUnsafeConsumer() {

		PortletURL updateStyleBookEntryURL = _renderResponse.createActionURL();

		updateStyleBookEntryURL.setParameter(
			ActionRequest.ACTION_NAME, "/style_book/update_style_book_entry");

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

	private final HttpServletRequest _httpServletRequest;
	private final RenderResponse _renderResponse;
	private final StyleBookEntry _styleBookEntry;
	private final ThemeDisplay _themeDisplay;

}