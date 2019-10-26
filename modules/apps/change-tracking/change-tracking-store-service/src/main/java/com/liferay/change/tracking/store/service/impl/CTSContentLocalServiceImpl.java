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

package com.liferay.change.tracking.store.service.impl;

import com.liferay.change.tracking.store.exception.NoSuchContentException;
import com.liferay.change.tracking.store.model.CTSContent;
import com.liferay.change.tracking.store.model.CTSContentDataBlobModel;
import com.liferay.change.tracking.store.service.base.CTSContentLocalServiceBaseImpl;
import com.liferay.petra.io.AutoDeleteFileInputStream;
import com.liferay.petra.io.StreamUtil;
import com.liferay.petra.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.dao.jdbc.OutputBlob;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.File;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.nio.channels.FileChannel;

import java.sql.Blob;

import java.util.List;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shuyang Zhou
 */
@Component(
	property = "model.class.name=com.liferay.change.tracking.store.model.CTSContent",
	service = AopService.class
)
public class CTSContentLocalServiceImpl extends CTSContentLocalServiceBaseImpl {

	@Override
	public CTSContent addCTSContent(
		long companyId, long repositoryId, String path, String version,
		String storeType, InputStream inputStream) {

		CTSContent ctsContent = ctsContentPersistence.fetchByC_R_P_V_S(
			companyId, repositoryId, path, version, storeType);

		if (ctsContent == null) {
			ctsContent = ctsContentPersistence.create(
				counterLocalService.increment());

			ctsContent.setCompanyId(companyId);
			ctsContent.setRepositoryId(repositoryId);
			ctsContent.setPath(path);
			ctsContent.setVersion(version);
			ctsContent.setStoreType(storeType);
		}

		OutputBlob outputBlob = _toOutputBlob(inputStream);

		ctsContent.setData(outputBlob);
		ctsContent.setSize(outputBlob.length());

		return ctsContentPersistence.update(ctsContent);
	}

	@Override
	public void deleteCTSContent(
		long companyId, long repositoryId, String path, String version,
		String storeType) {

		CTSContent ctsContent = ctsContentPersistence.fetchByC_R_P_V_S(
			companyId, repositoryId, path, version, storeType);

		if (ctsContent != null) {
			ctsContentPersistence.remove(ctsContent);
		}
	}

	@Override
	public void deleteCTSContentsByDirectory(
		long companyId, long repositoryId, String dirName, String storeType) {

		if (dirName.isEmpty()) {
			ctsContentPersistence.removeByC_R_S(
				companyId, repositoryId, storeType);

			return;
		}

		if (!dirName.endsWith(StringPool.SLASH)) {
			dirName = dirName.concat(StringPool.SLASH);
		}

		dirName = dirName.concat(StringPool.PERCENT);

		ctsContentPersistence.removeByC_R_LikeP_S(
			companyId, repositoryId, dirName, storeType);
	}

	@Override
	public CTSContent getCTSContent(
			long companyId, long repositoryId, String path, String version,
			String storeType)
		throws NoSuchContentException {

		if (version.isEmpty()) {
			List<CTSContent> ctsContents = ctsContentPersistence.findByC_R_P_S(
				companyId, repositoryId, path, storeType, 0, 1, null);

			if ((ctsContents == null) || ctsContents.isEmpty()) {
				throw new NoSuchContentException(path);
			}

			return ctsContents.get(0);
		}

		return ctsContentPersistence.findByC_R_P_V_S(
			companyId, repositoryId, path, version, storeType);
	}

	@Override
	public List<CTSContent> getCTSContents(
		long companyId, long repositoryId, String path, String storeType) {

		return ctsContentPersistence.findByC_R_P_S(
			companyId, repositoryId, path, storeType);
	}

	@Override
	public List<CTSContent> getCTSContentsByDirectory(
		long companyId, long repositoryId, String dirName, String storeType) {

		if (dirName.isEmpty()) {
			return ctsContentPersistence.findByC_R_S(
				companyId, repositoryId, storeType);
		}

		if (!dirName.endsWith(StringPool.SLASH)) {
			dirName = dirName.concat(StringPool.SLASH);
		}

		dirName = dirName.concat(StringPool.PERCENT);

		return ctsContentPersistence.findByC_R_LikeP_S(
			companyId, repositoryId, dirName, storeType);
	}

	@Override
	public boolean hasCTSContent(
		long companyId, long repositoryId, String path, String version,
		String storeType) {

		int count = ctsContentPersistence.countByC_R_P_V_S(
			companyId, repositoryId, path, version, storeType);

		if (count > 0) {
			return true;
		}

		return false;
	}

	@Override
	@Transactional(readOnly = true)
	public InputStream openCTSContentInputStream(long contentId) {
		try {
			CTSContentDataBlobModel ctsContentDataBlobModel = getDataBlobModel(
				contentId);

			Blob blob = ctsContentDataBlobModel.getDataBlob();

			InputStream inputStream = blob.getBinaryStream();

			if (_useTempFile) {
				inputStream = new AutoDeleteFileInputStream(
					_file.createTempFile(inputStream));
			}

			return inputStream;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@Activate
	protected void activate() {
		DB db = DBManagerUtil.getDB();

		if ((db.getDBType() != DBType.DB2) &&
			(db.getDBType() != DBType.MYSQL) &&
			(db.getDBType() != DBType.MARIADB) &&
			(db.getDBType() != DBType.SYBASE)) {

			_useTempFile = true;
		}
	}

	private OutputBlob _toOutputBlob(InputStream inputStream) {
		if (inputStream instanceof ByteArrayInputStream) {
			ByteArrayInputStream byteArrayInputStream =
				(ByteArrayInputStream)inputStream;

			return new OutputBlob(
				inputStream, byteArrayInputStream.available());
		}

		if (inputStream instanceof UnsyncByteArrayInputStream) {
			UnsyncByteArrayInputStream unsyncByteArrayInputStream =
				(UnsyncByteArrayInputStream)inputStream;

			return new OutputBlob(
				unsyncByteArrayInputStream,
				unsyncByteArrayInputStream.available());
		}

		if (inputStream instanceof
				com.liferay.portal.kernel.io.unsync.
					UnsyncByteArrayInputStream) {

			com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream
				unsyncByteArrayInputStream =
					(com.liferay.portal.kernel.io.unsync.
						UnsyncByteArrayInputStream)inputStream;

			return new OutputBlob(
				inputStream, unsyncByteArrayInputStream.available());
		}

		if (inputStream instanceof FileInputStream) {
			FileInputStream fileInputStream = (FileInputStream)inputStream;

			FileChannel fileChannel = fileInputStream.getChannel();

			try {
				return new OutputBlob(inputStream, fileChannel.size());
			}
			catch (IOException ioe) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to detect file size from file channel", ioe);
				}
			}
		}

		try {
			byte[] bytes = StreamUtil.toByteArray(inputStream);

			UnsyncByteArrayInputStream unsyncByteArrayInputStream =
				new UnsyncByteArrayInputStream(bytes);

			return new OutputBlob(unsyncByteArrayInputStream, bytes.length);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CTSContentLocalServiceImpl.class);

	@Reference
	private File _file;

	private boolean _useTempFile;

}