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
import com.liferay.document.library.kernel.store.StoreArea;
import com.liferay.document.library.kernel.util.DLValidatorUtil;
import com.liferay.petra.io.StreamUtil;
import com.liferay.petra.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.io.ByteArrayFileInputStream;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GroupThreadLocal;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ServiceProxyFactory;
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

	public static void setStore(Store store) {
		_store = store;
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

		_store.addFile(
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

		try (InputStream inputStream = new FileInputStream(file)) {
			_store.addFile(
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

		if (PropsValues.DL_STORE_ANTIVIRUS_ENABLED &&
			AntivirusScannerUtil.isActive()) {

			File tempFile = null;

			try {
				tempFile = FileUtil.createTempFile();

				FileUtil.write(tempFile, inputStream1);

				AntivirusScannerUtil.scan(tempFile);

				try (InputStream inputStream2 = new FileInputStream(tempFile)) {
					_store.addFile(
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
				_store.addFile(
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
	public void copyFileVersion(
			long companyId, long repositoryId, String fileName,
			String fromVersionLabel, String toVersionLabel)
		throws PortalException {

		InputStream inputStream = _store.getFileAsStream(
			companyId, repositoryId, fileName, fromVersionLabel);

		if (inputStream == null) {
			inputStream = new UnsyncByteArrayInputStream(new byte[0]);
		}

		_store.addFile(
			companyId, repositoryId, fileName, toVersionLabel, inputStream);
	}

	@Override
	public void deleteDirectory(
			long companyId, long repositoryId, String dirName)
		throws PortalException {

		if (FeatureFlagManagerUtil.isEnabled("LPS-174816")) {
			for (String fileName :
					_store.getFileNames(companyId, repositoryId, dirName)) {

				for (String versionLabel :
						_store.getFileVersions(
							companyId, repositoryId, fileName)) {

					_copy(
						StoreArea.DELETED, companyId, repositoryId, fileName,
						versionLabel);
				}
			}
		}

		_store.deleteDirectory(companyId, repositoryId, dirName);
	}

	@Override
	public void deleteFile(long companyId, long repositoryId, String fileName)
		throws PortalException {

		validate(fileName, false);

		for (String versionLabel :
				_store.getFileVersions(companyId, repositoryId, fileName)) {

			if (FeatureFlagManagerUtil.isEnabled("LPS-174816")) {
				_copy(
					StoreArea.DELETED, companyId, repositoryId, fileName,
					versionLabel);
			}

			_store.deleteFile(companyId, repositoryId, fileName, versionLabel);
		}
	}

	@Override
	public void deleteFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException {

		validate(fileName, false, versionLabel);

		try {
			if (FeatureFlagManagerUtil.isEnabled("LPS-174816")) {
				_copy(
					StoreArea.DELETED, companyId, repositoryId, fileName,
					versionLabel);
			}

			_store.deleteFile(companyId, repositoryId, fileName, versionLabel);
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

		try {
			return StreamUtil.toByteArray(
				_store.getFileAsStream(
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

		try {
			return StreamUtil.toByteArray(
				_store.getFileAsStream(
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

		return _store.getFileAsStream(
			companyId, repositoryId, fileName, StringPool.BLANK);
	}

	@Override
	public InputStream getFileAsStream(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException {

		validate(fileName, false, versionLabel);

		return _store.getFileAsStream(
			companyId, repositoryId, fileName, versionLabel);
	}

	@Override
	public String[] getFileNames(
			long companyId, long repositoryId, String dirName)
		throws PortalException {

		if (!DLValidatorUtil.isValidName(dirName)) {
			throw new DirectoryNameException(dirName);
		}

		return _store.getFileNames(companyId, repositoryId, dirName);
	}

	@Override
	public long getFileSize(long companyId, long repositoryId, String fileName)
		throws PortalException {

		validate(fileName, false);

		return _store.getFileSize(
			companyId, repositoryId, fileName, StringPool.BLANK);
	}

	@Override
	public boolean hasFile(long companyId, long repositoryId, String fileName)
		throws PortalException {

		validate(fileName, false);

		return _store.hasFile(
			companyId, repositoryId, fileName, Store.VERSION_DEFAULT);
	}

	@Override
	public boolean hasFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException {

		validate(fileName, false, versionLabel);

		return _store.hasFile(companyId, repositoryId, fileName, versionLabel);
	}

	@Override
	public void updateFile(DLStoreRequest dlStoreRequest, File file)
		throws PortalException {

		validate(
			dlStoreRequest.getFileName(), dlStoreRequest.getFileExtension(),
			dlStoreRequest.getSourceFileName(),
			dlStoreRequest.isValidateFileExtension());

		_validateVersionLabel(dlStoreRequest.getVersionLabel());

		if (PropsValues.DL_STORE_ANTIVIRUS_ENABLED) {
			AntivirusScannerUtil.scan(file);
		}

		try (InputStream inputStream = new FileInputStream(file)) {
			_store.addFile(
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

		_validateVersionLabel(dlStoreRequest.getVersionLabel());

		if (inputStream1 instanceof ByteArrayFileInputStream) {
			ByteArrayFileInputStream byteArrayFileInputStream =
				(ByteArrayFileInputStream)inputStream1;

			if (PropsValues.DL_STORE_ANTIVIRUS_ENABLED) {
				AntivirusScannerUtil.scan(byteArrayFileInputStream.getFile());
			}

			_store.addFile(
				dlStoreRequest.getCompanyId(), dlStoreRequest.getRepositoryId(),
				dlStoreRequest.getFileName(), dlStoreRequest.getVersionLabel(),
				inputStream1);

			return;
		}

		if (PropsValues.DL_STORE_ANTIVIRUS_ENABLED &&
			AntivirusScannerUtil.isActive()) {

			File tempFile = null;

			try {
				tempFile = FileUtil.createTempFile();

				FileUtil.write(tempFile, inputStream1);

				AntivirusScannerUtil.scan(tempFile);

				try (InputStream inputStream = new FileInputStream(tempFile)) {
					_store.addFile(
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
				_store.addFile(
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

		for (String versionLabel :
				_store.getFileVersions(companyId, repositoryId, fileName)) {

			_store.addFile(
				companyId, newRepositoryId, fileName, versionLabel,
				_store.getFileAsStream(
					companyId, repositoryId, fileName, versionLabel));

			if (FeatureFlagManagerUtil.isEnabled("LPS-174816")) {
				_copy(
					StoreArea.DELETED, companyId, repositoryId, fileName,
					versionLabel);
			}

			_store.deleteFile(companyId, repositoryId, fileName, versionLabel);
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

		InputStream inputStream = _store.getFileAsStream(
			companyId, repositoryId, fileName, fromVersionLabel);

		if (inputStream == null) {
			inputStream = new UnsyncByteArrayInputStream(new byte[0]);
		}

		_store.addFile(
			companyId, repositoryId, fileName, toVersionLabel, inputStream);

		if (FeatureFlagManagerUtil.isEnabled("LPS-174816")) {
			_copy(
				StoreArea.DELETED, companyId, repositoryId, fileName,
				fromVersionLabel);
		}

		_store.deleteFile(companyId, repositoryId, fileName, fromVersionLabel);
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
			GroupThreadLocal.getGroupId(), fileName,
			MimeTypesUtil.getContentType(fileName), bytes);
	}

	@Override
	public void validate(
			String fileName, boolean validateFileExtension, File file)
		throws PortalException {

		validate(fileName, validateFileExtension);

		DLValidatorUtil.validateFileSize(
			GroupThreadLocal.getGroupId(), fileName,
			MimeTypesUtil.getContentType(fileName), file);
	}

	@Override
	public void validate(
			String fileName, boolean validateFileExtension,
			InputStream inputStream)
		throws PortalException {

		validate(fileName, validateFileExtension);

		DLValidatorUtil.validateFileSize(
			GroupThreadLocal.getGroupId(), fileName,
			MimeTypesUtil.getContentType(fileName), inputStream);
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
			GroupThreadLocal.getGroupId(), fileName,
			MimeTypesUtil.getContentType(fileName), file);
	}

	@Override
	public void validate(
			String fileName, String fileExtension, String sourceFileName,
			boolean validateFileExtension, InputStream inputStream)
		throws PortalException {

		validate(
			fileName, fileExtension, sourceFileName, validateFileExtension);

		DLValidatorUtil.validateFileSize(
			GroupThreadLocal.getGroupId(), fileName,
			MimeTypesUtil.getContentType(fileName), inputStream);
	}

	protected void validate(
			String fileName, boolean validateFileExtension, String versionLabel)
		throws PortalException {

		validate(fileName, validateFileExtension);

		_validateVersionLabel(versionLabel);
	}

	protected void validate(
			String fileName, String fileExtension, String sourceFileName,
			boolean validateFileExtension, File file, String versionLabel)
		throws PortalException {

		validate(
			fileName, fileExtension, sourceFileName, validateFileExtension,
			file);

		_validateVersionLabel(versionLabel);
	}

	protected void validate(
			String fileName, String fileExtension, String sourceFileName,
			boolean validateFileExtension, InputStream inputStream,
			String versionLabel)
		throws PortalException {

		validate(
			fileName, fileExtension, sourceFileName, validateFileExtension,
			inputStream);

		_validateVersionLabel(versionLabel);
	}

	private void _copy(
			StoreArea storeArea, long companyId, long repositoryId,
			String fileName, String versionLabel)
		throws PortalException {

		try (InputStream inputStream = _store.getFileAsStream(
				companyId, repositoryId, fileName, versionLabel)) {

			StoreArea.withStoreArea(
				storeArea,
				() -> _store.addFile(
					companyId, repositoryId, fileName, versionLabel,
					inputStream));
		}
		catch (IOException ioException) {
			throw new SystemException(ioException);
		}
	}

	private void _validateVersionLabel(String versionLabel)
		throws PortalException {

		DLValidatorUtil.validateVersionLabel(versionLabel);
	}

	private static volatile Store _store =
		ServiceProxyFactory.newServiceTrackedInstance(
			Store.class, DLStoreImpl.class, "_store", "(default=true)", true);

}