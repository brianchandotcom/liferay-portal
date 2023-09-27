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
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.repository.model.RepositoryModelOperation;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Map;

/**
 * @author Adolfo Pérez
 */
public class FolderFileShortcutBulkSelection
	extends BaseFolderEntryBulkSelection<FileShortcut> {

	public FolderFileShortcutBulkSelection(
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

		return FileShortcutBulkSelectionFactory.class;
	}

	@Override
	public long getSize() throws PortalException {
		int fileEntriesAndFileShortcutsCount =
			_dlAppService.getFileEntriesAndFileShortcutsCount(
				_repositoryId, _folderId, WorkflowConstants.STATUS_APPROVED);
		int fileEntriesCount = _dlAppService.getFileEntriesCount(
			_repositoryId, _folderId);

		return fileEntriesAndFileShortcutsCount - fileEntriesCount;
	}

	@Override
	public BulkSelection<AssetEntry> toAssetEntryBulkSelection() {
		throw new UnsupportedOperationException(
			"File shortcut is not an asset");
	}

	@Override
	protected <E extends PortalException> RepositoryModelOperation
		getRepositoryModelOperation(
			UnsafeConsumer<? super FileShortcut, E> action) {

		return new BaseRepositoryModelOperation() {

			@Override
			public void execute(FileShortcut fileShortcut) throws E {
				action.accept(fileShortcut);
			}

		};
	}

	private final DLAppService _dlAppService;
	private final long _folderId;
	private final long _repositoryId;

}