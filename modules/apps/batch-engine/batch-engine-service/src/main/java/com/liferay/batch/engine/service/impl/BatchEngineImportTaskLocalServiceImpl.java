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

import com.liferay.batch.engine.model.BatchEngineImportTask;
import com.liferay.batch.engine.service.base.BatchEngineImportTaskLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.jdbc.OutputBlob;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Shuyang Zhou
 */
@Component(
	property = "model.class.name=com.liferay.batch.engine.model.BatchEngineImportTask",
	service = AopService.class
)
public class BatchEngineImportTaskLocalServiceImpl
	extends BatchEngineImportTaskLocalServiceBaseImpl {

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public BatchEngineImportTask addBatchEngineImportTask(
		long companyId, long userId, long batchSize, String callbackURL,
		String className, byte[] content, String contentType,
		String executeStatus, Map<String, String> fieldNameMappingMap,
		String operation, Map<String, Serializable> parameters,
		String version) {

		BatchEngineImportTask batchEngineImportTask =
			batchEngineImportTaskPersistence.create(
				counterLocalService.increment(
					BatchEngineImportTask.class.getName()));

		batchEngineImportTask.setCompanyId(companyId);
		batchEngineImportTask.setUserId(userId);
		batchEngineImportTask.setBatchSize(batchSize);
		batchEngineImportTask.setCallbackURL(callbackURL);
		batchEngineImportTask.setClassName(className);
		batchEngineImportTask.setContent(
			new OutputBlob(
				new UnsyncByteArrayInputStream(content), content.length));
		batchEngineImportTask.setContentType(contentType);
		batchEngineImportTask.setExecuteStatus(executeStatus);

		if ((fieldNameMappingMap != null) && !fieldNameMappingMap.isEmpty()) {
			batchEngineImportTask.setFieldNameMapping((Map)fieldNameMappingMap);
		}

		batchEngineImportTask.setOperation(operation);

		if ((parameters != null) && !parameters.isEmpty()) {
			batchEngineImportTask.setParameters(parameters);
		}

		batchEngineImportTask.setVersion(version);

		return batchEngineImportTaskPersistence.update(batchEngineImportTask);
	}

	@Override
	public List<BatchEngineImportTask> getBatchEngineImportTasks(
		String executeStatus) {

		return batchEngineImportTaskPersistence.findByExecuteStatus(
			executeStatus);
	}

}