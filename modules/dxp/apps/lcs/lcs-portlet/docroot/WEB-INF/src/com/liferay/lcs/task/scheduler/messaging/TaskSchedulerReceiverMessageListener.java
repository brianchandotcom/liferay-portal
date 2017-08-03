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

package com.liferay.lcs.task.scheduler.messaging;

import com.liferay.lcs.task.ScheduledTask;
import com.liferay.lcs.util.LCSConnectionManager;
import com.liferay.portal.kernel.bean.BeanLocator;
import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.concurrent.ConcurrentHashSet;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;

import java.util.Map;
import java.util.Set;

/**
 * @author Riccardo Ferrari
 */
public class TaskSchedulerReceiverMessageListener extends BaseMessageListener {

	public void setLCSConnectionManager(
		LCSConnectionManager lcsConnectionManager) {

		_lcsConnectionManager = lcsConnectionManager;
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		Map<String, String> schedulerContext =
			(Map<String, String>)message.getPayload();

		String taskName = schedulerContext.get("taskName");

		try {
			if (!_runningTaskNames.contains(taskName)) {
				if (!_lcsConnectionManager.isReady()) {
					if (_log.isDebugEnabled()) {
						_log.debug("Waiting for LCS connection manager");
					}

					return;
				}

				if (_log.isDebugEnabled()) {
					_log.debug(
						"LCS scheduler received trigger for task " + taskName);
				}

				BeanLocator beanLocator = PortletBeanLocatorUtil.getBeanLocator(
					"lcs-portlet");

				ScheduledTask scheduledTask = (ScheduledTask)beanLocator.locate(
					taskName);

				_runningTaskNames.add(taskName);

				long startTimeMillis = System.currentTimeMillis();

				scheduledTask.run();

				if (_log.isDebugEnabled()) {
					long taskExecutionMillis =
						System.currentTimeMillis() - startTimeMillis;

					_log.debug(
						"Executed LCS task " + taskName + " in " +
							taskExecutionMillis + "ms");
				}

				_runningTaskNames.remove(taskName);
			}
			else {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Skipping message because task " + taskName +
							" is still running");
				}

				return;
			}
		}
		catch (Exception e) {
			_runningTaskNames.remove(taskName);

			_log.error(e, e);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TaskSchedulerReceiverMessageListener.class);

	private static final Set<String> _runningTaskNames =
		new ConcurrentHashSet<>();

	private LCSConnectionManager _lcsConnectionManager;

}