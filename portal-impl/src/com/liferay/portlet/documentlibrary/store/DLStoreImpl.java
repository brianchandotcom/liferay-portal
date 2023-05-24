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
import com.liferay.document.library.kernel.store.StoreAreaProcessor;
import com.liferay.document.library.kernel.util.DLValidatorUtil;
import com.liferay.petra.io.StreamUtil;
import com.liferay.petra.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.petra.lang.SafeCloseable;
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
import java.io.FileNotFoundException;
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

		try (DLStoreFileProvider dlStoreFileProvider = new DLStoreFileProvider(
				bytes)) {

			validate(
				dlStoreRequest.getFileName(),
				dlStoreRequest.isValidateFileExtension());

			_addFile(dlStoreRequest, dlStoreFileProvider);
		}
	}

	@Override
	public void addFile(DLStoreRequest dlStoreRequest, File file)
		throws PortalException {

		try (DLStoreFileProvider dlStoreFileProvider = new DLStoreFileProvider(
				file)) {

			validate(
				dlStoreRequest.getFileName(),
				dlStoreRequest.isValidateFileExtension());

			_addFile(dlStoreRequest, dlStoreFileProvider);
		}
	}

	@Override
	public void addFile(DLStoreRequest dlStoreRequest, InputStream inputStream)
		throws PortalException {

		try (DLStoreFileProvider dlStoreFileProvider = new DLStoreFileProvider(
				inputStream)) {

			validate(
				dlStoreRequest.getFileName(),
				dlStoreRequest.isValidateFileExtension());

			_addFile(dlStoreRequest, dlStoreFileProvider);
		}
	}

	@Override
	public void copyFileVersion(
			long companyId, long repositoryId, String fileName,
			String fromVersionLabel, String toVersionLabel)
		throws PortalException {

		_store.addFile(
			companyId, repositoryId, fileName, toVersionLabel,
			_getNullSafeInputStream(
				_store.getFileAsStream(
					companyId, repositoryId, fileName, fromVersionLabel)));
	}

	@Override
	public void deleteDirectory(
			long companyId, long repositoryId, String dirName)
		throws PortalException {

		if (_isStoreAreaSupported()) {
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

			if (_isStoreAreaSupported()) {
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
			if (_isStoreAreaSupported()) {
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

		try (DLStoreFileProvider dlStoreFileProvider = new DLStoreFileProvider(
				file)) {

			validate(
				dlStoreRequest.getFileName(), dlStoreRequest.getFileExtension(),
				dlStoreRequest.getSourceFileName(),
				dlStoreRequest.isValidateFileExtension());

			_validateVersionLabel(dlStoreRequest.getVersionLabel());

			_addFile(dlStoreRequest, dlStoreFileProvider);
		}
	}

	@Override
	public void updateFile(
			DLStoreRequest dlStoreRequest, InputStream inputStream)
		throws PortalException {

		try (DLStoreFileProvider dlStoreFileProvider = new DLStoreFileProvider(
				inputStream)) {

			validate(
				dlStoreRequest.getFileName(), dlStoreRequest.getFileExtension(),
				dlStoreRequest.getSourceFileName(),
				dlStoreRequest.isValidateFileExtension());

			_validateVersionLabel(dlStoreRequest.getVersionLabel());

			_addFile(dlStoreRequest, dlStoreFileProvider);
		}
	}

	@Override
	public void updateFile(
			long companyId, long repositoryId, long newRepositoryId,
			String fileName)
		throws PortalException {

		for (String versionLabel :
				_store.getFileVersions(companyId, repositoryId, fileName)) {

			if (_isStoreAreaSupported()) {
				_copy(
					StoreArea.DELETED, companyId, repositoryId, fileName,
					versionLabel);
			}

			_store.deleteFile(companyId, repositoryId, fileName, versionLabel);
		}
	}

	@Override
	public void updateFileVersion(
			long companyId, long repositoryId, String fileName,
			String fromVersionLabel, String toVersionLabel)
		throws PortalException {

		_store.addFile(
			companyId, repositoryId, fileName, toVersionLabel,
			_getNullSafeInputStream(
				_store.getFileAsStream(
					companyId, repositoryId, fileName, fromVersionLabel)));

		if (_isStoreAreaSupported()) {
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

	private void _addFile(
			DLStoreRequest dlStoreRequest,
			DLStoreFileProvider dlStoreFileProvider)
		throws PortalException {

		try {
			if (PropsValues.DL_STORE_ANTIVIRUS_ENABLED &&
				AntivirusScannerUtil.isActive()) {

				AntivirusScannerUtil.scan(dlStoreFileProvider.getFile());
			}

			try (InputStream inputStream =
					dlStoreFileProvider.getInputStream()) {

				_store.addFile(
					dlStoreRequest.getCompanyId(),
					dlStoreRequest.getRepositoryId(),
					dlStoreRequest.getFileName(),
					dlStoreRequest.getVersionLabel(), inputStream);
			}
		}
		catch (AccessDeniedException accessDeniedException) {
			throw new PrincipalException(accessDeniedException);
		}
		catch (IOException ioException) {
			throw new SystemException(
				"Unable to scan file " + dlStoreRequest.getFileName(),
				ioException);
		}
	}

	private void _copy(
			StoreArea storeArea, long companyId, long repositoryId,
			String fileName, String versionLabel)
		throws PortalException {

		try (InputStream inputStream = _getNullSafeInputStream(
				_store.getFileAsStream(
					companyId, repositoryId, fileName, versionLabel))) {

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

	private InputStream _getNullSafeInputStream(InputStream inputStream) {
		if (inputStream == null) {
			return new UnsyncByteArrayInputStream(new byte[0]);
		}

		return inputStream;
	}

	private boolean _isStoreAreaSupported() {
		if (FeatureFlagManagerUtil.isEnabled("LPS-174816") &&
			(_storeAreaProcessor != null)) {

			return true;
		}

		return false;
	}

	private void _validateVersionLabel(String versionLabel)
		throws PortalException {

		DLValidatorUtil.validateVersionLabel(versionLabel);
	}

	private static volatile Store _store =
		ServiceProxyFactory.newServiceTrackedInstance(
			Store.class, DLStoreImpl.class, "_store", "(default=true)", true);
	private static volatile StoreAreaProcessor _storeAreaProcessor =
		ServiceProxyFactory.newServiceTrackedInstance(
			StoreAreaProcessor.class, DLStoreImpl.class, "_storeAreaProcessor",
			"(default=true)", false, true);

	private static class DLStoreFileProvider implements SafeCloseable {

		public DLStoreFileProvider(byte[] bytes) {
			this(bytes, null, null);
		}

		public DLStoreFileProvider(File file) {
			this(null, file, null);
		}

		public DLStoreFileProvider(InputStream inputStream) {
			this(null, null, inputStream);
		}

		@Override
		public void close() {
			if (_tempFile != null) {
				_tempFile.delete();
			}
		}

		public File getFile() throws IOException {
			if (_file != null) {
				return _file;
			}

			if (_tempFile != null) {
				return _tempFile;
			}

			if (_bytes != null) {
				_tempFile = FileUtil.createTempFile(_bytes);
			}
			else {
				_tempFile = FileUtil.createTempFile(_inputStream);
			}

			return _tempFile;
		}

		public InputStream getInputStream() throws FileNotFoundException {
			if (_file != null) {
				return new FileInputStream(_file);
			}

			if (_tempFile != null) {
				return new FileInputStream(_tempFile);
			}

			if (_bytes != null) {
				return new UnsyncByteArrayInputStream(_bytes);
			}

			return _inputStream;
		}

		private DLStoreFileProvider(
			byte[] bytes, File file, InputStream inputStream) {

			if (inputStream instanceof ByteArrayFileInputStream) {
				_bytes = null;

				ByteArrayFileInputStream byteArrayFileInputStream =
					(ByteArrayFileInputStream)inputStream;

				_file = byteArrayFileInputStream.getFile();

				_inputStream = null;
			}
			else {
				_bytes = bytes;
				_file = file;
				_inputStream = inputStream;
			}
		}

		private final byte[] _bytes;
		private final File _file;
		private final InputStream _inputStream;
		private File _tempFile;

	}

}