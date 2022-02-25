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

package com.liferay.batch.engine.internal.strategy;

import com.liferay.batch.engine.constants.BatchEngineImportTaskConstants;
import com.liferay.batch.engine.model.BatchEngineImportTask;
import com.liferay.batch.engine.strategy.ImportStrategy;

/**
 * @author Matija Petanjek
 */
public class ImportStrategyFactory {

	public ImportStrategy create(BatchEngineImportTask batchEngineImportTask) {
		if (batchEngineImportTask.getImportStrategy() ==
				BatchEngineImportTaskConstants.
					IMPORT_STRATEGY_ON_ERROR_CONTINUE) {

			return new OnErrorContinueImportStrategy(
				batchEngineImportTask.getBatchEngineImportTaskId(),
				batchEngineImportTask.getCompanyId(),
				batchEngineImportTask.getProcessedItemsCount(),
				batchEngineImportTask.getUserId());
		}

		return new OnErrorFailImportStrategy(
			batchEngineImportTask.getBatchEngineImportTaskId(),
			batchEngineImportTask.getCompanyId(),
			batchEngineImportTask.getProcessedItemsCount(),
			batchEngineImportTask.getUserId());
	}

}