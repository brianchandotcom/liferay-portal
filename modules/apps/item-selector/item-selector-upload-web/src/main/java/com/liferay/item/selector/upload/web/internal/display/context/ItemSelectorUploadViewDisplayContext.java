/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public ItemSelectorReturnTypeResolver<?, ?>
		getItemSelectorReturnTypeResolver() {

		return _itemSelectorReturnTypeResolverHandler.
			getItemSelectorReturnTypeResolver(
				_uploadItemSelectorCriterion, _itemSelectorUploadView,
				FileEntry.class);
	}

	public long getMaxFileSize() {
		return _uploadItemSelectorCriterion.getMaxFileSize();
	}

	public String getMimeTypeRestriction() {
		return _uploadItemSelectorCriterion.getMimeTypeRestriction();
	}

	public String getNamespace() {
		if (Validator.isNotNull(_uploadItemSelectorCriterion.getPortletId())) {
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