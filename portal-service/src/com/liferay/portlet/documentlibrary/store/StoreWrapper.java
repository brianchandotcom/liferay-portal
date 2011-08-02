/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.store;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;

import java.io.File;
import java.io.InputStream;

/**
 * @author Brian Wing Shun Chan
 */
public class StoreWrapper implements Store {

	public StoreWrapper(Store store) {
		_store = store;
	}

	public void addDirectory(long companyId, long repositoryId, String dirName)
		throws PortalException, SystemException {

		_store.addDirectory(companyId, repositoryId, dirName);
	}

	public void addFile(
			long companyId, long repositoryId, String fileName, byte[] bytes)
		throws PortalException, SystemException {

		_store.addFile(
			companyId, repositoryId, fileName, bytes);
	}

	public void addFile(
			long companyId, long repositoryId, String fileName, File file)
		throws PortalException, SystemException {

		_store.addFile(companyId, repositoryId, fileName, file);
	}

	public void addFile(
			long companyId, long repositoryId, String fileName, InputStream is)
		throws PortalException, SystemException {

		_store.addFile(companyId, repositoryId, fileName, is);
	}

	public void checkRoot(long companyId) throws SystemException {
		_store.checkRoot(companyId);
	}

	public void deleteDirectory(
			long companyId, long repositoryId, String dirName)
		throws PortalException, SystemException {

		_store.deleteDirectory(companyId, repositoryId, dirName);
	}

	public void copyFileVersion(
			long companyId, long repositoryId, String fileName,
			String fromVersionNumber, String toVersionNumber,
			String sourceFileName)
		throws PortalException, SystemException {

		_store.copyFileVersion(
			companyId, repositoryId, fileName, fromVersionNumber,
			toVersionNumber, sourceFileName);
	}

	public void deleteFile(long companyId, long repositoryId, String fileName)
		throws PortalException, SystemException {

		_store.deleteFile(companyId, repositoryId, fileName);
	}

	public void deleteFile(
			long companyId, long repositoryId, String fileName,
			String versionNumber)
		throws PortalException, SystemException {

		_store.deleteFile(companyId, repositoryId, fileName, versionNumber);
	}

	public byte[] getFile(long companyId, long repositoryId, String fileName)
		throws PortalException, SystemException {

		return _store.getFile(companyId, repositoryId, fileName);
	}

	public byte[] getFile(
			long companyId, long repositoryId, String fileName,
			String versionNumber)
		throws PortalException, SystemException {

		return _store.getFile(companyId, repositoryId, fileName, versionNumber);
	}

	public InputStream getFileAsStream(
			long companyId, long repositoryId, String fileName)
		throws PortalException, SystemException {

		return _store.getFileAsStream(companyId, repositoryId, fileName);
	}

	public InputStream getFileAsStream(
			long companyId, long repositoryId, String fileName,
			String versionNumber)
		throws PortalException, SystemException {

		return _store.getFileAsStream(
			companyId, repositoryId, fileName, versionNumber);
	}

	public String[] getFileNames(long companyId, long repositoryId)
		throws SystemException {

		return _store.getFileNames(companyId, repositoryId);
	}

	public String[] getFileNames(
			long companyId, long repositoryId, String dirName)
		throws PortalException, SystemException {

		return _store.getFileNames(companyId, repositoryId, dirName);
	}

	public long getFileSize(
			long companyId, long repositoryId, String fileName)
		throws PortalException, SystemException {

		return _store.getFileSize(companyId, repositoryId, fileName);
	}

	public boolean hasFile(
			long companyId, long repositoryId, String fileName,
			String versionNumber)
		throws PortalException, SystemException {

		return _store.hasFile(companyId, repositoryId, fileName, versionNumber);
	}

	public void move(String srcDir, String destDir) throws SystemException {
		_store.move(srcDir, destDir);
	}

	public void updateFile(
			long companyId, long repositoryId, long newRepositoryId,
			String fileName)
		throws PortalException, SystemException {

		_store.updateFile(companyId, repositoryId, newRepositoryId, fileName);
	}

	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String newFileName)
		throws PortalException, SystemException {

		_store.updateFile(companyId, repositoryId, fileName, newFileName);
	}

	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String versionNumber, String sourceFileName, byte[] bytes)
		throws PortalException, SystemException {

		_store.updateFile(
			companyId, repositoryId, fileName, versionNumber, sourceFileName,
			bytes);
	}

	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String versionNumber, String sourceFileName, File file)
		throws PortalException, SystemException {

		_store.updateFile(
			companyId, repositoryId, fileName, versionNumber, sourceFileName,
			file);
	}

	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String versionNumber, String sourceFileName, InputStream is)
		throws PortalException, SystemException {

		_store.updateFile(
			companyId, repositoryId, fileName, versionNumber, sourceFileName,
			is);
	}

	public void updateFileVersion(
			long companyId, long repositoryId, String fileName,
			String fromVersionNumber, String toVersionNumber,
			String sourceFileName)
		throws PortalException, SystemException {

		_store.updateFileVersion(
			companyId, repositoryId, fileName, fromVersionNumber,
			toVersionNumber, sourceFileName);
	}

	private Store _store;

}