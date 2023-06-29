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

import com.liferay.batch.engine.action.ImportTaskPostAction;
import com.liferay.batch.engine.action.ImportTaskPreAction;
import com.liferay.batch.engine.internal.util.ItemIndexThreadLocal;
import com.liferay.batch.engine.model.BatchEngineImportTask;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.List;

/**
 * @author Matija Petanjek
 */
public class OnErrorContinueBatchEngineImportStrategy
	extends BaseBatchEngineImportStrategy {

	public OnErrorContinueBatchEngineImportStrategy(
		BatchEngineImportTask batchEngineImportTask,
		List<ImportTaskPostAction> importTaskPostActions,
		List<ImportTaskPreAction> importTaskPreActions) {

		super(
			batchEngineImportTask, importTaskPostActions, importTaskPreActions);
	}

	@Override
	public <T> T importItem(
		T item, UnsafeFunction<T, T, Exception> unsafeFunction) {

		T persistedItem = null;

		try {
			persistedItem = unsafeFunction.apply(item);
		}
		catch (Exception exception) {
			_log.error(exception);

			addBatchEngineImportTaskError(
				batchEngineImportTask.getCompanyId(),
				batchEngineImportTask.getUserId(),
				batchEngineImportTask.getBatchEngineImportTaskId(),
				item.toString(), ItemIndexThreadLocal.get(item),
				exception.toString());
		}

		return persistedItem;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OnErrorContinueBatchEngineImportStrategy.class);

}