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

import com.liferay.document.library.kernel.store.Store;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.FileUtil;

import java.io.InputStream;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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

		if (!safeFileName.equals(fileName)) {
			_store.deleteFile(companyId, repositoryId, fileName, versionLabel);
		}

		_store.addFile(companyId, repositoryId, safeFileName, versionLabel, is);
	}

	@Override
	public void deleteDirectory(
		long companyId, long repositoryId, String dirName) {

		String safeDirName = FileUtil.encodeSafeFileName(dirName);

		if (!safeDirName.equals(dirName)) {
			_store.deleteDirectory(companyId, repositoryId, dirName);
		}

		_store.deleteDirectory(companyId, repositoryId, safeDirName);
	}

	@Override
	public void deleteFile(
		long companyId, long repositoryId, String fileName,
		String versionLabel) {

		String safeFileName = FileUtil.encodeSafeFileName(fileName);

		if (!safeFileName.equals(fileName)) {
			_store.deleteFile(companyId, repositoryId, fileName, versionLabel);
		}

		_store.deleteFile(companyId, repositoryId, safeFileName, versionLabel);
	}

	@Override
	public InputStream getFileAsStream(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException {

		String safeFileName = FileUtil.encodeSafeFileName(fileName);

		if (safeFileName.equals(fileName) ||
			_store.hasFile(
				companyId, repositoryId, safeFileName, versionLabel)) {

			return _store.getFileAsStream(
				companyId, repositoryId, safeFileName, versionLabel);
		}

		return _store.getFileAsStream(
			companyId, repositoryId, fileName, versionLabel);
	}

	@Override
	public String[] getFileNames(
		long companyId, long repositoryId, String dirName) {

		Set<String> decodedFileNameSet = new HashSet<>();

		String safeDirName = FileUtil.encodeSafeFileName(dirName);

		for (String fileName :
				_store.getFileNames(companyId, repositoryId, safeDirName)) {

			decodedFileNameSet.add(FileUtil.decodeSafeFileName(fileName));
		}

		if (!safeDirName.equals(dirName)) {
			for (String fileName :
					_store.getFileNames(companyId, repositoryId, dirName)) {

				decodedFileNameSet.add(FileUtil.decodeSafeFileName(fileName));
			}
		}

		String[] decodedFilesNames = decodedFileNameSet.toArray(new String[0]);

		Arrays.sort(decodedFilesNames);

		return decodedFilesNames;
	}

	@Override
	public long getFileSize(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException {

		String safeFileName = FileUtil.encodeSafeFileName(fileName);

		if (safeFileName.equals(fileName) ||
			_store.hasFile(
				companyId, repositoryId, safeFileName, versionLabel)) {

			return _store.getFileSize(
				companyId, repositoryId, safeFileName, versionLabel);
		}

		return _store.getFileSize(
			companyId, repositoryId, fileName, versionLabel);
	}

	@Override
	public String[] getFileVersions(
		long companyId, long repositoryId, String fileName) {

		Set<String> versionSet = new HashSet<>();

		String safeFileName = FileUtil.encodeSafeFileName(fileName);

		Collections.addAll(
			versionSet,
			_store.getFileVersions(companyId, repositoryId, safeFileName));

		if (!safeFileName.equals(fileName)) {
			Collections.addAll(
				versionSet,
				_store.getFileVersions(companyId, repositoryId, fileName));
		}

		String[] versions = versionSet.toArray(new String[0]);

		Arrays.sort(versions, DLUtil::compareVersions);

		return versions;
	}

	@Override
	public boolean hasFile(
		long companyId, long repositoryId, String fileName,
		String versionLabel) {

		String safeFileName = FileUtil.encodeSafeFileName(fileName);

		if (_store.hasFile(
				companyId, repositoryId, safeFileName, versionLabel)) {

			return true;
		}

		if (safeFileName.equals(fileName)) {
			return false;
		}

		return _store.hasFile(companyId, repositoryId, fileName, versionLabel);
	}

	private final Store _store;

}