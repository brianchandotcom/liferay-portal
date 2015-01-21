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

package com.liferay.document.library.google.docs.context;

import com.liferay.document.library.google.docs.util.GoogleDocsConstants;
import com.liferay.document.library.google.docs.util.GoogleDocsMetadataHelper;
import com.liferay.document.library.google.docs.util.ResourceUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.servlet.taglib.ui.MenuItem;
import com.liferay.portal.kernel.servlet.taglib.ui.ToolbarItem;
import com.liferay.portal.kernel.servlet.taglib.ui.UIItem;
import com.liferay.portal.kernel.servlet.taglib.ui.URLMenuItem;
import com.liferay.portal.kernel.servlet.taglib.ui.URLToolbarItem;
import com.liferay.portal.kernel.servlet.taglib.ui.URLUIItem;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.documentlibrary.context.DLUIItemKeys;

import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Ivan Zaera
 */
public class GoogleDocsUIItemsProcessor {

	public GoogleDocsUIItemsProcessor(
		HttpServletRequest request,
		GoogleDocsMetadataHelper googleDocsMetadataHelper) {

		_request = request;
		_googleDocsMetadataHelper = googleDocsMetadataHelper;
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

	private int _getIndex(List<? extends UIItem> uiItems, String key) {
		for (int i = 0; i < uiItems.size(); i++) {
			UIItem uiItem = uiItems.get(i);

			if (key.equals(uiItem.getKey())) {
				return i;
			}
		}

		return -1;
	}

	private <T extends URLUIItem> T _insertEditInGoogleURLUIItem(
		T urlUIItem, List<? super T> urlUIItems) {

		if (!_googleDocsMetadataHelper.containsField(
				GoogleDocsConstants.DDM_FIELD_NAME_URL)) {

			return urlUIItem;
		}

		int index = _getIndex(
			(List<? extends UIItem>)urlUIItems, DLUIItemKeys.EDIT);

		if (index == -1) {
			index = 0;
		}

		urlUIItem.setIcon("icon-edit");
		urlUIItem.setKey(GoogleDocsUIItemKeys.EDIT_IN_GOOGLE);

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		ResourceBundle resourceBundle = ResourceUtil.getResourceBundle(
			themeDisplay.getLocale());

		String message = LanguageUtil.get(
			resourceBundle, "edit-in-google-docs");

		urlUIItem.setLabel(message);

		urlUIItem.setTarget("_blank");

		String editURL = _googleDocsMetadataHelper.getFieldValue(
			GoogleDocsConstants.DDM_FIELD_NAME_URL);

		urlUIItem.setURL(editURL);

		urlUIItems.add(index, urlUIItem);

		return urlUIItem;
	}

	private void _removeUIItem(List<? extends UIItem> uiItems, String key) {
		int index = _getIndex(uiItems, key);

		if (index != -1) {
			uiItems.remove(index);
		}
	}

	private void _removeUnsupportedUIItems(List<? extends UIItem> uiItems) {
		_removeUIItem(uiItems, DLUIItemKeys.CANCEL_CHECKOUT);
		_removeUIItem(uiItems, DLUIItemKeys.CHECKIN);
		_removeUIItem(uiItems, DLUIItemKeys.CHECKOUT);
		_removeUIItem(uiItems, DLUIItemKeys.DOWNLOAD);
		_removeUIItem(uiItems, DLUIItemKeys.OPEN_IN_MS_OFFICE);
	}

	private final GoogleDocsMetadataHelper _googleDocsMetadataHelper;
	private final HttpServletRequest _request;

}