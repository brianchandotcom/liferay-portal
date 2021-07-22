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

package com.liferay.cluster.test.module.internel;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.SchedulerEntry;
import com.liferay.portal.kernel.scheduler.SchedulerEntryImpl;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerFactory;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tina Tian
 */
@Component(immediate = true, service = SchedulerTestMessageListener.class)
public class SchedulerTestMessageListener extends BaseMessageListener {

	@Activate
	protected void activate() {
		Class<?> clazz = getClass();

		String className = clazz.getName();

		Trigger trigger = _triggerFactory.createTrigger(
			className, className, null, null, 10, TimeUnit.SECOND);

		SchedulerEntry schedulerEntry = new SchedulerEntryImpl(
			className, trigger);

		_schedulerEngineHelper.register(
			this, schedulerEntry, "liferay/scheduler_dispatch");
	}

	@Deactivate
	protected void deactivate() {
		_schedulerEngineHelper.unregister(this);
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		if (_log.isDebugEnabled()) {
			_log.debug(
				"Memory clustered job from modules is triggered at " +
					System.currentTimeMillis());
		}
	}

	@Reference(
		target = "(module.service.lifecycle=portal.initialized)", unbind = "-"
	)
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	@Reference(unbind = "-")
	protected void setSchedulerEngineHelper(
		SchedulerEngineHelper schedulerEngineHelper) {

		_schedulerEngineHelper = schedulerEngineHelper;
	}

	@Reference(unbind = "-")
	protected void setTriggerFactory(TriggerFactory triggerFactory) {
		_triggerFactory = triggerFactory;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SchedulerTestMessageListener.class);

	private SchedulerEngineHelper _schedulerEngineHelper;
	private TriggerFactory _triggerFactory;

}