/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.scheduler.config;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.scheduler.SchedulerEngine;
import com.liferay.portal.kernel.scheduler.SchedulerEntry;
import com.liferay.portal.kernel.scheduler.Trigger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Shuyang Zhou
 */
public abstract class AbstractSchedulingConfigurator
	implements SchedulingConfigurator {

	public void destroy() {
		for (Map.Entry<String, List<SchedulerEntry>> schedulerEntries :
				_schedulerEntries.entrySet()) {

			for (SchedulerEntry schedulerEntry : schedulerEntries.getValue()) {
				try {
					destroySchedulerEntry(schedulerEntry);
				}
				catch (Exception e) {
					_log.error("Unable to unschedule " + schedulerEntry, e);
				}
			}
		}

		_schedulerEntries.clear();
	}

	public void init() {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			ClassLoader operatingClassLoader = getOperatingClassloader();

			currentThread.setContextClassLoader(operatingClassLoader);

			for (Map.Entry<String, List<SchedulerEntry>> schedulerEntries :
					_schedulerEntries.entrySet()) {

				String destinationName = schedulerEntries.getKey();

				for (SchedulerEntry schedulerEntry :
						schedulerEntries.getValue()) {

					try {
						initSchedulerEntry(destinationName, schedulerEntry);
					}
					catch (Exception e) {
						_log.error("Unable to schedule " + schedulerEntry, e);
					}
				}
			}
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	public void setMessageBus(MessageBus messageBus) {
		_messageBus = messageBus;
	}

	public void setSchedulerEngine(SchedulerEngine schedulerEngine) {
		_schedulerEngine = schedulerEngine;
	}

	public void setSchedulerEntries(
		Map<String, List<SchedulerEntry>> schedulerEntries) {

		_schedulerEntries = schedulerEntries;
	}

	protected void destroySchedulerEntry(SchedulerEntry schedulerEntry)
		throws Exception {

		Trigger trigger = schedulerEntry.getTrigger();

		_schedulerEngine.unschedule(
			trigger.getJobName(), trigger.getGroupName());
	}

	protected abstract ClassLoader getOperatingClassloader();

	protected void initSchedulerEntry(
			String destinationName, SchedulerEntry schedulerEntry)
		throws Exception {

		_messageBus.registerMessageListener(
			destinationName, schedulerEntry.getEventListener());

		_schedulerEngine.schedule(
			schedulerEntry.getTrigger(), schedulerEntry.getDescription(),
			destinationName, null);
	}

	private static Log _log = LogFactoryUtil.getLog(
		AbstractSchedulingConfigurator.class);

	private MessageBus _messageBus;
	private SchedulerEngine _schedulerEngine;
	private Map<String, List<SchedulerEntry>> _schedulerEntries =
		new HashMap<String, List<SchedulerEntry>>();

}