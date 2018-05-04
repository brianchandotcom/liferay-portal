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

package com.liferay.item.selector.upload.web.internal.display.context;

import com.liferay.item.selector.ItemSelectorReturnTypeResolver;
import com.liferay.item.selector.ItemSelectorReturnTypeResolverHandler;
import com.liferay.item.selector.criteria.upload.criterion.UploadItemSelectorCriterion;
import com.liferay.item.selector.upload.web.internal.ItemSelectorUploadView;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;

/**
 * @author Ambrín Chaudhary
 */
public class ItemSelectorUploadViewDisplayContext {

	public ItemSelectorUploadViewDisplayContext(
		UploadItemSelectorCriterion uploadItemSelectorCriterion,
		ItemSelectorUploadView itemSelectorUploadView,
		String itemSelectedEventName,
		ItemSelectorReturnTypeResolverHandler
			itemSelectorReturnTypeResolverHandler) {

		_uploadItemSelectorCriterion = uploadItemSelectorCriterion;
		_itemSelectorUploadView = itemSelectorUploadView;
		_itemSelectedEventName = itemSelectedEventName;
		_itemSelectorReturnTypeResolverHandler =
			itemSelectorReturnTypeResolverHandler;
	}

	public String[] getExtensions() {
		return _uploadItemSelectorCriterion.getExtensions();
	}

	public String getItemSelectedEventName() {
		return _itemSelectedEventName;
	}

	public ItemSelectorReturnTypeResolver getItemSelectorReturnTypeResolver() {
		return _itemSelectorReturnTypeResolverHandler.
			getItemSelectorReturnTypeResolver(
				_uploadItemSelectorCriterion, _itemSelectorUploadView,
				FileEntry.class);
	}

	public long getMaxFileSize() {
		return _uploadItemSelectorCriterion.getMaxFileSize();
	}

	public String getNamespace() {
		String portletId = _uploadItemSelectorCriterion.getPortletId();

		if (Validator.isNotNull(portletId)) {
			return PortalUtil.getPortletNamespace(
				_uploadItemSelectorCriterion.getPortletId());
		}

		return StringPool.BLANK;
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
	private final ItemSelectorReturnTypeResolverHandler
		_itemSelectorReturnTypeResolverHandler;
	private final ItemSelectorUploadView _itemSelectorUploadView;
	private final UploadItemSelectorCriterion _uploadItemSelectorCriterion;

}