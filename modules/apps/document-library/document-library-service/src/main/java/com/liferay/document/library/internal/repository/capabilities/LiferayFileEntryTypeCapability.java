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
			int start, int end, OrderByComparator<?> obc)
		throws PortalException {

		return RepositoryModelUtil.toRepositoryEntries(
			_dlFolderService.getFoldersAndFileEntriesAndFileShortcuts(
				groupId, folderId, mimeTypes, fileEntryTypeId,
				includeMountFolders, status, start, end, obc));
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