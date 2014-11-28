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
import com.liferay.portal.kernel.repository.capabilities.BulkOperationCapability;
import com.liferay.portal.kernel.repository.capabilities.ConfigurationCapability;
import com.liferay.portal.kernel.repository.capabilities.TemporaryFileEntriesCapability;
import com.liferay.portal.kernel.repository.capabilities.TemporaryFileEntriesScope;
import com.liferay.portal.kernel.repository.model.BaseRepositoryModelOperation;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author Iván Zaera
 */
public class TemporaryFileEntriesCapabilityImpl
	implements TemporaryFileEntriesCapability {

	public TemporaryFileEntriesCapabilityImpl(LocalRepository localRepository) {
		_localRepository = localRepository;
	}

	@Override
	public FileEntry addTemporaryFileEntry(
			TemporaryFileEntriesScope temporaryFileEntriesScope,
			String fileName, String mimeType, InputStream inputStream)
		throws PortalException {

		Folder folder = addTempFolder(temporaryFileEntriesScope);

		File file = null;

		try {
			file = FileUtil.createTempFile(inputStream);

			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setAddGroupPermissions(true);
			serviceContext.setAddGuestPermissions(true);

			return _localRepository.addFileEntry(
				temporaryFileEntriesScope.getUserId(), folder.getFolderId(),
				fileName, mimeType, fileName, StringPool.BLANK,
				StringPool.BLANK, file, serviceContext);
		}
		catch (IOException ioe) {
			throw new SystemException("Unable to write temporary file", ioe);
		}
		finally {
			FileUtil.delete(file);
		}
	}

	@Override
	public void deleteExpiredTemporaryFileEntries() throws PortalException {
		BulkOperationCapability bulkOperationCapability =
			_localRepository.getCapability(BulkOperationCapability.class);

		BulkOperationCapability.Filter<Date> bulkFilter =
			new BulkOperationCapability.Filter<>(
				BulkOperationCapability.Field.CreateDate.class,
				BulkOperationCapability.Operator.LT,
				new Date(
					System.currentTimeMillis() -
						getTemporaryFileEntriesTimeout()));

		bulkOperationCapability.execute(
			bulkFilter,
			new DeleteExpiredTemporaryFilesRepositoryModelOperation());
	}

	@Override
	public void deleteTemporaryFileEntry(
			TemporaryFileEntriesScope temporaryFileEntriesScope,
			String fileName)
		throws PortalException {

		try {
			FileEntry fileEntry = getTemporaryFileEntry(
				temporaryFileEntriesScope, fileName);

			_localRepository.deleteFileEntry(fileEntry.getFileEntryId());
		}
		catch (NoSuchModelException nsme) {
		}
	}

	@Override
	public List<FileEntry> getTemporaryFileEntries(
			TemporaryFileEntriesScope temporaryFileEntriesScope)
		throws PortalException {

		try {
			Folder folder = addTempFolder(temporaryFileEntriesScope);

			return _localRepository.getRepositoryFileEntries(
				folder.getFolderId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				null);
		}
		catch (NoSuchModelException nsme) {
			return Collections.emptyList();
		}
	}

	@Override
	public long getTemporaryFileEntriesTimeout() {
		ConfigurationCapability configurationCapability =
			_localRepository.getCapability(ConfigurationCapability.class);

		String temporaryFileEntriesTimeout =
			configurationCapability.getProperty(
				getClass(), _PROPERTY_TEMPORARY_FILE_ENTRIES_TIMEOUT);

		if (temporaryFileEntriesTimeout == null) {
			return _TEMPORARY_FILE_ENTRIES_TIMEOUT_DEFAULT;
		}

		return GetterUtil.getLong(temporaryFileEntriesTimeout);
	}

	@Override
	public FileEntry getTemporaryFileEntry(
			TemporaryFileEntriesScope temporaryFileEntriesScope,
			String fileName)
		throws PortalException {

		Folder folder = getTempFolder(temporaryFileEntriesScope);

		return _localRepository.getFileEntry(folder.getFolderId(), fileName);
	}

	@Override
	public void setTemporaryFileEntriesTimeout(
		long temporaryFileEntriesTimeout) {

		ConfigurationCapability configurationCapability =
			_localRepository.getCapability(ConfigurationCapability.class);

		configurationCapability.setProperty(
			getClass(), _PROPERTY_TEMPORARY_FILE_ENTRIES_TIMEOUT,
			String.valueOf(temporaryFileEntriesTimeout));
	}

	protected Folder addFolder(
			long userId, long parentFolderId, String folderName,
			ServiceContext serviceContext)
		throws PortalException {

		try {
			return _localRepository.getFolder(parentFolderId, folderName);
		}
		catch (NoSuchFolderException nsfe) {
			return _localRepository.addFolder(
				userId, parentFolderId, folderName, StringPool.BLANK,
				serviceContext);
		}
	}

	protected Folder addFolders(
			long userId, long folderId, String folderPath,
			ServiceContext serviceContext)
		throws PortalException {

		Folder folder = null;

		String[] folderNames = StringUtil.split(folderPath, StringPool.SLASH);

		for (String folderName : folderNames) {
			folder = addFolder(userId, folderId, folderName, serviceContext);

			folderId = folder.getFolderId();
		}

		return folder;
	}

	protected Folder addTempFolder(
			TemporaryFileEntriesScope temporaryFileEntriesScope)
		throws PortalException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		return addFolders(
			temporaryFileEntriesScope.getUserId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			_getFolderPath(temporaryFileEntriesScope), serviceContext);
	}

	protected Folder getDeepestFolder(long parentFolderId, String folderPath)
		throws PortalException {

		Folder folder = null;

		String[] folderNames = StringUtil.split(folderPath, StringPool.SLASH);

		for (String folderName : folderNames) {
			folder = _localRepository.getFolder(parentFolderId, folderName);

			parentFolderId = folder.getFolderId();
		}

		return folder;
	}

	protected Folder getTempFolder(
			TemporaryFileEntriesScope temporaryFileEntriesScope)
		throws PortalException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		return getDeepestFolder(
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			_getFolderPath(temporaryFileEntriesScope));
	}

	private String _getFolderPath(
		TemporaryFileEntriesScope temporaryFileEntriesScope) {

		StringBundler folderPath = new StringBundler(7);

		folderPath.append(_FOLDER_NAME_TEMP);
		folderPath.append(StringPool.SLASH);
		folderPath.append(temporaryFileEntriesScope.getCallerUuid());
		folderPath.append(StringPool.SLASH);
		folderPath.append(temporaryFileEntriesScope.getUserId());
		folderPath.append(StringPool.SLASH);
		folderPath.append(temporaryFileEntriesScope.getFolderPath());

		return folderPath.toString();
	}

	private static final String _FOLDER_NAME_TEMP = "temp";

	private static final String _PROPERTY_TEMPORARY_FILE_ENTRIES_TIMEOUT =
		"temporaryFilesTimeout";

	private static final long _TEMPORARY_FILE_ENTRIES_TIMEOUT_DEFAULT =
		12 * 60 * 60 * 1000;

	private final LocalRepository _localRepository;

	private class DeleteExpiredTemporaryFilesRepositoryModelOperation
		extends BaseRepositoryModelOperation {

		@Override
		public void execute(FileEntry fileEntry) throws PortalException {
			_localRepository.deleteFileEntry(fileEntry.getFileEntryId());
		}

	}

}