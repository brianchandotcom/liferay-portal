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

package com.liferay.document.library.google.docs.internal.display.context;

import com.liferay.document.library.display.context.DLUIItemKeys;
import com.liferay.document.library.google.docs.internal.helper.GoogleDocsMetadataHelper;
import com.liferay.document.library.google.docs.internal.util.constants.GoogleDocsConstants;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemBuilder;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.servlet.taglib.ui.MenuItem;
import com.liferay.portal.kernel.servlet.taglib.ui.ToolbarItem;
import com.liferay.portal.kernel.servlet.taglib.ui.UIItem;
import com.liferay.portal.kernel.servlet.taglib.ui.URLMenuItem;
import com.liferay.portal.kernel.servlet.taglib.ui.URLToolbarItem;
import com.liferay.portal.kernel.servlet.taglib.ui.URLUIItem;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Iván Zaera
 */
public class GoogleDocsUIItemsProcessor {

	public GoogleDocsUIItemsProcessor(
		HttpServletRequest httpServletRequest,
		GoogleDocsMetadataHelper googleDocsMetadataHelper) {

		_httpServletRequest = httpServletRequest;
		_googleDocsMetadataHelper = googleDocsMetadataHelper;
	}

	public void processDropdownItems(List<DropdownItem> dropdownItems) {
		_removeUnsupportedDropdownItems(dropdownItems);

		_insertEditInGoogleDropdownItem(dropdownItems);
	}

	public void processMenuItems(List<MenuItem> menuItems) {
		_removeUnsupportedUIItems(menuItems);

		URLMenuItem urlMenuItem = _insertEditInGoogleURLUIItem(
			new URLMenuItem(), menuItems);

		urlMenuItem.setMethod("GET");
	}

	public void processToolbarItems(List<ToolbarItem> toolbarItems) {
		_removeUnsupportedUIItems(toolbarItems);

		_insertEditInGoogleURLUIItem(new URLToolbarItem(), toolbarItems);
	}

	private <T> int _getIndex(List<T> items, Predicate<T> predicate) {
		for (int i = 0; i < items.size(); i++) {
			if (predicate.test(items.get(i))) {
				return i;
			}
		}

		return -1;
	}

	private void _insertEditInGoogleDropdownItem(
		List<DropdownItem> dropdownItems) {

		if (!_googleDocsMetadataHelper.containsField(
				GoogleDocsConstants.DDM_FIELD_NAME_URL)) {

			return;
		}

		int index = _getIndex(
			dropdownItems,
			dropdownItem -> Objects.equals(
				dropdownItem.get("key"), DLUIItemKeys.EDIT));

		if (index == -1) {
			index = 0;
		}

		dropdownItems.add(
			index,
			DropdownItemBuilder.setHref(
				_googleDocsMetadataHelper.getFieldValue(
					GoogleDocsConstants.DDM_FIELD_NAME_URL)
			).setKey(
				GoogleDocsUIItemKeys.EDIT_IN_GOOGLE
			).setLabel(
				() -> {
					ThemeDisplay themeDisplay =
						(ThemeDisplay)_httpServletRequest.getAttribute(
							WebKeys.THEME_DISPLAY);

					return LanguageUtil.get(
						themeDisplay.getLocale(), "edit-in-google-drive");
				}
			).setTarget(
				"_blank"
			).build());
	}

	private <T extends URLUIItem> T _insertEditInGoogleURLUIItem(
		T urlUIItem, List<? super T> urlUIItems) {

		if (!_googleDocsMetadataHelper.containsField(
				GoogleDocsConstants.DDM_FIELD_NAME_URL)) {

			return urlUIItem;
		}

		int index = _getIndex(
			(List<? extends UIItem>)urlUIItems,
			uiItem -> Objects.equals(uiItem.getKey(), DLUIItemKeys.EDIT));

		if (index == -1) {
			index = 0;
		}

		urlUIItem.setKey(GoogleDocsUIItemKeys.EDIT_IN_GOOGLE);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", themeDisplay.getLocale(), getClass());

		String message = LanguageUtil.get(
			resourceBundle, "edit-in-google-drive");

		urlUIItem.setLabel(message);

		urlUIItem.setTarget("_blank");

		String editURL = _googleDocsMetadataHelper.getFieldValue(
			GoogleDocsConstants.DDM_FIELD_NAME_URL);

		urlUIItem.setURL(editURL);

		urlUIItems.add(index, urlUIItem);

		return urlUIItem;
	}

	private <T> void _removeItems(
		List<T> items, Function<T, String> function, Set<String> keys) {

		Iterator<T> iterator = items.iterator();

		while (iterator.hasNext()) {
			T item = iterator.next();

			if (keys.contains(function.apply(item))) {
				iterator.remove();
			}
		}
	}

	private void _removeUnsupportedDropdownItems(
		List<DropdownItem> dropdownItems) {

		_removeItems(
			dropdownItems, dropdownItem -> (String)dropdownItem.get("key"),
			SetUtil.fromArray(
				DLUIItemKeys.CANCEL_CHECKOUT, DLUIItemKeys.CHECKIN,
				DLUIItemKeys.CHECKOUT, DLUIItemKeys.DOWNLOAD,
				DLUIItemKeys.OPEN_IN_MS_OFFICE, "#edit-in-google-drive"));
	}

	private void _removeUnsupportedUIItems(List<? extends UIItem> uiItems) {
		_removeItems(
			uiItems, UIItem::getKey,
			SetUtil.fromArray(
				DLUIItemKeys.CANCEL_CHECKOUT, DLUIItemKeys.CHECKIN,
				DLUIItemKeys.CHECKOUT, DLUIItemKeys.DOWNLOAD,
				DLUIItemKeys.OPEN_IN_MS_OFFICE, "#edit-in-google-drive"));
	}

	private final GoogleDocsMetadataHelper _googleDocsMetadataHelper;
	private final HttpServletRequest _httpServletRequest;

}