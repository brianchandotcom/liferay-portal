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

package com.liferay.document.library.internal.capabilities;

import com.liferay.document.library.kernel.exception.NoSuchFolderException;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.NoSuchModelException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.DocumentRepository;
import com.liferay.portal.kernel.repository.capabilities.BulkOperationCapability;
import com.liferay.portal.kernel.repository.capabilities.ConfigurationCapability;
import com.liferay.portal.kernel.repository.capabilities.TemporaryFileEntriesCapability;
import com.liferay.portal.kernel.repository.capabilities.TemporaryFileEntriesScope;
import com.liferay.portal.kernel.repository.model.BaseRepositoryModelOperation;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

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

	public TemporaryFileEntriesCapabilityImpl(
		DocumentRepository documentRepository) {

		_documentRepository = documentRepository;
	}

	@Override
	public FileEntry addTemporaryFileEntry(
			TemporaryFileEntriesScope temporaryFileEntriesScope,
			String fileName, String mimeType, InputStream inputStream)
		throws PortalException {

		Folder folder = addTempFolder(temporaryFileEntriesScope);

		File file = null;

		try {
			if (inputStream == null) {
				inputStream = new UnsyncByteArrayInputStream(new byte[0]);
			}

			file = FileUtil.createTempFile(inputStream);

			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setAddGroupPermissions(true);
			serviceContext.setAddGuestPermissions(true);

			return _documentRepository.addFileEntry(
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
			_documentRepository.getCapability(BulkOperationCapability.class);

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

			_documentRepository.deleteFileEntry(fileEntry.getFileEntryId());
		}
		catch (NoSuchModelException nsme) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(nsme, nsme);
			}
		}
	}

	@Override
	public List<FileEntry> getTemporaryFileEntries(
			TemporaryFileEntriesScope temporaryFileEntriesScope)
		throws PortalException {

		try {
			Folder folder = addTempFolder(temporaryFileEntriesScope);

			return _documentRepository.getRepositoryFileEntries(
				temporaryFileEntriesScope.getUserId(), folder.getFolderId(),
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
		}
		catch (NoSuchModelException nsme) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(nsme, nsme);
			}

			return Collections.emptyList();
		}
	}

	@Override
	public long getTemporaryFileEntriesTimeout() {
		ConfigurationCapability configurationCapability =
			_documentRepository.getCapability(ConfigurationCapability.class);

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

		return _documentRepository.getFileEntry(folder.getFolderId(), fileName);
	}

	@Override
	public void setTemporaryFileEntriesTimeout(
		long temporaryFileEntriesTimeout) {

		ConfigurationCapability configurationCapability =
			_documentRepository.getCapability(ConfigurationCapability.class);

		configurationCapability.setProperty(
			getClass(), _PROPERTY_TEMPORARY_FILE_ENTRIES_TIMEOUT,
			String.valueOf(temporaryFileEntriesTimeout));
	}

	protected Folder addFolder(
			long userId, long parentFolderId, String folderName,
			ServiceContext serviceContext)
		throws PortalException {

		try {
			return _documentRepository.getFolder(parentFolderId, folderName);
		}
		catch (NoSuchFolderException nsfe) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(nsfe, nsfe);
			}

			return _documentRepository.addFolder(
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
			folder = _documentRepository.getFolder(parentFolderId, folderName);

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

		StringBundler sb = new StringBundler(7);

		sb.append(_FOLDER_NAME_TEMP);
		sb.append(StringPool.SLASH);
		sb.append(temporaryFileEntriesScope.getCallerUuid());
		sb.append(StringPool.SLASH);
		sb.append(temporaryFileEntriesScope.getUserId());
		sb.append(StringPool.SLASH);
		sb.append(temporaryFileEntriesScope.getFolderPath());

		return sb.toString();
	}

	private static final String _FOLDER_NAME_TEMP = "temp";

	private static final String _PROPERTY_TEMPORARY_FILE_ENTRIES_TIMEOUT =
		"temporaryFilesTimeout";

	private static final long _TEMPORARY_FILE_ENTRIES_TIMEOUT_DEFAULT =
		12 * 60 * 60 * 1000;

	private static final Log _log = LogFactoryUtil.getLog(
		TemporaryFileEntriesCapabilityImpl.class);

	private final DocumentRepository _documentRepository;

	private class DeleteExpiredTemporaryFilesRepositoryModelOperation
		extends BaseRepositoryModelOperation {

		@Override
		public void execute(FileEntry fileEntry) throws PortalException {
			Folder folder = fileEntry.getFolder();

			_documentRepository.deleteFileEntry(fileEntry.getFileEntryId());

			Folder mountFolder = _documentRepository.getFolder(
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

			while ((folder.getFolderId() != mountFolder.getFolderId()) &&
				   (_documentRepository.getFileEntriesCount(
					   folder.getFolderId(),
					   WorkflowConstants.STATUS_ANY) == 0)) {

				long folderId = folder.getFolderId();

				folder = folder.getParentFolder();

				_documentRepository.deleteFolder(folderId);
			}
		}

	}

}