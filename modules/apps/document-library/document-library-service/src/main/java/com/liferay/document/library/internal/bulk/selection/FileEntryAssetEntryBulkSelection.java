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
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;

import java.io.Serializable;

import java.util.Locale;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author Adolfo Pérez
 */
public class FileEntryAssetEntryBulkSelection
	implements BulkSelection<AssetEntry> {

	public FileEntryAssetEntryBulkSelection(
		BulkSelection<FileEntry> fileEntryBulkSelection,
		AssetEntryLocalService assetEntryLocalService) {

		_fileEntryBulkSelection = fileEntryBulkSelection;
		_assetEntryLocalService = assetEntryLocalService;
	}

	@Override
	public String describe(Locale locale) throws PortalException {
		return _fileEntryBulkSelection.describe(locale);
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
	public boolean isMultiple() {
		return _fileEntryBulkSelection.isMultiple();
	}

	@Override
	public Serializable serialize() {
		return _fileEntryBulkSelection.serialize();
	}

	@Override
	public Stream<AssetEntry> stream() throws PortalException {
		Stream<FileEntry> fileEntryStream = _fileEntryBulkSelection.stream();

		return fileEntryStream.map(this::_toAssetEntry);
	}

	@Override
	public BulkSelection<AssetEntry> toAssetEntryBulkSelection() {
		return this;
	}

	private AssetEntry _toAssetEntry(FileEntry fileEntry) {
		try {
			return _assetEntryLocalService.getEntry(
				DLFileEntryConstants.getClassName(),
				fileEntry.getFileEntryId());
		}
		catch (PortalException pe) {
			return ReflectionUtil.throwException(pe);
		}
	}

	private final AssetEntryLocalService _assetEntryLocalService;
	private final BulkSelection<FileEntry> _fileEntryBulkSelection;

}