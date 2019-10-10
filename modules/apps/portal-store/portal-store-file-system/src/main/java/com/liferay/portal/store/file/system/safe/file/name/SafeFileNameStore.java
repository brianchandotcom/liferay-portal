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

package com.liferay.portal.store.file.system.safe.file.name;

import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.store.Store;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;

import java.io.InputStream;

/**
 * @author Roberto Díaz
 */
public class SafeFileNameStore implements Store {

	public SafeFileNameStore(Store store) {
		_store = store;
	}

	@Override
	public void addFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel, InputStream is)
		throws PortalException {

		String safeFileName = FileUtil.encodeSafeFileName(fileName);

		renameUnsafeFile(companyId, repositoryId, fileName, safeFileName);

		_store.addFile(companyId, repositoryId, safeFileName, versionLabel, is);
	}

	@Override
	public void deleteDirectory(
		long companyId, long repositoryId, String dirName) {

		String safeDirName = FileUtil.encodeSafeFileName(dirName);

		if (!safeDirName.equals(dirName)) {
			try {
				_store.deleteDirectory(companyId, repositoryId, dirName);

				return;
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug(e, e);
				}
			}
		}

		_store.deleteDirectory(companyId, repositoryId, safeDirName);
	}

	@Override
	public void deleteFile(long companyId, long repositoryId, String fileName) {
		String safeFileName = FileUtil.encodeSafeFileName(fileName);

		if (!safeFileName.equals(fileName) &&
			_store.hasFile(
				companyId, repositoryId, fileName, Store.VERSION_DEFAULT)) {

			_store.deleteFile(companyId, repositoryId, fileName);

			return;
		}

		_store.deleteFile(companyId, repositoryId, safeFileName);
	}

	@Override
	public void deleteFile(
		long companyId, long repositoryId, String fileName,
		String versionLabel) {

		String safeFileName = FileUtil.encodeSafeFileName(fileName);

		if (!safeFileName.equals(fileName) &&
			_store.hasFile(companyId, repositoryId, fileName, versionLabel)) {

			_store.deleteFile(companyId, repositoryId, fileName, versionLabel);

			return;
		}

		_store.deleteFile(companyId, repositoryId, safeFileName, versionLabel);
	}

	@Override
	public InputStream getFileAsStream(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException {

		String safeFileName = FileUtil.encodeSafeFileName(fileName);

		if (!safeFileName.equals(fileName) &&
			_store.hasFile(companyId, repositoryId, fileName, versionLabel)) {

			return _store.getFileAsStream(
				companyId, repositoryId, fileName, versionLabel);
		}

		return _store.getFileAsStream(
			companyId, repositoryId, safeFileName, versionLabel);
	}

	@Override
	public String[] getFileNames(long companyId, long repositoryId) {
		String[] fileNames = _store.getFileNames(companyId, repositoryId);

		String[] decodedFileNames = new String[fileNames.length];

		for (int i = 0; i < fileNames.length; i++) {
			decodedFileNames[i] = FileUtil.decodeSafeFileName(fileNames[i]);
		}

		return decodedFileNames;
	}

	@Override
	public String[] getFileNames(
		long companyId, long repositoryId, String dirName) {

		String safeDirName = FileUtil.encodeSafeFileName(dirName);

		String[] fileNames = _store.getFileNames(
			companyId, repositoryId, safeDirName);

		String[] decodedFileNames = new String[fileNames.length];

		for (int i = 0; i < fileNames.length; i++) {
			decodedFileNames[i] = FileUtil.decodeSafeFileName(fileNames[i]);
		}

		return decodedFileNames;
	}

	@Override
	public long getFileSize(long companyId, long repositoryId, String fileName)
		throws PortalException {

		String safeFileName = FileUtil.encodeSafeFileName(fileName);

		if (!safeFileName.equals(fileName) &&
			_store.hasFile(
				companyId, repositoryId, fileName, Store.VERSION_DEFAULT)) {

			return _store.getFileSize(companyId, repositoryId, fileName);
		}

		return _store.getFileSize(companyId, repositoryId, safeFileName);
	}

	@Override
	public boolean hasFile(
		long companyId, long repositoryId, String fileName,
		String versionLabel) {

		String safeFileName = FileUtil.encodeSafeFileName(fileName);

		if (!safeFileName.equals(fileName) &&
			_store.hasFile(companyId, repositoryId, fileName, versionLabel)) {

			return true;
		}

		return _store.hasFile(
			companyId, repositoryId, safeFileName, versionLabel);
	}

	@Override
	public void updateFile(
			long companyId, long repositoryId, String fileName,
			String newFileName)
		throws PortalException {

		String safeFileName = FileUtil.encodeSafeFileName(fileName);
		String safeNewFileName = FileUtil.encodeSafeFileName(newFileName);

		if (!safeFileName.equals(fileName) &&
			_store.hasFile(
				companyId, repositoryId, fileName,
				DLFileEntryConstants.VERSION_DEFAULT)) {

			safeFileName = fileName;
		}

		_store.updateFile(
			companyId, repositoryId, safeFileName, safeNewFileName);
	}

	protected void renameUnsafeFile(
			long companyId, long repositoryId, String fileName,
			String safeFileName)
		throws PortalException {

		if (!safeFileName.equals(fileName) &&
			_store.hasFile(
				companyId, repositoryId, fileName,
				DLFileEntryConstants.VERSION_DEFAULT)) {

			_store.updateFile(companyId, repositoryId, fileName, safeFileName);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SafeFileNameStore.class);

	private final Store _store;

}