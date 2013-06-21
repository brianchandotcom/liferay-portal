/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnmodifiableList;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Repository;
import com.liferay.portal.model.User;
import com.liferay.portal.repository.liferayrepository.LiferayRepository;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.RepositoryLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.util.DLAppHelperThreadLocal;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
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
		throws PortalException, SystemException {

		for (int i = 0; i < inputStreamOVPs.size(); i++) {
			ObjectValuePair<String, InputStream> inputStreamOVP =
				inputStreamOVPs.get(i);

			InputStream inputStream = inputStreamOVP.getValue();
			String fileName = inputStreamOVP.getKey();

			File file = null;

			try {
				file = FileUtil.createTempFile(inputStream);

				String mimeType = MimeTypesUtil.getContentType(file, fileName);

				addPortletFileEntry(
					groupId, userId, className, classPK, portletId, folderId,
					file, fileName, mimeType);
			}
			catch (IOException ioe) {
				throw new SystemException(
					"Unable to write temporary file " + file.getAbsolutePath(),
					ioe);
			}
			finally {
				FileUtil.delete(file);
			}
		}
	}

	@Override
	public FileEntry addPortletFileEntry(
			long groupId, long userId, String className, long classPK,
			String portletId, long folderId, File file, String fileName,
			String mimeType)
		throws PortalException, SystemException {

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

		boolean dlAppHelperEnabled = DLAppHelperThreadLocal.isEnabled();

		try {
			DLAppHelperThreadLocal.setEnabled(false);

			return DLAppLocalServiceUtil.addFileEntry(
				userId, repository.getRepositoryId(), folderId, fileName,
				mimeType, fileName, StringPool.BLANK, StringPool.BLANK, file,
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
			String fileName, String mimeType)
		throws PortalException, SystemException {

		if (inputStream == null) {
			return null;
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		Repository repository = addPortletRepository(
			groupId, portletId, serviceContext);

		serviceContext.setAttribute("className", className);
		serviceContext.setAttribute("classPK", String.valueOf(classPK));

		boolean dlAppHelperEnabled = DLAppHelperThreadLocal.isEnabled();

		File file = null;

		try {
			DLAppHelperThreadLocal.setEnabled(false);

			file = FileUtil.createTempFile(inputStream);

			return DLAppLocalServiceUtil.addFileEntry(
				userId, repository.getRepositoryId(), folderId, fileName,
				mimeType, fileName, StringPool.BLANK, StringPool.BLANK, file,
				serviceContext);
		}
		catch (IOException ioe) {
			throw new SystemException(
				"Unable to write temporary file " + file.getAbsolutePath(),
				ioe);
		}
		finally {
			FileUtil.delete(file);

			DLAppHelperThreadLocal.setEnabled(dlAppHelperEnabled);
		}
	}

	@Override
	public Folder addPortletFolder(
			long userId, long repositoryId, long parentFolderId,
			String folderName, ServiceContext serviceContext)
		throws PortalException, SystemException {

		boolean dlAppHelperEnabled = DLAppHelperThreadLocal.isEnabled();

		try {
			DLAppHelperThreadLocal.setEnabled(false);

			return DLAppLocalServiceUtil.getFolder(
				repositoryId, parentFolderId, folderName);
		}
		catch (NoSuchFolderException nsfe) {
			return DLAppLocalServiceUtil.addFolder(
				userId, repositoryId, parentFolderId, folderName,
				StringPool.BLANK, serviceContext);
		}
		finally {
			DLAppHelperThreadLocal.setEnabled(dlAppHelperEnabled);
		}
	}

	@Override
	public Repository addPortletRepository(
			long groupId, String portletId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		Repository repository = RepositoryLocalServiceUtil.fetchRepository(
			groupId, portletId);

		if (repository != null) {
			return repository;
		}

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		User user = UserLocalServiceUtil.getDefaultUser(group.getCompanyId());

		long classNameId = PortalUtil.getClassNameId(
			LiferayRepository.class.getName());

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

	@Override
	public void deleteFolder(long folderId)
		throws PortalException, SystemException {

		boolean dlAppHelperEnabled = DLAppHelperThreadLocal.isEnabled();

		try {
			DLAppHelperThreadLocal.setEnabled(false);

			DLAppLocalServiceUtil.deleteFolder(folderId);
		}
		finally {
			DLAppHelperThreadLocal.setEnabled(dlAppHelperEnabled);
		}
	}

	@Override
	public void deletePortletFileEntries(long groupId, long folderId)
		throws PortalException, SystemException {

		List<DLFileEntry> dlFileEntries =
			DLFileEntryLocalServiceUtil.getFileEntries(groupId, folderId);

		for (DLFileEntry dlFileEntry : dlFileEntries) {
			deletePortletFileEntry(dlFileEntry.getFileEntryId());
		}
	}

	@Override
	public void deletePortletFileEntries(
			long groupId, long folderId, int status)
		throws PortalException, SystemException {

		List<DLFileEntry> dlFileEntries =
			DLFileEntryLocalServiceUtil.getFileEntries(
				groupId, folderId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				null);

		for (DLFileEntry dlFileEntry : dlFileEntries) {
			deletePortletFileEntry(dlFileEntry.getFileEntryId());
		}
	}

	@Override
	public void deletePortletFileEntry(long fileEntryId)
		throws PortalException, SystemException {

		boolean dlAppHelperEnabled = DLAppHelperThreadLocal.isEnabled();

		try {
			DLAppHelperThreadLocal.setEnabled(false);

			DLAppLocalServiceUtil.deleteFileEntry(fileEntryId);
		}
		finally {
			DLAppHelperThreadLocal.setEnabled(dlAppHelperEnabled);
		}
	}

	@Override
	public void deletePortletFileEntry(
			long groupId, long folderId, String fileName)
		throws PortalException, SystemException {

		FileEntry fileEntry = DLAppLocalServiceUtil.getFileEntry(
			groupId, folderId, fileName);

		deletePortletFileEntry(fileEntry.getFileEntryId());
	}

	@Override
	public void deletePortletRepository(long groupId, String portletId)
		throws PortalException, SystemException {

		Repository repository = RepositoryLocalServiceUtil.fetchRepository(
			groupId, portletId);

		if (repository != null) {
			RepositoryLocalServiceUtil.deleteRepository(
				repository.getRepositoryId());
		}
	}

	@Override
	public Repository fetchPortletRepository(long groupId, String portletId)
		throws SystemException {

		return RepositoryLocalServiceUtil.fetchRepository(groupId, portletId);
	}

	@Override
	public List<FileEntry> getPortletFileEntries(long groupId, long folderId)
		throws SystemException {

		return toFileEntries(
			DLFileEntryLocalServiceUtil.getFileEntries(groupId, folderId));
	}

	@Override
	public List<FileEntry> getPortletFileEntries(
			long groupId, long folderId, int status)
		throws SystemException {

		return getPortletFileEntries(
			groupId, folderId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	@Override
	public List<FileEntry> getPortletFileEntries(
			long groupId, long folderId, int status, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return toFileEntries(
			DLFileEntryLocalServiceUtil.getFileEntries(
				groupId, folderId, status, start, end, obc));
	}

	@Override
	public int getPortletFileEntriesCount(long groupId, long folderId)
		throws SystemException {

		return DLFileEntryLocalServiceUtil.getFileEntriesCount(
			groupId, folderId);
	}

	@Override
	public int getPortletFileEntriesCount(
			long groupId, long folderId, int status)
		throws SystemException {

		return DLFileEntryLocalServiceUtil.getFileEntriesCount(
			groupId, folderId, status);
	}

	@Override
	public FileEntry getPortletFileEntry(long fileEntryId)
		throws PortalException, SystemException {

		return DLAppLocalServiceUtil.getFileEntry(fileEntryId);
	}

	@Override
	public FileEntry getPortletFileEntry(
			long groupId, long folderId, String fileName)
		throws PortalException, SystemException {

		return DLAppLocalServiceUtil.getFileEntry(groupId, folderId, fileName);
	}

	@Override
	public Folder getPortletFolder(long folderId)
		throws PortalException, SystemException {

		return DLAppLocalServiceUtil.getFolder(folderId);
	}

	@Override
	public Folder getPortletFolder(
			long repositoryId, long parentFolderId, String folderName)
		throws PortalException, SystemException {

		return DLAppLocalServiceUtil.getFolder(
			repositoryId, parentFolderId, folderName);
	}

	@Override
	public Repository getPortletRepository(long groupId, String portletId)
		throws PortalException, SystemException {

		return RepositoryLocalServiceUtil.getRepository(groupId, portletId);
	}

	@Override
	public void movePortletFileEntryToTrash(long userId, long fileEntryId)
		throws PortalException, SystemException {

		boolean dlAppHelperEnabled = DLAppHelperThreadLocal.isEnabled();

		try {
			DLAppHelperThreadLocal.setEnabled(false);

			DLAppLocalServiceUtil.moveFileEntryToTrash(userId, fileEntryId);
		}
		finally {
			DLAppHelperThreadLocal.setEnabled(dlAppHelperEnabled);
		}
	}

	@Override
	public void movePortletFileEntryToTrash(
			long groupId, long userId, long folderId, String fileName)
		throws PortalException, SystemException {

		FileEntry fileEntry = DLAppLocalServiceUtil.getFileEntry(
			groupId, folderId, fileName);

		movePortletFileEntryToTrash(userId, fileEntry.getFileEntryId());
	}

	@Override
	public void restorePortletFileEntryFromTrash(long userId, long fileEntryId)
		throws PortalException, SystemException {

		boolean dlAppHelperEnabled = DLAppHelperThreadLocal.isEnabled();

		try {
			DLAppHelperThreadLocal.setEnabled(false);

			DLAppLocalServiceUtil.restoreFileEntryFromTrash(
				userId, fileEntryId);
		}
		finally {
			DLAppHelperThreadLocal.setEnabled(dlAppHelperEnabled);
		}
	}

	@Override
	public void restorePortletFileEntryFromTrash(
			long groupId, long userId, long folderId, String fileName)
		throws PortalException, SystemException {

		FileEntry fileEntry = DLAppLocalServiceUtil.getFileEntry(
			groupId, folderId, fileName);

		restorePortletFileEntryFromTrash(userId, fileEntry.getFileEntryId());
	}

	/**
	 * @see {@link
	 *      com.liferay.portal.repository.liferayrepository.util.LiferayBase#toFileEntries}
	 */
	protected List<FileEntry> toFileEntries(List<DLFileEntry> dlFileEntries) {
		List<FileEntry> fileEntries = new ArrayList<FileEntry>(
			dlFileEntries.size());

		for (DLFileEntry dlFileEntry : dlFileEntries) {
			FileEntry fileEntry = new LiferayFileEntry(dlFileEntry);

			fileEntries.add(fileEntry);
		}

		if (dlFileEntries instanceof UnmodifiableList) {
			return new UnmodifiableList<FileEntry>(fileEntries);
		}
		else {
			return fileEntries;
		}
	}

}