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
import com.liferay.portal.kernel.util.ServiceProxyFactory;

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
 *     DLStoreUtil.addDirectory(companyId, repositoryId, dirName);
 * }
 * catch (PortalException pe) {
 * }
 *
 * DLStoreUtil.addFile(
 *     companyId, repositoryId, dirName + "/" + fileName, file);
 * </code>
 * </pre></p>
 *
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 * @author Edward Han
 * @author Raymond Augé
 * @see    DLStoreImpl
 */
public class DLStoreUtil {

	public static void addFile(DLStoreRequest dlStoreRequest, byte[] bytes)
		throws PortalException {

		_store.addFile(dlStoreRequest, bytes);
	}

	public static void addFile(DLStoreRequest dlStoreRequest, File file)
		throws PortalException {

		_store.addFile(dlStoreRequest, file);
	}

	public static void addFile(
			DLStoreRequest dlStoreRequest, InputStream inputStream)
		throws PortalException {

		_store.addFile(dlStoreRequest, inputStream);
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

		_store.addFile(
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

		_store.addFile(
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
	 * @param inputStream the files's data
	 */
	public static void addFile(
			long companyId, long repositoryId, String fileName,
			boolean validateFileExtension, InputStream inputStream)
		throws PortalException {

		_store.addFile(
			companyId, repositoryId, fileName, validateFileExtension,
			inputStream);
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

		_store.addFile(companyId, repositoryId, fileName, bytes);
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

		_store.addFile(companyId, repositoryId, fileName, file);
	}

	/**
	 * Adds a file based on an {@link InputStream} object. Enforces validation
	 * of file's extension.
	 *
	 * @param companyId the primary key of the company
	 * @param repositoryId the primary key of the data repository (optionally
	 *        {@link com.liferay.portal.kernel.model.CompanyConstants#SYSTEM})
	 * @param fileName the file name
	 * @param inputStream the files's data
	 */
	public static void addFile(
			long companyId, long repositoryId, String fileName,
			InputStream inputStream)
		throws PortalException {

		_store.addFile(companyId, repositoryId, fileName, inputStream);
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

		_store.copyFileVersion(
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

		_store.deleteDirectory(companyId, repositoryId, dirName);
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

		_store.deleteFile(companyId, repositoryId, fileName);
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

		_store.deleteFile(companyId, repositoryId, fileName, versionLabel);
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

		return _store.getFileAsBytes(companyId, repositoryId, fileName);
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

		return _store.getFileAsBytes(
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

		return _store.getFileAsStream(companyId, repositoryId, fileName);
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

		return _store.getFileAsStream(
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

		return _store.getFileNames(companyId, repositoryId, dirName);
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

		return _store.getFileSize(companyId, repositoryId, fileName);
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

		return _store.hasFile(companyId, repositoryId, fileName);
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

		return _store.hasFile(companyId, repositoryId, fileName, versionLabel);
	}

	public static void updateFile(DLStoreRequest dlStoreRequest, File file)
		throws PortalException {

		_store.updateFile(dlStoreRequest, file);
	}

	public static void updateFile(
			DLStoreRequest dlStoreRequest, InputStream inputStream)
		throws PortalException {

		_store.updateFile(dlStoreRequest, inputStream);
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

		_store.updateFile(companyId, repositoryId, newRepositoryId, fileName);
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

		_store.updateFile(
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
	 * @param inputStream the new file's data
	 */
	public static void updateFile(
			long companyId, long repositoryId, String fileName,
			String fileExtension, boolean validateFileExtension,
			String versionLabel, String sourceFileName, InputStream inputStream)
		throws PortalException {

		_store.updateFile(
			companyId, repositoryId, fileName, fileExtension,
			validateFileExtension, versionLabel, sourceFileName, inputStream);
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

		_store.updateFileVersion(
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

		_store.validate(fileName, validateFileExtension);
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

		_store.validate(fileName, validateFileExtension, bytes);
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

		_store.validate(fileName, validateFileExtension, file);
	}

	/**
	 * Validates a file's name and data.
	 *
	 * @param fileName the file's name
	 * @param validateFileExtension whether to validate the file's extension
	 * @param inputStream the file's data (optionally <code>null</code>)
	 */
	public static void validate(
			String fileName, boolean validateFileExtension,
			InputStream inputStream)
		throws PortalException {

		_store.validate(fileName, validateFileExtension, inputStream);
	}

	public static void validate(
			String fileName, String fileExtension, String sourceFileName,
			boolean validateFileExtension)
		throws PortalException {

		_store.validate(
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

		_store.validate(
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
	 * @param inputStream the file's data (optionally <code>null</code>)
	 */
	public static void validate(
			String fileName, String fileExtension, String sourceFileName,
			boolean validateFileExtension, InputStream inputStream)
		throws PortalException {

		_store.validate(
			fileName, fileExtension, sourceFileName, validateFileExtension,
			inputStream);
	}

	private static volatile DLStore _store =
		ServiceProxyFactory.newServiceTrackedInstance(
			DLStore.class, DLStoreUtil.class, "_store", true);

}