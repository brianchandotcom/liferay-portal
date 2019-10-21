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

import com.liferay.batch.engine.model.BatchEngineTask;
import com.liferay.batch.engine.service.base.BatchEngineTaskLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.jdbc.OutputBlob;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Shuyang Zhou
 */
@Component(
	property = "model.class.name=com.liferay.batch.engine.model.BatchEngineTask",
	service = AopService.class
)
public class BatchEngineTaskLocalServiceImpl
	extends BatchEngineTaskLocalServiceBaseImpl {

	@Override
	@SuppressWarnings("unchecked")
	public BatchEngineTask addBatchEngineTask(
		long companyId, long userId, long batchSize, String callbackURL,
		String className, byte[] content, String contentType,
		String executeStatus, Map<String, String> fieldNameMappingMap,
		String operation, String version) {

		BatchEngineTask batchEngineTask = batchEngineTaskPersistence.create(
			counterLocalService.increment(BatchEngineTask.class.getName()));

		batchEngineTask.setCompanyId(companyId);
		batchEngineTask.setUserId(userId);
		batchEngineTask.setBatchSize(batchSize);
		batchEngineTask.setCallbackURL(callbackURL);
		batchEngineTask.setClassName(className);
		batchEngineTask.setContent(
			new OutputBlob(
				new UnsyncByteArrayInputStream(content), content.length));
		batchEngineTask.setContentType(contentType);
		batchEngineTask.setExecuteStatus(executeStatus);
		batchEngineTask.setFieldNameMapping(
			(Map<String, Serializable>)(Map)fieldNameMappingMap);
		batchEngineTask.setOperation(operation);
		batchEngineTask.setVersion(version);

		return batchEngineTaskPersistence.update(batchEngineTask);
	}

	@Override
	public List<BatchEngineTask> getBatchEngineTasks(String executeStatus) {
		return batchEngineTaskPersistence.findByExecuteStatus(executeStatus);
	}

}