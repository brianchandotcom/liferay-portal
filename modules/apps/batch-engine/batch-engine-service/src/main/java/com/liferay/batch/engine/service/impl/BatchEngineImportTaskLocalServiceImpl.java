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
import com.liferay.portal.kernel.util.Validator;

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
			_validateDelimiter(
				(String)parameters.getOrDefault("delimiter", null));
			_validateEnclosingCharacter(
				(String)parameters.getOrDefault("enclosingCharacter", null));
		}

		batchEngineImportTask.setParameters(parameters);
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

	private void _validateDelimiter(String delimiter)
		throws BatchEngineImportTaskParametersException {

		if (Validator.isNull(delimiter)) {
			return;
		}

		if (_VALID_ENCLOSING_CHARACTERS.contains(delimiter)) {
			throw new BatchEngineImportTaskParametersException(
				"Illegal delimiter value " + delimiter);
		}
	}

	private void _validateEnclosingCharacter(String enclosingCharacter)
		throws BatchEngineImportTaskParametersException {

		if (Validator.isNull(enclosingCharacter)) {
			return;
		}

		if (!_VALID_ENCLOSING_CHARACTERS.contains(enclosingCharacter)) {
			throw new BatchEngineImportTaskParametersException(
				"Illegal enclosing character value " + enclosingCharacter);
		}
	}

	private static final String _VALID_ENCLOSING_CHARACTERS =
		StringPool.APOSTROPHE + StringPool.QUOTE;

}