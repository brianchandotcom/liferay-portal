/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.store.file.system.lenient;

import com.liferay.document.library.kernel.store.Store;
import com.liferay.petra.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.exception.PortalException;

import java.io.InputStream;

/**
 * @author Adolfo Pérez
 */
public class LenientStore implements Store {

	public LenientStore(Store store) {
		_store = store;
	}

	@Override
	public void addFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel, InputStream inputStream)
		throws PortalException {

		_store.addFile(
			companyId, repositoryId, fileName, versionLabel, inputStream);
	}

	@Override
	public void deleteDirectory(
		long companyId, long repositoryId, String dirName) {

		_store.deleteDirectory(companyId, repositoryId, dirName);
	}

	@Override
	public void deleteFile(
		long companyId, long repositoryId, String fileName,
		String versionLabel) {

		_store.deleteFile(companyId, repositoryId, fileName, versionLabel);
	}

	@Override
	public InputStream getFileAsStream(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException {

		if (!_store.hasFile(companyId, repositoryId, fileName, versionLabel)) {
			_store.addFile(
				companyId, repositoryId, fileName, versionLabel,
				new UnsyncByteArrayInputStream(_DUMMY_CONTENT));
		}

		return _store.getFileAsStream(
			companyId, repositoryId, fileName, versionLabel);
	}

	@Override
	public String[] getFileNames(
		long companyId, long repositoryId, String dirName) {

		return _store.getFileNames(companyId, repositoryId, dirName);
	}

	@Override
	public long getFileSize(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException {

		if (!_store.hasFile(companyId, repositoryId, fileName, versionLabel)) {
			_store.addFile(
				companyId, repositoryId, fileName, versionLabel,
				new UnsyncByteArrayInputStream(_DUMMY_CONTENT));
		}

		return _store.getFileSize(
			companyId, repositoryId, fileName, versionLabel);
	}

	@Override
	public String[] getFileVersions(
		long companyId, long repositoryId, String fileName) {

		return _store.getFileVersions(companyId, repositoryId, fileName);
	}

	@Override
	public boolean hasFile(
		long companyId, long repositoryId, String fileName,
		String versionLabel) {

		return true;
	}

	private static final byte[] _DUMMY_CONTENT =
		"This is a test file.".getBytes();

	private final Store _store;

}