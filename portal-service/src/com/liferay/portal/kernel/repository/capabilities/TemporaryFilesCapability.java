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

package com.liferay.portal.kernel.repository.capabilities;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;

import java.io.InputStream;

import java.util.List;
import java.util.UUID;

/**
 * @author Iván Zaera
 */
public interface TemporaryFilesCapability extends Capability {

	public FileEntry addTemporaryFile(
			UUID invokerUuid, String folderPath, String fileName, long userId,
			String mimeType, InputStream inputStream)
		throws PortalException;

	public void deleteExpiredTemporaryFiles() throws PortalException;

	public void deleteTemporaryFile(
			UUID invokerUuid, String folderPath, String fileName)
		throws PortalException;

	public FileEntry getTemporaryFile(
			UUID invokerUuid, String folderPath, String fileName)
		throws PortalException;

	public List<FileEntry> getTemporaryFiles(
			UUID invokerUuid, String folderPath)
		throws PortalException;

	public long getTemporaryFilesTimeout();

	public void setTemporaryFilesTimeout(long temporaryFilesTimeout);

}