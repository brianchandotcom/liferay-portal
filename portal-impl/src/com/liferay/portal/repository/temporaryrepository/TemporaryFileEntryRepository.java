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

package com.liferay.portal.repository.temporaryrepository;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLAppHelperLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryService;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.document.library.kernel.service.DLFileShortcutLocalService;
import com.liferay.document.library.kernel.service.DLFileShortcutService;
import com.liferay.document.library.kernel.service.DLFileVersionLocalService;
import com.liferay.document.library.kernel.service.DLFileVersionService;
import com.liferay.document.library.kernel.service.DLFolderLocalService;
import com.liferay.document.library.kernel.service.DLFolderService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.RepositoryLocalService;
import com.liferay.portal.kernel.service.RepositoryService;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.systemevent.SystemEventHierarchyEntryThreadLocal;
import com.liferay.portal.kernel.util.UnsafeRunnable;
import com.liferay.portal.repository.liferayrepository.LiferayRepository;

/**
 * @author Iván Zaera
 */
public class TemporaryFileEntryRepository extends LiferayRepository {

	public TemporaryFileEntryRepository(
		RepositoryLocalService repositoryLocalService,
		RepositoryService repositoryService,
		DLAppHelperLocalService dlAppHelperLocalService,
		DLFileEntryLocalService dlFileEntryLocalService,
		DLFileEntryService dlFileEntryService,
		DLFileEntryTypeLocalService dlFileEntryTypeLocalService,
		DLFileShortcutLocalService dlFileShortcutLocalService,
		DLFileShortcutService dlFileShortcutService,
		DLFileVersionLocalService dlFileVersionLocalService,
		DLFileVersionService dlFileVersionService,
		DLFolderLocalService dlFolderLocalService,
		DLFolderService dlFolderService,
		ResourceLocalService resourceLocalService, long groupId,
		long repositoryId, long dlFolderId) {

		super(
			repositoryLocalService, repositoryService, dlAppHelperLocalService,
			dlFileEntryLocalService, dlFileEntryService,
			dlFileEntryTypeLocalService, dlFileShortcutLocalService,
			dlFileShortcutService, dlFileVersionLocalService,
			dlFileVersionService, dlFolderLocalService, dlFolderService,
			resourceLocalService, groupId, repositoryId, dlFolderId);
	}

	@Override
	public void deleteAll() {
		_runWithoutSystemEvents(super::deleteAll);
	}

	@Override
	public void deleteFileEntry(long fileEntryId) throws PortalException {
		_runWithoutSystemEvents(() -> super.deleteFileEntry(fileEntryId));
	}

	@Override
	public void deleteFileEntry(long folderId, String title)
		throws PortalException {

		_runWithoutSystemEvents(() -> super.deleteFileEntry(folderId, title));
	}

	@Override
	public void deleteFileShortcut(long fileShortcutId) throws PortalException {
		_runWithoutSystemEvents(() -> super.deleteFileShortcut(fileShortcutId));
	}

	@Override
	public void deleteFileShortcuts(long toFileEntryId) throws PortalException {
		_runWithoutSystemEvents(() -> super.deleteFileShortcuts(toFileEntryId));
	}

	@Override
	public void deleteFileVersion(long fileEntryId, String version)
		throws PortalException {

		_runWithoutSystemEvents(
			() -> super.deleteFileVersion(fileEntryId, version));
	}

	@Override
	public void deleteFolder(long folderId) throws PortalException {
		_runWithoutSystemEvents(() -> super.deleteFolder(folderId));
	}

	@Override
	public void deleteFolder(long parentFolderId, String name)
		throws PortalException {

		_runWithoutSystemEvents(() -> super.deleteFolder(parentFolderId, name));
	}

	private <T extends Throwable> void _runWithoutSystemEvents(
			UnsafeRunnable<T> runnable)
		throws T {

		SystemEventHierarchyEntryThreadLocal.push(DLFileEntry.class);

		try {
			runnable.run();
		}
		finally {
			SystemEventHierarchyEntryThreadLocal.pop(DLFileEntry.class);
		}
	}

}