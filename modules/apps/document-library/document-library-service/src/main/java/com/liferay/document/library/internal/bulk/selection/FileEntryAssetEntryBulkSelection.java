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
import com.liferay.bulk.selection.BulkSelection;
import com.liferay.bulk.selection.BulkSelectionFactory;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.util.DLAssetHelper;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;

import java.io.Serializable;

import java.util.Map;

/**
 * @author Adolfo Pérez
 */
public class FileEntryAssetEntryBulkSelection
	implements BulkSelection<AssetEntry> {

	public FileEntryAssetEntryBulkSelection(
		BulkSelection<FileEntry> fileEntryBulkSelection,
		AssetEntryLocalService assetEntryLocalService,
		DLAssetHelper dlAssetHelper) {

		_fileEntryBulkSelection = fileEntryBulkSelection;
		_assetEntryLocalService = assetEntryLocalService;
		_dlAssetHelper = dlAssetHelper;
	}

	@Override
	public <E extends PortalException> void forEach(
			UnsafeConsumer<AssetEntry, E> unsafeConsumer)
		throws PortalException {

		_fileEntryBulkSelection.forEach(
			fileEntry -> unsafeConsumer.accept(_toAssetEntry(fileEntry)));
	}

	@Override
	public Class<? extends BulkSelectionFactory>
		getBulkSelectionFactoryClass() {

		return _fileEntryBulkSelection.getBulkSelectionFactoryClass();
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		return _fileEntryBulkSelection.getParameterMap();
	}

	@Override
	public long getSize() throws PortalException {
		return _fileEntryBulkSelection.getSize();
	}

	@Override
	public Serializable serialize() {
		return _fileEntryBulkSelection.serialize();
	}

	@Override
	public BulkSelection<AssetEntry> toAssetEntryBulkSelection() {
		return this;
	}

	private AssetEntry _toAssetEntry(FileEntry fileEntry) {
		try {
			return _assetEntryLocalService.getEntry(
				DLFileEntryConstants.getClassName(),
				_dlAssetHelper.getAssetClassPK(
					fileEntry, fileEntry.getLatestFileVersion()));
		}
		catch (PortalException pe) {
			return ReflectionUtil.throwException(pe);
		}
	}

	private final AssetEntryLocalService _assetEntryLocalService;
	private final DLAssetHelper _dlAssetHelper;
	private final BulkSelection<FileEntry> _fileEntryBulkSelection;

}