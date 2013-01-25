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
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.service.ServiceContext;

import java.io.File;
import java.io.InputStream;

import java.util.List;

/**
 * @author Eudaldo Alonso
 */
public interface PortletFileRepository {

	public void addPortletFileEntries(
			long groupId, long userId, String className, long classPK,
			String portletId, long folderId,
			List<ObjectValuePair<String, InputStream>> inputStreamOVPs)
		throws PortalException, SystemException;

	public FileEntry addPortletFileEntry(
			long groupId, long userId, String className, long classPK,
			String portletId, long folderId, File file, String fileName)
		throws PortalException, SystemException;

	public FileEntry addPortletFileEntry(
			long groupId, long userId, String className, long classPK,
			String portletId, long folderId, InputStream inputStream,
			String fileName)
		throws PortalException, SystemException;

	public void deleteFolder(long folderId)
		throws PortalException, SystemException;

	public void deletePortletFileEntries(long groupId, long folderId)
		throws PortalException, SystemException;

	public void deletePortletFileEntries(
			long groupId, long folderId, int status)
		throws PortalException, SystemException;

	public void deletePortletFileEntry(long fileEntryId)
		throws PortalException, SystemException;

	public void deletePortletFileEntry(
			long groupId, long folderId, String fileName)
		throws PortalException, SystemException;

	public List<FileEntry> getPortletFileEntries(long groupId, long folderId)
		throws SystemException;

	public List<FileEntry> getPortletFileEntries(
			long groupId, long folderId, int status)
		throws SystemException;

	public List<FileEntry> getPortletFileEntries(
			long groupId, long folderId, int status, int start, int end,
			OrderByComparator obc)
		throws SystemException;

	public int getPortletFileEntriesCount(long groupId, long folderId)
		throws SystemException;

	public int getPortletFileEntriesCount(
			long groupId, long folderId, int status)
		throws SystemException;

	public FileEntry getPortletFileEntry(long fileEntryId)
		throws PortalException, SystemException;

	public FileEntry getPortletFileEntry(
			long groupId, long folderId, String fileName)
		throws PortalException, SystemException;

	public Folder getPortletFolder(long folderId)
		throws PortalException, SystemException;

	public Folder getPortletFolder(
			long userId, long repositoryId, long parentFolderId,
			String folderName, ServiceContext serviceContext)
		throws PortalException, SystemException;

	public long getPortletRepository(
			long groupId, String portletId, ServiceContext serviceContext)
		throws PortalException, SystemException;

	public void movePortletFileEntryToTrash(long userId, long fileEntryId)
		throws PortalException, SystemException;

	public void movePortletFileEntryToTrash(
			long groupId, long userId, long folderId, String fileName)
		throws PortalException, SystemException;

	public void restorePortletFileEntryFromTrash(long userId, long fileEntryId)
		throws PortalException, SystemException;

	public void restorePortletFileEntryFromTrash(
			long groupId, long userId, long folderId, String fileName)
		throws PortalException, SystemException;

}