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
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.NoSuchFileException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Brian Wing Shun Chan
 */
public abstract class BaseStore implements Store {

	public abstract void addDirectory(
			long companyId, long repositoryId, String dirName)
		throws PortalException, SystemException;

	public void addFile(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, ServiceContext serviceContext, byte[] bytes)
		throws PortalException, SystemException {

		InputStream is = new UnsyncByteArrayInputStream(bytes);

		try {
			addFile(
				companyId, portletId, groupId, repositoryId, fileName,
				serviceContext, is);
		}
		finally {
			try {
				is.close();
			}
			catch (IOException ioe) {
				_log.error(ioe);
			}
		}
	}

	public void addFile(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, ServiceContext serviceContext, File file)
		throws PortalException, SystemException {

		InputStream is = null;

		try {
			is = new FileInputStream(file);

			addFile(
				companyId, portletId, groupId, repositoryId, fileName,
				serviceContext, is);
		}
		catch (FileNotFoundException fnfe) {
			throw new NoSuchFileException(fileName);
		}
		finally {
			try {
				if (is != null) {
					is.close();
				}
			}
			catch (IOException ioe) {
				_log.error(ioe);
			}
		}
	}

	public abstract void addFile(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, ServiceContext serviceContext, InputStream is)
		throws PortalException, SystemException;

	public abstract void checkRoot(long companyId) throws SystemException;

	public void copyFileVersion(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, String fromVersionNumber, String toVersionNumber,
			String sourceFileName, ServiceContext serviceContext)
		throws PortalException, SystemException {

		InputStream is = getFileAsStream(
			companyId, repositoryId, fileName, fromVersionNumber);

		updateFile(
			companyId, portletId, groupId, repositoryId, fileName,
			toVersionNumber, sourceFileName, serviceContext, is);
	}

	public abstract void deleteDirectory(
			long companyId, String portletId, long repositoryId, String dirName)
		throws PortalException, SystemException;

	public abstract void deleteFile(
			long companyId, String portletId, long repositoryId,
			String fileName)
		throws PortalException, SystemException;

	public abstract void deleteFile(
			long companyId, String portletId, long repositoryId,
			String fileName, String versionNumber)
		throws PortalException, SystemException;

	public byte[] getFile(long companyId, long repositoryId, String fileName)
		throws PortalException, SystemException {

		byte[] bytes = null;

		try {
			InputStream is = getFileAsStream(companyId, repositoryId, fileName);

			bytes = FileUtil.getBytes(is);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}

		return bytes;
	}

	public byte[] getFile(
			long companyId, long repositoryId, String fileName,
			String versionNumber)
		throws PortalException, SystemException {

		byte[] bytes = null;

		try {
			InputStream is = getFileAsStream(
				companyId, repositoryId, fileName, versionNumber);

			bytes = FileUtil.getBytes(is);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}

		return bytes;
	}

	public InputStream getFileAsStream(
			long companyId, long repositoryId, String fileName)
		throws PortalException, SystemException {

		return getFileAsStream(companyId, repositoryId, fileName,
			StringPool.BLANK);
	}

	public abstract InputStream getFileAsStream(
			long companyId, long repositoryId, String fileName,
			String versionNumber)
		throws PortalException, SystemException;

	public abstract String[] getFileNames(
			long companyId, long repositoryId, String dirName)
		throws PortalException, SystemException;

	public abstract long getFileSize(
			long companyId, long repositoryId, String fileName)
		throws PortalException, SystemException;

	public abstract boolean hasFile(
			long companyId, long repositoryId, String fileName,
			String versionNumber)
		throws PortalException, SystemException;

	public abstract void move(String srcDir, String destDir)
		throws SystemException;

	public abstract void updateFile(
			long companyId, String portletId, long groupId, long repositoryId,
			long newRepositoryId, String fileName)
		throws PortalException, SystemException;

	public void updateFile(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, String versionNumber, String sourceFileName,
			ServiceContext serviceContext, byte[] bytes)
		throws PortalException, SystemException {

		InputStream is = new UnsyncByteArrayInputStream(bytes);

		try {
			updateFile(
				companyId, portletId, groupId, repositoryId, fileName,
				versionNumber, sourceFileName, serviceContext, is);
		}
		finally {
			try {
				is.close();
			}
			catch (IOException ioe) {
				_log.error(ioe);
			}
		}
	}

	public void updateFile(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, String versionNumber, String sourceFileName,
			ServiceContext serviceContext, File file)
		throws PortalException, SystemException {

		InputStream is = null;

		try {
			is = new FileInputStream(file);

			updateFile(
				companyId, portletId, groupId, repositoryId, fileName,
				versionNumber, sourceFileName, serviceContext, is);
		}
		catch (FileNotFoundException fnfe) {
			throw new NoSuchFileException(fileName);
		}
		finally {
			try {
				if (is != null) {
					is.close();
				}
			}
			catch (IOException ioe) {
				_log.error(ioe);
			}
		}
	}

	public abstract void updateFile(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, String versionNumber, String sourceFileName,
			ServiceContext serviceContext, InputStream is)
		throws PortalException, SystemException;

	public void updateFileVersion(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, String fromVersionNumber, String toVersionNumber,
			String sourceFileName, ServiceContext serviceContext)
		throws PortalException, SystemException {

		InputStream is = getFileAsStream(
			companyId, repositoryId, fileName, fromVersionNumber);

		updateFile(
			companyId, portletId, groupId, repositoryId, fileName,
			toVersionNumber, sourceFileName, serviceContext, is);

		deleteFile(
			companyId, portletId, repositoryId, fileName, fromVersionNumber);
	}

	private static Log _log = LogFactoryUtil.getLog(BaseStore.class);

}