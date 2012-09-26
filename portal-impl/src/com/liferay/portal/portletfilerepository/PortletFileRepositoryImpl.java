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
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
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
			long groupId, long userId, long folderId,
			List<ObjectValuePair<String, InputStream>> inputStreamOVPs,
			String portletId)
		throws PortalException, SystemException {

		for (int i = 0; i < inputStreamOVPs.size(); i++) {
			ObjectValuePair<String, InputStream> inputStreamOVP =
				inputStreamOVPs.get(i);

			addPortletFileEntry(
				groupId, userId, folderId, portletId, inputStreamOVP.getValue(),
				inputStreamOVP.getKey());
		}
	}

	public void addPortletFileEntry(
			long groupId, long userId, long folderId, String portletId,
			File file, String fileName)
		throws PortalException, SystemException {

		if (Validator.isNull(fileName)) {
			return;
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		long repositoryId = getPortletRepository(
			groupId, portletId, serviceContext);

		String contentType = MimeTypesUtil.getContentType(file);

		try {
			DLAppHelperThreadLocal.setEnabled(false);

			DLAppLocalServiceUtil.addFileEntry(
				userId, repositoryId, folderId, fileName, contentType, fileName,
				StringPool.BLANK, StringPool.BLANK, file, serviceContext);
		}
		finally {
			DLAppHelperThreadLocal.setEnabled(true);
		}

	}

	public void addPortletFileEntry(
			long groupId, long userId, long folderId, String portletId,
			InputStream inputStream, String fileName)
		throws PortalException, SystemException {

		if (inputStream == null) {
			return;
		}

		long size = 0;

		try {
			byte[] bytes = FileUtil.getBytes(inputStream, -1, false);

			size = bytes.length;
		}
		catch (IOException ioe) {
			return;
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		long repositoryId = getPortletRepository(
			groupId, portletId, serviceContext);

		String contentType = MimeTypesUtil.getContentType(
			inputStream, fileName);

		try {
			DLAppHelperThreadLocal.setEnabled(false);

			DLAppLocalServiceUtil.addFileEntry(
				userId, repositoryId, folderId, fileName, contentType, fileName,
				StringPool.BLANK, StringPool.BLANK, inputStream, size,
				serviceContext);
		}
		finally {
			DLAppHelperThreadLocal.setEnabled(true);
		}
	}

	public void deletePortletFileEntries(long groupId, long folderId)
		throws PortalException, SystemException {

		List<DLFileEntry> dlFileEntries =
			DLFileEntryLocalServiceUtil.getFileEntries(
				groupId, folderId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		for (DLFileEntry dlFileEntry : dlFileEntries) {
			deletePortletFileEntry(dlFileEntry.getFileEntryId());
		}
	}

	public void deletePortletFileEntry(long fileEntryId)
		throws PortalException, SystemException {

		try {
			DLAppHelperThreadLocal.setEnabled(false);

			DLAppLocalServiceUtil.deleteFileEntry(fileEntryId);
		}
		finally {
			DLAppHelperThreadLocal.setEnabled(true);
		}
	}

	public void deletePortletFileEntry(
			long groupId, long folderId, String fileName)
		throws PortalException, SystemException {

		FileEntry fileEntry = DLAppLocalServiceUtil.getFileEntry(
			groupId, folderId, fileName);

		deletePortletFileEntry(fileEntry.getFileEntryId());
	}

	public long getFolder(
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

		return folder.getFolderId();
	}

	public long getPortletRepository(
			long groupId, String portletId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		Repository repository = RepositoryLocalServiceUtil.getRepository(
			groupId, portletId);

		if (repository != null) {
			return repository.getRepositoryId();
		}

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		User defaultUser = UserLocalServiceUtil.getDefaultUser(
			group.getCompanyId());

		long classNameId = PortalUtil.getClassNameId(
			LiferayRepository.class.getName());

		UnicodeProperties typeSettingsProperties = new UnicodeProperties(true);

		return RepositoryLocalServiceUtil.addRepository(
			defaultUser.getUserId(), groupId, classNameId,
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, PortletKeys.WIKI,
			StringPool.BLANK, PortletKeys.WIKI, typeSettingsProperties,
			DLFolderConstants.PORTLET_REPOSITORY, serviceContext);
	}

	public void movePortletFileEntryFromTrash(long userId, long fileEntryId)
		throws PortalException, SystemException {

		try {
			DLAppHelperThreadLocal.setEnabled(false);

			DLAppLocalServiceUtil.restoreFileEntryFromTrash(
				userId, fileEntryId);
		}
		finally {
			DLAppHelperThreadLocal.setEnabled(true);
		}
	}

	public void movePortletFileEntryFromTrash(
			long groupId, long userId, long folderId, String fileName)
		throws PortalException, SystemException {

		FileEntry fileEntry = DLAppLocalServiceUtil.getFileEntry(
			groupId, folderId, fileName);

		movePortletFileEntryFromTrash(userId, fileEntry.getFileEntryId());
	}

	public void movePortletFileEntryToTrash(long userId, long fileEntryId)
		throws PortalException, SystemException {

		try {
			DLAppHelperThreadLocal.setEnabled(false);

			DLAppLocalServiceUtil.moveFileEntryToTrash(userId, fileEntryId);
		}
		finally {
			DLAppHelperThreadLocal.setEnabled(true);
		}
	}

	public void movePortletFileEntryToTrash(
			long groupId, long userId, long folderId, String fileName)
		throws PortalException, SystemException {

		FileEntry fileEntry = DLAppLocalServiceUtil.getFileEntry(
			groupId, folderId, fileName);

		movePortletFileEntryToTrash(userId, fileEntry.getFileEntryId());
	}

}