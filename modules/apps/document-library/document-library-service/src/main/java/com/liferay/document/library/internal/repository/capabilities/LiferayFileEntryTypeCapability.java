/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.repository.capabilities;

import com.liferay.document.library.kernel.service.DLFolderService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.capabilities.FileEntryTypeCapability;
import com.liferay.portal.kernel.repository.model.RepositoryEntry;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portlet.documentlibrary.util.RepositoryModelUtil;

import java.util.List;

/**
 * @author Jürgen Kappler
 */
public class LiferayFileEntryTypeCapability implements FileEntryTypeCapability {

	public LiferayFileEntryTypeCapability(DLFolderService dlFolderService) {
		_dlFolderService = dlFolderService;
	}

	@Override
	public List<RepositoryEntry> getFoldersAndFileEntriesAndFileShortcuts(
			long groupId, long folderId, String[] mimeTypes,
			long fileEntryTypeId, boolean includeMountFolders, int status,
			int start, int end, OrderByComparator<?> orderByComparator)
		throws PortalException {

		return RepositoryModelUtil.toRepositoryEntries(
			_dlFolderService.getFoldersAndFileEntriesAndFileShortcuts(
				groupId, folderId, mimeTypes, fileEntryTypeId,
				includeMountFolders, status, start, end, orderByComparator));
	}

	@Override
	public int getFoldersAndFileEntriesAndFileShortcutsCount(
			long groupId, long folderId, String[] mimeTypes,
			long fileEntryTypeId, boolean includeMountFolders, int status)
		throws PortalException {

		return _dlFolderService.getFoldersAndFileEntriesAndFileShortcutsCount(
			groupId, folderId, mimeTypes, fileEntryTypeId, includeMountFolders,
			status);
	}

	private final DLFolderService _dlFolderService;

}