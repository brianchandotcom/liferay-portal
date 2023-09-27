/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.repository.capabilities.util;

import com.liferay.document.library.kernel.service.DLFolderLocalService;
import com.liferay.document.library.kernel.service.DLFolderLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLFolderService;
import com.liferay.document.library.kernel.service.DLFolderServiceUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.DocumentRepository;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.security.auth.PrincipalException;

import java.util.List;

/**
 * @author Iván Zaera
 */
public class DLFolderServiceAdapter {

	public static DLFolderServiceAdapter create(
		DocumentRepository documentRepository) {

		if (documentRepository instanceof LocalRepository) {
			return new DLFolderServiceAdapter(
				DLFolderLocalServiceUtil.getService());
		}

		return new DLFolderServiceAdapter(
			DLFolderLocalServiceUtil.getService(),
			DLFolderServiceUtil.getService());
	}

	public DLFolderServiceAdapter(DLFolderLocalService dlFolderLocalService) {
		this(dlFolderLocalService, null);
	}

	public DLFolderServiceAdapter(
		DLFolderLocalService dlFolderLocalService,
		DLFolderService dlFolderService) {

		_dlFolderLocalService = dlFolderLocalService;
		_dlFolderService = dlFolderService;
	}

	public void deleteFolder(long folderId, boolean includeTrashedEntries)
		throws PortalException {

		if (_dlFolderService != null) {
			_dlFolderService.deleteFolder(folderId, includeTrashedEntries);
		}
		else {
			_dlFolderLocalService.deleteFolder(folderId, includeTrashedEntries);
		}
	}

	public ActionableDynamicQuery getActionableDynamicQuery()
		throws PortalException {

		if (_dlFolderService != null) {
			throw new PrincipalException("DL folder service is not null");
		}

		return _dlFolderLocalService.getActionableDynamicQuery();
	}

	public List<Object> getFoldersAndFileEntriesAndFileShortcuts(
			long groupId, long folderId, String[] mimeTypes,
			boolean includeMountFolders, QueryDefinition<?> queryDefinition)
		throws PortalException {

		List<Object> foldersAndFileEntriesAndFileShortcuts = null;

		if (_dlFolderService != null) {
			foldersAndFileEntriesAndFileShortcuts =
				_dlFolderService.getFoldersAndFileEntriesAndFileShortcuts(
					groupId, folderId, mimeTypes, includeMountFolders,
					queryDefinition);
		}
		else {
			foldersAndFileEntriesAndFileShortcuts =
				_dlFolderLocalService.getFoldersAndFileEntriesAndFileShortcuts(
					groupId, folderId, mimeTypes, includeMountFolders,
					queryDefinition);
		}

		return foldersAndFileEntriesAndFileShortcuts;
	}

	private final DLFolderLocalService _dlFolderLocalService;
	private final DLFolderService _dlFolderService;

}