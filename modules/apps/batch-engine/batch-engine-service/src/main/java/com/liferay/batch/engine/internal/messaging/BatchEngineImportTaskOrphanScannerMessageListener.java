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

import com.liferay.batch.engine.BatchEngineImportTaskExecutor;
import com.liferay.batch.engine.BatchEngineTaskExecuteStatus;
import com.liferay.batch.engine.configuration.BatchEngineImportTaskConfiguration;
import com.liferay.batch.engine.model.BatchEngineImportTask;
import com.liferay.batch.engine.service.BatchEngineImportTaskLocalService;
import com.liferay.petra.concurrent.NoticeableExecutorService;
import com.liferay.petra.executor.PortalExecutorManager;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.SchedulerEntryImpl;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerFactory;
import com.liferay.portal.kernel.util.Time;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ivica Cardic
 */
@Component(
	configurationPid = "com.liferay.batch.engine.configuration.BatchEngineImportTaskConfiguration",
	immediate = true, service = MessageListener.class
)
public class BatchEngineImportTaskOrphanScannerMessageListener
	extends BaseMessageListener {

	@Activate
	protected void activate(Map<String, Object> properties) {
		BatchEngineImportTaskConfiguration batchEngineImportTaskConfiguration =
			ConfigurableUtil.createConfigurable(
				BatchEngineImportTaskConfiguration.class, properties);

		_orphanageThreshold =
			batchEngineImportTaskConfiguration.orphanageThreshold() *
				Time.MINUTE;

		String className =
			BatchEngineImportTaskOrphanScannerMessageListener.class.getName();
		int scanInterval =
			batchEngineImportTaskConfiguration.orphanScanInterval();

		Trigger trigger = _triggerFactory.createTrigger(
			className, className,
			new Date(System.currentTimeMillis() + scanInterval * Time.MINUTE),
			null, scanInterval, TimeUnit.MINUTE);

		_schedulerEngineHelper.register(
			this, new SchedulerEntryImpl(className, trigger),
			DestinationNames.SCHEDULER_DISPATCH);
	}

	@Deactivate
	protected void deactivate() {
		ExecutorService executorService =
			_portalExecutorManager.getPortalExecutor(
				BatchEngineImportTaskOrphanScannerMessageListener.class.
					getName(),
				false);

		if (executorService != null) {
			executorService.shutdownNow();
		}

		_schedulerEngineHelper.unregister(this);
	}

	@Override
	protected void doReceive(Message message) {
		NoticeableExecutorService noticeableExecutorService =
			_portalExecutorManager.getPortalExecutor(
				BatchEngineImportTaskOrphanScannerMessageListener.class.
					getName());

		long time = System.currentTimeMillis();

		for (BatchEngineImportTask batchEngineImportTask :
				_batchEngineImportTaskLocalService.getBatchEngineImportTasks(
					BatchEngineTaskExecuteStatus.STARTED.toString())) {

			Date modifiedDate = batchEngineImportTask.getModifiedDate();

			if ((time - modifiedDate.getTime()) > _orphanageThreshold) {
				noticeableExecutorService.submit(
					() -> _batchEngineImportTaskExecutor.execute(
						batchEngineImportTask));
			}
		}
	}

	@Reference
	private BatchEngineImportTaskExecutor _batchEngineImportTaskExecutor;

	@Reference
	private BatchEngineImportTaskLocalService
		_batchEngineImportTaskLocalService;

	private long _orphanageThreshold;

	@Reference
	private PortalExecutorManager _portalExecutorManager;

	@Reference
	private SchedulerEngineHelper _schedulerEngineHelper;

	@Reference
	private TriggerFactory _triggerFactory;

}