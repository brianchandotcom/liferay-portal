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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.MethodCache;
import com.liferay.portal.kernel.util.ReferenceRegistry;

import java.io.File;
import java.io.InputStream;

/**
 * @author Brian Wing Shun Chan
 */
public class DLStoreUtil {

	public static void addDirectory(
			long companyId, long repositoryId, String dirName)
		throws PortalException, SystemException {

		getStore().addDirectory(companyId, repositoryId, dirName);
	}

	public static void addFile(
			long companyId, long repositoryId, String fileName,
			boolean validateFileExtension, File file)
		throws PortalException, SystemException {

		getStore().addFile(
			companyId, repositoryId, fileName, validateFileExtension, file);
	}

	public static void addFile(
			long companyId, long repositoryId, String fileName,
			boolean validateFileExtension, InputStream is)
		throws PortalException, SystemException {

		getStore().addFile(
			companyId, repositoryId, fileName, validateFileExtension, is);
	}

	public static void addFile(
			long companyId, long repositoryId, String fileName, byte[] bytes)
		throws PortalException, SystemException {

		getStore().addFile(companyId, repositoryId, fileName, bytes);
	}

	public static void addFile(
			long companyId, long repositoryId, String fileName, File file)
		throws PortalException, SystemException {

		getStore().addFile(companyId, repositoryId, fileName, file);
	}

	public static void checkRoot(long companyId) throws SystemException {
		getStore().checkRoot(companyId);
	}

	public static void copyFileVersion(
			long companyId, long repositoryId, String fileName,
			String fromVersionNumber, String toVersionNumber,
			String sourceFileName)
		throws PortalException, SystemException {

		getStore().copyFileVersion(
			companyId, repositoryId, fileName, fromVersionNumber,
			toVersionNumber, sourceFileName);
	}

	public static void deleteDirectory(
			long companyId, long repositoryId, String dirName)
		throws PortalException, SystemException {

		getStore().deleteDirectory(companyId, repositoryId, dirName);
	}

	public static void deleteFile(
			long companyId, long repositoryId, String fileName)
		throws PortalException, SystemException {

		getStore().deleteFile(companyId, repositoryId, fileName);
	}

	public static void deleteFile(
			long companyId, long repositoryId, String fileName,
			String versionNumber)
		throws PortalException, SystemException {

		getStore().deleteFile(companyId, repositoryId, fileName, versionNumber);
	}

	public static byte[] getFile(
			long companyId, long repositoryId, String fileName)
		throws PortalException, SystemException {

		return getStore().getFile(companyId, repositoryId, fileName);
	}

	public static byte[] getFile(
			long companyId, long repositoryId, String fileName,
			String versionNumber)
		throws PortalException, SystemException {

		return getStore().getFile(
			companyId, repositoryId, fileName, versionNumber);
	}

	public static InputStream getFileAsStream(
			long companyId, long repositoryId, String fileName)
		throws PortalException, SystemException {

		return getStore().getFileAsStream(companyId, repositoryId, fileName);
	}

	public static InputStream getFileAsStream(
			long companyId, long repositoryId, String fileName,
			String versionNumber)
		throws PortalException, SystemException {

		return getStore().getFileAsStream(
			companyId, repositoryId, fileName, versionNumber);
	}

	public static String[] getFileNames(
			long companyId, long repositoryId, String dirName)
		throws PortalException, SystemException {

		return getStore().getFileNames(companyId, repositoryId, dirName);
	}

	public static long getFileSize(
			long companyId, long repositoryId, String fileName)
		throws PortalException, SystemException {

		return getStore().getFileSize(companyId, repositoryId, fileName);
	}

	public static DLStore getStore() {
		if (_store == null) {
			_store = (DLStore)PortalBeanLocatorUtil.locate(
				DLStore.class.getName());

			ReferenceRegistry.registerReference(DLStoreUtil.class, "_store");

			MethodCache.remove(DLStore.class);
		}

		return _store;
	}

	public static boolean hasFile(
			long companyId, long repositoryId, String fileName,
			String versionNumber)
		throws PortalException, SystemException {

		return getStore().hasFile(
			companyId, repositoryId, fileName, versionNumber);
	}

	public static void move(String srcDir, String destDir)
		throws SystemException {

		getStore().move(srcDir, destDir);
	}

	public static void updateFile(
			long companyId, long repositoryId, long newRepositoryId,
			String fileName)
		throws PortalException, SystemException {

		getStore().updateFile(
			companyId, repositoryId, newRepositoryId, fileName);
	}

	public static void updateFile(
			long companyId, long repositoryId, String fileName,
			String newFileName)
		throws PortalException, SystemException {

		getStore().updateFile(companyId, repositoryId, fileName, newFileName);
	}

	public static void updateFile(
			long companyId, long repositoryId, String fileName,
			String fileExtension, boolean validateFileExtension,
			String versionNumber, String sourceFileName, File file)
		throws PortalException, SystemException {

		getStore().updateFile(
			companyId, repositoryId, fileName, fileExtension,
			validateFileExtension, versionNumber, sourceFileName, file);
	}

	public static void updateFile(
			long companyId, long repositoryId, String fileName,
			String fileExtension, boolean validateFileExtension,
			String versionNumber, String sourceFileName, InputStream is)
		throws PortalException, SystemException {

		getStore().updateFile(
			companyId, repositoryId, fileName, fileExtension,
			validateFileExtension, versionNumber, sourceFileName, is);
	}

	public static void updateFile(
			long companyId, long repositoryId, String fileName,
			String versionNumber, String sourceFileName, byte[] bytes)
		throws PortalException, SystemException {

		getStore().updateFile(
			companyId, repositoryId, fileName, versionNumber, sourceFileName,
			bytes);
	}

	public static void updateFile(
			long companyId, long repositoryId, String fileName,
			String versionNumber, String sourceFileName, File file)
		throws PortalException, SystemException {

		getStore().updateFile(
			companyId, repositoryId, fileName, versionNumber, sourceFileName,
			file);
	}

	public static void updateFileVersion(
			long companyId, long repositoryId, String fileName,
			String fromVersionNumber, String toVersionNumber,
			String sourceFileName)
		throws PortalException, SystemException {

		getStore().updateFileVersion(
			companyId, repositoryId, fileName, fromVersionNumber,
			toVersionNumber, sourceFileName);
	}

	public static void validate(String fileName, boolean validateFileExtension)
		throws PortalException, SystemException {

		getStore().validate(fileName, validateFileExtension);
	}

	public static void validate(
			String fileName, boolean validateFileExtension, byte[] bytes)
		throws PortalException, SystemException {

		getStore().validate(fileName, validateFileExtension, bytes);
	}

	public static void validate(
			String fileName, boolean validateFileExtension, File file)
		throws PortalException, SystemException {

		getStore().validate(fileName, validateFileExtension, file);
	}

	public static void validate(
			String fileName, boolean validateFileExtension, InputStream is)
		throws PortalException, SystemException {

		getStore().validate(fileName, validateFileExtension, is);
	}

	public static void validate(
			String fileName, String fileExtension, String sourceFileName,
			boolean validateFileExtension, File file)
		throws PortalException, SystemException {

		getStore().validate(
			fileName, fileExtension, sourceFileName, validateFileExtension,
			file);
	}

	public static void validate(
			String fileName, String fileExtension, String sourceFileName,
			boolean validateFileExtension, InputStream is)
		throws PortalException, SystemException {

		getStore().validate(
			fileName, fileExtension, sourceFileName, validateFileExtension, is);
	}

	public void setStore(DLStore store) {
		_store = store;

		ReferenceRegistry.registerReference(DLStoreUtil.class, "_store");

		MethodCache.remove(DLStore.class);
	}

	private static DLStore _store;

}