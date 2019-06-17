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
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.util.DLAssetHelper;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.RepositoryProvider;
import com.liferay.portal.kernel.repository.model.BaseRepositoryModelOperation;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.RepositoryModelOperation;

import java.util.Map;

/**
 * @author Adolfo Pérez
 */
public class FolderFileEntryBulkSelection
	extends BaseFolderEntryBulkSelection<FileEntry> {

	public FolderFileEntryBulkSelection(
		long repositoryId, long folderId, Map<String, String[]> parameterMap,
		RepositoryProvider repositoryProvider, DLAppService dlAppService,
		AssetEntryLocalService assetEntryLocalService,
		DLAssetHelper dlAssetHelper) {

		super(repositoryId, folderId, parameterMap, repositoryProvider);

		_repositoryId = repositoryId;
		_folderId = folderId;
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
	public long getSize() throws PortalException {
		return _dlAppService.getFileEntriesCount(_repositoryId, _folderId);
	}

	@Override
	public BulkSelection<AssetEntry> toAssetEntryBulkSelection() {
		return new FileEntryAssetEntryBulkSelection(
			this, _assetEntryLocalService, _dlAssetHelper);
	}

	@Override
	protected <E extends PortalException> RepositoryModelOperation
		getRepositoryModelOperation(
			UnsafeConsumer<? super FileEntry, E> action) {

		return new BaseRepositoryModelOperation() {

			@Override
			public void execute(FileEntry fileEntry) throws E {
				action.accept(fileEntry);
			}

		};
	}

	private final AssetEntryLocalService _assetEntryLocalService;
	private final DLAppService _dlAppService;
	private final DLAssetHelper _dlAssetHelper;
	private final long _folderId;
	private final long _repositoryId;

}