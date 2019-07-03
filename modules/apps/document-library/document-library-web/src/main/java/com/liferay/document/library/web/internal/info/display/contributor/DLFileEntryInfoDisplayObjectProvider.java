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

package com.liferay.document.library.web.internal.info.display.contributor;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.info.display.contributor.InfoDisplayObjectProvider;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.Locale;

/**
 * @author Jürgen Kappler
 */
public class DLFileEntryInfoDisplayObjectProvider
	implements InfoDisplayObjectProvider<FileEntry> {

	public DLFileEntryInfoDisplayObjectProvider(FileEntry fileEntry) {
		_assetEntry = _getAssetEntry(fileEntry);
		_fileEntry = fileEntry;
	}

	@Override
	public long getClassNameId() {
		return PortalUtil.getClassNameId(DLFileEntryConstants.getClassName());
	}

	@Override
	public long getClassPK() {
		return _fileEntry.getFileEntryId();
	}

	@Override
	public long getClassTypeId() {
		if (_assetEntry != null) {
			return _assetEntry.getClassTypeId();
		}

		return 0;
	}

	@Override
	public String getDescription(Locale locale) {
		return _fileEntry.getDescription();
	}

	@Override
	public FileEntry getDisplayObject() {
		return _fileEntry;
	}

	@Override
	public long getGroupId() {
		return _fileEntry.getGroupId();
	}

	@Override
	public String getKeywords(Locale locale) {
		return StringPool.BLANK;
	}

	@Override
	public String getTitle(Locale locale) {
		return _fileEntry.getTitle();
	}

	@Override
	public String getURLTitle(Locale locale) {
		return String.valueOf(_fileEntry.getFileEntryId());
	}

	private AssetEntry _getAssetEntry(FileEntry fileEntry) {
		AssetRendererFactory assetRendererFactory =
			AssetRendererFactoryRegistryUtil.
				getAssetRendererFactoryByClassNameId(getClassNameId());

		if (assetRendererFactory == null) {
			return null;
		}

		try {
			AssetRenderer assetRenderer = assetRendererFactory.getAssetRenderer(
				fileEntry.getFileEntryId());

			return assetRendererFactory.getAssetEntry(
				DLFileEntryConstants.getClassName(),
				assetRenderer.getClassPK());
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}

			return null;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DLFileEntryInfoDisplayObjectProvider.class);

	private final AssetEntry _assetEntry;
	private final FileEntry _fileEntry;

}