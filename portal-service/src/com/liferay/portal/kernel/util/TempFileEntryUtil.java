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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.capabilities.TemporaryFileEntriesCapability;
import com.liferay.portal.kernel.repository.capabilities.TemporaryFileEntriesScope;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Repository;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.RepositoryLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.util.DLAppHelperThreadLocal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Sergio González
 * @author Matthew Kong
 * @author Alexander Chow
 * @author Iván Zaera
 */
public class TempFileEntryUtil {

	public static FileEntry addTempFileEntry(
			long groupId, long userId, String folderName, String fileName,
			File file, String mimeType)
		throws PortalException {

		InputStream inputStream = null;

		try {
			inputStream = new FileInputStream(file);

			return addTempFileEntry(
				groupId, userId, folderName, fileName, inputStream, mimeType);
		}
		catch (FileNotFoundException fnfe) {
			throw new PortalException(fnfe);
		}
		finally {
			StreamUtil.cleanUp(inputStream);
		}
	}

	public static FileEntry addTempFileEntry(
			long groupId, long userId, String folderName, String fileName,
			InputStream inputStream, String mimeType)
		throws PortalException {

		TemporaryFileEntriesCapability temporaryFileEntriesCapability =
			_getTemporaryFileEntriesCapability(groupId);

		return temporaryFileEntriesCapability.addTemporaryFileEntry(
			new TemporaryFileEntriesScope(_UUID, userId, folderName), fileName,
			mimeType, inputStream);
	}

	public static void deleteTempFileEntry(long fileEntryId)
		throws PortalException {

		DLFileEntry dlFileEntry = DLFileEntryLocalServiceUtil.getDLFileEntry(
			fileEntryId);

		DLFolder dlFolder = dlFileEntry.getFolder();

		deleteTempFileEntry(
			dlFileEntry.getGroupId(), dlFileEntry.getUserId(),
			dlFolder.getName(), dlFileEntry.getTitle());
	}

	public static void deleteTempFileEntry(
			long groupId, long userId, String folderName, String fileName)
		throws PortalException {

		TemporaryFileEntriesCapability temporaryFileEntriesCapability =
			_getTemporaryFileEntriesCapability(groupId);

		temporaryFileEntriesCapability.deleteTemporaryFileEntry(
			new TemporaryFileEntriesScope(_UUID, userId, folderName), fileName);
	}

	public static FileEntry getTempFileEntry(
			long groupId, long userId, String folderName, String fileName)
		throws PortalException {

		TemporaryFileEntriesCapability temporaryFileEntriesCapability =
			_getTemporaryFileEntriesCapability(groupId);

		return temporaryFileEntriesCapability.getTemporaryFileEntry(
			new TemporaryFileEntriesScope(_UUID, userId, folderName), fileName);
	}

	public static String[] getTempFileNames(
			long groupId, long userId, String folderName)
		throws PortalException {

		TemporaryFileEntriesCapability temporaryFileEntriesCapability =
			_getTemporaryFileEntriesCapability(groupId);

		List<FileEntry> fileEntries =
			temporaryFileEntriesCapability.getTemporaryFileEntries(
				new TemporaryFileEntriesScope(_UUID, userId, folderName));

		List<String> fileNames = new ArrayList<>();

		for (FileEntry fileEntry : fileEntries) {
			fileNames.add(fileEntry.getFileName());
		}

		return ArrayUtil.toStringArray(fileNames);
	}

	private static LocalRepository _addPortletRepository(
			long groupId, ServiceContext serviceContext)
		throws PortalException {

		Repository repository = RepositoryLocalServiceUtil.fetchRepository(
			groupId, TempFileEntryUtil.class.getName(), StringPool.BLANK);

		if (repository != null) {
			return RepositoryLocalServiceUtil.getLocalRepositoryImpl(
				repository.getRepositoryId());
		}

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		User user = UserLocalServiceUtil.getDefaultUser(group.getCompanyId());

		long classNameId = PortalUtil.getClassNameId(
			"com.liferay.portal.repository.temporaryrepository." +
				"TemporaryRepository");

		UnicodeProperties typeSettingsProperties = new UnicodeProperties();

		boolean dlAppHelperEnabled = DLAppHelperThreadLocal.isEnabled();

		try {
			DLAppHelperThreadLocal.setEnabled(false);

			repository = RepositoryLocalServiceUtil.addRepository(
				user.getUserId(), groupId, classNameId,
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				TempFileEntryUtil.class.getName(), StringPool.BLANK,
				StringPool.BLANK, typeSettingsProperties, true, serviceContext);

			return RepositoryLocalServiceUtil.getLocalRepositoryImpl(
				repository.getRepositoryId());
		}
		finally {
			DLAppHelperThreadLocal.setEnabled(dlAppHelperEnabled);
		}
	}

	private static TemporaryFileEntriesCapability
			_getTemporaryFileEntriesCapability(long groupId)
		throws PortalException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		LocalRepository localRepository = _addPortletRepository(
			groupId, serviceContext);

		return localRepository.getCapability(
			TemporaryFileEntriesCapability.class);
	}

	private static final UUID _UUID = UUID.fromString(
		"00EF1134-B3EE-432A-BABD-367CEFA44DE1");

}