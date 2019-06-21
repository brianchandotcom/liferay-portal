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

/**
 * Provides methods for storing files in the portal. The file storage
 * implementation can be selected in <code>portal.properties</code> under the
 * property <code>dl.store.impl</code>. Virus checking can also be enabled under
 * the property <code>dl.store.antivirus.impl</code>.
 *
 * <p>
 * The main client for this class is the Document Library portlet. It is also
 * used by other portlets like Wiki and Message Boards to store file
 * attachments. For the Document Library portlet, the <code>repositoryId</code>
 * can be obtained by calling {@link
 * com.liferay.portlet.documentlibrary.model.DLFolderConstants#getDataRepositoryId(
 * long,long)}. For all other portlets, the <code>repositoryId</code> should be
 * set to {@link com.liferay.portal.kernel.model.CompanyConstants#SYSTEM}. These
 * methods can be used in plugins and other portlets, as shown below.
 * </p>
 *
 * <p>
 * <pre>
 * <code>
 * long repositoryId = CompanyConstants.SYSTEM;
 * String dirName = "portlet_name/1234";
 *
 * try {
 * DLStoreUtil.addDirectory(companyId, repositoryId, dirName);
 * }
 * catch (DuplicateDirectoryException dde) {
 * }
 *
 * DLStoreUtil.addFile(
 * companyId, repositoryId, dirName + "/" + fileName, file);
 * </code>
 * </pre></p>
 *
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 * @author Edward Han
 * @see    DLStoreImpl
 */
public class DLStoreUtil {

	/**
	 * Adds a directory.
	 *
	 * @param companyId the primary key of the company
	 * @param repositoryId the primary key of the data repository (optionally
	 *        {@link com.liferay.portal.kernel.model.CompanyConstants#SYSTEM})
	 * @param dirName the directory's name
	 */
	public static void addDirectory(
			long companyId, long repositoryId, String dirName)
		throws PortalException {

		getStore().addDirectory(companyId, repositoryId, dirName);
	}

	/**
	 * Adds a file based on a byte array.
	 *
	 * @param companyId the primary key of the company
	 * @param repositoryId the primary key of the data repository (optionally
	 *        {@link com.liferay.portal.kernel.model.CompanyConstants#SYSTEM})
	 * @param fileName the file name
	 * @param validateFileExtension whether to validate the file's extension
	 * @param bytes the files's data
	 */
	public static void addFile(
			long companyId, long repositoryId, String fileName,
			boolean validateFileExtension, byte[] bytes)
		throws PortalException {

		getStore().addFile(
			companyId, repositoryId, fileName, validateFileExtension, bytes);
	}

	/**
	 * Adds a file based on a {@link File} object.
	 *
	 * @param companyId the primary key of the company
	 * @param repositoryId the primary key of the data repository (optionally
	 *        {@link com.liferay.portal.kernel.model.CompanyConstants#SYSTEM})
	 * @param fileName the file name
	 * @param validateFileExtension whether to validate the file's extension
	 * @param file Name the file name
	 */
	public static void addFile(
			long companyId, long repositoryId, String fileName,
			boolean validateFileExtension, File file)
		throws PortalException {

		getStore().addFile(
			companyId, repositoryId, fileName, validateFileExtension, file);
	}

	/**
	 * Adds a file based on a {@link InputStream} object.
	 *
	 * @param companyId the primary key of the company
	 * @param repositoryId the primary key of the data repository (optionally
	 *        {@link com.liferay.portal.kernel.model.CompanyConstants#SYSTEM})
	 * @param fileName the file name
	 * @param validateFileExtension whether to validate the file's extension
	 * @param is the files's data
	 */
	public static void addFile(
			long companyId, long repositoryId, String fileName,
			boolean validateFileExtension, InputStream is)
		throws PortalException {

		getStore().addFile(
			companyId, repositoryId, fileName, validateFileExtension, is);
	}

	/**
	 * Adds a file based on a byte array. Enforces validation of file's
	 * extension.
	 *
	 * @param companyId the primary key of the company
	 * @param repositoryId the primary key of the data repository (optionally
	 *        {@link com.liferay.portal.kernel.model.CompanyConstants#SYSTEM})
	 * @param fileName the file name
	 * @param bytes the files's data
	 */
	public static void addFile(
			long companyId, long repositoryId, String fileName, byte[] bytes)
		throws PortalException {

		getStore().addFile(companyId, repositoryId, fileName, bytes);
	}

	/**
	 * Adds a file based on a {@link File} object. Enforces validation of file's
	 * extension.
	 *
	 * @param companyId the primary key of the company
	 * @param repositoryId the primary key of the data repository (optionally
	 *        {@link com.liferay.portal.kernel.model.CompanyConstants#SYSTEM})
	 * @param fileName the file name
	 * @param file Name the file name
	 */
	public static void addFile(
			long companyId, long repositoryId, String fileName, File file)
		throws PortalException {

		getStore().addFile(companyId, repositoryId, fileName, file);
	}

	/**
	 * Adds a file based on an {@link InputStream} object. Enforces validation
	 * of file's extension.
	 *
	 * @param companyId the primary key of the company
	 * @param repositoryId the primary key of the data repository (optionally
	 *        {@link com.liferay.portal.kernel.model.CompanyConstants#SYSTEM})
	 * @param fileName the file name
	 * @param is the files's data
	 */
	public static void addFile(
			long companyId, long repositoryId, String fileName, InputStream is)
		throws PortalException {

		getStore().addFile(companyId, repositoryId, fileName, is);
	}

	/**
	 * Ensures company's root directory exists.
	 *
	 * @deprecated As of Mueller (7.2.x), with no direct replacement
	 * @param companyId the primary key of the company
	 */
	@Deprecated
	public static void checkRoot(long companyId) {
		getStore().checkRoot(companyId);
	}

	/**
	 * Creates a new copy of the file version.
	 *
	 * @param companyId the primary key of the company
	 * @param repositoryId the primary key of the data repository (optionally
	 *        {@link com.liferay.portal.kernel.model.CompanyConstants#SYSTEM})
	 * @param fileName the original's file name
	 * @param fromVersionLabel the original file's version label
	 * @param toVersionLabel the new version label
	 */
	public static void copyFileVersion(
			long companyId, long repositoryId, String fileName,
			String fromVersionLabel, String toVersionLabel)
		throws PortalException {

		getStore().copyFileVersion(
			companyId, repositoryId, fileName, fromVersionLabel,
			toVersionLabel);
	}

	/**
	 * Deletes a directory.
	 *
	 * @param companyId the primary key of the company
	 * @param repositoryId the primary key of the data repository (optionally
	 *        {@link com.liferay.portal.kernel.model.CompanyConstants#SYSTEM})
	 * @param dirName the directory's name
	 */
	public static void deleteDirectory(
		long companyId, long repositoryId, String dirName) {

		getStore().deleteDirectory(companyId, repositoryId, dirName);
	}

	/**
	 * Deletes a file. If a file has multiple versions, all versions will be
	 * deleted.
	 *
	 * @param companyId the primary key of the company
	 * @param repositoryId the primary key of the data repository (optionally
	 *        {@link com.liferay.portal.kernel.model.CompanyConstants#SYSTEM})
	 * @param fileName the file's name
	 */
	public static void deleteFile(
			long companyId, long repositoryId, String fileName)
		throws PortalException {

		getStore().deleteFile(companyId, repositoryId, fileName);
	}

	/**
	 * Deletes a file at a particular version.
	 *
	 * @param companyId the primary key of the company
	 * @param repositoryId the primary key of the data repository (optionally
	 *        {@link com.liferay.portal.kernel.model.CompanyConstants#SYSTEM})
	 * @param fileName the file's name
	 * @param versionLabel the file's version label
	 */
	public static void deleteFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException {

		getStore().deleteFile(companyId, repositoryId, fileName, versionLabel);
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
	 * If using an S3 store, it is preferable for performance reasons to use
	 * {@link #getFileAsStream(long, long, String)} instead of this method
	 * wherever possible.
	 * </p>
	 *
	 * @param  companyId the primary key of the company
	 * @param  repositoryId the primary key of the data repository (optionally
	 *         {@link com.liferay.portal.kernel.model.CompanyConstants#SYSTEM})
	 * @param  fileName the file's name
	 * @return Returns the {@link File} object with the file's name
	 */
	public static File getFile(
			long companyId, long repositoryId, String fileName)
		throws PortalException {

		return getStore().getFile(companyId, repositoryId, fileName);
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
	 * If using an S3 store, it is preferable for performance reasons to use
	 * {@link #getFileAsStream(long, long, String, String)} instead of this
	 * method wherever possible.
	 * </p>
	 *
	 * @param  companyId the primary key of the company
	 * @param  repositoryId the primary key of the data repository (optionally
	 *         {@link com.liferay.portal.kernel.model.CompanyConstants#SYSTEM})
	 * @param  fileName the file's name
	 * @param  versionLabel the file's version label
	 * @return Returns the {@link File} object with the file's name
	 */
	public static File getFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException {

		return getStore().getFile(
			companyId, repositoryId, fileName, versionLabel);
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
	public static byte[] getFileAsBytes(
			long companyId, long repositoryId, String fileName)
		throws PortalException {

		return getStore().getFileAsBytes(companyId, repositoryId, fileName);
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
	public static byte[] getFileAsBytes(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException {

		return getStore().getFileAsBytes(
			companyId, repositoryId, fileName, versionLabel);
	}

	/**
	 * Returns the file as an {@link InputStream} object.
	 *
	 * <p>
	 * If using an S3 store, it is preferable for performance reasons to use
	 * this method to get the file as an {@link InputStream} instead of using
	 * other methods to get the file as a {@link File}.
	 * </p>
	 *
	 * @param  companyId the primary key of the company
	 * @param  repositoryId the primary key of the data repository (optionally
	 *         {@link com.liferay.portal.kernel.model.CompanyConstants#SYSTEM})
	 * @param  fileName the file's name
	 * @return Returns the {@link InputStream} object with the file's name
	 */
	public static InputStream getFileAsStream(
			long companyId, long repositoryId, String fileName)
		throws PortalException {

		return getStore().getFileAsStream(companyId, repositoryId, fileName);
	}

	/**
	 * Returns the file as an {@link InputStream} object.
	 *
	 * <p>
	 * If using an S3 store, it is preferable for performance reasons to use
	 * this method to get the file as an {@link InputStream} instead of using
	 * other methods to get the file as a {@link File}.
	 * </p>
	 *
	 * @param  companyId the primary key of the company
	 * @param  repositoryId the primary key of the data repository (optionally
	 *         {@link com.liferay.portal.kernel.model.CompanyConstants#SYSTEM})
	 * @param  fileName the file's name
	 * @param  versionLabel the file's version label
	 * @return Returns the {@link InputStream} object with the file's name
	 */
	public static InputStream getFileAsStream(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException {

		return getStore().getFileAsStream(
			companyId, repositoryId, fileName, versionLabel);
	}

	/**
	 * Returns all files of the directory.
	 *
	 * @param  companyId the primary key of the company
	 * @param  repositoryId the primary key of the data repository (optionally
	 *         {@link com.liferay.portal.kernel.model.CompanyConstants#SYSTEM})
	 * @param  dirName the directory's name
	 * @return Returns all files of the directory
	 */
	public static String[] getFileNames(
			long companyId, long repositoryId, String dirName)
		throws PortalException {

		return getStore().getFileNames(companyId, repositoryId, dirName);
	}

	/**
	 * Returns the size of the file.
	 *
	 * @param  companyId the primary key of the company
	 * @param  repositoryId the primary key of the data repository (optionally
	 *         {@link com.liferay.portal.kernel.model.CompanyConstants#SYSTEM})
	 * @param  fileName the file's name
	 * @return Returns the size of the file
	 */
	public static long getFileSize(
			long companyId, long repositoryId, String fileName)
		throws PortalException {

		return getStore().getFileSize(companyId, repositoryId, fileName);
	}

	/**
	 * Returns the {@link DLStore} object. Used primarily by Spring and should
	 * not be used by the client.
	 *
	 * @return Returns the {@link DLStore} object
	 */
	public static DLStore getStore() {
		return _store;
	}

	/**
	 * Returns <code>true</code> if the directory exists.
	 *
	 * @param  companyId the primary key of the company
	 * @param  repositoryId the primary key of the data repository (optionally
	 *         {@link com.liferay.portal.kernel.model.CompanyConstants#SYSTEM})
	 * @param  dirName the directory's name
	 * @return <code>true</code> if the directory exists; <code>false</code>
	 *         otherwise
	 */
	public static boolean hasDirectory(
			long companyId, long repositoryId, String dirName)
		throws PortalException {

		return getStore().hasDirectory(companyId, repositoryId, dirName);
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
	public static boolean hasFile(
			long companyId, long repositoryId, String fileName)
		throws PortalException {

		return getStore().hasFile(companyId, repositoryId, fileName);
	}

	/**
	 * Returns <code>true</code> if the file exists.
	 *
	 * @param  companyId the primary key of the company
	 * @param  repositoryId the primary key of the data repository (optionally
	 *         {@link com.liferay.portal.kernel.model.CompanyConstants#SYSTEM})
	 * @param  fileName the file's name
	 * @param  versionLabel the file's version label
	 * @return <code>true</code> if the file exists; <code>false</code>
	 *         otherwise
	 */
	public static boolean hasFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException {

		return getStore().hasFile(
			companyId, repositoryId, fileName, versionLabel);
	}

	public static boolean isValidName(String name) {
		return getStore().isValidName(name);
	}

	/**
	 * Moves an existing directory.
	 *
	 * @deprecated As of Mueller (7.2.x), with no direct replacement
	 * @param srcDir the original directory's name
	 * @param destDir the new directory's name
	 */
	@Deprecated
	public static void move(String srcDir, String destDir) {
		getStore().move(srcDir, destDir);
	}

	/**
	 * Moves a file to a new data repository.
	 *
	 * @param companyId the primary key of the company
	 * @param repositoryId the primary key of the data repository
	 * @param newRepositoryId the primary key of the new data repository
	 * @param fileName the file's name
	 */
	public static void updateFile(
			long companyId, long repositoryId, long newRepositoryId,
			String fileName)
		throws PortalException {

		getStore().updateFile(
			companyId, repositoryId, newRepositoryId, fileName);
	}

	/**
	 * Update's the file's name
	 *
	 * @param companyId the primary key of the company
	 * @param repositoryId the primary key of the data repository (optionally
	 *        {@link com.liferay.portal.kernel.model.CompanyConstants#SYSTEM})
	 * @param fileName the file's name
	 * @param newFileName the file's new name
	 */
	public static void updateFile(
			long companyId, long repositoryId, String fileName,
			String newFileName)
		throws PortalException {

		getStore().updateFile(companyId, repositoryId, fileName, newFileName);
	}

	/**
	 * Updates a file based on a {@link File} object.
	 *
	 * @param companyId the primary key of the company
	 * @param repositoryId the primary key of the data repository (optionally
	 *        {@link com.liferay.portal.kernel.model.CompanyConstants#SYSTEM})
	 * @param fileName the file name
	 * @param fileExtension the file's extension
	 * @param validateFileExtension whether to validate the file's extension
	 * @param versionLabel the file's new version label
	 * @param sourceFileName the new file's original name
	 * @param file Name the file name
	 */
	public static void updateFile(
			long companyId, long repositoryId, String fileName,
			String fileExtension, boolean validateFileExtension,
			String versionLabel, String sourceFileName, File file)
		throws PortalException {

		getStore().updateFile(
			companyId, repositoryId, fileName, fileExtension,
			validateFileExtension, versionLabel, sourceFileName, file);
	}

	/**
	 * Updates a file based on a {@link InputStream} object.
	 *
	 * @param companyId the primary key of the company
	 * @param repositoryId the primary key of the data repository (optionally
	 *        {@link com.liferay.portal.kernel.model.CompanyConstants#SYSTEM})
	 * @param fileName the file name
	 * @param fileExtension the file's extension
	 * @param validateFileExtension whether to validate the file's extension
	 * @param versionLabel the file's new version label
	 * @param sourceFileName the new file's original name
	 * @param is the new file's data
	 */
	public static void updateFile(
			long companyId, long repositoryId, String fileName,
			String fileExtension, boolean validateFileExtension,
			String versionLabel, String sourceFileName, InputStream is)
		throws PortalException {

		getStore().updateFile(
			companyId, repositoryId, fileName, fileExtension,
			validateFileExtension, versionLabel, sourceFileName, is);
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
	public static void updateFileVersion(
			long companyId, long repositoryId, String fileName,
			String fromVersionLabel, String toVersionLabel)
		throws PortalException {

		getStore().updateFileVersion(
			companyId, repositoryId, fileName, fromVersionLabel,
			toVersionLabel);
	}

	/**
	 * Validates a file's name.
	 *
	 * @param fileName the file's name
	 * @param validateFileExtension whether to validate the file's extension
	 */
	public static void validate(String fileName, boolean validateFileExtension)
		throws PortalException {

		getStore().validate(fileName, validateFileExtension);
	}

	/**
	 * Validates a file's name and data.
	 *
	 * @param fileName the file's name
	 * @param validateFileExtension whether to validate the file's extension
	 * @param bytes the file's data (optionally <code>null</code>)
	 */
	public static void validate(
			String fileName, boolean validateFileExtension, byte[] bytes)
		throws PortalException {

		getStore().validate(fileName, validateFileExtension, bytes);
	}

	/**
	 * Validates a file's name and data.
	 *
	 * @param fileName the file's name
	 * @param validateFileExtension whether to validate the file's extension
	 * @param file Name the file's name
	 */
	public static void validate(
			String fileName, boolean validateFileExtension, File file)
		throws PortalException {

		getStore().validate(fileName, validateFileExtension, file);
	}

	/**
	 * Validates a file's name and data.
	 *
	 * @param fileName the file's name
	 * @param validateFileExtension whether to validate the file's extension
	 * @param is the file's data (optionally <code>null</code>)
	 */
	public static void validate(
			String fileName, boolean validateFileExtension, InputStream is)
		throws PortalException {

		getStore().validate(fileName, validateFileExtension, is);
	}

	public static void validate(
			String fileName, String fileExtension, String sourceFileName,
			boolean validateFileExtension)
		throws PortalException {

		getStore().validate(
			fileName, fileExtension, sourceFileName, validateFileExtension);
	}

	/**
	 * Validates a file's name and data.
	 *
	 * @param fileName the file's name
	 * @param fileExtension the file's extension
	 * @param sourceFileName the file's original name
	 * @param validateFileExtension whether to validate the file's extension
	 * @param file Name the file's name
	 */
	public static void validate(
			String fileName, String fileExtension, String sourceFileName,
			boolean validateFileExtension, File file)
		throws PortalException {

		getStore().validate(
			fileName, fileExtension, sourceFileName, validateFileExtension,
			file);
	}

	/**
	 * Validates a file's name and data.
	 *
	 * @param fileName the file's name
	 * @param fileExtension the file's extension
	 * @param sourceFileName the file's original name
	 * @param validateFileExtension whether to validate the file's extension
	 * @param is the file's data (optionally <code>null</code>)
	 */
	public static void validate(
			String fileName, String fileExtension, String sourceFileName,
			boolean validateFileExtension, InputStream is)
		throws PortalException {

		getStore().validate(
			fileName, fileExtension, sourceFileName, validateFileExtension, is);
	}

	public static void validateDirectoryName(String directoryName)
		throws PortalException {

		getStore().validateDirectoryName(directoryName);
	}

	/**
	 * Set's the {@link DLStore} object. Used primarily by Spring and should not
	 * be used by the client.
	 *
	 * @param store the {@link DLStore} object
	 */
	public void setStore(DLStore store) {
		_store = store;
	}

	private static DLStore _store;

}