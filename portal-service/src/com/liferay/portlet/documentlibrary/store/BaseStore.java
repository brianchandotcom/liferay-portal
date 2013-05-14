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
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
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
 * The abstract base class for all file store implementations. Most, if not all
 * implementations should extend this class.
 *
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 * @author Edward Han
 */
public abstract class BaseStore implements Store {

	/**
	 * Adds a directory.
	 *
	 * @param  companyId the primary key of the company
	 * @param  repositoryId the primary key of the data repository (optionally
	 *         {@link com.liferay.portal.model.CompanyConstants#SYSTEM})
	 * @param  dirName the directory's name
	 * @throws PortalException if the directory's information was invalid
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public abstract void addDirectory(
			long companyId, long repositoryId, String dirName)
		throws PortalException, SystemException;

	/**
	 * Adds a file based on a byte array.
	 *
	 * @param  companyId the primary key of the company
	 * @param  repositoryId the primary key of the data repository (optionally
	 *         {@link com.liferay.portal.model.CompanyConstants#SYSTEM})
	 * @param  fileName the file name
	 * @param  bytes the files's data
	 * @throws PortalException if the file's information was invalid
	 * @throws SystemException if a system exception occurred
	 */
	@Override
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

	/**
	 * Adds a file based on a {@link File} object.
	 *
	 * @param  companyId the primary key of the company
	 * @param  repositoryId the primary key of the data repository (optionally
	 *         {@link com.liferay.portal.model.CompanyConstants#SYSTEM})
	 * @param  fileName the file name
	 * @param  file Name the file name
	 * @throws PortalException if the file's information was invalid
	 * @throws SystemException if a system exception occurred
	 */
	@Override
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

	/**
	 * Adds a file based on an {@link InputStream} object.
	 *
	 * @param  companyId the primary key of the company
	 * @param  repositoryId the primary key of the data repository (optionally
	 *         {@link com.liferay.portal.model.CompanyConstants#SYSTEM})
	 * @param  fileName the file name
	 * @param  is the files's data
	 * @throws PortalException if the file's information was invalid
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public abstract void addFile(
			long companyId, long repositoryId, String fileName, InputStream is)
		throws PortalException, SystemException;

	/**
	 * Ensures company's root directory exists. Only implemented by {@link
	 * JCRStore#checkRoot(long)}.
	 *
	 * @param  companyId the primary key of the company
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public abstract void checkRoot(long companyId) throws SystemException;

	/**
	 * Creates a new copy of the file version.
	 *
	 * <p>
	 * This method should be overrided if a more optimized approach can be used
	 * (e.g., {@link FileSystemStore#copyFileVersion(long, long, String, String,
	 * String)}).
	 * </p>
	 *
	 * @param  companyId the primary key of the company
	 * @param  repositoryId the primary key of the data repository (optionally
	 *         {@link com.liferay.portal.model.CompanyConstants#SYSTEM})
	 * @param  fileName the original's file name
	 * @param  fromVersionLabel the original file's version label
	 * @param  toVersionLabel the new version label
	 * @throws PortalException if the file's information was invalid
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void copyFileVersion(
			long companyId, long repositoryId, String fileName,
			String fromVersionLabel, String toVersionLabel)
		throws PortalException, SystemException {

		InputStream is = getFileAsStream(
			companyId, repositoryId, fileName, fromVersionLabel);

		if (is == null) {
			is = new UnsyncByteArrayInputStream(new byte[0]);
		}

		updateFile(companyId, repositoryId, fileName, toVersionLabel, is);
	}

	/**
	 * Deletes a directory.
	 *
	 * @param  companyId the primary key of the company
	 * @param  repositoryId the primary key of the data repository (optionally
	 *         {@link com.liferay.portal.model.CompanyConstants#SYSTEM})
	 * @param  dirName the directory's name
	 * @throws PortalException if the directory's information was invalid
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public abstract void deleteDirectory(
			long companyId, long repositoryId, String dirName)
		throws PortalException, SystemException;

	/**
	 * Deletes a file. If a file has multiple versions, all versions will be
	 * deleted.
	 *
	 * @param  companyId the primary key of the company
	 * @param  repositoryId the primary key of the data repository (optionally
	 *         {@link com.liferay.portal.model.CompanyConstants#SYSTEM})
	 * @param  fileName the file's name
	 * @throws PortalException if the file's information was invalid
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public abstract void deleteFile(
			long companyId, long repositoryId, String fileName)
		throws PortalException, SystemException;

	/**
	 * Deletes a file at a particular version.
	 *
	 * @param  companyId the primary key of the company
	 * @param  repositoryId the primary key of the data repository (optionally
	 *         {@link com.liferay.portal.model.CompanyConstants#SYSTEM})
	 * @param  fileName the file's name
	 * @param  versionLabel the file's version label
	 * @throws PortalException if the file's information was invalid
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public abstract void deleteFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException, SystemException;

	/**
	 * Returns the file as a {@link File} object.
	 *
	 * <p>
	 * This method is useful when optimizing low-level file operations like
	 * copy. The client must not delete or change the returned {@link File}
	 * object in any way. This method is only supported in certain stores. If
	 * not supported, this method will throw an {@link
	 * UnsupportedOperationException}.
	 * </p>
	 *
	 * @param  companyId the primary key of the company
	 * @param  repositoryId the primary key of the data repository (optionally
	 *         {@link com.liferay.portal.model.CompanyConstants#SYSTEM})
	 * @param  fileName the file's name
	 * @return Returns the {@link File} object with the file's name
	 * @throws PortalException if the file's information was invalid
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public File getFile(long companyId, long repositoryId, String fileName)
		throws PortalException, SystemException {

		return getFile(companyId, repositoryId, fileName, StringPool.BLANK);
	}

	/**
	 * Returns the file as a {@link File} object.
	 *
	 * <p>
	 * This method is useful when optimizing low-level file operations like
	 * copy. The client must not delete or change the returned {@link File}
	 * object in any way. This method is only supported in certain stores. If
	 * not supported, this method will throw an {@link
	 * UnsupportedOperationException}.
	 * </p>
	 *
	 * <p>
	 * This method should be overrided if a more optimized approach can be used
	 * (e.g., {@link FileSystemStore#getFile(long, long, String, String)}).
	 * </p>
	 *
	 * @param  companyId the primary key of the company
	 * @param  repositoryId the primary key of the data repository (optionally
	 *         {@link com.liferay.portal.model.CompanyConstants#SYSTEM})
	 * @param  fileName the file's name
	 * @param  versionLabel the file's version label
	 * @return Returns the {@link File} object with the file's name
	 * @throws PortalException if the file's information was invalid
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public File getFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException, SystemException {

		throw new UnsupportedOperationException();
	}

	/**
	 * Returns the file as a byte array.
	 *
	 * @param  companyId the primary key of the company
	 * @param  repositoryId the primary key of the data repository (optionally
	 *         {@link com.liferay.portal.model.CompanyConstants#SYSTEM})
	 * @param  fileName the file's name
	 * @return Returns the byte array with the file's name
	 * @throws PortalException if the file's information was invalid
	 * @throws SystemException if a system exception occurred
	 */
	@Override
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

	/**
	 * Returns the file as a byte array.
	 *
	 * @param  companyId the primary key of the company
	 * @param  repositoryId the primary key of the data repository (optionally
	 *         {@link com.liferay.portal.model.CompanyConstants#SYSTEM})
	 * @param  fileName the file's name
	 * @param  versionLabel the file's version label
	 * @return Returns the byte array with the file's name
	 * @throws PortalException if the file's information was invalid
	 * @throws SystemException if a system exception occurred
	 */
	@Override
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

	/**
	 * Returns the file as an {@link InputStream} object.
	 *
	 * @param  companyId the primary key of the company
	 * @param  repositoryId the primary key of the data repository (optionally
	 *         {@link com.liferay.portal.model.CompanyConstants#SYSTEM})
	 * @param  fileName the file's name
	 * @return Returns the {@link InputStream} object with the file's name
	 * @throws PortalException if the file's information was invalid
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public InputStream getFileAsStream(
			long companyId, long repositoryId, String fileName)
		throws PortalException, SystemException {

		return getFileAsStream(
			companyId, repositoryId, fileName, StringPool.BLANK);
	}

	/**
	 * Returns the file as an {@link InputStream} object.
	 *
	 * @param  companyId the primary key of the company
	 * @param  repositoryId the primary key of the data repository (optionally
	 *         {@link com.liferay.portal.model.CompanyConstants#SYSTEM})
	 * @param  fileName the file's name
	 * @param  versionLabel the file's version label
	 * @return Returns the {@link InputStream} object with the file's name
	 * @throws PortalException if the file's information was invalid
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public abstract InputStream getFileAsStream(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException, SystemException;

	/**
	 * Returns all files of the directory.
	 *
	 * @param  companyId the primary key of the company
	 * @param  repositoryId the primary key of the data repository (optionally
	 *         {@link com.liferay.portal.model.CompanyConstants#SYSTEM})
	 * @param  dirName the directory's name
	 * @return Returns all files of the directory
	 * @throws PortalException if the directory's information was invalid
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public abstract String[] getFileNames(
			long companyId, long repositoryId, String dirName)
		throws PortalException, SystemException;

	/**
	 * Returns the size of the file.
	 *
	 * @param  companyId the primary key of the company
	 * @param  repositoryId the primary key of the data repository (optionally
	 *         {@link com.liferay.portal.model.CompanyConstants#SYSTEM})
	 * @param  fileName the file's name
	 * @return Returns the size of the file
	 * @throws PortalException if the file's information was invalid
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public abstract long getFileSize(
			long companyId, long repositoryId, String fileName)
		throws PortalException, SystemException;

	/**
	 * Returns <code>true</code> if the directory exists.
	 *
	 * @param  companyId the primary key of the company
	 * @param  repositoryId the primary key of the data repository (optionally
	 *         {@link com.liferay.portal.model.CompanyConstants#SYSTEM})
	 * @param  dirName the directory's name
	 * @return <code>true</code> if the directory exists; <code>false</code>
	 *         otherwise
	 * @throws PortalException if the directory's information was invalid
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public abstract boolean hasDirectory(
			long companyId, long repositoryId, String dirName)
		throws PortalException, SystemException;

	/**
	 * Returns <code>true</code> if the file exists.
	 *
	 * @param  companyId the primary key of the company
	 * @param  repositoryId the primary key of the data repository (optionally
	 *         {@link com.liferay.portal.model.CompanyConstants#SYSTEM})
	 * @param  fileName the file's name
	 * @return <code>true</code> if the file exists; <code>false</code>
	 *         otherwise
	 * @throws PortalException if the file's information was invalid
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public boolean hasFile(long companyId, long repositoryId, String fileName)
		throws PortalException, SystemException {

		return hasFile(companyId, repositoryId, fileName, VERSION_DEFAULT);
	}

	/**
	 * Returns <code>true</code> if the file exists.
	 *
	 * @param  companyId the primary key of the company
	 * @param  repositoryId the primary key of the data repository (optionally
	 *         {@link com.liferay.portal.model.CompanyConstants#SYSTEM})
	 * @param  fileName the file's name
	 * @param  versionLabel the file's version label
	 * @return <code>true</code> if the file exists; <code>false</code>
	 *         otherwise
	 * @throws PortalException if the file's information was invalid
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public abstract boolean hasFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException, SystemException;

	/**
	 * Moves an existing directory. Only implemented by {@link
	 * JCRStore#move(String, String)}.
	 *
	 * @param  srcDir the original directory's name
	 * @param  destDir the new directory's name
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public abstract void move(String srcDir, String destDir)
		throws SystemException;

	/**
	 * Moves a file to a new data repository.
	 *
	 * @param  companyId the primary key of the company
	 * @param  repositoryId the primary key of the data repository
	 * @param  newRepositoryId the primary key of the new data repository
	 * @param  fileName the file's name
	 * @throws PortalException if the file's information was invalid
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public abstract void updateFile(
			long companyId, long repositoryId, long newRepositoryId,
			String fileName)
		throws PortalException, SystemException;

	/**
	 * Updates a file based on a byte array.
	 *
	 * @param  companyId the primary key of the company
	 * @param  repositoryId the primary key of the data repository (optionally
	 *         {@link com.liferay.portal.model.CompanyConstants#SYSTEM})
	 * @param  fileName the file name
	 * @param  versionLabel the file's new version label
	 * @param  bytes the new file's data
	 * @throws PortalException if the file's information was invalid
	 * @throws SystemException if a system exception occurred
	 */
	@Override
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

	/**
	 * Updates a file based on a {@link File} object.
	 *
	 * @param  companyId the primary key of the company
	 * @param  repositoryId the primary key of the data repository (optionally
	 *         {@link com.liferay.portal.model.CompanyConstants#SYSTEM})
	 * @param  fileName the file name
	 * @param  versionLabel the file's new version label
	 * @param  file Name the file name
	 * @throws PortalException if the file's information was invalid
	 * @throws SystemException if a system exception occurred
	 */
	@Override
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

	/**
	 * Updates a file based on an {@link InputStream} object.
	 *
	 * @param  companyId the primary key of the company
	 * @param  repositoryId the primary key of the data repository (optionally
	 *         {@link com.liferay.portal.model.CompanyConstants#SYSTEM})
	 * @param  fileName the file name
	 * @param  versionLabel the file's new version label
	 * @param  is the new file's data
	 * @throws PortalException if the file's information was invalid
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public abstract void updateFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel, InputStream is)
		throws PortalException, SystemException;

	/**
	 * Update's a file version label. Similar to {@link #copyFileVersion(long,
	 * long, String, String, String)} except that the old file version is
	 * deleted.
	 *
	 * @param  companyId the primary key of the company
	 * @param  repositoryId the primary key of the data repository (optionally
	 *         {@link com.liferay.portal.model.CompanyConstants#SYSTEM})
	 * @param  fileName the file's name
	 * @param  fromVersionLabel the file's version label
	 * @param  toVersionLabel the file's new version label
	 * @throws PortalException if the file's information was invalid
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void updateFileVersion(
			long companyId, long repositoryId, String fileName,
			String fromVersionLabel, String toVersionLabel)
		throws PortalException, SystemException {

		InputStream is = getFileAsStream(
			companyId, repositoryId, fileName, fromVersionLabel);

		if (is == null) {
			is = new UnsyncByteArrayInputStream(new byte[0]);
		}

		updateFile(companyId, repositoryId, fileName, toVersionLabel, is);

		deleteFile(companyId, repositoryId, fileName, fromVersionLabel);
	}

	private static Log _log = LogFactoryUtil.getLog(BaseStore.class);

}