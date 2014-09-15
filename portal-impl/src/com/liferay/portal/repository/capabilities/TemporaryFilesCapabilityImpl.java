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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.capabilities.TemporaryFilesCapability;
import com.liferay.portal.kernel.repository.model.FileEntry;

import java.io.InputStream;

import java.util.List;
import java.util.UUID;

/**
 * @author Iván Zaera
 */
public class TemporaryFilesCapabilityImpl implements TemporaryFilesCapability {

	public TemporaryFilesCapabilityImpl(LocalRepository localRepository) {
		_localRepository = localRepository;
	}

	@Override
	public FileEntry addTemporaryFile(
			long userId, UUID callerUuid, String folderPath, String fileName,
			String mimeType, InputStream inputStream)
		throws PortalException {

		return null;
	}

	@Override
	public void deleteExpiredTemporaryFiles() throws PortalException {
	}

	@Override
	public void deleteTemporaryFile(
			long userId, UUID callerUuid, String folderPath, String fileName)
		throws PortalException {
	}

	@Override
	public FileEntry getTemporaryFile(
			long userId, UUID callerUuid, String folderPath, String fileName)
		throws PortalException {

		return null;
	}

	@Override
	public List<FileEntry> getTemporaryFiles(
			long userId, UUID callerUuid, String folderPath)
		throws PortalException {

		return null;
	}

	@Override
	public long getTemporaryFilesTimeout() {
		return 0;
	}

	@Override
	public void setTemporaryFilesTimeout(long temporaryFilesTimeout) {
	}

	private LocalRepository _localRepository;

}