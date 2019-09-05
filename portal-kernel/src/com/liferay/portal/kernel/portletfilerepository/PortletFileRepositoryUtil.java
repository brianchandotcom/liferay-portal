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

package com.liferay.portal.kernel.portletfilerepository;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

import java.io.File;
import java.io.InputStream;

import java.util.List;

/**
 * @author Eudaldo Alonso
 * @author Alexander Chow
 */
public class PortletFileRepositoryUtil {

	public static void addPortletFileEntries(
			long groupId, long userId, String className, long classPK,
			String portletId, long folderId,
			List<ObjectValuePair<String, InputStream>> inputStreamOVPs)
		throws PortalException {

		getPortletFileRepository().addPortletFileEntries(
			groupId, userId, className, classPK, portletId, folderId,
			inputStreamOVPs);
	}

	public static FileEntry addPortletFileEntry(
			long groupId, long userId, String className, long classPK,
			String portletId, long folderId, byte[] bytes, String fileName,
			String mimeType, boolean indexingEnabled)
		throws PortalException {

		return getPortletFileRepository().addPortletFileEntry(
			groupId, userId, className, classPK, portletId, folderId, bytes,
			fileName, mimeType, indexingEnabled);
	}

	public static FileEntry addPortletFileEntry(
			long groupId, long userId, String className, long classPK,
			String portletId, long folderId, File file, String fileName,
			String mimeType, boolean indexingEnabled)
		throws PortalException {

		return getPortletFileRepository().addPortletFileEntry(
			groupId, userId, className, classPK, portletId, folderId, file,
			fileName, mimeType, indexingEnabled);
	}

	public static FileEntry addPortletFileEntry(
			long groupId, long userId, String className, long classPK,
			String portletId, long folderId, InputStream inputStream,
			String fileName, String mimeType, boolean indexingEnabled)
		throws PortalException {

		return getPortletFileRepository().addPortletFileEntry(
			groupId, userId, className, classPK, portletId, folderId,
			inputStream, fileName, mimeType, indexingEnabled);
	}

	public static Folder addPortletFolder(
			long userId, long repositoryId, long parentFolderId,
			String folderName, ServiceContext serviceContext)
		throws PortalException {

		return getPortletFileRepository().addPortletFolder(
			userId, repositoryId, parentFolderId, folderName, serviceContext);
	}

	public static Folder addPortletFolder(
			long groupId, long userId, String portletId, long parentFolderId,
			String folderName, ServiceContext serviceContext)
		throws PortalException {

		return getPortletFileRepository().addPortletFolder(
			groupId, userId, portletId, parentFolderId, folderName,
			serviceContext);
	}

	public static Repository addPortletRepository(
			long groupId, String portletId, ServiceContext serviceContext)
		throws PortalException {

		return getPortletFileRepository().addPortletRepository(
			groupId, portletId, serviceContext);
	}

	public static void deletePortletFileEntries(long groupId, long folderId)
		throws PortalException {

		getPortletFileRepository().deletePortletFileEntries(groupId, folderId);
	}

	public static void deletePortletFileEntries(
			long groupId, long folderId, int status)
		throws PortalException {

		getPortletFileRepository().deletePortletFileEntries(
			groupId, folderId, status);
	}

	public static void deletePortletFileEntry(long fileEntryId)
		throws PortalException {

		getPortletFileRepository().deletePortletFileEntry(fileEntryId);
	}

	public static void deletePortletFileEntry(
			long groupId, long folderId, String fileName)
		throws PortalException {

		getPortletFileRepository().deletePortletFileEntry(
			groupId, folderId, fileName);
	}

	public static void deletePortletFolder(long folderId)
		throws PortalException {

		getPortletFileRepository().deletePortletFolder(folderId);
	}

	public static void deletePortletRepository(long groupId, String portletId)
		throws PortalException {

		getPortletFileRepository().deletePortletRepository(groupId, portletId);
	}

	public static FileEntry fetchPortletFileEntry(
		long groupId, long folderId, String fileName) {

		return getPortletFileRepository().fetchPortletFileEntry(
			groupId, folderId, fileName);
	}

	public static Repository fetchPortletRepository(
		long groupId, String portletId) {

		return getPortletFileRepository().fetchPortletRepository(
			groupId, portletId);
	}

	public static String getDownloadPortletFileEntryURL(
		ThemeDisplay themeDisplay, FileEntry fileEntry, String queryString) {

		return getPortletFileRepository().getDownloadPortletFileEntryURL(
			themeDisplay, fileEntry, queryString);
	}

	public static String getDownloadPortletFileEntryURL(
		ThemeDisplay themeDisplay, FileEntry fileEntry, String queryString,
		boolean absoluteURL) {

		return getPortletFileRepository().getDownloadPortletFileEntryURL(
			themeDisplay, fileEntry, queryString, absoluteURL);
	}

	public static List<FileEntry> getPortletFileEntries(
			long groupId, long folderId)
		throws PortalException {

		return getPortletFileRepository().getPortletFileEntries(
			groupId, folderId);
	}

	public static List<FileEntry> getPortletFileEntries(
			long groupId, long folderId, int status)
		throws PortalException {

		return getPortletFileRepository().getPortletFileEntries(
			groupId, folderId, status);
	}

	public static List<FileEntry> getPortletFileEntries(
			long groupId, long folderId, int status, int start, int end,
			OrderByComparator<FileEntry> obc)
		throws PortalException {

		return getPortletFileRepository().getPortletFileEntries(
			groupId, folderId, status, start, end, obc);
	}

	public static List<FileEntry> getPortletFileEntries(
			long groupId, long folderId, OrderByComparator<FileEntry> obc)
		throws PortalException {

		return getPortletFileRepository().getPortletFileEntries(
			groupId, folderId, obc);
	}

	public static List<FileEntry> getPortletFileEntries(
			long groupId, long folderId, String[] mimeTypes, int status,
			int start, int end, OrderByComparator<FileEntry> obc)
		throws PortalException {

		return getPortletFileRepository().getPortletFileEntries(
			groupId, folderId, mimeTypes, status, start, end, obc);
	}

	public static int getPortletFileEntriesCount(long groupId, long folderId)
		throws PortalException {

		return getPortletFileRepository().getPortletFileEntriesCount(
			groupId, folderId);
	}

	public static int getPortletFileEntriesCount(
			long groupId, long folderId, int status)
		throws PortalException {

		return getPortletFileRepository().getPortletFileEntriesCount(
			groupId, folderId, status);
	}

	public static int getPortletFileEntriesCount(
			long groupId, long folderId, String[] mimeTypes, int status)
		throws PortalException {

		return getPortletFileRepository().getPortletFileEntriesCount(
			groupId, folderId, mimeTypes, status);
	}

	public static FileEntry getPortletFileEntry(long fileEntryId)
		throws PortalException {

		return getPortletFileRepository().getPortletFileEntry(fileEntryId);
	}

	public static FileEntry getPortletFileEntry(
			long groupId, long folderId, String fileName)
		throws PortalException {

		return getPortletFileRepository().getPortletFileEntry(
			groupId, folderId, fileName);
	}

	public static FileEntry getPortletFileEntry(String uuid, long groupId)
		throws PortalException {

		return getPortletFileRepository().getPortletFileEntry(uuid, groupId);
	}

	public static String getPortletFileEntryURL(
		ThemeDisplay themeDisplay, FileEntry fileEntry, String queryString) {

		return getPortletFileRepository().getPortletFileEntryURL(
			themeDisplay, fileEntry, queryString);
	}

	public static String getPortletFileEntryURL(
		ThemeDisplay themeDisplay, FileEntry fileEntry, String queryString,
		boolean absoluteURL) {

		return getPortletFileRepository().getPortletFileEntryURL(
			themeDisplay, fileEntry, queryString, absoluteURL);
	}

	public static PortletFileRepository getPortletFileRepository() {
		return _portletFileRepository;
	}

	public static Folder getPortletFolder(long folderId)
		throws PortalException {

		return getPortletFileRepository().getPortletFolder(folderId);
	}

	public static Folder getPortletFolder(
			long repositoryId, long parentFolderId, String folderName)
		throws PortalException {

		return getPortletFileRepository().getPortletFolder(
			repositoryId, parentFolderId, folderName);
	}

	public static Repository getPortletRepository(
			long groupId, String portletId)
		throws PortalException {

		return getPortletFileRepository().getPortletRepository(
			groupId, portletId);
	}

	public static String getUniqueFileName(
		long groupId, long folderId, String fileName) {

		return getPortletFileRepository().getUniqueFileName(
			groupId, folderId, fileName);
	}

	public static FileEntry movePortletFileEntryToTrash(
			long userId, long fileEntryId)
		throws PortalException {

		return getPortletFileRepository().movePortletFileEntryToTrash(
			userId, fileEntryId);
	}

	public static FileEntry movePortletFileEntryToTrash(
			long groupId, long userId, long folderId, String fileName)
		throws PortalException {

		return getPortletFileRepository().movePortletFileEntryToTrash(
			groupId, userId, folderId, fileName);
	}

	public static Folder movePortletFolder(
			long groupId, long userId, long folderId, long parentFolderId,
			ServiceContext serviceContext)
		throws PortalException {

		return getPortletFileRepository().movePortletFolder(
			groupId, userId, folderId, parentFolderId, serviceContext);
	}

	public static void restorePortletFileEntryFromTrash(
			long userId, long fileEntryId)
		throws PortalException {

		getPortletFileRepository().restorePortletFileEntryFromTrash(
			userId, fileEntryId);
	}

	public static void restorePortletFileEntryFromTrash(
			long groupId, long userId, long folderId, String fileName)
		throws PortalException {

		getPortletFileRepository().restorePortletFileEntryFromTrash(
			groupId, userId, folderId, fileName);
	}

	public static Hits searchPortletFileEntries(
			long repositoryId, SearchContext searchContext)
		throws PortalException {

		return getPortletFileRepository().searchPortletFileEntries(
			repositoryId, searchContext);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), with no direct replacement
	 */
	@Deprecated
	public void setPortletFileRepository(
		PortletFileRepository portletFileRepository) {

		_portletFileRepository = portletFileRepository;
	}

	private static volatile PortletFileRepository _portletFileRepository =
		ServiceProxyFactory.newServiceTrackedInstance(
			PortletFileRepository.class, PortletFileRepositoryUtil.class,
			"_portletFileRepository", false);

}