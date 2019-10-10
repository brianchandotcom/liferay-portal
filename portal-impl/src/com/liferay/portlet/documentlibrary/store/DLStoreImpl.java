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

package com.liferay.portlet.documentlibrary.store;

import com.liferay.document.library.kernel.antivirus.AntivirusScannerUtil;
import com.liferay.document.library.kernel.exception.AccessDeniedException;
import com.liferay.document.library.kernel.exception.DirectoryNameException;
import com.liferay.document.library.kernel.store.DLStore;
import com.liferay.document.library.kernel.store.Store;
import com.liferay.document.library.kernel.util.DLValidatorUtil;
import com.liferay.petra.io.StreamUtil;
import com.liferay.petra.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.ByteArrayFileInputStream;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 * @author Edward Han
 */
public class DLStoreImpl implements DLStore {

	public DLStoreImpl() {
		_storeFactory = StoreFactory.getInstance();
	}

	@Override
	public void addFile(
			long companyId, long repositoryId, String fileName,
			boolean validateFileExtension, byte[] bytes)
		throws PortalException {

		validate(fileName, validateFileExtension);

		if (PropsValues.DL_STORE_ANTIVIRUS_ENABLED) {
			AntivirusScannerUtil.scan(bytes);
		}

		Store store = _storeFactory.getStore();

		store.addFile(
			companyId, repositoryId, fileName, Store.VERSION_DEFAULT,
			new UnsyncByteArrayInputStream(bytes));
	}

	@Override
	public void addFile(
			long companyId, long repositoryId, String fileName,
			boolean validateFileExtension, File file)
		throws PortalException {

		validate(fileName, validateFileExtension);

		if (PropsValues.DL_STORE_ANTIVIRUS_ENABLED) {
			AntivirusScannerUtil.scan(file);
		}

		Store store = _storeFactory.getStore();

		try (InputStream is = new FileInputStream(file)) {
			store.addFile(
				companyId, repositoryId, fileName, Store.VERSION_DEFAULT, is);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	@Override
	public void addFile(
			long companyId, long repositoryId, String fileName,
			boolean validateFileExtension, InputStream is)
		throws PortalException {

		if (is instanceof ByteArrayFileInputStream) {
			ByteArrayFileInputStream byteArrayFileInputStream =
				(ByteArrayFileInputStream)is;

			File file = byteArrayFileInputStream.getFile();

			addFile(
				companyId, repositoryId, fileName, validateFileExtension, file);

			return;
		}

		validate(fileName, validateFileExtension);

		Store store = _storeFactory.getStore();

		if (PropsValues.DL_STORE_ANTIVIRUS_ENABLED &&
			AntivirusScannerUtil.isActive()) {

			File tempFile = null;

			try {
				tempFile = FileUtil.createTempFile();

				FileUtil.write(tempFile, is);

				AntivirusScannerUtil.scan(tempFile);

				try (InputStream fis = new FileInputStream(tempFile)) {
					store.addFile(
						companyId, repositoryId, fileName,
						Store.VERSION_DEFAULT, fis);
				}
			}
			catch (IOException ioe) {
				throw new SystemException(
					"Unable to scan file " + fileName, ioe);
			}
			finally {
				if (tempFile != null) {
					tempFile.delete();
				}
			}
		}
		else {
			try {
				store.addFile(
					companyId, repositoryId, fileName, Store.VERSION_DEFAULT,
					is);
			}
			catch (AccessDeniedException ade) {
				throw new PrincipalException(ade);
			}
		}
	}

	@Override
	public void addFile(
			long companyId, long repositoryId, String fileName, byte[] bytes)
		throws PortalException {

		addFile(companyId, repositoryId, fileName, true, bytes);
	}

	@Override
	public void addFile(
			long companyId, long repositoryId, String fileName, File file)
		throws PortalException {

		addFile(companyId, repositoryId, fileName, true, file);
	}

	@Override
	public void addFile(
			long companyId, long repositoryId, String fileName, InputStream is)
		throws PortalException {

		addFile(companyId, repositoryId, fileName, true, is);
	}

	@Override
	public void copyFileVersion(
			long companyId, long repositoryId, String fileName,
			String fromVersionLabel, String toVersionLabel)
		throws PortalException {

		Store store = _storeFactory.getStore();

		InputStream is = store.getFileAsStream(
			companyId, repositoryId, fileName, fromVersionLabel);

		if (is == null) {
			is = new UnsyncByteArrayInputStream(new byte[0]);
		}

		store.updateFile(companyId, repositoryId, fileName, toVersionLabel, is);
	}

	@Override
	public void deleteDirectory(
		long companyId, long repositoryId, String dirName) {

		Store store = _storeFactory.getStore();

		store.deleteDirectory(companyId, repositoryId, dirName);
	}

	@Override
	public void deleteFile(long companyId, long repositoryId, String fileName)
		throws PortalException {

		validate(fileName, false);

		Store store = _storeFactory.getStore();

		store.deleteFile(companyId, repositoryId, fileName);
	}

	@Override
	public void deleteFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException {

		validate(fileName, false, versionLabel);

		Store store = _storeFactory.getStore();

		try {
			store.deleteFile(companyId, repositoryId, fileName, versionLabel);
		}
		catch (AccessDeniedException ade) {
			throw new PrincipalException(ade);
		}
	}

	@Override
	public byte[] getFileAsBytes(
			long companyId, long repositoryId, String fileName)
		throws PortalException {

		validate(fileName, false);

		Store store = _storeFactory.getStore();

		try {
			return StreamUtil.toByteArray(
				store.getFileAsStream(
					companyId, repositoryId, fileName, StringPool.BLANK));
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	@Override
	public byte[] getFileAsBytes(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException {

		validate(fileName, false, versionLabel);

		Store store = _storeFactory.getStore();

		try {
			return StreamUtil.toByteArray(
				store.getFileAsStream(
					companyId, repositoryId, fileName, versionLabel));
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	@Override
	public InputStream getFileAsStream(
			long companyId, long repositoryId, String fileName)
		throws PortalException {

		validate(fileName, false);

		Store store = _storeFactory.getStore();

		return store.getFileAsStream(
			companyId, repositoryId, fileName, StringPool.BLANK);
	}

	@Override
	public InputStream getFileAsStream(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException {

		validate(fileName, false, versionLabel);

		Store store = _storeFactory.getStore();

		return store.getFileAsStream(
			companyId, repositoryId, fileName, versionLabel);
	}

	@Override
	public String[] getFileNames(
			long companyId, long repositoryId, String dirName)
		throws PortalException {

		if (!DLValidatorUtil.isValidName(dirName)) {
			throw new DirectoryNameException(dirName);
		}

		Store store = _storeFactory.getStore();

		return store.getFileNames(companyId, repositoryId, dirName);
	}

	@Override
	public long getFileSize(long companyId, long repositoryId, String fileName)
		throws PortalException {

		validate(fileName, false);

		Store store = _storeFactory.getStore();

		return store.getFileSize(companyId, repositoryId, fileName);
	}

	@Override
	public boolean hasFile(long companyId, long repositoryId, String fileName)
		throws PortalException {

		validate(fileName, false);

		Store store = _storeFactory.getStore();

		return store.hasFile(
			companyId, repositoryId, fileName, Store.VERSION_DEFAULT);
	}

	@Override
	public boolean hasFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException {

		validate(fileName, false, versionLabel);

		Store store = _storeFactory.getStore();

		return store.hasFile(companyId, repositoryId, fileName, versionLabel);
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 *             DLValidatorUtil#isValidName(String)}
	 */
	@Deprecated
	@Override
	public boolean isValidName(String name) {
		return DLValidatorUtil.isValidName(name);
	}

	@Override
	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String newFileName)
		throws PortalException {

		Store store = _storeFactory.getStore();

		store.updateFile(companyId, repositoryId, fileName, newFileName);
	}

	@Override
	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String fileExtension, boolean validateFileExtension,
			String versionLabel, String sourceFileName, File file)
		throws PortalException {

		validate(
			fileName, fileExtension, sourceFileName, validateFileExtension);

		DLValidatorUtil.validateVersionLabel(versionLabel);

		if (PropsValues.DL_STORE_ANTIVIRUS_ENABLED) {
			AntivirusScannerUtil.scan(file);
		}

		Store store = _storeFactory.getStore();

		try (InputStream is = new FileInputStream(file)) {
			store.updateFile(
				companyId, repositoryId, fileName, versionLabel, is);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	@Override
	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String fileExtension, boolean validateFileExtension,
			String versionLabel, String sourceFileName, InputStream is)
		throws PortalException {

		validate(
			fileName, fileExtension, sourceFileName, validateFileExtension);

		if (is instanceof ByteArrayFileInputStream) {
			ByteArrayFileInputStream byteArrayFileInputStream =
				(ByteArrayFileInputStream)is;

			File file = byteArrayFileInputStream.getFile();

			DLValidatorUtil.validateVersionLabel(versionLabel);

			if (PropsValues.DL_STORE_ANTIVIRUS_ENABLED) {
				AntivirusScannerUtil.scan(file);
			}

			Store store = _storeFactory.getStore();

			store.updateFile(
				companyId, repositoryId, fileName, versionLabel, is);

			return;
		}

		DLValidatorUtil.validateVersionLabel(versionLabel);

		Store store = _storeFactory.getStore();

		if (PropsValues.DL_STORE_ANTIVIRUS_ENABLED &&
			AntivirusScannerUtil.isActive()) {

			File tempFile = null;

			try {
				tempFile = FileUtil.createTempFile();

				FileUtil.write(tempFile, is);

				AntivirusScannerUtil.scan(tempFile);

				try (InputStream fis = new FileInputStream(tempFile)) {
					store.updateFile(
						companyId, repositoryId, fileName, versionLabel, fis);
				}
			}
			catch (IOException ioe) {
				throw new SystemException(
					"Unable to scan file " + fileName, ioe);
			}
			finally {
				if (tempFile != null) {
					tempFile.delete();
				}
			}
		}
		else {
			try {
				store.updateFile(
					companyId, repositoryId, fileName, versionLabel, is);
			}
			catch (AccessDeniedException ade) {
				throw new PrincipalException(ade);
			}
		}
	}

	@Override
	public void updateFileVersion(
			long companyId, long repositoryId, String fileName,
			String fromVersionLabel, String toVersionLabel)
		throws PortalException {

		Store store = _storeFactory.getStore();

		InputStream is = store.getFileAsStream(
			companyId, repositoryId, fileName, fromVersionLabel);

		if (is == null) {
			is = new UnsyncByteArrayInputStream(new byte[0]);
		}

		store.updateFile(companyId, repositoryId, fileName, toVersionLabel, is);

		store.deleteFile(companyId, repositoryId, fileName, fromVersionLabel);
	}

	@Override
	public void validate(String fileName, boolean validateFileExtension)
		throws PortalException {

		DLValidatorUtil.validateFileName(fileName);

		if (validateFileExtension) {
			DLValidatorUtil.validateFileExtension(fileName);
		}
	}

	@Override
	public void validate(
			String fileName, boolean validateFileExtension, byte[] bytes)
		throws PortalException {

		validate(fileName, validateFileExtension);

		DLValidatorUtil.validateFileSize(fileName, bytes);
	}

	@Override
	public void validate(
			String fileName, boolean validateFileExtension, File file)
		throws PortalException {

		validate(fileName, validateFileExtension);

		DLValidatorUtil.validateFileSize(fileName, file);
	}

	@Override
	public void validate(
			String fileName, boolean validateFileExtension, InputStream is)
		throws PortalException {

		validate(fileName, validateFileExtension);

		DLValidatorUtil.validateFileSize(fileName, is);
	}

	@Override
	public void validate(
			String fileName, String fileExtension, String sourceFileName,
			boolean validateFileExtension)
		throws PortalException {

		validate(fileName, validateFileExtension);

		DLValidatorUtil.validateSourceFileExtension(
			fileExtension, sourceFileName);
	}

	@Override
	public void validate(
			String fileName, String fileExtension, String sourceFileName,
			boolean validateFileExtension, File file)
		throws PortalException {

		validate(
			fileName, fileExtension, sourceFileName, validateFileExtension);

		DLValidatorUtil.validateFileSize(fileName, file);
	}

	@Override
	public void validate(
			String fileName, String fileExtension, String sourceFileName,
			boolean validateFileExtension, InputStream is)
		throws PortalException {

		validate(
			fileName, fileExtension, sourceFileName, validateFileExtension);

		DLValidatorUtil.validateFileSize(fileName, is);
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 *             DLValidatorUtil#validateDirectoryName(String)}
	 */
	@Deprecated
	@Override
	public void validateDirectoryName(String directoryName)
		throws PortalException {

		DLValidatorUtil.validateDirectoryName(directoryName);
	}

	protected void validate(
			String fileName, boolean validateFileExtension, String versionLabel)
		throws PortalException {

		validate(fileName, validateFileExtension);

		DLValidatorUtil.validateVersionLabel(versionLabel);
	}

	protected void validate(
			String fileName, String fileExtension, String sourceFileName,
			boolean validateFileExtension, File file, String versionLabel)
		throws PortalException {

		validate(
			fileName, fileExtension, sourceFileName, validateFileExtension,
			file);

		DLValidatorUtil.validateVersionLabel(versionLabel);
	}

	protected void validate(
			String fileName, String fileExtension, String sourceFileName,
			boolean validateFileExtension, InputStream is, String versionLabel)
		throws PortalException {

		validate(
			fileName, fileExtension, sourceFileName, validateFileExtension, is);

		DLValidatorUtil.validateVersionLabel(versionLabel);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), with no direct replacement
	 */
	@Deprecated
	protected GroupLocalService groupLocalService;

	private final StoreFactory _storeFactory;

}