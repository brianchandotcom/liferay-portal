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

import com.liferay.batch.engine.BatchEngineImportTaskExecutor;
import com.liferay.batch.engine.BatchEngineTaskContentType;
import com.liferay.batch.engine.BatchEngineTaskExecuteStatus;
import com.liferay.batch.engine.BatchEngineTaskOperation;
import com.liferay.batch.engine.model.BatchEngineImportTask;
import com.liferay.batch.engine.service.BatchEngineImportTaskLocalService;
import com.liferay.portal.kernel.exception.PortalException;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;

import java.util.Map;

/**
 * @author Riccardo Ferrari
 */
public class BatchEngineImportTaskHelper {

	public BatchEngineImportTaskHelper(
			BatchEngineImportTaskExecutor batchEngineImportTaskExecutor,
			BatchEngineImportTaskLocalService batchEngineImportTaskLocalService,
			String batchEngineImportTaskItemDelegateName, long batchSize,
			long companyId, Map<String, String> fieldMapping, File resourceFile,
			String resourceName, long userId)
		throws IOException, PortalException {

		_batchEngineImportTaskExecutor = batchEngineImportTaskExecutor;

		_batchEngineImportTaskLocalService = batchEngineImportTaskLocalService;

		_batchEngineImportTask =
			_batchEngineImportTaskLocalService.addBatchEngineImportTask(
				companyId, userId, batchSize, null, resourceName,
				Files.readAllBytes(resourceFile.toPath()),
				BatchEngineTaskContentType.JSONL.name(),
				BatchEngineTaskExecuteStatus.INITIAL.name(), fieldMapping,
				BatchEngineTaskOperation.CREATE.name(), null,
				batchEngineImportTaskItemDelegateName);
	}

	public void clean() {
		_batchEngineImportTaskLocalService.deleteBatchEngineImportTask(
			_batchEngineImportTask);
	}

	public boolean execute() {
		_batchEngineImportTaskExecutor.execute(_batchEngineImportTask);

		BatchEngineTaskExecuteStatus batchEngineTaskExecuteStatus =
			BatchEngineTaskExecuteStatus.valueOf(
				_batchEngineImportTask.getExecuteStatus());

		return batchEngineTaskExecuteStatus.equals(
			BatchEngineTaskExecuteStatus.COMPLETED);
	}

	public int getTotalItemsCount() {
		return _batchEngineImportTask.getTotalItemsCount();
	}

	private final BatchEngineImportTask _batchEngineImportTask;
	private final BatchEngineImportTaskExecutor _batchEngineImportTaskExecutor;
	private final BatchEngineImportTaskLocalService
		_batchEngineImportTaskLocalService;

}