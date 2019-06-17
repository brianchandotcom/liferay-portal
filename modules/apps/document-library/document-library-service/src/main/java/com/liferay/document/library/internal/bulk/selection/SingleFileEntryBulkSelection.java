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

package com.liferay.document.library.internal.bulk.selection;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.bulk.selection.BaseSingleEntryBulkSelection;
import com.liferay.bulk.selection.BulkSelection;
import com.liferay.bulk.selection.BulkSelectionFactory;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.util.DLAssetHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;

import java.util.Map;

/**
 * @author Adolfo Pérez
 */
public class SingleFileEntryBulkSelection
	extends BaseSingleEntryBulkSelection<FileEntry> {

	public SingleFileEntryBulkSelection(
		long fileEntryId, Map<String, String[]> parameterMap,
		DLAppService dlAppService,
		AssetEntryLocalService assetEntryLocalService,
		DLAssetHelper dlAssetHelper) {

		super(fileEntryId, parameterMap);

		_fileEntryId = fileEntryId;
		_dlAppService = dlAppService;
		_assetEntryLocalService = assetEntryLocalService;
		_dlAssetHelper = dlAssetHelper;
	}

	@Override
	public Class<? extends BulkSelectionFactory>
		getBulkSelectionFactoryClass() {

		return FileEntryBulkSelectionFactory.class;
	}

	@Override
	public BulkSelection<AssetEntry> toAssetEntryBulkSelection() {
		return new FileEntryAssetEntryBulkSelection(
			this, _assetEntryLocalService, _dlAssetHelper);
	}

	@Override
	protected FileEntry getEntry() throws PortalException {
		return _dlAppService.getFileEntry(_fileEntryId);
	}

	@Override
	protected String getEntryName() throws PortalException {
		FileEntry fileEntry = getEntry();

		return fileEntry.getTitle();
	}

	private final AssetEntryLocalService _assetEntryLocalService;
	private final DLAppService _dlAppService;
	private final DLAssetHelper _dlAssetHelper;
	private final long _fileEntryId;

}