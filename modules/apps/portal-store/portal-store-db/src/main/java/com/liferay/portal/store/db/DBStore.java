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

package com.liferay.portal.store.db;

import com.liferay.document.library.content.exception.NoSuchContentException;
import com.liferay.document.library.content.model.DLContent;
import com.liferay.document.library.content.model.DLContentDataBlobModel;
import com.liferay.document.library.content.service.DLContentLocalService;
import com.liferay.document.library.kernel.exception.NoSuchFileException;
import com.liferay.document.library.kernel.store.Store;
import com.liferay.petra.io.AutoDeleteFileInputStream;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.FileUtil;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.nio.channels.FileChannel;

import java.sql.Blob;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shuyang Zhou
 * @author Tina Tian
 */
@Component(
	immediate = true,
	property = "store.type=com.liferay.portal.store.db.DBStore",
	service = Store.class
)
public class DBStore implements Store {

	@Override
	public void addFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel, InputStream inputStream)
		throws PortalException {

		if (_dlContentLocalService.hasContent(
				companyId, repositoryId, fileName, versionLabel)) {

			_dlContentLocalService.deleteContent(
				companyId, repositoryId, fileName, versionLabel);
		}

		long length = -1;

		if (inputStream instanceof ByteArrayInputStream) {
			ByteArrayInputStream byteArrayInputStream =
				(ByteArrayInputStream)inputStream;

			length = byteArrayInputStream.available();
		}
		else if (inputStream instanceof FileInputStream) {
			FileInputStream fileInputStream = (FileInputStream)inputStream;

			FileChannel fileChannel = fileInputStream.getChannel();

			try {
				length = fileChannel.size();
			}
			catch (IOException ioe) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to detect file size from file channel", ioe);
				}
			}
		}
		else if (inputStream instanceof UnsyncByteArrayInputStream) {
			UnsyncByteArrayInputStream unsyncByteArrayInputStream =
				(UnsyncByteArrayInputStream)inputStream;

			length = unsyncByteArrayInputStream.available();
		}

		if (length >= 0) {
			_dlContentLocalService.addContent(
				companyId, repositoryId, fileName, versionLabel, inputStream,
				length);
		}
		else {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to detect length from input stream. Reading " +
						"entire input stream into memory as a last resort.");
			}

			byte[] bytes = null;

			try {
				bytes = FileUtil.getBytes(inputStream);
			}
			catch (IOException ioe) {
				throw new SystemException(ioe);
			}

			_dlContentLocalService.addContent(
				companyId, repositoryId, fileName, versionLabel, bytes);
		}
	}

	@Override
	public void deleteDirectory(
		long companyId, long repositoryId, String dirName) {

		_dlContentLocalService.deleteContentsByDirectory(
			companyId, repositoryId, dirName);
	}

	@Override
	public void deleteFile(
		long companyId, long repositoryId, String fileName,
		String versionLabel) {

		try {
			_dlContentLocalService.deleteContent(
				companyId, repositoryId, fileName, versionLabel);
		}
		catch (PortalException pe) {
			throw new SystemException(pe);
		}
	}

	@Override
	public InputStream getFileAsStream(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws NoSuchFileException {

		try {
			DLContent dlContent = _dlContentLocalService.getContent(
				companyId, repositoryId, fileName, versionLabel);

			return TransactionInvokerUtil.invoke(
				_transactionConfig,
				new GetBlobDataCallable(dlContent.getContentId()));
		}
		catch (Throwable t) {
			throw new NoSuchFileException(
				companyId, repositoryId, fileName, versionLabel, t);
		}
	}

	@Override
	public String[] getFileNames(
		long companyId, long repositoryId, String dirName) {

		List<DLContent> dlContents = null;

		if (dirName.isEmpty()) {
			dlContents = _dlContentLocalService.getContents(
				companyId, repositoryId);
		}
		else {
			dlContents = _dlContentLocalService.getContentsByDirectory(
				companyId, repositoryId, dirName);
		}

		String[] fileNames = new String[dlContents.size()];

		for (int i = 0; i < dlContents.size(); i++) {
			DLContent dlContent = dlContents.get(i);

			fileNames[i] = dlContent.getPath();
		}

		return fileNames;
	}

	@Override
	public long getFileSize(
			long companyId, long repositoryId, String fileName,
			String versionLabel)
		throws NoSuchFileException {

		DLContent dlContent = null;

		try {
			dlContent = _dlContentLocalService.getContent(
				companyId, repositoryId, fileName, versionLabel);
		}
		catch (NoSuchContentException nsce) {
			throw new NoSuchFileException(
				companyId, repositoryId, fileName, nsce);
		}

		return dlContent.getSize();
	}

	@Override
	public String[] getFileVersions(
			long companyId, long repositoryId, String fileName)
		throws PortalException {

		List<DLContent> dlContents = _dlContentLocalService.getContents(
			companyId, repositoryId, fileName);

		if (dlContents.isEmpty()) {
			return StringPool.EMPTY_ARRAY;
		}

		String[] versions = new String[dlContents.size()];

		for (int i = 0; i < dlContents.size(); i++) {
			DLContent dlContent = dlContents.get(i);

			versions[i] = dlContent.getVersion();
		}

		Arrays.sort(versions);

		return versions;
	}

	@Override
	public boolean hasFile(
		long companyId, long repositoryId, String fileName,
		String versionLabel) {

		return _dlContentLocalService.hasContent(
			companyId, repositoryId, fileName, versionLabel);
	}

	@Activate
	protected void activate() {
		DB db = DBManagerUtil.getDB();

		if ((db.getDBType() == DBType.DB2) ||
			(db.getDBType() == DBType.MYSQL) ||
			(db.getDBType() == DBType.MARIADB) ||
			(db.getDBType() == DBType.SYBASE)) {

			_transactionConfig = TransactionConfig.Factory.create(
				Propagation.SUPPORTS,
				new Class<?>[] {PortalException.class, SystemException.class});
		}
		else {
			_transactionConfig = TransactionConfig.Factory.create(
				Propagation.REQUIRED,
				new Class<?>[] {PortalException.class, SystemException.class});
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(DBStore.class);

	@Reference
	private DLContentLocalService _dlContentLocalService;

	private TransactionConfig _transactionConfig;

	private class GetBlobDataCallable implements Callable<InputStream> {

		@Override
		public InputStream call() throws Exception {
			DLContentDataBlobModel dlContentDataBlobModel =
				_dlContentLocalService.getDataBlobModel(_contentId);

			Blob blob = dlContentDataBlobModel.getDataBlob();

			InputStream inputStream = blob.getBinaryStream();

			if (_transactionConfig.getPropagation() == Propagation.REQUIRED) {
				inputStream = new AutoDeleteFileInputStream(
					FileUtil.createTempFile(inputStream));
			}

			return inputStream;
		}

		private GetBlobDataCallable(long contentId) {
			_contentId = contentId;
		}

		private final long _contentId;

	}

}