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

package com.liferay.portal.store.ignore.duplicates.wrapper.internal;

import com.liferay.document.library.kernel.exception.DuplicateFileException;
import com.liferay.document.library.kernel.store.Store;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.File;
import java.io.InputStream;

/**
 * @author Adolfo Pérez
 */
public abstract class IgnoreDuplicatesStore implements Store {

	public static final int SERVICE_RANKING = 100;

	@Override
	public void addDirectory(
		long companyId, long repositoryId, String dirName) {

		store.addDirectory(companyId, repositoryId, dirName);
	}

	@Override
	public void addFile(
			final long companyId, final long repositoryId,
			final String fileName, final byte[] bytes)
		throws PortalException {

		recoverAndRetryOnFailure(
			createDeleteFileStoreAction(
				companyId, repositoryId, fileName, Store.VERSION_DEFAULT),
			() -> store.addFile(companyId, repositoryId, fileName, bytes));
	}

	@Override
	public void addFile(
			final long companyId, final long repositoryId,
			final String fileName, final File file)
		throws PortalException {

		recoverAndRetryOnFailure(
			createDeleteFileStoreAction(
				companyId, repositoryId, fileName, Store.VERSION_DEFAULT),
			() -> store.addFile(companyId, repositoryId, fileName, file));
	}

	@Override
	public void addFile(
			final long companyId, final long repositoryId,
			final String fileName, final InputStream is)
		throws PortalException {

		recoverAndRetryOnFailure(
			createDeleteFileStoreAction(
				companyId, repositoryId, fileName, Store.VERSION_DEFAULT),
			() -> store.addFile(companyId, repositoryId, fileName, is));
	}

	@Override
	public void checkRoot(long companyId) {
		store.checkRoot(companyId);
	}

	@Override
	public void copyFileToStore(
			long companyId, long repositoryId, String fileName,
			String versionLabel, Store targetStore)
		throws PortalException {

		store.copyFileToStore(
			companyId, repositoryId, fileName, versionLabel, targetStore);
	}

	@Override
	public void copyFileVersion(
			final long companyId, final long repositoryId,
			final String fileName, final String fromVersionLabel,
			final String toVersionLabel)
		throws PortalException {

		if (fromVersionLabel.equals(toVersionLabel)) {
			return;
		}

		recoverAndRetryOnFailure(
			createDeleteFileStoreAction(
				companyId, repositoryId, fileName, toVersionLabel),
			() -> store.copyFileVersion(
				companyId, repositoryId, fileName, fromVersionLabel,
				toVersionLabel));
	}

	@Override
	public void deleteDirectory(
		long companyId, long repositoryId, String dirName) {

		store.deleteDirectory(companyId, repositoryId, dirName);
	}

	@Override
	public void deleteFile(long companyId, long repositoryId, String fileName) {
		store.deleteFile(companyId, repositoryId, fileName);
	}

	@Override
	public void deleteFile(
		long companyId, long repositoryId, String fileName,
		String versionLabel) {

		store.deleteFile(companyId, repositoryId, fileName, versionLabel);
	}

	@Override
	public File getFile(long companyId, long repositoryId, String fileName)
		throws PortalException {

		return store.getFile(companyId, repositoryId, fileName);
	}

	@Override
	public File getFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException {

		return store.getFile(companyId, repositoryId, fileName, versionLabel);
	}

	@Override
	public byte[] getFileAsBytes(
			long companyId, long repositoryId, String fileName)
		throws PortalException {

		return store.getFileAsBytes(companyId, repositoryId, fileName);
	}

	@Override
	public byte[] getFileAsBytes(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException {

		return store.getFileAsBytes(
			companyId, repositoryId, fileName, versionLabel);
	}

	@Override
	public InputStream getFileAsStream(
			long companyId, long repositoryId, String fileName)
		throws PortalException {

		return store.getFileAsStream(companyId, repositoryId, fileName);
	}

	@Override
	public InputStream getFileAsStream(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException {

		return store.getFileAsStream(
			companyId, repositoryId, fileName, versionLabel);
	}

	@Override
	public String[] getFileNames(long companyId, long repositoryId) {
		return store.getFileNames(companyId, repositoryId);
	}

	@Override
	public String[] getFileNames(
		long companyId, long repositoryId, String dirName) {

		return store.getFileNames(companyId, repositoryId, dirName);
	}

	@Override
	public long getFileSize(long companyId, long repositoryId, String fileName)
		throws PortalException {

		return store.getFileSize(companyId, repositoryId, fileName);
	}

	@Override
	public boolean hasDirectory(
		long companyId, long repositoryId, String dirName) {

		return store.hasDirectory(companyId, repositoryId, dirName);
	}

	@Override
	public boolean hasFile(long companyId, long repositoryId, String fileName) {
		return store.hasFile(companyId, repositoryId, fileName);
	}

	@Override
	public boolean hasFile(
		long companyId, long repositoryId, String fileName,
		String versionLabel) {

		return store.hasFile(companyId, repositoryId, fileName, versionLabel);
	}

	@Override
	public void move(String srcDir, String destDir) {
		store.move(srcDir, destDir);
	}

	@Override
	public void moveFileToStore(
			long companyId, long repositoryId, String fileName,
			String versionLabel, Store targetStore)
		throws PortalException {

		store.moveFileToStore(
			companyId, repositoryId, fileName, versionLabel, targetStore);
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
			() -> store.updateFile(
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
			() -> store.updateFile(
				companyId, repositoryId, fileName, newFileName));
	}

	@Override
	public void updateFile(
			final long companyId, final long repositoryId,
			final String fileName, final String versionLabel,
			final byte[] bytes)
		throws PortalException {

		recoverAndRetryOnFailure(
			createDeleteFileStoreAction(
				companyId, repositoryId, fileName, versionLabel),
			() -> store.updateFile(
				companyId, repositoryId, fileName, versionLabel, bytes));
	}

	@Override
	public void updateFile(
			final long companyId, final long repositoryId,
			final String fileName, final String versionLabel, final File file)
		throws PortalException {

		recoverAndRetryOnFailure(
			createDeleteFileStoreAction(
				companyId, repositoryId, fileName, versionLabel),
			() -> store.updateFile(
				companyId, repositoryId, fileName, versionLabel, file));
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
			() -> store.updateFile(
				companyId, repositoryId, fileName, versionLabel, is));
	}

	@Override
	public void updateFileVersion(
			final long companyId, final long repositoryId,
			final String fileName, final String fromVersionLabel,
			final String toVersionLabel)
		throws PortalException {

		recoverAndRetryOnFailure(
			createDeleteFileStoreAction(
				companyId, repositoryId, fileName, toVersionLabel),
			() -> store.updateFileVersion(
				companyId, repositoryId, fileName, fromVersionLabel,
				toVersionLabel));
	}

	protected StoreAction createDeleteFileStoreAction(
		final long companyId, final long repositoryId, final String fileName) {

		return () -> store.deleteFile(companyId, repositoryId, fileName);
	}

	protected StoreAction createDeleteFileStoreAction(
		final long companyId, final long repositoryId, final String fileName,
		final String versionLabel) {

		return () -> store.deleteFile(
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

	protected volatile Store store;

	private static final Log _log = LogFactoryUtil.getLog(
		IgnoreDuplicatesStore.class);

	private interface StoreAction {

		public void execute() throws PortalException;

	}

}