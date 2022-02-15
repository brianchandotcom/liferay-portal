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

package com.liferay.analytics.batch.exportimport.internal.batch;

import com.liferay.batch.engine.BatchEngineExportTaskExecutor;
import com.liferay.batch.engine.BatchEngineTaskContentType;
import com.liferay.batch.engine.BatchEngineTaskExecuteStatus;
import com.liferay.batch.engine.model.BatchEngineExportTask;
import com.liferay.batch.engine.service.BatchEngineExportTaskLocalService;

import java.io.InputStream;
import java.io.Serializable;

import java.util.List;
import java.util.Map;

/**
 * @author Riccardo Ferrari
 */
public class BatchEngineExportTaskHelper {

	public BatchEngineExportTaskHelper(
		BatchEngineExportTaskExecutor batchEngineExportTaskExecutor,
		BatchEngineExportTaskLocalService batchEngineExportTaskLocalService,
		String batchEngineImportTaskItemDelegateName, long companyId,
		List<String> filedList, Map<String, Serializable> parameters,
		String resourceName, long userId) {

		_batchEngineExportTaskExecutor = batchEngineExportTaskExecutor;

		_batchEngineExportTaskLocalService = batchEngineExportTaskLocalService;

		_batchEngineExportTask =
			_batchEngineExportTaskLocalService.addBatchEngineExportTask(
				companyId, userId, null, resourceName,
				BatchEngineTaskContentType.JSONL.name(),
				BatchEngineTaskExecuteStatus.INITIAL.name(), filedList,
				parameters, batchEngineImportTaskItemDelegateName);
	}

	public void clean() {
		_batchEngineExportTaskLocalService.deleteBatchEngineExportTask(
			_batchEngineExportTask);
	}

	public boolean execute() {
		_batchEngineExportTaskExecutor.execute(_batchEngineExportTask);

		BatchEngineTaskExecuteStatus batchEngineTaskExecuteStatus =
			BatchEngineTaskExecuteStatus.valueOf(
				_batchEngineExportTask.getExecuteStatus());

		return batchEngineTaskExecuteStatus.equals(
			BatchEngineTaskExecuteStatus.COMPLETED);
	}

	public InputStream getContentInputStream() {
		return _batchEngineExportTaskLocalService.openContentInputStream(
			_batchEngineExportTask.getBatchEngineExportTaskId());
	}

	public int getTotalItemsCount() {
		return _batchEngineExportTask.getTotalItemsCount();
	}

	private final BatchEngineExportTask _batchEngineExportTask;
	private final BatchEngineExportTaskExecutor _batchEngineExportTaskExecutor;
	private final BatchEngineExportTaskLocalService
		_batchEngineExportTaskLocalService;

}