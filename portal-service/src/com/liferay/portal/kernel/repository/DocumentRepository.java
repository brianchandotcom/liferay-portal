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

package com.liferay.portal.kernel.repository;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.capabilities.CapabilityProvider;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.service.ServiceContext;

import java.io.File;
import java.io.InputStream;

/**
 * @author Iván Zaera
 */
public interface DocumentRepository extends CapabilityProvider {

	public FileEntry addFileEntry(
			long userId, long folderId, String sourceFileName, String mimeType,
			String title, String description, String changeLog, File file,
			ServiceContext serviceContext)
		throws PortalException;

	public FileEntry addFileEntry(
			long userId, long folderId, String sourceFileName, String mimeType,
			String title, String description, String changeLog, InputStream is,
			long size, ServiceContext serviceContext)
		throws PortalException;

	public FileEntry copyFileEntry(
			long userId, long groupId, long fileEntryId, long destFolderId,
			ServiceContext serviceContext)
		throws PortalException;

	public long getRepositoryId();

	public FileEntry updateFileEntry(
			long userId, long fileEntryId, String sourceFileName,
			String mimeType, String title, String description, String changeLog,
			boolean majorVersion, File file, ServiceContext serviceContext)
		throws PortalException;

	public FileEntry updateFileEntry(
			long userId, long fileEntryId, String sourceFileName,
			String mimeType, String title, String description, String changeLog,
			boolean majorVersion, InputStream is, long size,
			ServiceContext serviceContext)
		throws PortalException;

}