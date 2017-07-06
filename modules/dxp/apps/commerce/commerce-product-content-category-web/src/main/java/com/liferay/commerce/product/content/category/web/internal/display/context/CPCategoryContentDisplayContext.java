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

package com.liferay.commerce.product.content.category.web.internal.display.context;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.service.CPAttachmentFileEntryService;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marco Leo
 */
public class CPCategoryContentDisplayContext {

	public CPCategoryContentDisplayContext(
			HttpServletRequest httpServletRequest,
			CPAttachmentFileEntryService cpAttachmentFileEntryService,
			Portal portal)
		throws ConfigurationException {

		_httpServletRequest = httpServletRequest;
		_cpAttachmentFileEntryService = cpAttachmentFileEntryService;
		_portal = portal;
	}

	public AssetCategory getAssetCategory() {
		if (_assetCategory != null) {
			return _assetCategory;
		}

		_assetCategory = (AssetCategory)_httpServletRequest.getAttribute(
			WebKeys.ASSET_CATEGORY);

		return _assetCategory;
	}

	public String getDefaultImageSrc(ThemeDisplay themeDisplay)
		throws Exception {

		AssetCategory assetCategory = getAssetCategory();

		if (assetCategory == null) {
			return null;
		}

		CPAttachmentFileEntry cpAttachmentFileEntry = null;

		long classNameId = _portal.getClassNameId(AssetCategory.class);

		List<CPAttachmentFileEntry> cpAttachmentFileEntries =
			_cpAttachmentFileEntryService.getCPAttachmentFileEntries(
				classNameId, assetCategory.getCategoryId(),
				CPConstants.ATTACHMENT_FILE_ENTRY_TYPE_IMAGE, 0, 1);

		if (cpAttachmentFileEntries.isEmpty()) {
			return null;
		}

		cpAttachmentFileEntry = cpAttachmentFileEntries.get(0);

		if (cpAttachmentFileEntry == null) {
			return null;
		}

		FileEntry fileEntry = cpAttachmentFileEntry.getFileEntry();

		if (fileEntry == null) {
			return null;
		}

		return DLUtil.getDownloadURL(
			fileEntry, fileEntry.getFileVersion(), themeDisplay, "");
	}

	private AssetCategory _assetCategory;
	private final CPAttachmentFileEntryService _cpAttachmentFileEntryService;
	private final HttpServletRequest _httpServletRequest;
	private final Portal _portal;

}