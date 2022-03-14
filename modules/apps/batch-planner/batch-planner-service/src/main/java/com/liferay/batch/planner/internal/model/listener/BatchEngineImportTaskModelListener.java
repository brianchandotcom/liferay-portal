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

package com.liferay.batch.planner.internal.model.listener;

import com.liferay.batch.engine.BatchEngineTaskExecuteStatus;
import com.liferay.batch.engine.model.BatchEngineImportTask;
import com.liferay.batch.planner.constants.BatchPlannerLogConstants;
import com.liferay.batch.planner.model.BatchPlannerLog;
import com.liferay.batch.planner.service.BatchPlannerLogLocalService;
import com.liferay.batch.planner.service.BatchPlannerPlanLocalService;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Matija Petanjek
 * @author Igor Beslic
 */
@Component(immediate = true, service = ModelListener.class)
public class BatchEngineImportTaskModelListener
	extends BaseModelListener<BatchEngineImportTask> {

	@Override
	public void onAfterCreate(BatchEngineImportTask batchEngineImportTask)
		throws ModelListenerException {

		_updateStatus(batchEngineImportTask);
	}

	@Override
	public void onAfterRemove(BatchEngineImportTask batchEngineImportTask)
		throws ModelListenerException {

		try {
			_batchPlannerPlanLocalService.
				updateBatchPlannerLogBatchPlannerPlanActive(
					false,
					String.valueOf(
						batchEngineImportTask.getBatchEngineImportTaskId()),
					true);
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	@Override
	public void onAfterUpdate(
			BatchEngineImportTask originalBatchEngineImportTask,
			BatchEngineImportTask batchEngineImportTask)
		throws ModelListenerException {

		_updateStatus(batchEngineImportTask);
	}

	private void _updateStatus(BatchEngineImportTask batchEngineImportTask) {
		BatchPlannerLog batchPlannerLog =
			_batchPlannerLogLocalService.fetchBatchPlannerLog(
				String.valueOf(
					batchEngineImportTask.getBatchEngineImportTaskId()),
				false);

		if (batchPlannerLog == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No batch planner log found for batch engine import task " +
						"ID " +
							batchEngineImportTask.getBatchEngineImportTaskId());
			}

			return;
		}

		batchPlannerLog.setStatus(
			BatchPlannerLogConstants.getStatus(
				BatchEngineTaskExecuteStatus.valueOf(
					batchEngineImportTask.getExecuteStatus())));

		_batchPlannerLogLocalService.updateBatchPlannerLog(batchPlannerLog);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BatchEngineImportTaskModelListener.class);

	@Reference
	private BatchPlannerLogLocalService _batchPlannerLogLocalService;

	@Reference
	private BatchPlannerPlanLocalService _batchPlannerPlanLocalService;

}