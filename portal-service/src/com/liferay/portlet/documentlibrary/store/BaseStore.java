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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portlet.documentlibrary.NoSuchFileException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public abstract class BaseStore implements Store {

	public abstract void addDirectory(
			long companyId, long repositoryId, String dirName)
		throws PortalException, SystemException;

	public void addFile(
			long companyId, long repositoryId, String fileName, byte[] bytes)
		throws PortalException, SystemException {

		File file = null;

		try {
			file = FileUtil.createTempFile(bytes);

			addFile(companyId, repositoryId, fileName, file);
		}
		catch (IOException ioe) {
			throw new SystemException("Unable to write temporary file", ioe);
		}
		finally {
			FileUtil.delete(file);
		}
	}

	public void addFile(
			long companyId, long repositoryId, String fileName, File file)
		throws PortalException, SystemException {

		InputStream is = null;

		try {
			is = new FileInputStream(file);

			addFile(companyId, repositoryId, fileName, is);
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
			long companyId, long repositoryId, String fileName, InputStream is)
		throws PortalException, SystemException;

	public abstract void checkRoot(long companyId) throws SystemException;

	public void copyFileVersion(
			long companyId, long repositoryId, String fileName,
			String fromVersionLabel, String toVersionLabel)
		throws PortalException, SystemException {

		InputStream is = getFileAsStream(
			companyId, repositoryId, fileName, fromVersionLabel);

		updateFile(companyId, repositoryId, fileName, toVersionLabel, is);
	}

	public abstract void deleteDirectory(
			long companyId, long repositoryId, String dirName)
		throws PortalException, SystemException;

	public abstract void deleteFile(
			long companyId, long repositoryId, String fileName)
		throws PortalException, SystemException;

	public abstract void deleteFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException, SystemException;

	public File getFile(long companyId, long repositoryId, String fileName)
		throws PortalException, SystemException {

		return getFile(companyId, repositoryId, fileName, StringPool.BLANK);
	}

	@SuppressWarnings("unused")
	public File getFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException, SystemException {

		throw new UnsupportedOperationException();
	}

	public byte[] getFileAsBytes(
			long companyId, long repositoryId, String fileName)
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

	public byte[] getFileAsBytes(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException, SystemException {

		byte[] bytes = null;

		try {
			InputStream is = getFileAsStream(
				companyId, repositoryId, fileName, versionLabel);

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
			String versionLabel)
		throws PortalException, SystemException;

	public abstract String[] getFileNames(
			long companyId, long repositoryId, String dirName)
		throws PortalException, SystemException;

	public abstract long getFileSize(
			long companyId, long repositoryId, String fileName)
		throws PortalException, SystemException;

	public boolean hasFile(long companyId, long repositoryId, String fileName)
		throws PortalException, SystemException {

		return hasFile(companyId, repositoryId, fileName, VERSION_DEFAULT);
	}

	public abstract boolean hasFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException, SystemException;

	public abstract void move(String srcDir, String destDir)
		throws SystemException;

	public abstract void updateFile(
			long companyId, long repositoryId,
			long newRepositoryId, String fileName)
		throws PortalException, SystemException;

	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel, byte[] bytes)
		throws PortalException, SystemException {

		File file = null;

		try {
			file = FileUtil.createTempFile(bytes);

			updateFile(companyId, repositoryId, fileName, versionLabel, file);
		}
		catch (IOException ioe) {
			throw new SystemException("Unable to write temporary file", ioe);
		}
		finally {
			FileUtil.delete(file);
		}
	}

	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel, File file)
		throws PortalException, SystemException {

		InputStream is = null;

		try {
			is = new FileInputStream(file);

			updateFile(companyId, repositoryId, fileName, versionLabel, is);
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
			long companyId, long repositoryId, String fileName,
			String versionLabel, InputStream is)
		throws PortalException, SystemException;

	public void updateFileVersion(
			long companyId, long repositoryId, String fileName,
			String fromVersionLabel, String toVersionLabel)
		throws PortalException, SystemException {

		InputStream is = getFileAsStream(
			companyId, repositoryId, fileName, fromVersionLabel);

		updateFile(
			companyId, repositoryId, fileName, toVersionLabel, is);

		deleteFile(
			companyId, repositoryId, fileName, fromVersionLabel);
	}

	private static Log _log = LogFactoryUtil.getLog(BaseStore.class);

}