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

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.RepositoryProviderUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.repository.util.RepositoryTrashUtil;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.systemevent.SystemEventHierarchyEntryThreadLocal;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Repository;
import com.liferay.portal.model.User;
import com.liferay.portal.repository.portletrepository.PortletRepository;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.RepositoryLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.webserver.WebServerServlet;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.util.DLAppHelperThreadLocal;
import com.liferay.portlet.trash.util.TrashUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.List;

/**
 * @author Eudaldo Alonso
 * @author Alexander Chow
 */
@DoPrivileged
public class PortletFileRepositoryImpl implements PortletFileRepository {

	@Override
	public void addPortletFileEntries(
			long groupId, long userId, String className, long classPK,
			String portletId, long folderId,
			List<ObjectValuePair<String, InputStream>> inputStreamOVPs)
		throws PortalException {

		for (int i = 0; i < inputStreamOVPs.size(); i++) {
			ObjectValuePair<String, InputStream> inputStreamOVP =
				inputStreamOVPs.get(i);

			InputStream inputStream = inputStreamOVP.getValue();
			String fileName = inputStreamOVP.getKey();

			addPortletFileEntry(
				groupId, userId, className, classPK, portletId, folderId,
				inputStream, fileName, StringPool.BLANK, true);
		}
	}

	@Override
	public FileEntry addPortletFileEntry(
			long groupId, long userId, String className, long classPK,
			String portletId, long folderId, File file, String fileName,
			String mimeType, boolean indexingEnabled)
		throws PortalException {

		if (Validator.isNull(fileName)) {
			return null;
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		Repository repository = addPortletRepository(
			groupId, portletId, serviceContext);

		serviceContext.setAttribute("className", className);
		serviceContext.setAttribute("classPK", String.valueOf(classPK));
		serviceContext.setIndexingEnabled(indexingEnabled);

		if (Validator.isNull(mimeType) ||
			mimeType.equals(ContentTypes.APPLICATION_OCTET_STREAM)) {

			mimeType = MimeTypesUtil.getContentType(file, fileName);
		}

		boolean dlAppHelperEnabled = DLAppHelperThreadLocal.isEnabled();

		try {
			DLAppHelperThreadLocal.setEnabled(false);

			LocalRepository localRepository =
				RepositoryProviderUtil.getLocalRepository(
					repository.getRepositoryId());

			return localRepository.addFileEntry(
				userId, folderId, fileName, mimeType, fileName,
				StringPool.BLANK, StringPool.BLANK, file, serviceContext);
		}
		finally {
			DLAppHelperThreadLocal.setEnabled(dlAppHelperEnabled);
		}
	}

	@Override
	public FileEntry addPortletFileEntry(
			long groupId, long userId, String className, long classPK,
			String portletId, long folderId, InputStream inputStream,
			String fileName, String mimeType, boolean indexingEnabled)
		throws PortalException {

		if (inputStream == null) {
			return null;
		}

		File file = null;

		try {
			file = FileUtil.createTempFile(inputStream);

			return addPortletFileEntry(
				groupId, userId, className, classPK, portletId, folderId, file,
				fileName, mimeType, indexingEnabled);
		}
		catch (IOException ioe) {
			throw new SystemException("Unable to write temporary file", ioe);
		}
		finally {
			FileUtil.delete(file);
		}
	}

	@Override
	public Folder addPortletFolder(
			long userId, long repositoryId, long parentFolderId,
			String folderName, ServiceContext serviceContext)
		throws PortalException {

		boolean dlAppHelperEnabled = DLAppHelperThreadLocal.isEnabled();

		LocalRepository localRepository =
			RepositoryProviderUtil.getLocalRepository(repositoryId);

		try {
			DLAppHelperThreadLocal.setEnabled(false);

			return localRepository.getFolder(parentFolderId, folderName);
		}
		catch (NoSuchFolderException nsfe) {
			return localRepository.addFolder(
				userId, parentFolderId, folderName, StringPool.BLANK,
				serviceContext);
		}
		finally {
			DLAppHelperThreadLocal.setEnabled(dlAppHelperEnabled);
		}
	}

	@Override
	public Folder addPortletFolder(
			long groupId, long userId, String portletId, long parentFolderId,
			String folderName, ServiceContext serviceContext)
		throws PortalException {

		Repository repository = addPortletRepository(
			groupId, portletId, serviceContext);

		return addPortletFolder(
			userId, repository.getRepositoryId(), parentFolderId, folderName,
			serviceContext);
	}

	@Override
	public Repository addPortletRepository(
			long groupId, String portletId, ServiceContext serviceContext)
		throws PortalException {

		Repository repository = RepositoryLocalServiceUtil.fetchRepository(
			groupId, portletId);

		if (repository != null) {
			return repository;
		}

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		User user = UserLocalServiceUtil.getDefaultUser(group.getCompanyId());

		long classNameId = PortalUtil.getClassNameId(
			PortletRepository.class.getName());

		UnicodeProperties typeSettingsProperties = new UnicodeProperties();

		boolean dlAppHelperEnabled = DLAppHelperThreadLocal.isEnabled();

		try {
			DLAppHelperThreadLocal.setEnabled(false);

			return RepositoryLocalServiceUtil.addRepository(
				user.getUserId(), groupId, classNameId,
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, portletId,
				StringPool.BLANK, portletId, typeSettingsProperties, true,
				serviceContext);
		}
		finally {
			DLAppHelperThreadLocal.setEnabled(dlAppHelperEnabled);
		}
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #deletePortletFolder}
	 */
	@Deprecated
	@Override
	public void deleteFolder(long folderId) throws PortalException {
		deletePortletFolder(folderId);
	}

	@Override
	public void deletePortletFileEntries(long groupId, long folderId)
		throws PortalException {

		LocalRepository localRepository =
			RepositoryProviderUtil.getLocalRepository(groupId);

		List<FileEntry> fileEntries = localRepository.getFileEntries(
			folderId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		for (FileEntry fileEntry : fileEntries) {
			deletePortletFileEntry(fileEntry.getFileEntryId());
		}
	}

	@Override
	public void deletePortletFileEntries(
			long groupId, long folderId, int status)
		throws PortalException {

		LocalRepository localRepository =
			RepositoryProviderUtil.getLocalRepository(groupId);

		List<FileEntry> fileEntries = localRepository.getFileEntries(
			folderId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		for (FileEntry fileEntry : fileEntries) {
			deletePortletFileEntry(fileEntry.getFileEntryId());
		}
	}

	@Override
	public void deletePortletFileEntry(long fileEntryId)
		throws PortalException {

		boolean dlAppHelperEnabled = DLAppHelperThreadLocal.isEnabled();

		try {
			DLAppHelperThreadLocal.setEnabled(false);

			SystemEventHierarchyEntryThreadLocal.push(FileEntry.class);

			LocalRepository localRepository =
				RepositoryProviderUtil.getFileEntryLocalRepository(fileEntryId);

			localRepository.deleteFileEntry(fileEntryId);
		}
		catch (NoSuchFileEntryException nsfee) {
			if (_log.isWarnEnabled()) {
				_log.warn(nsfee, nsfee);
			}
		}
		finally {
			DLAppHelperThreadLocal.setEnabled(dlAppHelperEnabled);

			SystemEventHierarchyEntryThreadLocal.pop(FileEntry.class);
		}
	}

	@Override
	public void deletePortletFileEntry(
			long groupId, long folderId, String fileName)
		throws PortalException {

		LocalRepository localRepository =
			RepositoryProviderUtil.getLocalRepository(groupId);

		FileEntry fileEntry = localRepository.getFileEntry(folderId, fileName);

		deletePortletFileEntry(fileEntry.getFileEntryId());
	}

	@Override
	public void deletePortletFolder(long folderId) throws PortalException {
		boolean dlAppHelperEnabled = DLAppHelperThreadLocal.isEnabled();

		try {
			DLAppHelperThreadLocal.setEnabled(false);

			SystemEventHierarchyEntryThreadLocal.push(Folder.class);

			LocalRepository localRepository =
				RepositoryProviderUtil.getFolderLocalRepository(folderId);

			localRepository.deleteFolder(folderId);
		}
		catch (NoSuchFileEntryException nsfee) {
			if (_log.isWarnEnabled()) {
				_log.warn(nsfee, nsfee);
			}
		}
		finally {
			DLAppHelperThreadLocal.setEnabled(dlAppHelperEnabled);

			SystemEventHierarchyEntryThreadLocal.pop(Folder.class);
		}
	}

	@Override
	public void deletePortletRepository(long groupId, String portletId)
		throws PortalException {

		Repository repository = RepositoryLocalServiceUtil.fetchRepository(
			groupId, portletId);

		if (repository != null) {
			RepositoryLocalServiceUtil.deleteRepository(
				repository.getRepositoryId());
		}
	}

	@Override
	public Repository fetchPortletRepository(long groupId, String portletId) {
		return RepositoryLocalServiceUtil.fetchRepository(groupId, portletId);
	}

	@Override
	public String getDownloadPortletFileEntryURL(
		ThemeDisplay themeDisplay, FileEntry fileEntry, String queryString) {

		return getDownloadPortletFileEntryURL(
			themeDisplay, fileEntry, queryString, true);
	}

	@Override
	public String getDownloadPortletFileEntryURL(
		ThemeDisplay themeDisplay, FileEntry fileEntry, String queryString,
		boolean absoluteURL) {

		String portletFileEntryURL = getPortletFileEntryURL(
			themeDisplay, fileEntry, queryString, absoluteURL);

		return HttpUtil.addParameter(portletFileEntryURL, "download", true);
	}

	@Override
	public List<FileEntry> getPortletFileEntries(long groupId, long folderId)
		throws PortalException {

		LocalRepository localRepository =
			RepositoryProviderUtil.getLocalRepository(groupId);

		return localRepository.getFileEntries(
			folderId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	@Override
	public List<FileEntry> getPortletFileEntries(
			long groupId, long folderId, int status)
		throws PortalException {

		return getPortletFileEntries(
			groupId, folderId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	@Override
	public List<FileEntry> getPortletFileEntries(
			long groupId, long folderId, int status, int start, int end,
			OrderByComparator<FileEntry> obc)
		throws PortalException {

		LocalRepository localRepository =
			RepositoryProviderUtil.getLocalRepository(groupId);

		return localRepository.getFileEntries(
			folderId, status, start, end, obc);
	}

	@Override
	public List<FileEntry> getPortletFileEntries(
			long groupId, long folderId, OrderByComparator<FileEntry> obc)
		throws PortalException {

		LocalRepository localRepository =
			RepositoryProviderUtil.getLocalRepository(groupId);

		return localRepository.getFileEntries(
			folderId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, obc);
	}

	@Override
	public int getPortletFileEntriesCount(long groupId, long folderId)
		throws PortalException {

		LocalRepository localRepository =
			RepositoryProviderUtil.getLocalRepository(groupId);

		return localRepository.getFileEntriesCount(folderId);
	}

	@Override
	public int getPortletFileEntriesCount(
			long groupId, long folderId, int status)
		throws PortalException {

		LocalRepository localRepository =
			RepositoryProviderUtil.getLocalRepository(groupId);

		return localRepository.getFileEntriesCount(folderId, status);
	}

	@Override
	public FileEntry getPortletFileEntry(long fileEntryId)
		throws PortalException {

		try {
			LocalRepository localRepository =
				RepositoryProviderUtil.getFileEntryLocalRepository(fileEntryId);

			return localRepository.getFileEntry(fileEntryId);
		}
		catch (NoSuchFileEntryException nsfee) {
			throw new NoSuchFileEntryException(nsfee);
		}
	}

	@Override
	public FileEntry getPortletFileEntry(
			long groupId, long folderId, String fileName)
		throws PortalException {

		LocalRepository localRepository =
			RepositoryProviderUtil.getLocalRepository(groupId);

		return localRepository.getFileEntry(folderId, fileName);
	}

	@Override
	public FileEntry getPortletFileEntry(String uuid, long groupId)
		throws PortalException {

		LocalRepository localRepository =
			RepositoryProviderUtil.getLocalRepository(groupId);

		return localRepository.getFileEntryByUuid(uuid);
	}

	@Override
	public String getPortletFileEntryURL(
		ThemeDisplay themeDisplay, FileEntry fileEntry, String queryString) {

		return getPortletFileEntryURL(
			themeDisplay, fileEntry, queryString, true);
	}

	@Override
	public String getPortletFileEntryURL(
		ThemeDisplay themeDisplay, FileEntry fileEntry, String queryString,
		boolean absoluteURL) {

		StringBundler sb = new StringBundler(10);

		if (themeDisplay != null) {
			if (absoluteURL) {
				sb.append(themeDisplay.getPortalURL());
			}
		}

		sb.append(PortalUtil.getPathContext());
		sb.append("/documents/");
		sb.append(WebServerServlet.PATH_PORTLET_FILE_ENTRY);
		sb.append(StringPool.SLASH);
		sb.append(fileEntry.getGroupId());
		sb.append(StringPool.SLASH);

		String title = fileEntry.getTitle();

		if (fileEntry.isInTrash()) {
			title = TrashUtil.getOriginalTitle(fileEntry.getTitle());
		}

		sb.append(HttpUtil.encodeURL(HtmlUtil.unescape(title)));

		sb.append(StringPool.SLASH);
		sb.append(HttpUtil.encodeURL(fileEntry.getUuid()));

		if (Validator.isNotNull(queryString)) {
			sb.append(StringPool.QUESTION);
			sb.append(queryString);
		}

		String portletFileEntryURL = sb.toString();

		if ((themeDisplay != null) && themeDisplay.isAddSessionIdToURL()) {
			return PortalUtil.getURLWithSessionId(
				portletFileEntryURL, themeDisplay.getSessionId());
		}

		return portletFileEntryURL;
	}

	@Override
	public Folder getPortletFolder(long folderId) throws PortalException {
		try {
			LocalRepository localRepository =
				RepositoryProviderUtil.getFolderLocalRepository(folderId);

			return localRepository.getFolder(folderId);
		}
		catch (NoSuchFileEntryException nsfee) {
			throw new NoSuchFolderException(nsfee);
		}
	}

	@Override
	public Folder getPortletFolder(
			long repositoryId, long parentFolderId, String folderName)
		throws PortalException {

		LocalRepository localRepository =
			RepositoryProviderUtil.getLocalRepository(repositoryId);

		return localRepository.getFolder(parentFolderId, folderName);
	}

	@Override
	public Repository getPortletRepository(long groupId, String portletId)
		throws PortalException {

		return RepositoryLocalServiceUtil.getRepository(groupId, portletId);
	}

	@Override
	public String getUniqueFileName(
		long groupId, long folderId, String fileName) {

		String uniqueFileName = fileName;

		for (int i = 1;; i++) {
			try {
				getPortletFileEntry(groupId, folderId, uniqueFileName);

				uniqueFileName = FileUtil.appendParentheticalSuffix(
					fileName, String.valueOf(i));
			}
			catch (Exception e) {
				break;
			}
		}

		return uniqueFileName;
	}

	@Override
	public FileEntry movePortletFileEntryToTrash(long userId, long fileEntryId)
		throws PortalException {

		boolean dlAppHelperEnabled = DLAppHelperThreadLocal.isEnabled();

		try {
			DLAppHelperThreadLocal.setEnabled(false);

			LocalRepository localRepository =
				RepositoryProviderUtil.getFileEntryLocalRepository(fileEntryId);

			return RepositoryTrashUtil.moveFileEntryToTrash(
				userId, localRepository.getRepositoryId(), fileEntryId);
		}
		finally {
			DLAppHelperThreadLocal.setEnabled(dlAppHelperEnabled);
		}
	}

	@Override
	public FileEntry movePortletFileEntryToTrash(
			long groupId, long userId, long folderId, String fileName)
		throws PortalException {

		LocalRepository localRepository =
			RepositoryProviderUtil.getLocalRepository(groupId);

		FileEntry fileEntry = localRepository.getFileEntry(folderId, fileName);

		return movePortletFileEntryToTrash(userId, fileEntry.getFileEntryId());
	}

	@Override
	public void restorePortletFileEntryFromTrash(long userId, long fileEntryId)
		throws PortalException {

		boolean dlAppHelperEnabled = DLAppHelperThreadLocal.isEnabled();

		try {
			DLAppHelperThreadLocal.setEnabled(false);

			LocalRepository localRepository =
				RepositoryProviderUtil.getFileEntryLocalRepository(fileEntryId);

			RepositoryTrashUtil.restoreFileEntryFromTrash(
				userId, localRepository.getRepositoryId(), fileEntryId);
		}
		finally {
			DLAppHelperThreadLocal.setEnabled(dlAppHelperEnabled);
		}
	}

	@Override
	public void restorePortletFileEntryFromTrash(
			long groupId, long userId, long folderId, String fileName)
		throws PortalException {

		LocalRepository localRepository =
			RepositoryProviderUtil.getLocalRepository(groupId);

		FileEntry fileEntry = localRepository.getFileEntry(folderId, fileName);

		restorePortletFileEntryFromTrash(userId, fileEntry.getFileEntryId());
	}

	@Override
	public Hits searchPortletFileEntries(
			long repositoryId, SearchContext searchContext)
		throws PortalException {

		com.liferay.portal.kernel.repository.Repository repository =
			RepositoryProviderUtil.getRepository(repositoryId);

		return repository.search(searchContext);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortletFileRepositoryImpl.class);

}