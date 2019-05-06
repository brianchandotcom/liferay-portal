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

import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.DocumentRepository;
import com.liferay.portal.kernel.repository.capabilities.Capability;
import com.liferay.portal.kernel.repository.capabilities.CapabilityProvider;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.File;
import java.io.InputStream;

/**
 * @author Adolfo Pérez
 */
public abstract class BaseCapabilityRepository<R>
	implements DocumentRepository {

	public BaseCapabilityRepository(
		R repository, CapabilityProvider capabilityProvider) {

		_repository = repository;
		_capabilityProvider = capabilityProvider;
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #checkInFileEntry(long, long, DLVersionNumberIncrease,
	 *             String, ServiceContext)}
	 */
	@Deprecated
	@Override
	public void checkInFileEntry(
			long userId, long fileEntryId, boolean majorVersion,
			String changeLog, ServiceContext serviceContext)
		throws PortalException {

		checkInFileEntry(
			userId, fileEntryId,
			DLVersionNumberIncrease.fromMajorVersion(majorVersion), changeLog,
			serviceContext);
	}

	@Override
	public <T extends Capability> T getCapability(Class<T> capabilityClass) {
		return _capabilityProvider.getCapability(capabilityClass);
	}

	@Override
	public abstract long getRepositoryId();

	@Override
	public <T extends Capability> boolean isCapabilityProvided(
		Class<T> capabilityClass) {

		return _capabilityProvider.isCapabilityProvided(capabilityClass);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #updateFileEntry(long, long, String, String, String, String,
	 *             String, DLVersionNumberIncrease, File, ServiceContext)}
	 */
	@Deprecated
	@Override
	public FileEntry updateFileEntry(
			long userId, long fileEntryId, String sourceFileName,
			String mimeType, String title, String description, String changeLog,
			boolean majorVersion, File file, ServiceContext serviceContext)
		throws PortalException {

		return updateFileEntry(
			userId, fileEntryId, sourceFileName, mimeType, title, description,
			changeLog, DLVersionNumberIncrease.fromMajorVersion(majorVersion),
			file, serviceContext);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #updateFileEntry(long, long, String, String, String, String,
	 *             String, DLVersionNumberIncrease, InputStream, long,
	 *             ServiceContext)}
	 */
	@Deprecated
	@Override
	public FileEntry updateFileEntry(
			long userId, long fileEntryId, String sourceFileName,
			String mimeType, String title, String description, String changeLog,
			boolean majorVersion, InputStream is, long size,
			ServiceContext serviceContext)
		throws PortalException {

		return updateFileEntry(
			userId, fileEntryId, sourceFileName, mimeType, title, description,
			changeLog, DLVersionNumberIncrease.fromMajorVersion(majorVersion),
			is, size, serviceContext);
	}

	protected R getRepository() {
		return _repository;
	}

	private final CapabilityProvider _capabilityProvider;
	private final R _repository;

}