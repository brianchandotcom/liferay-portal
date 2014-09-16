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

package com.liferay.portal.repository.capabilities;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.capabilities.ConfigurationCapability;
import com.liferay.portal.kernel.repository.capabilities.TemporaryFilesCapability;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * @author Iván Zaera
 */
public class TemporaryFilesCapabilityImpl implements TemporaryFilesCapability {

	public static final long DEFAULT_TEMPORARY_FILES_TIMEOUT =
		12 * 60 * 60 * 1000;

	public static final String FOLDER_NAME_TMP = "tmp";

	public static final String PROPERTY_TEMPORARY_FILES_TIMEOUT =
		"temporaryFilesTimeout";

	public TemporaryFilesCapabilityImpl(LocalRepository localRepository) {
		_localRepository = localRepository;
	}

	@Override
	public FileEntry addTemporaryFile(
			UUID invokerUuid, String folderPath, String fileName, long userId,
			String mimeType, InputStream inputStream)
		throws PortalException {

		Folder folder = addTempFolder(
			userId, invokerUuid, folderPath, fileName);

		File file = null;

		try {
			file = FileUtil.createTempFile(inputStream);

			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setAddGroupPermissions(true);
			serviceContext.setAddGuestPermissions(true);

			return _localRepository.addFileEntry(
				userId, folder.getFolderId(), fileName, mimeType, fileName, "",
				"", file, serviceContext);
		}
		catch (IOException ioe) {
			throw new SystemException("Unable to write temporary file", ioe);
		}
		finally {
			FileUtil.delete(file);
		}
	}

	@Override
	public void deleteExpiredTemporaryFiles() throws PortalException {
	}

	@Override
	public void deleteTemporaryFile(
			UUID invokerUuid, String folderPath, String fileName)
		throws PortalException {

		try {
			FileEntry fileEntry = getTemporaryFile(
				invokerUuid, folderPath, fileName);

			_localRepository.deleteFileEntry(fileEntry.getFileEntryId());
		}
		catch (NoSuchModelException nsme) {

			// ignore

		}
	}

	@Override
	public FileEntry getTemporaryFile(
			UUID invokerUuid, String folderPath, String fileName)
		throws PortalException {

		Folder folder = getTempFolder(invokerUuid, folderPath);

		return _localRepository.getFileEntry(folder.getFolderId(), fileName);
	}

	@Override
	public List<FileEntry> getTemporaryFiles(
			UUID invokerUuid, String folderPath)
		throws PortalException {

		try {
			Folder folder = getTempFolder(invokerUuid, folderPath);

			return _localRepository.getRepositoryFileEntries(
				folder.getFolderId(), 0, QueryUtil.ALL_POS, null);
		}
		catch (NoSuchModelException nsme) {
			return Collections.emptyList();
		}
	}

	@Override
	public long getTemporaryFilesTimeout() {
		ConfigurationCapability configurationCapability =
			_localRepository.getCapability(ConfigurationCapability.class);

		String minimumLifespanMilliseconds =
			configurationCapability.getProperty(
				getClass(), PROPERTY_TEMPORARY_FILES_TIMEOUT);

		if (minimumLifespanMilliseconds == null) {
			return DEFAULT_TEMPORARY_FILES_TIMEOUT;
		}

		return Long.valueOf(minimumLifespanMilliseconds);
	}

	@Override
	public void setTemporaryFilesTimeout(long temporaryFilesTimeout) {
		ConfigurationCapability configurationCapability =
			_localRepository.getCapability(ConfigurationCapability.class);

		configurationCapability.setProperty(
			getClass(), PROPERTY_TEMPORARY_FILES_TIMEOUT,
			String.valueOf(temporaryFilesTimeout));
	}

	protected Folder addTempFolder(
			long userId, UUID invokerUuid, String folderPath, String fileName)
		throws PortalException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		Folder tmpFolder = _addFolder(
			userId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, FOLDER_NAME_TMP,
			serviceContext);

		Folder callerFolder = _addFolder(
			userId, tmpFolder.getFolderId(), invokerUuid.toString(),
			serviceContext);

		return _addFolders(
			userId, callerFolder.getFolderId(), folderPath, serviceContext);
	}

	protected Folder getTempFolder(UUID invokerUuid, String folderPath)
		throws PortalException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		Folder tmpFolder = _getFolder(
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, FOLDER_NAME_TMP);

		Folder callerFolder = _getFolder(
			tmpFolder.getFolderId(), invokerUuid.toString());

		return _getFolders(callerFolder.getFolderId(), folderPath);
	}

	private Folder _addFolder(
			long userId, long parentFolderId, String folderName,
			ServiceContext serviceContext)
		throws PortalException {

		try {
			return _getFolder(parentFolderId, folderName);
		}
		catch (NoSuchFolderException nsfe) {
			return _localRepository.addFolder(
				userId, parentFolderId, folderName, "", serviceContext);
		}
	}

	private Folder _addFolders(
			long userId, long folderId, String folderPath,
			ServiceContext serviceContext)
		throws PortalException {

		String[] folderNames = folderPath.split(_PATTERN_SLASH);

		Folder folder = null;

		for (String folderName : folderNames) {
			folder = _addFolder(userId, folderId, folderName, serviceContext);

			folderId = folder.getFolderId();
		}

		return folder;
	}

	private Folder _getFolder(long parentFolderId, String folderName)
		throws PortalException {

		return _localRepository.getFolder(parentFolderId, folderName);
	}

	private Folder _getFolders(long parentFolderId, String folderPath)
		throws PortalException {

		String[] folderNames = folderPath.split(_PATTERN_SLASH);

		Folder folder = null;

		for (String folderName : folderNames) {
			folder = _localRepository.getFolder(parentFolderId, folderName);

			parentFolderId = folder.getFolderId();
		}

		return folder;
	}

	private static final String _PATTERN_SLASH = Pattern.quote("/");

	private LocalRepository _localRepository;

}