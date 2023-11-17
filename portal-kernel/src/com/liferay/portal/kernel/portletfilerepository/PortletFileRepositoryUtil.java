/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.portletfilerepository;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.OrderByComparator;

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

		PortletFileRepository portletFileRepository =
			_portletFileRepositorySnapshot.get();

		portletFileRepository.addPortletFileEntries(
			groupId, userId, className, classPK, portletId, folderId,
			inputStreamOVPs);
	}

	public static FileEntry addPortletFileEntry(
			long groupId, long userId, String className, long classPK,
			String portletId, long folderId, byte[] bytes, String fileName,
			String mimeType, boolean indexingEnabled)
		throws PortalException {

		PortletFileRepository portletFileRepository =
			_portletFileRepositorySnapshot.get();

		return portletFileRepository.addPortletFileEntry(
			groupId, userId, className, classPK, portletId, folderId, bytes,
			fileName, mimeType, indexingEnabled);
	}

	public static FileEntry addPortletFileEntry(
			String externalReferenceCode, long groupId, long userId,
			String className, long classPK, String portletId, long folderId,
			File file, String fileName, String mimeType,
			boolean indexingEnabled)
		throws PortalException {

		PortletFileRepository portletFileRepository =
			_portletFileRepositorySnapshot.get();

		return portletFileRepository.addPortletFileEntry(
			externalReferenceCode, groupId, userId, className, classPK,
			portletId, folderId, file, fileName, mimeType, indexingEnabled);
	}

	public static FileEntry addPortletFileEntry(
			String externalReferenceCode, long groupId, long userId,
			String className, long classPK, String portletId, long folderId,
			InputStream inputStream, String fileName, String mimeType,
			boolean indexingEnabled)
		throws PortalException {

		PortletFileRepository portletFileRepository =
			_portletFileRepositorySnapshot.get();

		return portletFileRepository.addPortletFileEntry(
			externalReferenceCode, groupId, userId, className, classPK,
			portletId, folderId, inputStream, fileName, mimeType,
			indexingEnabled);
	}

	public static Folder addPortletFolder(
			long userId, long repositoryId, long parentFolderId,
			String folderName, ServiceContext serviceContext)
		throws PortalException {

		PortletFileRepository portletFileRepository =
			_portletFileRepositorySnapshot.get();

		return portletFileRepository.addPortletFolder(
			userId, repositoryId, parentFolderId, folderName, serviceContext);
	}

	public static Folder addPortletFolder(
			long groupId, long userId, String portletId, long parentFolderId,
			String folderName, ServiceContext serviceContext)
		throws PortalException {

		PortletFileRepository portletFileRepository =
			_portletFileRepositorySnapshot.get();

		return portletFileRepository.addPortletFolder(
			groupId, userId, portletId, parentFolderId, folderName,
			serviceContext);
	}

	public static Repository addPortletRepository(
			long groupId, String portletId, ServiceContext serviceContext)
		throws PortalException {

		PortletFileRepository portletFileRepository =
			_portletFileRepositorySnapshot.get();

		return portletFileRepository.addPortletRepository(
			groupId, portletId, serviceContext);
	}

	public static void deletePortletFileEntries(long groupId, long folderId)
		throws PortalException {

		PortletFileRepository portletFileRepository =
			_portletFileRepositorySnapshot.get();

		portletFileRepository.deletePortletFileEntries(groupId, folderId);
	}

	public static void deletePortletFileEntries(
			long groupId, long folderId, int status)
		throws PortalException {

		PortletFileRepository portletFileRepository =
			_portletFileRepositorySnapshot.get();

		portletFileRepository.deletePortletFileEntries(
			groupId, folderId, status);
	}

	public static void deletePortletFileEntry(long fileEntryId)
		throws PortalException {

		PortletFileRepository portletFileRepository =
			_portletFileRepositorySnapshot.get();

		portletFileRepository.deletePortletFileEntry(fileEntryId);
	}

	public static void deletePortletFileEntry(
			long groupId, long folderId, String fileName)
		throws PortalException {

		PortletFileRepository portletFileRepository =
			_portletFileRepositorySnapshot.get();

		portletFileRepository.deletePortletFileEntry(
			groupId, folderId, fileName);
	}

	public static void deletePortletFolder(long folderId)
		throws PortalException {

		PortletFileRepository portletFileRepository =
			_portletFileRepositorySnapshot.get();

		portletFileRepository.deletePortletFolder(folderId);
	}

	public static void deletePortletRepository(long groupId, String portletId)
		throws PortalException {

		PortletFileRepository portletFileRepository =
			_portletFileRepositorySnapshot.get();

		portletFileRepository.deletePortletRepository(groupId, portletId);
	}

	public static FileEntry fetchPortletFileEntry(
		long groupId, long folderId, String fileName) {

		PortletFileRepository portletFileRepository =
			_portletFileRepositorySnapshot.get();

		return portletFileRepository.fetchPortletFileEntry(
			groupId, folderId, fileName);
	}

	public static Repository fetchPortletRepository(
		long groupId, String portletId) {

		PortletFileRepository portletFileRepository =
			_portletFileRepositorySnapshot.get();

		return portletFileRepository.fetchPortletRepository(groupId, portletId);
	}

	public static String getDownloadPortletFileEntryURL(
		ThemeDisplay themeDisplay, FileEntry fileEntry, String queryString) {

		PortletFileRepository portletFileRepository =
			_portletFileRepositorySnapshot.get();

		return portletFileRepository.getDownloadPortletFileEntryURL(
			themeDisplay, fileEntry, queryString);
	}

	public static String getDownloadPortletFileEntryURL(
		ThemeDisplay themeDisplay, FileEntry fileEntry, String queryString,
		boolean absoluteURL) {

		PortletFileRepository portletFileRepository =
			_portletFileRepositorySnapshot.get();

		return portletFileRepository.getDownloadPortletFileEntryURL(
			themeDisplay, fileEntry, queryString, absoluteURL);
	}

	public static List<FileEntry> getPortletFileEntries(
			long groupId, long folderId)
		throws PortalException {

		PortletFileRepository portletFileRepository =
			_portletFileRepositorySnapshot.get();

		return portletFileRepository.getPortletFileEntries(groupId, folderId);
	}

	public static List<FileEntry> getPortletFileEntries(
			long groupId, long folderId, int status)
		throws PortalException {

		PortletFileRepository portletFileRepository =
			_portletFileRepositorySnapshot.get();

		return portletFileRepository.getPortletFileEntries(
			groupId, folderId, status);
	}

	public static List<FileEntry> getPortletFileEntries(
			long groupId, long folderId, int status, int start, int end,
			OrderByComparator<FileEntry> orderByComparator)
		throws PortalException {

		PortletFileRepository portletFileRepository =
			_portletFileRepositorySnapshot.get();

		return portletFileRepository.getPortletFileEntries(
			groupId, folderId, status, start, end, orderByComparator);
	}

	public static List<FileEntry> getPortletFileEntries(
			long groupId, long folderId,
			OrderByComparator<FileEntry> orderByComparator)
		throws PortalException {

		PortletFileRepository portletFileRepository =
			_portletFileRepositorySnapshot.get();

		return portletFileRepository.getPortletFileEntries(
			groupId, folderId, orderByComparator);
	}

	public static List<FileEntry> getPortletFileEntries(
			long groupId, long folderId, String[] mimeTypes, int status,
			int start, int end, OrderByComparator<FileEntry> orderByComparator)
		throws PortalException {

		PortletFileRepository portletFileRepository =
			_portletFileRepositorySnapshot.get();

		return portletFileRepository.getPortletFileEntries(
			groupId, folderId, mimeTypes, status, start, end,
			orderByComparator);
	}

	public static int getPortletFileEntriesCount(long groupId, long folderId)
		throws PortalException {

		PortletFileRepository portletFileRepository =
			_portletFileRepositorySnapshot.get();

		return portletFileRepository.getPortletFileEntriesCount(
			groupId, folderId);
	}

	public static int getPortletFileEntriesCount(
			long groupId, long folderId, int status)
		throws PortalException {

		PortletFileRepository portletFileRepository =
			_portletFileRepositorySnapshot.get();

		return portletFileRepository.getPortletFileEntriesCount(
			groupId, folderId, status);
	}

	public static int getPortletFileEntriesCount(
			long groupId, long folderId, String[] mimeTypes, int status)
		throws PortalException {

		PortletFileRepository portletFileRepository =
			_portletFileRepositorySnapshot.get();

		return portletFileRepository.getPortletFileEntriesCount(
			groupId, folderId, mimeTypes, status);
	}

	public static FileEntry getPortletFileEntry(long fileEntryId)
		throws PortalException {

		PortletFileRepository portletFileRepository =
			_portletFileRepositorySnapshot.get();

		return portletFileRepository.getPortletFileEntry(fileEntryId);
	}

	public static FileEntry getPortletFileEntry(
			long groupId, long folderId, String fileName)
		throws PortalException {

		PortletFileRepository portletFileRepository =
			_portletFileRepositorySnapshot.get();

		return portletFileRepository.getPortletFileEntry(
			groupId, folderId, fileName);
	}

	public static FileEntry getPortletFileEntry(String uuid, long groupId)
		throws PortalException {

		PortletFileRepository portletFileRepository =
			_portletFileRepositorySnapshot.get();

		return portletFileRepository.getPortletFileEntry(uuid, groupId);
	}

	public static FileEntry getPortletFileEntryByExternalReferenceCode(
			String externalReferenceCode, long groupId)
		throws PortalException {

		PortletFileRepository portletFileRepository =
			_portletFileRepositorySnapshot.get();

		return portletFileRepository.getPortletFileEntryByExternalReferenceCode(
			externalReferenceCode, groupId);
	}

	public static String getPortletFileEntryURL(
		ThemeDisplay themeDisplay, FileEntry fileEntry, String queryString) {

		PortletFileRepository portletFileRepository =
			_portletFileRepositorySnapshot.get();

		return portletFileRepository.getPortletFileEntryURL(
			themeDisplay, fileEntry, queryString);
	}

	public static String getPortletFileEntryURL(
		ThemeDisplay themeDisplay, FileEntry fileEntry, String queryString,
		boolean absoluteURL) {

		PortletFileRepository portletFileRepository =
			_portletFileRepositorySnapshot.get();

		return portletFileRepository.getPortletFileEntryURL(
			themeDisplay, fileEntry, queryString, absoluteURL);
	}

	public static PortletFileRepository getPortletFileRepository() {
		return _portletFileRepositorySnapshot.get();
	}

	public static Folder getPortletFolder(long folderId)
		throws PortalException {

		PortletFileRepository portletFileRepository =
			_portletFileRepositorySnapshot.get();

		return portletFileRepository.getPortletFolder(folderId);
	}

	public static Folder getPortletFolder(
			long repositoryId, long parentFolderId, String folderName)
		throws PortalException {

		PortletFileRepository portletFileRepository =
			_portletFileRepositorySnapshot.get();

		return portletFileRepository.getPortletFolder(
			repositoryId, parentFolderId, folderName);
	}

	public static Repository getPortletRepository(
			long groupId, String portletId)
		throws PortalException {

		PortletFileRepository portletFileRepository =
			_portletFileRepositorySnapshot.get();

		return portletFileRepository.getPortletRepository(groupId, portletId);
	}

	public static String getUniqueFileName(
		long groupId, long folderId, String fileName) {

		PortletFileRepository portletFileRepository =
			_portletFileRepositorySnapshot.get();

		return portletFileRepository.getUniqueFileName(
			groupId, folderId, fileName);
	}

	public static FileEntry movePortletFileEntryToTrash(
			long userId, long fileEntryId)
		throws PortalException {

		PortletFileRepository portletFileRepository =
			_portletFileRepositorySnapshot.get();

		return portletFileRepository.movePortletFileEntryToTrash(
			userId, fileEntryId);
	}

	public static FileEntry movePortletFileEntryToTrash(
			long groupId, long userId, long folderId, String fileName)
		throws PortalException {

		PortletFileRepository portletFileRepository =
			_portletFileRepositorySnapshot.get();

		return portletFileRepository.movePortletFileEntryToTrash(
			groupId, userId, folderId, fileName);
	}

	public static Folder movePortletFolder(
			long groupId, long userId, long folderId, long parentFolderId,
			ServiceContext serviceContext)
		throws PortalException {

		PortletFileRepository portletFileRepository =
			_portletFileRepositorySnapshot.get();

		return portletFileRepository.movePortletFolder(
			groupId, userId, folderId, parentFolderId, serviceContext);
	}

	public static void restorePortletFileEntryFromTrash(
			long userId, long fileEntryId)
		throws PortalException {

		PortletFileRepository portletFileRepository =
			_portletFileRepositorySnapshot.get();

		portletFileRepository.restorePortletFileEntryFromTrash(
			userId, fileEntryId);
	}

	public static void restorePortletFileEntryFromTrash(
			long groupId, long userId, long folderId, String fileName)
		throws PortalException {

		PortletFileRepository portletFileRepository =
			_portletFileRepositorySnapshot.get();

		portletFileRepository.restorePortletFileEntryFromTrash(
			groupId, userId, folderId, fileName);
	}

	public static Hits searchPortletFileEntries(
			long repositoryId, SearchContext searchContext)
		throws PortalException {

		PortletFileRepository portletFileRepository =
			_portletFileRepositorySnapshot.get();

		return portletFileRepository.searchPortletFileEntries(
			repositoryId, searchContext);
	}

	private static final Snapshot<PortletFileRepository>
		_portletFileRepositorySnapshot = new Snapshot<>(
			PortletFileRepositoryUtil.class, PortletFileRepository.class);

}