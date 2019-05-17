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

import java.io.File;
import java.io.InputStream;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The interface for all file store implementations. Most, if not all
 * implementations should extend from the class {@link BaseStore}.
 *
 * @author Brian Wing Shun Chan
 * @author Edward Han
 * @see    BaseStore
 */
@ProviderType
public interface Store {

	public static final String VERSION_DEFAULT = "1.0";

	public void addDirectory(long companyId, long repositoryId, String dirName);

	public void addFile(
			long companyId, long repositoryId, String fileName, byte[] bytes)
		throws PortalException;

	public void addFile(
			long companyId, long repositoryId, String fileName, File file)
		throws PortalException;

	public void addFile(
			long companyId, long repositoryId, String fileName, InputStream is)
		throws PortalException;

	/**
	 * @deprecated As of Mueller (7.2.x), with no direct replacement
	 */
	@Deprecated
	public void checkRoot(long companyId);

	public void copyFileToStore(
			long companyId, long repositoryId, String fileName,
			String versionLabel, Store targetStore)
		throws PortalException;

	public void copyFileVersion(
			long companyId, long repositoryId, String fileName,
			String fromVersionLabel, String toVersionLabel)
		throws PortalException;

	public void deleteDirectory(
		long companyId, long repositoryId, String dirName);

	public void deleteFile(long companyId, long repositoryId, String fileName);

	public void deleteFile(
		long companyId, long repositoryId, String fileName,
		String versionLabel);

	public File getFile(long companyId, long repositoryId, String fileName)
		throws PortalException;

	public File getFile(
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

	public String[] getFileNames(long companyId, long repositoryId);

	public String[] getFileNames(
		long companyId, long repositoryId, String dirName);

	public long getFileSize(long companyId, long repositoryId, String fileName)
		throws PortalException;

	public boolean hasDirectory(
		long companyId, long repositoryId, String dirName);

	public boolean hasFile(long companyId, long repositoryId, String fileName);

	public boolean hasFile(
		long companyId, long repositoryId, String fileName,
		String versionLabel);

	/**
	 * @deprecated As of Mueller (7.2.x), with no direct replacement
	 */
	@Deprecated
	public void move(String srcDir, String destDir);

	public void moveFileToStore(
			long companyId, long repositoryId, String fileName,
			String versionLabel, Store targetStore)
		throws PortalException;

	public void updateFile(
			long companyId, long repositoryId, long newRepositoryId,
			String fileName)
		throws PortalException;

	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String newFileName)
		throws PortalException;

	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel, byte[] bytes)
		throws PortalException;

	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel, File file)
		throws PortalException;

	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel, InputStream is)
		throws PortalException;

	public void updateFileVersion(
			long companyId, long repositoryId, String fileName,
			String fromVersionLabel, String toVersionLabel)
		throws PortalException;

}