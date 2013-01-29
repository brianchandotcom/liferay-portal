/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;

import java.io.File;
import java.io.InputStream;

import java.util.List;

/**
 * @author Sergio González
 * @author Matthew Kong
 * @author Alexander Chow
 */
public class TempFileUtil {

	public static FileEntry addTempFile(
			long groupId, long userId, String fileName, String tempFolderName,
			File file, String mimeType)
		throws PortalException, SystemException {

		long folderId = getTempFolderId(groupId, userId, tempFolderName);

		return PortletFileRepositoryUtil.addPortletFileEntry(
			groupId, userId, StringPool.BLANK, 0, PortletKeys.DOCUMENT_LIBRARY,
			folderId, file, fileName, mimeType);
	}

	public static FileEntry addTempFile(
			long groupId, long userId, String fileName, String tempFolderName,
			InputStream inputStream, String mimeType)
		throws PortalException, SystemException {

		long folderId = getTempFolderId(groupId, userId, tempFolderName);

		return PortletFileRepositoryUtil.addPortletFileEntry(
			groupId, userId, StringPool.BLANK, 0, PortletKeys.DOCUMENT_LIBRARY,
			folderId, inputStream, fileName, mimeType);
	}

	public static void deleteTempFile(long fileEntryId)
		throws PortalException, SystemException {

		PortletFileRepositoryUtil.deletePortletFileEntry(fileEntryId);
	}

	public static void deleteTempFile(
			long groupId, long userId, String fileName, String tempFolderName)
		throws PortalException, SystemException {

		long folderId = getTempFolderId(groupId, userId, tempFolderName);

		PortletFileRepositoryUtil.deletePortletFileEntry(
			groupId, folderId, fileName);
	}

	public static FileEntry getTempFile(
			long groupId, long userId, String fileName, String tempFolderName)
		throws PortalException, SystemException {

		long folderId = getTempFolderId(groupId, userId, tempFolderName);

		return PortletFileRepositoryUtil.getPortletFileEntry(
			groupId, folderId, fileName);
	}

	public static String[] getTempFileEntryNames(
			long groupId, long userId, String tempFolderName)
		throws PortalException, SystemException {

		long folderId = getTempFolderId(groupId, userId, tempFolderName);

		List<FileEntry> fileEntries =
			PortletFileRepositoryUtil.getPortletFileEntries(groupId, folderId);

		String[] fileEntryNames = new String[fileEntries.size()];

		for (int i = 0; i < fileEntries.size(); i++) {
			fileEntryNames[i] = fileEntries.get(i).getTitle();
		}

		return fileEntryNames;
	}

	protected static long getTempFolderId(
			long groupId, long userId, String tempFolderName)
		throws PortalException, SystemException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		long repositoryId = PortletFileRepositoryUtil.getPortletRepository(
			groupId, PortletKeys.DOCUMENT_LIBRARY, serviceContext);

		Folder userFolder = PortletFileRepositoryUtil.getPortletFolder(
			userId, repositoryId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			String.valueOf(userId), serviceContext);

		Folder tempFolder = PortletFileRepositoryUtil.getPortletFolder(
			userId, repositoryId, userFolder.getFolderId(), tempFolderName,
			serviceContext);

		return tempFolder.getFolderId();
	}

}