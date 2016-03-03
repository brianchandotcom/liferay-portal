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

package com.liferay.item.selector.upload.web.display.context;

import com.liferay.item.selector.criteria.upload.criterion.UploadItemSelectorCriterion;
import com.liferay.item.selector.upload.web.ItemSelectorUploadView;

import java.util.Locale;

/**
 * @author Ambrín Chaudhary
 */
public class ItemSelectorUploadViewDisplayContext {

	public ItemSelectorUploadViewDisplayContext(
		UploadItemSelectorCriterion uploadItemSelectorCriterion,
		ItemSelectorUploadView itemSelectorUploadView,
		String itemSelectedEventName) {

		_uploadItemSelectorCriterion = uploadItemSelectorCriterion;
		_itemSelectorUploadView = itemSelectorUploadView;
		_itemSelectedEventName = itemSelectedEventName;
	}

	public String getItemSelectedEventName() {
		return _itemSelectedEventName;
	}

	public long getMaxFileSize() {
		return _uploadItemSelectorCriterion.getMaxFileSize();
	}

	public String getRepositoryName() {
		return _uploadItemSelectorCriterion.getRepositoryName();
	}

	public String getTitle(Locale locale) {
		return _itemSelectorUploadView.getTitle(locale);
	}

	public String getURL() {
		return _uploadItemSelectorCriterion.getURL();
	}

	private final String _itemSelectedEventName;
	private final ItemSelectorUploadView _itemSelectorUploadView;
	private final UploadItemSelectorCriterion _uploadItemSelectorCriterion;

}