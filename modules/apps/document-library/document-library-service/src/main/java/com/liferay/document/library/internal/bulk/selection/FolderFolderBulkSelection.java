/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.bulk.selection;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.bulk.selection.BulkSelection;
import com.liferay.bulk.selection.BulkSelectionFactory;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.RepositoryProvider;
import com.liferay.portal.kernel.repository.model.BaseRepositoryModelOperation;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.repository.model.RepositoryModelOperation;

import java.util.Map;

/**
 * @author Adolfo Pérez
 */
public class FolderFolderBulkSelection
	extends BaseFolderEntryBulkSelection<Folder> {

	public FolderFolderBulkSelection(
		long repositoryId, long folderId, Map<String, String[]> parameterMap,
		RepositoryProvider repositoryProvider, DLAppService dlAppService) {

		super(repositoryId, folderId, parameterMap, repositoryProvider);

		_repositoryId = repositoryId;
		_folderId = folderId;
		_dlAppService = dlAppService;
	}

	@Override
	public Class<? extends BulkSelectionFactory>
		getBulkSelectionFactoryClass() {

		return FolderBulkSelectionFactory.class;
	}

	@Override
	public long getSize() throws PortalException {
		return _dlAppService.getFoldersCount(_repositoryId, _folderId);
	}

	@Override
	public BulkSelection<AssetEntry> toAssetEntryBulkSelection() {
		throw new UnsupportedOperationException("Folder is not an asset");
	}

	@Override
	protected <E extends PortalException> RepositoryModelOperation
		getRepositoryModelOperation(UnsafeConsumer<? super Folder, E> action) {

		return new BaseRepositoryModelOperation() {

			@Override
			public void execute(Folder folder) throws E {
				action.accept(folder);
			}

		};
	}

	private final DLAppService _dlAppService;
	private final long _folderId;
	private final long _repositoryId;

}