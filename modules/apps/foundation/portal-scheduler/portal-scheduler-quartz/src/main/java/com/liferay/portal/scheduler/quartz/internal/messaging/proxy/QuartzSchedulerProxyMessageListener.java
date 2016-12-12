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

package com.liferay.portal.scheduler.quartz.internal.messaging.proxy;

import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.proxy.ProxyMessageListener;
import com.liferay.portal.kernel.scheduler.SchedulerEngine;
import com.liferay.portal.scheduler.quartz.internal.QuartzSchedulerEngine;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tina Tian
 */
@Component(
	enabled = false, immediate = true,
	property = {"destination.name=" + DestinationNames.SCHEDULER_ENGINE},
	service = ProxyMessageListener.class
)
public class QuartzSchedulerProxyMessageListener extends ProxyMessageListener {

	@Override
	@Reference(unbind = "-")
	public void setMessageBus(MessageBus messageBus) {
		_messageBus = messageBus;
	}

	@Activate
	protected void activate() {
		setManager(_schedulerEngine);
		setMessageBus(_messageBus);
	}

	@Reference(service = QuartzSchedulerEngine.class, unbind = "-")
	protected void setSchedulerEngine(SchedulerEngine schedulerEngine) {
		_schedulerEngine = schedulerEngine;
	}

	private MessageBus _messageBus;
	private SchedulerEngine _schedulerEngine;

}