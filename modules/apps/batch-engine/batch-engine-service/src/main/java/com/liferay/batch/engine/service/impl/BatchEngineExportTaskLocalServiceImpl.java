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

package com.liferay.batch.engine.service.impl;

import com.liferay.batch.engine.model.BatchEngineExportTask;
import com.liferay.batch.engine.model.BatchEngineExportTaskContentBlobModel;
import com.liferay.batch.engine.service.base.BatchEngineExportTaskLocalServiceBaseImpl;
import com.liferay.petra.io.AutoDeleteFileInputStream;
import com.liferay.petra.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.dao.jdbc.OutputBlob;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.File;

import java.io.InputStream;
import java.io.Serializable;

import java.sql.Blob;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ivica Cardic
 */
@Component(
	property = "model.class.name=com.liferay.batch.engine.model.BatchEngineExportTask",
	service = AopService.class
)
public class BatchEngineExportTaskLocalServiceImpl
	extends BatchEngineExportTaskLocalServiceBaseImpl {

	@Override
	public BatchEngineExportTask addBatchEngineExportTask(
		long companyId, long userId, String callbackURL, String className,
		String contentType, String executeStatus, List<String> fieldNamesList,
		Map<String, Serializable> parameters, String version) {

		BatchEngineExportTask batchEngineExportTask =
			batchEngineExportTaskPersistence.create(
				counterLocalService.increment(
					BatchEngineExportTask.class.getName()));

		batchEngineExportTask.setCompanyId(companyId);
		batchEngineExportTask.setUserId(userId);
		batchEngineExportTask.setCallbackURL(callbackURL);
		batchEngineExportTask.setClassName(className);
		batchEngineExportTask.setContent(
			new OutputBlob(new UnsyncByteArrayInputStream(new byte[0]), 0));
		batchEngineExportTask.setContentType(contentType);
		batchEngineExportTask.setExecuteStatus(executeStatus);
		batchEngineExportTask.setFieldNamesList(fieldNamesList);
		batchEngineExportTask.setParameters(parameters);
		batchEngineExportTask.setVersion(version);

		return batchEngineExportTaskPersistence.update(batchEngineExportTask);
	}

	@Override
	public List<BatchEngineExportTask> getBatchEngineExportTasks(
		String executeStatus) {

		return batchEngineExportTaskPersistence.findByExecuteStatus(
			executeStatus);
	}

	@Override
	@Transactional(readOnly = true)
	public InputStream openContentInputStream(long batchEngineExportTaskId) {
		try {
			BatchEngineExportTaskContentBlobModel
				batchEngineExportTaskContentBlobModel = getContentBlobModel(
					batchEngineExportTaskId);

			Blob blob = batchEngineExportTaskContentBlobModel.getContentBlob();

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

	@Reference
	private File _file;

	private boolean _useTempFile;

}