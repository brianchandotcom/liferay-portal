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

package com.liferay.document.library.repository.portlet.file.repository.internal;

import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.document.library.kernel.exception.NoSuchFolderException;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLTrashLocalService;
import com.liferay.document.library.kernel.util.DLAppHelperThreadLocal;
import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepository;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.RepositoryFactory;
import com.liferay.portal.kernel.repository.RepositoryProvider;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RepositoryLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.systemevent.SystemEventHierarchyEntryThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.repository.portletrepository.PortletRepository;
import com.liferay.portal.webserver.WebServerServlet;
import com.liferay.trash.kernel.util.Trash;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 * @author Alexander Chow
 */
@Component(service = PortletFileRepository.class)
public class PortletFileRepositoryImpl implements PortletFileRepository {

	@Override
	public void addPortletFileEntries(
			long groupId, long userId, String className, long classPK,
			String portletId, long folderId,
			List<ObjectValuePair<String, InputStream>> inputStreamOVPs)
		throws PortalException {

		for (ObjectValuePair<String, InputStream> inputStreamOVP :
				inputStreamOVPs) {

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
			String portletId, long folderId, byte[] bytes, String fileName,
			String mimeType, boolean indexingEnabled)
		throws PortalException {

		if (bytes == null) {
			return null;
		}

		File file = null;

		try {
			file = FileUtil.createTempFile(bytes);

			return addPortletFileEntry(
				groupId, userId, className, classPK, portletId, folderId, file,
				fileName, mimeType, indexingEnabled);
		}
		catch (IOException ioException) {
			throw new SystemException(
				"Unable to write temporary file", ioException);
		}
		finally {
			FileUtil.delete(file);
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

		if (Validator.isNotNull(className) && (classPK > 0)) {
			serviceContext.setAttribute("className", className);
			serviceContext.setAttribute("classPK", String.valueOf(classPK));
		}

		serviceContext.setIndexingEnabled(indexingEnabled);

		if (Validator.isNull(mimeType) ||
			mimeType.equals(ContentTypes.APPLICATION_OCTET_STREAM)) {

			mimeType = MimeTypesUtil.getContentType(file, fileName);
		}

		boolean dlAppHelperEnabled = DLAppHelperThreadLocal.isEnabled();

		try {
			DLAppHelperThreadLocal.setEnabled(false);

			LocalRepository localRepository =
				_repositoryProvider.getLocalRepository(
					repository.getRepositoryId());

			return localRepository.addFileEntry(
				null, userId, folderId, fileName, mimeType, fileName, fileName,
				StringPool.BLANK, StringPool.BLANK, file, null, null,
				serviceContext);
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
		catch (IOException ioException) {
			throw new SystemException(
				"Unable to write temporary file", ioException);
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

		return _run(
			() -> {
				LocalRepository localRepository =
					_repositoryProvider.getLocalRepository(repositoryId);

				try {
					DLAppHelperThreadLocal.setEnabled(false);

					return localRepository.getFolder(
						parentFolderId, folderName);
				}
				catch (NoSuchFolderException noSuchFolderException) {

					// LPS-52675

					if (_log.isDebugEnabled()) {
						_log.debug(noSuchFolderException);
					}

					return localRepository.addFolder(
						userId, parentFolderId, folderName, StringPool.BLANK,
						serviceContext);
				}
			});
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

		Repository repository = _repositoryLocalService.fetchRepository(
			groupId, portletId);

		if (repository != null) {
			return repository;
		}

		Group group = _groupLocalService.getGroup(groupId);

		User user = _userLocalService.getDefaultUser(group.getCompanyId());

		long classNameId = _portal.getClassNameId(
			PortletRepository.class.getName());

		UnicodeProperties typeSettingsUnicodeProperties =
			new UnicodeProperties();

		return _run(
			() -> _repositoryLocalService.addRepository(
				user.getUserId(), groupId, classNameId,
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, portletId,
				StringPool.BLANK, portletId, typeSettingsUnicodeProperties,
				true, serviceContext));
	}

	@Override
	public void deletePortletFileEntries(long groupId, long folderId)
		throws PortalException {

		LocalRepository localRepository =
			_repositoryProvider.getLocalRepository(groupId);

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
			_repositoryProvider.getLocalRepository(groupId);

		List<FileEntry> fileEntries = localRepository.getFileEntries(
			folderId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		for (FileEntry fileEntry : fileEntries) {
			deletePortletFileEntry(fileEntry.getFileEntryId());
		}
	}

	@Override
	public void deletePortletFileEntry(long fileEntryId)
		throws PortalException {

		try {
			LocalRepository localRepository =
				_repositoryProvider.getFileEntryLocalRepository(fileEntryId);

			if (_isAttachment(localRepository.getFileEntry(fileEntryId))) {
				try {
					SystemEventHierarchyEntryThreadLocal.push(FileEntry.class);

					localRepository.deleteFileEntry(fileEntryId);
				}
				finally {
					SystemEventHierarchyEntryThreadLocal.pop(FileEntry.class);
				}
			}
			else {
				localRepository.deleteFileEntry(fileEntryId);
			}
		}
		catch (NoSuchFileEntryException noSuchFileEntryException) {
			if (_log.isWarnEnabled()) {
				_log.warn(noSuchFileEntryException);
			}
		}
	}

	@Override
	public void deletePortletFileEntry(
			long groupId, long folderId, String fileName)
		throws PortalException {

		LocalRepository localRepository =
			_repositoryProvider.getLocalRepository(groupId);

		FileEntry fileEntry = localRepository.getFileEntry(folderId, fileName);

		deletePortletFileEntry(fileEntry.getFileEntryId());
	}

	@Override
	public void deletePortletFolder(long folderId) throws PortalException {
		_run(
			Folder.class,
			() -> {
				try {
					LocalRepository localRepository =
						_repositoryProvider.getFolderLocalRepository(folderId);

					localRepository.deleteFolder(folderId);
				}
				catch (NoSuchFolderException noSuchFolderException) {
					if (_log.isWarnEnabled()) {
						_log.warn(noSuchFolderException);
					}
				}

				return null;
			});
	}

	@Override
	public void deletePortletRepository(long groupId, String portletId)
		throws PortalException {

		Repository repository = _repositoryLocalService.fetchRepository(
			groupId, portletId);

		if (repository != null) {
			_repositoryLocalService.deleteRepository(
				repository.getRepositoryId());
		}
	}

	@Override
	public FileEntry fetchPortletFileEntry(
		long groupId, long folderId, String fileName) {

		try {
			return getPortletFileEntry(groupId, folderId, fileName);
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}
		}

		return null;
	}

	@Override
	public Repository fetchPortletRepository(long groupId, String portletId) {
		return _repositoryLocalService.fetchRepository(groupId, portletId);
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

		return _http.addParameter(portletFileEntryURL, "download", true);
	}

	@Override
	public List<FileEntry> getPortletFileEntries(long groupId, long folderId)
		throws PortalException {

		LocalRepository localRepository =
			_repositoryProvider.getLocalRepository(groupId);

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
			OrderByComparator<FileEntry> orderByComparator)
		throws PortalException {

		LocalRepository localRepository =
			_repositoryProvider.getLocalRepository(groupId);

		return localRepository.getFileEntries(
			folderId, status, start, end, orderByComparator);
	}

	@Override
	public List<FileEntry> getPortletFileEntries(
			long groupId, long folderId,
			OrderByComparator<FileEntry> orderByComparator)
		throws PortalException {

		LocalRepository localRepository =
			_repositoryProvider.getLocalRepository(groupId);

		return localRepository.getFileEntries(
			folderId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, orderByComparator);
	}

	@Override
	public List<FileEntry> getPortletFileEntries(
			long groupId, long folderId, String[] mimeTypes, int status,
			int start, int end, OrderByComparator<FileEntry> orderByComparator)
		throws PortalException {

		LocalRepository localRepository =
			_repositoryProvider.getLocalRepository(groupId);

		return localRepository.getFileEntries(
			folderId, mimeTypes, status, start, end, orderByComparator);
	}

	@Override
	public int getPortletFileEntriesCount(long groupId, long folderId)
		throws PortalException {

		LocalRepository localRepository =
			_repositoryProvider.getLocalRepository(groupId);

		return localRepository.getFileEntriesCount(folderId);
	}

	@Override
	public int getPortletFileEntriesCount(
			long groupId, long folderId, int status)
		throws PortalException {

		LocalRepository localRepository =
			_repositoryProvider.getLocalRepository(groupId);

		return localRepository.getFileEntriesCount(folderId, status);
	}

	@Override
	public int getPortletFileEntriesCount(
			long groupId, long folderId, String[] mimeTypes, int status)
		throws PortalException {

		LocalRepository localRepository =
			_repositoryProvider.getLocalRepository(groupId);

		return localRepository.getFileEntriesCount(folderId, mimeTypes, status);
	}

	@Override
	public FileEntry getPortletFileEntry(long fileEntryId)
		throws PortalException {

		LocalRepository localRepository =
			_repositoryProvider.getFileEntryLocalRepository(fileEntryId);

		return localRepository.getFileEntry(fileEntryId);
	}

	@Override
	public FileEntry getPortletFileEntry(
			long groupId, long folderId, String fileName)
		throws PortalException {

		LocalRepository localRepository =
			_repositoryProvider.getLocalRepository(groupId);

		return localRepository.getFileEntry(folderId, fileName);
	}

	@Override
	public FileEntry getPortletFileEntry(String uuid, long groupId)
		throws PortalException {

		LocalRepository localRepository =
			_repositoryProvider.getLocalRepository(groupId);

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

		StringBundler sb = new StringBundler(12);

		if ((themeDisplay != null) && absoluteURL) {
			sb.append(themeDisplay.getPortalURL());
		}

		sb.append(_portal.getPathContext());
		sb.append("/documents/");
		sb.append(WebServerServlet.PATH_PORTLET_FILE_ENTRY);
		sb.append(StringPool.SLASH);
		sb.append(fileEntry.getGroupId());
		sb.append(StringPool.SLASH);

		String fileName = fileEntry.getFileName();

		if (fileEntry.isInTrash()) {
			fileName = _trash.getOriginalTitle(fileEntry.getTitle());
		}

		sb.append(URLCodec.encodeURL(HtmlUtil.unescape(fileName)));

		sb.append(StringPool.SLASH);
		sb.append(URLCodec.encodeURL(fileEntry.getUuid()));

		if (Validator.isNotNull(queryString)) {
			sb.append(StringPool.QUESTION);

			if (queryString.startsWith(StringPool.AMPERSAND)) {
				sb.append(queryString.substring(1));
			}
			else {
				sb.append(queryString);
			}
		}

		String portletFileEntryURL = sb.toString();

		if ((themeDisplay != null) && themeDisplay.isAddSessionIdToURL()) {
			return _portal.getURLWithSessionId(
				portletFileEntryURL, themeDisplay.getSessionId());
		}

		return portletFileEntryURL;
	}

	@Override
	public Folder getPortletFolder(long folderId) throws PortalException {
		LocalRepository localRepository =
			_repositoryProvider.getFolderLocalRepository(folderId);

		return localRepository.getFolder(folderId);
	}

	@Override
	public Folder getPortletFolder(
			long repositoryId, long parentFolderId, String folderName)
		throws PortalException {

		LocalRepository localRepository =
			_repositoryProvider.getLocalRepository(repositoryId);

		return localRepository.getFolder(parentFolderId, folderName);
	}

	@Override
	public Repository getPortletRepository(long groupId, String portletId)
		throws PortalException {

		return _repositoryLocalService.getRepository(groupId, portletId);
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
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception);
				}

				break;
			}
		}

		return uniqueFileName;
	}

	@Override
	public FileEntry movePortletFileEntryToTrash(long userId, long fileEntryId)
		throws PortalException {

		return _run(
			() -> {
				LocalRepository localRepository =
					_repositoryProvider.getFileEntryLocalRepository(
						fileEntryId);

				return _dlTrashLocalService.moveFileEntryToTrash(
					userId, localRepository.getRepositoryId(), fileEntryId);
			});
	}

	@Override
	public FileEntry movePortletFileEntryToTrash(
			long groupId, long userId, long folderId, String fileName)
		throws PortalException {

		LocalRepository localRepository =
			_repositoryProvider.getLocalRepository(groupId);

		FileEntry fileEntry = localRepository.getFileEntry(folderId, fileName);

		return movePortletFileEntryToTrash(userId, fileEntry.getFileEntryId());
	}

	@Override
	public Folder movePortletFolder(
			long groupId, long userId, long folderId, long parentFolderId,
			ServiceContext serviceContext)
		throws PortalException {

		return _run(
			() -> {
				LocalRepository localRepository =
					_repositoryProvider.getLocalRepository(groupId);

				return localRepository.moveFolder(
					userId, folderId, parentFolderId, serviceContext);
			});
	}

	@Override
	public void restorePortletFileEntryFromTrash(long userId, long fileEntryId)
		throws PortalException {

		_run(
			() -> {
				LocalRepository localRepository =
					_repositoryProvider.getFileEntryLocalRepository(
						fileEntryId);

				_dlTrashLocalService.restoreFileEntryFromTrash(
					userId, localRepository.getRepositoryId(), fileEntryId);

				return null;
			});
	}

	@Override
	public void restorePortletFileEntryFromTrash(
			long groupId, long userId, long folderId, String fileName)
		throws PortalException {

		LocalRepository localRepository =
			_repositoryProvider.getLocalRepository(groupId);

		FileEntry fileEntry = localRepository.getFileEntry(folderId, fileName);

		restorePortletFileEntryFromTrash(userId, fileEntry.getFileEntryId());
	}

	@Override
	public Hits searchPortletFileEntries(
			long repositoryId, SearchContext searchContext)
		throws PortalException {

		com.liferay.portal.kernel.repository.Repository repository =
			_repositoryProvider.getRepository(repositoryId);

		return repository.search(searchContext);
	}

	private boolean _isAttachment(FileEntry fileEntry) {
		if (!(fileEntry.getModel() instanceof DLFileEntry)) {
			return false;
		}

		DLFileEntry dlFileEntry = (DLFileEntry)fileEntry.getModel();

		if (dlFileEntry.getClassNameId() == 0) {
			return false;
		}

		return true;
	}

	private <T, E extends Throwable> T _run(
			Class<?> clazz, UnsafeSupplier<T, E> unsafeSupplier)
		throws E {

		boolean dlAppHelperEnabled = DLAppHelperThreadLocal.isEnabled();

		try {
			DLAppHelperThreadLocal.setEnabled(false);

			if (clazz != null) {
				SystemEventHierarchyEntryThreadLocal.push(clazz);
			}

			return unsafeSupplier.get();
		}
		finally {
			if (clazz != null) {
				SystemEventHierarchyEntryThreadLocal.pop(clazz);
			}

			DLAppHelperThreadLocal.setEnabled(dlAppHelperEnabled);
		}
	}

	private <T, E extends Throwable> T _run(UnsafeSupplier<T, E> unsafeSupplier)
		throws E {

		return _run(null, unsafeSupplier);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortletFileRepositoryImpl.class);

	@Reference
	private DLTrashLocalService _dlTrashLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Http _http;

	@Reference(
		target = "(class.name=com.liferay.portal.repository.liferayrepository.LiferayRepository)"
	)
	private RepositoryFactory _liferayRepositoryFactory;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(class.name=com.liferay.portal.repository.portletrepository.PortletRepository)"
	)
	private RepositoryFactory _portletRepositoryFactory;

	@Reference
	private RepositoryLocalService _repositoryLocalService;

	@Reference
	private RepositoryProvider _repositoryProvider;

	@Reference
	private Trash _trash;

	@Reference
	private UserLocalService _userLocalService;

}