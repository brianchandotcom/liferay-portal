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

package com.liferay.portal.scheduler.internal;

import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.proxy.ProxyMessageListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tina Tian
 */
@Component(
	immediate = true, service = SchedulerProxyMessagingConfigurator.class
)
public class SchedulerProxyMessagingConfigurator {

	@Reference(unbind = "-")
	protected void setMessageBus(MessageBus messageBus) {
	}

	@Reference(
		service = ProxyMessageListener.class,
		target = "(destination.name=" + DestinationNames.SCHEDULER_ENGINE + ")",
		unbind = "-"
	)
	protected void setProxyMessageListener(
		ProxyMessageListener proxyMessageListener) {

		_proxyMessageListener = proxyMessageListener;
	}

	@Reference(
		service = Destination.class,
		target = "(destination.name=" + DestinationNames.SCHEDULER_DISPATCH + ")",
		unbind = "-"
	)
	protected void setSchedulerDispatchDestination(Destination destination) {
	}

	@Reference(
		service = Destination.class,
		target = "(destination.name=" + DestinationNames.SCHEDULER_ENGINE + ")",
		unbind = "unsetSchedulerEngineDestination"
	)
	protected void setSchedulerEngineDestination(Destination destination) {
		destination.register(_proxyMessageListener);
	}

	protected void unsetSchedulerEngineDestination(Destination destination) {
		destination.unregister(_proxyMessageListener);
	}

	private ProxyMessageListener _proxyMessageListener;

}