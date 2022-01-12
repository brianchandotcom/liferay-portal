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

import com.liferay.batch.engine.exception.BatchEngineImportTaskParametersException;
import com.liferay.batch.engine.model.BatchEngineImportTask;
import com.liferay.batch.engine.service.base.BatchEngineImportTaskLocalServiceBaseImpl;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.jdbc.OutputBlob;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

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
			String taskItemDelegateName)
		throws PortalException {

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
			_validateParameters(parameters);
			batchEngineImportTask.setParameters(parameters);
		}

		batchEngineImportTask.setTaskItemDelegateName(taskItemDelegateName);

		return batchEngineImportTaskPersistence.update(batchEngineImportTask);
	}

	@Override
	public List<BatchEngineImportTask> getBatchEngineImportTasks(
		long companyId, int start, int end) {

		return batchEngineImportTaskPersistence.findByCompanyId(
			companyId, start, end);
	}

	@Override
	public List<BatchEngineImportTask> getBatchEngineImportTasks(
		long companyId, int start, int end,
		OrderByComparator<BatchEngineImportTask> orderByComparator) {

		return batchEngineImportTaskPersistence.findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	@Override
	public List<BatchEngineImportTask> getBatchEngineImportTasks(
		String executeStatus) {

		return batchEngineImportTaskPersistence.findByExecuteStatus(
			executeStatus);
	}

	@Override
	public int getBatchEngineImportTasksCount(long companyId) {
		return batchEngineImportTaskPersistence.countByCompanyId(companyId);
	}

	private void _validateParameters(Map<String, Serializable> parameters)
		throws BatchEngineImportTaskParametersException {

		String delimiter = (String)parameters.getOrDefault(
			"delimiter", (Serializable)StringPool.COMMA);

		if (delimiter.length() > 1) {
			throw new BatchEngineImportTaskParametersException(
				"Delimiter cannot be more than one character");
		}

		if (delimiter.equals(StringPool.APOSTROPHE)) {
			throw new BatchEngineImportTaskParametersException(
				"Apostrophe (') cannot be used as delimiter");
		}

		if (delimiter.equals(StringPool.QUOTE)) {
			throw new BatchEngineImportTaskParametersException(
				"Quote (\") cannot be used as delimiter");
		}

		String enclosingCharacter = (String)parameters.getOrDefault(
			"enclosingCharacter", null);

		if ((enclosingCharacter != null) &&
			!enclosingCharacter.equals(StringPool.QUOTE) &&
			!enclosingCharacter.equals(StringPool.APOSTROPHE)) {

			throw new BatchEngineImportTaskParametersException(
				"Only Quote(\") or Apostrophe(') can be used as" +
					" enclosing character");
		}
	}

}