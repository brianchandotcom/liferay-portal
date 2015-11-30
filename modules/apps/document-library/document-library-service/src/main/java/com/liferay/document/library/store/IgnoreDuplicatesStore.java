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

package com.liferay.document.library.store;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portlet.documentlibrary.DuplicateFileException;
import com.liferay.portlet.documentlibrary.store.Store;

import java.io.File;
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
			final String fileName, final byte[] bytes)
		throws PortalException {

		recoverAndRetryOnFailure(
			createDeleteFileStoreAction(
				companyId, repositoryId, fileName, Store.VERSION_DEFAULT),
			new StoreAction() {

				@Override
				public void execute() throws PortalException {
					_store.addFile(companyId, repositoryId, fileName, bytes);
				}

			});
	}

	@Override
	public void addFile(
			final long companyId, final long repositoryId,
			final String fileName, final File file)
		throws PortalException {

		recoverAndRetryOnFailure(
			createDeleteFileStoreAction(
				companyId, repositoryId, fileName, Store.VERSION_DEFAULT),
			new StoreAction() {

				@Override
				public void execute() throws PortalException {
					_store.addFile(companyId, repositoryId, fileName, file);
				}

			}
		);
	}

	@Override
	public void addFile(
			final long companyId, final long repositoryId,
			final String fileName, final InputStream is)
		throws PortalException {

		recoverAndRetryOnFailure(
			createDeleteFileStoreAction(
				companyId, repositoryId, fileName, Store.VERSION_DEFAULT),
			new StoreAction() {

				@Override
				public void execute() throws PortalException {
					_store.addFile(companyId, repositoryId, fileName, is);
				}

			});
	}

	@Override
	public void checkRoot(long companyId) {
		_store.checkRoot(companyId);
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
			new StoreAction() {

				@Override
				public void execute() throws PortalException {
					_store.copyFileVersion(
						companyId, repositoryId, fileName, fromVersionLabel,
						toVersionLabel);
				}

			});
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
	public File getFile(long companyId, long repositoryId, String fileName)
		throws PortalException {

		return _store.getFile(companyId, repositoryId, fileName);
	}

	@Override
	public File getFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException {

		return _store.getFile(companyId, repositoryId, fileName, versionLabel);
	}

	@Override
	public byte[] getFileAsBytes(
			long companyId, long repositoryId, String fileName)
		throws PortalException {

		return _store.getFileAsBytes(companyId, repositoryId, fileName);
	}

	@Override
	public byte[] getFileAsBytes(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws PortalException {

		return _store.getFileAsBytes(
			companyId, repositoryId, fileName, versionLabel);
	}

	@Override
	public InputStream getFileAsStream(
			long companyId, long repositoryId, String fileName)
		throws PortalException {

		return _store.getFileAsStream(companyId, repositoryId, fileName);
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
	public boolean hasFile(long companyId, long repositoryId, String fileName) {
		return _store.hasFile(companyId, repositoryId, fileName);
	}

	@Override
	public boolean hasFile(
		long companyId, long repositoryId, String fileName,
		String versionLabel) {

		return _store.hasFile(companyId, repositoryId, fileName, versionLabel);
	}

	@Override
	public void move(String srcDir, String destDir) {
		_store.move(srcDir, destDir);
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
			createDeleteFileStoreAction(companyId, repositoryId, fileName),
			new StoreAction() {

				@Override
				public void execute() throws PortalException {
					_store.updateFile(
						companyId, repositoryId, newRepositoryId, fileName);
				}

			});
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
			new StoreAction() {

				@Override
				public void execute() throws PortalException {
					_store.updateFile(
						companyId, repositoryId, fileName, newFileName);
				}

			});
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
			new StoreAction() {

				@Override
				public void execute() throws PortalException {
					_store.updateFile(
						companyId, repositoryId, fileName, versionLabel, bytes);
				}

			});
	}

	@Override
	public void updateFile(
			final long companyId, final long repositoryId,
			final String fileName, final String versionLabel, final File file)
		throws PortalException {

		recoverAndRetryOnFailure(
			createDeleteFileStoreAction(
				companyId, repositoryId, fileName, versionLabel),
			new StoreAction() {

				@Override
				public void execute() throws PortalException {
					_store.updateFile(
						companyId, repositoryId, fileName, versionLabel, file);
				}

			});
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
			new StoreAction() {

				@Override
				public void execute() throws PortalException {
					_store.updateFile(
						companyId, repositoryId, fileName, versionLabel, is);
				}

			});
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
			new StoreAction() {

				@Override
				public void execute() throws PortalException {
					_store.updateFileVersion(
						companyId, repositoryId, fileName, fromVersionLabel,
						toVersionLabel);
				}

			});
	}

	protected StoreAction createDeleteFileStoreAction(
		final long companyId, final long repositoryId, final String fileName) {

		return new StoreAction() {

			@Override
			public void execute() throws PortalException {
				_store.deleteFile(companyId, repositoryId, fileName);
			}

		};
	}

	protected StoreAction createDeleteFileStoreAction(
		final long companyId, final long repositoryId, final String fileName,
		final String versionLabel) {

		return new StoreAction() {

			@Override
			public void execute() throws PortalException {
				_store.deleteFile(
					companyId, repositoryId, fileName, versionLabel);
			}

		};
	}

	protected void recoverAndRetryOnFailure(
			StoreAction recoverStoreAction, StoreAction storeAction)
		throws PortalException {

		try {
			storeAction.execute();
		}
		catch (DuplicateFileException dfe) {
			recoverStoreAction.execute();

			storeAction.execute();
		}
	}

	private final Store _store;

	private interface StoreAction {

		public void execute() throws PortalException;

	}

}