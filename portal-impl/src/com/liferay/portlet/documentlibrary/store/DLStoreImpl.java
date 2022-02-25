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
import com.liferay.document.library.kernel.store.DLStoreRequest;
import com.liferay.document.library.kernel.store.Store;
import com.liferay.document.library.kernel.util.DLValidatorUtil;
import com.liferay.petra.io.StreamUtil;
import com.liferay.petra.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.ByteArrayFileInputStream;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 * @author Edward Han
 * @author Raymond Augé
 */
public class DLStoreImpl implements DLStore {

	public DLStoreImpl() {
		_storeFactory = StoreFactory.getInstance();
	}

	@Override
	public void addFile(DLStoreRequest dlStoreRequest, byte[] bytes)
		throws PortalException {

		validate(
			dlStoreRequest.getFileName(),
			dlStoreRequest.isValidateFileExtension());

		if (PropsValues.DL_STORE_ANTIVIRUS_ENABLED) {
			AntivirusScannerUtil.scan(bytes);
		}

		Store store = _storeFactory.getStore();

		store.addFile(
			dlStoreRequest.getCompanyId(), dlStoreRequest.getRepositoryId(),
			dlStoreRequest.getFileName(), dlStoreRequest.getVersionLabel(),
			new UnsyncByteArrayInputStream(bytes));
	}

	@Override
	public void addFile(DLStoreRequest dlStoreRequest, File file)
		throws PortalException {

		validate(
			dlStoreRequest.getFileName(),
			dlStoreRequest.isValidateFileExtension());

		if (PropsValues.DL_STORE_ANTIVIRUS_ENABLED) {
			AntivirusScannerUtil.scan(file);
		}

		Store store = _storeFactory.getStore();

		try (InputStream inputStream = new FileInputStream(file)) {
			store.addFile(
				dlStoreRequest.getCompanyId(), dlStoreRequest.getRepositoryId(),
				dlStoreRequest.getFileName(), dlStoreRequest.getVersionLabel(),
				inputStream);
		}
		catch (IOException ioException) {
			throw new SystemException(ioException);
		}
	}

	@Override
	public void addFile(DLStoreRequest dlStoreRequest, InputStream inputStream1)
		throws PortalException {

		if (inputStream1 instanceof ByteArrayFileInputStream) {
			ByteArrayFileInputStream byteArrayFileInputStream =
				(ByteArrayFileInputStream)inputStream1;

			addFile(dlStoreRequest, byteArrayFileInputStream.getFile());

			return;
		}

		validate(
			dlStoreRequest.getFileName(),
			dlStoreRequest.isValidateFileExtension());

		Store store = _storeFactory.getStore();

		if (PropsValues.DL_STORE_ANTIVIRUS_ENABLED &&
			AntivirusScannerUtil.isActive()) {

			File tempFile = null;

			try {
				tempFile = FileUtil.createTempFile();

				FileUtil.write(tempFile, inputStream1);

				AntivirusScannerUtil.scan(tempFile);

				try (InputStream inputStream2 = new FileInputStream(tempFile)) {
					store.addFile(
						dlStoreRequest.getCompanyId(),
						dlStoreRequest.getRepositoryId(),
						dlStoreRequest.getFileName(),
						dlStoreRequest.getVersionLabel(), inputStream2);
				}
			}
			catch (IOException ioException) {
				throw new SystemException(
					"Unable to scan file " + dlStoreRequest.getFileName(),
					ioException);
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
					dlStoreRequest.getCompanyId(),
					dlStoreRequest.getRepositoryId(),
					dlStoreRequest.getFileName(),
					dlStoreRequest.getVersionLabel(), inputStream1);
			}
			catch (AccessDeniedException accessDeniedException) {
				throw new PrincipalException(accessDeniedException);
			}
		}
	}

	@Override
	public void addFile(
			long companyId, long repositoryId, String fileName,
			boolean validateFileExtension, byte[] bytes)
		throws PortalException {

		addFile(
			DLStoreRequest.builder(
				companyId, repositoryId, fileName
			).validateFileExtension(
				validateFileExtension
			).build(),
			bytes);
	}

	@Override
	public void addFile(
			long companyId, long repositoryId, String fileName,
			boolean validateFileExtension, File file)
		throws PortalException {

		addFile(
			DLStoreRequest.builder(
				companyId, repositoryId, fileName
			).validateFileExtension(
				validateFileExtension
			).build(),
			file);
	}

	@Override
	public void addFile(
			long companyId, long repositoryId, String fileName,
			boolean validateFileExtension, InputStream inputStream)
		throws PortalException {

		addFile(
			DLStoreRequest.builder(
				companyId, repositoryId, fileName
			).validateFileExtension(
				validateFileExtension
			).build(),
			inputStream);
	}

	@Override
	public void addFile(
			long companyId, long repositoryId, String fileName, byte[] bytes)
		throws PortalException {

		addFile(
			DLStoreRequest.builder(
				companyId, repositoryId, fileName
			).validateFileExtension(
				true
			).build(),
			bytes);
	}

	@Override
	public void addFile(
			long companyId, long repositoryId, String fileName, File file)
		throws PortalException {

		addFile(
			DLStoreRequest.builder(
				companyId, repositoryId, fileName
			).validateFileExtension(
				true
			).build(),
			file);
	}

	@Override
	public void addFile(
			long companyId, long repositoryId, String fileName,
			InputStream inputStream)
		throws PortalException {

		addFile(
			DLStoreRequest.builder(
				companyId, repositoryId, fileName
			).validateFileExtension(
				true
			).build(),
			inputStream);
	}

	@Override
	public void copyFileVersion(
			long companyId, long repositoryId, String fileName,
			String fromVersionLabel, String toVersionLabel)
		throws PortalException {

		Store store = _storeFactory.getStore();

		InputStream inputStream = store.getFileAsStream(
			companyId, repositoryId, fileName, fromVersionLabel);

		if (inputStream == null) {
			inputStream = new UnsyncByteArrayInputStream(new byte[0]);
		}

		store.addFile(
			companyId, repositoryId, fileName, toVersionLabel, inputStream);
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

		for (String versionLabel :
				store.getFileVersions(companyId, repositoryId, fileName)) {

			store.deleteFile(companyId, repositoryId, fileName, versionLabel);
		}
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
		catch (AccessDeniedException accessDeniedException) {
			throw new PrincipalException(accessDeniedException);
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
		catch (IOException ioException) {
			throw new SystemException(ioException);
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
		catch (IOException ioException) {
			throw new SystemException(ioException);
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

		return store.getFileSize(
			companyId, repositoryId, fileName, StringPool.BLANK);
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

	@Override
	public void updateFile(DLStoreRequest dlStoreRequest, File file)
		throws PortalException {

		validate(
			dlStoreRequest.getFileName(), dlStoreRequest.getFileExtension(),
			dlStoreRequest.getSourceFileName(),
			dlStoreRequest.isValidateFileExtension());

		DLValidatorUtil.validateVersionLabel(dlStoreRequest.getVersionLabel());

		if (PropsValues.DL_STORE_ANTIVIRUS_ENABLED) {
			AntivirusScannerUtil.scan(file);
		}

		Store store = _storeFactory.getStore();

		try (InputStream inputStream = new FileInputStream(file)) {
			store.addFile(
				dlStoreRequest.getCompanyId(), dlStoreRequest.getRepositoryId(),
				dlStoreRequest.getFileName(), dlStoreRequest.getVersionLabel(),
				inputStream);
		}
		catch (IOException ioException) {
			throw new SystemException(ioException);
		}
	}

	@Override
	public void updateFile(
			DLStoreRequest dlStoreRequest, InputStream inputStream1)
		throws PortalException {

		validate(
			dlStoreRequest.getFileName(), dlStoreRequest.getFileExtension(),
			dlStoreRequest.getSourceFileName(),
			dlStoreRequest.isValidateFileExtension());

		if (inputStream1 instanceof ByteArrayFileInputStream) {
			ByteArrayFileInputStream byteArrayFileInputStream =
				(ByteArrayFileInputStream)inputStream1;

			DLValidatorUtil.validateVersionLabel(
				dlStoreRequest.getVersionLabel());

			if (PropsValues.DL_STORE_ANTIVIRUS_ENABLED) {
				AntivirusScannerUtil.scan(byteArrayFileInputStream.getFile());
			}

			Store store = _storeFactory.getStore();

			store.addFile(
				dlStoreRequest.getCompanyId(), dlStoreRequest.getRepositoryId(),
				dlStoreRequest.getFileName(), dlStoreRequest.getVersionLabel(),
				inputStream1);

			return;
		}

		DLValidatorUtil.validateVersionLabel(dlStoreRequest.getVersionLabel());

		Store store = _storeFactory.getStore();

		if (PropsValues.DL_STORE_ANTIVIRUS_ENABLED &&
			AntivirusScannerUtil.isActive()) {

			File tempFile = null;

			try {
				tempFile = FileUtil.createTempFile();

				FileUtil.write(tempFile, inputStream1);

				AntivirusScannerUtil.scan(tempFile);

				try (InputStream inputStream = new FileInputStream(tempFile)) {
					store.addFile(
						dlStoreRequest.getCompanyId(),
						dlStoreRequest.getRepositoryId(),
						dlStoreRequest.getFileName(),
						dlStoreRequest.getVersionLabel(), inputStream);
				}
			}
			catch (IOException ioException) {
				throw new SystemException(
					"Unable to scan file " + dlStoreRequest.getFileName(),
					ioException);
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
					dlStoreRequest.getCompanyId(),
					dlStoreRequest.getRepositoryId(),
					dlStoreRequest.getFileName(),
					dlStoreRequest.getVersionLabel(), inputStream1);
			}
			catch (AccessDeniedException accessDeniedException) {
				throw new PrincipalException(accessDeniedException);
			}
		}
	}

	@Override
	public void updateFile(
			long companyId, long repositoryId, long newRepositoryId,
			String fileName)
		throws PortalException {

		Store store = _storeFactory.getStore();

		for (String versionLabel :
				store.getFileVersions(companyId, repositoryId, fileName)) {

			store.addFile(
				companyId, newRepositoryId, fileName, versionLabel,
				store.getFileAsStream(
					companyId, repositoryId, fileName, versionLabel));

			store.deleteFile(companyId, repositoryId, fileName, versionLabel);
		}
	}

	@Override
	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String fileExtension, boolean validateFileExtension,
			String versionLabel, String sourceFileName, File file)
		throws PortalException {

		updateFile(
			DLStoreRequest.builder(
				companyId, repositoryId, fileName
			).fileExtension(
				fileExtension
			).sourceFileName(
				sourceFileName
			).validateFileExtension(
				validateFileExtension
			).versionLabel(
				versionLabel
			).build(),
			file);
	}

	@Override
	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String fileExtension, boolean validateFileExtension,
			String versionLabel, String sourceFileName,
			InputStream inputStream1)
		throws PortalException {

		updateFile(
			DLStoreRequest.builder(
				companyId, repositoryId, fileName
			).fileExtension(
				fileExtension
			).sourceFileName(
				sourceFileName
			).validateFileExtension(
				validateFileExtension
			).versionLabel(
				versionLabel
			).build(),
			inputStream1);
	}

	@Override
	public void updateFileVersion(
			long companyId, long repositoryId, String fileName,
			String fromVersionLabel, String toVersionLabel)
		throws PortalException {

		Store store = _storeFactory.getStore();

		InputStream inputStream = store.getFileAsStream(
			companyId, repositoryId, fileName, fromVersionLabel);

		if (inputStream == null) {
			inputStream = new UnsyncByteArrayInputStream(new byte[0]);
		}

		store.addFile(
			companyId, repositoryId, fileName, toVersionLabel, inputStream);

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

		DLValidatorUtil.validateFileSize(
			fileName, MimeTypesUtil.getContentType(fileName), bytes);
	}

	@Override
	public void validate(
			String fileName, boolean validateFileExtension, File file)
		throws PortalException {

		validate(fileName, validateFileExtension);

		DLValidatorUtil.validateFileSize(
			fileName, MimeTypesUtil.getContentType(fileName), file);
	}

	@Override
	public void validate(
			String fileName, boolean validateFileExtension,
			InputStream inputStream)
		throws PortalException {

		validate(fileName, validateFileExtension);

		DLValidatorUtil.validateFileSize(
			fileName, MimeTypesUtil.getContentType(fileName), inputStream);
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

		DLValidatorUtil.validateFileSize(
			fileName, MimeTypesUtil.getContentType(fileName), file);
	}

	@Override
	public void validate(
			String fileName, String fileExtension, String sourceFileName,
			boolean validateFileExtension, InputStream inputStream)
		throws PortalException {

		validate(
			fileName, fileExtension, sourceFileName, validateFileExtension);

		DLValidatorUtil.validateFileSize(
			fileName, MimeTypesUtil.getContentType(fileName), inputStream);
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
			boolean validateFileExtension, InputStream inputStream,
			String versionLabel)
		throws PortalException {

		validate(
			fileName, fileExtension, sourceFileName, validateFileExtension,
			inputStream);

		DLValidatorUtil.validateVersionLabel(versionLabel);
	}

	private final StoreFactory _storeFactory;

}