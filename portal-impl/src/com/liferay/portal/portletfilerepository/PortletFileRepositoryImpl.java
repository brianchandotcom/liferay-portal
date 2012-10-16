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

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Repository;
import com.liferay.portal.model.User;
import com.liferay.portal.repository.liferayrepository.LiferayRepository;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.RepositoryLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import com.liferay.portlet.documentlibrary.util.DLAppHelperThreadLocal;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.List;

/**
 * @author Eudaldo Alonso
 */
public class PortletFileRepositoryImpl implements PortletFileRepository {

	public void addPortletFileEntries(
			long groupId, long userId, String portletId, long folderId,
			List<ObjectValuePair<String, InputStream>> inputStreamOVPs)
		throws PortalException, SystemException {

		for (int i = 0; i < inputStreamOVPs.size(); i++) {
			ObjectValuePair<String, InputStream> inputStreamOVP =
				inputStreamOVPs.get(i);

			addPortletFileEntry(
				groupId, userId, portletId, folderId, inputStreamOVP.getValue(),
				inputStreamOVP.getKey());
		}
	}

	public FileEntry addPortletFileEntry(
			long groupId, long userId, String portletId, long folderId,
			File file, String fileName)
		throws PortalException, SystemException {

		if (Validator.isNull(fileName)) {
			return null;
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		long repositoryId = getPortletRepository(
			groupId, portletId, serviceContext);

		String contentType = MimeTypesUtil.getContentType(file);

		boolean dlAppHelperEnabled = DLAppHelperThreadLocal.isEnabled();

		try {
			DLAppHelperThreadLocal.setEnabled(false);

			return DLAppLocalServiceUtil.addFileEntry(
				userId, repositoryId, folderId, fileName, contentType, fileName,
				StringPool.BLANK, StringPool.BLANK, file, serviceContext);
		}
		finally {
			DLAppHelperThreadLocal.setEnabled(dlAppHelperEnabled);
		}
	}

	public FileEntry addPortletFileEntry(
			long groupId, long userId, String portletId, long folderId,
			InputStream inputStream, String fileName)
		throws PortalException, SystemException {

		if (inputStream == null) {
			return null;
		}

		long size = 0;

		try {
			byte[] bytes = FileUtil.getBytes(inputStream, -1, false);

			size = bytes.length;
		}
		catch (IOException ioe) {
			return null;
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		long repositoryId = getPortletRepository(
			groupId, portletId, serviceContext);

		String contentType = MimeTypesUtil.getContentType(
			inputStream, fileName);

		boolean dlAppHelperEnabled = DLAppHelperThreadLocal.isEnabled();

		try {
			DLAppHelperThreadLocal.setEnabled(false);

			return DLAppLocalServiceUtil.addFileEntry(
				userId, repositoryId, folderId, fileName, contentType, fileName,
				StringPool.BLANK, StringPool.BLANK, inputStream, size,
				serviceContext);
		}
		finally {
			DLAppHelperThreadLocal.setEnabled(dlAppHelperEnabled);
		}
	}

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

	public void deletePortletFileEntries(long groupId, long folderId)
		throws PortalException, SystemException {

		List<DLFileEntry> dlFileEntries =
			DLFileEntryLocalServiceUtil.getFileEntries(groupId, folderId);

		for (DLFileEntry dlFileEntry : dlFileEntries) {
			deletePortletFileEntry(dlFileEntry.getFileEntryId());
		}
	}

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

	public void deletePortletFileEntry(
			long groupId, long folderId, String fileName)
		throws PortalException, SystemException {

		FileEntry fileEntry = DLAppLocalServiceUtil.getFileEntry(
			groupId, folderId, fileName);

		deletePortletFileEntry(fileEntry.getFileEntryId());
	}

	public List<DLFileEntry> getPortletFileEntries(long groupId, long folderId)
		throws SystemException {

		return DLFileEntryLocalServiceUtil.getFileEntries(groupId, folderId);
	}

	public List<DLFileEntry> getPortletFileEntries(
			long groupId, long folderId, int status)
		throws SystemException {

		return getPortletFileEntries(
			groupId, folderId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	public List<DLFileEntry> getPortletFileEntries(
			long groupId, long folderId, int status, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return DLFileEntryLocalServiceUtil.getFileEntries(
			groupId, folderId, status, start, end, obc);
	}

	public int getPortletFileEntriesCount(long groupId, long folderId)
		throws SystemException {

		return DLFileEntryLocalServiceUtil.getFileEntriesCount(
			groupId, folderId);
	}

	public int getPortletFileEntriesCount(
			long groupId, long folderId, int status)
		throws SystemException {

		return DLFileEntryLocalServiceUtil.getFileEntriesCount(
			groupId, folderId, status);
	}

	public DLFileEntry getPortletFileEntry(long fileEntryId)
		throws PortalException, SystemException {

		return DLFileEntryLocalServiceUtil.getFileEntry(fileEntryId);
	}

	public DLFileEntry getPortletFileEntry(
			long groupId, long folderId, String fileName)
		throws PortalException, SystemException {

		return DLFileEntryLocalServiceUtil.getFileEntry(
			groupId, folderId, fileName);
	}

	public DLFolder getPortletFolder(long folderId)
		throws PortalException, SystemException {

		return DLFolderLocalServiceUtil.getFolder(folderId);
	}

	public DLFolder getPortletFolder(
			long userId, long repositoryId, long parentFolderId,
			String folderName, ServiceContext serviceContext)
		throws PortalException, SystemException {

		Folder folder = null;

		try {
			folder = DLAppLocalServiceUtil.getFolder(
				repositoryId, parentFolderId, folderName);
		}
		catch (NoSuchFolderException nsfe) {
			folder = DLAppLocalServiceUtil.addFolder(
				userId, repositoryId, parentFolderId, folderName,
				StringPool.BLANK, serviceContext);
		}

		return (DLFolder)folder.getModel();
	}

	public long getPortletRepository(
			long groupId, String portletId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		Repository repository = RepositoryLocalServiceUtil.fetchRepository(
			groupId, portletId);

		if (repository != null) {
			return repository.getRepositoryId();
		}

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		User user = UserLocalServiceUtil.getDefaultUser(group.getCompanyId());

		long classNameId = PortalUtil.getClassNameId(
			LiferayRepository.class.getName());
		UnicodeProperties typeSettingsProperties = new UnicodeProperties(true);

		return RepositoryLocalServiceUtil.addRepository(
			user.getUserId(), groupId, classNameId,
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, portletId,
			StringPool.BLANK, portletId, typeSettingsProperties, true,
			serviceContext);
	}

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

	public void movePortletFileEntryToTrash(
			long groupId, long userId, long folderId, String fileName)
		throws PortalException, SystemException {

		FileEntry fileEntry = DLAppLocalServiceUtil.getFileEntry(
			groupId, folderId, fileName);

		movePortletFileEntryToTrash(userId, fileEntry.getFileEntryId());
	}

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

	public void restorePortletFileEntryFromTrash(
			long groupId, long userId, long folderId, String fileName)
		throws PortalException, SystemException {

		FileEntry fileEntry = DLAppLocalServiceUtil.getFileEntry(
			groupId, folderId, fileName);

		restorePortletFileEntryFromTrash(userId, fileEntry.getFileEntryId());
	}

}