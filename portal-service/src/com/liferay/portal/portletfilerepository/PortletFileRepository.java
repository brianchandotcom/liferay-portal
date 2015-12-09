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

package com.liferay.portal.portletfilerepository;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.Repository;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.theme.ThemeDisplay;

import java.io.File;
import java.io.InputStream;

import java.util.List;

/**
 * @author Eudaldo Alonso
 * @author Alexander Chow
 */
@ProviderType
public interface PortletFileRepository {

	public void addPortletFileEntries(
			long groupId, long userId, String className, long classPK,
			String portletId, long folderId,
			List<ObjectValuePair<String, InputStream>> inputStreamOVPs)
		throws PortalException;

	public FileEntry addPortletFileEntry(
			long groupId, long userId, String className, long classPK,
			String portletId, long folderId, byte[] bytes, String fileName,
			String mimeType, boolean indexingEnabled)
		throws PortalException;

	public FileEntry addPortletFileEntry(
			long groupId, long userId, String className, long classPK,
			String portletId, long folderId, File file, String fileName,
			String mimeType, boolean indexingEnabled)
		throws PortalException;

	public FileEntry addPortletFileEntry(
			long groupId, long userId, String className, long classPK,
			String portletId, long folderId, InputStream inputStream,
			String fileName, String mimeType, boolean indexingEnabled)
		throws PortalException;

	public Folder addPortletFolder(
			long userId, long repositoryId, long parentFolderId,
			String folderName, ServiceContext serviceContext)
		throws PortalException;

	public Folder addPortletFolder(
			long groupId, long userId, String portletId, long parentFolderId,
			String folderName, ServiceContext serviceContext)
		throws PortalException;

	public Repository addPortletRepository(
			long groupId, String portletId, ServiceContext serviceContext)
		throws PortalException;

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #deletePortletFolder}
	 */
	@Deprecated
	public void deleteFolder(long folderId) throws PortalException;

	public void deletePortletFileEntries(long groupId, long folderId)
		throws PortalException;

	public void deletePortletFileEntries(
			long groupId, long folderId, int status)
		throws PortalException;

	public void deletePortletFileEntry(long fileEntryId) throws PortalException;

	public void deletePortletFileEntry(
			long groupId, long folderId, String fileName)
		throws PortalException;

	public void deletePortletFolder(long folderId) throws PortalException;

	public void deletePortletRepository(long groupId, String portletId)
		throws PortalException;

	public Repository fetchPortletRepository(long groupId, String portletId);

	public String getDownloadPortletFileEntryURL(
		ThemeDisplay themeDisplay, FileEntry fileEntry, String queryString);

	public String getDownloadPortletFileEntryURL(
		ThemeDisplay themeDisplay, FileEntry fileEntry, String queryString,
		boolean absoluteURL);

	public List<FileEntry> getPortletFileEntries(long groupId, long folderId)
		throws PortalException;

	public List<FileEntry> getPortletFileEntries(
			long groupId, long folderId, int status)
		throws PortalException;

	public List<FileEntry> getPortletFileEntries(
			long groupId, long folderId, int status, int start, int end,
			OrderByComparator<FileEntry> obc)
		throws PortalException;

	public List<FileEntry> getPortletFileEntries(
			long groupId, long folderId, OrderByComparator<FileEntry> obc)
		throws PortalException;

	public int getPortletFileEntriesCount(long groupId, long folderId)
		throws PortalException;

	public int getPortletFileEntriesCount(
			long groupId, long folderId, int status)
		throws PortalException;

	public FileEntry getPortletFileEntry(long fileEntryId)
		throws PortalException;

	public FileEntry getPortletFileEntry(
			long groupId, long folderId, String fileName)
		throws PortalException;

	public FileEntry getPortletFileEntry(String uuid, long groupId)
		throws PortalException;

	public String getPortletFileEntryURL(
		ThemeDisplay themeDisplay, FileEntry fileEntry, String queryString);

	public String getPortletFileEntryURL(
		ThemeDisplay themeDisplay, FileEntry fileEntry, String queryString,
		boolean absoluteURL);

	public Folder getPortletFolder(long folderId) throws PortalException;

	public Folder getPortletFolder(
			long repositoryId, long parentFolderId, String folderName)
		throws PortalException;

	public Repository getPortletRepository(long groupId, String portletId)
		throws PortalException;

	public String getUniqueFileName(
		long groupId, long folderId, String fileName);

	public FileEntry movePortletFileEntryToTrash(long userId, long fileEntryId)
		throws PortalException;

	public FileEntry movePortletFileEntryToTrash(
			long groupId, long userId, long folderId, String fileName)
		throws PortalException;

	public void restorePortletFileEntryFromTrash(long userId, long fileEntryId)
		throws PortalException;

	public void restorePortletFileEntryFromTrash(
			long groupId, long userId, long folderId, String fileName)
		throws PortalException;

	public Hits searchPortletFileEntries(
			long repositoryId, SearchContext searchContext)
		throws PortalException;

}