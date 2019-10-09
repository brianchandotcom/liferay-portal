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

package com.liferay.portal.store.file.system.ignore.duplicates;

import com.liferay.document.library.kernel.exception.DuplicateFileException;
import com.liferay.document.library.kernel.store.Store;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.InputStream;

/**
 * @author Adolfo Pérez
 */
public class IgnoreDuplicatesStore implements Store {

	public IgnoreDuplicatesStore(Store store) {
		_store = store;
	}

	@Override
	public void addDirectory(
		long companyId, long repositoryId, String dirName) {

		_store.addDirectory(companyId, repositoryId, dirName);
	}

	@Override
	public void addFile(
			final long companyId, final long repositoryId,
			final String fileName, final InputStream is)
		throws PortalException {

		recoverAndRetryOnFailure(
			createDeleteFileStoreAction(
				companyId, repositoryId, fileName, Store.VERSION_DEFAULT),
			() -> _store.addFile(companyId, repositoryId, fileName, is));
	}

	@Override
	public void checkRoot(long companyId) {
		_store.checkRoot(companyId);
	}

	@Override
	public void deleteDirectory(
		long companyId, long repositoryId, String dirName) {

		_store.deleteDirectory(companyId, repositoryId, dirName);
	}

	@Override
	public void deleteFile(long companyId, long repositoryId, String fileName) {
		_store.deleteFile(companyId, repositoryId, fileName);
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

		return _store.getFileAsStream(
			companyId, repositoryId, fileName, versionLabel);
	}

	@Override
	public String[] getFileNames(long companyId, long repositoryId) {
		return _store.getFileNames(companyId, repositoryId);
	}

	@Override
	public String[] getFileNames(
		long companyId, long repositoryId, String dirName) {

		return _store.getFileNames(companyId, repositoryId, dirName);
	}

	@Override
	public long getFileSize(long companyId, long repositoryId, String fileName)
		throws PortalException {

		return _store.getFileSize(companyId, repositoryId, fileName);
	}

	@Override
	public boolean hasDirectory(
		long companyId, long repositoryId, String dirName) {

		return _store.hasDirectory(companyId, repositoryId, dirName);
	}

	@Override
	public boolean hasFile(
		long companyId, long repositoryId, String fileName,
		String versionLabel) {

		return _store.hasFile(companyId, repositoryId, fileName, versionLabel);
	}

	@Override
	public void updateFile(
			final long companyId, final long repositoryId,
			final long newRepositoryId, final String fileName)
		throws PortalException {

		if (repositoryId == newRepositoryId) {
			return;
		}

		recoverAndRetryOnFailure(
			createDeleteFileStoreAction(companyId, newRepositoryId, fileName),
			() -> _store.updateFile(
				companyId, repositoryId, newRepositoryId, fileName));
	}

	@Override
	public void updateFile(
			final long companyId, final long repositoryId,
			final String fileName, final String newFileName)
		throws PortalException {

		if (fileName.equals(newFileName)) {
			return;
		}

		recoverAndRetryOnFailure(
			createDeleteFileStoreAction(companyId, repositoryId, newFileName),
			() -> _store.updateFile(
				companyId, repositoryId, fileName, newFileName));
	}

	@Override
	public void updateFile(
			final long companyId, final long repositoryId,
			final String fileName, final String versionLabel,
			final InputStream is)
		throws PortalException {

		recoverAndRetryOnFailure(
			createDeleteFileStoreAction(
				companyId, repositoryId, fileName, versionLabel),
			() -> _store.updateFile(
				companyId, repositoryId, fileName, versionLabel, is));
	}

	protected StoreAction createDeleteFileStoreAction(
		final long companyId, final long repositoryId, final String fileName) {

		return () -> _store.deleteFile(companyId, repositoryId, fileName);
	}

	protected StoreAction createDeleteFileStoreAction(
		final long companyId, final long repositoryId, final String fileName,
		final String versionLabel) {

		return () -> _store.deleteFile(
			companyId, repositoryId, fileName, versionLabel);
	}

	protected void recoverAndRetryOnFailure(
			StoreAction recoverStoreAction, StoreAction storeAction)
		throws PortalException {

		try {
			storeAction.execute();
		}
		catch (DuplicateFileException dfe) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(dfe, dfe);
			}

			recoverStoreAction.execute();

			storeAction.execute();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		IgnoreDuplicatesStore.class);

	private final Store _store;

	private interface StoreAction {

		public void execute() throws PortalException;

	}

}