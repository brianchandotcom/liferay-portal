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

import com.liferay.document.library.kernel.exception.NoSuchFileException;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.Validator;

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
	 * Adds a file based on a byte array.
	 *
	 * @param companyId the primary key of the company
	 * @param repositoryId the primary key of the data repository (optionally
	 *        {@link com.liferay.portal.kernel.model.CompanyConstants#SYSTEM})
	 * @param fileName the file name
	 * @param bytes the files's data
	 */
	@Override
	public void addFile(
			long companyId, long repositoryId, String fileName, byte[] bytes)
		throws PortalException {

		try (UnsyncByteArrayInputStream unsyncByteArrayInputStream =
				new UnsyncByteArrayInputStream(bytes)) {

			addFile(
				companyId, repositoryId, fileName, unsyncByteArrayInputStream);
		}
		catch (IOException ioe) {
			throw new SystemException("Unable to read bytes", ioe);
		}
	}

	/**
	 * Adds a file based on a {@link File} object.
	 *
	 * @param companyId the primary key of the company
	 * @param repositoryId the primary key of the data repository (optionally
	 *        {@link com.liferay.portal.kernel.model.CompanyConstants#SYSTEM})
	 * @param fileName the file name
	 * @param file Name the file name
	 */
	@Override
	public void addFile(
			long companyId, long repositoryId, String fileName, File file)
		throws PortalException {

		try (InputStream is = new FileInputStream(file)) {
			addFile(companyId, repositoryId, fileName, is);
		}
		catch (FileNotFoundException fnfe) {
			throw new SystemException(fnfe);
		}
		catch (IOException ioe) {
			_log.error("Unable to add file", ioe);
		}
	}

	/**
	 * Creates a new copy of the file version.
	 *
	 * <p>
	 * This method should be overrided if a more optimized approach can be used
	 * (e.g., {@link FileSystemStore#copyFileVersion(long, long, String, String,
	 * String)}).
	 * </p>
	 *
	 * @param companyId the primary key of the company
	 * @param repositoryId the primary key of the data repository (optionally
	 *        {@link com.liferay.portal.kernel.model.CompanyConstants#SYSTEM})
	 * @param fileName the original's file name
	 * @param fromVersionLabel the original file's version label
	 * @param toVersionLabel the new version label
	 */
	@Override
	public void copyFileVersion(
			long companyId, long repositoryId, String fileName,
			String fromVersionLabel, String toVersionLabel)
		throws PortalException {

		InputStream is = getFileAsStream(
			companyId, repositoryId, fileName, fromVersionLabel);

		if (is == null) {
			is = new UnsyncByteArrayInputStream(new byte[0]);
		}

		updateFile(companyId, repositoryId, fileName, toVersionLabel, is);
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
	 * @param  companyId the primary key of the company
	 * @param  repositoryId the primary key of the data repository (optionally
	 *         {@link com.liferay.portal.kernel.model.CompanyConstants#SYSTEM})
	 * @param  fileName the file's name
	 * @return Returns the {@link File} object with the file's name
	 */
	@Override
	public File getFile(long companyId, long repositoryId, String fileName)
		throws PortalException {

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
	 *         {@link com.liferay.portal.kernel.model.CompanyConstants#SYSTEM})
	 * @param  fileName the file's name
	 * @param  versionLabel the file's version label
	 * @return Returns the {@link File} object with the file's name
	 * @throws PortalException
	 */
	@Override
	public File getFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	/**
	 * Returns the file as a byte array.
	 *
	 * @param  companyId the primary key of the company
	 * @param  repositoryId the primary key of the data repository (optionally
	 *         {@link com.liferay.portal.kernel.model.CompanyConstants#SYSTEM})
	 * @param  fileName the file's name
	 * @return Returns the byte array with the file's name
	 */
	@Override
	public byte[] getFileAsBytes(
			long companyId, long repositoryId, String fileName)
		throws PortalException {

		try {
			InputStream is = getFileAsStream(companyId, repositoryId, fileName);

			return FileUtil.getBytes(is);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	/**
	 * Returns the file as a byte array.
	 *
	 * @param  companyId the primary key of the company
	 * @param  repositoryId the primary key of the data repository (optionally
	 *         {@link com.liferay.portal.kernel.model.CompanyConstants#SYSTEM})
	 * @param  fileName the file's name
	 * @param  versionLabel the file's version label
	 * @return Returns the byte array with the file's name
	 */
	@Override
	public byte[] getFileAsBytes(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException {

		try {
			InputStream is = getFileAsStream(
				companyId, repositoryId, fileName, versionLabel);

			return FileUtil.getBytes(is);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	/**
	 * Returns the file as an {@link InputStream} object.
	 *
	 * @param  companyId the primary key of the company
	 * @param  repositoryId the primary key of the data repository (optionally
	 *         {@link com.liferay.portal.kernel.model.CompanyConstants#SYSTEM})
	 * @param  fileName the file's name
	 * @return Returns the {@link InputStream} object with the file's name
	 */
	@Override
	public InputStream getFileAsStream(
			long companyId, long repositoryId, String fileName)
		throws PortalException {

		return getFileAsStream(
			companyId, repositoryId, fileName, StringPool.BLANK);
	}

	/**
	 * Returns <code>true</code> if the file exists.
	 *
	 * @param  companyId the primary key of the company
	 * @param  repositoryId the primary key of the data repository (optionally
	 *         {@link com.liferay.portal.kernel.model.CompanyConstants#SYSTEM})
	 * @param  fileName the file's name
	 * @return <code>true</code> if the file exists; <code>false</code>
	 *         otherwise
	 */
	@Override
	public boolean hasFile(long companyId, long repositoryId, String fileName) {
		return hasFile(companyId, repositoryId, fileName, VERSION_DEFAULT);
	}

	/**
	 * Moves an existing directory.
	 *
	 * @deprecated As of Mueller (7.2.x), with no direct replacement
	 * @param srcDir the original directory's name
	 * @param destDir the new directory's name
	 */
	@Deprecated
	@Override
	public void move(String srcDir, String destDir) {
	}

	/**
	 * Updates a file based on a byte array.
	 *
	 * @param companyId the primary key of the company
	 * @param repositoryId the primary key of the data repository (optionally
	 *        {@link com.liferay.portal.kernel.model.CompanyConstants#SYSTEM})
	 * @param fileName the file name
	 * @param versionLabel the file's new version label
	 * @param bytes the new file's data
	 */
	@Override
	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel, byte[] bytes)
		throws PortalException {

		try (UnsyncByteArrayInputStream unsyncByteArrayInputStream =
				new UnsyncByteArrayInputStream(bytes)) {

			updateFile(
				companyId, repositoryId, fileName, versionLabel,
				unsyncByteArrayInputStream);
		}
		catch (IOException ioe) {
			throw new SystemException("Unable to read bytes", ioe);
		}
	}

	/**
	 * Updates a file based on a {@link File} object.
	 *
	 * @param companyId the primary key of the company
	 * @param repositoryId the primary key of the data repository (optionally
	 *        {@link com.liferay.portal.kernel.model.CompanyConstants#SYSTEM})
	 * @param fileName the file name
	 * @param versionLabel the file's new version label
	 * @param file Name the file name
	 */
	@Override
	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel, File file)
		throws PortalException {

		try (InputStream is = new FileInputStream(file)) {
			updateFile(companyId, repositoryId, fileName, versionLabel, is);
		}
		catch (FileNotFoundException fnfe) {
			throw new NoSuchFileException(
				companyId, repositoryId, fileName, versionLabel, fnfe);
		}
		catch (IOException ioe) {
			_log.error("Unable to update file", ioe);
		}
	}

	/**
	 * Update's a file version label. Similar to {@link #copyFileVersion(long,
	 * long, String, String, String)} except that the old file version is
	 * deleted.
	 *
	 * @param companyId the primary key of the company
	 * @param repositoryId the primary key of the data repository (optionally
	 *        {@link com.liferay.portal.kernel.model.CompanyConstants#SYSTEM})
	 * @param fileName the file's name
	 * @param fromVersionLabel the file's version label
	 * @param toVersionLabel the file's new version label
	 */
	@Override
	public void updateFileVersion(
			long companyId, long repositoryId, String fileName,
			String fromVersionLabel, String toVersionLabel)
		throws PortalException {

		InputStream is = getFileAsStream(
			companyId, repositoryId, fileName, fromVersionLabel);

		if (is == null) {
			is = new UnsyncByteArrayInputStream(new byte[0]);
		}

		updateFile(companyId, repositoryId, fileName, toVersionLabel, is);

		deleteFile(companyId, repositoryId, fileName, fromVersionLabel);
	}

	protected void logFailedDeletion(
		long companyId, long repositoryId, String fileName) {

		logFailedDeletion(companyId, repositoryId, fileName, null, null);
	}

	protected void logFailedDeletion(
		long companyId, long repositoryId, String fileName,
		Exception exception) {

		logFailedDeletion(companyId, repositoryId, fileName, null, exception);
	}

	protected void logFailedDeletion(
		long companyId, long repositoryId, String fileName,
		String versionLabel) {

		logFailedDeletion(
			companyId, repositoryId, fileName, versionLabel, null);
	}

	protected void logFailedDeletion(
		long companyId, long repositoryId, String fileName, String versionLabel,
		Exception cause) {

		if ((_log.isWarnEnabled() && (cause != null)) ||
			(_log.isDebugEnabled() && (cause == null))) {

			StringBundler sb = new StringBundler(9);

			sb.append("Unable to delete file {companyId=");
			sb.append(companyId);
			sb.append(", repositoryId=");
			sb.append(repositoryId);
			sb.append(", fileName=");
			sb.append(fileName);

			if (Validator.isNotNull(versionLabel)) {
				sb.append(", versionLabel=");
				sb.append(versionLabel);
			}

			sb.append("} because it does not exist");

			if (_log.isWarnEnabled() && (cause != null)) {
				_log.warn(sb.toString(), cause);
			}

			if (_log.isDebugEnabled() && (cause == null)) {
				_log.debug(sb.toString());
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(BaseStore.class);

}