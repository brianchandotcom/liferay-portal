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
import com.liferay.batch.engine.BatchEngineTaskExecutor;
import com.liferay.batch.engine.model.BatchEngineTask;
import com.liferay.batch.engine.service.BatchEngineTaskLocalService;
import com.liferay.petra.concurrent.NoticeableExecutorService;
import com.liferay.petra.executor.PortalExecutorManager;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.SchedulerEntryImpl;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerFactory;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.util.LocalDateTimeUtil;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import java.util.List;
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
	immediate = true, property = {"heartbeat.period=300", "interval=60"},
	service = MessageListener.class
)
public class BatchEngineTaskListener extends BaseMessageListener {

	@Activate
	protected void activate(Map<String, Object> properties) {
		_heartbeatInterval = GetterUtil.getInteger(
			properties.get("heartbeat.period"));

		Class<?> clazz = getClass();

		String className = clazz.getName();

		Trigger trigger = _triggerFactory.createTrigger(
			className, className, null, null,
			GetterUtil.getInteger(properties.get("interval")), TimeUnit.SECOND);

		SchedulerEntryImpl schedulerEntry = new SchedulerEntryImpl(
			className, trigger);

		_schedulerEngineHelper.register(
			this, schedulerEntry, DestinationNames.SCHEDULER_DISPATCH);
	}

	@Deactivate
	protected void deactivate() {
		ExecutorService executorService =
			_portalExecutorManager.getPortalExecutor(
				BatchEngineTaskListener.class.getName());

		executorService.shutdownNow();

		_schedulerEngineHelper.unregister(this);
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		NoticeableExecutorService noticeableExecutorService =
			_portalExecutorManager.getPortalExecutor(
				BatchEngineTaskListener.class.getName());

		List<BatchEngineTask> batchEngineTasks =
			_batchEngineTaskLocalService.getBatchEngineTasks(
				BatchEngineTaskExecuteStatus.STARTED);

		LocalDateTime localDateTime = LocalDateTime.now();

		localDateTime = localDateTime.minus(
			_heartbeatInterval, ChronoUnit.SECONDS);

		for (BatchEngineTask batchEngineTask : batchEngineTasks) {
			LocalDateTime modifiedDateTime = LocalDateTimeUtil.toLocalDateTime(
				batchEngineTask.getModifiedDate());

			if (modifiedDateTime.isBefore(localDateTime)) {
				noticeableExecutorService.submit(
					() -> {
						try {
							_batchEngineTaskExecutor.execute(batchEngineTask);
						}
						catch (Exception e) {
							_log.error(e, e);
						}
					});
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BatchEngineTaskListener.class);

	@Reference
	private BatchEngineTaskExecutor _batchEngineTaskExecutor;

	@Reference
	private BatchEngineTaskLocalService _batchEngineTaskLocalService;

	private int _heartbeatInterval;

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED)
	private ModuleServiceLifecycle _moduleServiceLifecycle;

	@Reference
	private PortalExecutorManager _portalExecutorManager;

	@Reference
	private SchedulerEngineHelper _schedulerEngineHelper;

	@Reference
	private TriggerFactory _triggerFactory;

}