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

package com.liferay.portal.repository.liferayrepository;

import com.liferay.document.library.kernel.service.DLAppHelperLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.repository.capabilities.WorkflowSupport;
import com.liferay.portal.repository.util.LocalRepositoryWrapper;

import java.io.File;
import java.io.InputStream;

/**
 * @author Adolfo Pérez
 */
public class LiferayWorkflowLocalRepositoryWrapper
	extends LocalRepositoryWrapper {

	public LiferayWorkflowLocalRepositoryWrapper(
		LocalRepository localRepository, WorkflowSupport workflowSupport) {

		super(localRepository);

		_workflowSupport = workflowSupport;
	}

	@Override
	public FileEntry addFileEntry(
			long userId, long folderId, String sourceFileName, String mimeType,
			String title, String description, String changeLog, File file,
			ServiceContext serviceContext)
		throws PortalException {

		FileEntry fileEntry = super.addFileEntry(
			userId, folderId, sourceFileName, mimeType, title, description,
			changeLog, file, serviceContext);

		DLAppHelperLocalServiceUtil.updateAsset(
			userId, fileEntry, fileEntry.getFileVersion(),
			serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames(),
			serviceContext.getAssetLinkEntryIds());

		_workflowSupport.addFileEntry(userId, fileEntry, serviceContext);

		return fileEntry;
	}

	@Override
	public FileEntry addFileEntry(
			long userId, long folderId, String sourceFileName, String mimeType,
			String title, String description, String changeLog, InputStream is,
			long size, ServiceContext serviceContext)
		throws PortalException {

		FileEntry fileEntry = super.addFileEntry(
			userId, folderId, sourceFileName, mimeType, title, description,
			changeLog, is, size, serviceContext);

		DLAppHelperLocalServiceUtil.updateAsset(
			userId, fileEntry, fileEntry.getFileVersion(),
			serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames(),
			serviceContext.getAssetLinkEntryIds());

		_workflowSupport.addFileEntry(userId, fileEntry, serviceContext);

		return fileEntry;
	}

	@Override
	public void checkInFileEntry(
			long userId, long fileEntryId, boolean majorVersion,
			String changeLog, ServiceContext serviceContext)
		throws PortalException {

		super.checkInFileEntry(
			userId, fileEntryId, majorVersion, changeLog, serviceContext);

		FileEntry fileEntry = super.getFileEntry(fileEntryId);

		_workflowSupport.checkInFileEntry(
			userId, fileEntry, majorVersion, serviceContext);
	}

	@Override
	public void checkInFileEntry(
			long userId, long fileEntryId, String lockUuid,
			ServiceContext serviceContext)
		throws PortalException {

		super.checkInFileEntry(userId, fileEntryId, lockUuid, serviceContext);

		FileEntry fileEntry = super.getFileEntry(fileEntryId);

		_workflowSupport.checkInFileEntry(
			userId, fileEntry, false, serviceContext);
	}

	@Override
	public FileEntry copyFileEntry(
			long userId, long groupId, long fileEntryId, long destFolderId,
			ServiceContext serviceContext)
		throws PortalException {

		FileEntry fileEntry = super.copyFileEntry(
			userId, groupId, fileEntryId, destFolderId, serviceContext);

		DLAppHelperLocalServiceUtil.updateAsset(
			userId, fileEntry, fileEntry.getFileVersion(),
			serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames(),
			serviceContext.getAssetLinkEntryIds());

		_workflowSupport.addFileEntry(userId, fileEntry, serviceContext);

		return fileEntry;
	}

	@Override
	public void revertFileEntry(
			long userId, long fileEntryId, String version,
			ServiceContext serviceContext)
		throws PortalException {

		super.revertFileEntry(userId, fileEntryId, version, serviceContext);

		FileEntry fileEntry = super.getFileEntry(fileEntryId);

		_workflowSupport.revertFileEntry(userId, fileEntry, serviceContext);
	}

	@Override
	public FileEntry updateFileEntry(
			long userId, long fileEntryId, String sourceFileName,
			String mimeType, String title, String description, String changeLog,
			boolean majorVersion, File file, ServiceContext serviceContext)
		throws PortalException {

		FileEntry fileEntry = super.updateFileEntry(
			userId, fileEntryId, sourceFileName, mimeType, title, description,
			changeLog, majorVersion, file, serviceContext);

		_workflowSupport.updateFileEntry(
			userId, fileEntry, majorVersion, serviceContext);

		return super.getFileEntry(fileEntryId);
	}

	@Override
	public FileEntry updateFileEntry(
			long userId, long fileEntryId, String sourceFileName,
			String mimeType, String title, String description, String changeLog,
			boolean majorVersion, InputStream is, long size,
			ServiceContext serviceContext)
		throws PortalException {

		FileEntry fileEntry = super.updateFileEntry(
			userId, fileEntryId, sourceFileName, mimeType, title, description,
			changeLog, majorVersion, is, size, serviceContext);

		_workflowSupport.updateFileEntry(
			userId, fileEntry, majorVersion, serviceContext);

		return super.getFileEntry(fileEntryId);
	}

	private final WorkflowSupport _workflowSupport;

}