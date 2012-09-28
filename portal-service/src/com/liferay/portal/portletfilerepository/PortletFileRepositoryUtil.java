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

package com.liferay.portal.portletfilerepository;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;

import java.io.File;
import java.io.InputStream;

import java.util.List;

/**
 * @author Eudaldo Alonso
 */
public class PortletFileRepositoryUtil {

	public static void addPortletFileEntries(
			long groupId, long userId, long folderId,
			List<ObjectValuePair<String, InputStream>> inputStreamOVPs,
			String portletId)
		throws PortalException, SystemException {

		getPortletFileRepository().addPortletFileEntries(
			groupId, userId, folderId, inputStreamOVPs, portletId);
	}

	public static FileEntry addPortletFileEntry(
			long groupId, long userId, long folderId, String portletId,
			File file, String fileName)
		throws PortalException, SystemException {

		return getPortletFileRepository().addPortletFileEntry(
			groupId, userId, folderId, portletId, file, fileName);
	}

	public static FileEntry addPortletFileEntry(
			long groupId, long userId, long folderId, String portletId,
			InputStream inputStream, String fileName)
		throws PortalException, SystemException {

		return getPortletFileRepository().addPortletFileEntry(
			groupId, userId, folderId, portletId, inputStream, fileName);
	}

	public static void deleteFolder(long folderId)
		throws PortalException, SystemException {

		getPortletFileRepository().deleteFolder(folderId);
	}

	public static void deletePortletFileEntries(long groupId, long folderId)
		throws PortalException, SystemException {

		getPortletFileRepository().deletePortletFileEntries(groupId, folderId);
	}

	public static void deletePortletFileEntry(long fileEntryId)
		throws PortalException, SystemException {

		getPortletFileRepository().deletePortletFileEntry(fileEntryId);
	}

	public static void deletePortletFileEntry(
			long groupId, long folderId, String fileName)
		throws PortalException, SystemException {

		getPortletFileRepository().deletePortletFileEntry(
			groupId, folderId, fileName);
	}

	public static long getFolder(
			long userId, long repositoryId, long parentFolderId,
			String folderName, ServiceContext serviceContext)
		throws PortalException, SystemException {

		return getPortletFileRepository().getFolder(
			userId, repositoryId, parentFolderId, folderName, serviceContext);
	}

	public static List<DLFileEntry> getPortletFileEntries(
			long groupId, long folderId)
		throws SystemException {

		return getPortletFileRepository().getPortletFileEntries(
				groupId, folderId);
	}

	public static int getPortletFileEntriesCount(long groupId, long folderId)
		throws SystemException {

		return getPortletFileRepository().getPortletFileEntriesCount(
				groupId, folderId);
	}

	public static PortletFileRepository getPortletFileRepository() {
		PortalRuntimePermission.checkGetBeanProperty(
			PortletFileRepositoryUtil.class);

		return _portletFileRepository;
	}

	public static long getPortletRepository(
			long groupId, String portletId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		return getPortletFileRepository().getPortletRepository(
			groupId, portletId, serviceContext);
	}

	public static void movePortletFileEntryFromTrash(
			long userId, long fileEntryId)
		throws PortalException, SystemException {

		getPortletFileRepository().movePortletFileEntryFromTrash(
			userId, fileEntryId);
	}

	public static void movePortletFileEntryFromTrash(
			long groupId, long userId, long folderId, String fileName)
		throws PortalException, SystemException {

		getPortletFileRepository().movePortletFileEntryFromTrash(
			groupId, userId, folderId, fileName);
	}

	public static void movePortletFileEntryToTrash(
			long userId, long fileEntryId)
		throws PortalException, SystemException {

		getPortletFileRepository().movePortletFileEntryToTrash(
			userId, fileEntryId);
	}

	public static void movePortletFileEntryToTrash(
			long groupId, long userId, long folderId, String fileName)
		throws PortalException, SystemException {

		getPortletFileRepository().movePortletFileEntryToTrash(
			groupId, userId, folderId, fileName);
	}

	public void setPortletFileRepository(
		PortletFileRepository portletFileRepository) {

		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_portletFileRepository = portletFileRepository;
	}

	private static PortletFileRepository _portletFileRepository;

}