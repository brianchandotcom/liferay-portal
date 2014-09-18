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
import com.liferay.portal.kernel.repository.capabilities.TemporaryFilesCapability;
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
public class TempFileUtil {

	public static FileEntry addTempFile(
			long groupId, long userId, String fileName, String tempFolderName,
			File file, String mimeType)
		throws PortalException {

		InputStream inputStream = null;

		try {
			inputStream = new FileInputStream(file);

			return addTempFile(
				groupId, userId, fileName, tempFolderName, inputStream,
				mimeType);
		}
		catch (FileNotFoundException fnfe) {
			throw new PortalException(fnfe);
		}
		finally {
			StreamUtil.cleanUp(inputStream);
		}
	}

	public static FileEntry addTempFile(
			long groupId, long userId, String fileName, String tempFolderName,
			InputStream inputStream, String mimeType)
		throws PortalException {

		TemporaryFilesCapability temporaryFilesCapability =
			_getTemporaryFilesCapability(groupId);

		return temporaryFilesCapability.addTemporaryFile(
			_UUID, userId, _getFolderPath(userId, tempFolderName), fileName,
			mimeType, inputStream);
	}

	public static void deleteTempFile(long fileEntryId) throws PortalException {
		DLFileEntry dlFileEntry = DLFileEntryLocalServiceUtil.getDLFileEntry(
			fileEntryId);

		DLFolder dlFolder = dlFileEntry.getFolder();

		deleteTempFile(
			dlFileEntry.getGroupId(), dlFileEntry.getUserId(),
			dlFileEntry.getTitle(), dlFolder.getName());
	}

	public static void deleteTempFile(
			long groupId, long userId, String fileName, String tempFolderName)
		throws PortalException {

		TemporaryFilesCapability temporaryFilesCapability =
			_getTemporaryFilesCapability(groupId);

		temporaryFilesCapability.deleteTemporaryFile(
			_UUID, _getFolderPath(userId, tempFolderName), fileName);
	}

	public static FileEntry getTempFile(
			long groupId, long userId, String fileName, String tempFolderName)
		throws PortalException {

		TemporaryFilesCapability temporaryFilesCapability =
			_getTemporaryFilesCapability(groupId);

		return temporaryFilesCapability.getTemporaryFile(
			_UUID, _getFolderPath(userId, tempFolderName), fileName);
	}

	public static String[] getTempFileEntryNames(
			long groupId, long userId, String tempFolderName)
		throws PortalException {

		TemporaryFilesCapability temporaryFilesCapability =
			_getTemporaryFilesCapability(groupId);

		List<FileEntry> temporaryFiles =
			temporaryFilesCapability.getTemporaryFiles(
				_UUID, _getFolderPath(userId, tempFolderName));

		List<String> temporaryFileNames = new ArrayList<>();

		for (FileEntry fileEntry : temporaryFiles) {
			temporaryFileNames.add(fileEntry.getFileName());
		}

		return ArrayUtil.toStringArray(temporaryFileNames);
	}

	private static LocalRepository _addPortletRepository(
			long groupId, ServiceContext serviceContext)
		throws PortalException {

		Repository repository = RepositoryLocalServiceUtil.fetchRepository(
			groupId, TempFileUtil.class.getName(), StringPool.BLANK);

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
				TempFileUtil.class.getName(), StringPool.BLANK,
				StringPool.BLANK, typeSettingsProperties, true, serviceContext);

			return RepositoryLocalServiceUtil.getLocalRepositoryImpl(
				repository.getRepositoryId());
		}
		finally {
			DLAppHelperThreadLocal.setEnabled(dlAppHelperEnabled);
		}
	}

	private static String _getFolderPath(long userId, String tempFolderName) {
		return
			String.valueOf(userId) + StringPool.FORWARD_SLASH + tempFolderName;
	}

	private static TemporaryFilesCapability _getTemporaryFilesCapability(
			long groupId)
		throws PortalException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		LocalRepository localRepository = _addPortletRepository(
			groupId, serviceContext);

		return localRepository.getCapability(TemporaryFilesCapability.class);
	}

	private static final UUID _UUID = UUID.fromString(
		"00EF1134-B3EE-432A-BABD-367CEFA44DE1");

}