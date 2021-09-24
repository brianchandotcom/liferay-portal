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

package com.liferay.document.library.kernel.store;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.transaction.Transactional;

import java.io.File;
import java.io.InputStream;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 * @author Edward Han
 * @author Raymond Augé
 */
@ProviderType
@Transactional(rollbackFor = {PortalException.class, SystemException.class})
public interface DLStore {

	public default void addFile(DLStoreRequest dlStoreRequest, byte[] bytes)
		throws PortalException {

		addFile(
			dlStoreRequest.getCompanyId(), dlStoreRequest.getRepositoryId(),
			dlStoreRequest.getFileName(),
			dlStoreRequest.isValidateFileExtension(), bytes);
	}

	public default void addFile(DLStoreRequest dlStoreRequest, File file)
		throws PortalException {

		addFile(
			dlStoreRequest.getCompanyId(), dlStoreRequest.getRepositoryId(),
			dlStoreRequest.getFileName(),
			dlStoreRequest.isValidateFileExtension(), file);
	}

	public default void addFile(
			DLStoreRequest dlStoreRequest, InputStream inputStream)
		throws PortalException {

		addFile(
			dlStoreRequest.getCompanyId(), dlStoreRequest.getRepositoryId(),
			dlStoreRequest.getFileName(),
			dlStoreRequest.isValidateFileExtension(), inputStream);
	}

	public void addFile(
			long companyId, long repositoryId, String fileName,
			boolean validateFileExtension, byte[] bytes)
		throws PortalException;

	public void addFile(
			long companyId, long repositoryId, String fileName,
			boolean validateFileExtension, File file)
		throws PortalException;

	public void addFile(
			long companyId, long repositoryId, String fileName,
			boolean validateFileExtension, InputStream inputStream)
		throws PortalException;

	public void addFile(
			long companyId, long repositoryId, String fileName, byte[] bytes)
		throws PortalException;

	public void addFile(
			long companyId, long repositoryId, String fileName, File file)
		throws PortalException;

	public void addFile(
			long companyId, long repositoryId, String fileName,
			InputStream inputStream)
		throws PortalException;

	public void copyFileVersion(
			long companyId, long repositoryId, String fileName,
			String fromVersionLabel, String toVersionLabel)
		throws PortalException;

	public void deleteDirectory(
		long companyId, long repositoryId, String dirName);

	public void deleteFile(long companyId, long repositoryId, String fileName)
		throws PortalException;

	public void deleteFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException;

	public byte[] getFileAsBytes(
			long companyId, long repositoryId, String fileName)
		throws PortalException;

	public byte[] getFileAsBytes(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException;

	public InputStream getFileAsStream(
			long companyId, long repositoryId, String fileName)
		throws PortalException;

	public InputStream getFileAsStream(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException;

	public String[] getFileNames(
			long companyId, long repositoryId, String dirName)
		throws PortalException;

	public long getFileSize(long companyId, long repositoryId, String fileName)
		throws PortalException;

	public boolean hasFile(long companyId, long repositoryId, String fileName)
		throws PortalException;

	public boolean hasFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException;

	public default void updateFile(DLStoreRequest dlStoreRequest, File file)
		throws PortalException {

		updateFile(
			dlStoreRequest.getCompanyId(), dlStoreRequest.getRepositoryId(),
			dlStoreRequest.getFileName(), dlStoreRequest.getFileExtension(),
			dlStoreRequest.isValidateFileExtension(),
			dlStoreRequest.getVersionLabel(),
			dlStoreRequest.getSourceFileName(), file);
	}

	public default void updateFile(
			DLStoreRequest dlStoreRequest, InputStream inputStream)
		throws PortalException {

		updateFile(
			dlStoreRequest.getCompanyId(), dlStoreRequest.getRepositoryId(),
			dlStoreRequest.getFileName(), dlStoreRequest.getFileExtension(),
			dlStoreRequest.isValidateFileExtension(),
			dlStoreRequest.getVersionLabel(),
			dlStoreRequest.getSourceFileName(), inputStream);
	}

	public void updateFile(
			long companyId, long repositoryId, long newRepositoryId,
			String fileName)
		throws PortalException;

	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String fileExtension, boolean validateFileExtension,
			String versionLabel, String sourceFileName, File file)
		throws PortalException;

	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String fileExtension, boolean validateFileExtension,
			String versionLabel, String sourceFileName, InputStream inputStream)
		throws PortalException;

	public void updateFileVersion(
			long companyId, long repositoryId, String fileName,
			String fromVersionLabel, String toVersionLabel)
		throws PortalException;

	public void validate(String fileName, boolean validateFileExtension)
		throws PortalException;

	public void validate(
			String fileName, boolean validateFileExtension, byte[] bytes)
		throws PortalException;

	public void validate(
			String fileName, boolean validateFileExtension, File file)
		throws PortalException;

	public void validate(
			String fileName, boolean validateFileExtension,
			InputStream inputStream)
		throws PortalException;

	public void validate(
			String fileName, String fileExtension, String sourceFileName,
			boolean validateFileExtension)
		throws PortalException;

	public void validate(
			String fileName, String fileExtension, String sourceFileName,
			boolean validateFileExtension, File file)
		throws PortalException;

	public void validate(
			String fileName, String fileExtension, String sourceFileName,
			boolean validateFileExtension, InputStream inputStream)
		throws PortalException;

}