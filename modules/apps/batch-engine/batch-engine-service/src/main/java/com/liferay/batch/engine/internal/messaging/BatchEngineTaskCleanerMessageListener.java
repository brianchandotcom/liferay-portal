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

package com.liferay.batch.engine.internal.messaging;

import com.liferay.batch.engine.BatchEngineTaskExecuteStatus;
import com.liferay.batch.engine.configuration.BatchEngineTaskConfiguration;
import com.liferay.batch.engine.model.BatchEngineExportTask;
import com.liferay.batch.engine.model.BatchEngineImportTask;
import com.liferay.batch.engine.service.BatchEngineExportTaskLocalService;
import com.liferay.batch.engine.service.BatchEngineImportTaskLocalService;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.scheduler.SchedulerJobConfiguration;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.TriggerConfiguration;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ivica Cardic
 */
@Component(
	configurationPid = "com.liferay.batch.engine.configuration.BatchEngineTaskConfiguration",
	service = SchedulerJobConfiguration.class
)
public class BatchEngineTaskCleanerMessageListener
	implements SchedulerJobConfiguration {

	@Override
	public UnsafeRunnable<Exception> getJobExecutor() {
		return () -> {
			for (BatchEngineExportTask batchEngineExportTask :
					_batchEngineExportTaskLocalService.
						getBatchEngineExportTasks(
							BatchEngineTaskExecuteStatus.COMPLETED.
								toString())) {

				_batchEngineExportTaskLocalService.deleteBatchEngineExportTask(
					batchEngineExportTask.getBatchEngineExportTaskId());
			}

			for (BatchEngineImportTask batchEngineImportTask :
					_batchEngineImportTaskLocalService.
						getBatchEngineImportTasks(
							BatchEngineTaskExecuteStatus.COMPLETED.
								toString())) {

				_batchEngineImportTaskLocalService.deleteBatchEngineImportTask(
					batchEngineImportTask.getBatchEngineImportTaskId());
			}
		};
	}

	@Override
	public TriggerConfiguration getTriggerConfiguration() {
		return TriggerConfiguration.createTriggerConfiguration(
			_batchEngineTaskConfiguration.completedTasksCleanerScanInterval(),
			TimeUnit.DAY);
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_batchEngineTaskConfiguration = ConfigurableUtil.createConfigurable(
			BatchEngineTaskConfiguration.class, properties);
	}

	@Reference
	private BatchEngineExportTaskLocalService
		_batchEngineExportTaskLocalService;

	@Reference
	private BatchEngineImportTaskLocalService
		_batchEngineImportTaskLocalService;

	private BatchEngineTaskConfiguration _batchEngineTaskConfiguration;

}