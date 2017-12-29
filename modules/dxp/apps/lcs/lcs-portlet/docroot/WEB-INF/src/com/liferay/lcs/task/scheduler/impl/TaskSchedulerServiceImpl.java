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

package com.liferay.lcs.task.scheduler.impl;

import com.liferay.lcs.task.ScheduledTask;
import com.liferay.lcs.task.Type;
import com.liferay.lcs.task.scheduler.TaskSchedulerService;
import com.liferay.lcs.util.ClusterNodeUtil;
import com.liferay.portal.kernel.bean.BeanLocator;
import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelperUtil;
import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerFactoryUtil;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerResponse;
import com.liferay.portal.kernel.security.RandomUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author Riccardo Ferrari
 */
public class TaskSchedulerServiceImpl implements TaskSchedulerService {

	public void destroy() {
		_scheduledExecutorService.shutdown();

		try {
			if (!_scheduledExecutorService.awaitTermination(
					5, TimeUnit.SECONDS)) {

				_scheduledExecutorService.shutdownNow();
			}
		}
		catch (InterruptedException ie) {
			_scheduledExecutorService.shutdownNow();
		}
	}

	@Override
	public void scheduleTask(Map<String, String> schedulerContext) {
		BeanLocator beanLocator = PortletBeanLocatorUtil.getBeanLocator(
			"lcs-portlet");

		String taskName = schedulerContext.get("taskName");

		try {
			ScheduledTask scheduledTask = (ScheduledTask)beanLocator.locate(
				taskName);

			if (scheduledTask == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(taskName + " task is not supported");
				}

				return;
			}

			if (scheduledTask.getType() == Type.LOCAL) {
				scheduleLocalScheduledTask(schedulerContext);
			}
			else if (scheduledTask.getType() == Type.MEMORY_CLUSTERED) {
				_scheduleClusteredScheduledTask(schedulerContext);
			}
		}
		catch (Exception e) {
			_log.error("Unable to create new scheduled task", e);
		}
	}

	public void setDefaultInterval(int defaultInterval) {
		_defaultInterval = defaultInterval;
	}

	public void setScheduleDelayMax(int scheduleDelayMax) {
		_scheduleDelayMax = scheduleDelayMax;
	}

	@Override
	public void unscheduleAllTasks() {
		unscheduleLocalScheduledTasks();

		unscheduleClusteredScheduledTasks();
	}

	protected int getInterval(Map<String, String> schedulerContext) {
		String interval = schedulerContext.get("interval");

		return GetterUtil.getInteger(interval, _defaultInterval);
	}

	protected String getJobName(Map<String, String> schedulerContext) {
		return schedulerContext.get("taskName");
	}

	protected List<String> getScheduledTaskJobNames() {
		List<String> scheduledTaskJobNames = new ArrayList<>();

		try {
			List<SchedulerResponse> schedulerResponses =
				SchedulerEngineHelperUtil.getScheduledJobs(
					_LCS_SCHEDULE_GROUP_NAME, StorageType.MEMORY_CLUSTERED);

			for (SchedulerResponse schedulerResponse : schedulerResponses) {
				scheduledTaskJobNames.add(schedulerResponse.getJobName());
			}
		}
		catch (SchedulerException se) {
			_log.error(se, se);
		}

		return scheduledTaskJobNames;
	}

	protected void scheduleLocalScheduledTask(
		Map<String, String> schedulerContext) {

		int interval = getInterval(schedulerContext);

		String taskName = schedulerContext.get("taskName");

		BeanLocator beanLocator = PortletBeanLocatorUtil.getBeanLocator(
			"lcs-portlet");

		ScheduledTask scheduledTask = (ScheduledTask)beanLocator.locate(
			taskName);

		_scheduledFuturesMap.put(
			taskName,
			_scheduledExecutorService.scheduleAtFixedRate(
				scheduledTask, interval, interval, TimeUnit.SECONDS));

		if (_log.isDebugEnabled()) {
			_log.debug("Scheduled task " + taskName);
		}
	}

	protected void unscheduleClusteredScheduledTasks() {
		try {
			if (ClusterExecutorUtil.isEnabled()) {
				try {
					Thread.sleep(RandomUtil.nextInt(1000));
				}
				catch (InterruptedException ie) {
				}

				boolean otherClusterNodesConnected = false;

				List<Map<String, Object>> clusterNodeInfos =
					ClusterNodeUtil.getClusterNodeInfos();

				for (Map<String, Object> clusterNodeInfo : clusterNodeInfos) {
					if (GetterUtil.getBoolean(clusterNodeInfo.get("ready"))) {
						otherClusterNodesConnected = true;

						break;
					}
				}

				if (!otherClusterNodesConnected) {
					List<String> scheduledTaskJobNames =
						getScheduledTaskJobNames();

					for (String scheduledTaskJobName : scheduledTaskJobNames) {
						SchedulerEngineHelperUtil.delete(
							scheduledTaskJobName, _LCS_SCHEDULE_GROUP_NAME,
							StorageType.MEMORY_CLUSTERED);

						if (_log.isDebugEnabled()) {
							_log.debug(
								"Unscheduled clustered task " +
									scheduledTaskJobName);
						}
					}
				}
			}
			else {
				List<String> scheduledTaskJobNames = getScheduledTaskJobNames();

				for (String scheduledTaskJobName : scheduledTaskJobNames) {
					SchedulerEngineHelperUtil.delete(
						scheduledTaskJobName, _LCS_SCHEDULE_GROUP_NAME,
						StorageType.MEMORY_CLUSTERED);

					if (_log.isDebugEnabled()) {
						_log.debug("Unscheduled task " + scheduledTaskJobName);
					}
				}
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e.getMessage());
			}
		}
	}

	protected void unscheduleLocalScheduledTasks() {
		for (String taskName : _scheduledFuturesMap.keySet()) {
			ScheduledFuture<?> scheduledFuture = _scheduledFuturesMap.get(
				taskName);

			while (!scheduledFuture.isCancelled()) {
				scheduledFuture.cancel(true);
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Unscheduled task " + taskName);
			}
		}

		_scheduledFuturesMap.clear();
	}

	private void _scheduleClusteredScheduledTask(
		Map<String, String> schedulerContext) {

		try {
			Thread.sleep(RandomUtil.nextInt(_scheduleDelayMax));
		}
		catch (Exception e) {
		}

		String jobName = getJobName(schedulerContext);

		for (String scheduledTaskJobName : getScheduledTaskJobNames()) {
			if (jobName.equals(scheduledTaskJobName)) {
				if (_log.isDebugEnabled()) {
					_log.debug("Already scheduled job " + jobName);
				}

				return;
			}
		}

		try {
			Date endDate = null;

			long endTime = GetterUtil.getLong(
				schedulerContext.get("endTime"), 0);

			if (endTime > 0) {
				endDate = new Date(endTime);
			}

			Date startDate = null;

			long startTime = GetterUtil.getLong(
				schedulerContext.get("startTime"), 0);

			if (startTime > 0) {
				startDate = new Date(startTime);
			}

			Trigger trigger = TriggerFactoryUtil.createTrigger(
				jobName, _LCS_SCHEDULE_GROUP_NAME, startDate, endDate,
				getInterval(schedulerContext),
				com.liferay.portal.kernel.scheduler.TimeUnit.SECOND);

			SchedulerEngineHelperUtil.schedule(
				trigger, StorageType.MEMORY_CLUSTERED, StringPool.BLANK,
				"liferay/lcs_scheduler", schedulerContext, 0);

			if (_log.isDebugEnabled()) {
				_log.debug("Scheduled job " + jobName);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	private static final String _LCS_SCHEDULE_GROUP_NAME = "com.liferay.lcs";

	private static final Log _log = LogFactoryUtil.getLog(
		TaskSchedulerServiceImpl.class);

	private static final ScheduledExecutorService _scheduledExecutorService =
		Executors.newScheduledThreadPool(10);

	private int _defaultInterval;
	private int _scheduleDelayMax;
	private final Map<String, ScheduledFuture<?>> _scheduledFuturesMap =
		new HashMap<>();

}