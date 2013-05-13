/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
 * @author Edward Han
 */
public class StoreProxyImpl implements Store {

	@Override
	public void addDirectory(long companyId, long repositoryId, String dirName)
		throws PortalException, SystemException {

		Store store = StoreFactory.getInstance();

		store.addDirectory(companyId, repositoryId, dirName);
	}

	@Override
	public void addFile(
			long companyId, long repositoryId, String fileName, byte[] bytes)
		throws PortalException, SystemException {

		Store store = StoreFactory.getInstance();

		store.addFile(companyId, repositoryId, fileName, bytes);
	}

	@Override
	public void addFile(
			long companyId, long repositoryId, String fileName, File file)
		throws PortalException, SystemException {

		Store store = StoreFactory.getInstance();

		store.addFile(companyId, repositoryId, fileName, file);
	}

	@Override
	public void addFile(
			long companyId, long repositoryId, String fileName, InputStream is)
		throws PortalException, SystemException {

		Store store = StoreFactory.getInstance();

		store.addFile(companyId, repositoryId, fileName, is);
	}

	@Override
	public void checkRoot(long companyId) throws SystemException {
		Store store = StoreFactory.getInstance();

		store.checkRoot(companyId);
	}

	@Override
	public void copyFileVersion(
			long companyId, long repositoryId, String fileName,
			String fromVersionLabel, String toVersionLabel)
		throws PortalException, SystemException {

		Store store = StoreFactory.getInstance();

		store.copyFileVersion(
			companyId, repositoryId, fileName, fromVersionLabel,
			toVersionLabel);
	}

	@Override
	public void deleteDirectory(
			long companyId, long repositoryId, String dirName)
		throws PortalException, SystemException {

		Store store = StoreFactory.getInstance();

		store.deleteDirectory(companyId, repositoryId, dirName);
	}

	@Override
	public void deleteFile(long companyId, long repositoryId, String fileName)
		throws PortalException, SystemException {

		Store store = StoreFactory.getInstance();

		store.deleteFile(companyId, repositoryId, fileName);
	}

	@Override
	public void deleteFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException, SystemException {

		Store store = StoreFactory.getInstance();

		store.deleteFile(companyId, repositoryId, fileName, versionLabel);
	}

	@Override
	public File getFile(long companyId, long repositoryId, String fileName)
		throws PortalException, SystemException {

		Store store = StoreFactory.getInstance();

		return store.getFile(companyId, repositoryId, fileName);
	}

	@Override
	public File getFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException, SystemException {

		Store store = StoreFactory.getInstance();

		return store.getFile(companyId, repositoryId, fileName, versionLabel);
	}

	@Override
	public byte[] getFileAsBytes(
			long companyId, long repositoryId, String fileName)
		throws PortalException, SystemException {

		Store store = StoreFactory.getInstance();

		return store.getFileAsBytes(companyId, repositoryId, fileName);
	}

	@Override
	public byte[] getFileAsBytes(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException, SystemException {

		Store store = StoreFactory.getInstance();

		return store.getFileAsBytes(
			companyId, repositoryId, fileName, versionLabel);
	}

	@Override
	public InputStream getFileAsStream(
			long companyId, long repositoryId, String fileName)
		throws PortalException, SystemException {

		Store store = StoreFactory.getInstance();

		return store.getFileAsStream(companyId, repositoryId, fileName);
	}

	@Override
	public InputStream getFileAsStream(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException, SystemException {

		Store store = StoreFactory.getInstance();

		return store.getFileAsStream(
			companyId, repositoryId, fileName, versionLabel);
	}

	@Override
	public String[] getFileNames(long companyId, long repositoryId)
		throws SystemException {

		Store store = StoreFactory.getInstance();

		return store.getFileNames(companyId, repositoryId);
	}

	@Override
	public String[] getFileNames(
			long companyId, long repositoryId, String dirName)
		throws PortalException, SystemException {

		Store store = StoreFactory.getInstance();

		return store.getFileNames(companyId, repositoryId, dirName);
	}

	@Override
	public long getFileSize(long companyId, long repositoryId, String fileName)
		throws PortalException, SystemException {

		Store store = StoreFactory.getInstance();

		return store.getFileSize(companyId, repositoryId, fileName);
	}

	@Override
	public boolean hasDirectory(
			long companyId, long repositoryId, String dirName)
		throws PortalException, SystemException {

		Store store = StoreFactory.getInstance();

		return store.hasDirectory(companyId, repositoryId, dirName);
	}

	@Override
	public boolean hasFile(long companyId, long repositoryId, String fileName)
		throws PortalException, SystemException {

		Store store = StoreFactory.getInstance();

		return store.hasFile(companyId, repositoryId, fileName);
	}

	@Override
	public boolean hasFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException, SystemException {

		Store store = StoreFactory.getInstance();

		return store.hasFile(companyId, repositoryId, fileName, versionLabel);
	}

	@Override
	public void move(String srcDir, String destDir) throws SystemException {
		Store store = StoreFactory.getInstance();

		store.move(srcDir, destDir);
	}

	@Override
	public void updateFile(
			long companyId, long repositoryId, long newRepositoryId,
			String fileName)
		throws PortalException, SystemException {

		Store store = StoreFactory.getInstance();

		store.updateFile(companyId, repositoryId, newRepositoryId, fileName);
	}

	@Override
	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String newFileName)
		throws PortalException, SystemException {

		Store store = StoreFactory.getInstance();

		store.updateFile(companyId, repositoryId, fileName, newFileName);
	}

	@Override
	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel, byte[] bytes)
		throws PortalException, SystemException {

		Store store = StoreFactory.getInstance();

		store.updateFile(
			companyId, repositoryId, fileName, versionLabel, bytes);
	}

	@Override
	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel, File file)
		throws PortalException, SystemException {

		Store store = StoreFactory.getInstance();

		store.updateFile(companyId, repositoryId, fileName, versionLabel, file);
	}

	@Override
	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel, InputStream is)
		throws PortalException, SystemException {

		Store store = StoreFactory.getInstance();

		store.updateFile(companyId, repositoryId, fileName, versionLabel, is);
	}

	@Override
	public void updateFileVersion(
			long companyId, long repositoryId, String fileName,
			String fromVersionLabel, String toVersionLabel)
		throws PortalException, SystemException {

		Store store = StoreFactory.getInstance();

		store.updateFileVersion(
			companyId, repositoryId, fileName, fromVersionLabel,
			toVersionLabel);
	}

}